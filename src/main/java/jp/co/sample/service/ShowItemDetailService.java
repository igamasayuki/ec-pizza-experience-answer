package jp.co.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sample.domain.Item;
import jp.co.sample.domain.Topping;
import jp.co.sample.repository.ItemRepository;
import jp.co.sample.repository.ToppingRepository;

/**
 * itemの詳細表示に関するサービスクラス.
 * 
 * @author igamasayuki
 *
 */
@Service
public class ShowItemDetailService {
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private ToppingRepository toppingRepository;

	
	public Item load(Integer id) {
		return itemRepository.load(id);
	}

	public List<Topping> findAll() {
		return toppingRepository.findAll();
	}

}
