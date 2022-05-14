package wooteco.subway.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import wooteco.subway.dao.dto.SectionDto;
import wooteco.subway.domain.Section;

@Repository
public class SectionDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper<SectionDto> sectionRowMapper = (resultSet, rowNum) ->
            new SectionDto(
                    resultSet.getLong("id"),
                    resultSet.getLong("line_id"),
                    resultSet.getLong("up_station_id"),
                    resultSet.getLong("down_station_id"),
                    resultSet.getInt("distance"));

    public SectionDao(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("section")
                .usingGeneratedKeyColumns("id");
    }

    public Section save(Section section) {
        SectionDto sectionDto = new SectionDto(section);
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(sectionDto);
        long id = simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();
        return new Section(id, sectionDto.getLineId(), section.getUpStation(), section.getDownStation(),
                sectionDto.getDistance());
    }

    public void update(List<SectionDto> sections) {
        for (SectionDto section : sections) {
            SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(section);
            simpleJdbcInsert.execute(parameterSource);
        }
    }

    public List<SectionDto> findById(long id) {
        String sql = "SELECT * FROM section WHERE id = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
        return namedParameterJdbcTemplate.query(sql, parameterSource, sectionRowMapper);
    }

    public List<SectionDto> findAll() {
        String sql = "SELECT * FROM section";
        return namedParameterJdbcTemplate.query(sql, sectionRowMapper);
    }

    public void deleteByLineId(long lineId) {
        String sql = " DELETE FROM section WHERE line_id = :lineId";
        SqlParameterSource parameterSource = new MapSqlParameterSource("lineId", lineId);
        namedParameterJdbcTemplate.update(sql, parameterSource);
    }

    public void deleteAll() {
        String sql = "TRUNCATE TABLE section";
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource());
        String resetIdSql = "ALTER TABLE section ALTER COLUMN id RESTART WITH 1";
        namedParameterJdbcTemplate.update(resetIdSql, new MapSqlParameterSource());
    }
}
