package br.com.tcc.Utilitarios;

import javax.persistence.EntityManager;

import br.com.jpa.fabrica.DaoFactory;

public class AtualizaBanco {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		EntityManager em = DaoFactory.entiManagerFactoryInstance().createEntityManager(); 
		
		em.getTransaction().begin();
		em.getTransaction().commit();
		
		em.close();
		DaoFactory.entiManagerFactoryInstance().close();
	}

}
