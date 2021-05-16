package uz.pdp.lesson9task1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lesson9task1.entity.Icon;

import java.util.UUID;

public interface IconRepository extends JpaRepository<Icon, UUID> {




}
