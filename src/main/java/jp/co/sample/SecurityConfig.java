package jp.co.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * ログイン認証用設定.
 * 
 * @author igamasayuki
 *
 */
@Configuration // 設定用クラス
@EnableWebSecurity // Spring Securityのウェブ用の機能を利用する
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	
	/**
	 * このメソッドをオーバーライドすることで
	 * 特定のリクエストに対して「セキュリティ設定」を
	 * 無視する設定など全体にかかわる設定ができる.
	 * 具体的には静的リソースに対してセキュリティの設定を無視する。
	 * 
	 * 
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.WebSecurity)
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(	"/css/**",
									"/img/**",
									"/js/**",
									"/fonts/**");
	}

	
	
	
	/**
	 * このメソッドをオーバーライドすることで、認可の設定や
	 * ログインアウトに関する設定ができる。
	 * 
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests() 					// 認可に関する設定
				.antMatchers("/", "/registeruser/form", "/registeruser/create", "/viewCart",
							"/deleteByOrderItemId","/viewItemList/list","/ShowItemDetail/detail/{id}",
							"/addCart","/SearchItem/search","/404","/updateQuantity","/myname/index").permitAll() // 「/」などのパスはすべてのユーザーに許可
				.anyRequest().authenticated();		 // それ以外のパスは認証が必要

		http.formLogin() 							// ログインに関する設定
				.loginPage("/") 					// ログイン画面に遷移させるパス（ログイン認証が必要なパスを指定してかつログインされていないとこのパスに遷移される）
				.loginProcessingUrl("/login")		 // ログインボタンを押した際に遷移させるパス（ここに遷移させれば自動的にログインが行われる）
				.failureUrl("/?error=true") 		// ログイン失敗に遷移させるパス
				.defaultSuccessUrl("/loginafter/process", true) // 第1引数：デフォルトでログイン成功時に遷移させるパス
													// 第2引数： true :認証後常に第1引数のパスに遷移
													// false: 認証されてなくて一度ログイン画面に飛ばされてもログインしたら指定したURLに遷移
				
				.usernameParameter("email") 		// 認証時に使用するユーザー名のリクエストパラメーター名（今回はメールアドレス）
				.passwordParameter("password"); 	// 認証時に使用するパスワードのリクエストパラメータ名

		http.logout() 								// ログアウトに関する設定
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout**")) // ログアウトさせる際に遷移させるパス
				.logoutSuccessUrl("/viewItemList/list") 				// ログアウト後に遷移させるパス（ここではログイン画面を設定）
				.deleteCookies("JSESSINID")			 // ログアウト後、Cookieに保存されているセッションIDを削除
				.invalidateHttpSession(true); 		// true:ログアウト後、セッションを無効にする false:セッションを無効にしない

		// Exceptionハンドラ
		http.exceptionHandling().accessDeniedPage("/404.jsp"); // 不正なリクエストを検知しました

	}

	/**
	 * 「認証」に関する設定. 認証ユーザーを取得する「UserDetailsService」の設定や
	 * パスワード照合時に使う「PasswordEncoder」の設定
	 * 
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder)
	 */
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

	/**
	 * bcryptアルゴリズムで暗号化する実装を返します これをしていすることでパスワードの暗号化やマッチ確認する際に
	 * 
	 * @Autowired private PasswordEncoder passwordEncoder; と記載するとDIされるようになります。
	 * @return bcrypt アルゴリズムで暗号化する実装オブジェクト
	 * 
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
