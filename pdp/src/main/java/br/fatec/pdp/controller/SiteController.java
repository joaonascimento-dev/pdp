package br.fatec.pdp.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
import br.fatec.pdp.service.FormacaoService;
import br.fatec.pdp.service.HabilidadeService;
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

    @Autowired
    private FormacaoService formacaoService;

    @Autowired
    private HabilidadeService habilidadeService;

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
            @ModelAttribute("senha") String senha,
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

    @RequestMapping("/logoff")
    public String logoff(HttpSession session) {
        System.out.println("Logoff");
        session.setAttribute("usuario", null);

        return "redirect:/";
    }

    @RequestMapping("/vagas")
    public ModelAndView vagas(Optional<String> busca, HttpSession session) {
        ModelAndView mav = new ModelAndView("vagas");

        mav.addObject("busca", busca.orElse(null));

        List<Vaga> listVaga = vagaService.findByCriteria(
            (VagaFiltro) new VagaFiltro.Builder().busca(busca.orElse(null)).build()
            );
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

    @PostMapping(value = "/cadastrarUsuEmp/")
    public String cadastrarUsuEmp(String login, String senha) {

        Usuario usuario = new Usuario();
        usuario.setLogin(login);
        usuario.setSenha(senha);
        usuarioService.save(usuario);

        return "redirect:/cadastroEmpresa2/" + usuario.getId();

    }

    @RequestMapping("/cadastroEmpresa1")
    public ModelAndView cadastroEmpresa1(HttpSession session) {
        ModelAndView mav = new ModelAndView("cadastroEmpresa1");

        return mav;
    }

    @RequestMapping("/cadastroEmpresa2/{id}")
    public ModelAndView cadastroEmp(@PathVariable(name = "id") Integer id, HttpSession session) {
        ModelAndView mav = new ModelAndView("cadastroEmpresa2");

        mav.addObject("idUsuario", id);

        return mav;
    }

    @PostMapping(value = "/cadastrarEmpresa/")
    public String formacaoCadastrar(Integer idUsuario, String nomeFantasia, String razaoSocial, String cnpj, String telefone, String email, String logradouro,
    String numero, String cep, String complemento, String bairro, String cidade, String estado, String pais) {

        Empresa empresa = new Empresa();

        Usuario usuario = usuarioService.findById(idUsuario);
        
        empresa.setUsuario(usuario);
        empresa.setNomeFantasia(nomeFantasia.trim().equals("") ? empresa.getNomeFantasia() : nomeFantasia.trim());
        empresa.setRazaoSocial(razaoSocial.trim().equals("") ? empresa.getRazaoSocial() : razaoSocial.trim());
        empresa.setCnpj(cnpj.trim().equals("") ? empresa.getCnpj() : cnpj.trim());
        empresa.setTelefone(telefone.trim().equals("") ? empresa.getTelefone() : telefone.trim());
        empresa.setEmail(email.trim().equals("") ? empresa.getEmail() : email.trim());
        empresa.setLogradouro(logradouro.trim().equals("") ? empresa.getLogradouro() : logradouro.trim());
        empresa.setNumero(numero.trim().equals("") ? empresa.getNumero() : numero.trim());
        empresa.setCep(cep.trim().equals("") ? empresa.getCep() : cep.trim());
        empresa.setComplemento(complemento.trim().equals("") ? empresa.getComplemento() : complemento.trim());
        empresa.setBairro(bairro.trim().equals("") ? empresa.getBairro() : bairro.trim());
        empresa.setCidade(cidade.trim().equals("") ? empresa.getCidade() : cidade.trim());
        empresa.setEstado(estado.trim().equals("") ? empresa.getEstado() : estado.trim());
        empresa.setPais(pais.trim().equals("") ? empresa.getPais() : pais.trim());
        empresaService.save(empresa);
        usuario.setEmpresa(empresa);
        usuarioService.save(usuario);

        return "redirect:/login";
    }

    @RequestMapping("/empresa/editar/{id}")
    public ModelAndView editarEmpresa(@PathVariable(name = "id") Integer id,
            HttpSession session) {
        ModelAndView mav = new ModelAndView("editarEmpresa");

        Empresa empresa = empresaService.findById(id);
        mav.addObject("empresa", empresa);

        return mav;
    }

    @PostMapping(value = "/editarEmpresa/{id}")
    public String editarEmpresa(@PathVariable(name = "id") Integer id, String nomeFantasia, String razaoSocial, String cnpj, String telefone, String email, String logradouro,
    String numero, String cep, String complemento, String bairro, String cidade, String estado, String pais) {

        Empresa empresa = new Empresa();
        empresa.setNomeFantasia(nomeFantasia.trim().equals("") ? empresa.getNomeFantasia() : nomeFantasia.trim());
        empresa.setRazaoSocial(razaoSocial.trim().equals("") ? empresa.getRazaoSocial() : razaoSocial.trim());
        empresa.setCnpj(cnpj.trim().equals("") ? empresa.getCnpj() : cnpj.trim());
        empresa.setTelefone(telefone.trim().equals("") ? empresa.getTelefone() : telefone.trim());
        empresa.setEmail(email.trim().equals("") ? empresa.getEmail() : email.trim());
        empresa.setLogradouro(logradouro.trim().equals("") ? empresa.getLogradouro() : logradouro.trim());
        empresa.setNumero(numero.trim().equals("") ? empresa.getNumero() : numero.trim());
        empresa.setCep(cep.trim().equals("") ? empresa.getCep() : cep.trim());
        empresa.setComplemento(complemento.trim().equals("") ? empresa.getComplemento() : complemento.trim());
        empresa.setBairro(bairro.trim().equals("") ? empresa.getBairro() : bairro.trim());
        empresa.setCidade(cidade.trim().equals("") ? empresa.getCidade() : cidade.trim());
        empresa.setEstado(estado.trim().equals("") ? empresa.getEstado() : estado.trim());
        empresa.setPais(pais.trim().equals("") ? empresa.getPais() : pais.trim());
        empresaService.save(empresa);

        return "redirect:/perfilEmpresa/" + empresa.getId();
    }


    @PostMapping(value = "/cadastrarUsuAlu/")
    public String cadastrarUsuAlu(String login, String senha) {

        Usuario usuario = new Usuario();
        usuario.setLogin(login);
        usuario.setSenha(senha);
        usuarioService.save(usuario);

        return "redirect:/cadastroAluno2/" + usuario.getId();

    }

    @RequestMapping("/cadastroAluno1")
    public ModelAndView cadastroAluno1(HttpSession session) {
        ModelAndView mav = new ModelAndView("cadastroAluno1");

        return mav;
    }

    @RequestMapping("/cadastroAluno2/{id}")
    public ModelAndView cadastroAluno2(@PathVariable(name = "id") Integer id, HttpSession session) {
        ModelAndView mav = new ModelAndView("cadastroAluno2");

        mav.addObject("idUsuario", id);

        return mav;
    }

    @PostMapping(value = "/cadastrarAluno/")
    public String alunoCadastrar(Integer idUsuario, String nome, String nomeSocial, String dataNasc, String sexo, String rg, String cpf, String ra, String curso, String dataInicio,
    String dataFim, String telefone, String email, String logradouro, String numero, String cep, String complemento, String bairro, String cidade, String estado,
    String pais, String nacionalidade) {

        Aluno aluno = new Aluno();

        Usuario usuario = usuarioService.findById(idUsuario);
        
        aluno.setUsuario(usuario);
        aluno.setNome(nome.trim().equals("") ? aluno.getNome() : nome.trim());
        aluno.setNomeSocial(nomeSocial.trim().equals("") ? aluno.getNomeSocial() : nomeSocial.trim());
        aluno.setDataNasc(dataNasc != null ? Data.fromStringISO(dataNasc).atStartOfDay() : null);
        aluno.setSexo(sexo.trim().equals("") ? aluno.getSexo() : sexo.trim());
        aluno.setRg(rg.trim().equals("") ? aluno.getRg() : rg.trim());
        aluno.setCpf(cpf.trim().equals("") ? aluno.getCpf() : cpf.trim());
        aluno.setRa(ra.trim().equals("") ? aluno.getRa() : ra.trim());
        aluno.setCurso(curso.trim().equals("") ? aluno.getCurso() : curso.trim());
        aluno.setDataInicio(dataInicio != null ? Data.fromStringISO(dataInicio).atStartOfDay() : null);
        aluno.setDataTermino(dataFim != null ? Data.fromStringISO(dataFim).atStartOfDay() : null);
        aluno.setTelefone(telefone.trim().equals("") ? aluno.getTelefone() : telefone.trim());
        aluno.setEmail(email.trim().equals("") ? aluno.getEmail() : email.trim());
        aluno.setLogradouro(logradouro.trim().equals("") ? aluno.getLogradouro() : logradouro.trim());
        aluno.setNumero(numero.trim().equals("") ? aluno.getNumero() : numero.trim());
        aluno.setCep(cep.trim().equals("") ? aluno.getCep() : cep.trim());
        aluno.setComplemento(complemento.trim().equals("") ? aluno.getComplemento() : complemento.trim());
        aluno.setBairro(bairro.trim().equals("") ? aluno.getBairro() : bairro.trim());
        aluno.setCidade(cidade.trim().equals("") ? aluno.getCidade() : cidade.trim());
        aluno.setEstado(estado.trim().equals("") ? aluno.getEstado() : estado.trim());
        aluno.setPais(pais.trim().equals("") ? aluno.getPais() : pais.trim());
        aluno.setNacionalidade(nacionalidade.trim().equals("") ? aluno.getNacionalidade() : nacionalidade.trim());
        alunoService.save(aluno);
        usuario.setAluno(aluno);
        usuarioService.save(usuario);

        return "redirect:/login";
    }

    @RequestMapping("/aluno/editar/{id}")
    public ModelAndView editarAluno(@PathVariable(name = "id") Integer id,
            HttpSession session) {
        ModelAndView mav = new ModelAndView("editarAluno");

        Aluno aluno = alunoService.findById(id);
        mav.addObject("aluno", aluno);

        return mav;
    }

    @PostMapping(value = "/editarAluno/{id}")
    public String alunoEditar(@PathVariable(name = "id") Integer id, String nome, String nomeSocial, String dataNasc, String sexo, String rg, String cpf, String ra, String telefone, String email, String logradouro, String numero, String cep, String complemento, String bairro, String cidade, String estado,
    String pais) {

        Aluno aluno = alunoService.findById(id);
        aluno.setNome(nome.trim().equals("") ? aluno.getNome() : nome.trim());
        aluno.setNomeSocial(nomeSocial.trim().equals("") ? aluno.getNomeSocial() : nomeSocial.trim());
        aluno.setDataNasc(dataNasc != null ? Data.fromStringISO(dataNasc).atStartOfDay() : null);
        aluno.setSexo(sexo.trim().equals("") ? aluno.getSexo() : sexo.trim());
        aluno.setRg(rg.trim().equals("") ? aluno.getRg() : rg.trim());
        aluno.setCpf(cpf.trim().equals("") ? aluno.getCpf() : cpf.trim());
        aluno.setRa(ra.trim().equals("") ? aluno.getRa() : ra.trim());
        aluno.setTelefone(telefone.trim().equals("") ? aluno.getTelefone() : telefone.trim());
        aluno.setEmail(email.trim().equals("") ? aluno.getEmail() : email.trim());
        aluno.setLogradouro(logradouro.trim().equals("") ? aluno.getLogradouro() : logradouro.trim());
        aluno.setNumero(numero.trim().equals("") ? aluno.getNumero() : numero.trim());
        aluno.setCep(cep.trim().equals("") ? aluno.getCep() : cep.trim());
        aluno.setComplemento(complemento.trim().equals("") ? aluno.getComplemento() : complemento.trim());
        aluno.setBairro(bairro.trim().equals("") ? aluno.getBairro() : bairro.trim());
        aluno.setCidade(cidade.trim().equals("") ? aluno.getCidade() : cidade.trim());
        aluno.setEstado(estado.trim().equals("") ? aluno.getEstado() : estado.trim());
        aluno.setPais(pais.trim().equals("") ? aluno.getPais() : pais.trim());
        alunoService.save(aluno);

        return "redirect:/perfilAluno/" + aluno.getId();
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
        experiencia.setDataInicio(dataInicio != null ? Data.fromStringISO(dataInicio).atStartOfDay() : null);
        experiencia.setDataFim(dataFim != null ? Data.fromStringISO(dataFim).atStartOfDay() : null);
        experiencia.setAtual(atual);
        experiencia.setDescricao(descricao.trim().equals("") ? experiencia.getDescricao() : descricao.trim());
        experienciaService.save(experiencia);

        return "redirect:/perfilAluno/" + experiencia.getAluno().getId();
    }

    @RequestMapping("/experiencia/editar/{id}")
    public ModelAndView experienciaEditar(@PathVariable(name = "id") Integer id,
            HttpSession session) {
        ModelAndView mav = new ModelAndView("experienciaEditar");

        Experiencia experiencia = experienciaService.findById(id);
        mav.addObject("experiencia", experiencia);

        return mav;
    }

    @PostMapping(value = "/editarExperiencia/{id}")
    public String experienciaEditar(@PathVariable(name = "id") Integer id, String titulo, String cargo, String nomeEmpresa, String cidadeEmpresa, String estadoEmpresa, String paisEmpresa, String dataInicio, String dataFim, boolean atual, String descricao) {

        Experiencia experiencia = experienciaService.findById(id);
        experiencia.setTitulo(titulo.trim().equals("") ? experiencia.getTitulo() : titulo.trim());
        experiencia.setCargo(cargo.trim().equals("") ? experiencia.getCargo() : cargo.trim());
        experiencia.setNomeEmpresa(nomeEmpresa.trim().equals("") ? experiencia.getNomeEmpresa() : nomeEmpresa.trim());
        experiencia.setCidadeEmpresa(cidadeEmpresa.trim().equals("") ? experiencia.getCidadeEmpresa() : cidadeEmpresa.trim());
        experiencia.setEstadoEmpresa(estadoEmpresa.trim().equals("") ? experiencia.getEstadoEmpresa() : estadoEmpresa.trim());
        experiencia.setPaisEmpresa(paisEmpresa.trim().equals("") ? experiencia.getPaisEmpresa() : paisEmpresa.trim());
        experiencia.setDataInicio(dataInicio != null ? Data.fromStringISO(dataInicio).atStartOfDay() : null);
        experiencia.setDataFim(dataFim != null ? Data.fromStringISO(dataFim).atStartOfDay() : null);
        experiencia.setAtual(atual);
        experiencia.setDescricao(descricao.trim());
        experienciaService.save(experiencia);

        return "redirect:/perfilAluno/" + experiencia.getAluno().getId();
    }

    @GetMapping(value = "/excluirExperiencia/{id}")
    public String experienciaExcluir(@PathVariable(name = "id") Integer id) {
        
        Experiencia experiencia = experienciaService.findById(id);
        experienciaService.delete(experiencia);

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
        formacao.setDataInicio(dataInicio != null ? Data.fromStringISO(dataInicio).atStartOfDay() : null);
        formacao.setDataFim(dataFim != null ? Data.fromStringISO(dataFim).atStartOfDay() : null);
        formacao.setAtual(atual);
        formacao.setDescricao(descricao.trim().equals("") ? formacao.getDescricao() : descricao.trim());
        formacaoService.save(formacao);

        return "redirect:/perfilAluno/" + formacao.getAluno().getId();
    }

    @RequestMapping("/formacao/editar/{id}")
    public ModelAndView formacaoEditar(@PathVariable(name = "id") Integer id, 
            HttpSession session) {
        ModelAndView mav = new ModelAndView("formacaoEditar");

        Formacao formacao = formacaoService.findById(id);
        mav.addObject("formacao", formacao);

        return mav;
    }

    @PostMapping(value = "/editarFormacao/{id}")
    public String formacaoEditar(@PathVariable(name = "id") Integer id, String curso, String diploma, String instituicao, String dataInicio, String dataFim, boolean atual, String descricao) {

        Formacao formacao = formacaoService.findById(id);
        formacao.setCurso(curso.trim().equals("") ? formacao.getCurso() : curso.trim());
        formacao.setDiploma(diploma.trim().equals("") ? formacao.getDiploma() : diploma.trim());
        formacao.setInstituicao(instituicao.trim().equals("") ? formacao.getInstituicao() : instituicao.trim());
        formacao.setDataInicio(dataInicio != null ? Data.fromStringISO(dataInicio).atStartOfDay() : null);
        formacao.setDataFim(dataFim != null ? Data.fromStringISO(dataFim).atStartOfDay() : null);
        formacao.setAtual(atual);
        formacao.setDescricao(descricao.trim());
        formacaoService.save(formacao);

        return "redirect:/perfilAluno/" + formacao.getAluno().getId();
    }

    @GetMapping(value = "/excluirFormacao/{id}")
    public String excluirFormacao(@PathVariable(name = "id") Integer id) {
        
        Formacao formacao = formacaoService.findById(id);
        formacaoService.delete(formacao);

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

    @PostMapping(value = "/cadastrarHabilidade/")
    public String habilidadeCadastrar(Integer idAluno, String habilidade) {

        Aluno aluno = alunoService.findById(idAluno);

        Habilidade hab = new Habilidade();
        hab.setAluno(aluno);
        hab.setHabilidade(habilidade.trim().equals("") ? hab.getHabilidade() : habilidade.trim());
        habilidadeService.save(hab);

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

    @GetMapping(value = "/excluirHabilidade/{id}")
    public String excluirHabilidade(@PathVariable(name = "id") Integer id) {
        
        Habilidade habilidade = habilidadeService.findById(id);
        habilidadeService.delete(habilidade);

        return "redirect:/perfilAluno/" + habilidade.getAluno().getId();
    }

    
    @RequestMapping("/perfilEmpresa/{id}")
    public ModelAndView empresa(@PathVariable(name = "id") Integer id, HttpSession session) {
        ModelAndView mav = new ModelAndView("perfilEmpresa");

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
        vaga.setAtivo(true);
        vagaService.save(vaga);

        return "redirect:/perfilEmpresa/" + vaga.getEmpresa().getId();
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

        return "redirect:/perfilEmpresa/" + vaga.getEmpresa().getId();
    }

    @PostMapping(value = "/excluirVaga/{id}")
    public String vagaExcluir(@PathVariable(name = "id") Integer id) {

        Vaga vaga = vagaService.findById(id);
        vaga.setExclusao(LocalDateTime.now());
        vagaService.save(vaga);

        return "redirect:/perfilEmpresa/" + vaga.getEmpresa().getId();
    }
}
