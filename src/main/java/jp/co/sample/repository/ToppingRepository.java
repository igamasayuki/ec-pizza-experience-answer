package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Topping;

/**
 * toppingsテーブルを操作するリポジトリクラス.
 * 
 * @author igamasayuki
 *
 */
@Repository
public class ToppingRepository {

	/**
	 * ResultSetオブジェクトからToppingsオブジェクトに変換するためのクラス実装&インスタンス化.
	 */
	@Autowired
	private NamedParameterJdbcTemplate template;

	
	private static final RowMapper<Topping> TOPPING_ROW_MAPPER = (rs,i) -> {
		Integer id = rs.getInt("id");
		String name = rs.getString("name");
		Integer priceM = rs.getInt("price_m");
		Integer priceL = rs.getInt("price_l");
		
		return new Topping(id,name,priceM,priceL);
	};
	
	/**
	 * トッピングの全情報を取得する.
	 * 
	 * @return 取得したトッピングの情報
	 */
	public List<Topping> findAll(){
		List<Topping> toppingList = template.query(
				"SELECT id,name,price_m,price_l FROM toppings ORDER BY name DESC",
				TOPPING_ROW_MAPPER);
		return toppingList;
	}

}
