package jpabook.jpashop.repository.query;

import java.time.LocalDateTime;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.OrderStatus;

public class OrderFlatDto {
	private Long orderId;
	private String name;
	private LocalDateTime orderDate; //주문시간
	private Address address;
	private OrderStatus orderStatus;
	private String itemName;//상품 명
	private int orderPrice; //주문 가격
	private int count; //주문 수량

	public OrderFlatDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address, String itemName, int orderPrice,
			int count) {
		this.orderId = orderId;
		this.name = name;
		this.orderDate = orderDate;
		this.orderStatus = orderStatus;
		this.address = address;
		this.itemName = itemName;
		this.orderPrice = orderPrice;
		this.count = count;
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

	public Address getAddress() {
		return address;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
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
