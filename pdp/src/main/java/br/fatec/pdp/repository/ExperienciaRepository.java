package br.fatec.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.fatec.pdp.model.Experiencia;
import br.fatec.pdp.repository.custom.RepositoryCustom;

@Repository
public interface ExperienciaRepository extends JpaRepository<Experiencia, Integer>, RepositoryCustom<Experiencia> {
    
}
