package br.com.servicos;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.google.gson.Gson;

import br.com.jpa.contratos.BaseDao;
import br.com.jpa.dao.MonitoramentoDAO;
import br.com.tcc.Dominio.Monitoramento;
import br.com.tcc.Enums.StatusEnum;
import br.com.tcc.Relatorio.MonitoramentoRelatorio;
import br.com.tcc.Threads.MonitoramentoThread;
import br.com.tcc.VO.MonitoramentoVO;


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
		return new Gson().toJson(repositorio().getLista());
	}
	
	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON) 
	public String busqueMonitoramentoPorId(@PathParam("id") int id) {
		return new Gson().toJson(repositorio().findById(id));
	}
	
	@Path("/iniciar/{titulo}")
	@GET
	@Produces(MediaType.APPLICATION_JSON) 
	public String inicieMonitoramento(@PathParam("titulo") String titulo) throws InterruptedException {
		
//		if(monitoramentoThread == null || verifiqueMonitoramentoAtivo() == null) {
			monitoramentoThread = new MonitoramentoThread(new Monitoramento(titulo));
			monitoramentoThread.start();
			Thread.sleep(2000);
//		}
		
		return new Gson().toJson(verifiqueMonitoramentoAtivo()); 
	}
	
	@Path("/cancelar")
	@GET
	@Produces(MediaType.APPLICATION_JSON) 
	public String canceleMonitoramento() throws InterruptedException {
		
		MonitoramentoVO monitoramentoAtual = null;
		if (monitoramentoThread != null) {
			monitoramentoAtual = monitoramentoThread.getMonitoramento().getVO();
			
			monitoramentoThread.interrupt();
			monitoramentoThread = null;
		}
		
		return new Gson().toJson(monitoramentoAtual);
	}
	
	@Path("/verifiqueMonitoramento")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String verifiqueMonitoramentoAtivo() {
		
		MonitoramentoVO monitoramentoAtual = null;
		if (monitoramentoThread != null) {
			monitoramentoAtual = monitoramentoThread.getMonitoramento().getVO();
			monitoramentoAtual = (monitoramentoAtual.getStatus() == StatusEnum.INATIVO) ? null : monitoramentoAtual;
		}
		
		return new Gson().toJson(monitoramentoAtual);
	}
	
	private BaseDao<MonitoramentoVO, Integer> repositorio() {
		return repositorio != null ? repositorio : new MonitoramentoDAO();
	}
	
}
