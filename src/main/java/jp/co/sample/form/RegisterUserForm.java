package jp.co.sample.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
//FIXME:インポート文に警告あり。警告は０を目指しましょう
/**
 * ユーザー関連のリクエストパラメーターが入るフォーム.
 * 
 * @author igamasayuki
 *
 */
public class RegisterUserForm {

	/** 名前 */
	@NotBlank (message = "名前を入力してください")
	private String name;
	
	/** メールアドレス */
	@NotBlank ( message = "メールアドレスを入力してください")
	@Pattern(regexp = "^([\\w])+([\\w\\._-])*\\@([\\w])+([\\w\\._-])*\\.([a-zA-Z])+$",message="メールアドレスの形式が間違っています")
	private String email;
	
	/** 郵便番号 */
	@NotBlank( message = "郵便番号を入力してください")
	private String zipcode;
	
	/** 住所 */
	@NotBlank( message = "住所を入力してください")
	private String address;
	
	/** 電話番号 */
	@NotBlank ( message = "電話番号を入力してください")
	private String telephone;
	
	/** パスワード */
	@NotBlank ( message = "パスワードを入力してください")
	private String password;
	
	/** 確認用パスワード */
	@NotBlank ( message = "もう一度パスワードを入力してください")
	private String checkPassword;

	@Override
	public String toString() {
		return "RegisterUserForm [name=" + name + ", email=" + email + ", zipcode=" + zipcode + ", address=" + address
				+ ", telephone=" + telephone + ", password=" + password + ", checkPassword=" + checkPassword + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCheckPassword() {
		return checkPassword;
	}

	public void setCheckPassword(String checkPassword) {
		this.checkPassword = checkPassword;
	}

}
