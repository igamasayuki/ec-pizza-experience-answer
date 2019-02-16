package jp.co.sample.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Item;
import jp.co.sample.service.RecommendService;

/**
 * エラーページを表示するコントローラ.
 * 
 * @author igamasayuki
 *
 */
@Controller
@RequestMapping("/")
@Transactional

public class Extra505Controller {

	@Autowired
	private RecommendService recommendService;
	
	/**
	 * お勧め商品を表示する.
	 * @param model モデル
	 * @return　エラー画面
	 */
	@RequestMapping("/404")
	public String index(Model model) {
		List<Item> itemRecommendList = recommendService.recommend();
		model.addAttribute("itemRecommendList",itemRecommendList);
		
		return "500";
	}
}
