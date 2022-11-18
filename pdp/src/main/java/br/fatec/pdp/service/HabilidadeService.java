package br.fatec.pdp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.fatec.pdp.filtro.HabilidadeFiltro;
import br.fatec.pdp.model.Habilidade;
import br.fatec.pdp.repository.HabilidadeRepository;

@Service("HabilidadeService")
public class HabilidadeService {

    @Autowired
    private HabilidadeRepository habilidadeRepo;

    public Habilidade save(Habilidade habilidade) {
        return habilidadeRepo.save(habilidade);
    }

    public Habilidade findById(Integer id) {
        return habilidadeRepo.findById(id).get();
    }
    
    public List<Habilidade> findByCriteria(HabilidadeFiltro habilidadeFiltro) {
        return habilidadeRepo.findByCriteria(habilidadeFiltro);
    }
    
    public Page<Habilidade> findByCriteria(Pageable pageable, HabilidadeFiltro habilidadeFiltro) {
        return habilidadeRepo.findByCriteria(pageable, habilidadeFiltro);
    }

    public void delete(Habilidade habilidade) {
        habilidadeRepo.delete(habilidade);
    }
    
}
