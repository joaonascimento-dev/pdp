package br.fatec.pdp.repository.custom;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.fatec.pdp.filtro.BaseFiltro;

public interface RepositoryCustom <T> {
    List<T> findByCriteria(BaseFiltro filtro);

    Page<T> findByCriteria(Pageable pageable, BaseFiltro filtro);
}
