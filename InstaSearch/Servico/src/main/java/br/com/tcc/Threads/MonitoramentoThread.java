package br.com.tcc.Threads;

import java.util.Calendar;

import br.com.mongoDB.MonitoramentoRepository;
import br.com.mongoDB.PostagemRepository;
import br.com.tcc.Dominio.Monitoramento;
import br.com.tcc.Enums.StatusEnum;

public class MonitoramentoThread extends Thread {
	
	private Monitoramento monitoramento;
	private MonitoramentoRepository repositorio;
	private PostagemRepository repoPostagem;
	
	public MonitoramentoThread(Monitoramento monitoramento) {
		this.monitoramento = monitoramento;
		repositorio = new MonitoramentoRepository();
		repoPostagem = new PostagemRepository();
	}
	
	public void run() {
		
		try {
			
			monitoramento.realizaMonitoramento();
			repositorio.salvar(monitoramento.getVO());
			monitoramento.getVO().getPostagens().forEach(x -> repoPostagem.salvar(x));
			Thread.sleep(60 * 1000);
			
			while(!verifiqueSePassouTresHoras()) {
				monitoramento.realizaMonitoramento();
				repositorio.atualizar(monitoramento.getVO());
				monitoramento.getVO().getPostagens().forEach(x -> {
					if(x.getStatus() == StatusEnum.ATIVO) 
						repoPostagem.atualizar(x);	
					});
				
				Thread.sleep(60 * 1000);
			}
			
			this.interrupt();
			
		} catch (Exception e) {
			e.getMessage();
		}
	}

	
	@Override
	public void interrupt() {
		
		pareMonitoramentoAtualizandoStatus();
		repositorio.atualizar(monitoramento.getVO());
		monitoramento = null;
		super.interrupt();
	}
	
	private boolean verifiqueSePassouTresHoras() {
		//realizar monitoramento infinito
		return false;
		
//		return (System.currentTimeMillis() - monitoramento.getVO().getDataDeInicio()) 
//				>=  3600000 * 3; // 3 horas
//		return (System.currentTimeMillis() - monitoramento.getVO().getDataDeInicio()) 
//				>=  (60 * 1000 * 10);
	}
	
	public Monitoramento getMonitoramento() {
		return monitoramento;
	}
	
	public void setMonitoramento(Monitoramento monitoramento) {
		this.monitoramento = monitoramento;
	}
	
	private void pareMonitoramentoAtualizandoStatus()
	{
		monitoramento.getVO().setDataDeTermino(Calendar.getInstance().getTimeInMillis());
		monitoramento.getVO().setStatus(StatusEnum.INATIVO);
	}
	
}
