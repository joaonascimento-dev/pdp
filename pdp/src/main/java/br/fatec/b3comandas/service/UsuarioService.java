package br.fatec.b3comandas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.fatec.b3comandas.filtro.UsuarioFiltro;
import br.fatec.b3comandas.model.Usuario;
import br.fatec.b3comandas.repository.UsuarioRepository;

@Service("UsuarioService")
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepo;

    public Usuario save(Usuario usuario) {
        return usuarioRepo.save(usuario);
    }

    public Usuario findById(Integer id) {
        return usuarioRepo.findById(id).get();
    }
    
    public List<Usuario> findByCriteria(UsuarioFiltro usuarioFiltro) {
        return usuarioRepo.findByCriteria(usuarioFiltro);
    }
    
    public Page<Usuario> findByCriteria(Pageable pageable, UsuarioFiltro usuarioFiltro) {
        return usuarioRepo.findByCriteria(pageable, usuarioFiltro);
    }

    public void delete(Usuario usuario) {
        usuarioRepo.delete(usuario);
    }
    
}
