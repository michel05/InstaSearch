package br.com.ufg.tcc.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.ufg.tcc.constants.Constants;
import br.com.ufg.tcc.model.FiltroPost;
import br.com.ufg.tcc.model.InformacoesUsuario;
import br.com.ufg.tcc.model.Post;
import br.com.ufg.tcc.model.Usuario;

@Repository
public class InstagramService {

	private HttpSession session;
	private static final Logger logger =  Logger.getLogger(InstagramService.class);
	
	@Autowired
	public InstagramService(HttpSession session) {
		this.session = session;
	}
	
	private JSONObject busqueObjetoJson(JSONObject json, String nome) {
		
		JSONObject jsonObj = null;
		
		try {
			
			jsonObj = json.getJSONObject(nome);
			
		} catch (JSONException e) {
			logger.error(e.getMessage());
			
		} catch (Exception e) {
			logger.error(e.getMessage());
//			session.setAttribute("error", "Um erro ocorreu, tente novamente");
		}
		
		return jsonObj;
	}
	
	private JSONArray busqueArrayObjetoJson(JSONObject json, String nome) throws Exception {
		
		JSONArray jsonObj = null;
		
		try {
			
			jsonObj = json.getJSONArray(nome);
			
		} catch (JSONException e) {
			logger.error(e.getMessage());
			
		} catch (Exception e) {
			throw new Exception();
		}
		
		return jsonObj;
	}
	
	private JSONObject busqueObjetoJsonDeUmArray(JSONArray json, int indice) throws Exception {
	
		JSONObject jsonObj = null;
		
		try {
			
			jsonObj = json.getJSONObject(indice);
			
		} catch (JSONException e) {
			logger.error(e.getMessage());
			
		} catch (Exception e) {
			throw new Exception();
		}
		
		return jsonObj;
	}

	public InformacoesUsuario busqueInformacoesUsuario(FiltroPost filtro) {
		
		InformacoesUsuario infoUser = new InformacoesUsuario();
		
		try {
			
			filtro.setNumPostsFaltantes(filtro.getNumPosts());
			
			String url = "https://api.instagram.com/v1/users/" + filtro.getIdUsuario() + "/media/recent?access_token=" + Constants.TOKEN + filtro.monteSufixoUrl();
			
			logger.info("URL: " + url);
			
			String response = getResponse(url);
			JSONObject jsonResposta = new JSONObject(response.toString());
			
			preencheListaDePosts(infoUser, jsonResposta);
			
			filtro.setNumPostsFaltantes(filtro.getNumPosts() - infoUser.getListaPosts().size());
			
			while (filtro.getNumPostsFaltantes() > 0) {

				JSONObject paginacao = busqueObjetoJson(jsonResposta, "pagination");
				if (!paginacao.isNull("next_max_id")) {
					String next_max_id = paginacao.getString("next_max_id");
					
					url = "https://api.instagram.com/v1/users/" + filtro.getIdUsuario() + "/media/recent?access_token=" + Constants.TOKEN + filtro.monteSufixoUrl() + "&max_id=" + next_max_id;
					logger.info("URL: " + url);
					response = getResponse(url);
					jsonResposta = new JSONObject(response.toString());
					preencheListaDePosts(infoUser, jsonResposta);
					filtro.setNumPostsFaltantes(filtro.getNumPosts() - infoUser.getListaPosts().size());
				
				} else {
					break;
				}
				
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			session.setAttribute("error", "Um erro ocorreu, tente novamente");
		}
		
		return infoUser;
	}
	
	
	
	private void preencheListaDePosts(InformacoesUsuario infoUser, JSONObject jsonResposta) throws Exception {
		
		JSONArray medias = busqueArrayObjetoJson(jsonResposta, "data");
		
		String nome = "";
		
//		System.out.println(" Medias: " + medias.length());
		
		for (int i=0; i < medias.length(); i++) {
			
			Post post = new Post();
			JSONObject postagem =  busqueObjetoJsonDeUmArray(medias, i);
			JSONObject likes = busqueObjetoJson(postagem, "likes");
			JSONObject comments = busqueObjetoJson(postagem,"comments");
			JSONArray listaComentarios = busqueArrayObjetoJson(comments, "data");
			
			int marcacoes = 0;
			if(comments.getInt("count") > listaComentarios.length()) {
				String respListaComentarios = getResponse("https://api.instagram.com/v1/media/" + postagem.getString("id") + "/comments?access_token=" + Constants.TOKEN);
				JSONObject jsonComentarios = new JSONObject(respListaComentarios.toString());
				listaComentarios = busqueArrayObjetoJson(jsonComentarios, "data");
			}
			
			for (int j=0; j < listaComentarios.length(); j++) {
				JSONObject comment = busqueObjetoJsonDeUmArray(listaComentarios, j);
				String comentario= comment.getString("text");
				
				marcacoes += StringUtils.countMatches(comentario, "@");
				
				if (comentario.contains("@")) {
					
					List<String> palavras = new ArrayList<String>(Arrays.asList( comentario.split(" ")));
					
					palavras.forEach(x -> {
						if (x.contains("@"))  post.getListaNomesMencao().add(x.replace("@", ""));
					});

				}
				
			}
			
			String respLikesPost = getResponse("https://api.instagram.com/v1/media/" + postagem.getString("id") + "/likes?access_token=" + Constants.TOKEN);
			JSONObject jsonLikesPost = new JSONObject(respLikesPost.toString());
			JSONArray listaLikesPost = busqueArrayObjetoJson(jsonLikesPost, "data");
			
			
			listaLikesPost.forEach(like -> {
				if(post.getListaNomesMencao().contains(((JSONObject) like).getString("username"))){
					post.adicioneLikeDeMencao();
				}
			});
			
			JSONObject cabecalho = busqueObjetoJson(postagem, "caption");
			
			if (cabecalho != null) {
				post.setDescricao(cabecalho.getString("text").substring(0,
						(cabecalho.getString("text").length() > Constants.MAX_CARACTERES_TITULO)
								? Constants.MAX_CARACTERES_TITULO : cabecalho.getString("text").length()));
				post.setNumCaracteres(cabecalho.getString("text").length());
				post.setNumHashtags(StringUtils.countMatches(cabecalho.getString("text"), "#"));
				
			}
			
			post.setId(postagem.getString("id"));
			post.setNumCurtidas(likes.getInt("count"));
			post.setNumMarcacoes(marcacoes);
			post.setNumComentarios(comments.getInt("count"));
			long ano = 0;
			ano = Long.parseLong(postagem.getString("created_time")) * 1000;
			post.setData(new Date(ano));
			
			
			post.setLink(postagem.getString("link"));
			
			infoUser.adicioneListaPosts(post);
			
			if (i == 0){
				nome = busqueObjetoJson(postagem, "user").getString("username");
				post.setNome(nome);
				infoUser.setNome(nome);
				infoUser.setLocalArquivo(Constants.CONTEXTO + infoUser.getNome() + ".xls");
			}
		}
		
	}

	private String getResponse(String url) throws Exception {
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "");
		con.getResponseCode();

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		
		in.close();
		
		return response.toString();
	}

	public List<Usuario> busqueUsuarios(String usuario) {
		
		List<Usuario> usuarios = new ArrayList<Usuario>();
		
		try {
			
			String url = "https://api.instagram.com/v1/users/search?q=" + usuario + "&access_token=" + Constants.TOKEN;
			String response = getResponse(url);
			
			JSONObject usersJson = new JSONObject(response.toString());
			JSONArray users = busqueArrayObjetoJson(usersJson, "data");
			
			
			for (int i=0; i < users.length() && i < Constants.MAX_USUARIOS_BUSCADOS; i++) {
				
				Usuario usr = new Usuario();
				JSONObject u = busqueObjetoJsonDeUmArray(users, i);
				
				usr.setNome(u.getString("username"));
				usr.setImagem(u.getString("profile_picture"));
				usr.setId(u.getString("id"));
				usr.setFullName(u.optString("full_name","-"));
				
				usuarios.add(usr);
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			session.setAttribute("error", "Um erro ocorreu, tente novamente");
		}
		
		return usuarios;
		
	}
}
