package br.fatec.pdp.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.fatec.pdp.filtro.EmpresaFiltro;
import br.fatec.pdp.filtro.UsuarioFiltro;
import br.fatec.pdp.filtro.VagaFiltro;
import br.fatec.pdp.model.Aluno;
import br.fatec.pdp.model.AlunoVaga;
import br.fatec.pdp.model.Empresa;
import br.fatec.pdp.model.Usuario;
import br.fatec.pdp.model.Vaga;
import br.fatec.pdp.service.AlunoService;
import br.fatec.pdp.service.AlunoVagaService;
import br.fatec.pdp.service.EmpresaService;
import br.fatec.pdp.service.UsuarioService;
import br.fatec.pdp.service.VagaService;

//@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class SiteController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private VagaService vagaService;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private AlunoVagaService alunoVagaService;

    @Autowired
    private EmpresaService empresaService;

    @RequestMapping("/")
    public ModelAndView index(HttpSession session) {
        ModelAndView mav = new ModelAndView("index");

        List<Vaga> listVaga = vagaService.findByCriteria((VagaFiltro) new VagaFiltro.Builder().limite(3).build());
        mav.addObject("listVaga", listVaga);

        return mav;
    }

    @RequestMapping("/login")
    public ModelAndView login(HttpSession session) {
        ModelAndView mav = new ModelAndView("login");

        return mav;
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("login") String login,
            @ModelAttribute("password") String senha,
            HttpSession session) {

        UsuarioFiltro filtro = new UsuarioFiltro.Builder().login(login).build();

        List<Usuario> listUsuario = usuarioService.findByCriteria(filtro);
        Usuario usuario = null;

        if (listUsuario != null && !listUsuario.isEmpty()) {
            usuario = listUsuario.get(0);
        } else {
            System.out.println("Login não encontrado!");
            return "redirect:/login?invalido";
        }

        if (!senha.equals(usuario.getSenha())) {
            System.out.println("Senha incorreta!");
            return "redirect:/login?error";
        }

        session.setAttribute("usuario", usuario);

        return "redirect:/";
    }

    @RequestMapping("/vagas")
    public ModelAndView vagas(HttpSession session) {
        ModelAndView mav = new ModelAndView("vagas");

        List<Vaga> listVaga = vagaService.findByCriteria((VagaFiltro) new VagaFiltro.Builder().build());
        mav.addObject("listVaga", listVaga);

        return mav;
    }

    @RequestMapping("/vaga/{id}")
    public ModelAndView vaga(@PathVariable(name = "id") Integer id,
            HttpSession session) {
        ModelAndView mav = new ModelAndView("vagaDetalhe");

        Vaga vaga = vagaService.findById(id);
        mav.addObject("vaga", vaga);

        return mav;
    }

    @RequestMapping("/vaga/aplicar/{id}")
    public String vagaAplicar(@PathVariable(name = "id") Integer id,
            HttpSession session) {
        //ModelAndView mav = new ModelAndView("vagaDetalhe");

        Vaga vaga = vagaService.findById(id);
        //mav.addObject("vaga", vaga);

        
        //Puxar usuario q está logado
        //Usuario usuario = usuarioService.findById(1);
        Aluno aluno = alunoService.findById(1);
        
        AlunoVaga alunoVaga = new AlunoVaga();
        alunoVaga.setAluno(aluno);
        alunoVaga.setVaga(vaga);
        alunoVagaService.save(alunoVaga);

        return "redirect:/vaga/aplicar/sucesso";
    }

    @RequestMapping("/vaga/aplicar/sucesso")
    public ModelAndView vagasAplicarSucesso(HttpSession session) {
        ModelAndView mav = new ModelAndView("vagaAplicarSucesso");

        return mav;
    }

    @RequestMapping("/cadastroEscolher")
    public ModelAndView cadastro(HttpSession session) {
        ModelAndView mav = new ModelAndView("cadastroEscolher");

        return mav;
    }

    @RequestMapping("/CadastroUsuarioEmpresa")
    public ModelAndView cadastroUsuEmp(HttpSession session) {
        ModelAndView mav = new ModelAndView("CadastroUsuarioEmpresa");

        return mav;
    }

    @RequestMapping("/cadastroEmpresa")
    public ModelAndView cadastroEmp(HttpSession session) {
        ModelAndView mav = new ModelAndView("cadastroEmpresa");

        return mav;
    }

    @RequestMapping("/cadastroAluno")
    public ModelAndView cadastroAluno(HttpSession session) {
        ModelAndView mav = new ModelAndView("cadastroAluno");

        return mav;
    }

    
    @RequestMapping("/empresa/{id}")
    public ModelAndView empresa(@PathVariable(name = "id") Integer id, HttpSession session) {
        ModelAndView mav = new ModelAndView("empresa");

        Empresa empresa = empresaService.getByCriteria((EmpresaFiltro) new EmpresaFiltro.Builder().id(id).build());

        mav.addObject("empresa", empresa);
        mav.addObject("listVaga", empresa.getListVaga());

        return mav;
    }

    @RequestMapping("/vaga/editar/{id}")
    public ModelAndView vagaEditar(@PathVariable(name = "id") Integer id,
            HttpSession session) {
        ModelAndView mav = new ModelAndView("vagaEditar");

        Vaga vaga = vagaService.findById(id);
        mav.addObject("vaga", vaga);

        return mav;
    }

    @PostMapping(value = "/editarVaga/{id}")
    public String vagaEditar(@PathVariable(name = "id") Integer id, String titulo, String descricao) {

        Vaga vaga = vagaService.findById(id);
        vaga.setTitulo(titulo.trim().equals("") ? vaga.getTitulo() : titulo.trim());
        vaga.setDescricao(descricao.trim().equals("") ? vaga.getDescricao() : descricao.trim());
        vagaService.save(vaga);

        return "redirect:/empresa/" + vaga.getEmpresa().getId();
    }

    @PostMapping(value = "/excluirVaga/{id}")
    public String vagaExcluir(@PathVariable(name = "id") Integer id) {

        Vaga vaga = vagaService.findById(id);
        vaga.setExclusao(LocalDateTime.now());
        vagaService.save(vaga);

        return "redirect:/empresa/" + vaga.getEmpresa().getId();
    }
}
