package apis.Manga.API.Repository;

import apis.Manga.API.Entety.Paket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaketRepository extends JpaRepository<Paket, Long> {
}