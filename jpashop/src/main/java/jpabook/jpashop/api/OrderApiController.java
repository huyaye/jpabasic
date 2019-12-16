package jpabook.jpashop.api;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jpabook.jpashop.api.dto.OrderAPIDto.OrderDto;
import jpabook.jpashop.api.dto.ResponseDto;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.OrderSearch;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.query.OrderFlatDto;
import jpabook.jpashop.repository.query.OrderItemQueryDto;
import jpabook.jpashop.repository.query.OrderQueryDto;
import jpabook.jpashop.repository.query.OrderQueryRepository;

@RestController
public class OrderApiController {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderQueryRepository orderQueryRepository;

	/**
	 * V1. 엔티티 직접 노출
	 * - Hibernate5Module 모듈 등록, LAZY=null 처리
	 * - 양방향 관계 문제 발생 -> @JsonIgnore
	 */
	@GetMapping("/api/v1/orders")
	public List<Order> ordersV1() {
		List<Order> all = orderRepository.findAll(OrderSearch.EMPTY);
		for (Order order : all) {
			order.getMember().getName(); //Lazy 강제 초기화
			order.getDelivery().getAddress(); //Lazy 강제 초기환

			List<OrderItem> orderItems = order.getOrderItems();
			orderItems.stream().forEach(o -> o.getItem().getName()); //Lazy 강제 초기화
		}
		return all;
	}

	@GetMapping("/api/v2/orders")
	public ResponseDto<List<OrderDto>> ordersV2() {
		List<Order> orders = orderRepository.findAll(OrderSearch.EMPTY);
		List<OrderDto> result = orders.stream().map(o -> new OrderDto(o)).collect(Collectors.toList());
		return new ResponseDto<>(result);
	}

	/**
	 * SQL 1회 실행
	 */
	@GetMapping("/api/v3/orders")
	public ResponseDto<List<OrderDto>> ordersV3(@RequestParam(value = "orderStatus", required = false) OrderStatus orderStatus,
			@RequestParam(value = "memberName", required = false) String memberName) {
		OrderSearch orderSearch = new OrderSearch();
		orderSearch.setOrderStatus(orderStatus);
		orderSearch.setMemberName(memberName);

		List<Order> orders = orderRepository.findAllWithItem(orderSearch);
		List<OrderDto> result = orders.stream().map(o -> new OrderDto(o)).collect(Collectors.toList());
		return new ResponseDto<>(result);
	}

	/**
	 * V3.1 엔티티를 조회해서 DTO로 변환 페이징 고려
	 * - ToOne 관계만 우선 모두 페치 조인으로 최적화
	 * - 컬렉션 관계는 hibernate.default_batch_fetch_size, @BatchSize로 최적화
	 */
	@GetMapping("/api/v3.1/orders")
	public ResponseDto<List<OrderDto>> ordersV3_page(@RequestParam(value = "offset", defaultValue = "0") int offset,
			@RequestParam(value = "limit", defaultValue = "100") int limit) {
		List<Order> orders = orderRepository.findAllWithMemberDelivery_Paging(offset, limit);
		List<OrderDto> result = orders.stream().map(o -> new OrderDto(o)).collect(Collectors.toList());
		return new ResponseDto<>(result);
	}

	@GetMapping("/api/v4/orders")
	public ResponseDto<List<OrderQueryDto>> ordersV4() {
		List<OrderQueryDto> result = orderQueryRepository.findOrderQueryDtos();
		return new ResponseDto<>(result);
	}

	@GetMapping("/api/v5/orders")
	public ResponseDto<List<OrderQueryDto>> ordersV5() {
		List<OrderQueryDto> result = orderQueryRepository.findAllByDto_optimization();
		return new ResponseDto<>(result);
	}

	@GetMapping("/api/v6/orders")
	public List<OrderQueryDto> ordersV6() {
		List<OrderFlatDto> flats = orderQueryRepository.findAllByDto_flat();
		// OrderQueryDto equals() 구현 필요
		return flats.stream()
				.collect(Collectors.groupingBy(o -> new OrderQueryDto(o.getOrderId(), o.getName(), o.getOrderDate(), o.getOrderStatus(), o.getAddress()),
						Collectors.mapping(o -> new OrderItemQueryDto(o.getOrderId(), o.getItemName(), o.getOrderPrice(), o.getCount()), Collectors.toList())))
				.entrySet().stream().map(e -> new OrderQueryDto(e.getKey().getOrderId(), e.getKey().getName(), e.getKey().getOrderDate(),
						e.getKey().getOrderStatus(), e.getKey().getAddress(), e.getValue()))
				.collect(Collectors.toList());
	}

}
