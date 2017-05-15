package br.com.jpa.contratos;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import br.com.jpa.fabrica.DaoFactory;
import br.com.tcc.Interfaces.IDominioPersistente;


@SuppressWarnings("unchecked")
public abstract class BaseDao<T extends IDominioPersistente<PK>, PK> {
	
	private final EntityManager entityManager;
	private final EntityManagerFactory entityManagerFactory;

	private Class<?> clazz;
	
	public BaseDao(){
		this(DaoFactory.entiManagerFactoryInstance());
	}
	
	private BaseDao(EntityManagerFactory entityManagerFactory){
		
		this.entityManagerFactory = entityManagerFactory;
		this.entityManager = entityManagerFactory.createEntityManager();
		this.clazz = (Class<?>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		
	}
	
	public Object executeQuery(String query, Object... params) {
		Query createdQuery = this.entityManager.createQuery(query);

		for (int i = 0; i < params.length; i++) {
			createdQuery.setParameter(i, params[i]);
		}

		return createdQuery.getResultList();
	}

	public List<T> getLista() {
		return this.entityManager.createQuery(("FROM " + this.clazz.getName()))
				.getResultList();
	}

	public T findById(PK pk) {
		return (T) this.entityManager.find(this.clazz, pk);
	}

	public boolean findBy(T t) {
		return  this.entityManager.contains(t);
	}
	
	public void inserir(T entity) throws Exception  {
		try {
			this.beginTransaction();
			this.entityManager.persist(entity);
			this.commit();
		} catch (Exception e) {
			this.rollBack();
			throw e;
		} 

	}

	public void atualizar(T entity) {
		try {
			this.beginTransaction();
			this.entityManager.merge(entity);
			this.commit();
		} catch (Exception e) {
			this.rollBack();
			e.printStackTrace();
		}
	}

	public void deleteAll(List<T> listT) {
		listT.forEach(t -> {
			this.delete(t, (PK) t.getId());
		});
    }
	
	public T delete(T entity, PK codigo){
		try {
			this.beginTransaction();
			entity = this.findById(codigo);
			this.entityManager.remove(entity);
			this.commit();
		} catch (Exception e) {
			this.rollBack();
			e.printStackTrace();
		}
		return entity;
	}

	
	public void beginTransaction() {
		this.entityManager.getTransaction().begin();
	}
	
	public void commit(){
		this.entityManager.getTransaction().commit();
	}

	public void close(){
		this.entityManager.close();
		this.entityManagerFactory.close();
	}
	
	public void rollBack(){
		this.entityManager.getTransaction().rollback();
	}
	
	public EntityManager getEntityManager(){
		return this.entityManager;
	}
}

