package uz.pdp.lesson9task1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lesson9task1.entity.Workspace;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {
    boolean existsByOwnerIdAndName(UUID owner_id, String name);

    Optional<List<Workspace>> findByOwnerId(UUID owner_id);

    Optional<List<Workspace>> findAllByOwnerId(UUID owner_id);

}
