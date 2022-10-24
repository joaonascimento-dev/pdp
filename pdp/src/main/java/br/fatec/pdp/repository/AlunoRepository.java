package br.fatec.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.fatec.pdp.model.Aluno;
import br.fatec.pdp.repository.custom.RepositoryCustom;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Integer>, RepositoryCustom<Aluno> {
    
}
