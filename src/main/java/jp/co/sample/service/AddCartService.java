package jp.co.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sample.domain.Order;
import jp.co.sample.domain.OrderItem;
import jp.co.sample.domain.OrderTopping;
import jp.co.sample.repository.OrderItemRepository;
import jp.co.sample.repository.OrderRepository;
import jp.co.sample.repository.OrderToppingRepository;

/**
 * ショッピングカートに商品を追加するサービスクラス.
 * 
 * @author igamasayuki
 *
 */
@Service
public class AddCartService {

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private OrderToppingRepository orderToppingRepository;

	@Autowired
	private OrderRepository orderRepository;

	/**
	 * ショッピングカートに商品を追加する.
	 * 
	 * @param itemId
	 *            商品のID
	 * @param userId
	 *            ユーザーのID
	 * @param quantity
	 *            注文商品の数量
	 * @param size
	 *            注文商品のサイズ
	 * @param toppingIdList
	 *            注文トッピングのIDの詰まったリスト
	 */
	public void addCart(Integer itemId, Integer userId, Integer quantity, Character size, List<Integer> toppingIdList) {

		Integer status = 0;
		
		// ユーザーIDと注文ステータスが未購入のものを検索
		Integer orderId = null;
		
		Order order = orderRepository.findByUserIdAndStatus(userId, status);
		
		int totalPrice = 0;

		// 注文情報がない場合は、新しい注文クラスを作成し、採番された注文のＩＤを取得する.
		// 注文情報がある場合は、すで採番されている注文情報のＩＤを取得する.
		if (order == null) {
			Order newOrder = new Order();
			newOrder.setUserId(userId);
			newOrder.setStatus(status);
			newOrder.setTotalPrice(totalPrice);
			orderId = orderRepository.save(newOrder).getId();
		} else {
			orderId = order.getId();
		}

		// 注文商品の情報を登録し、採番された注文情報のＩＤを取得する.
		OrderItem orderItem = new OrderItem();
		orderItem.setItemId(itemId);
		orderItem.setOrderId(orderId);
		orderItem.setQuantity(quantity);
		orderItem.setSize(size);
		orderItem.setId(orderItemRepository.save(orderItem).getId());

		// 注文トッピングの情報を登録する.
		for (Integer toppingId : toppingIdList) {
			OrderTopping orderTopping = new OrderTopping();
			orderTopping.setToppingId(toppingId);
			orderTopping.setOrderItemId(orderItem.getId());
			orderTopping.setId(orderToppingRepository.save(orderTopping).getId());
		}

	}

}
