package br.com.ufg.tcc.model;

import java.util.ArrayList;
import java.util.List;

public class InformacoesUsuario {

	private List<Post> listaPosts;
	private Usuario usuario;
	private String nome;
	private String localArquivo;
	
	public InformacoesUsuario() {
		listaPosts = new ArrayList<Post>();
		usuario = new Usuario();
	}
	
	
	public List<Post> getListaPosts() {
		return listaPosts;
	}
	public void setListaPosts(List<Post> listaPosts) {
		this.listaPosts = listaPosts;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getLocalArquivo() {
		return localArquivo;
	}


	public void setLocalArquivo(String localArquivo) {
		this.localArquivo = localArquivo;
	}
}
