package br.fatec.b3comandas.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.fatec.b3comandas.filtro.UsuarioFiltro;
import br.fatec.b3comandas.filtro.VagaFiltro;
import br.fatec.b3comandas.model.Usuario;
import br.fatec.b3comandas.model.Vaga;
import br.fatec.b3comandas.service.UsuarioService;

import br.fatec.b3comandas.service.VagaService;

//@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class SiteController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private VagaService vagaService;

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

}
