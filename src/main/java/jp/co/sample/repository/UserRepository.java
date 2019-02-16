package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.User;

/**
 * usersテーブル操作用リポジトリクラス.
 * 
 * @author igamasayuki
 *
 */

@Repository
public class UserRepository {
	
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	
	
	
	/**
	 * ResultSetオブジェクトからUserオブジェクトに変換するためのクラス実装＆インスタンス化
	 * 
	 */
	private static final RowMapper<User> USER_ROW_MAPPER = (rs,i) -> {
		Integer id = rs.getInt("id");
		String name = rs.getString("name");
		String email = rs.getString("email");
		String password = rs.getString("password");
		String zipcode = rs.getString("zipcode");
		String address = rs.getString("address");
		String telephone = rs.getString("telephone");
		return new User(id,name,email,password,zipcode,address,telephone);
	};

	
	
	
	/**
	 * ユーザー情報を保存または更新する.
	 * 
	 * @param user
	 * 				保存または更新するユーザー情報
	 * @return　	保存または更新されたユーザー情報
	 */
	public User save(User user) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		
		if (user.getId() == null) {
			template.update("INSERT INTO users(name,email,password,zipcode,address,telephone)"
					+ " values(:name ,:email ,:password, :zipcode, :address, :telephone)", param);
		} else {
			template.update("UPDATE users SET name=:name, email=:email, password=:password, "
					+ "zipcode=:zipcode, address=:address, telephone=:telephone WHERE id=:id", param);
		}
		return user;
	}
	
	
	
	
	
	/**
	 * メールアドレスからユーザーを検索する.
	 * 
	 * @param email　メールアドレス
	 * @return　Userオブジェクト.該当データがなければnullを返す
	 */
	public User findByEmail(String email) {
		String sql = "SELECT id, name, email, password, zipcode, address, telephone FROM users WHERE email=:email";
		SqlParameterSource param = new MapSqlParameterSource().addValue("email",email);
		
		List<User> userList = template.query(sql,param,USER_ROW_MAPPER);
		if(userList.size() == 0) {
			return null;
		}
		return userList.get(0);
	}
	
	
	
	
}
