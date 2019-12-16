package jpabook.jpashop.domain;

public class OrderSearch {

	public static final OrderSearch EMPTY = new OrderSearch();

	private OrderStatus orderStatus = null;

	private String memberName = null;

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

}
