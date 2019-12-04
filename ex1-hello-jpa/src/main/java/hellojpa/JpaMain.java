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
			Member member = new Member();
			member.setUsername("memberA");

			System.out.println("===============================");
			em.persist(member);
			System.out.println("===============================");

			Team team = new Team();
			team.setName("TeamA");
			team.getMembers().add(member); // Update query

			System.out.println("===============================");
			em.persist(team);
			System.out.println("===============================");

			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}

		emf.close();
	}

}
