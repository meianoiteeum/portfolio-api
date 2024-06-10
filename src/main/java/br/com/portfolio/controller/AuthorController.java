package br.com.portfolio.controller;

import br.com.portfolio.dto.AuthorDTO;
import br.com.portfolio.model.Author;
import br.com.portfolio.service.AuthorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@AllArgsConstructor
@RequestMapping("author")
public class AuthorController {

    private AuthorService service;

    @PostMapping
    @Transactional
    public ResponseEntity<Author> create(@Valid @RequestBody AuthorDTO authorDTO, UriComponentsBuilder uriBuilder){
        var author = service.saveOrUpdate(authorDTO);

        var uri = uriBuilder.path("/author/{id}").buildAndExpand(author.id()).toUri();

        return ResponseEntity.created(uri).body(author);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> findAuthor(@PathVariable String id){
        return ResponseEntity.ok(service.getAuthor(id));
    }

}
