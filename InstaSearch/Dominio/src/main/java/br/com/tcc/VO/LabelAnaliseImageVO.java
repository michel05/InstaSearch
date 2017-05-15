package br.com.tcc.VO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.tcc.Interfaces.IDominioPersistente;

//@Entity
@Table(name="analise_imagem_label")
public class LabelAnaliseImageVO implements IDominioPersistente<Integer>{
	
	private static final long serialVersionUID = -4622044138072553427L;

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO) 
	private Integer id;
	
	@ManyToOne
	@JoinColumns({
		  @JoinColumn(name = "idPostagem", insertable = false, updatable = false),
		  @JoinColumn(name = "idLabelAnaliseImageVO", insertable = false, updatable = false)
		})
	private PostagemVO postagem;
	
	@ManyToOne
	@JoinColumn(name="fk_categoria_imagem")
	private CategoriaImagemVO categoriaImagem;
	
	private double nota;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public PostagemVO getPostagem() {
		return postagem;
	}
	public void setPostagem(PostagemVO postagem) {
		this.postagem = postagem;
	}
	public CategoriaImagemVO getCategoriaImagem() {
		return categoriaImagem;
	}
	public void setCategoriaImagem(CategoriaImagemVO categoriaImagem) {
		this.categoriaImagem = categoriaImagem;
	}
	public double getNota() {
		return nota;
	}
	public void setNota(double nota) {
		this.nota = nota;
	}
}
