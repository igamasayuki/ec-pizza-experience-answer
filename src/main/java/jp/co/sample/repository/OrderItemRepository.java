package jp.co.sample.repository;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.OrderItem;

/**
 * 注文商品のリポジトリ.
 * 
 * @author igamasayuki
 *
 */
@Repository
public class OrderItemRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	private SimpleJdbcInsert insert;
	
	/**
	 * 注文商品が登録されたときに採番されたIDを取得する.
	 */
	@PostConstruct
	public void init() {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert((JdbcTemplate)namedParameterJdbcTemplate.getJdbcOperations());
		SimpleJdbcInsert withTableName = simpleJdbcInsert.withTableName("order_items");
		insert = withTableName.usingGeneratedKeyColumns("id");
	}
	
	/**
	 * 注文商品の情報を登録する.
	 * 
	 * @param orderItem 注文商品の情報
	 * @return　IDを採番された注文商品の情報
	 */
	public OrderItem save (OrderItem orderItem) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(orderItem);
		Number key = insert.executeAndReturnKey(param);
		orderItem.setId(key.intValue());

		return orderItem;
	}
	
	/**
	 * 注文商品の情報を削除する.
	 * 
	 * @param OrderItemId 注文商品のID
	 */
	public void deleteById (Integer orderItemId) {
		String sql = "WITH deleted AS (DELETE FROM order_items WHERE id = :id RETURNING id)\r\n"
					+ "DELETE FROM order_toppings WHERE id IN (SELECT id FROM deleted);";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", orderItemId);
		namedParameterJdbcTemplate.update(sql, param);
	}
	
	/**
	 * OrderIdをアップデートする.
	 * 
	 * @param beforeOrderId 前のOrderId
	 * @param afterOrderId 変更するOrderId
	 */
	public void updateToOrderId(Integer beforeOrderId,Integer afterOrderId) {
		String sql = "UPDATE order_items SET order_id=:afterOrderId WHERE order_id=:beforeOrderId;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("beforeOrderId", beforeOrderId).addValue("afterOrderId", afterOrderId);
		namedParameterJdbcTemplate.update(sql, param);
	}
	
	/**
	 * バッジにするためのカートの商品数を検索する.
	 * 
	 * @param orderId OrderのId
	 * @return カートの商品数
	 */
	public Integer countByOrderId(Integer orderId) {
		String sql = "SELECT count(*) FROM order_items WHERE order_id=:orderId;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("orderId", orderId);
		Integer cartCount = namedParameterJdbcTemplate.queryForObject(sql, param, Integer.class);
		return cartCount;
	}
	
	/**
	 * 注文情報のIDと注文商品のIDが一致した、注文商品の数量を変更する.
	 * 
	 * @param orderId2 注文情報のID
	 * @param orderItemId2　注文商品のID
	 * @param quantity2　注文商品の数量
	 */
	public void updateQuantity(Integer orderId2,Integer orderItemId2,Integer quantity2) {
		String sql = "UPDATE order_items SET quantity = :quantity WHERE id = :id AND order_id = :orderId;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("orderId", orderId2).addValue("id", orderItemId2).addValue("quantity", quantity2);
		namedParameterJdbcTemplate.update(sql, param);
		
	}
	
	
}
