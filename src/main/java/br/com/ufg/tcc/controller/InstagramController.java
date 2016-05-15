package br.com.ufg.tcc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.ufg.tcc.constants.Constants;
import br.com.ufg.tcc.model.InformacoesUsuario;
import br.com.ufg.tcc.model.Usuario;
import br.com.ufg.tcc.service.InstagramService;
import br.com.ufg.tcc.utils.ExcelUtil;

@Controller
public class InstagramController {

	private InstagramService instagramServ;
	private HttpSession session;
	private HttpServletResponse response;
	
	@Autowired
	public InstagramController(InstagramService instagramServ, HttpSession session, HttpServletResponse response) {
		this.instagramServ = instagramServ;
		this.session = session;
		this.response = response;
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
		InformacoesUsuario infoUser = instagramServ.busqueInformacoesUsuario(id);
		ExcelUtil.gerarExcelInstagram(infoUser);
		
		download(new File(Constants.LOCAL_ARQUIVO + infoUser.getNome() + ".xls"));
		
		try {
			
    		InputStream is = new FileInputStream(Constants.LOCAL_ARQUIVO + infoUser.getNome() + ".xls");
    		
    		response.setContentType("APPLICATION/OCTET-STREAM");
    		response.setHeader(
    				"Content-Disposition", 
    				"attachment;filename=" + URLEncoder.encode(infoUser.getNome() + ".xls", 
					"UTF-8"));
    		IOUtils.copy(is, response.getOutputStream());
    		response.getOutputStream().flush();
	    	
	    } catch (Exception e){
	        e.printStackTrace();
	    }
		session.setAttribute("sucesso", infoUser.getNome() + ".xls");
		
		return home(model);
	}
	
	@RequestMapping(method = RequestMethod.GET)
    public HttpEntity<byte[]> download(File arquivo) {
        byte[] zipBytes = ExcelUtil.fileToArrayByte(arquivo);
	    HttpHeaders httpHeaders = new HttpHeaders();
	    httpHeaders.add("Content-Disposition", "attachment; filename=\"");
	    HttpEntity<byte[]> entity = new HttpEntity<byte[]>(zipBytes,httpHeaders);
	    return entity;
    }
	
	@RequestMapping("/buscarUsuario")
	public String busqueUsuarios(@RequestParam String usuario, Model model) throws Exception {
		List<Usuario> usuariosLista = instagramServ.busqueUsuarios(usuario);
		model.addAttribute("usuariosLista", usuariosLista);

		return "home";
	}
	
	
}

