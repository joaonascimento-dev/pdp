package br.fatec.pdp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.fatec.pdp.filtro.AlunoVagaFiltro;
import br.fatec.pdp.model.AlunoVaga;
import br.fatec.pdp.repository.AlunoVagaRepository;

@Service("AlunoVagaService")
public class AlunoVagaService {

    @Autowired
    private AlunoVagaRepository alunovagaRepo;

    public AlunoVaga save(AlunoVaga alunovaga) {
        return alunovagaRepo.save(alunovaga);
    }

    public AlunoVaga findById(Integer id) {
        return alunovagaRepo.findById(id).get();
    }
    
    public List<AlunoVaga> findByCriteria(AlunoVagaFiltro alunovagaFiltro) {
        return alunovagaRepo.findByCriteria(alunovagaFiltro);
    }
    
    public Page<AlunoVaga> findByCriteria(Pageable pageable, AlunoVagaFiltro alunovagaFiltro) {
        return alunovagaRepo.findByCriteria(pageable, alunovagaFiltro);
    }

    public void delete(AlunoVaga alunovaga) {
        alunovagaRepo.delete(alunovaga);
    }
    
}
