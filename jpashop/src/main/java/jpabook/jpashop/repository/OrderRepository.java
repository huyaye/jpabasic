package jpabook.jpashop.repository;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import jpabook.jpashop.api.dto.OrderAPIDto.SimpleOrderDto;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderSearch;

@Repository
public class OrderRepository {

	@PersistenceContext
	private EntityManager em;

	public void save(Order order) {
		em.persist(order);
	}

	public Order findOne(Long id) {
		return em.find(Order.class, id);
	}

	public List<Order> findAll(OrderSearch orderSearch) {
		// @formatter:off
        return em.createQuery("select o from Order o join o.member m" +
				" where (:status is null or o.status = :status)" +
				" and (:name is null or m.name like :name)", Order.class)
                .setParameter("status", orderSearch.getOrderStatus())
                .setParameter("name", orderSearch.getMemberName())
        .getResultList();
	    // @formatter:on
	}

	public List<Order> findAllWithMemberDelivery(OrderSearch orderSearch) {
		// @formatter:off
		return em.createQuery("select o from Order o " + 
								"join fetch o.member m " + 
								"join fetch o.delivery d " +
								"where (:status is null or o.status = :status) " +
								"and (:name is null or m.name like :name)", Order.class)
				.setParameter("status", orderSearch.getOrderStatus())
                .setParameter("name", orderSearch.getMemberName())
				.getResultList();
	    // @formatter:on
	}

	public List<SimpleOrderDto> findOrderDtos() {
		// @formatter:off
		return em.createQuery("select new jpabook.jpashop.api.dto.OrderAPIDto$SimpleOrderDto(o.id, m.name, o.orderDate, o.status, d.address)" +
								" from Order o" +
								" join o.member m" +
								" join o.delivery d", SimpleOrderDto.class)
				.getResultList();
	    // @formatter:on
	}

	public List<Order> findAllByCriteria(OrderSearch orderSearch) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Order> query = criteriaBuilder.createQuery(Order.class);
		Root<Order> o = query.from(Order.class);
		Join<Object, Object> m = o.join("member", JoinType.INNER);

		//주문 상태 검색
		List<Predicate> criteria = new ArrayList<>();
		if (orderSearch.getOrderStatus() != null) {
			Predicate status = criteriaBuilder.equal(o.get("status"), orderSearch.getOrderStatus());
		}

		//회원 이름 검색
		if (StringUtils.hasText(orderSearch.getMemberName())) {
			Predicate name = criteriaBuilder.like(m.get("name"), "%" + orderSearch.getMemberName() + "%");
			criteria.add(name);
		}

		query.where(criteriaBuilder.and(criteria.toArray(new Predicate[criteria.size()])));
		TypedQuery<Order> query1 = em.createQuery(query).setMaxResults(1000);
		return query1.getResultList();
	}
}
