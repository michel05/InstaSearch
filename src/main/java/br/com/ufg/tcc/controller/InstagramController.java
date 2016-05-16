package br.com.ufg.tcc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.ufg.tcc.constants.Constants;
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
	
	@RequestMapping(value = "/info")
	public @ResponseBody String busqueInformacoes(@RequestParam String idUsuario,
													@RequestParam String dataInicio,
													@RequestParam int numPosts,
													Model model) throws Exception {
		
		FiltroPost filtroPost = new FiltroPost(idUsuario, numPosts, dataInicio);
		
		InformacoesUsuario infoUser = instagramServ.busqueInformacoesUsuario(filtroPost);
		
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

