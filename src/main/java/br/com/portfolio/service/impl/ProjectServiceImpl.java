package br.com.portfolio.service.impl;

import br.com.portfolio.dto.ProjectDTO;
import br.com.portfolio.dto.ProjectDescriptionDTO;
import br.com.portfolio.exception.NotFoundException;
import br.com.portfolio.mapper.ProjectMapper;
import br.com.portfolio.model.ProjectDetail;
import br.com.portfolio.repository.AuthorRepository;
import br.com.portfolio.repository.ProjectRepository;
import br.com.portfolio.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private ProjectMapper mapper;
    private ProjectRepository repository;
    private AuthorRepository authorRepository;

    @Override
    public ProjectDTO save(ProjectDescriptionDTO dto) throws NotFoundException {
        if(!authorRepository.existsById(dto.authorId()))
            throw new NotFoundException("Author not found");

        var document = mapper.convertToProject(dto);

        var project = repository.save(document);

        return mapper.covertProjectToProjectDTO(project);
    }

    @Override
    public ProjectDTO saveDetails(String id, List<ProjectDetail> detail) {
        var document = repository.findById(id).orElseThrow(RuntimeException::new);

        var project = repository.save(document.detail(detail));

        return mapper.covertProjectToProjectDTO(project);
    }

    @Override
    public ProjectDTO findProject(String id) {
        var project = repository.findById(id).orElseThrow(RuntimeException::new);

        return mapper.convertToProjectDTO(project);
    }

    @Override
    public ProjectDTO updateProject(String id, ProjectDTO dto) throws NotFoundException {
        existDocument(id);

        var newProject = mapper.convertProjectDTOToProject(dto);

        var document = repository.save(newProject.id(id));

        return mapper.convertToProjectDTO(document);
    }

    @Override
    public void deleteProject(String id) throws NotFoundException {
        existDocument(id);

        repository.deleteById(id);
    }

    private void existDocument(String id) throws NotFoundException {
        var exist = repository.existsById(id);

        if(!exist) throw new NotFoundException("Documento not found");
    }
}
