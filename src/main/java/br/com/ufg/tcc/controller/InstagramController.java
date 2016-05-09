package br.com.ufg.tcc.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.ufg.tcc.model.Usuario;
import br.com.ufg.tcc.service.InstagramService;

@Controller
public class InstagramController {

	private InstagramService instagramServ;
	private HttpSession session;
	
	@Autowired
	public InstagramController(InstagramService instagramServ, HttpSession session) {
		this.instagramServ = instagramServ;
		this.session = session;
	}
	
	@RequestMapping("/")
	public String home() {
		session.removeAttribute("sucesso");
		session.removeAttribute("error");
		
		return "home";
	}
	
	@RequestMapping("/home")
	public String home(Model model) {
		return "home";
	}
	
	@RequestMapping("/info")
	public String busqueInformacoes(@RequestParam String id, Model model) throws Exception {
		instagramServ.busqueInformacoesUsuario(id);
		
		return "redirect:home";
	}
	
	@RequestMapping("/buscarUsuario")
	public String busqueUsuarios(@RequestParam String usuario, Model model) throws Exception {
		List<Usuario> usuariosLista = instagramServ.busqueUsuarios(usuario);
		model.addAttribute("usuariosLista", usuariosLista);
		
		return "home";
	}
	
	
}

