package br.com.portfolio.dto;

import java.net.URI;

public record ProjectDescriptionDTO(
        String title,
        String subtitle,
        String description,
        URI logo,
        String urlReference,
        String authorId
) {
}
