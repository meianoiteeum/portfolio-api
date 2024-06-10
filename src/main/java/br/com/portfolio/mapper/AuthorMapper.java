package br.com.portfolio.mapper;

import br.com.portfolio.dto.AuthorDTO;
import br.com.portfolio.model.Author;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthorMapper {

    private ObjectMapper mapper;

    public Author convertToEntity(AuthorDTO dto){
        return mapper.convertValue(dto, Author.class);
    }

    public AuthorDTO convertToDto(Author entity){
        return mapper.convertValue(entity, AuthorDTO.class);
    }

}
