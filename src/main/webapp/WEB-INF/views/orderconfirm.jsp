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
<title>注文確認画面</title>
<link href="/css/bootstrap.css" rel="stylesheet">
<link href="/css/piza.css" rel="stylesheet">
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
<script src="https://ajaxzip3.github.io/ajaxzip3.js" charset="UTF-8"></script>
<script src="http://code.jquery.com/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="/js/zip_code.js"></script>
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
		<div class="row">
			<div
				class="table-responsive col-lg-offset-1 col-lg-10 col-md-offset-1 col-md-10 col-sm-10 col-xs-12">
				<h3 class="text-center">注文内容確認</h3>
				<table class="table table-striped table table-bordered">
					<tbody>
						<tr>
							<th>
								<div class="text-center">商品名</div>
							</th>
							<th>
								<div class="text-center">サイズ、価格(税抜)、数量</div>
							</th>
							<th>
								<div class="text-center">トッピング、価格(税抜)</div>
							</th>
							<th>
								<div class="text-center">小計</div>
							</th>
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
		
		
		
		<div class="row">
			<div
				class="table-responsive col-lg-offset-3 col-lg-6 col-md-offset-1 col-md-10 col-sm-10 col-xs-12">
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
<%-- 					<span id="total-price">消費税：<fmt:formatNumber --%>
<%-- 							value="${order.tax}" pattern="###,###円" /></span><br> <span --%>
<%-- 						id="total-price">ご注文金額合計：<fmt:formatNumber --%>
<%-- 							value="${order.calcTotalPrice+order.tax}" pattern="###,###円" />(税込) --%>
<!-- 					</span> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->

		<!-- table -->
		<form:form action="/order/order" modelAttribute="orderDestinationForm">
			<div class="row">
				<div
					class="table-responsive col-lg-offset-3 col-lg-6 col-md-offset-1 col-md-10 col-sm-10 col-xs-12">
					<h3 class="text-center">お届け先情報</h3>
					<table class="table table-striped table table-bordered">
						<tbody>
							<tr>
								<td>
									<div class="text-center">お名前</div>
								</td>
								
								<td>
									<form:errors path="destinationName" cssStyle="color:red" element="div"/>
									<form:input path="destinationName" class="form-control" value="${user.name}"/>
								</td>
							</tr>
							<tr>
								<td>
									<div class="text-center">メールアドレス</div>
								</td>
								<td>
									<form:errors path="destinationEmail" cssStyle="color:red" element="div"/>
									<form:input path="destinationEmail"  class="form-control" value="${user.email}"/>
								</td>
							</tr>
							<tr>
								<td>
									<div class="text-center">郵便番号</div>
								</td>
									<td>
									<form:errors path="destinationZipcode" cssStyle="color:red" element="div"/>
									<div class="input-group">
										<form:input path="destinationZipcode" pattern="^[0-9]{7}$" title="郵便番号はハイフン無しで入力してください" class="form-control" value="${user.zipcode}"/>
										<span class="input-group-btn">
											<button type="button" id="address" class="btn btn-default">住所検索</button>
										</span>
									</div>
								</td>
							</tr>
							<tr>
								<td>
									<div class="text-center">住所</div>
								</td>
								<td>
									<form:errors path="destinationAddress" cssStyle="color:red" element="div"/>									
									<form:input path="destinationAddress" class="form-control" value="${user.address}"/>
								</td>
							</tr>
							<tr>
								<td>
									<div class="text-center">電話番号</div>
								</td>
								<td>
<%-- 									<form:errors path="destinationTel" cssStyle="color:red" element="div"/>									 --%>
									<form:input path="destinationTel" pattern="^([0-9]{10,11})$" title="電話番号はハイフン無しで入力してください" class="form-control" value="${user.telephone}"/>
								</td>
							</tr>
							<tr>
								<td>
									<div class="text-center">配達日時</div>
								</td>
								<td>
									<div class="form-group">
										<div class="row">
											
											<div class="col-sm-10">
												<form:errors path="deliveryDate" cssStyle="color:red" element="div"/>									
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12">
												<input type="date" name="deliveryDate" id="deliveryDate"
													class="form-control" />
											</div>
										</div>

										<div class="row">
											<div class="col-sm-12">
												<label class="radio-inline"> <input type="radio"
													name="deliveryTime" checked="checked" id="deliveryTime" value="10"> 10時
												</label> <label class="radio-inline"> <input type="radio"
													name="deliveryTime" id="deliveryTime" value="11"> 11時
												</label> <label class="radio-inline"> <input type="radio"
													name="deliveryTime" id="deliveryTime" value="12"> 12時
												</label><br> <label class="radio-inline"> <input
													type="radio" name="deliveryTime" id="deliveryTime" value="13"> 13時
												</label> <label class="radio-inline"> <input type="radio"
													name="deliveryTime" id="deliveryTime" value="14"> 14時
												</label> <label class="radio-inline"> <input type="radio"
													name="deliveryTime" id="deliveryTime" value="15"> 15時
												</label><br> <label class="radio-inline"> <input
													type="radio" name="deliveryTime" id="deliveryTime" value="16"> 16時
												</label> <label class="radio-inline"> <input type="radio"
													name="deliveryTime" id="deliveryTime" value="17"> 17時
												</label> <label class="radio-inline"> <input type="radio"
													name="deliveryTime" id="deliveryTime" value="18"> 18時
												</label><br>
											</div>
										</div>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>

			<!-- table -->
			<div class="row">
				<div
					class="table-responsive col-lg-offset-3 col-lg-6 col-md-offset-1 col-md-10 col-sm-10 col-xs-12">
					<h3 class="text-center">お支払い方法</h3>
					<table class="table table-bordered">
						<tbody>
							<tr>
								<td>
									<div class="text-center">代金引換</div>
								</td>
								<td align="center">
									<div class="row">
										<div class="col-sm-12">
											<label class="radio-inline"> <input type="radio"
												name="paymentMethod" checked="checked" id="paymentMethod" value="1"> 代金引換
											</label>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td>
									<div class="text-center">クレジットカード決済</div>
								</td>
								<td align="center">
									<div class="row">
										<div class="col-sm-12">
											<label class="radio-inline"> <input type="radio"
												name="paymentMethod" checked="checked" id="paymentMethod" value="2">
												クレジットカード
											</label><br> <br>
										</div>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-offset-4 col-xs-4">
					<div class="form-group">
							<button type="button" class="form-control btn btn-warning btn-block" data-toggle="modal" data-target="#sampleModal">
							この内容で注文する
							</button>

							<div class="modal fade" id="sampleModal" tabindex="-1">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-body">
											注文を完了しますか？
										</div>
										<div class="modal-footer">
											<button type="submit" class="btn btn-primary">はい</button>
											<button type="button" class="btn btn-default" data-dismiss="modal">キャンセル</button>
										</div>
									</div>
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
	<script src="/js/bootstrap.min.js"></script>
</body>
</html>