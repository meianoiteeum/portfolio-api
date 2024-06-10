package br.com.portfolio.dto;

import br.com.portfolio.model.Education;
import br.com.portfolio.model.Experience;
import br.com.portfolio.model.Phone;

import java.util.List;

public record AuthorDTO(String name, Integer age, Phone phone, String cep, List<Education> education, List<Experience> experience) {
}
