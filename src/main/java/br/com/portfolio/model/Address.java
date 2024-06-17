package br.com.portfolio.model;

import br.com.portfolio.dto.CepDTO;

public record Address(Integer zipCode, String street, String cityState) {
    public Address(CepDTO cepDTO){
        this(Integer.valueOf(cepDTO.cep()), cepDTO.street(), String.join("/", cepDTO.city(), cepDTO.state()));
    }
}
