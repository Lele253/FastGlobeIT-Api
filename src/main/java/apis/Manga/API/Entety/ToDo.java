package apis.Manga.API.Entety;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ToDo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    @Lob
    private String aufgabe;
    private Boolean erledigt;
}
