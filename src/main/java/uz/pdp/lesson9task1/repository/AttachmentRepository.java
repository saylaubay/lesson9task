package uz.pdp.lesson9task1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lesson9task1.entity.Attachment;

import java.util.UUID;

public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {
}
