package br.com.portfolio.controller;

import br.com.portfolio.dto.ProjectDTO;
import br.com.portfolio.dto.ProjectDescriptionDTO;
import br.com.portfolio.exception.NotFoundException;
import br.com.portfolio.model.ProjectDetail;
import br.com.portfolio.service.ProjectService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("projects")
public class ProjectController {

    private ProjectService service;

    @PostMapping
    @Transactional
    public ResponseEntity<ProjectDTO> create(@Valid @RequestBody ProjectDescriptionDTO dto, UriComponentsBuilder uriBuilder) throws NotFoundException {
        var project = service.save(dto);

        var uri = uriBuilder.path("/projects/{id}").buildAndExpand(project.id()).toUri();

        return ResponseEntity.created(uri).body(project);
    }

    @PostMapping("/{id}")
    @Transactional
    public ResponseEntity<ProjectDTO> createDetail(@PathVariable String id, @Valid @RequestBody List<ProjectDetail> details, UriComponentsBuilder uriBuilder){
        var project = service.saveDetails(id, details);

        var uri = uriBuilder.path("/projects/{id}").buildAndExpand(project.id()).toUri();

        return ResponseEntity.created(uri).body(project);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> findProject(@PathVariable String id){
        return ResponseEntity.ok(service.findProject(id));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable String id, @Valid @RequestBody ProjectDTO dto) throws NotFoundException {
        var newDto = service.updateProject(id, dto);

        return ResponseEntity.ok(newDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable String id) throws NotFoundException {
        service.deleteProject(id);

        return ResponseEntity.ok().build();
    }

}
