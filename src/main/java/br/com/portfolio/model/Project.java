package br.com.portfolio.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@Document("project")
public record Project(
        @Id
        String id,
        String title,
        String subtitle,
        String description,
        URI logo,
        String urlReference,
        List<ProjectDetail> details,
        @DocumentReference
        Author author
) {
        public Project author(String authorId){
                return new Project(id, title, subtitle, description, logo, urlReference, details, new Author(authorId));
        }

        public Project detail(List<ProjectDetail> detail){
                if(Objects.isNull(details) || details.isEmpty())
                        return new Project(id, title, subtitle, description, logo, urlReference, detail, author);
                return new Project(id, title, subtitle, description, logo, urlReference, details, author);
        }

        public Project id(String id){
                return new Project(id, title, subtitle, description, logo, urlReference, details, author);
        }
}
