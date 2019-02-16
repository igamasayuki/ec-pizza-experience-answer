package jp.co.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sample.domain.Order;
import jp.co.sample.repository.OrderRepository;

/**
 * ショッピングカートの中身を表示するサービス.
 * 
 * @author igamasayuki
 *
 */
@Service
public class ViewCartService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	/**
	 * 注文商品情報の詰まった注文情報を呼び出す.
	 * 
	 * @param userId ユーザーID
	 * @param status　注文状態（未購入で固定）
	 * @return　注文情報
	 */
	public Order viewCart(Integer userId , int status) {
		
		Order order = orderRepository.findByUserIdAndStatus(userId, status);
		
		return order;
	}
	

}
