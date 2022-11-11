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
import br.fatec.pdp.filtro.ExperienciaFiltro;
import br.fatec.pdp.model.Experiencia;

@Repository
public class ExperienciaRepositoryImpl implements RepositoryCustom<Experiencia> {

    @Autowired
    EntityManager em;

    @Override
    public Page<Experiencia> findByCriteria(Pageable pageable, BaseFiltro filtro) {
        return PageUtil.create(pageable, (ArrayList<Experiencia>) findByCriteria(filtro));
    }

    @Override
    public List<Experiencia> findByCriteria(BaseFiltro filtro) {
        ExperienciaFiltro experienciaFiltro = (ExperienciaFiltro) filtro;

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Experiencia> cq = cb.createQuery(Experiencia.class);
        Root<Experiencia> rootExperiencia = cq.from(Experiencia.class);

        List<Predicate> predicates = new ArrayList<>();
        if (experienciaFiltro.getId() != null) {
            predicates.add(cb.equal(rootExperiencia.get("id"), experienciaFiltro.getId()));
        }

        cq.select(rootExperiencia).where(
                cb.and(predicates.toArray(new Predicate[] {})));

        // Ordem
        List<Order> o = new ArrayList<>();
        if (filtro.getListOrdem().isEmpty()) {
            o.add(cb.desc(rootExperiencia.get("id")));

        } else {
            for (Map.Entry<ExperienciaFiltro.OrdemEnum, String> ordem : filtro.getListOrdem()) {
                o.add(ordem.getKey().equals(ExperienciaFiltro.OrdemEnum.ASC)
                        ? cb.asc(rootExperiencia.get(ordem.getValue()))
                        : cb.desc(rootExperiencia.get(ordem.getValue())));
            }
        }

        cq.orderBy(o);
        TypedQuery<Experiencia> query = em.createQuery(cq);

        if (filtro.getLimite() != null) {
            query.setMaxResults(filtro.getLimite());
        }

        return query.getResultList();
    }

}
