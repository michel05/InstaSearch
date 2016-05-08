package br.com.ufg.tcc.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jinstagram.Instagram;
import org.jinstagram.auth.InstagramAuthService;
import org.jinstagram.auth.model.Token;
import org.jinstagram.auth.model.Verifier;
import org.jinstagram.auth.oauth.InstagramService;
import org.jinstagram.exceptions.InstagramException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.ufg.tcc.constants.Constants;
import br.com.ufg.tcc.model.Post;
import br.com.ufg.tcc.model.Usuario;
import br.com.ufg.tcc.utils.Utils;

@Controller
public class AppController {

	InstagramService service;
	Instagram instagram;
	
	public final static int MAX_CARACTERES_TITULO = 100;
	
	@RequestMapping(value = "/t")
	public String telaInicial(Model model){
		
		String clientId = Constants.CLIENT_ID;
        String clientSecret = Constants.CLIENT_SECRET;
        String callbackUrl = Constants.REDIRECT_URI;


        service = new InstagramAuthService()
		                .apiKey(clientId)
		                .apiSecret(clientSecret)
		                .callback(callbackUrl)
		                .build();
        
        String authorizationUrl = service.getAuthorizationUrl();
		model.addAttribute("url", authorizationUrl);
		
		return "login";
	}
	
	@RequestMapping("/validaToken")
	public String validaToken(@RequestParam String code, Model model) throws InstagramException {
		System.out.println("Validando Token...");
		
        Verifier verifier = new Verifier(code);

        Token accessToken = service.getAccessToken(verifier);
        instagram = new Instagram(accessToken);
		
        
        System.out.println("Token: " + instagram.getAccessToken().getToken());
		return "redirect:home";
	}
	
	@RequestMapping("/")
	public String home(Model model) {
		return "home";
	}
	
	
	@RequestMapping("/buscarInformacoes")
	public String buscarInformacoes(@RequestParam String id, Model model) throws InstagramException, Exception {
		
		
		try {
			
			String url = "https://api.instagram.com/v1/users/" + id + "/media/recent?count=5&access_token=" + Constants.TOKEN;
			String response = getResponse(url);
			
			JSONObject jObj = new JSONObject(response.toString());
			JSONArray medias = jObj.getJSONArray("data");
			List<Post> lista = new ArrayList<Post>();
			
			String nome = "";
			
			System.out.println(" Medias: " + medias.length());
			
			for (int i=0; i < medias.length() && i < Constants.TOTAL_POSTS; i++) {
				
				Post post = new Post();
				JSONObject u = medias.getJSONObject(i);
				JSONObject likes = u.getJSONObject("likes");
				JSONObject comments = u.getJSONObject("comments");
				JSONArray listaComentarios = comments.getJSONArray("data");
				
				int marcacoes = 0;
				if(comments.getInt("count") > listaComentarios.length()) {
					String respListaComentarios = getResponse("https://api.instagram.com/v1/media/" + u.getString("id") + "/comments?access_token=" + Constants.TOKEN);
					JSONObject jsonComentarios = new JSONObject(respListaComentarios.toString());
					listaComentarios = jsonComentarios.getJSONArray("data");
				}
				
				for (int j=0; j < listaComentarios.length(); j++) {
					JSONObject comment = listaComentarios.getJSONObject(j);
					marcacoes += StringUtils.countMatches(comment.getString("text"), "@");
				}
				
				JSONObject cabecalho = u.getJSONObject("caption");
				
				post.setId(u.getString("id"));
				post.setDescricao(cabecalho.getString("text")
						.substring(0, (cabecalho.getString("text").length() > MAX_CARACTERES_TITULO) ? MAX_CARACTERES_TITULO : cabecalho.getString("text").length()));
				post.setNumCurtidas(likes.getInt("count"));
				post.setNumMarcacoes(marcacoes);
				post.setNumComentarios(comments.getInt("count"));
				long ano = 0;
				ano = Long.parseLong(u.getString("created_time")) * 1000;
				post.setData(new Date(ano));
				post.setNumCaracteres(cabecalho.getString("text").length());
				post.setNumHashtags(StringUtils.countMatches(cabecalho.getString("text"), "#"));
				post.setLink(u.getString("link"));
				
				lista.add(post);
				
				if (i == 0){
					JSONObject from = cabecalho.getJSONObject("from");
					nome = from.getString("username");
				}
//				System.out.println("\n\n" + post.toString());
			}
			
			System.out.println("Lista: " + lista.size());
			
			Utils.gerarExcelInstagram(lista, nome);
			
			model.addAttribute("sucesso", "posts_" + nome + ".xls");
			
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
			model.addAttribute("error", "Um erro ocorreu, tente novamente");
			
			return "home";
		}
		
		return "home";
	}
	
	
	@RequestMapping("/buscarUsuario")
	public String buscarUsuario(@RequestParam String usuario, Model model) throws InstagramException, Exception {
		
		try {
			String url = "https://api.instagram.com/v1/users/search?q=" + usuario + "&access_token=" + Constants.TOKEN;
			
			String response = getResponse(url);
			
	//		System.out.println(response.toString());
			
			JSONObject usersJson = new JSONObject(response.toString());
			
			JSONArray users = usersJson.getJSONArray("data");
			
			List<Usuario> usuarios = new ArrayList<Usuario>();
			
			for (int i=0; i < users.length() && i <= 8; i++) {
				
				Usuario usr = new Usuario();
				
				JSONObject u = users.getJSONObject(i);
				
				usr.setNome(u.getString("username"));
				usr.setImagem(u.getString("profile_picture"));
				usr.setId(u.getString("id"));
				usr.setFullName(u.optString("full_name","-"));
				
//				System.out.println("nome: " + u.getString("username"));
//				System.out.println("imagem: " + u.getString("profile_picture"));
//				System.out.println("id: " + u.getString("id"));
//				System.out.println("full_name: " + u.optString("full_name","-"));
//				System.out.println();
				
				usuarios.add(usr);
			}
			
			model.addAttribute("msg", usuario);
			
			model.addAttribute("lista", usuarios);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.addAttribute("error", "Um erro ocorreu, tente novamente");
			
			return "home";
		}
		
		return "home";
	}
	
	
	private String getResponse(String url) throws Exception {
		
//		System.out.println(url);
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", "");

		int responseCode = con.getResponseCode();
//		System.out.println("\nSending 'GET' request to URL : " + url);
//		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		return response.toString();
	}
	
}

