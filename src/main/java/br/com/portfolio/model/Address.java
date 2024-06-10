package br.com.portfolio.model;

import br.com.portfolio.dto.Cep;

public record Address(Integer zipCode, String street, String cityState) {
    public Address(Cep cep){
        this(Integer.valueOf(cep.cep()), cep.street(), String.join("/", cep.city(), cep.state()));
    }
}
