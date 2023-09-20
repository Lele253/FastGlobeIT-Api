package apis.Manga.API.Entety;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String url;

    @OneToOne(mappedBy = "project")
    private User user;

    @OneToMany(mappedBy = "project")
    private List<Paket> pakete;

    @OneToMany(mappedBy = "project")
    private List<ToDo> toDos;

    //test
}
