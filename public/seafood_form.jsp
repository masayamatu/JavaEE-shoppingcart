<%@ page import="exercise3object.Seafood" %>
<%@ page import="exercise3object.FileOperator" %>
<%@ page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>海産物購入フォーム</title>
<style>
	td{
		text-align:center;
	}
</style>
</head>
<body>
<div id="wrapper">
	<header><h1>海産物購入フォーム</h1></header>
	<main>
		<table border="1">
		<%
			ArrayList<Seafood> sf = (ArrayList<Seafood>)session.getAttribute("menu");
			for(int i = 0; i < sf.size(); i++) {
		%>
			<tr>
				<td>
					<%=sf.get(i).itemName%>
				</td>
				<td>
					<img src="img/<%=sf.get(i).imagePath %>" width="128" height="96" alt="<%=sf.get(i).itemName %>">
				</td>
				<td>
					￥<%=sf.get(i).price %>
				</td>
				<td>
					<form action="form" method="post">
						<input type="hidden" name="itemName"	value="<%=sf.get(i).itemName %>">
						<input type="submit" value="カートに入れる">
					</form>
				</td>
			</tr>
		<%
			}
		%>
		</table>
		<p>
			<%=request.getAttribute("message") %>
		</p>
		<form action="form" method="post">
			<p>
				<input type="submit" name="clear" value="カートを空にする">
			</p>
		</form>
		<p>
			<a href="cart">カートを確認する</a>
		</p>
	</main>
</div>
</body>
</html>