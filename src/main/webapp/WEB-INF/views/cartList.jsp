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
<title>ショッピングカート</title>
<link href="css/bootstrap.css" rel="stylesheet">
<link href="css/piza.css" rel="stylesheet">
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
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
						alt="main log" src="img/header_logo.png" height="35">
					</a>
				</div>

				<!-- Collect the nav links, forms, and other content for toggling -->
				<div class="collapse navbar-collapse"
					id="bs-example-navbar-collapse-1">
					<p class="navbar-text navbar-right">
						<a href="${pageContext.request.contextPath}/viewCart" class="navbar-link">ショッピングカート<span class="badge"><c:out value="${cartCount}"/></span></a>&nbsp;&nbsp;
						<a href="${pageContext.request.contextPath}/orderhistory/history" class="navbar-link">注文履歴</a>&nbsp;&nbsp;
						
					<sec:authorize access="hasRole('ROLE_USER') and isAuthenticated()">
						<sec:authentication var="userName" property="principal.user.name" />
					</sec:authorize>
						
					<c:choose>
						<c:when test="${not empty userName}">
							<span style="color:red">
								<c:out value="${userName} 様"></c:out>&nbsp;&nbsp;
							</span>
								<a href="${pageContext.request.contextPath}/logout" class="navbar-link">ログアウト</a>
						</c:when>
							<c:otherwise>
								<a href="${pageContext.request.contextPath}/" class="navbar-link">ログイン</a>&nbsp;&nbsp;
							</c:otherwise>
					</c:choose>
						
					</p>
				</div>
				<!-- /.navbar-collapse -->
			</div>
			<!-- /.container-fluid -->
		</nav>
		
		<!-- ショッピングカートに商品がない場合 -->
		<c:if test="${order==null}">
			現在、ショッピングカートに商品はありません。<br>
			<h3 class="text-center">オススメ商品</h3>

			
		<!-- table -->
						
		<div class="row">
			<div
				class="table-responsive col-lg-offset-1 col-lg-10 col-md-offset-1 col-md-10 col-sm-10 col-xs-12">
				<table class="table table-striped table table-bordered">
					<tbody>
						<tr>
							<c:forEach var="item" items="${itemRecommendList}" varStatus="status">
								<td>
									
									<a href="${pageContext.request.contextPath}/ShowItemDetail/detail/${item.id}">
										<img src="<c:out value="${item.imagePath}" />"class="img-responsive img-rounded" width="200" height="200">
									</a><br>
									<a href="${pageContext.request.contextPath}/ShowItemDetail/detail/${item.id}">
										<c:out value="${item.name}"></c:out><br>
									</a><br>
									
									<span class="price">&nbsp;М&nbsp;</span>&nbsp;&nbsp;
									<fmt:formatNumber value="${item.priceM}" pattern="###,###"/>円（税抜き）<br>
									<span class="price">&nbsp;Ｌ&nbsp;</span>&nbsp;&nbsp;
									<fmt:formatNumber value="${item.priceL}" pattern="###,###"/>円（税抜き）<br>
									<br>
								</td>
							</c:forEach>
			
							</tr>
					</tbody>
				</table>
				</div>
			</div>
			
			<div class="row">
			<div class="col-xs-offset-5 col-xs-3">
				<div class="form-group">
					<form:form action="${pageContext.request.contextPath}/viewItemList/list" method="post">
						<input class="form-control btn btn-warning btn-block"
							type="submit" value="商品一覧に戻る">
					</form:form>
				</div>
			</div>
		</div>
			
		</c:if><br>
		
		<!-- ショッピングカートに商品が1つ以上入っている場合 -->
		<c:if test="${order != null}">

		<!-- table -->
		<div class="row">
			<div
				class="table-responsive col-lg-offset-1 col-lg-10 col-md-offset-1 col-md-10 col-sm-10 col-xs-12">
				<h3 class="text-center">ショッピングカート</h3>
				<table class="table table-striped table table-bordered">
					<tbody>
						<tr>
							<th>
								<div class="text-center">
									商品名
								</div>
							</th>
							<th>
								<div class="text-center">
									サイズ、価格(税抜)、数量
								</div>
							</th>
							<th>
								<div class="text-center">
									トッピング、価格(税抜)
								</div>
							</th>
							<th>
								<div class="text-center">
									小計
								</div>
							</th>
							<th>
							</th>
						</tr>
						
						
						<!-- 注文商品の表示 -->
						<c:forEach var="orderItem" items="${order.orderItemList}" >
							<tr>
							<td>
								<div class="center">
									<!-- 注文商品の画像 -->
									<a href="${pageContext.request.contextPath}/ShowItemDetail/detail/${orderItem.item.id}"><img src="<c:out value="${orderItem.item.imagePath}"/>"
										class="img-responsive img-rounded" width="100" height="300"></a><br>
									<!-- 注文商品名 -->
									<a href="${pageContext.request.contextPath}/ShowItemDetail/detail/${orderItem.item.id}"><c:out value="${orderItem.item.name}" /></a>
								</div>
							</td>
							<td>
								<span class="price">&nbsp;<c:out value="${orderItem.size}" /></span>
								<!-- Mサイズの時の商品の金額 -->
								<c:if test="${orderItem.size=='M'.charAt(0)}">
									<fmt:formatNumber value="${orderItem.item.priceM}" pattern="###,###"/>円
								</c:if>
								<!-- Lサイズの時の商品の金額 -->
								<c:if test="${orderItem.size=='L'.charAt(0)}">
									<fmt:formatNumber value="${orderItem.item.priceL}" pattern="###,###"/>円
								</c:if>
								<!-- 商品の個数 -->
								&nbsp;&nbsp;<c:out value="${orderItem.quantity}" />個
								<form:form action="${pageContext.request.contextPath}/updateQuantity">							
											<select name="quantity">
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
											<input type="hidden" name="orderItemId" value="<c:out value="${orderItem.id}"/>">
											<input type="hidden" name="orderId" value="<c:out value="${orderItem.orderId}"/>">
											<input type="submit" value="数量を変更">
								</form:form>		
							</td>
							<td>
								<c:forEach var="orderTopping" items="${orderItem.orderToppingList}">
								<ul>
									<li><c:out value="${orderTopping.topping.name}" />
										<!-- Mサイズの時のトッピングの金額 -->
										<c:if test="${orderItem.size=='M'.charAt(0)}">
											<fmt:formatNumber value="${orderTopping.topping.priceM}" pattern="###,###"/>円
										</c:if>
										<!-- Lサイズの時のトッピングの金額 -->
										<c:if test="${orderItem.size=='L'.charAt(0)}">
											<fmt:formatNumber value="${orderTopping.topping.priceL}" pattern="###,###"/>円
										</c:if>
										</li>
								</ul>
								</c:forEach>
							</td>
							<td>
								<div class="text-center">
									<!-- 注文商品と注文トッピングの小計 -->
									<fmt:formatNumber value="${orderItem.subTotal}" pattern="###,###"/>円
								</div>
							</td>
							<td>
								<div class="text-center">
									<!-- 注文商品を削除 -->
									<form:form action="${pageContext.request.contextPath}/deleteByOrderItemId" method="post">
										<input type="hidden" name="orderItemId" value="<c:out value="${orderItem.id}"/>">
										<input type="submit" class="btn btn-primary" value="削除">
									</form:form>
								</div>
							</td>
							</tr>
						</c:forEach>					
					</tbody>
				</table>
			</div>
			
		</div>
		
		<div class="row">
			<div
				class="table-responsive col-lg-offset-1 col-lg-10 col-md-offset-1 col-md-10 col-sm-10 col-xs-12">
				<h3 class="text-center">お支払い金額</h3>
			<table class="table table-striped table table-bordered">
						<tr>
							<td colspan="5">
								<div class="text-right">
									<span style="font-size:15pt;">合計：<fmt:formatNumber value="${order.calcTotalPrice}" pattern="###,###"/>円</span>
								</div>
							</td >
						</tr>
						
						<tr>
							<td colspan="5">
								<div class="text-right">
									<span style="font-size:15pt;">消費税：<fmt:formatNumber value="${order.tax}" pattern="###,###"/>円</span>
								</div>
							</td>
						</tr>
						
						<tr>
							<td colspan="5">
								<div class="text-right">
									<span style="font-size:15pt; color:red;">お支払い金額合計：<fmt:formatNumber value="${order.calcTotalPrice + order.tax}" pattern="###,###"/>円</span>
								</div>
							</td>
						</tr>
			</table>
		</div>
		</div>	
			
			
<!-- 		<div class="row"> -->
<!-- 			<div class="col-xs-offset-2 col-xs-8"> -->
<!-- 				<div class="form-group text-center"> -->
					
<%-- 					<span id="total-price">合計：<fmt:formatNumber value="${order.calcTotalPrice}" pattern="###,###"/>円</span><br> --%>
<%-- 					<span id="total-price">消費税：<fmt:formatNumber value="${order.tax}" pattern="###,###"/>円</span><br> --%>
<%-- 					<span id="total-price">ご注文金額合計：<fmt:formatNumber value="${order.calcTotalPrice + order.tax}" pattern="###,###"/>円</span> --%>
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
		
		<div class="row">
			<div class="col-xs-offset-5 col-xs-3">
				<div class="form-group">
					<form:form action="${pageContext.request.contextPath}/viewItemList/list" method="post">
						<input class="form-control btn btn-warning btn-block"
							type="submit" value="買い物を続ける">
					</form:form>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-xs-offset-5 col-xs-3">
				<div class="form-group">
					<form:form action="${pageContext.request.contextPath}/order/orderconfirm" method="post">
						<input class="form-control btn btn-warning btn-block"
							type="submit" value="注文に進む">
					</form:form>
				</div>
			</div>
		</div>
		
	
		
		

		
	</div>
	
	</c:if>
	
	<!-- end container -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>