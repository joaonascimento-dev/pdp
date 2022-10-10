package br.fatec.b3comandas.repository.custom;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.fatec.b3comandas.filtro.BaseFiltro;

public interface RepositoryCustom <T> {
    List<T> findByCriteria(BaseFiltro filtro);

    Page<T> findByCriteria(Pageable pageable, BaseFiltro filtro);
}
