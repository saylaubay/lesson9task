package uz.pdp.lesson9task1.service;

import org.springframework.stereotype.Service;
import uz.pdp.lesson9task1.entity.User;
import uz.pdp.lesson9task1.payload.ApiResponse;
import uz.pdp.lesson9task1.payload.ProjectDto;

import java.util.UUID;


public interface ProjectService  {

    ApiResponse add(ProjectDto projectDto, User user);

    ApiResponse getAllProjects(User user);

    ApiResponse editProject(UUID id, ProjectDto projectDto);

    ApiResponse deleteProject(UUID id);

}
