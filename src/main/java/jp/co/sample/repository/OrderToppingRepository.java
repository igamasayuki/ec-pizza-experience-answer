package jp.co.sample.repository;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.OrderTopping;

@Repository
public class OrderToppingRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	private SimpleJdbcInsert insert;
	
	/**
	 * 注文トッピングが登録されたときに採番されたIDを取得する.
	 */
	@PostConstruct
	public void init() {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert((JdbcTemplate)namedParameterJdbcTemplate.getJdbcOperations());
		SimpleJdbcInsert withTableName = simpleJdbcInsert.withTableName("order_toppings");
		insert = withTableName.usingGeneratedKeyColumns("id");
	}
	
	/**
	 * 注文トッピングの情報を登録する.
	 * 
	 * @param orderItem  注文トッピングの情報
	 * @return　IDを採番された 注文トッピングの情報
	 */
	public OrderTopping save(OrderTopping orderTopping) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(orderTopping);
		Number key = insert.executeAndReturnKey(param);
		orderTopping.setId(key.intValue());
		
		return orderTopping;
	}
	
}
