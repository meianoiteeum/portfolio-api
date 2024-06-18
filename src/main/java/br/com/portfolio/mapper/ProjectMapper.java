package br.com.portfolio.mapper;

import br.com.portfolio.dto.ProjectDTO;
import br.com.portfolio.dto.ProjectDescriptionDTO;
import br.com.portfolio.model.Project;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProjectMapper {
    private ObjectMapper mapper;

    public Project convertToProject(ProjectDescriptionDTO dto){
        return mapper.convertValue(dto, Project.class).author(dto.authorId());
    }

    public ProjectDTO covertProjectToProjectDTO(Project project){
        var descriptionDTO = mapper.convertValue(project, ProjectDescriptionDTO.class);

        return new ProjectDTO(project.id(), descriptionDTO, project.details());
    }

    public ProjectDTO convertToProjectDTO(Project project){
        var dto = mapper.convertValue(project, ProjectDescriptionDTO.class);

        return new ProjectDTO(project.id(), dto, project.details());
    }

    public Project convertProjectDTOToProject(ProjectDTO dto){
        var project = mapper.convertValue(dto.descriptionDTO(), Project.class);

        return project.detail(dto.details());
    }
}
