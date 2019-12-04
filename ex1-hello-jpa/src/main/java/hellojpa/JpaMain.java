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

			// OneToMany 는 Join 쿼리를 만들지 않고 있음. 쿼리 2번 날라감
			Team findTeam = em.find(Team.class, 1L);
			System.out.println(findTeam.getMembers().get(0).getUsername()); // 지연 로딩

			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}

		emf.close();
	}

}
