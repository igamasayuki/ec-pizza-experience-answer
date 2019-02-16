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
<title>注文履歴</title>
<link href="/css/bootstrap.css" rel="stylesheet">
<link href="/css/piza.css" rel="stylesheet">
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
					<a class="navbar-brand" href="/viewItemList/list"> <!-- 企業ロゴ --> <img
						alt="main log" src="/img/header_logo.png" height="35">
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


		<!-- table -->
		<h3 class="text-center">注文内容確認</h3>
		<c:forEach var="order" items="${orders}">
		<div class="row">
			<div
				class="table-responsive col-lg-offset-1 col-lg-10 col-md-offset-1 col-md-10 col-sm-10 col-xs-12">
				<table class="table table-striped table table-bordered">
					<tbody>
						<tr>
							<th>
								注文日：<fmt:formatDate value="${order.orderDate}" pattern="MM月dd日" />
							</th>
							<th>
								お届け先:<c:out value="${order.destinationAddress}"/>
							</th>
							<th>
								<c:if test="${order.status == 1}">
									<fmt:formatDate value="${order.deliveryTime}" pattern="MM月dd日 hh時"/>に配達予定(代金引換)
								</c:if>
								<c:if test="${order.status == 2}">
									<fmt:formatDate value="${order.deliveryTime}" pattern="MM月dd日 hh時"/>に配達予定
								</c:if>
								<c:if test="${order.status == 3}">
									<fmt:formatDate value="${order.deliveryTime}" pattern="MM月dd日"/>に配達済み
								</c:if>
							</th>
							<th>
								ご注文金額合計：<fmt:formatNumber value="${order.calcTotalPrice+order.tax}" pattern="###,###円" />(税込)
							</th>
						</tr>
						<tr>
							<td>
								商品名
							</td>
							<td>
								サイズ、価格(税抜)、数量
							</td>
							<td>
								トッピング、価格(税抜)
							</td>
							<td>
								小計
							</td>
						</tr>
						<c:forEach var="orderitem" items="${order.orderItemList}">
							<tr>
								<td>
									<div class="center">
										<img src="${orderitem.item.imagePath}"
											class="img-responsive img-rounded" width="100" height="300"><br>
										<c:out value="${orderitem.item.name}" />
									</div>
								</td>
								<td><span class="price">&nbsp; <c:out
											value="${orderitem.size}" />
								</span>&nbsp;&nbsp; <c:if test="${orderitem.size=='M'.charAt(0)}">
										<fmt:formatNumber value="${orderitem.item.priceM}"
											pattern="###,###円" />
									</c:if> <c:if test="${orderitem.size=='L'.charAt(0)}">
										<fmt:formatNumber value="${orderitem.item.priceL}"
											pattern="###,###円" />
									</c:if> &nbsp;&nbsp; <c:out value="${orderitem.quantity}" />個</td>
								<td>
									<ul>
										<c:forEach var="ordertopping"
											items="${orderitem.orderToppingList}">
											<li><c:out value="${ordertopping.topping.name}" /> <c:if
													test="${orderitem.size=='M'.charAt(0)}">
													<fmt:formatNumber value="${ordertopping.topping.priceM}"
														pattern="###,###円" />
												</c:if> <c:if test="${orderitem.size=='L'.charAt(0)}">
													<fmt:formatNumber value="${ordertopping.topping.priceL}"
														pattern="###,###円" />
												</c:if></li>
										</c:forEach>
									</ul>
								</td>
								<td><fmt:formatNumber value="${orderitem.subTotal}"
										pattern="###,###円" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		</c:forEach>

	</div>
	<!-- end container -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="/js/bootstrap.min.js"></script>
</body>
</html>