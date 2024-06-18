package br.com.portfolio.service;

import br.com.portfolio.dto.ProjectDTO;
import br.com.portfolio.dto.ProjectDescriptionDTO;
import br.com.portfolio.exception.NotFoundException;
import br.com.portfolio.model.ProjectDetail;

import java.util.List;

public interface ProjectService {
    ProjectDTO save(ProjectDescriptionDTO dto) throws NotFoundException;
    ProjectDTO saveDetails(String id, List<ProjectDetail> detail);
    ProjectDTO findProject(String id);
    ProjectDTO updateProject(String id, ProjectDTO dto) throws NotFoundException;
    void deleteProject(String id) throws NotFoundException;
}
