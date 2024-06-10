package br.com.portfolio.service.impl;

import br.com.portfolio.client.BrasilAPIClient;
import br.com.portfolio.dto.AuthorDTO;
import br.com.portfolio.mapper.AuthorMapper;
import br.com.portfolio.model.Address;
import br.com.portfolio.model.Author;
import br.com.portfolio.repository.AuthorRepository;
import br.com.portfolio.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository repository;
    private AuthorMapper mapping;
    private BrasilAPIClient service;

    @Override
    public Author saveOrUpdate(AuthorDTO dto) {
        var author = mapping.convertToEntity(dto);

        var cep = service.getCEP(dto.cep());

        var address = new Address(cep);

        return repository.save(author.address(address));
    }

    @Override
    public AuthorDTO getAuthor(String id) {
        var author = repository.findById(id).orElseThrow(RuntimeException::new);

        return mapping.convertToDto(author);
    }
}
