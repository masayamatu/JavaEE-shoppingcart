<%@page import = "java.util.ArrayList" %>
<%@page import = "java.util.HashMap" %>
<%@page import = "exercise3object.Seafood" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>海産物カート</title>
<style>
	td {
		text-align:center;
	}
</style>
</head>
<body>
<div id="wrapper">
	<header><h1>海産物カート</h1></header>
	<main>
	<%
	    HashMap<String, Seafood> cart = (HashMap<String, Seafood>)session.getAttribute("cart");
		if(cart.size() != 0){
	%>
		<table border="1">
		<%
			for(String key:cart.keySet()){

		 %>
			<tr>
				<td>
					<%=cart.get(key).itemName %>
				</td>
				<td>
					<img src="img/<%=cart.get(key).imagePath %>" width="128" height="96" alt="<%=cart.get(key).itemName %>">
				</td>
				<td>
					￥<%=cart.get(key).price %>
				</td>
				<td>
					<form style="display: inline" action="cart" method="post">
						<input type="hidden" name="change" value="+">
						<input type="hidden" name="changeItem" value="<%=cart.get(key).itemName %>">
						<input type="submit" value="+">
					</form>
					<%=cart.get(key).count %>個
					<form style="display: inline" action="cart" method="post">
						<input type="hidden" name="change" value="-">
						<input type="hidden" name="changeItem" value="<%=cart.get(key).itemName %>">
						<input type="submit" value="-">
					</form>
					<form action="cart" method="post">
						<input type="hidden" name="changeItem" value="<%=cart.get(key).itemName %>">
						<input type="text" size="1" name="changecount">個
						<input type="submit" value="変更">
					</form>
				</td>
				<td>
					￥<%=Integer.parseInt(cart.get(key).price) * cart.get(key).count %>
				</td>
				<td>
					<form action="cart" method="post">
						<input type="hidden" name="delItem" value="<%=cart.get(key).itemName %>">
						<input type="submit" value="カートから削除する">
					</form>
				</td>
			</tr>
		<%
			}
		%>
		</table>
		<%
		}
		%>
		<%
			if(request.getAttribute("alert") != null) {
		%>
		<p><%=request.getAttribute("alert")%></p>
		<%
			}
		%>
    	<p><%=request.getAttribute("message") %></p>
    	<p>
    		<a href="form">購入フォームへ戻る</a>
    	</p>
	</main>
</div>
</body>
</html>