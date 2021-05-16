package uz.pdp.lesson9task1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lesson9task1.entity.SpaceUser;

public interface SpaceUserRepository extends JpaRepository<SpaceUser,Long> {



}
