package wooteco.subway.dto;

import java.util.List;
import wooteco.subway.domain.Line;

public class LineResponse {
    private final Long id;
    private final String name;
    private final String color;
    private final List<StationResponse> stations;

    private LineResponse() {
        this(null, null, null, null);
    }

    public LineResponse(Line line) {
        this(line.getId(), line.getName(), line.getColor(), null);
    }

    public LineResponse(Long id, String name, String color) {
        this(id, name, color, null);
    }

    public LineResponse(Long id, String name, String color, List<StationResponse> stations) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.stations = stations;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public List<StationResponse> getStations() {
        return stations;
    }
}
