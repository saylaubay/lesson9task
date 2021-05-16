package uz.pdp.lesson9task1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lesson9task1.entity.Space;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpaceRepository extends JpaRepository<Space,Long> {

        boolean existsByOwnerIdAndNameAndWorkspaceId(UUID owner_id, String name, Long workspace_id);

        Optional<List<Space>> findByOwnerId(UUID owner_id);

}
