package jp.co.sample.domain;

import java.util.List;

/**
 * 商品一覧を表示するドメイン.
 * @author igamasayuki
 *
 */
public class Item {
	/**商品ID*/
	private Integer id;
	
	/**商品名*/
	private String name;
	
	/**商品説明*/
	private String description;
	
	/**Mサイズの価格*/
	private Integer priceM;
	
	/**Lサイズの価格*/
	private Integer priceL;
	
	/**商品写真のパス*/
	private String imagePath;
	
	/**削除フラグ*/
	private Boolean deleted;
	
	/**トッピングのリスト*/
	private List<Topping> toppingList;
	
	public Item() {}
	public Item(Integer id, String name, String description, Integer priceM, Integer priceL, String imagePath,
			Boolean deleted) {
		this.id=id;
		this.name=name;
		this.description=description;
		this.priceM=priceM;
		this.priceL=priceL;
		this.imagePath=imagePath;
		this.deleted=deleted;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getPriceM() {
		return priceM;
	}
	public void setPriceM(Integer priceM) {
		this.priceM = priceM;
	}
	public Integer getPriceL() {
		return priceL;
	}
	public void setPriceL(Integer priceL) {
		this.priceL = priceL;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public List<Topping> getToppingList() {
		return toppingList;
	}
	public void setToppingList(List<Topping> toppingList) {
		this.toppingList = toppingList;
	}
}
