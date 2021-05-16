package uz.pdp.lesson9task1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.lesson9task1.entity.WorkspaceUser;
import uz.pdp.lesson9task1.entity.enums.WorkspaceRoleName;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkspaceUserRepository extends JpaRepository<WorkspaceUser, UUID> {
    Optional<WorkspaceUser> findByWorkspaceIdAndUserId(Long workspace_id, UUID user_id);

    @Transactional
    @Modifying
    void deleteByWorkspaceIdAndUserId(Long workspace_id, UUID user_id);


    Optional<List<WorkspaceUser>> findByWorkspaceId(Long workspace_id);
//    Optional<List<WorkspaceUser>> findAllByWorkspaceId(Long workspace_id);
    Optional<List<WorkspaceUser>> findByWorkspaceIdAndWorkspaceRole_Name(Long workspace_id, String workspaceRole_name);


}
