package br.com.servicos;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.google.gson.GsonBuilder;

import br.com.jpa.contratos.BaseDao;
import br.com.jpa.dao.MonitoramentoDAO;
import br.com.tcc.Dominio.Monitoramento;
import br.com.tcc.Enums.StatusEnum;
import br.com.tcc.Relatorio.MonitoramentoRelatorio;
import br.com.tcc.Threads.MonitoramentoThread;
import br.com.tcc.VO.MonitoramentoVO;
import br.com.tcc.configuracoes.Configuracoes;

@Path("/monitoramento")
public class ServicoDeMonitoramento {

	private static BaseDao<MonitoramentoVO, Integer> repositorio;
	private static MonitoramentoThread monitoramentoThread = null;
	
	@GET
	@Path("/exportarMonitoramento/{id}")
	@Produces("application/vnd.ms-excel")
	public Response downloadMonitoramento(@PathParam("id") int id) {
		MonitoramentoVO monitoramento = repositorio().findById(id);
		
		try {
			MonitoramentoRelatorio mRelatorio = new MonitoramentoRelatorio(monitoramento);
			ResponseBuilder response = Response.ok((Object) mRelatorio.gerarExcelInstagram().toByteArray());
			response.header("Content-Disposition",
				"attachment; filename=monitoramento"+ id +".xls");
			return response.build();
		} catch (Exception e) {
			return Response.status(404).entity("Falha no download").build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON) 
	public String busqueMonitoramentos() { 
		return gerarGson(repositorio().getLista());
	}
	
	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON) 
	public String busqueMonitoramentoPorId(@PathParam("id") int id) {
		return gerarGson(repositorio().findById(id));
	}
	
	@Path("/iniciar/{titulo}")
	@GET
	@Produces(MediaType.APPLICATION_JSON) 
	public String inicieMonitoramento(@PathParam("titulo") String titulo) throws InterruptedException {
			monitoramentoThread = new MonitoramentoThread(new Monitoramento(titulo),
										Configuracoes.tempoDeMonitoramento);
			monitoramentoThread.start();
			Thread.sleep(2000);
		
		return gerarGson(verifiqueMonitoramentoAtivo()); 
	}
	
	@Path("/cancelar")
	@GET
	@Produces(MediaType.APPLICATION_JSON) 
	public String canceleMonitoramento() throws InterruptedException {
		
		MonitoramentoVO monitoramentoAtual = null;
		if (monitoramentoThread != null) {
			monitoramentoAtual = monitoramentoThread.getMonitoramento().getVO();
			
			monitoramentoThread.setStatus(StatusEnum.INATIVO);
			monitoramentoThread = null;
		}
		
		return gerarGson(monitoramentoAtual);
	}
	
	@Path("/verifiqueMonitoramento")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String verifiqueMonitoramentoAtivo() {
		
 		MonitoramentoVO monitoramentoAtual = null;
		if (monitoramentoThread != null) {
			if(monitoramentoThread.isAlive()) {
				monitoramentoAtual = monitoramentoThread.getMonitoramento().getVO();
				monitoramentoAtual = (monitoramentoAtual.getStatus() == StatusEnum.INATIVO) ? null : monitoramentoAtual;
			}
		}
		
		return gerarGson(monitoramentoAtual);
	}
	
	@Path("/tempoMonitoramento")
	@GET
	@Produces(MediaType.TEXT_PLAIN) 
	public String buscarTempoMonitoramento() {
		long milissegundos = (Configuracoes.tempoDeMonitoramento);
		int horas = (int) (milissegundos / 3600000);
		int minutos = (int) ((milissegundos % 3600000) / 60000);
		return "" + (horas < 10 ? "0" : "") + horas 
				+ ":"
				+ (minutos < 10 ? "0" : "") + minutos;
	}
	
	@Path("/tempoMonitoramento/{tempo}")
	@GET
	@Produces({MediaType.TEXT_PLAIN})
	public String tempoDeMonitoramento(@PathParam("tempo") long tempo) {
		Configuracoes.tempoDeMonitoramento = tempo;
		return buscarTempoMonitoramento();
	}
	
	private BaseDao<MonitoramentoVO, Integer> repositorio() {
		return repositorio != null ? repositorio : new MonitoramentoDAO();
	}
	
	private String gerarGson(Object object) {
		if(object == null) {
			return null;
		}
		return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(object);
	}
	
}
