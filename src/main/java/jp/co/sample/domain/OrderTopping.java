package jp.co.sample.domain;

/**
 * 注文しているトッピングを表すドメイン.
 * 
 * @author igamasayuki
 *
 */
public class OrderTopping {
	
	/**	注文トッピングのID */
	private Integer id;
	/**	トッピングのID */
	private Integer toppingId;
	/**	注文商品のID */
	private Integer orderItemId;
	/**	トッピング */
	private Topping topping;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getToppingId() {
		return toppingId;
	}
	public void setToppingId(Integer toppingId) {
		this.toppingId = toppingId;
	}
	public Integer getOrderItemId() {
		return orderItemId;
	}
	public void setOrderItemId(Integer orderItemId) {
		this.orderItemId = orderItemId;
	}
	public Topping getTopping() {
		return topping;
	}
	public void setTopping(Topping topping) {
		this.topping = topping;
	}
	
	

}
