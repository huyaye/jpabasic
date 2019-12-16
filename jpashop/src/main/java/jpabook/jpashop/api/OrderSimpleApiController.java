package jpabook.jpashop.api;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jpabook.jpashop.api.dto.OrderAPIDto.SimpleOrderDto;
import jpabook.jpashop.api.dto.ResponseDto;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderSearch;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;

@RestController
public class OrderSimpleApiController {

	@Autowired
	private OrderRepository orderRepository;

	/**
	 * V1. 엔티티 직접 노출
	 * - Hibernate5Module 모듈 등록, LAZY=null 처리
	 * - 양방향 관계 문제 발생, 무한 Recursive -> @JsonIgnore
	 */
	@GetMapping("/api/v1/simple-orders")
	public List<Order> ordersV1() {
		List<Order> all = orderRepository.findAll(OrderSearch.EMPTY);
		for (Order order : all) {
			order.getMember().getName(); //Lazy 강제 초기화
			order.getDelivery().getAddress(); //Lazy 강제 초기환
		}
		return all;
	}

	/**
	 * V2. 엔티티를 조회해서 DTO로 변환(fetch join 사용X)
	 * - 단점: 지연로딩으로 쿼리 N번 호출
	 */
	@GetMapping("/api/v2/simple-orders")
	public ResponseDto<List<SimpleOrderDto>> ordersV2() {
		List<Order> orders = orderRepository.findAll(OrderSearch.EMPTY);
		List<SimpleOrderDto> result = orders.stream().map(SimpleOrderDto::new).collect(Collectors.toList());
		return new ResponseDto<>(result);
	}

	/**
	 * V3. 엔티티를 조회해서 DTO로 변환(fetch join 사용O)
	 * - fetch join으로 쿼리 1번 호출
	 */
	@GetMapping("/api/v3/simple-orders")
	public ResponseDto<List<SimpleOrderDto>> ordersV3(@RequestParam(value = "orderStatus", required = false) OrderStatus orderStatus,
			@RequestParam(value = "memberName", required = false) String memberName) {
		OrderSearch orderSearch = new OrderSearch();
		orderSearch.setOrderStatus(orderStatus);
		orderSearch.setMemberName(memberName);
		List<Order> orders = orderRepository.findAllWithMemberDelivery(orderSearch);
		List<SimpleOrderDto> result = orders.stream().map(o -> new SimpleOrderDto(o)).collect(Collectors.toList());
		return new ResponseDto<>(result);
	}

	/**
	 * V4. JPA에서 DTO로 바로 조회
	 * - 쿼리 1번 호출
	 * - select 절에서 원하는 데이터만 선택해서 조회
	 */
	@GetMapping("/api/v4/simple-orders")
	public ResponseDto<List<SimpleOrderDto>> ordersV4() {
		List<SimpleOrderDto> result = orderRepository.findOrderDtos();
		return new ResponseDto<>(result);
	}
}
