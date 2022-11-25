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
import br.fatec.pdp.filtro.EmpresaFiltro;
import br.fatec.pdp.model.Empresa;

@Repository
public class EmpresaRepositoryImpl implements RepositoryCustom<Empresa> {

    @Autowired
    EntityManager em;

    @Override
    public Page<Empresa> findByCriteria(Pageable pageable, BaseFiltro filtro){
        return PageUtil.create(pageable, (ArrayList<Empresa>) findByCriteria(filtro));
    }

    @Override
    public List<Empresa> findByCriteria(BaseFiltro filtro) {
        EmpresaFiltro empresaFiltro = (EmpresaFiltro) filtro;

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Empresa> cq = cb.createQuery(Empresa.class);
        Root<Empresa> rootEmpresa = cq.from(Empresa.class);

        List<Predicate> predicates = new ArrayList<>();
        if (empresaFiltro.getId() != null) {
            predicates.add(cb.equal(rootEmpresa.get("id"), empresaFiltro.getId()));
        }

        if (empresaFiltro.getNomeFantasia() != null) {
            predicates.add(cb.equal(rootEmpresa.get("nomeFantasia"), empresaFiltro.getNomeFantasia()));
        }

        if (empresaFiltro.isAprovacao() != null) {
            if (empresaFiltro.isAprovacao()) {
                predicates.add(cb.isTrue(rootEmpresa.get("aprovacao")));
            } else {
                predicates.add(cb.isFalse(rootEmpresa.get("aprovacao")));
            }
        } else {
            predicates.add(cb.isNull(rootEmpresa.get("aprovacao")));
        }

        /* Empresa não possui campo exclusao
        if(filtro.isExibirExcluidos() != null){
            if (!filtro.isExibirExcluidos()) {
                predicates.add(cb.isNull(rootProduto.get("exclusao")));
            } else {
                predicates.add(cb.isNotNull(rootProduto.get("exclusao")));
            }
        }*/

        cq.select(rootEmpresa).where(
                cb.and(predicates.toArray(new Predicate[]{}))
        );

        //Ordem
        List<Order> o = new ArrayList<>();
        if (filtro.getListOrdem().isEmpty()) {
            o.add(cb.desc(rootEmpresa.get("id")));

        } else {
            for (Map.Entry<EmpresaFiltro.OrdemEnum, String> ordem : filtro.getListOrdem()) {
                o.add(ordem.getKey().equals(EmpresaFiltro.OrdemEnum.ASC)
                        ? cb.asc(rootEmpresa.get(ordem.getValue()))
                        : cb.desc(rootEmpresa.get(ordem.getValue()))
                );
            }
        }

        cq.orderBy(o);
        TypedQuery<Empresa> query = em.createQuery(cq);

        if (filtro.getLimite() != null) {
            query.setMaxResults(filtro.getLimite());
        }

        return query.getResultList();
    }
    
}
