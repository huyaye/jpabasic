package jpabook.jpashop.api.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.OrderStatus;

public class OrderAPIDto {

	public static class SimpleOrderDto {
		private Long orderId;
		private String name;
		private LocalDateTime orderDate; //주문시간
		private OrderStatus orderStatus;
		private Address address;

		public SimpleOrderDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address) {
			this.orderId = orderId;
			this.name = name;
			this.orderDate = orderDate;
			this.orderStatus = orderStatus;
			this.address = address;
		}

		public SimpleOrderDto(Order order) {
			orderId = order.getId();
			name = order.getMember().getName();
			orderDate = order.getOrderDate();
			orderStatus = order.getStatus();
			address = order.getDelivery().getAddress();
		}

		public Long getOrderId() {
			return orderId;
		}

		public String getName() {
			return name;
		}

		public LocalDateTime getOrderDate() {
			return orderDate;
		}

		public OrderStatus getOrderStatus() {
			return orderStatus;
		}

		public Address getAddress() {
			return address;
		}
	}

	public static class OrderDto {
		private Long orderId;
		private String name;
		private LocalDateTime orderDate; //주문시간
		private OrderStatus orderStatus;
		private Address address;
		private List<OrderItemDto> orderItems;

		public OrderDto(Order order) {
			orderId = order.getId();
			name = order.getMember().getName();
			orderDate = order.getOrderDate();
			orderStatus = order.getStatus();
			address = order.getDelivery().getAddress();
			orderItems = order.getOrderItems().stream().map(orderItem -> new OrderItemDto(orderItem)).collect(Collectors.toList());
		}

		public Long getOrderId() {
			return orderId;
		}

		public String getName() {
			return name;
		}

		public LocalDateTime getOrderDate() {
			return orderDate;
		}

		public OrderStatus getOrderStatus() {
			return orderStatus;
		}

		public Address getAddress() {
			return address;
		}

		public List<OrderItemDto> getOrderItems() {
			return orderItems;
		}
	}

	public static class OrderItemDto {
		private String itemName;//상품명
		private int orderPrice; //주문 가격
		private int count; //주문 수량

		public OrderItemDto(OrderItem orderItem) {
			itemName = orderItem.getItem().getName();
			orderPrice = orderItem.getOrderPrice();
			count = orderItem.getCount();
		}

		public String getItemName() {
			return itemName;
		}

		public int getOrderPrice() {
			return orderPrice;
		}

		public int getCount() {
			return count;
		}
	}

}
