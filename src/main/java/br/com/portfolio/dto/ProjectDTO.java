package br.com.portfolio.dto;

import br.com.portfolio.model.ProjectDetail;

import java.util.List;

public record ProjectDTO(String id, ProjectDescriptionDTO descriptionDTO, List<ProjectDetail> details) {}
