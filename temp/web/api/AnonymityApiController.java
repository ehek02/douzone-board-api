package project.web.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import project.service.AnonymityService;
import project.web.dto.AnonymityDto;
import project.web.dto.AnonymityReqDto;

import java.net.URI;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AnonymityApiController {
    private final AnonymityService anonymityService;

    @GetMapping("/anonymity")
    public ResponseEntity<List<AnonymityDto>> getAll() {
        return ResponseEntity.ok().body(anonymityService.findAll());
    }

    @PostMapping("/anonymity")
    public ResponseEntity<?> create(@RequestBody AnonymityReqDto dto) {
        anonymityService.create(dto);

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/anonymity").toUriString());
        return ResponseEntity.created(uri).build();
    }
}