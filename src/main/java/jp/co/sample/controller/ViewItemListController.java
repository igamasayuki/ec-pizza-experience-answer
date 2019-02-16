package jp.co.sample.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Item;
import jp.co.sample.domain.LoginUser;
import jp.co.sample.domain.Order;
import jp.co.sample.domain.User;
import jp.co.sample.form.SearchItemForm;
import jp.co.sample.service.ShoppingCartBadgeService;
import jp.co.sample.service.ViewCartService;
import jp.co.sample.service.ViewItemListService;

/**
 * itemの一覧表示を行うコントローラー
 * 
 * @author igamasayuki
 *
 */
@Controller
@RequestMapping("/viewItemList")
@Transactional
public class ViewItemListController {

	@Autowired
	private  ViewItemListService viewItemListService;
	
	@Autowired
	private ViewCartService service;
	
	@Autowired
	private ShoppingCartBadgeService shoppingCartBadgeService;
	
	@Autowired
	private HttpSession session;

	@ModelAttribute
	public SearchItemForm setUpForm() {
		return new SearchItemForm();
	}

	/**
	 * itemの全情報を取得し、画面に表示する.
	 * @param model モデル
	 * @return item情報表示画面
	 */
	@RequestMapping("/list")
	public String list(Model model,@AuthenticationPrincipal LoginUser loginUser) {
		// データベースから商品一覧を検索してitemListという名前でデータ格納領域に入れる処理
		model.addAttribute("itemList", viewItemListService.findAll());
		Integer userId;
		
		if(loginUser == null) {
			userId = session.getId().hashCode();
		}else {
			User user = loginUser.getUser();
			userId = user.getId();
		}
		
		//未購入の注文情報を指定.
		Integer status = 0;
		
		Order order = service.viewCart(userId, status);
		if(order != null) {
			Integer cartCount = shoppingCartBadgeService.countByOrderId(order.getId());
			model.addAttribute("cartCount", cartCount);
		}
	
		return "itemList";
	}

}
