package mg.itu.taskmanagerspringws.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    @ManyToOne
    @JsonIgnore
    private User user;
    @OneToMany(mappedBy = "project")
    private List<Task> tasks;
}
