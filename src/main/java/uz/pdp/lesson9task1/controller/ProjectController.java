package uz.pdp.lesson9task1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson9task1.entity.Project;
import uz.pdp.lesson9task1.entity.User;
import uz.pdp.lesson9task1.payload.ApiResponse;
import uz.pdp.lesson9task1.payload.ProjectDto;
import uz.pdp.lesson9task1.repository.ProjectRepository;
import uz.pdp.lesson9task1.security.CurrentUser;
import uz.pdp.lesson9task1.service.ProjectService;
import uz.pdp.lesson9task1.service.ProjectUserService;

import java.util.UUID;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    ProjectService projectService;
    @Autowired
    ProjectUserService projectUserService;

    @PostMapping
    public HttpEntity<?> add(@RequestBody ProjectDto projectDto, @CurrentUser User user) {
        ApiResponse apiResponse = projectService.add(projectDto, user);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping
    public HttpEntity<?> getAllProjects(@CurrentUser User user) {
        ApiResponse apiResponse = projectService.getAllProjects(user);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PutMapping
    public HttpEntity<?> editProject(@RequestParam UUID id, @RequestBody ProjectDto projectDto) {
        ApiResponse apiResponse = projectService.editProject(id, projectDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @DeleteMapping
    public HttpEntity<?> deleteProject(@RequestParam UUID id) {
        ApiResponse apiResponse = projectService.deleteProject(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PostMapping("/addUserToProject")
    public HttpEntity<?> addUserToProject(@RequestParam UUID projectId,@RequestParam UUID userId) {
        ApiResponse apiResponse = projectUserService.addUserToProject(projectId, userId);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PutMapping("/editUsertoProject")
    public HttpEntity<?> editUser(@RequestParam UUID projectId, @RequestParam UUID userId) {
        ApiResponse apiResponse = projectUserService.editUser(projectId, userId);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @DeleteMapping("/deleteUserInProject")
    public HttpEntity<?> deleteUserInProject(@RequestParam UUID projectId,@RequestParam UUID userId) {
        ApiResponse apiResponse = projectUserService.deleteUserInProject(projectId, userId);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


}
