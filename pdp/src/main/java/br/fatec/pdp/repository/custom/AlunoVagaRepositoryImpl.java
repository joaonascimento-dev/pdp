package br.fatec.pdp.repository.custom;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.fatec.pdp.filtro.BaseFiltro;
import br.fatec.pdp.filtro.AlunoVagaFiltro;
import br.fatec.pdp.model.AlunoVaga;

@Repository
public class AlunoVagaRepositoryImpl implements RepositoryCustom<AlunoVaga> {

    @Autowired
    EntityManager em;

    @Override
    public Page<AlunoVaga> findByCriteria(Pageable pageable, BaseFiltro filtro){
        return PageUtil.create(pageable, (ArrayList<AlunoVaga>) findByCriteria(filtro));
    }

    @Override
    public List<AlunoVaga> findByCriteria(BaseFiltro filtro) {
        AlunoVagaFiltro alunoVagaFiltro = (AlunoVagaFiltro) filtro;

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<AlunoVaga> cq = cb.createQuery(AlunoVaga.class);
        Root<AlunoVaga> rootAlunoVaga = cq.from(AlunoVaga.class);

        List<Predicate> predicates = new ArrayList<>();
        if (alunoVagaFiltro.getId() != null) {
            predicates.add(cb.equal(rootAlunoVaga.get("id"), alunoVagaFiltro.getId()));
        }

        if (alunoVagaFiltro.getAluno() != null) {
            predicates.add(cb.equal(rootAlunoVaga.get("aluno"), alunoVagaFiltro.getAluno()));
        }

        if (alunoVagaFiltro.getVaga() != null) {
            predicates.add(cb.equal(rootAlunoVaga.get("vaga"), alunoVagaFiltro.getVaga()));
        }

        /* AlunoVaga não possui campo exclusao
        if(filtro.isExibirExcluidos() != null){
            if (!filtro.isExibirExcluidos()) {
                predicates.add(cb.isNull(rootProduto.get("exclusao")));
            } else {
                predicates.add(cb.isNotNull(rootProduto.get("exclusao")));
            }
        }*/

        cq.select(rootAlunoVaga).where(
                cb.and(predicates.toArray(new Predicate[]{}))
        );

        //Ordem
        List<Order> o = new ArrayList<>();
        if (filtro.getListOrdem().isEmpty()) {
            o.add(cb.desc(rootAlunoVaga.get("id")));

        } else {
            for (Map.Entry<AlunoVagaFiltro.OrdemEnum, String> ordem : filtro.getListOrdem()) {
                o.add(ordem.getKey().equals(AlunoVagaFiltro.OrdemEnum.ASC)
                        ? cb.asc(rootAlunoVaga.get(ordem.getValue()))
                        : cb.desc(rootAlunoVaga.get(ordem.getValue()))
                );
            }
        }

        cq.orderBy(o);
        TypedQuery<AlunoVaga> query = em.createQuery(cq);

        if (filtro.getLimite() != null) {
            query.setMaxResults(filtro.getLimite());
        }

        return query.getResultList();
    }
    
}
