package br.com.tcc.VO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import br.com.tcc.Enums.TipoDeAnaliseDeImagem;
import br.com.tcc.Interfaces.IDominioPersistente;

//@Entity
@Table(name="categoria_imagem")
@NamedQueries({
	@NamedQuery(name = "categoriaImagem.buscarPorNome", 
			query = "SELECT categoriaImagem FROM CategoriaImagemVO categoriaImagem "
					+ "WHERE categoriaImagem.nome = :nome")
	})
public class CategoriaImagemVO implements IDominioPersistente<Integer> {

	private static final long serialVersionUID = 6204842107018776017L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO) 
	private int id;
	private String nome;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_analise_imagem")
	private TipoDeAnaliseDeImagem tipoDeAnaliseDeImagem;
	
	public Integer getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public TipoDeAnaliseDeImagem getTipoDeAnaliseDeImagem() {
		return tipoDeAnaliseDeImagem;
	}
	public void setTipoDeAnaliseDeImagem(TipoDeAnaliseDeImagem tipoDeAnaliseDeImagem) {
		this.tipoDeAnaliseDeImagem = tipoDeAnaliseDeImagem;
	}
}
