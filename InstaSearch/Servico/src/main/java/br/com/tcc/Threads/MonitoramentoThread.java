package br.com.tcc.Threads;

import java.util.Calendar;

import br.com.jpa.dao.MonitoramentoDAO;
import br.com.jpa.dao.PostagemDAO;
import br.com.tcc.Dominio.Monitoramento;
import br.com.tcc.Enums.StatusEnum;

public class MonitoramentoThread extends Thread {
	
	private Monitoramento monitoramento;
	private MonitoramentoDAO repositorio;
	private PostagemDAO repoPostagem;
	private StatusEnum status;
	private long tempoDeMonitoramento;
	
	public MonitoramentoThread(Monitoramento monitoramento, long tempoDeMonitoramento) {
		this.monitoramento = monitoramento;
		repositorio = new MonitoramentoDAO();
		repoPostagem = new PostagemDAO();
		status = StatusEnum.ATIVO;
		this.tempoDeMonitoramento = tempoDeMonitoramento;
	}
	
	public void run() {
		
		try {
			
			monitoramento.realizaMonitoramento();
			repositorio.inserir(monitoramento.getVO());
//			monitoramento.getVO().getPostagens().forEach(x -> {
//				try {
//					repoPostagem.atualizar(x);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			});
			Thread.sleep(60 * 1000);
			
			while(!verifiqueSePassouTempoMaximoMonitoramento() && verifiqueSeFoiCancelada()) {
				monitoramento.realizaMonitoramento();
				repositorio.atualizar(monitoramento.getVO());
//				monitoramento.getVO().getPostagens().forEach(x -> {
//					if(x.getStatus() == StatusEnum.ATIVO) 
//						repoPostagem.atualizar(x);	
//					});
				
				Thread.sleep(60 * 1000);
			}
			
			this.interrupt();
			
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}
	}

	
	private boolean verifiqueSeFoiCancelada() {
		return getStatus().equals(StatusEnum.ATIVO);
	}

	@Override
	public void interrupt() {
		
		pareMonitoramentoAtualizandoStatus();
		repositorio.atualizar(monitoramento.getVO());
		monitoramento = null;
		super.interrupt();
	}
	
	private boolean verifiqueSePassouTempoMaximoMonitoramento() {
		if(getTempoDeMonitoramento() == 0) {
			return false;
		}
		return (System.currentTimeMillis() - monitoramento.getVO().getDataDeInicio()) 
				>=  getTempoDeMonitoramento(); 
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

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public long getTempoDeMonitoramento() {
		return tempoDeMonitoramento;
	}

	public void setTempoDeMonitoramento(long tempoDeMonitoramento) {
		this.tempoDeMonitoramento = tempoDeMonitoramento;
	}
	
}
