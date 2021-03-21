package ir.amin.contextswitch;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import ir.amin.contextswitch.jpa.entity.Car;

public class JPAManager {

	protected EntityManager entityManager;

	public JPAManager(String puName) {
		if (entityManager == null) {
			this.entityManager = getEntityManager(puName);
		}
	}

	public void insertSampleData() {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		for (int i = 0; i < 5; i++) {
			Car car = new Car();
			car.setName("car" + i);
			entityManager.persist(car);
		}
		transaction.commit();
	}

	public Integer carCount() {
		Query query = entityManager.createQuery("select count(c) from Car c");
		Object singleResult = query.getSingleResult();
		return Integer.valueOf(singleResult.toString());
	}

	public List<Car> getCars() {
		Query query = entityManager.createQuery("select c from Car c");
		List list = query.getResultList();
		return list;
	}

	public void insertCar(Car car) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(car);
		transaction.commit();
		car.getClass().getSimpleName();
	}

	private EntityManager getEntityManager(String puName) {
		// Map<Object,Object> props = new HashMap<Object,Object>();
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory(puName);
			EntityManager entityManager = emf.createEntityManager();
			return entityManager;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void truncateCarTable() {
		entityManager.getTransaction().begin();

		// JPQL
		// entityManager.createQuery("DELETE FROM Car c").executeUpdate();

		// SQL
		entityManager.createNativeQuery("TRUNCATE TABLE Car").executeUpdate();
		entityManager.getTransaction().commit();
	}

}
