package br.com.tcc.VO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.tcc.Enums.StatusEnum;
import br.com.tcc.Interfaces.IDominioPersistente;

@SuppressWarnings("restriction")
@XmlRootElement(name = "monitoramento")
public class MonitoramentoVO implements IDominioPersistente, Comparable<String>{

	private String id;
	private String titulo;
	private long dataDeInicio;
	private long dataDeTermino;
	private StatusEnum status;
	private List<PostagemVO> postagens;

	public MonitoramentoVO(String titulo) {
//		id = String.valueOf(GeradorDeCodigoAutomatico.getProximoId());
		this.titulo = titulo == null || titulo.isEmpty() ? "Monitoramento " + this.id : titulo;
		setDataDeInicio(Calendar.getInstance().getTimeInMillis());
		postagens = new ArrayList<PostagemVO>();
		setStatus(StatusEnum.ATIVO);
	}
	
	public List<PostagemVO> getPostagens() {
		return postagens;
	}

	public void setPostagens(List<PostagemVO> postagens) {
		this.postagens = postagens;
	}

	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public int compareTo(String arg0) {
		return this.getId().equals(arg0) ? 0 : -1;
	}

	public long getDataDeInicio() {
		return dataDeInicio;
	}

	public void setDataDeInicio(long dataDeInicio) {
		this.dataDeInicio = dataDeInicio;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public long getDataDeTermino() {
		return dataDeTermino;
	}

	public void setDataDeTermino(long dataDeTermino) {
		this.dataDeTermino = dataDeTermino;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}
	
	
}
