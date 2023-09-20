package apis.Manga.API.Repository;

import apis.Manga.API.Entety.Paketprodukt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaketproduktRepository extends JpaRepository<Paketprodukt, Long> {
}