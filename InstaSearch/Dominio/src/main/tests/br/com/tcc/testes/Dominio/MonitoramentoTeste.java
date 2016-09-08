package br.com.tcc.testes.Dominio;

import java.util.Calendar;
import java.util.Date;

import br.com.tcc.Dominio.Monitoramento;

public class MonitoramentoTeste {

	public static void main(String[] args) throws InterruptedException {
		
//		Monitoramento monitoramento = new Monitoramento("Monitoramento X");
//		
//		while(true) {
//			monitoramento.realizaMonitoramento();
//			
//			Thread.sleep(3 * 1000);
//		}
		
//		System.out.println(new Date(Long.parseLong("1472257590") * 1000));
		System.out.println(Calendar.getInstance().getTimeInMillis());
		
	}
}
