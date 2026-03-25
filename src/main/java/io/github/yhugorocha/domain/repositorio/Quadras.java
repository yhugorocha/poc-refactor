package io.github.yhugorocha.domain.repositorio;

import io.github.yhugorocha.domain.entity.Quadra;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface Quadras extends JpaRepository<Quadra, Integer> {

    @Query(value = " SELECT q FROM Quadra q left join fetch q.regiao r where r.id =:idRegiao ")
    List<Quadra> quadraPorRegiao(@Param("idRegiao") Integer idRegiao);

}
