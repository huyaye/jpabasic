package jpabook.jpashop.api.dto;

import java.time.LocalDateTime;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
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

}
