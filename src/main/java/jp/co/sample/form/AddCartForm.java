package jp.co.sample.form;

import java.util.List;

/**
 * 注文商品のリクエストパラメータを受け取るフォーム.
 * 
 * @author igamasayuki
 *
 */
public class AddCartForm {
	
	/** 商品のID	 */
	private String itemId;
	/** 注文商品の数量　*/
	private String quantity;
	/** 注文商品のサイズ　*/
	private String size;
	/** 注文商品のトッピング　*/
	private List<Integer> toppingIdList;
	
	public Integer getIntItemId() {
		return Integer.parseInt(itemId);
	}
	
	public Integer getIntQuantity() {
		return Integer.parseInt(quantity);
	}
	
	public Character getCharSize() {
		return size.charAt(0);
	}
	
	
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public List<Integer> getToppingIdList() {
		return toppingIdList;
	}
	public void setToppingIdList(List<Integer> toppingIdList) {
		this.toppingIdList = toppingIdList;
	}
	

}
