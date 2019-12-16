package jpabook.jpashop.repository.query;

import java.time.LocalDateTime;
import java.util.List;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.OrderStatus;

public class OrderQueryDto {
	private Long orderId;
	private String name;
	private LocalDateTime orderDate; //주문시간
	private OrderStatus orderStatus;
	private Address address;
	private List<OrderItemQueryDto> orderItems;

	public OrderQueryDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address) {
		this.orderId = orderId;
		this.name = name;
		this.orderDate = orderDate;
		this.orderStatus = orderStatus;
		this.address = address;
	}

	public OrderQueryDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address, List<OrderItemQueryDto> orderItems) {
		this(orderId, name, orderDate, orderStatus, address);
		this.orderItems = orderItems;
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

	public List<OrderItemQueryDto> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItemQueryDto> orderItems) {
		this.orderItems = orderItems;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderQueryDto other = (OrderQueryDto) obj;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		return true;
	}

}
