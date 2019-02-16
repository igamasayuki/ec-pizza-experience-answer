package jp.co.sample.domain;

/**
 * トッピングを表すドメイン.
 * 
 * @author igamasayuki
 *
 */
public class Topping {
	
	/**	トッピングのID */
	private Integer id;
	/**	トッピングの名前 */
	private String name;
	/**	Mサイズのトッピング価格 */
	private Integer priceM;
	/**	Lサイズのトッピング価格 */
	private Integer priceL;
	
	public Topping() {}
	public Topping(Integer id, String name, Integer priceM, Integer priceL) {
		this.id=id;
		this.name=name;
		this.priceM=priceM;
		this.priceL=priceL;
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
	

}
