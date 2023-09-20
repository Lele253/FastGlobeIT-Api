package apis.Manga.API.Entety;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Paketprodukt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String preis;
    
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "paket_id", referencedColumnName = "id")
    private Paket paket;
}
