package jp.co.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sample.repository.OrderItemRepository;

@Service
public class ChangeQuantityService {
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	public void updateQuantity(Integer orderId2,Integer orderItemId2,Integer quantity) {
		orderItemRepository.updateQuantity(orderId2, orderItemId2, quantity);
	}
	

}
