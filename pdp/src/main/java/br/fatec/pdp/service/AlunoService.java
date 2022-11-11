package br.fatec.pdp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.fatec.pdp.filtro.AlunoFiltro;
import br.fatec.pdp.model.Aluno;
import br.fatec.pdp.repository.AlunoRepository;

@Service("AlunoService")
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepo;

    public Aluno save(Aluno aluno) {
        return alunoRepo.save(aluno);
    }

    public Aluno findById(Integer id) {
        return alunoRepo.findById(id).get();
    }
    
    public List<Aluno> findByCriteria(AlunoFiltro alunoFiltro) {
        return alunoRepo.findByCriteria(alunoFiltro);
    }
    
    public Page<Aluno> findByCriteria(Pageable pageable, AlunoFiltro alunoFiltro) {
        return alunoRepo.findByCriteria(pageable, alunoFiltro);
    }

    public Aluno getByCriteria(AlunoFiltro alunoFiltro) {
        List<Aluno> listAluno = findByCriteria(alunoFiltro);
        return listAluno.isEmpty() ? null : listAluno.get(0);
    }

    public void delete(Aluno aluno) {
        alunoRepo.delete(aluno);
    }
    
}
