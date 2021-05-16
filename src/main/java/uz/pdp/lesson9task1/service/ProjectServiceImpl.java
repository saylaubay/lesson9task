package uz.pdp.lesson9task1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.lesson9task1.entity.Project;
import uz.pdp.lesson9task1.entity.Space;
import uz.pdp.lesson9task1.entity.User;
import uz.pdp.lesson9task1.payload.ApiResponse;
import uz.pdp.lesson9task1.payload.ProjectDto;
import uz.pdp.lesson9task1.repository.ProjectRepository;
import uz.pdp.lesson9task1.repository.SpaceRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    SpaceRepository spaceRepository;

    @Override
    public ApiResponse add(ProjectDto projectDto, User user) {
        Optional<Space> op = spaceRepository.findById(projectDto.getSpaceId());
        if (!op.isPresent()){
            return new ApiResponse("Bunday id li space id topilmadi", false);
        }
        Space space = op.get();
        Project project = new Project(
                projectDto.getName(),
                space,
                true,
                false,
                projectDto.getColor()
        );

        projectRepository.save(project);

        return new ApiResponse("Project added", true);
    }

    @Override
    public ApiResponse getAllProjects(User user) {
        List<Project> projects = projectRepository.findBySpace_OwnerId(user.getId());
        return new ApiResponse("Project list", true, projects);
    }

    @Override
    public ApiResponse editProject(UUID id, ProjectDto projectDto) {
        Optional<Project> optionalProject = projectRepository.findById(id);
        if (!optionalProject.isPresent()){
            return new ApiResponse("Bunday id li project topilmadi", false);
        }
        Project project = optionalProject.get();
        project.setColor(projectDto.getColor());
        project.setName(projectDto.getName());

        Optional<Space> optionalSpace = spaceRepository.findById(projectDto.getSpaceId());
        if (!optionalSpace.isPresent()){
            return new ApiResponse("Bunday id li spaec topilmadi", false);
        }
        Space space = optionalSpace.get();

        project.setSpace(space);
        project.setAccessType(true);
        project.setArchived(false);

        projectRepository.save(project);

        return new ApiResponse("Project Edit qilindi", true);
    }

    @Override
    public ApiResponse deleteProject(UUID id) {
        try {
            projectRepository.deleteById(id);
            return new ApiResponse("Project o'chirilsi", true);
        }catch (Exception e){
            return new ApiResponse("Error", false);
        }
    }


}
