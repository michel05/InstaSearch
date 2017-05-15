package br.com.tcc.VO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.tcc.Interfaces.IDominioPersistente;

//@Entity
@Table(name = "analise_imagem_ocr")
public class OCRAnaliseImageVO implements IDominioPersistente<Integer>{

	private static final long serialVersionUID = -2308463341095814800L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@OneToOne
	private PostagemVO postagem;

	@Column(columnDefinition = "TEXT")
	private String mensagem;

	@Column(name = "qtde_caracteres")
	private int quantidadeDeCaracteres;

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PostagemVO getPostagem() {
		return postagem;
	}

	public void setPostagem(PostagemVO postagem) {
		this.postagem = postagem;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public int getQuantidadeDeCaracteres() {
		return quantidadeDeCaracteres;
	}

	public void setQuantidadeDeCaracteres(int quantidadeDeCaracteres) {
		this.quantidadeDeCaracteres = quantidadeDeCaracteres;
	}

}
