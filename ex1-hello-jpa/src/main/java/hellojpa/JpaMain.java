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
			Team team = new Team();

			Member member1 = new Member();
			member1.setUsername("m1");

			Member member2 = new Member();
			member2.setUsername("m2");

			team.addMember(member1);
			team.addMember(member2);

			em.persist(team);

			em.clear();

			Team findTeam = em.find(Team.class, team.getId());
			findTeam.getMembers().remove(0); // Select, Delete query

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
