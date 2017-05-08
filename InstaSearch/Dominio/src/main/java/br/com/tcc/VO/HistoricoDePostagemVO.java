package br.com.tcc.VO;

import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.tcc.Interfaces.IDominioPersistente;

@XmlRootElement(name = "historicoPostagem")
@Entity
@Table(name="historico_postagem")
public class HistoricoDePostagemVO implements IDominioPersistente<String>{
	
	private static final long serialVersionUID = -2319495054564625636L;

	@Id
	@Basic(optional = false)
    @Column(name = "id",unique=true, nullable = false)
	private String id;
	
	@Column(name = "data_inicial")
	private Calendar dataInicial;
	
	@Column(name = "data_final")
	private Calendar dataFinal;
	
	@Column(name = "num_curtidas")
	private int numCurtidas;
	
	@Column(name = "num_comentarios")
	private int numComentarios;
	
	@Column(name = "num_curtidas_parcial")
	private int numCurtidasParcial;
	
	@Column(name = "num_comentarios_parcial")
	private int numComentariosParcial;
	
	@ManyToOne
	@JoinColumns({
		  @JoinColumn(name = "idPostagem", insertable = false, updatable = false),
		  @JoinColumn(name = "idHistoricoPostagem", insertable = false, updatable = false)
		})
	private PostagemVO postagem;

	public HistoricoDePostagemVO() {
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Calendar getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Calendar dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Calendar getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Calendar dataFinal) {
		this.dataFinal = dataFinal;
	}

	public int getNumCurtidas() {
		return numCurtidas;
	}

	public void setNumCurtidas(int numCurtidas) {
		this.numCurtidas = numCurtidas;
	}

	public int getNumComentarios() {
		return numComentarios;
	}

	public void setNumComentarios(int numComentarios) {
		this.numComentarios = numComentarios;
	}

	public PostagemVO getPostagem() {
		return postagem;
	}

	public void setPostagem(PostagemVO postagem) {
		this.postagem = postagem;
	}

	public int getNumComentariosParcial() {
		return numComentariosParcial;
	}

	public void setNumComentariosParcial(int numComentariosParcial) {
		this.numComentariosParcial = numComentariosParcial;
	}

	public int getNumCurtidasParcial() {
		return numCurtidasParcial;
	}

	public void setNumCurtidasParcial(int numCurtidasParcial) {
		this.numCurtidasParcial = numCurtidasParcial;
	}
}
