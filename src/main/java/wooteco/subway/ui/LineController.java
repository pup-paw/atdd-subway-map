package wooteco.subway.ui;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import wooteco.subway.dto.LineCreateRequest;
import wooteco.subway.dto.LineCreateResponse;
import wooteco.subway.dto.LineRequest;
import wooteco.subway.dto.LineResponse;
import wooteco.subway.service.LineCreateService;
import wooteco.subway.service.LineService;

@RestController
@RequestMapping("/lines")
public class LineController {

    private final LineService lineService;
    private final LineCreateService lineCreateService;

    public LineController(LineService lineService, LineCreateService lineCreateService) {
        this.lineService = lineService;
        this.lineCreateService = lineCreateService;
    }

    @PostMapping
    public ResponseEntity<LineCreateResponse> createLine2(
            @RequestBody LineCreateRequest lineCreateRequest) {
        LineCreateResponse lineCreateResponse = lineCreateService.create(lineCreateRequest);
        return ResponseEntity.created(URI.create("/lines/" + lineCreateResponse.getId()))
                .body(lineCreateResponse);
    }

    @GetMapping
    public ResponseEntity<List<LineResponse>> findAllLine() {
        return ResponseEntity.ok(lineService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LineResponse> findLineById(@PathVariable Long id) {
        return ResponseEntity.ok(lineService.findById(id));
    }

    @PutMapping("/{id}")
    public void updateLine(@PathVariable Long id, @RequestBody LineRequest lineRequest) {
        lineService.update(id, lineRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        lineService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
