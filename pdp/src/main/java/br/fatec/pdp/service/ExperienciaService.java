package br.fatec.pdp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.fatec.pdp.filtro.ExperienciaFiltro;
import br.fatec.pdp.model.Experiencia;
import br.fatec.pdp.repository.ExperienciaRepository;

@Service("ExperienciaService")
public class ExperienciaService {

    @Autowired
    private ExperienciaRepository experienciaRepo;

    public Experiencia save(Experiencia experiencia) {
        return experienciaRepo.save(experiencia);
    }

    public Experiencia findById(Integer id) {
        return experienciaRepo.findById(id).get();
    }
    
    public List<Experiencia> findByCriteria(ExperienciaFiltro experienciaFiltro) {
        return experienciaRepo.findByCriteria(experienciaFiltro);
    }
    
    public Page<Experiencia> findByCriteria(Pageable pageable, ExperienciaFiltro experienciaFiltro) {
        return experienciaRepo.findByCriteria(pageable, experienciaFiltro);
    }

    public void delete(Experiencia experiencia) {
        experienciaRepo.delete(experiencia);
    }
    
}
