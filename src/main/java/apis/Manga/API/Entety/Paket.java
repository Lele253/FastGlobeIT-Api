package apis.Manga.API.Entety;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Paket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @JsonIgnore
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    @ManyToOne
    private Project project;

    @OneToMany(mappedBy = "paket")
    private List<Paketprodukt> paketprodukte;
}
