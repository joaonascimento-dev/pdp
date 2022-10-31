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
import br.fatec.pdp.filtro.VagaFiltro;
import br.fatec.pdp.model.Vaga;

@Repository
public class VagaRepositoryImpl implements RepositoryCustom<Vaga> {

    @Autowired
    EntityManager em;

    @Override
    public Page<Vaga> findByCriteria(Pageable pageable, BaseFiltro filtro){
        return PageUtil.create(pageable, (ArrayList<Vaga>) findByCriteria(filtro));
    }

    @Override
    public List<Vaga> findByCriteria(BaseFiltro filtro) {
        VagaFiltro vagaFiltro = (VagaFiltro) filtro;

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Vaga> cq = cb.createQuery(Vaga.class);
        Root<Vaga> rootVaga = cq.from(Vaga.class);

        List<Predicate> predicates = new ArrayList<>();
        if (vagaFiltro.getId() != null) {
            predicates.add(cb.equal(rootVaga.get("id"), vagaFiltro.getId()));
        }

        if (vagaFiltro.isAprovacao()) {
            predicates.add(cb.isTrue(rootVaga.get("aprovacao")));
        } else {
            predicates.add(cb.isFalse(rootVaga.get("aprovacao")));
        }

        if (vagaFiltro.isAtivo()) {
            predicates.add(cb.isTrue(rootVaga.get("ativo")));
        } else {
            predicates.add(cb.isFalse(rootVaga.get("ativo")));
        }

        if(filtro.isExibirExcluidos() != null){
            if (!filtro.isExibirExcluidos()) {
                predicates.add(cb.isNull(rootVaga.get("exclusao")));
            } else {
                predicates.add(cb.isNotNull(rootVaga.get("exclusao")));
            }
        }

        cq.select(rootVaga).where(
                cb.and(predicates.toArray(new Predicate[]{}))
        );

        //Ordem
        List<Order> o = new ArrayList<>();
        if (filtro.getListOrdem().isEmpty()) {
            o.add(cb.desc(rootVaga.get("id")));

        } else {
            for (Map.Entry<VagaFiltro.OrdemEnum, String> ordem : filtro.getListOrdem()) {
                o.add(ordem.getKey().equals(VagaFiltro.OrdemEnum.ASC)
                        ? cb.asc(rootVaga.get(ordem.getValue()))
                        : cb.desc(rootVaga.get(ordem.getValue()))
                );
            }
        }

        cq.orderBy(o);
        TypedQuery<Vaga> query = em.createQuery(cq);

        if (filtro.getLimite() != null) {
            query.setMaxResults(filtro.getLimite());
        }

        return query.getResultList();
    }
    
}
