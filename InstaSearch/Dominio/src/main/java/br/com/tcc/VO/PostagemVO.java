package br.com.tcc.VO;

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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.tcc.Enums.StatusEnum;
import br.com.tcc.Interfaces.IDominioPersistente;

@XmlRootElement(name = "postagem")
@Entity
@Table(name = "postagem")
public class PostagemVO implements IDominioPersistente<Integer> {

	private static final long serialVersionUID = 5003228171033508600L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Id
	@Column(name = "id_instagram")
	private String idInstagram;

	@Column(length = 10485760)
	private String descricao;

	@Column(name = "num_total_curtidas")
	private int numTotalCurtidas;

	@Column(name = "num_total_comentarios")
	private int numTotalComentarios;

	@Column(length = 10485760, name = "ur_limagem")
	private String urlImagem;

	@Enumerated(EnumType.STRING)
	private StatusEnum status = StatusEnum.ATIVO;
	private String link;

	@Column(name = "data_criacao")
	private Calendar dataCriacao;

	@Column(name = "data_inicio_monitoramento")
	private Calendar dataInicioMonitoramento;

	@Column(name = "perio_dodia")
	private String periodoDoDia;

	@ManyToOne
	@JoinColumn(name = "id_monitoramento")
	private MonitoramentoVO monitoramento;

	@OneToMany(mappedBy = "postagem", cascade = { CascadeType.ALL })
	private List<HistoricoDePostagemVO> historico;

	@OneToMany(mappedBy = "postagem", cascade = { CascadeType.ALL })
	private List<LabelAnaliseImageVO> listaAnaliseImageVO;

	@OneToOne(mappedBy = "postagem", cascade = { CascadeType.ALL })
	private OCRAnaliseImageVO ocrAnaliseImageVO;

	public PostagemVO() {
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

	public MonitoramentoVO getMonitoramento() {
		return monitoramento;
	}

	public void setMonitoramento(MonitoramentoVO monitoramento) {
		this.monitoramento = monitoramento;
	}

	public List<HistoricoDePostagemVO> getHistorico() {
		return historico;
	}

	public void setHistorico(List<HistoricoDePostagemVO> historico) {
		this.historico = historico;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public String getPeriodoDoDia() {
		return periodoDoDia;
	}

	public void setPeriodoDoDia(String periodoDoDia) {
		this.periodoDoDia = periodoDoDia;
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

	public List<LabelAnaliseImageVO> getListaAnaliseImageVO() {
		return listaAnaliseImageVO;
	}

	public void setListaAnaliseImageVO(List<LabelAnaliseImageVO> listaAnaliseImageVO) {
		this.listaAnaliseImageVO = listaAnaliseImageVO;
	}

	public OCRAnaliseImageVO getOcrAnaliseImageVO() {
		return ocrAnaliseImageVO;
	}

	public void setOcrAnaliseImageVO(OCRAnaliseImageVO ocrAnaliseImageVO) {
		this.ocrAnaliseImageVO = ocrAnaliseImageVO;
	}

	public String getIdInstagram() {
		return idInstagram;
	}

	public void setIdInstagram(String idInstagram) {
		this.idInstagram = idInstagram;
	}

}
