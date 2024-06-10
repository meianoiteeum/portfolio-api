package br.com.portfolio.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document("author")
public record Author(@Id String id, String name, Integer age, Phone phone, Address address, List<Education> education, List<Experience> experience, @CreatedDate LocalDateTime createAt) {
    public Author address(Address address){
        return new Author(null, name, age, phone, address, education, experience, null);
    }
}
