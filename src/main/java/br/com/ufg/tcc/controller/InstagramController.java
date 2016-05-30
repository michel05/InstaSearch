package br.com.ufg.tcc.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.ufg.tcc.model.FiltroPost;
import br.com.ufg.tcc.model.InformacoesUsuario;
import br.com.ufg.tcc.model.Usuario;
import br.com.ufg.tcc.service.InstagramService;
import br.com.ufg.tcc.utils.ExcelUtil;

@Controller
public class InstagramController {

	private InstagramService instagramServ;
	private HttpSession session;
	private HttpServletResponse response;
	protected String urlArquivo;
	protected String arquivoNome = "";
	private static final Logger logger =  Logger.getLogger(InstagramController.class);
	
	
	@Autowired
	public InstagramController(InstagramService instagramServ, HttpSession session, HttpServletResponse response) {
		this.instagramServ = instagramServ;
		this.session = session;
		this.response = response;
	}
	
	@RequestMapping("/")
	public String home() {
		return "home";
	}
	
	@RequestMapping("/home")
	public String home(Model model) {
		return "home";
	}
	
	@RequestMapping(value = "/info",  method = RequestMethod.POST)
	public @ResponseBody String busqueInformacoes(@RequestBody FiltroPost filtroPost, Model model) throws Exception {
		
		InformacoesUsuario infoUser = instagramServ.busqueInformacoesUsuario(filtroPost);
		
		if (infoUser.getListaPosts().isEmpty()) {
			return "";
		}
		
		try {
			
			urlArquivo = ExcelUtil.gerarExcelInstagram(infoUser);
		} catch (Exception e) {
			model.addAttribute("error", "Houve um erro ao gerar o arquivo");
			e.getMessage();
		}
		
	    return infoUser.getLocalArquivo();
		
	}
	
	@RequestMapping("/buscarUsuario")
	public String busqueUsuarios(@RequestParam String usuario, Model model) throws Exception {
		List<Usuario> usuariosLista = instagramServ.busqueUsuarios(usuario);
		model.addAttribute("usuariosLista", usuariosLista);

		return "home";
	}
	
	
}

