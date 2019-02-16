package jp.co.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sample.repository.OrderItemRepository;

/**
 * 注文商品の情報を削除するサービス.
 * 
 * @author igamasayuki
 *
 */
@Service
public class DeleteOrderItemService {
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	/**
	 * 注文商品をショッピングカートから削除する.
	 * 
	 * @param orderItemId 注文商品のID
	 */
	public void deleteByOrderItemId(Integer orderItemId) {
		orderItemRepository.deleteById(orderItemId);
		
	}

}
