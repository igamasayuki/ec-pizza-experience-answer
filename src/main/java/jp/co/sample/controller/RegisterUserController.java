package jp.co.sample.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.LoginUser;
import jp.co.sample.domain.Order;
import jp.co.sample.domain.User;
import jp.co.sample.form.RegisterUserForm;
import jp.co.sample.service.RegisterUserService;
import jp.co.sample.service.ShoppingCartBadgeService;
import jp.co.sample.service.ViewCartService;

/**
 * ユーザー関連処理を行うコントローラー.
 * 
 * @author igamasayuki
 *
 */
@Controller
@RequestMapping("/registeruser")
//FIXME:javadoc漏れ
public class RegisterUserController {

	@Autowired
	private RegisterUserService registerUserService;

	@Autowired
	private ViewCartService service;
	
	@Autowired
	private ShoppingCartBadgeService shoppingCartBadgeService;
	
	@Autowired
	private HttpSession session;
	
	/**
	 * フォームを初期化する.
	 * @return　フォーム
	 */
	@ModelAttribute
	public RegisterUserForm setUpForm() {
		return new RegisterUserForm();
	}
	
	
	/**
	 * メンバー情報登録画面を表示する.
	 * @param model 
	 * @return　メンバー情報登録画面
	 */
	@RequestMapping("/form")
	public String form(Model model,@AuthenticationPrincipal LoginUser loginUser) {
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
		return "registeruser";
	}
	
	
	
	/**
	 * ユーザー情報を登録する.
	 * @param form　　　フォーム
	 * @param result　リザルト
	 * @param model　　モデル
	 * @return　　　　　　ログイン画面
	 */
	@RequestMapping("/create")
	public String create(@Validated RegisterUserForm form,
								BindingResult result,
								Model model,
								@AuthenticationPrincipal LoginUser loginUser) {
		
		
		
		
		//パスワード確認
		if(!form.getPassword().equals(form.getCheckPassword())) {
			result.rejectValue("checkPassword", "","パスワードと入力が異なります");
		}
		
		
		//メールアドレスが重複している場合の処理
		User checkUser = registerUserService.findByEmail(form.getEmail());
		if(checkUser != null) {
			result.rejectValue("email", "","すでにメールアドレスが登録されています");
		}
		
		
		if(result.hasErrors()) {
			return form(model,loginUser);
		}
		
		
		//パスワードの暗号化し、formに設定
		form.setPassword(registerUserService.encodePassword(form.getPassword()));
		
		
		//フォームの内容をドメインに格納
		User user = new User();
		BeanUtils.copyProperties(form, user);
		user = registerUserService.save(user);
		
		return "redirect:/";
	}
	

	
}
