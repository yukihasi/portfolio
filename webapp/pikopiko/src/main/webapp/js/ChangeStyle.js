/**
 * 
 */
function changeStyleSheet(num) {
  var elem = document.getElementById("PageStyleSheet");
  if(num == 1){
      elem.href = "/pikopiko/css/homestyle.css";
      var str = "8bitモード";
      document.getElementById('edit_area').textContent = str ;
  }
  else if(num == 2){
      elem.href = "/pikopiko/css/homestyle2.css";
      var str = "パイプオルガンモード";
      document.getElementById('edit_area').textContent = str ;
  }
}
