package uz.pdp.lesson9task1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.lesson9task1.entity.Project;
import uz.pdp.lesson9task1.entity.ProjectUser;
import uz.pdp.lesson9task1.entity.User;
import uz.pdp.lesson9task1.entity.enums.TaskPermission;
import uz.pdp.lesson9task1.payload.ApiResponse;
import uz.pdp.lesson9task1.repository.ProjectRepository;
import uz.pdp.lesson9task1.repository.ProjectUserRepository;
import uz.pdp.lesson9task1.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectUserServiceImpl implements ProjectUserService{

    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProjectUserRepository projectUserRepository;

    @Override
    public ApiResponse addUserToProject(UUID projectId, UUID userId) {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if (!optionalProject.isPresent()){
            return new ApiResponse("Bunday id li project topilamdi", false);
        }
        Project project = optionalProject.get();

        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()){
            return new ApiResponse("Bunday id li User topilmadi", false);
        }
        User user = optionalUser.get();

        ProjectUser projectUser = new ProjectUser(
                project,
                user,
                TaskPermission.VIEW_ONLY
        );

        projectUserRepository.save(projectUser);

        return new ApiResponse("Project user saqlandi", true);
    }

    @Override
    public ApiResponse editUser(UUID projectId, UUID userId) {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if (!optionalProject.isPresent()){
            return new ApiResponse("Bunday id li project topilamdi", false);
        }
        Project project = optionalProject.get();

        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()){
            return new ApiResponse("Bunday id li User topilmadi", false);
        }
        User user = optionalUser.get();

        ProjectUser projectUser = new ProjectUser();

        projectUser.setUser(user);

        projectUserRepository.save(projectUser);

        return new ApiResponse("Project User edit qilindi", true);
    }

    @Override
    public ApiResponse deleteUserInProject(UUID projectId, UUID userId) {
        try {
            projectUserRepository.deleteByProjectIdAndUserId(projectId, userId);
            return new ApiResponse("O'chirildi", true);
        }catch (Exception e){
            return new ApiResponse("Error", false);
        }
    }
}
