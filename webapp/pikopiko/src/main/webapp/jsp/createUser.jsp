<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ユーザー登録画面</title>
<h1>ピコピコサイト：ユーザー登録</h1>
</head>
<body>
<%-- <c:choose> --%>
<%-- 	<c:when test="flag"> --%>
<!-- 		<p>パスワードとユーザー名を入力してください</p> -->
<%-- 	</c:when> --%>
<%-- 	<c:otherwise> --%>
<!-- 		<p>パスワードもしくはユーザー名が違います</p> -->
<%-- 	</c:otherwise> --%>
<%-- </c:choose> --%>

<form action="../CreateUser" method="post">
ユーザー名：<input type="text" name="name"><br>
パスワード：<input type="text" name="pass"><br>
<input type="submit" value ="登録">

</form>
</body>
</html>