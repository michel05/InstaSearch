package br.com.ufg.tcc.model;

import java.util.Date;

public class Post {

	private String id;
	private String nome;
	private String descricao;
	
	private int numCurtidas;
	private int numMarcacoes;
	private int numComentarios;
	private Date data;
	private int mes;
	private int numCaracteres;
	private int numHashtags;
	private String link;
	
	public int getNumCurtidas() {
		return numCurtidas;
	}
	public void setNumCurtidas(int numCurtidas) {
		this.numCurtidas = numCurtidas;
	}
	public int getNumMarcacoes() {
		return numMarcacoes;
	}
	public void setNumMarcacoes(int numMarcacoes) {
		this.numMarcacoes = numMarcacoes;
	}
	public int getNumComentarios() {
		return numComentarios;
	}
	public void setNumComentarios(int numComentarios) {
		this.numComentarios = numComentarios;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	public int getNumCaracteres() {
		return numCaracteres;
	}
	public void setNumCaracteres(int numCaracteres) {
		this.numCaracteres = numCaracteres;
	}
	public int getNumHashtags() {
		return numHashtags;
	}
	public void setNumHashtags(int numHashtags) {
		this.numHashtags = numHashtags;
	}
	
	@Override
	public String toString(){
		
		return "Curtidas: " + getNumCurtidas() + " | Marcacoes: " + getNumMarcacoes() + ""
				+ " | Comentarios: " + getNumComentarios() + " | data: " + getData().toString() + " "
						+ " | Caracteres: " + getNumCaracteres() + " | HashTags: " + getNumHashtags();
				
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
}
