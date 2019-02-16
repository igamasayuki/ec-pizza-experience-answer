package jp.co.sample.domain;

import java.util.List;

/**
 * 注文商品の情報を表すドメイン.
 * 
 * @author igamasayuki
 *
 */
public class OrderItem {
	
	
	/** 注文商品のID	 */
	private Integer id;
	/** 商品のID	 */
	private Integer itemId;
	/** 注文のID	 */
	private Integer orderId;
	/** 数量	 */
	private Integer quantity;
	/** サイズ　*/
	private Character size;
	/** 商品情報　*/
	private Item item;
	/**　注文のトッピングのリスト　*/
	private List<OrderTopping> orderToppingList;
	
	
	/**
	 * 注文商品ごとの小計金額を計算する.
	 * 
	 * @return 注文商品の小計金額
	 */
	public int getSubTotal() {
		// FIXME:この場合はfinalつけて定数にしても良いかも
		int subTotal = 0;
		int orderItemPrice = 0;
		int orderToppingPrice = 0;
		int toppingPriceM = 200;
		int toppingPriceL = 300;
		
		if(this.size=='M') {
			orderItemPrice = item.getPriceM();
			orderToppingPrice = orderToppingList.size() * toppingPriceM;
		}else {
			orderItemPrice = item.getPriceL();
			orderToppingPrice = orderToppingList.size() * toppingPriceL;
		}
		
		subTotal = (orderItemPrice + orderToppingPrice)*quantity;
		
		return subTotal;
	}
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Character getSize() {
		return size;
	}
	public void setSize(Character size) {
		this.size = size;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public List<OrderTopping> getOrderToppingList() {
		return orderToppingList;
	}
	public void setOrderToppingList(List<OrderTopping> orderToppingList) {
		this.orderToppingList = orderToppingList;
	}
	
	
	
	

}
