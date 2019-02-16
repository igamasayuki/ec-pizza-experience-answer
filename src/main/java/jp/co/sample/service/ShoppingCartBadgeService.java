package jp.co.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sample.repository.OrderItemRepository;

@Service
//FIXME:javadoc漏れ
public class ShoppingCartBadgeService {
	@Autowired
	private OrderItemRepository orederItemRepository;
	
	public Integer countByOrderId(Integer orderId) {
		return orederItemRepository.countByOrderId(orderId);
	}
}
