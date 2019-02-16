<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link href="/css/bootstrap.css" rel="stylesheet">
<script src="https://ajaxzip3.github.io/ajaxzip3.js" charset="UTF-8"></script>
<script src="http://code.jquery.com/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="/js/zip_code2.js"></script>


</head>

<body>
<div class="container">
	<nav class="navbar navbar-default">
			<div class="container-fluid">
				<!-- Brand and toggle get grouped for better mobile display -->
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed"
						data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
						aria-expanded="false">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="${pageContext.request.contextPath}/viewItemList/list"> <!-- 企業ロゴ --> <img
						alt="main log" src="../img/header_logo.png" height="35">
					</a>
				</div>
 
				<!-- Collect the nav links, forms, and other content for toggling -->
				<div class="collapse navbar-collapse"
					id="bs-example-navbar-collapse-1">
					<p class="navbar-text navbar-right">
						<a href="${pageContext.request.contextPath}/viewCart" class="navbar-link">ショッピングカート<span class="badge"><c:out value="${cartCount}"/></span></a>&nbsp;&nbsp;
					</p>
				</div>
				<!-- /.navbar-collapse -->
			</div>
			<!-- /.container-fluid -->
		</nav>
	<div class="row">
			<div class="col-lg-offset-3 col-lg-6 col-md-offset-2 col-md-8 col-sm-10 col-xs-12">
				<div class="well">
					<form:form name="registerForm" modelAttribute="registerUserForm" method="post" action="${pageContext.request.contextPath}/registeruser/create">
						<fieldset>
							<legend>ユーザ登録</legend>
							<div class="form-group">
								<label for="inputName">名前:</label><label
									class="control-label" style="color: red" for="inputError"></label>
								<form:errors path="name" cssStyle="color:red" element="div"/>
								<form:input type="text" path="name" id="inputName" class="form-control"
									placeholder="Name"/>
							</div>
							<div class="form-group">
								<label for="inputEmail">メールアドレス:</label><label
									class="control-label" style="color: red" for="inputError"></label>
								<form:errors path="email" cssStyle="color:red" element="div"/>
								<form:input type="text" path="email" id="inputEmail" class="form-control"
									placeholder="Email"/>
							</div>
							<div class="form-group">
								<label for="inputZipcode">郵便番号:　　※ハイフン無しで入力してください</label>
								<label
									class="control-label" style="color: red" for="inputError"></label>
									<form:errors path="zipcode" cssStyle="color:red" element="div"/>
									<div class="input-group">
										<form:input type="text" pattern="^[0-9]{7}$" title="郵便番号はハイフン無しで入力してください" path="zipcode" id="inputZipcode" class="form-control"
										placeholder="Zipcode"/>
										<span class="input-group-btn">
											<input type="button" id="residence" value="住所検索" class="btn btn-default">
										</span>
									</div>

							</div>
							<div class="form-group">
								<label for="inputAddress">住所:</label>
								<label
									class="control-label" style="color: red" for="inputError"></label>
								<form:errors path="address" cssStyle="color:red" element="div"/>
								<form:input type="text" path="address" id="inputAddress" class="form-control"
									placeholder="Address"/>
							</div>
							<div class="form-group">
								<label for="inputTel">電話番号:　　※ハイフン無しで入力してください</label>
								<label
									class="control-label" style="color: red" for="inputError"></label>
								<form:errors path="telephone" cssStyle="color:red" element="div"/>
								<form:input type="text" pattern="^([0-9]{10,11})$" title="電話番号はハイフン無しで入力してください" path="telephone" id="inputTel" class="form-control"
									placeholder="Tell"/>
							</div>
							<div class="form-group">
								<label for="inputPassword">パスワード:　※半角英数字　大文字小文字を入れた8～16字で設定してください</label>
								<label
									class="control-label" style="color: red" for="inputError"></label>
								<form:errors path="password" cssStyle="color:red" element="div"/>				
								<form:input type="password" name="pass" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" title="パスワードは半角英数字 8~16文字で入力してください。" path="password" id="inputPassword" class="form-control"
									placeholder="Password"/>
							</div>
							<div class="form-group">
								<label for="inputConfirmationPassword">もう一度パスワードを入力してください:</label>
								<label
									class="control-label" style="color: red" for="inputError"></label>
								<form:errors path="checkPassword" cssStyle="color:red" element="div"/>
								<form:input type="password" path="checkPassword" id="inputConfirmationPassword" class="form-control"
									placeholder="Confirmation Password"/>
							</div>
							<div class="form-group">
								<button type="submit" class="btn btn-primary">登録</button>
								<button type="button" class="btn btn-default" id="jquery_reset_perfect">リセット</button>
								
							</div>
						</fieldset>
					</form:form>
				</div>
			</div>
		</div>	
</div>
	
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>


</body>
</html>