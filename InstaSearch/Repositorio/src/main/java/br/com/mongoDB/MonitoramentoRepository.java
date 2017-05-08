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
import br.com.tcc.VO.MonitoramentoVO;


public class MonitoramentoRepository implements IRepository<MonitoramentoVO, Integer>{

	private MongoDBConection conexao;
	private final String COLECAO = "Monitoramento";
	private final String SEQUENCE = "idMonit";
	
	public MonitoramentoRepository() {
		conexao = MongoDBConection.getConexao();
	}
	
	public void salvar(MonitoramentoVO dominio) {
		dominio.setId(Integer.parseInt(String.valueOf(getNextSequence()).replace(".0", "")));
		DBObject dbObject = (DBObject)JSON.parse(new Gson().toJson(dominio));
		getTabela().insert(dbObject);
		
	}

	public List<MonitoramentoVO> listarTodos() {
		DBCursor cursor = getTabela().find();
		List<MonitoramentoVO> listaPostagem = new ArrayList<MonitoramentoVO>();
		while(cursor.hasNext()) {
			MonitoramentoVO postagem = new Gson().fromJson(cursor.next().toString(),MonitoramentoVO.class);
		    listaPostagem.add(postagem);
		}
		return listaPostagem;
	}

	public MonitoramentoVO buscarPorIdInstagram(String id) {
		DBObject searchQuery = new BasicDBObject().append("idInstagram", id);
		DBCursor cursor = getTabela().find(searchQuery);
		while(cursor.hasNext()) {
			return (new Gson()).fromJson(cursor.next().toString(),MonitoramentoVO.class);
		}
		return null;
	}
	
	public MonitoramentoVO buscarPorId(Integer id) {
		DBObject searchQuery = new BasicDBObject().append("id", id);
		DBCursor cursor = getTabela().find(searchQuery);
		while(cursor.hasNext()) {
			return (new Gson()).fromJson(cursor.next().toString(),MonitoramentoVO.class);
		}
		return null;
	}


	public void atualizar(MonitoramentoVO dominio) {
		
		BasicDBObject newDocument = new BasicDBObject();
		newDocument.append("$set", (DBObject)JSON.parse(new Gson().toJson(dominio)));
				
		DBObject searchQuery = new BasicDBObject().append("id", dominio.getId());
		
		getTabela().update(searchQuery, newDocument);
	}

	private DBCollection getTabela() {
		return conexao.getBanco().getCollection(COLECAO);
	}
		
	public Object getNextSequence() {

		if (conexao.getTabelaSequences().count() == 0) {
			BasicDBObject document = new BasicDBObject();
		    document.append("_id", SEQUENCE);
		    document.append("seq", 0);
		    conexao.getTabelaSequences().insert(document);
	    }
		
	    BasicDBObject searchQuery = new BasicDBObject("_id", SEQUENCE);
	    BasicDBObject increase = new BasicDBObject("seq", 1);
	    BasicDBObject updateQuery = new BasicDBObject("$inc", increase);
	    DBObject result = conexao.getTabelaSequences().findAndModify(searchQuery, null, null,
	            false, updateQuery, true, false);

	    return result.get("seq");
	}
	

}
