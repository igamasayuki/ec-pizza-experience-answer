package jp.co.sample.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

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
import jp.co.sample.form.OrderDestinationForm;
import jp.co.sample.service.OrderService;
import jp.co.sample.service.ShoppingCartBadgeService;

/**
 * 注文処理を行うコントローラ.
 * 
 * @author igamasayuki
 *
 */
@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private ShoppingCartBadgeService shoppingCartBadgeService;
	
	//未注文のstatus
	public Integer UNORDERED_ID = 0;
	
	@ModelAttribute
	public OrderDestinationForm setUpForm() {
		return new OrderDestinationForm();
	}
	
	/**
	 * 注文情報を表示する.
	 * 注文情報をuseridとstatus(0:未注文)で調べてmodelに格納する
	 * 
	 * @param model　モデル
	 * @return 注文表示画面
	 */
	@RequestMapping("/orderconfirm")
	public String index(Model model,@AuthenticationPrincipal LoginUser loginUser,boolean error){
		Integer status = UNORDERED_ID; 
		//ログイン中のユーザを取得する
		User user = loginUser.getUser();
		Integer userId = user.getId();
		
		Order order = orderService.findByUserIdAndStatus(userId, status);
		Integer cartCount = shoppingCartBadgeService.countByOrderId(order.getId());
		model.addAttribute("cartCount", cartCount);
		model.addAttribute("order", order);
		if(!error) {
			model.addAttribute("user", user);
		}
		return "orderconfirm";
	}
	
	/**
	 * 注文する.
	 * 
	 * @param form 配達先情報が入ったフォーム
	 * @param result リザルト
	 * @param loginUser ログインユーザ情報
	 * @return 注文完了画面
	 * 入力フォームのエラーが起きたときは確認画面に戻す
	 */
	@RequestMapping("/order")
	public String order(@Validated OrderDestinationForm form,
									BindingResult result,Model model,
									@AuthenticationPrincipal LoginUser loginUser) {
		Timestamp timestamp = null;
		if(result.getFieldErrorCount("deliveryDate") == 0) {
			timestamp = orderService.stringToTimestamp(form.getDeliveryDate()+","+form.getDeliveryTime());
			LocalDateTime localDateTime = LocalDateTime.now();
			Timestamp nowTimestampPlusOneHour = Timestamp.valueOf(localDateTime.plusHours(1));
			if(!nowTimestampPlusOneHour.before(timestamp)) {
				result.rejectValue("deliveryDate", null, "配達日時は現時刻の1時間以降を指定してください");
			}
		}
		if(result.hasErrors()) {
			return index(model,loginUser,true);
		}
		
		Integer status = UNORDERED_ID;
		//ログイン中のユーザを取得する
		User user = loginUser.getUser();
		Integer userId = user.getId();
		
		Order order = orderService.findByUserIdAndStatus(userId, status);

		//formの内容をorderに詰める
		order.setStatus(Integer.valueOf(form.getPaymentMethod()));
		order.setTotalPrice(order.getCalcTotalPrice());
		order.setOrderDate(new Date());
		order.setDestinationName(form.getDestinationName());
		order.setDestinationEmail(form.getDestinationEmail());
		order.setDestinationZipcode(form.getDestinationZipcode());
		order.setDestinationAddress(form.getDestinationAddress());
		order.setDestinationTel(form.getDestinationTel());
		order.setDeliveryTime(timestamp);
		order.setPaymentMethod(Integer.valueOf(form.getPaymentMethod()));
		
		orderService.update(order);
		
		//注文完了画面にリダイレクトで遷移
		return "redirect:/order/finish";
	}
	
	/**
	 * 注文完了画面を表示する.
	 * 
	 * @return 注文完了画面
	 */
	@RequestMapping("/finish")
	public String orderFinished() {
		return "orderfinished";
	}
}
