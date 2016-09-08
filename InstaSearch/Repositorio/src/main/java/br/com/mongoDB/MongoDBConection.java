package br.com.mongoDB;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public class MongoDBConection {

	private final String SEQUENCES = "counters";
	private static MongoDBConection mongoDBConection;
	private Mongo mongoClient;
	private DB db;
	
	@SuppressWarnings("deprecation")
	private MongoDBConection() {
		mongoClient = new Mongo("52.10.61.126", 27017);
//		mongoClient = new Mongo("localhost", 27017);

		db = mongoClient.getDB("instaSearch");
	}
	
	public static MongoDBConection getConexao(){
		return mongoDBConection != null ? mongoDBConection : new MongoDBConection();
	}

	public DB getBanco() {
		return db;
	}
	
	public DBCollection getTabelaSequences() {
		return this.getBanco().getCollection(SEQUENCES);
	}
	
}
