package br.com.tcc.Dominio;

import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONObject;

import br.com.tcc.Constantes.Constantes;
import br.com.tcc.Enums.StatusEnum;
import br.com.tcc.Utils.RequisicaoUtil;
import br.com.tcc.VO.MonitoramentoVO;
import br.com.tcc.VO.PostagemVO;

public class Monitoramento {

	private MonitoramentoVO vo;
	private int contadorPostsAtivos = 0;
	
	public Monitoramento(String titulo) {
		vo = new MonitoramentoVO(titulo);
		vo.setPostagens(new ArrayList<PostagemVO>());
	}
	
	public MonitoramentoVO realizaMonitoramento() {
		try {
			String url = "https://api.instagram.com/v1/users/" 
					+ Constantes.CODIGO_GOIANIAINDICA 
					+ "/media/recent?access_token=" 
					+ Constantes.TOKEN 
					+ "&count=" + Constantes.NUM_POSTS;
	
			String response = RequisicaoUtil.getResponse(url);
			JSONObject jsonResposta = new JSONObject(response.toString());
			
			JSONArray medias = RequisicaoUtil.busqueArrayObjetoJson(jsonResposta, "data");
			
			for (int i=0; i < medias.length(); i++) {

				PostagemVO post = new PostagemVO();
				
				JSONObject postagem =  RequisicaoUtil.busqueObjetoJsonDeUmArray(medias, i);
				post.setId(postagem.getString("id"));
				
				if(!vo.getPostagens().contains(post)) {
					vo.getPostagens().add(post);
					post.setDataInicioMonitoramento(Calendar.getInstance());
					verifiqueQuantidadePostsAtivos();
				} else {
					post = busquePostPorId(post.getId());
				}
				
				Postagem dPostagem = new Postagem();
				dPostagem.setVo(post);
				dPostagem.populePostagem(postagem);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return vo;
	}

	private PostagemVO busquePostPorId(String id) {
		
		for (PostagemVO post : vo.getPostagens()) {
			if(post.getId().equals(id)) {
				return post;
			}
		}
		return null;
	}

	// Verifica se a quantidade de posts ultrapassa o máximo permitido, caso 
	// ultrapassar é Inativado o post mais velho
	private void verifiqueQuantidadePostsAtivos() {
		contadorPostsAtivos = 0;
		
		for (PostagemVO post : vo.getPostagens()) {
			if (post.getStatus() == StatusEnum.ATIVO)
				adicioneContadorPostsAtivo();
		}
		
		int indicePost = 0;
		while(contadorPostsAtivos > Constantes.NUM_POSTS) {
			inativePostagem(indicePost);
			indicePost++;
			contadorPostsAtivos--;
		}
	}
	
	private void inativePostagem(int indice) {
		vo.getPostagens().get(indice).setStatus(StatusEnum.INATIVO);
	}
	
	private void adicioneContadorPostsAtivo() {
		contadorPostsAtivos++;
	}
	
	public MonitoramentoVO getVO() {
		return vo;
	}
	
}
