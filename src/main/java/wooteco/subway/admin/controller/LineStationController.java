package wooteco.subway.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wooteco.subway.admin.domain.Line;
import wooteco.subway.admin.dto.LineResponse;
import wooteco.subway.admin.dto.LineStationCreateRequest;
import wooteco.subway.admin.service.LineService;

import java.net.URI;

@RestController
public class LineStationController {
    private LineService lineService;

    public LineStationController(LineService lineService) {
        this.lineService = lineService;
    }

    @PostMapping("/lines/{lineId}/stations")
    public ResponseEntity createLineStation(@RequestBody LineStationCreateRequest lineStationCreateRequest, @PathVariable Long lineId) {
        Line line = lineService.addLineStation(lineId, lineStationCreateRequest);
        return ResponseEntity
                .created(URI.create("/line-station/" + lineId + "/stations/" + lineStationCreateRequest.getStationId()))
                .body(LineResponse.of(line));
    }

    @GetMapping("/lines/{lineId}/stations")
    public ResponseEntity showLineStations(@PathVariable Long lineId) {
        return ResponseEntity
                .ok()
                .body(lineService.findStationsByLineId(lineId));
    }

    @DeleteMapping("/lines/{lineId}/stations/{stationId}")
    public ResponseEntity deleteLineStation(@PathVariable Long lineId, @PathVariable Long stationId) {
        lineService.removeLineStation(lineId, stationId);
        return ResponseEntity
                .noContent()
                .build();
    }
}
