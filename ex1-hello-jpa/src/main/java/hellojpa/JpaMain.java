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
			team.setName("TeamA");
			em.persist(team);

			Member member = new Member();
			member.setUsername("memberA");
			member.setTeam(team);
			em.persist(member);

			em.clear();

			//			Member findMember = em.find(Member.class, 1L);
			//			List<Member> members = findMember.getTeam().getMembers();
			//			members.forEach(m -> System.out.println(m.getUsername()));

			// OneToMany �� Join ������ ������ �ʰ� ����. ���� 2�� ����
			Team findTeam = em.find(Team.class, 1L);
			System.out.println(findTeam.getMembers().get(0).getUsername()); // ���� �ε�

			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}

		emf.close();
	}

}
