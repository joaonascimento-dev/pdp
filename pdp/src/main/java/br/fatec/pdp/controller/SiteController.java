package br.fatec.pdp.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.fatec.pdp.filtro.AlunoFiltro;
import br.fatec.pdp.filtro.EmpresaFiltro;
import br.fatec.pdp.filtro.UsuarioFiltro;
import br.fatec.pdp.filtro.VagaFiltro;
import br.fatec.pdp.model.Aluno;
import br.fatec.pdp.model.AlunoVaga;
import br.fatec.pdp.model.Empresa;
import br.fatec.pdp.model.Experiencia;
import br.fatec.pdp.model.Formacao;
import br.fatec.pdp.model.Habilidade;
import br.fatec.pdp.model.Usuario;
import br.fatec.pdp.model.Vaga;
import br.fatec.pdp.service.AlunoService;
import br.fatec.pdp.service.AlunoVagaService;
import br.fatec.pdp.service.EmpresaService;
import br.fatec.pdp.service.ExperienciaService;
import br.fatec.pdp.service.UsuarioService;
import br.fatec.pdp.service.VagaService;
import br.fatec.pdp.util.Data;

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

    @Autowired
    private ExperienciaService experienciaService;

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

    @RequestMapping("/perfilAluno/{id}")
    public ModelAndView perfilAluno(@PathVariable(name = "id") Integer id, HttpSession session) {
        ModelAndView mav = new ModelAndView("perfilAluno");

        Aluno aluno = alunoService.findById(id);
        mav.addObject("aluno", aluno);

        mav.addObject("listExperiencia", aluno.getListExperiencia());
        mav.addObject("listFormacao", aluno.getListFormacao());
        mav.addObject("listHabilidade", aluno.getListHabilidade());
        return mav;
    }

    @RequestMapping("/experiencia/cadastrar/{id}")
    public ModelAndView experienciaCadastrar(@PathVariable(name = "id") Integer id, HttpSession session) {
        ModelAndView mav = new ModelAndView("experienciaCadastrar");

        Aluno aluno = alunoService.getByCriteria((AlunoFiltro) new AlunoFiltro.Builder().id(id).build());
        mav.addObject("aluno", aluno);

        Experiencia experiencia = new Experiencia();
        mav.addObject("experiencia", experiencia);

        return mav;
    }


    @PostMapping(value = "/cadastrarExperiencia/")
    public String formacaoCadastrar(Integer idAluno, String titulo, String cargo, String nomeEmpresa, String cidadeEmpresa, String estadoEmpresa, String paisEmpresa, String dataInicio, String dataFim, boolean atual, String descricao) {

        Aluno aluno = alunoService.findById(idAluno);

        Experiencia experiencia = new Experiencia();
        experiencia.setAluno(aluno);
        experiencia.setTitulo(titulo.trim().equals("") ? experiencia.getTitulo() : titulo.trim());
        experiencia.setCargo(cargo.trim().equals("") ? experiencia.getCargo() : cargo.trim());
        experiencia.setNomeEmpresa(nomeEmpresa.trim().equals("") ? experiencia.getNomeEmpresa() : nomeEmpresa.trim());
        experiencia.setCidadeEmpresa(cidadeEmpresa.trim().equals("") ? experiencia.getCidadeEmpresa() : cidadeEmpresa.trim());
        experiencia.setEstadoEmpresa(estadoEmpresa.trim().equals("") ? experiencia.getEstadoEmpresa() : estadoEmpresa.trim());
        experiencia.setPaisEmpresa(paisEmpresa.trim().equals("") ? experiencia.getPaisEmpresa() : paisEmpresa.trim());
        experiencia.setDataInicio(Data.fromStringISO(dataInicio).atStartOfDay());
        experiencia.setDataFim(Data.fromStringISO(dataFim).atStartOfDay());
        experiencia.setAtual(atual);
        experiencia.setDescricao(descricao.trim().equals("") ? experiencia.getDescricao() : descricao.trim());
        experienciaService.save(experiencia);

        return "redirect:/perfilAluno/" + experiencia.getAluno().getId();
    }

    @RequestMapping("/formacao/cadastrar/{id}")
    public ModelAndView formacaoCadastrar(@PathVariable(name = "id") Integer id, HttpSession session) {
        ModelAndView mav = new ModelAndView("formacaoCadastrar");

        Aluno aluno = alunoService.getByCriteria((AlunoFiltro) new AlunoFiltro.Builder().id(id).build());
        mav.addObject("aluno", aluno);

        Formacao formacao = new Formacao();
        mav.addObject("formacao", formacao);

        return mav;
    }

    @PostMapping(value = "/cadastrarFormacao/")
    public String formacaoCadastrar(Integer idAluno, String curso, String diploma, String instituicao, String dataInicio, String dataFim, boolean atual, String descricao) {

        Aluno aluno = alunoService.findById(idAluno);

        Formacao formacao = new Formacao();
        formacao.setAluno(aluno);
        formacao.setCurso(curso.trim().equals("") ? formacao.getCurso() : curso.trim());
        formacao.setDiploma(diploma.trim().equals("") ? formacao.getDiploma() : diploma.trim());
        formacao.setInstituicao(instituicao.trim().equals("") ? formacao.getInstituicao() : instituicao.trim());
        formacao.setDataInicio(Data.fromStringISO(dataInicio).atStartOfDay());
        formacao.setDataFim(Data.fromStringISO(dataFim).atStartOfDay());
        formacao.setAtual(atual);
        formacao.setDescricao(descricao.trim().equals("") ? formacao.getDescricao() : descricao.trim());

        return "redirect:/perfilAluno/" + formacao.getAluno().getId();
    }

    @RequestMapping("/formacao/editar/{id}")
    public ModelAndView formacaoEditar(@PathVariable(name = "id") Integer id,
            HttpSession session) {
        ModelAndView mav = new ModelAndView("formacaoEditar");

        Formacao formacao = new Formacao();
        mav.addObject("formacao", formacao);

        return mav;
    }

    @PostMapping(value = "/formacaoEditar/{id}")
    public String formacaoEditar(@PathVariable(name = "id") Integer idAluno, String curso, String diploma, String instituicao, String dataInicio, String dataFim, boolean atual, String descricao) {

        Aluno aluno = alunoService.findById(idAluno);

        Formacao formacao = new Formacao();
        formacao.setAluno(aluno);
        formacao.setCurso(curso.trim().equals("") ? formacao.getCurso() : curso.trim());
        formacao.setDiploma(diploma.trim().equals("") ? formacao.getDiploma() : diploma.trim());
        formacao.setInstituicao(instituicao.trim().equals("") ? formacao.getInstituicao() : instituicao.trim());
        formacao.setDataInicio(Data.fromStringISO(dataInicio).atStartOfDay());
        formacao.setDataFim(Data.fromStringISO(dataFim).atStartOfDay());
        formacao.setAtual(atual);
        formacao.setDescricao(descricao.trim().equals("") ? formacao.getDescricao() : descricao.trim());

        return "redirect:/perfilAluno/" + formacao.getAluno().getId();
    }

    @RequestMapping("/habilidade/cadastrar/{id}")
    public ModelAndView habilidadeCadastrar(@PathVariable(name = "id") Integer id, HttpSession session) {
        ModelAndView mav = new ModelAndView("habilidadeCadastrar");

        Aluno aluno = alunoService.getByCriteria((AlunoFiltro) new AlunoFiltro.Builder().id(id).build());
        mav.addObject("aluno", aluno);

        Habilidade habilidade = new Habilidade();
        mav.addObject("habilidade", habilidade);

        return mav;
    }

    @PostMapping(value = "/habilidadeCadastrar/")
    public String habilidadeCadastrar(Integer idAluno, String habilidade) {

        Aluno aluno = alunoService.findById(idAluno);

        Habilidade hab = new Habilidade();
        hab.setAluno(aluno);
        hab.setHabilidade(habilidade.trim().equals("") ? hab.getHabilidade() : habilidade.trim());

        return "redirect:/perfilAluno/" + hab.getAluno().getId();
    }

    @RequestMapping("/habilidade/editar/{id}")
    public ModelAndView habilidadeEditar(@PathVariable(name = "id") Integer id,
            HttpSession session) {
        ModelAndView mav = new ModelAndView("vagaEditar");

        Habilidade habilidade = new Habilidade();
        mav.addObject("habilidade", habilidade);

        return mav;
    }

    @PostMapping(value = "/habilidadeEditar/{id}")
    public String habilidadeEditar(@PathVariable(name = "id") Integer idAluno, String habilidade) {

        Aluno aluno = alunoService.findById(idAluno);

        Habilidade hab = new Habilidade();
        hab.setAluno(aluno);
        hab.setHabilidade(habilidade.trim().equals("") ? hab.getHabilidade() : habilidade.trim());

        return "redirect:/perfilAluno/" + hab.getAluno().getId();
    }

    
    @RequestMapping("/empresa/{id}")
    public ModelAndView empresa(@PathVariable(name = "id") Integer id, HttpSession session) {
        ModelAndView mav = new ModelAndView("empresa");

        Empresa empresa = empresaService.getByCriteria((EmpresaFiltro) new EmpresaFiltro.Builder().id(id).build());

        mav.addObject("empresa", empresa);
        mav.addObject("listVaga", empresa.getListVaga().stream().filter(v -> v.getExclusao() == null).collect(Collectors.toList()));

        return mav;
    }

    @RequestMapping("/vaga/cadastrar/{id}")
    public ModelAndView vagaCadastrar(@PathVariable(name = "id") Integer id, HttpSession session) {
        ModelAndView mav = new ModelAndView("vagaCadastrar");

        Empresa empresa = empresaService.getByCriteria((EmpresaFiltro) new EmpresaFiltro.Builder().id(id).build());
        mav.addObject("empresa", empresa);

        Vaga vaga = new Vaga();
        mav.addObject("vaga", vaga);

        return mav;
    }

    @PostMapping(value = "/cadastrarVaga/")
    public String vagaCadastrar(String titulo, String descricao, Integer idEmpresa) {

        Vaga vaga = new Vaga();
        vaga.setTitulo(titulo.trim().equals("") ? vaga.getTitulo() : titulo.trim());
        vaga.setDescricao(descricao.trim().equals("") ? vaga.getDescricao() : descricao.trim());
        vaga.setEmpresa(empresaService.getByCriteria((EmpresaFiltro) new EmpresaFiltro.Builder().id(idEmpresa).build()));
        vagaService.save(vaga);

        return "redirect:/empresa/" + vaga.getEmpresa().getId();
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
