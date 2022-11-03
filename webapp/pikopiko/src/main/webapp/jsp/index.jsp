<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- <%@ page import="model.LoginLogic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
LoginLogic loginLogic = (LoginLogic) request.getAttribute("loginlogic");
%>
 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=DotGothic16&display=swap" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/pikopiko/css/style.css">

<title>ピコピコサイト</title>
</head>
<body>
<header>
<h1>ピコピコサイト</h1>
</header>

<h2 class="coment">ピアノが現れた！</h2>
<%-- <c:choose> --%>
<%-- 	<c:when test="flag"> --%>
<!-- 		<p>パスワードとユーザー名を入力してください</p> -->
<%-- 	</c:when> --%>
<%-- 	<c:otherwise> --%>
<!-- 		<p>パスワードもしくはユーザー名が違います</p> -->
<%-- 	</c:otherwise> --%>
<%-- </c:choose> --%>
<div class="box">
<div id="piano-container">
    <div id="piano-wrap">
        <div class="piano-key white-key" data-key-num="0"><span class="key-label">Q<br>　</span></div><!-- ラ -->
        <div class="piano-key black-key" data-key-num="1"><span class="key-label">2<br>　</span></div><!-- ラ# -->
        <div class="piano-key white-key" data-key-num="2"><span class="key-label">W<br>　</span></div><!-- シ -->
        <div class="piano-key white-key" data-key-num="3"><span class="key-label">E<br>　</span></div><!-- ド -->
        <div class="piano-key black-key" data-key-num="4"><span class="key-label">4<br>　</span></div><!-- ド# -->
        <div class="piano-key white-key" data-key-num="5"><span class="key-label">R<br>　</span></div><!-- レ -->
        <div class="piano-key black-key" data-key-num="6"><span class="key-label">5<br>　</span></div><!-- レ# -->
        <div class="piano-key white-key" data-key-num="7"><span class="key-label">T<br>　</span></div><!-- ミ -->
        <div class="piano-key white-key" data-key-num="8"><span class="key-label">Y<br>　</span></div><!-- ファ -->
        <div class="piano-key black-key" data-key-num="9"><span class="key-label">7<br>　</span></div><!-- ファ# -->
        <div class="piano-key white-key" data-key-num="10"><span class="key-label">U<br>　</span></div><!-- ソ -->
        <div class="piano-key black-key" data-key-num="11"><span class="key-label">8<br>　</span></div><!-- ソ# -->
        <div class="piano-key white-key" data-key-num="12"><span class="key-label">I<br>Z</span></div><!-- ラ -->
        <div class="piano-key black-key" data-key-num="13"><span class="key-label">9<br>S</span></div><!-- ラ# -->
        <div class="piano-key white-key" data-key-num="14"><span class="key-label">O<br>X</span></div><!-- シ -->
        <div class="piano-key white-key" data-key-num="15"><span class="key-label">P<br>C</span></div><!-- ド -->
        <div class="piano-key black-key" data-key-num="16"><span class="key-label">-<br>F</span></div><!-- ド# -->
        <div class="piano-key white-key" data-key-num="17"><span class="key-label">@<br>V</span></div><!-- レ -->
        <div class="piano-key black-key" data-key-num="18"><span class="key-label">^<br>G</span></div><!-- レ# -->
        <div class="piano-key white-key" data-key-num="19"><span class="key-label">[<br>B</span></div><!-- ミ -->
        <div class="piano-key white-key" data-key-num="20"><span class="key-label">N</span></div><!-- ファ -->
        <div class="piano-key black-key" data-key-num="21"><span class="key-label">J</span></div><!-- ファ# -->
        <div class="piano-key white-key" data-key-num="22"><span class="key-label">M</span></div><!-- ソ -->
        <div class="piano-key black-key" data-key-num="23"><span class="key-label">K</span></div><!-- ソ# -->
        <div class="piano-key white-key" data-key-num="24"><span class="key-label">,</span></div><!-- ラ -->
        <div class="piano-key black-key" data-key-num="25"><span class="key-label">L</span></div><!-- ラ# -->
        <div class="piano-key white-key" data-key-num="26"><span class="key-label">.</span></div><!-- シ -->
        <div class="piano-key white-key" data-key-num="27"><span class="key-label">/</span></div><!-- ド -->
        <div class="piano-key black-key" data-key-num="28"><span class="key-label">:</span></div><!-- ド# -->
        <div class="piano-key white-key" data-key-num="29"><span class="key-label">\</span></div><!-- レ -->
    </div>
    <h2 class="serif">ピアノ：鍵盤を引いて遊んでね！</h2>
</div>
	<script type="text/javascript" src="/pikopiko/js/piano.js"></script>
<div class="form">
<div class="inputform">
<form action="../Login" method="post">
ユーザー名：<input type="text" name="name"><br>
パスワード：<input type="text" name="pass"><br>
</div>
<div class="formbutton">
<button  type="submit" class="button">もっと遊ぶ<br>(ログイン)</button>
</form>
<button onclick="location.href='createUser.jsp'" type="submit" class="button">仲間にする<br>(新規の方はこちら)</button>
</div>
</div>
</body>
</html>