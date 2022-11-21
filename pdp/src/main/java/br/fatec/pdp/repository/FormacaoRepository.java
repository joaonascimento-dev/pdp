package br.fatec.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.fatec.pdp.model.Formacao;
import br.fatec.pdp.repository.custom.RepositoryCustom;

@Repository
public interface FormacaoRepository extends JpaRepository<Formacao, Integer>, RepositoryCustom<Formacao> {
    
}