<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>ピザ詳細</title>
<link href="../../css/bootstrap.css" rel="stylesheet">
<link href="../../css/piza.css" rel="stylesheet">

<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

<!-- <script src="http://code.jquery.com/jquery-3.2.1.min.js"></script> -->
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script type="text/javascript">
$(function(){
	$('.size').change(function(){
		calc();
	});
	
	$('.toppingList').change(function() {
		calc();
	});
	
    $('.form-control').change(function() {
    	calc();
    });
 
    function calc() {
    	var str = $('.size:checked').val();
    	var cnt = $('.toppingList:checkbox:checked').length;
    	var quantity = $('.form-control option:selected').val();
    	var priceM = $('#priceM').val();
    	var priceL = $('#priceL').val();
    	var toppingM = $('#toppingM').val();
    	var toppingL = $('#toppingL').val();
    	var total = 0;
    	
    	if( str == 'M' ){
    		total = ( Number(priceM) + Number(cnt * toppingM) ) * quantity;
    	}else{
    		total = ( Number(priceL) + Number(cnt * toppingL) ) * quantity;
    	}
    	$('#total').text(total.toLocaleString());
    };
    	
});
</script>

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
					<a class="navbar-brand" href="${pageContext.request.contextPath}/viewItemList/list"> <!-- 企業ロゴ -->
						<img alt="main log" src="../../img/header_logo.png" height="35">
					</a>
				</div>

				<!-- Collect the nav links, forms, and other content for toggling -->
				<div class="collapse navbar-collapse"
					id="bs-example-navbar-collapse-1">
					<p class="navbar-text navbar-right">
						<a href="${pageContext.request.contextPath}/viewCart" class="navbar-link">ショッピングカート<span class="badge"><c:out value="${cartCount}"/></span></a>&nbsp;&nbsp;
						<a href="/orderhistory/history" class="navbar-link">注文履歴</a>&nbsp;&nbsp;
						
						<sec:authorize access="hasRole('ROLE_USER') and isAuthenticated()">
							<sec:authentication var="userName" property="principal.user.name" />
						</sec:authorize>
						
					<c:choose>
						<c:when test="${not empty userName}">
							<span style="color:red">
								<c:out value="${userName} 様" ></c:out>&nbsp;&nbsp;
							</span>
								<a href="/logout" class="navbar-link">ログアウト</a>
						</c:when>
							<c:otherwise>
								<a href="/" class="navbar-link">ログイン</a>&nbsp;&nbsp;
							</c:otherwise>
					</c:choose>
						
						
						
					</p>
				</div>
				<!-- /.navbar-collapse -->
			</div>
			<!-- /.container-fluid -->
		</nav>

		<form:form modelAttribute="addCartForm" action="${pageContext.request.contextPath}/addCart" method="post" >
		<div class="row">
			<div class="col-xs-offset-2 col-xs-8">

				<h3 class="text-center">商品詳細</h3>
				<div class="row">
					<div class="col-xs-5">
						<img src="<c:out value="${item.imagePath}" />" class="img-responsive img-rounded">
					</div>

					<div class="col-xs-5">
						<div class="bs-component">
							<h4>	<c:out value="${item.name}"></c:out></h4> <br>
							<br>
							<p><c:out value="${item.description}"></c:out></p>
						</div>
					</div>
				</div><br>
				<div class="row">
					<div class="col-xs-offset-2 col-xs-8">
						<div class="form-group">
							<div class="row">
								<div class="col-sm-12">
									<label for="inputResponsibleCompany">サイズ</label>
								</div>
								<div class="col-sm-12">
									<label class="radio-inline"> 
										<form:radiobutton class="size"
											path="size" value="M" checked="checked"/>
										<span class="price">&nbsp;М&nbsp;</span>&nbsp;&nbsp;
										<input id="priceM" type="hidden" value="${item.priceM}">
										<fmt:formatNumber value="${item.priceM}" pattern="###,###"/>円（税抜き）<br>
									</label>
									<label class="radio-inline"> 
										<form:radiobutton class="size" path="size" value="L"/> 
										<span class="price">&nbsp;Ｌ</span>&nbsp;&nbsp;
										<input id="priceL" type="hidden" value="${item.priceL}">
										<fmt:formatNumber value="${item.priceL}" pattern="###,###"/>円（税抜き）<br>
									</label>
								</div>
							</div>
						</div>
					</div>
				</div><br>
				<div class="row">
					<div class="col-xs-offset-2 col-xs-8">
						<div class="form-group">
							<div class="row">
								<div class="col-sm-12">
									<label for="inputResponsibleCompany">
										トッピング：<br>
										&nbsp;1つにつき&nbsp;&nbsp;
										<span>&nbsp;М&nbsp;</span>&nbsp;&nbsp;
										<input id="toppingM" type="hidden" value="${toppingList.get(0).priceM}">
										<fmt:formatNumber value="${toppingList.get(0).priceM}" pattern="###,###"/>円（税抜き）&nbsp;&nbsp;
										<span>&nbsp;Ｌ</span>&nbsp;&nbsp;
										<input id="toppingL" type="hidden" value="${toppingList.get(0).priceL}">
										<fmt:formatNumber value="${toppingList.get(0).priceL}" pattern="###,###"/>円（税抜き）<br>
									</label>
								</div>
								<div class="col-sm-12">
										<c:forEach var="topping" items="${toppingList}">
										<label class="checkbox-inline">
											<form:checkbox class="toppingList" path="toppingIdList" value="${topping.id}"/><c:out value="${topping.name}"/>
										</label>
										</c:forEach>
								</div>
									
<!-- 									<label class="checkbox-inline"> -->
<!-- 										<input type="checkbox" value="">チーズ -->
<!-- 									</label> -->
<!-- 									<label class="checkbox-inline"> -->
<!-- 										<input type="checkbox" value="">ピーマン -->
<!-- 									</label> -->
<!-- 									<label class="checkbox-inline"> -->
<!-- 										<input type="checkbox" value="">ロースハム -->
<!-- 									</label><br> -->
<!-- 									<label class="checkbox-inline"> -->
<!-- 										<input type="checkbox" value="">ほうれん草 -->
<!-- 									</label> -->
<!-- 									<label class="checkbox-inline"> -->
<!-- 										<input type="checkbox" value="">ぺパロニ -->
<!-- 									</label> -->
<!-- 									<label class="checkbox-inline"> -->
<!-- 										<input type="checkbox" value="">グリルナス -->
<!-- 									</label> -->
<!-- 									<label class="checkbox-inline"> -->
<!-- 										<input type="checkbox" value="">あらびきソーセージ -->
<!-- 									</label> -->
							</div>
						</div>
					</div>
				
				</div>
				<div class="row">
					<div class="col-xs-offset-2 col-xs-8">
						<div class="form-group">
							<div class="row">
								<div class="col-xs-5 col-sm-5">
									<label for="">数量:</label>
									<label class="control-label"
										style="color: red" for="inputError">数量を選択してください</label>
										<select name="quantity" class="form-control">
										<option value="1">1</option>
										<option value="2">2</option>
										<option value="3">3</option>
										<option value="4">4</option>
										<option value="5">5</option>
										<option value="6">6</option>
										<option value="7">7</option>
										<option value="8">8</option>
										<option value="9">9</option>
										<option value="10">10</option>
										<option value="11">11</option>
										<option value="12">12</option>
									</select>
								</div>
							</div>
						</div>
					</div>
				</div>
				<br>
				<div class="row">
					<div class="col-xs-offset-2 col-xs-10">
						<div class="form-group">
							<!-- 注文商品と注文トッピングの小計 -->
							<span id="total-price">この商品の合計金額：
							<span id="total"><fmt:formatNumber value="${item.priceM}" pattern="###,###"/></span>円（税抜）</span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-offset-2 col-xs-3">
						<div class="form-group">
							<p>
								<input type="hidden" name="itemId" value="<c:out value="${item.id}"/>">
								<input class="form-control btn btn-warning btn-block"
									type="submit" value="カートに入れる">
							</p>
							
						</div>
					</div>
				</div>
			</div>
		</div>
		</form:form>

	</div>
	<!-- end container -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="../js/bootstrap.min.js"></script>
	
</body>
</html>