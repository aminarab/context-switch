package ir.amin.contextswitch;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.Persistence;

import org.dozer.DozerBeanMapper;

import ir.amin.contextswitch.jpa.entity.Car;

public class Bootstrap {

	public static void main(String[] args) throws IOException, SQLException {

		JPAManager mysqlJPAManager = new JPAManager("mysqlpu");
		mysqlJPAManager.insertSampleData();
		System.out.println(mysqlJPAManager.carCount());
		List<Car> cars = mysqlJPAManager.getCars();
		
		try {
			JPAManager h2JPAManager = new JPAManager("h2pu");
			for (Car car : cars) {
				
				/*
				 * Exception in thread "main" <openjpa-2.4.2-r422266:1777108 nonfatal user error> org.apache.openjpa.persistence.ArgumentException:
				 *  The given instance "ir.amin.contextswitch.jpa.entity.Car-1" is not managed by this context.
				 *  FailedObject: ir.amin.contextswitch.jpa.entity.Car-1
				 */
				
				
	//			car.setId(null);
				/*
				 * Exception in thread "main" <openjpa-2.4.2-r422266:1777108 nonfatal user error> org.apache.openjpa.persistence.InvalidStateException:
				 * Attempt to change a primary key field of an instance that already has a final object id.  Only new, unflushed instances whose id you have not retrieved can have their primary keys changed.
				 * FailedObject: ir.amin.contextswitch.jpa.entity.Car-1
				 */
				
				
				
	//			h2JPAManager.getEntityManager().merge(car);
				/* 
				 * Exception in thread "main" <openjpa-2.4.2-r422266:1777108 nonfatal user error> org.apache.openjpa.persistence.InvalidStateException:
				 * Can only perform operation while a transaction is active.
				 * FailedObject: ir.amin.contextswitch.jpa.entity.Car-1 [java.lang.String]
				 */		
				
//				mysqlJPAManager.getEntityManager().detach(car);
//				h2JPAManager.getEntityManager().clear();
//				h2JPAManager.getEntityManager().getTransaction().begin();
//				h2JPAManager.getEntityManager().refresh(car, LockModeType.NONE);
//				h2JPAManager.getEntityManager().lock(car, LockModeType.NONE);
//				h2JPAManager.getEntityManager().merge(car);
//				h2JPAManager.getEntityManager().getTransaction().commit();
				/*
				 * Exception in thread "main" <openjpa-2.4.2-r422266:1777108 fatal store error> org.apache.openjpa.persistence.OptimisticLockException:
				 * Attempted to attach deleted instance type "class ir.amin.contextswitch.jpa.entity.Car" with oid "1".  If the instance is new, the version field should be left to its default value.
				 * FailedObject: ir.amin.contextswitch.jpa.entity.Car-1
				 */
				 
	
	//			h2JPAManager.getEntityManager().getTransaction().begin();
	//			car.setId(null);
	//			h2JPAManager.getEntityManager().merge(car);
	//			h2JPAManager.getEntityManager().getTransaction().commit();
				/*
				 * Exception in thread "main" <openjpa-2.4.2-r422266:1777108 nonfatal user error> org.apache.openjpa.persistence.InvalidStateException:
				 * Attempt to change a primary key field of an instance that already has a final object id.  Only new, unflushed instances whose id you have not retrieved can have their primary keys changed.
				 * FailedObject: ir.amin.contextswitch.jpa.entity.Car-1
				 */
				
				
                            /*
			     * Method 1 : clear OpenJPA State
			     */
			    Field field = car.getClass().getDeclaredField("pcStateManager");
			    field.setAccessible(true);
			    field.set(car, null);
			    car.setId(null);
			    h2JPAManager.insertCar(car);
				
			   /*
			    * Method 2: entity mapping 
			    */
				
			//    List<String> mappingFiles = new ArrayList<String>();
			//    mappingFiles.add("dozer-mappings.xml");
			//    DozerBeanMapper mapper = new DozerBeanMapper();
			//    mapper.setMappingFiles(mappingFiles);


			    Car h2Car = mapper.map(car, Car.class);
			    /*
			     * <openjpa-2.4.2-r422266:1777108 nonfatal store error> org.apache.openjpa.persistence.EntityExistsException:
			     *  Attempt to persist detached object "ir.amin.contextswitch.jpa.entity.Car@6ad6fa53".  If this is a new instance, make sure any version and/or auto-generated primary key fields are null/default when persisting.
			     * FailedObject: ir.amin.contextswitch.jpa.entity.Car@6ad6fa5
			     */
			    
				
				h2JPAManager.insertCar(h2Car);
			}
		} catch (Exception e) {
			e.printStackTrace();
			mysqlJPAManager.cleanMySQL();
		}
//		h2JPAManager.insertSampleData();
//		System.out.println(h2JPAManager.carCount());
		
		H2Server h2Server = new H2Server();
		h2Server.createDBByNameAndDefaultUser("jpadb");
		String url = h2Server.getH2Container().getConnection().getMetaData().getURL();
		System.out.println(url);
		String address = "http://127.0.0.1:8082/";
		Runtime.getRuntime().exec(new String[] { "cmd", "/c", "start chrome " + address });		
		
	}
	
}
