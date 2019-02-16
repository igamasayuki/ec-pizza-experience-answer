package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Item;

/**
 * Itemテーブル操作用のリポジトリ.
 * 
 * @author igamasayuki
 *
 */
@Repository
public class ItemRepository {
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


	/**
	 * ResultSetオブジェクトからItemオブジェクトに変換するためのクラス実装&インスタンス化.
	 */
	private static final RowMapper<Item> ITEM_ROW_MAPPER = (rs, i) -> {
	Integer id = rs.getInt("id");
	String name = rs.getString("name");
	String description = rs.getString("description");
	Integer priceM = rs.getInt("price_m");
	Integer priceL = rs.getInt("price_l");
	String imagePath = rs.getString("image_path");
	Boolean deleted = rs.getBoolean("deleted");
	return new Item(id, name, description, priceM, priceL, imagePath, deleted);

};



	/**
	 * 商品の全情報を取得する
	 * 
	 * @return 取得したピザの情報
	 */
	public List<Item> findAll() {
		List<Item> items = namedParameterJdbcTemplate.query(
				"SELECT id,name,description,price_m,price_l,image_path,deleted FROM items ORDER BY price_m DESC",
				ITEM_ROW_MAPPER);
		return items;
	}

	/**
	 * 名前から商品を(曖昧)検索する.
	 * 
	 * @param name
	 *            商品の名前
	 * @return 検索された商品の情報
	 */
	public List<Item> findByName(String name) {
		String sql = "SELECT id,name,description,price_m,price_l,image_path,deleted FROM items WHERE name LIKE :name ORDER BY name DESC";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%");

		List<Item> itemList = namedParameterJdbcTemplate.query(sql, param, ITEM_ROW_MAPPER);
		return itemList;

	}

	/**
	 * idから商品を1件検索する.
	 * 
	 * @param id
	 *            商品id
	 * @return
	 */
	public Item load(Integer id) {
		String sql="SELECT id,name,description,price_m,price_l,image_path,deleted FROM items WHERE id= :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		List<Item> itemList=namedParameterJdbcTemplate.query(sql, param, ITEM_ROW_MAPPER);
		
		if(itemList.size()==0) {
			return null;
		}
		
		return itemList.get(0);
		
	}
	


}
