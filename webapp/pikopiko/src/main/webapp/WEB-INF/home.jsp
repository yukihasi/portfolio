<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=DotGothic16&display=swap" rel="stylesheet">
<link id="PageStyleSheet" rel="stylesheet" type="text/css" href="/pikopiko/css/homestyle.css">
<!--  <script type="text/javascript">
   function changeStyleSheet(num) {
     var elem = document.getElementById("PageStyleSheet");
     if(num == 1){
         elem.href = "/pikopiko/css/homestyle.css";
     }
     else if(num == 2){
	     elem.href = "/pikopiko/css/homestyle2.css";
     }
   }
 </script>
 -->
 <title>ピコピコホーム</title>
</head>

<body>
<header>
<h1>ピアノの鍵盤が仲間になった！</h1>
<!-- rightmenu -->
<div class="menu-section">
  <div class="menu-toggle">
    <div class="one"></div>
    <div class="two"></div>
    <div class="three"></div>
  </div>
  <nav>
    <ul role="navigation" class="hidden">
      <li><a href="#">User</a></li>
      <li><a href="#">MusicList</a></li>
      <li><a href="#">MyFavorite</a></li>
      <li><a href="./jsp/index.jsp">Logout</a></li>
    </ul>
  </nav>
</div>
</header>
<!-- もーｄ -->
<div id="edit_area">8bitモード</div>
 <ul class="gnav">
    <li>
    <a tabindex="-1">
    <strong>楽器</strong>
    <small>instrument</small>
      <ul>
            <li><a href="javascript:musicChangeId('1')" onclick="javascript:changeStyleSheet(1);" class="select_music">8bit</a></li>       
            <li><a href="javascript:musicChangeId('2')" onclick="javascript:changeStyleSheet(2);" class="select_music">パイプオルガン</a></li>
      </ul>
    </a>
 	</li>	
    <li>
    <a tabindex="-1">
    <strong>録音</strong>
    <small>recording</small>
        <ul>
        	<li><a tabindex="-1"><label for="musicTitle">musicTitle</label></li><li><input type=text id="musicTitle" name="musicTitle" value="musicTitle"></a></li>
            <li><a href="javascript:recordStart()" class="select" >start</a></li>
            <li><a href="javascript:recordStop()" class="select">stop</a></li>
        </ul>
	</a>
	</li>
    <li>
    <a tabindex="-1">
    <strong>再生</strong>
    <small>playback</small>
        <ul>
           	<li><a tabindex="-1"><label for="playBackTitle">playBackTitle</label></li><li><select name="playBackTitle" id="playBackTitle">
<%-- 				<c:forEach var="titleList" items="${titleList}">
				 <option value=" ${titleList}"></option>
				</c:forEach>
 --%>           	</select></a></li>
            <li><a href="javascript:playBackStart()" class="select">start</a></li>
            <li><a href="javascript:playBackStop()" class="select">stop</a></li>
        </ul>
	</a>
	</li>
    <li>
    <a tabindex="-1">
    <strong>変換</strong>
    <small>conversion</small>
	</a>
	</li>
    <li>
    <a tabindex="-1">
    <strong>ノイズ</strong>
    <small>noise</small>
        <ul>
        	<li><a tabindex="-1"><label for="sampleRate">sampling_rate</label></li><li><input type=number id="sampleRate" name="sampleRate" value="sampleRate" min="1" max="100000"></a></li>
        	<li><a tabindex="-1"><label for="intervalTime">interval</label></li><li><input type=number id="intervalTime" name="intervalTime" value="intervalTime" min="1" max="100000"></a></li>
        	<li><a tabindex="-1"><label for="Time">time</label></li><li><input type=number id="Time" name="Time" value="Time" min="1" max="100000"></a></li>
        	<li><a href="javascript:noiseStart()" class="select">start</a></li>
            <li><a href="javascript:noiseStop()" class="select">stop</a></li>
        </ul>
	</a>
	</li>
	
</ul>
<!-- piano -->
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
 </div>
<div class="box">
<h2 class="box1">曲リスト<p>作曲者名　　曲名　　再生　　DL　　良いねボタン　　お気に入り</p></h2>

<h2 class="box2">ランキング<p>順位　曲名　再生　良いね数　詳細　お気に入り</p></h2>
<script type="text/javascript" src="/pikopiko/js/piano.js"></script>
<script type="text/javascript" src="/pikopiko/js/ChangeStyle.js"></script>
<script type="text/javascript" src="/pikopiko/js/AnimatedMenu.js"></script>
</div>
</body>
</html>