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
			member.setUsername("A");

			Member member2 = new Member();
			member2.setUsername("B");

			Member member3 = new Member();
			member3.setUsername("C");

			System.out.println("=============================");

			em.persist(member);
			em.persist(member2);
			em.persist(member3);

			System.out.println("memberId : " + member.getId());
			System.out.println("member2Id : " + member2.getId());
			System.out.println("member3Id : " + member3.getId());
			System.out.println("=============================");

			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}

		emf.close();
	}

}
