package br.com.tcc.VO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.tcc.Enums.StatusEnum;
import br.com.tcc.Interfaces.IDominioPersistente;
import br.com.tcc.Utils.GeradorDeCodigoAutomatico;

@XmlRootElement(name = "monitoramento")
@Entity
@Table(name="monitoramento")
public class MonitoramentoVO implements IDominioPersistente<Integer>, Comparable<Integer>{
	
	private static final long serialVersionUID = 5802080080211470068L;
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	private String titulo;
	
	@Column(name = "data_inicio")
	private long dataDeInicio;
	
	@Column(name = "data_termino")
	private long dataDeTermino;

	@Enumerated(EnumType.STRING)
	private StatusEnum status;
	
	@OneToMany(mappedBy="monitoramento", cascade = {CascadeType.MERGE}, orphanRemoval=true)
	private List<PostagemVO> postagens;

	public MonitoramentoVO() { }
	
	public MonitoramentoVO(String titulo) {
//		id = GeradorDeCodigoAutomatico.getProximoId();
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

	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public int compareTo(Integer arg0) {
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
