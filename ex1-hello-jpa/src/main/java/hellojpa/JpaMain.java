package hellojpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class JpaMain {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		EntityManager em = emf.createEntityManager();

		EntityTransaction tx = em.getTransaction();
		tx.begin();

		try {
			Team team = new Team();
			team.setName("teamA");
			em.persist(team);

			Member member = new Member();
			member.setUsername("member1");
			member.setAge(10);
			member.changeTeam(team);
			em.persist(member);
	
			em.clear();

			TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);

			TypedQuery<String> query2 = em.createQuery("select m.username from Member m", String.class);

			Member result = em.createQuery("select m from Member m where m.username = :username", Member.class)
								.setParameter("username", "member1")
								.getSingleResult();
			System.out.println("result = " + result.getUsername());

			// Embedded type projection
			em.createQuery("select o.address from Order o", Address.class).getResultList();

			// Scalar type projection
			List<MemberDTO> result2 = em.createQuery("select distinct new hellojpa.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
										.getResultList();
			// Paging
			List<Member> result3 = em.createQuery("select m from Member m order by m.age desc", Member.class)
										.setFirstResult(10)
										.setMaxResults(20)
										.getResultList();
			/*
			 * Join
			 * fetch join이 아니므로 Team정보는 가져오지 않는다. 따라서 team은 Proxy로 가져온다. 
			 * Member의 Team을 Lazy fetch로 설정하지 않으면 바로 Team에 대한 Select쿼리가 실행됨. ManyToOne은 기본 fetch전략이 Eager이기 때문. 
			 */
			em.clear();
			List<Member> result4 = em.createQuery("select m from Member m inner join m.team t", Member.class)
										.getResultList();
			em.clear();
			List<Member> result5 = em.createQuery("select m from Member m left outer join m.team t", Member.class)
										.getResultList();

			// Case
			String query = "select " +
								"case when m.age <= 10 then 'A' " +
								"	  when m.age <= 60 then 'B' " +
								"     else 'C' " +
								"end " +
							"from Member m";
			List<String> result6 = em.createQuery(query, String.class).getResultList();
			result6.forEach(r -> System.out.println(r));
			
			
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
