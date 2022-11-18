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
import br.fatec.pdp.filtro.FormacaoFiltro;
import br.fatec.pdp.model.Formacao;

@Repository
public class FormacaoRepositoryImpl implements RepositoryCustom<Formacao> {

    @Autowired
    EntityManager em;

    @Override
    public Page<Formacao> findByCriteria(Pageable pageable, BaseFiltro filtro) {
        return PageUtil.create(pageable, (ArrayList<Formacao>) findByCriteria(filtro));
    }

    @Override
    public List<Formacao> findByCriteria(BaseFiltro filtro) {
        FormacaoFiltro formacaoFiltro = (FormacaoFiltro) filtro;

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Formacao> cq = cb.createQuery(Formacao.class);
        Root<Formacao> rootFormacao = cq.from(Formacao.class);

        List<Predicate> predicates = new ArrayList<>();
        if (formacaoFiltro.getId() != null) {
            predicates.add(cb.equal(rootFormacao.get("id"), formacaoFiltro.getId()));
        }

        cq.select(rootFormacao).where(
                cb.and(predicates.toArray(new Predicate[] {})));

        // Ordem
        List<Order> o = new ArrayList<>();
        if (filtro.getListOrdem().isEmpty()) {
            o.add(cb.desc(rootFormacao.get("id")));

        } else {
            for (Map.Entry<FormacaoFiltro.OrdemEnum, String> ordem : filtro.getListOrdem()) {
                o.add(ordem.getKey().equals(FormacaoFiltro.OrdemEnum.ASC)
                        ? cb.asc(rootFormacao.get(ordem.getValue()))
                        : cb.desc(rootFormacao.get(ordem.getValue())));
            }
        }

        cq.orderBy(o);
        TypedQuery<Formacao> query = em.createQuery(cq);

        if (filtro.getLimite() != null) {
            query.setMaxResults(filtro.getLimite());
        }

        return query.getResultList();
    }

}
