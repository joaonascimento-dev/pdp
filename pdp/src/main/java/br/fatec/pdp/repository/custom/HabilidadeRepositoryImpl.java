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
import br.fatec.pdp.filtro.HabilidadeFiltro;
import br.fatec.pdp.model.Habilidade;

@Repository
public class HabilidadeRepositoryImpl implements RepositoryCustom<Habilidade> {

    @Autowired
    EntityManager em;

    @Override
    public Page<Habilidade> findByCriteria(Pageable pageable, BaseFiltro filtro) {
        return PageUtil.create(pageable, (ArrayList<Habilidade>) findByCriteria(filtro));
    }

    @Override
    public List<Habilidade> findByCriteria(BaseFiltro filtro) {
        HabilidadeFiltro habilidadeFiltro = (HabilidadeFiltro) filtro;

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Habilidade> cq = cb.createQuery(Habilidade.class);
        Root<Habilidade> rootHabilidade = cq.from(Habilidade.class);

        List<Predicate> predicates = new ArrayList<>();
        if (habilidadeFiltro.getId() != null) {
            predicates.add(cb.equal(rootHabilidade.get("id"), habilidadeFiltro.getId()));
        }

        cq.select(rootHabilidade).where(
                cb.and(predicates.toArray(new Predicate[] {})));

        // Ordem
        List<Order> o = new ArrayList<>();
        if (filtro.getListOrdem().isEmpty()) {
            o.add(cb.desc(rootHabilidade.get("id")));

        } else {
            for (Map.Entry<HabilidadeFiltro.OrdemEnum, String> ordem : filtro.getListOrdem()) {
                o.add(ordem.getKey().equals(HabilidadeFiltro.OrdemEnum.ASC)
                        ? cb.asc(rootHabilidade.get(ordem.getValue()))
                        : cb.desc(rootHabilidade.get(ordem.getValue())));
            }
        }

        cq.orderBy(o);
        TypedQuery<Habilidade> query = em.createQuery(cq);

        if (filtro.getLimite() != null) {
            query.setMaxResults(filtro.getLimite());
        }

        return query.getResultList();
    }

}
