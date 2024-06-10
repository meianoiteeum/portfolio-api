package br.com.portfolio.service;

import br.com.portfolio.dto.AuthorDTO;
import br.com.portfolio.model.Author;

public interface AuthorService {
    Author saveOrUpdate(AuthorDTO dto);

    AuthorDTO getAuthor(String id);
}
