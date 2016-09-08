package br.com.tcc.VO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.tcc.Dominio.Monitoramento;
import br.com.tcc.Enums.StatusEnum;
import br.com.tcc.Interfaces.IDominioPersistente;

@XmlRootElement(name = "postagem")
public class PostagemVO implements IDominioPersistente {

	private String id;
	private String descricao;
	private int numTotalCurtidas;
	private int numTotalComentarios;
	private String urlImagem;
	private StatusEnum status;
	private String link;
	private Calendar dataCriacao;
	private Calendar dataInicioMonitoramento;
	
	private Monitoramento monitoramento;
	private List<HistoricoDePostagemVO> historico;
	
	public PostagemVO() {
		historico = new ArrayList<HistoricoDePostagemVO>();
		status = StatusEnum.ATIVO;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getNumTotalCurtidas() {
		return numTotalCurtidas;
	}

	public void setNumTotalCurtidas(int numTotalCurtidas) {
		this.numTotalCurtidas = numTotalCurtidas;
	}

	public int getNumTotalComentarios() {
		return numTotalComentarios;
	}

	public void setNumTotalComentarios(int numTotalComentarios) {
		this.numTotalComentarios = numTotalComentarios;
	}

	public Monitoramento getMonitoramento() {
		return monitoramento;
	}

	public void setMonitoramento(Monitoramento monitoramento) {
		this.monitoramento = monitoramento;
	}

	public List<HistoricoDePostagemVO> getHistorico() {
		return historico;
	}

	public void setHistorico(List<HistoricoDePostagemVO> historico) {
		this.historico = historico;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		return this.getId().equals(((IDominioPersistente) obj).getId());
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public String getUrlImagem() {
		return urlImagem;
	}

	public void setUrlImagem(String urlImagem) {
		this.urlImagem = urlImagem;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Calendar getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Calendar dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Calendar getDataInicioMonitoramento() {
		return dataInicioMonitoramento;
	}

	public void setDataInicioMonitoramento(Calendar dataInicioMonitoramento) {
		this.dataInicioMonitoramento = dataInicioMonitoramento;
	}
}
