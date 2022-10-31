package br.fatec.pdp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.fatec.pdp.filtro.EmpresaFiltro;
import br.fatec.pdp.model.Empresa;
import br.fatec.pdp.repository.EmpresaRepository;

@Service("EmpresaService")
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepo;

    public Empresa save(Empresa empresa) {
        return empresaRepo.save(empresa);
    }

    public Empresa findById(Integer id) {
        return empresaRepo.findById(id).get();
    }
    
    public List<Empresa> findByCriteria(EmpresaFiltro empresaFiltro) {
        return empresaRepo.findByCriteria(empresaFiltro);
    }
    
    public Page<Empresa> findByCriteria(Pageable pageable, EmpresaFiltro empresaFiltro) {
        return empresaRepo.findByCriteria(pageable, empresaFiltro);
    }

    public void delete(Empresa empresa) {
        empresaRepo.delete(empresa);
    }

    public Empresa getByCriteria(EmpresaFiltro empresaFiltro) {
        List<Empresa> listEmpresa = findByCriteria(empresaFiltro);
        return listEmpresa.isEmpty() ? null : listEmpresa.get(0);
    }
    
}
