package jp.co.sample.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
 * 	商品の検索を行うコントローラー.
 * 
 * @author igamasayuki
 *
 */
@Controller
@Transactional
@RequestMapping("/SearchItem")
public class SearchItemController {
	
	@Autowired
	private ViewItemListService viewItemListService;
	
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
	
	@RequestMapping("/search")
	public String searchItem(Model model,@Validated SearchItemForm searchItemForm, BindingResult result,@AuthenticationPrincipal LoginUser loginUser) {
		
		
		List<Item> itemList = new ArrayList<>();
		itemList =viewItemListService.findByName(searchItemForm.getName());
		
		if(itemList.size() == 0) {
			result.rejectValue("name", null, "該当する商品がありません");
			itemList = viewItemListService.findAll();

		}

		model.addAttribute("itemList", itemList);
		//ログイン認証からユーザー情報を取得し、ユーザーIDに代入.
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
