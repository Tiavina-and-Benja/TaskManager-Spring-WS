package mg.itu.taskmanagerspringws.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import mg.itu.taskmanagerspringws.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findByNameContaining(String keyword);
}