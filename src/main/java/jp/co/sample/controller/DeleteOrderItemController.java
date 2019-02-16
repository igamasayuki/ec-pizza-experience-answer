package jp.co.sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.service.DeleteOrderItemService;

/**
 * ショッピングカートから注文商品を削除するコントローラ.
 * 
 * @author igamasayuki
 *
 */
@Controller
@RequestMapping("/")
@Transactional
public class DeleteOrderItemController {
	
	@Autowired
	private  DeleteOrderItemService service;
	
	/**
	 * 削除ボタンが押されると注文商品のIDをもとにショッピングカートから商品を削除し、注文商品一覧に戻る.
	 * 
	 * @param orderItemId
	 * @return　注文商品一覧表示画面
	 */
	@RequestMapping("/deleteByOrderItemId")
	public String deleteByOrderItemId(Integer orderItemId) {
		
		service.deleteByOrderItemId(orderItemId);
		
		return "redirect:/viewCart";
	}
	

}
