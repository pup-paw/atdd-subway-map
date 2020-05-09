package wooteco.subway.admin.service;

import org.springframework.stereotype.Service;
import wooteco.subway.admin.domain.Line;
import wooteco.subway.admin.domain.Station;
import wooteco.subway.admin.dto.LineResponse;
import wooteco.subway.admin.dto.LineStationCreateRequest;
import wooteco.subway.admin.dto.StationResponse;
import wooteco.subway.admin.repository.LineRepository;
import wooteco.subway.admin.repository.StationRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class LineService {
    private LineRepository lineRepository;
    private StationRepository stationRepository;

    public LineService(LineRepository lineRepository, StationRepository stationRepository) {
        this.lineRepository = lineRepository;
        this.stationRepository = stationRepository;
    }

    public Line save(Line line) {
        return lineRepository.save(line);
    }

    public List<LineResponse> showLines() {
        return LineResponse.listOf(lineRepository.findAll());
    }

    public void updateLine(Long id, Line line) {
        Line persistLine = lineRepository.findById(id).orElseThrow(RuntimeException::new);
        persistLine.update(line);
        lineRepository.save(persistLine);
    }

    public void deleteLineById(Long id) {
        lineRepository.deleteById(id);
    }

    public Line addLineStation(Long id, LineStationCreateRequest request) {
        Line line = lineRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당되는 노선을 찾을 수 없습니다."));
        line.addLineStation(request.toLineStation());
        return lineRepository.save(line);
    }

    public void removeLineStation(Long lineId, Long stationId) {
        Line line = lineRepository.findById(lineId).orElseThrow(() -> new IllegalArgumentException("해당되는 노선을 찾을 수 없습니다."));
        line.removeLineStationById(stationId);
        lineRepository.save(line);
    }

    public LineResponse findLineWithStationsById(Long id) {
        Line line = lineRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당되는 노선을 찾을 수 없습니다."));
        Set<Station> stations = stationRepository.findAllById(line.getLineStationsId());
        return LineResponse.of(line, stations);
    }

    public List<StationResponse> findStationsByLineId(Long id) {
        List<StationResponse> stations = new ArrayList<>();
        Line line = lineRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당되는 노선을 찾을 수 없습니다."));
        Set<Station> foundStations = stationRepository.findAllById(line.getLineStationsId());
        Iterator<Station> iterator = foundStations.iterator();
        while (iterator.hasNext()) {
            stations.add(StationResponse.of(iterator.next()));
        }
        return stations;
    }
}
