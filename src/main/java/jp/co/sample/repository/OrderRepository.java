package jp.co.sample.repository;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Item;
import jp.co.sample.domain.Order;
import jp.co.sample.domain.OrderItem;
import jp.co.sample.domain.OrderTopping;
import jp.co.sample.domain.Topping;

/**
 * ordersテーブルを操作するリポジトリ.
 * 
 * @author igamasayuki
 *
 */
@Repository
public class OrderRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;
	/** ordersのテーブル名の定数 */
	public static final String TABLE_NAME = "orders";
	
	//TODO:ResultSetExtractorを無駄に二つ使っているので余裕があれば修正する
	/** 注文情報をOrderドメインに格納するResultSetExtractor */
	private static final ResultSetExtractor<Order> ORDER_RESULT_SET_EXTRACTOR = (rs) ->{
		Order order = null;
		List<OrderItem> orderItemList = null;
		boolean orderInitialProcessing = true;
		int beforeOrderItemId = 0;
		OrderItem orderItem = null;
		Item item = null;
		List<OrderTopping> orderToppingList = null;
		while(rs.next()) {
			//初回だけOrderに値をセットする
			if(orderInitialProcessing) {
				order = new Order();
				orderItemList = new ArrayList<>();
				order.setId					(rs.getInt		("order_id"));
				order.setUserId				(rs.getInt		("user_id"));
				order.setStatus				(rs.getInt		("order_status"));
				order.setTotalPrice			(rs.getInt		("order_total_price"));
				order.setOrderDate			(rs.getDate		("order_date"));
				order.setDestinationName	(rs.getString	("order_destination_name"));
				order.setDestinationEmail	(rs.getString	("order_destination_email"));
				order.setDestinationZipcode	(rs.getString	("order_destination_zipcode"));
				order.setDestinationAddress	(rs.getString	("order_destination_address"));
				order.setDestinationTel		(rs.getString	("order_destination_tel"));
				order.setDeliveryTime		(rs.getTimestamp("order_delivery_time"));
				order.setPaymentMethod		(rs.getInt		("order_payment_method"));
				order.setOrderItemList(orderItemList);
				orderInitialProcessing = false;
			}
			
			//OrderItemに値をセットする
			if(rs.getInt("order_item_id") != beforeOrderItemId) {
				orderItem = new OrderItem();
				item = new Item();
				orderToppingList = new ArrayList<>();
				orderItemList.add(orderItem);
				orderItem.setId					(rs.getInt		("order_item_id"));
				orderItem.setItemId				(rs.getInt		("item_id"));
				orderItem.setOrderId			(rs.getInt		("order_id"));
				orderItem.setQuantity			(rs.getInt		("order_item_quantity"));
				orderItem.setSize				(rs.getString	("order_item_size").toCharArray()[0]);
				orderItem.setItem				(item);
				orderItem.setOrderToppingList	(orderToppingList);
				//Itemに値をセットする
				item.setId			(rs.getInt		("item_id"));
				item.setName		(rs.getString	("item_name"));
				item.setDescription	(rs.getString	("item_description"));
				item.setPriceM		(rs.getInt		("item_price_m"));
				item.setPriceL		(rs.getInt		("item_price_l"));
				item.setImagePath	(rs.getString	("item_image_path"));
				item.setDeleted		(rs.getBoolean	("item_deleted"));
			}
			
			
			//OrderToppingに値をセットする
			if(rs.getInt("order_topping_id") != 0) {
				OrderTopping orderTopping = new OrderTopping();
				Topping topping = new Topping();
				orderToppingList.add(orderTopping);
				orderTopping.setId			(rs.getInt("order_topping_id"));
				orderTopping.setToppingId	(rs.getInt("topping_id"));
				orderTopping.setOrderItemId	(rs.getInt("order_item_id"));
				orderTopping.setTopping		(topping);
				
				//Toppingに値をセットする
				topping.setId		(rs.getInt		("topping_id"));
				topping.setName		(rs.getString	("topping_name"));
				topping.setPriceM	(rs.getInt		("topping_price_m"));
				topping.setPriceL	(rs.getInt		("topping_price_l"));
			}
				
			//前のOrderItemのIdを保持する
			beforeOrderItemId = rs.getInt("order_item_id");
		}
		
		return order;
	};
	
	/** 注文履歴をOrderのListに格納するResultSetExtractor */
	private static final ResultSetExtractor<List<Order>> ORDER_HISTORY_RESULT_SET_EXTRACTOR = (rs) ->{
		List<Order> orders = new ArrayList<>();
		Order order = null;
		List<OrderItem> orderItemList = null;
		int beforeOrderId = 0;
		int beforeOrderItemId = 0;
		OrderItem orderItem = null;
		Item item = null;
		List<OrderTopping> orderToppingList = null;
		while(rs.next()) {
			//初回だけOrderに値をセットする
			if(beforeOrderId != rs.getInt("order_id")) {
				order = new Order();
				orders.add(order);
				orderItemList = new ArrayList<>();
				order.setId					(rs.getInt		("order_id"));
				order.setUserId				(rs.getInt		("user_id"));
				order.setStatus				(rs.getInt		("order_status"));
				order.setTotalPrice			(rs.getInt		("order_total_price"));
				order.setOrderDate			(rs.getDate		("order_date"));
				order.setDestinationName	(rs.getString	("order_destination_name"));
				order.setDestinationEmail	(rs.getString	("order_destination_email"));
				order.setDestinationZipcode	(rs.getString	("order_destination_zipcode"));
				order.setDestinationAddress	(rs.getString	("order_destination_address"));
				order.setDestinationTel		(rs.getString	("order_destination_tel"));
				order.setDeliveryTime		(rs.getTimestamp("order_delivery_time"));
				order.setPaymentMethod		(rs.getInt		("order_payment_method"));
				order.setOrderItemList(orderItemList);
			}
			
			//OrderItemに値をセットする
			if(rs.getInt("order_item_id") != beforeOrderItemId) {
				orderItem = new OrderItem();
				item = new Item();
				orderToppingList = new ArrayList<>();
				orderItemList.add(orderItem);
				orderItem.setId					(rs.getInt		("order_item_id"));
				orderItem.setItemId				(rs.getInt		("item_id"));
				orderItem.setOrderId			(rs.getInt		("order_id"));
				orderItem.setQuantity			(rs.getInt		("order_item_quantity"));
				orderItem.setSize				(rs.getString	("order_item_size").toCharArray()[0]);
				orderItem.setItem				(item);
				orderItem.setOrderToppingList	(orderToppingList);
				//Itemに値をセットする
				item.setId			(rs.getInt		("item_id"));
				item.setName		(rs.getString	("item_name"));
				item.setDescription	(rs.getString	("item_description"));
				item.setPriceM		(rs.getInt		("item_price_m"));
				item.setPriceL		(rs.getInt		("item_price_l"));
				item.setImagePath	(rs.getString	("item_image_path"));
				item.setDeleted		(rs.getBoolean	("item_deleted"));
			}
			
			
			//OrderToppingに値をセットする
			if(rs.getInt("order_topping_id") != 0) {
				OrderTopping orderTopping = new OrderTopping();
				Topping topping = new Topping();
				orderToppingList.add(orderTopping);
				orderTopping.setId			(rs.getInt("order_topping_id"));
				orderTopping.setToppingId	(rs.getInt("topping_id"));
				orderTopping.setOrderItemId	(rs.getInt("order_item_id"));
				orderTopping.setTopping		(topping);
				
				//Toppingに値をセットする
				topping.setId		(rs.getInt		("topping_id"));
				topping.setName		(rs.getString	("topping_name"));
				topping.setPriceM	(rs.getInt		("topping_price_m"));
				topping.setPriceL	(rs.getInt		("topping_price_l"));
			}
			
			
			//前のOrderのIdを保持する
			beforeOrderId = rs.getInt("order_id");
			//前のOrderItemのIdを保持する
			beforeOrderItemId = rs.getInt("order_item_id");
		}
		
		return orders;
	};
	
	/**
	 * 注文情報を取得する.
	 * userIdとstatusを使用して注文情報を取得する
	 * 
	 * @param userId ユーザID
	 * @param status 注文の状態
	 * @return 取得した注文情報を格納したOrderオブジェクト
	 * 注文が見つからなかった場合null
	 */
	public Order findByUserIdAndStatus(Integer userId,Integer status){
		String sql = "SELECT o.id order_id,o.user_id user_id,o.status order_status,o.total_price order_total_price,"
				+ "o.order_date order_date,o.destination_name order_destination_name,o.destination_email order_destination_email,"
				+ "o.destination_zipcode order_destination_zipcode,o.destination_address order_destination_address,"
				+ "o.destination_tel order_destination_tel,o.delivery_time order_delivery_time,o.payment_method order_payment_method,"
				+ "oi.id order_item_id,oi.item_id item_id,oi.quantity order_item_quantity,oi.size order_item_size,i.name item_name,"
				+ "i.description item_description,i.price_m item_price_m,i.price_l item_price_l,i.image_path item_image_path,"
				+ "i.deleted item_deleted,ot.id order_topping_id,ot.topping_id topping_id,t.name topping_name,t.price_m topping_price_m,"
				+ "t.price_l topping_price_l FROM orders o JOIN order_items oi ON o.id = oi.order_id "
				+ "LEFT OUTER JOIN order_toppings ot ON oi.id = ot.order_item_id INNER JOIN items i ON oi.item_id = i.id "
				+ "LEFT OUTER JOIN toppings t ON ot.topping_id = t.id WHERE o.user_id=:user_id AND o.status=:status ORDER BY oi.id ,t.name; ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("user_id", userId).addValue("status", status);
		Order order = template.query(sql, param, ORDER_RESULT_SET_EXTRACTOR);
		return order;
	}

	/**
	 * 注文履歴を取得する.
	 * userIdとstatusのリストを使用して注文情報を取得する
	 * 
	 * @param userId ユーザID
	 * @param statusList 注文の状態
	 * @return 取得した注文情報を格納したList<Order>オブジェクト
	 * 注文が見つからなかった場合null
	 */
	public List<Order> findByUserIdAndStatusList(Integer userId,List<Integer> statusList){
		String sql = "SELECT o.id order_id,o.user_id user_id,o.status order_status,o.total_price order_total_price,"
				+ "o.order_date order_date,o.destination_name order_destination_name,o.destination_email order_destination_email,"
				+ "o.destination_zipcode order_destination_zipcode,o.destination_address order_destination_address,"
				+ "o.destination_tel order_destination_tel,o.delivery_time order_delivery_time,o.payment_method order_payment_method,"
				+ "oi.id order_item_id,oi.item_id item_id,oi.quantity order_item_quantity,oi.size order_item_size,i.name item_name,"
				+ "i.description item_description,i.price_m item_price_m,i.price_l item_price_l,i.image_path item_image_path,"
				+ "i.deleted item_deleted,ot.id order_topping_id,ot.topping_id topping_id,t.name topping_name,t.price_m topping_price_m,"
				+ "t.price_l topping_price_l FROM orders o JOIN order_items oi ON o.id = oi.order_id "
				+ "LEFT OUTER JOIN order_toppings ot ON oi.id = ot.order_item_id INNER JOIN items i ON oi.item_id = i.id "
				+ "LEFT OUTER JOIN toppings t ON ot.topping_id = t.id WHERE o.user_id=:user_id";
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource().addValue("user_id", userId);
		// FIXME:これで実現できたのはすごい！ただ、後から手を入れづらいBチームのコードがシンプルに書けているので参考にしてみてください
		for(int i = 1;i <= statusList.size();i++) {
			if(i == 1) {
				sql = sql + " AND ( o.status=:status"+i;
			}else {
				sql = sql + " OR o.status=:status"+i;				
			}
			mapSqlParameterSource.addValue("status"+i, statusList.get(i-1));
		}
		sql = sql + " ) ORDER BY o.order_date DESC,o.id DESC,i.name; ";
		SqlParameterSource param = mapSqlParameterSource;
		List<Order> orders = template.query(sql, param, ORDER_HISTORY_RESULT_SET_EXTRACTOR);
		if(orders.isEmpty()) {
			return null;
		}
		return orders;
	}
	
	private SimpleJdbcInsert insert;
	
	/**
	 * initメソッド.
	 * 
	 * テーブルにinsertするためのメソッド
	 */
	@PostConstruct
	public void init() {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert((JdbcTemplate)template.getJdbcOperations());
		SimpleJdbcInsert withTableName = simpleJdbcInsert.withTableName(TABLE_NAME);
		insert = withTableName.usingGeneratedKeyColumns("id");
	}
	
	/**
	 * saveメソッド.
	 * 
	 * @param order insert,updateするOrderドメイン.
	 * @return insertの場合は自動採番されたidが入ったOrderドメイン、updateの場合は引数をそのまま返す
	 */
	public Order save(Order order) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(order);
		if(order.getId() == null) {
			//ordersテーブルのINSERT処理
			Number key = insert.executeAndReturnKey(param);
			order.setId(key.intValue());
		}else {
			//ordersテーブルのUPDATE処理
			String sql = "UPDATE "+TABLE_NAME+" SET user_id=:userId,status=:status,total_price=:totalPrice,order_date=:orderDate,"
					+ "destination_name=:destinationName,destination_email=:destinationEmail,destination_zipcode=:destinationZipcode,"
					+ "destination_address=:destinationAddress,destination_tel=:destinationTel,delivery_time=:deliveryTime,"
					+ "payment_method=:paymentMethod WHERE id=:id;";
			template.update(sql, param);
		}
		return order;
	}
	
	/**
	 * deleteメソッド.
	 * 
	 * @param id
	 */
	public void deleteById(Integer id) {
		String sql = "DELETE FROM orders WHERE id=:id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}
}
