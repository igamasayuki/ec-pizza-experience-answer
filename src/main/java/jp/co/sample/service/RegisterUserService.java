package jp.co.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jp.co.sample.domain.User;
import jp.co.sample.repository.UserRepository;

/**
 * ユーザー登録関連サービスクラス.
 * 
 * @author igamasayuki
 *
 */
@Service
//FIXME:javadoc漏れ
public class RegisterUserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	
	
	/**
	 * 入力された情報を保存する.
	 * 
	 * @param user
	 * 			入力された登録情報
	 * @return
	 */
	public User save(User user) {
		return userRepository.save(user);
	}
	
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	
	public String encodePassword(String rawPassword) {
		String encodedPassword = passwordEncoder.encode(rawPassword);
		return encodedPassword;
	}
	
	
}
