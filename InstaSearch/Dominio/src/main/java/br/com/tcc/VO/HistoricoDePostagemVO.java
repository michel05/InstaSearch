package br.com.tcc.VO;

import java.util.Calendar;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.tcc.Interfaces.IDominioPersistente;

@XmlRootElement(name = "historicoPostagem")
public class HistoricoDePostagemVO implements IDominioPersistente{
	
	private String id;
	private Calendar dataInicial;
	private Calendar dataFinal;
	private int numCurtidas;
	private int numComentarios;
	private int numCurtidasParcial;
	private int numComentariosParcial;
	
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
