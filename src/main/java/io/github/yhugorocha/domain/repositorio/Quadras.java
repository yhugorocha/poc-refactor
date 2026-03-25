package io.github.yhugorocha.domain.repositorio;

import io.github.yhugorocha.domain.entity.Quadra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Quadras extends JpaRepository<Quadra, Integer> {

    @Query(value = " SELECT q FROM Quadra q left join fetch q.regiao r where r.id =:idRegiao ")
    List<Quadra> quadraPorRegiao(@Param("idRegiao") Integer idRegiao);

}
