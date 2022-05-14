package wooteco.subway.dao.dto;

import wooteco.subway.domain.Section;

public class SectionDto {
    private final long id;
    private final long lineId;
    private final long upStationId;
    private final long downStationId;
    private final int distance;

    public SectionDto(Section section) {
        this(0L, section.getLineId(), section.getUpStation().getId(), section.getDownStation().getId(),
                section.getDistance());
    }

    public SectionDto(long id, long lineId, long upStationId, long downStationId, int distance) {
        this.id = id;
        this.lineId = lineId;
        this.upStationId = upStationId;
        this.downStationId = downStationId;
        this.distance = distance;
    }

    public long getId() {
        return id;
    }

    public long getLineId() {
        return lineId;
    }

    public long getUpStationId() {
        return upStationId;
    }

    public long getDownStationId() {
        return downStationId;
    }

    public int getDistance() {
        return distance;
    }
}
