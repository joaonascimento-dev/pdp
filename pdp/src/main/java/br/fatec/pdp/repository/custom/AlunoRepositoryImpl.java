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
import br.fatec.pdp.filtro.AlunoFiltro;
import br.fatec.pdp.model.Aluno;

@Repository
public class AlunoRepositoryImpl implements RepositoryCustom<Aluno> {

    @Autowired
    EntityManager em;

    @Override
    public Page<Aluno> findByCriteria(Pageable pageable, BaseFiltro filtro){
        return PageUtil.create(pageable, (ArrayList<Aluno>) findByCriteria(filtro));
    }

    @Override
    public List<Aluno> findByCriteria(BaseFiltro filtro) {
        AlunoFiltro alunoFiltro = (AlunoFiltro) filtro;

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Aluno> cq = cb.createQuery(Aluno.class);
        Root<Aluno> rootAluno = cq.from(Aluno.class);

        List<Predicate> predicates = new ArrayList<>();
        if (alunoFiltro.getId() != null) {
            predicates.add(cb.equal(rootAluno.get("id"), alunoFiltro.getId()));
        }

        if (alunoFiltro.getNome() != null) {
            predicates.add(cb.equal(rootAluno.get("nome"), alunoFiltro.getNome()));
        }

        /* Aluno não possui campo exclusao
        if(filtro.isExibirExcluidos() != null){
            if (!filtro.isExibirExcluidos()) {
                predicates.add(cb.isNull(rootProduto.get("exclusao")));
            } else {
                predicates.add(cb.isNotNull(rootProduto.get("exclusao")));
            }
        }*/

        cq.select(rootAluno).where(
                cb.and(predicates.toArray(new Predicate[]{}))
        );

        //Ordem
        List<Order> o = new ArrayList<>();
        if (filtro.getListOrdem().isEmpty()) {
            o.add(cb.desc(rootAluno.get("id")));

        } else {
            for (Map.Entry<AlunoFiltro.OrdemEnum, String> ordem : filtro.getListOrdem()) {
                o.add(ordem.getKey().equals(AlunoFiltro.OrdemEnum.ASC)
                        ? cb.asc(rootAluno.get(ordem.getValue()))
                        : cb.desc(rootAluno.get(ordem.getValue()))
                );
            }
        }

        cq.orderBy(o);
        TypedQuery<Aluno> query = em.createQuery(cq);

        if (filtro.getLimite() != null) {
            query.setMaxResults(filtro.getLimite());
        }

        return query.getResultList();
    }
    
}
