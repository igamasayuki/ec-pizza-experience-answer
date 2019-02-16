package jp.co.sample.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Item;
import jp.co.sample.domain.LoginUser;
import jp.co.sample.domain.Order;
import jp.co.sample.domain.User;
import jp.co.sample.service.ChangeQuantityService;
import jp.co.sample.service.RecommendService;
import jp.co.sample.service.ShoppingCartBadgeService;
import jp.co.sample.service.ViewCartService;

/**
 * ショッピングカートの中身を表示するコントローラー
 * 
 * @author igamasayuki
 *
 */
@Controller
@RequestMapping("/")
@Transactional
public class ViewCartController {
	
	@Autowired
	private ViewCartService service;
	
	@Autowired
	private RecommendService recommendService;

	@Autowired
	private ShoppingCartBadgeService shoppingCartBadgeService;
	
	@Autowired
	private ChangeQuantityService changeQuantityService;
	
	@Autowired
	private HttpSession session;
	
	/**
	 * ユーザーIDをもとに、ショッピングカートの中身（未購入状態のも）の一覧を表示する.
	 * 
	 * @param userId ユーザーＩＤ
	 * @param status　購入状態
	 * @param model　リクエストスコープ
	 * @return　ショッピングカートの中身を表示するJSP
	 */
	@RequestMapping("/viewCart")
	public String viewCart(@AuthenticationPrincipal LoginUser loginUser ,Integer status,Model model) {
		
		
		//ログイン認証からユーザー情報を取得し、ユーザーIDに代入.
		Integer userId;
		
		if(loginUser == null) {
			userId = session.getId().hashCode();
		}else {
			User user = loginUser.getUser();
			userId = user.getId();
		}
		
		//未購入の注文情報を指定.
		status = 0;
		
		Order order = service.viewCart(userId, status);
		if(order != null) {
			Integer cartCount = shoppingCartBadgeService.countByOrderId(order.getId());
			model.addAttribute("cartCount", cartCount);
		}
		model.addAttribute("order",order);
		
		//未購入時、ランダムでおすすめ商品を取得.
		List<Item> itemRecommendList = recommendService.recommend();
		model.addAttribute("itemRecommendList",itemRecommendList);
		
		
		return "cartList";
		
	}
	
	/**
	 * 注文商品の個数を変更した後、更新されたカートを表示する.
	 * 
	 * @param orderId 注文情報のID
	 * @param orderItemId　注文商品のID
	 * @param quantity　注文商品の数量
	 * @return　カートを表示するコントローラー
	 */
	@RequestMapping("/updateQuantity")
	public String updateQuantity(String orderId, String orderItemId, String quantity) {
		System.out.println("test");
		Integer orderId2 = Integer.parseInt(orderId);
		Integer orderItemId2 = Integer.parseInt(orderItemId);
		Integer quantity2 = Integer.parseInt(quantity);
		
		changeQuantityService.updateQuantity(orderId2, orderItemId2, quantity2);
		
		return "redirect:/viewCart";
		
	}
	

}
