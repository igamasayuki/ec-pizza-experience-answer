package jp.co.sample.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.User;


/**
 * 利用者のログイン情報を格納するドメイン.
 * @author igamasayuki
 *
 */
public class LoginUser extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = 1L;
	
	/** ユーザー情報 */
	private final User user;
	
	
	/**
	 * 通常のメンバー情報に加え、認可用ロールを設定する.
	 * 
	 * @param user　ユーザー情報
	 * @param authorityList　権限が入ったリスト
	 */
	public LoginUser( User user, Collection<GrantedAuthority> authorityList) {
		super(user.getEmail(), user.getPassword(),authorityList);
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}
}
