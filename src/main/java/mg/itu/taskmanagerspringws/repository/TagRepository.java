package mg.itu.taskmanagerspringws.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import mg.itu.taskmanagerspringws.model.Tag;
import mg.itu.taskmanagerspringws.enums.TagStatus;

public interface TagRepository extends JpaRepository<Tag, Long> {

    List<Tag> findByStatus(TagStatus status);

    List<Tag> findByNameContaining(String keyword);
}