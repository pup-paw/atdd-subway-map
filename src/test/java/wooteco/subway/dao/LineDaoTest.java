package wooteco.subway.dao;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import wooteco.subway.domain.Line;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class LineDaoTest {

    private final LineDao lineDao;

    public LineDaoTest(LineDao lineDao) {
        this.lineDao = lineDao;
    }

    @BeforeEach
    void set() {
        lineDao.save(new Line("2호선", "green"));
    }

    @AfterEach
    void reset() {
        lineDao.deleteAll();
    }

    @Test
    @DisplayName("노선을 저장한다.")
    void save() {
        Line expectedLine = new Line("1호선", "blue");

        Line actualLine = lineDao.save(expectedLine);

        assertThat(actualLine).isEqualTo(expectedLine);
    }

    @Test
    @DisplayName("모든 노선을 조회한다")
    void findAll() {
        Line expectedLine = new Line("1호선", "blue");
        lineDao.save(expectedLine);

        List<Line> lines = lineDao.findAll();

        assertThat(lines).hasSize(2);
    }

    @Test
    @DisplayName("입력된 id의 노선을 삭제한다")
    void deleteById() {
        lineDao.deleteById(1L);

        assertThat(lineDao.findAll()).hasSize(0);
    }

    @Test
    @DisplayName("입력된 id의 노선을 수정한다.")
    void update() {
        Line expected = new Line(1L, "분당선", "green");

        lineDao.update(expected);

        assertThat(lineDao.findById(1L).orElseThrow()).isEqualTo(expected);
    }
}
