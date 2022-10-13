package br.fatec.b3comandas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.fatec.b3comandas.model.Vaga;
import br.fatec.b3comandas.repository.custom.RepositoryCustom;

@Repository
public interface VagaRepository extends JpaRepository<Vaga, Integer>, RepositoryCustom<Vaga> {
    
}
