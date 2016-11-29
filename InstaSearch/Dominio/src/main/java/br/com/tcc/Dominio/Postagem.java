package br.com.tcc.Dominio;

import java.util.Calendar;
import java.util.Date;

import org.json.JSONObject;

import br.com.tcc.Enums.StatusEnum;
import br.com.tcc.Utils.RequisicaoUtil;
import br.com.tcc.VO.HistoricoDePostagemVO;
import br.com.tcc.VO.PostagemVO;

public class Postagem{

	private PostagemVO vo;
	
	public Postagem() {
		vo = new PostagemVO();
	}

	public PostagemVO getVo() {
		return vo;
	}

	public void setVo(PostagemVO vo) {
		this.vo = vo;
	}

	public void populePostagem(JSONObject postagem) {
		
		verifiqueTempoDeVidaDaPostagem(vo);
		
		if(vo.getStatus() == StatusEnum.ATIVO) {
			vo.setDescricao(RequisicaoUtil.busqueObjetoJson(postagem, "caption").getString("text"));
			vo.setUrlImagem(RequisicaoUtil.busqueObjetoJson(postagem, "images").getJSONObject("standard_resolution").getString("url"));
			vo.setLink(postagem.getString("link"));
			
			HistoricoDePostagemVO histPostagem = new  HistoricoDePostagemVO();
			histPostagem.setDataInicial(Calendar.getInstance());
			histPostagem.setNumComentarios(RequisicaoUtil.busqueObjetoJson(postagem,"comments").getInt("count"));
			histPostagem.setNumCurtidas(RequisicaoUtil.busqueObjetoJson(postagem, "likes").getInt("count"));
			
			if(!vo.getHistorico().isEmpty()) {
				HistoricoDePostagemVO ultimoHistorico = vo.getHistorico().get(vo.getHistorico().size() - 1);
				histPostagem.setNumCurtidasParcial(histPostagem.getNumCurtidas() - ultimoHistorico.getNumCurtidas());
				histPostagem.setNumComentariosParcial(histPostagem.getNumComentarios() - ultimoHistorico.getNumComentarios());
			}
			
			vo.getHistorico().add(histPostagem);
			
			vo.setNumTotalCurtidas(histPostagem.getNumCurtidas());
			vo.setNumTotalComentarios(histPostagem.getNumComentarios());
			Calendar c = Calendar.getInstance();
			c.setTime(new Date(Long.parseLong(postagem.getString("created_time")) * 1000));
			vo.setDataCriacao(c);
		}
	}

	//Verifica se passou tres horas desde a criacao do post
	private void verifiqueTempoDeVidaDaPostagem(PostagemVO vo) {
		if (((new Date()).getTime() - vo.getDataInicioMonitoramento().getTime().getTime()) >=  (3600000 * 3)) {
			vo.setStatus(StatusEnum.INATIVO);
		}
	}
	
}