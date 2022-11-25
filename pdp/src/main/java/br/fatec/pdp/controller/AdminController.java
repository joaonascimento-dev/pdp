package br.fatec.pdp.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.fatec.pdp.filtro.EmpresaFiltro;
import br.fatec.pdp.filtro.VagaFiltro;
import br.fatec.pdp.model.Empresa;
import br.fatec.pdp.model.Vaga;
import br.fatec.pdp.service.EmpresaService;
import br.fatec.pdp.service.VagaService;

@Controller
public class AdminController {

    @Autowired
    private VagaService vagaService;

    @Autowired
    private EmpresaService empresaService;
    
    @RequestMapping("/admin")
    public ModelAndView index(HttpSession session) {
        ModelAndView mav = new ModelAndView("admin/index");

        return mav;
    }

    @RequestMapping("/admin/aprovarVagas")
    public ModelAndView aprovarVagas(HttpSession session) {
        ModelAndView mav = new ModelAndView("admin/aprovarVaga");

        List<Vaga> listVaga = vagaService.findByCriteria(
            (VagaFiltro) new VagaFiltro.Builder().aprovacao(null).build()
            );
        mav.addObject("listVaga", listVaga);

        return mav;

    }

    @RequestMapping("admin/vaga/editar/{id}")
    public ModelAndView vagaEditar(@PathVariable(name = "id") Integer id,
            HttpSession session) {
        ModelAndView mav = new ModelAndView("admin/vagaEditar");

        Vaga vaga = vagaService.findById(id);
        mav.addObject("vaga", vaga);

        return mav;
    }

    @PostMapping(value = "/aprovarVaga/{id}")
    public String vagaAprovar(@PathVariable(name = "id") Integer id) {

        Vaga vaga = vagaService.findById(id);
        vaga.setAprovacao(true);
        vagaService.save(vaga);

        return "redirect:/admin/aprovarVagas";
    }

    @PostMapping(value = "/reprovarVaga/{id}")
    public String vagaReprovar(@PathVariable(name = "id") Integer id) {

        Vaga vaga = vagaService.findById(id);
        vaga.setAprovacao(false);
        vagaService.save(vaga);

        return "redirect:/admin/aprovarVagas";
    }

    @RequestMapping("/admin/aprovarEmpresas")
    public ModelAndView aprovarEmpresas(HttpSession session) {
        ModelAndView mav = new ModelAndView("admin/aprovarEmpresa");

        List<Empresa> listEmpresa = empresaService.findByCriteria(
            (EmpresaFiltro) new EmpresaFiltro.Builder().aprovacao(null).build()
            );
        mav.addObject("listEmpresa", listEmpresa);

        return mav;

    }

    @RequestMapping("admin/empresa/editar/{id}")
    public ModelAndView empresaEditar(@PathVariable(name = "id") Integer id,
            HttpSession session) {
        ModelAndView mav = new ModelAndView("admin/empresaEditar");

        Empresa empresa = empresaService.findById(id);
        mav.addObject("empresa", empresa);

        return mav;
    }

    @PostMapping(value = "/aprovarEmpresa/{id}")
    public String empresaAprovar(@PathVariable(name = "id") Integer id) {

        Empresa empresa = empresaService.findById(id);
        empresa.setAprovacao(true);
        empresaService.save(empresa);

        return "redirect:/admin/aprovarEmpresas";
    }

    @PostMapping(value = "/reprovarEmpresa/{id}")
    public String empresaReprovar(@PathVariable(name = "id") Integer id) {

        Empresa empresa = empresaService.findById(id);
        empresa.setAprovacao(false);
        empresaService.save(empresa);

        return "redirect:/admin/aprovarEmpresas";
    }

}
