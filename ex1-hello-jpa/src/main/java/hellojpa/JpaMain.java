package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		EntityManager em = emf.createEntityManager();

		EntityTransaction tx = em.getTransaction();
		tx.begin();

		try {
			Movie movie = new Movie();
			movie.setName("Avengers");
			movie.setActor("Ironman");
			em.persist(movie);

			Book book = new Book();
			book.setName("Book");
			book.setIsbn("87561AEB1");
			em.persist(book);

			em.clear();
			
			Item item = em.find(Item.class, 1L);
			System.out.println(item.getName());

			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}

		emf.close();
	}

}
