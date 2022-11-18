package br.fatec.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.fatec.pdp.model.Habilidade;
import br.fatec.pdp.repository.custom.RepositoryCustom;

@Repository
public interface HabilidadeRepository extends JpaRepository<Habilidade, Integer>, RepositoryCustom<Habilidade> {
    
}
