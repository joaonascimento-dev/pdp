package br.fatec.pdp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.fatec.pdp.filtro.FormacaoFiltro;
import br.fatec.pdp.model.Formacao;
import br.fatec.pdp.repository.FormacaoRepository;

@Service("FormacaoService")
public class FormacaoService {

    @Autowired
    private FormacaoRepository formacaoRepo;

    public Formacao save(Formacao formacao) {
        return formacaoRepo.save(formacao);
    }

    public Formacao findById(Integer id) {
        return formacaoRepo.findById(id).get();
    }
    
    public List<Formacao> findByCriteria(FormacaoFiltro formacaoFiltro) {
        return formacaoRepo.findByCriteria(formacaoFiltro);
    }
    
    public Page<Formacao> findByCriteria(Pageable pageable, FormacaoFiltro formacaoFiltro) {
        return formacaoRepo.findByCriteria(pageable, formacaoFiltro);
    }

    public void delete(Formacao formacao) {
        formacaoRepo.delete(formacao);
    }
    
}
