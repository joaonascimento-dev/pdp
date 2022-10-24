package br.fatec.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.fatec.pdp.model.AlunoVaga;
import br.fatec.pdp.repository.custom.RepositoryCustom;

@Repository
public interface AlunoVagaRepository extends JpaRepository<AlunoVaga, Integer>, RepositoryCustom<AlunoVaga> {
    
}
