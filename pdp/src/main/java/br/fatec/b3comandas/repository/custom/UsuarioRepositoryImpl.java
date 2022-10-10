package br.fatec.b3comandas.repository.custom;

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

import br.fatec.b3comandas.filtro.BaseFiltro;
import br.fatec.b3comandas.filtro.UsuarioFiltro;
import br.fatec.b3comandas.model.Usuario;

@Repository
public class UsuarioRepositoryImpl implements RepositoryCustom<Usuario> {

    @Autowired
    EntityManager em;

    @Override
    public Page<Usuario> findByCriteria(Pageable pageable, BaseFiltro filtro){
        return PageUtil.create(pageable, (ArrayList<Usuario>) findByCriteria(filtro));
    }

    @Override
    public List<Usuario> findByCriteria(BaseFiltro filtro) {
        UsuarioFiltro usuarioFiltro = (UsuarioFiltro) filtro;

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);
        Root<Usuario> rootUsuario = cq.from(Usuario.class);

        List<Predicate> predicates = new ArrayList<>();
        if (usuarioFiltro.getId() != null) {
            predicates.add(cb.equal(rootUsuario.get("id"), usuarioFiltro.getId()));
        }

        if (usuarioFiltro.getLogin() != null) {
            predicates.add(cb.equal(rootUsuario.get("login"), usuarioFiltro.getLogin()));
        }

        /* Usuario não possui campo exclusao
        if(filtro.isExibirExcluidos() != null){
            if (!filtro.isExibirExcluidos()) {
                predicates.add(cb.isNull(rootProduto.get("exclusao")));
            } else {
                predicates.add(cb.isNotNull(rootProduto.get("exclusao")));
            }
        }*/

        cq.select(rootUsuario).where(
                cb.and(predicates.toArray(new Predicate[]{}))
        );

        //Ordem
        List<Order> o = new ArrayList<>();
        if (filtro.getListOrdem().isEmpty()) {
            o.add(cb.desc(rootUsuario.get("id")));

        } else {
            for (Map.Entry<UsuarioFiltro.OrdemEnum, String> ordem : filtro.getListOrdem()) {
                o.add(ordem.getKey().equals(UsuarioFiltro.OrdemEnum.ASC)
                        ? cb.asc(rootUsuario.get(ordem.getValue()))
                        : cb.desc(rootUsuario.get(ordem.getValue()))
                );
            }
        }

        cq.orderBy(o);
        TypedQuery<Usuario> query = em.createQuery(cq);

        if (filtro.getLimite() != null) {
            query.setMaxResults(filtro.getLimite());
        }

        return query.getResultList();
    }
    
}
