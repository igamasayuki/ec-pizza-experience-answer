package jp.co.sample.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 送り先情報が格納されるフォーム.
 * 
 * @author igamasayuki
 *
 */
public class OrderDestinationForm {
	
	/** 送り先名前 */
	@NotBlank( message = "名前を入力してください")
	private String destinationName;
	
	/** 送り先メールアドレス */
	@NotBlank ( message = "メールアドレスを入力してください")
	@Pattern(regexp = "^([\\w])+([\\w\\._-])*\\@([\\w])+([\\w\\._-])*\\.([a-zA-Z])+$",message="メールアドレスの形式が間違っています")
	private String destinationEmail;
	
	/** 送り先郵便番号 */
	@NotBlank ( message = "郵便番号を入力してください")
	@Size (min = 1, max = 7, message = "郵便番号は　数字7文字で入力してください")
	private String destinationZipcode;
	
	/** 送り先住所 */
	@NotBlank ( message = "住所を入力してください")
	private String destinationAddress;
	
	/** 送り先電話番号 */
	@NotBlank ( message = "電話番号を入力してください")
	@Size ( min = 1, max = 11, message = "電話番号はハイフン無し、11文字で入力をお願いします")
	private String destinationTel;
	
	/** 配達日 */
	@NotEmpty ( message = "配達希望日を選んでください")
	private String deliveryDate;
	
	/** 配達時間 */
	private String deliveryTime;
	
	/** 支払い方法 */
	private String paymentMethod;

	//getter,setter
	public String getDestinationName() {
		return destinationName;
	}
	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}
	public String getDestinationEmail() {
		return destinationEmail;
	}
	public void setDestinationEmail(String destinationEmail) {
		this.destinationEmail = destinationEmail;
	}
	public String getDestinationZipcode() {
		return destinationZipcode;
	}
	public void setDestinationZipcode(String destinationZipcode) {
		this.destinationZipcode = destinationZipcode;
	}
	public String getDestinationAddress() {
		return destinationAddress;
	}
	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}
	public String getDestinationTel() {
		return destinationTel;
	}
	public void setDestinationTel(String destinationTel) {
		this.destinationTel = destinationTel;
	}
	public String getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public String getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
}
