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
			Team teamA = new Team();
			teamA.setName("teamA");
			em.persist(teamA);

			Team teamB = new Team();
			teamB.setName("teamB");
			em.persist(teamB);

			Member member = new Member();
			member.setUsername("member1");
			member.changeTeam(teamA);
			em.persist(member);

			member = new Member();
			member.setUsername("member2");
			member.changeTeam(teamA);
			em.persist(member);

			member = new Member();
			member.setUsername("member3");
			member.changeTeam(teamB);
			em.persist(member);

			em.clear();

			/*
			 * Lazy fetch. (N + 1)
			 * 팀갯수만큼 SELECT 쿼리 발생
			 */
			List<Member> members = em.createQuery("Select m From Member m", Member.class).getResultList();
			members.forEach(m -> System.out.println("member = " + m.getUsername() + ", " + m.getTeam().getName()));
			
			System.out.println("===================================================================");

			/*
			 * Fetch join
			 * 1회 쿼리로 팀정보 모두 조회
			 */
			members = em.createQuery("Select m From Member m join fetch m.team", Member.class).getResultList();
			members.forEach(m -> System.out.println("member = " + m.getUsername() + ", " + m.getTeam().getName()));
			
			System.out.println("===================================================================");
			/*
			 * Collection fetch join (OneToMany)
			 * distinct로 엔티티중복을 제거 (뻥튀기 가능)
			 */
			List<Team> teams = em.createQuery("Select distinct t From Team t join fetch t.members", Team.class).getResultList();
			teams.forEach(t -> {
				System.out.println("team = " + t.getName() + ", members = " + t.getMembers().size());
				for (Member m : t.getMembers()) {
					System.out.println("--> member = " + m.getUsername());
				}
			});
			
			System.out.println("===================================================================");
			em.clear();
			/*
			 * Paging for Collection fetch join
			 * Batch size를 설정해서 쿼리횟수를 최대 테이블 갯수만큼 줄일 수 있다. (N+1) -> (테이블수)
			 */
			teams = em.createQuery("Select t From Team t", Team.class)
					.setFirstResult(0)
					.setMaxResults(2)
					.getResultList();
			teams.forEach(t -> {
				System.out.println("team = " + t.getName() + ", members = " + t.getMembers().size());
				for (Member m : t.getMembers()) {
					System.out.println("--> member = " + m.getUsername());
				}
			});

			System.out.println("===================================================================");
			/*
			 * Bulk
			 * 영속성컨텍스트에 영향을 받지도 주지도 않는다. 데이터정합성에 문제 가능성 있음
			 */
			int resultCount = em.createQuery("Update Member m set m.age = 20").executeUpdate();
			System.out.println("resultCount = " + resultCount);
			System.out.println(member.getAge());	// 0

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
