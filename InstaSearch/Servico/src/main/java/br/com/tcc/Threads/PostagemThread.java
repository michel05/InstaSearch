package br.com.tcc.Threads;

import br.com.mongoDB.MonitoramentoRepository;
import br.com.mongoDB.PostagemRepository;
import br.com.tcc.Dominio.Monitoramento;
import br.com.tcc.Dominio.Postagem;

public class PostagemThread extends Thread {

	private Postagem postagem;
	private PostagemRepository repositorio;
	
	public PostagemThread(Monitoramento monitoramento) {
		postagem = new Postagem();
		repositorio = new PostagemRepository();
	}
	
	public void run() {
			
	}
}
