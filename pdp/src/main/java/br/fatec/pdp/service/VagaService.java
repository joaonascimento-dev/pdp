package br.fatec.pdp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.fatec.pdp.filtro.VagaFiltro;
import br.fatec.pdp.model.Vaga;
import br.fatec.pdp.repository.VagaRepository;

@Service("VagaService")
public class VagaService {

    @Autowired
    private VagaRepository vagaRepo;

    public Vaga save(Vaga vaga) {
        return vagaRepo.save(vaga);
    }

    public Vaga findById(Integer id) {
        return vagaRepo.findById(id).get();
    }
    
    public List<Vaga> findByCriteria(VagaFiltro vagaFiltro) {
        return vagaRepo.findByCriteria(vagaFiltro);
    }
    
    public Page<Vaga> findByCriteria(Pageable pageable, VagaFiltro vagaFiltro) {
        return vagaRepo.findByCriteria(pageable, vagaFiltro);
    }

    public void delete(Vaga vaga) {
        vagaRepo.delete(vaga);
    }
    
}
