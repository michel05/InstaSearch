package br.com.jpa.fabrica;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.jpa.dao.MonitoramentoDAO;
import br.com.jpa.dao.PostagemDAO;

public final class DaoFactory {

	private static final String PERSISTENCE = "postgres";
	private static EntityManagerFactory entityManagerFactoryInstance;
	private static MonitoramentoDAO monitoramentoDao;
	private static PostagemDAO postagemDao;
	
	private DaoFactory(){}
	
	public static EntityManagerFactory entiManagerFactoryInstance() {
		entityManagerFactoryInstance = Persistence.createEntityManagerFactory(PERSISTENCE);
		
		return entityManagerFactoryInstance;
	}
	
	public static MonitoramentoDAO monitoramentoDaoInstance() {
		if(monitoramentoDao == null)
			monitoramentoDao = new MonitoramentoDAO();
		
		return monitoramentoDao;
	}
	
	public static PostagemDAO postagemDaoInstance() {
		if(postagemDao == null)
			postagemDao = new PostagemDAO();
		
		return postagemDao;
	}

}
