package br.com.mongoDB;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import br.com.interfaces.IRepository;
import br.com.tcc.VO.PostagemVO;


public class PostagemRepository implements IRepository<PostagemVO, Integer>{

	private MongoDBConection conexao;
	private final String COLECAO = "Postagem";
	
	public PostagemRepository() {
		conexao = MongoDBConection.getConexao();
	}
	
	public void salvar(PostagemVO dominio) {
		if(!verifiqueRegistroExiste(dominio.getIdInstagram())) {
			DBObject dbObject = (DBObject)JSON.parse(new Gson().toJson(dominio));
			getTabela().insert(dbObject);
		} else {
			atualizar(dominio);
		}
	}

	public List<PostagemVO> listarTodos() {
		DBCursor cursor = getTabela().find();
		List<PostagemVO> listaPostagem = new ArrayList<PostagemVO>();
		while(cursor.hasNext()) {
			PostagemVO postagemVO = new Gson().fromJson(cursor.next().toString(),PostagemVO.class);
		    listaPostagem.add(postagemVO);
		}
		return listaPostagem;
	}

	public PostagemVO buscarPorIdInstagram(String id) {
		DBObject searchQuery = new BasicDBObject().append("idInstagram", id);
		DBCursor cursor = getTabela().find(searchQuery);
		while(cursor.hasNext()) {
			return (new Gson()).fromJson(cursor.next().toString(),PostagemVO.class);
		}
		return null;
	}
	
	public PostagemVO buscarPorId(Integer id) {
		DBObject searchQuery = new BasicDBObject().append("id", id);
		DBCursor cursor = getTabela().find(searchQuery);
		while(cursor.hasNext()) {
			return (new Gson()).fromJson(cursor.next().toString(),PostagemVO.class);
		}
		return null;
	}
	
	public boolean verifiqueRegistroExiste(String id) {
		DBObject searchQuery = new BasicDBObject().append("id", id);
		DBCursor cursor = getTabela().find(searchQuery);
		if(cursor.hasNext()) {
			return true;
		}
		return false;
	}

	public void atualizar(PostagemVO dominio) {
		BasicDBObject newDocument = new BasicDBObject();
		newDocument.append("$set", (DBObject)JSON.parse(new Gson().toJson(dominio)));
				
		DBObject searchQuery = new BasicDBObject().append("id", dominio.getId());
		
		getTabela().update(searchQuery, newDocument);
	}

	private DBCollection getTabela() {
		return conexao.getBanco().getCollection(COLECAO);
	}

	public Object getNextSequence() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
