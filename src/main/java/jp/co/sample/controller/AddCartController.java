package jp.co.sample.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.LoginUser;
import jp.co.sample.domain.User;
import jp.co.sample.form.AddCartForm;
import jp.co.sample.service.AddCartService;

/**
 * ショッピングカートに商品を追加するコントローラー.
 * 
 * @author igamasayuki
 *
 */
@Controller
@RequestMapping("/")
@Transactional
public class AddCartController {
	
	@Autowired
	private AddCartService service;
	
	@Autowired
	private HttpSession session;
	
	@ModelAttribute
	public AddCartForm setUpForm() {
		return new AddCartForm();
	}
	
	/**
	 * ショッピングカートに商品を追加する.
	 * 
	 * @param userId ユーザーのID
	 * @param addcartForm　注文する商品のリクエストパラメータが入ったフォーム
	 * @return ショッピングカートリストを表示するコントローラー.
	 */
	@RequestMapping("/addCart")
	public String addCart(@AuthenticationPrincipal LoginUser loginUser ,AddCartForm addCartForm) {
		
		//ログイン認証からユーザー情報を取得し、ユーザーIDに代入.
		//もしログインしていなかったらログイン画面へ遷移.
		
		Integer userId;
		
		if(loginUser == null) {
			userId = session.getId().hashCode();
			session.setAttribute("sessionId", userId);
		}else {
			User user = loginUser.getUser();
			userId = user.getId();
		}
		
		
		//フォームで受け取ったリクエストパラメータを対応するデータ型に変換する.
		Integer itemId = addCartForm.getIntItemId();
		Integer quantity = addCartForm.getIntQuantity();
		Character size = addCartForm.getCharSize();
		List<Integer> toppingIdList = addCartForm.getToppingIdList();


		service.addCart(itemId, userId, quantity, size, toppingIdList);
		
		return "redirect:/viewCart";
	}
	
	

}
