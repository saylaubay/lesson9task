package uz.pdp.lesson9task1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lesson9task1.entity.ProjectUser;

import java.util.UUID;

public interface ProjectUserRepository extends JpaRepository<ProjectUser, Long> {

    boolean deleteByProjectIdAndUserId(UUID project_id, UUID user_id);

}
