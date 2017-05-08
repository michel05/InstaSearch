package br.com.servicos;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import br.com.jpa.dao.PostagemDAO;
import br.com.tcc.VO.PostagemVO;


@Path("/posts")
public class ServicoDePostagem {

	private PostagemDAO repositorio;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON) 
	public Response busquePostagens() { 
		
		return Response.ok() //200
				.entity(new Gson().toJson(repositorio().getLista()))
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
				.build(); 
	}
	
	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON) 
	public Response busquePostagemPorId(@PathParam("id") int id) {
		return Response.ok() //200
				.entity(new Gson().toJson(repositorio().findById(id)))
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
				.build(); 
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response adicionePostagem(PostagemVO postagemVO) {
		try {
			repositorio().inserir(postagemVO);
			return Response.ok()
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		} catch (Exception e) {
			return Response.serverError()
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
		}
	}
	
	private PostagemDAO repositorio() {
		return repositorio != null ? repositorio : new PostagemDAO();
	}
}
