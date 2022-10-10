package br.fatec.b3comandas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.fatec.b3comandas.model.Usuario;
import br.fatec.b3comandas.repository.custom.RepositoryCustom;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>, RepositoryCustom<Usuario> {
    
}
