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

import br.com.mongoDB.PostagemRepository;
import br.com.tcc.VO.PostagemVO;


@Path("/posts")
public class ServicoDePostagem {

	private PostagemRepository repositorio;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON) 
	public Response busquePostagens() { 
		
		return Response.ok() //200
				.entity(new Gson().toJson(repositorio().listarTodos()))
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
				.build(); 
	}
	
	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON) 
	public Response busquePostagemPorId(@PathParam("id") String id) {
		return Response.ok() //200
				.entity(new Gson().toJson(repositorio().buscarPorId(id)))
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
				.build(); 
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response adicionePostagem(PostagemVO postagemVO) {
		try {
			repositorio().salvar(postagemVO);
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
	
	private PostagemRepository repositorio() {
		return repositorio != null ? repositorio : new PostagemRepository();
	}
}
