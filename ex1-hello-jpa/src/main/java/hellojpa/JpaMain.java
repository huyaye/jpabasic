package hellojpa;

import java.util.List;
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
			Team team1 = new Team();
			team1.setName("TeamA");
			em.persist(team1);

			Member member1 = new Member();
			member1.setUsername("m1");
			member1.setTeam(team1);
			em.persist(member1);

			em.clear();

			// Member m1 = em.find(Member.class, member1.getId());
			// System.out.println("username : " + m1.getUsername());
			// System.out.println("teamname : " + m1.getTeam().getName());

			List<Member> members = em.createQuery("SELECT m FROM Member m", Member.class).getResultList(); // EAGER 인 경우, SQL 쿼리 2번.

			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}

		emf.close();
	}

}
