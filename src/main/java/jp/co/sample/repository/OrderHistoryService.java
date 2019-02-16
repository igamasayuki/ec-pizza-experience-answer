package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sample.domain.Order;

/**
 * 注文履歴を検索するサービス.
 * 
 * @author igamasayuki
 *
 */
@Service
public class OrderHistoryService {
	@Autowired
	private OrderRepository orderRepository;
	
	/**
	 * 注文履歴を検索する.
	 * 
	 * @param userId ユーザID
	 * @param statusList 注文の状態
	 * @return 取得した注文情報を格納したList<Order>オブジェクト
	 * 注文が見つからなかった場合null
	 */
	public List<Order> findByUserIdAndStatusList(Integer userId,List<Integer> statusList){
		return orderRepository.findByUserIdAndStatusList(userId, statusList);
	}
}
