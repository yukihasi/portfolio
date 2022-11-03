/**
 * 
 */
 // 変数宣言
// Load compatibility script
/*load("nashorn:mozilla_compat.js");
// Import the java.awt package
importPackage(javax);
// Import the java.awt.Frame class
importClass(javax.sound.sampled.AudioFormat);
importClass(javax.sound.sampled.AudioSystem);
importClass(javax.sound.sampled.LineUnavailableException);
importClass(javax.sound.sampled.SourceDataLine);
*/
/*var CollectionsAndFiles = new JavaImporter(
    javax.sound.sampled.AudioFormat,
    javax.sound.sampled.AudioSystem,
    javax.sound.sampled.LineUnavailableException,
    javax.sound.sampled.SourceDataLine
    );
*/
//const path = "./audio/"             // オーディオファイルのパス
src="https://code.jquery.com/jquery.min.js"
const keyMap = [
    { pcKey: "q", pianoKey: 0 },{ pcKey: "2", pianoKey: 1 },{ pcKey: "w", pianoKey: 2 },{ pcKey: "e", pianoKey: 3 },{ pcKey: "4", pianoKey: 4 },{ pcKey: "r", pianoKey: 5 },{ pcKey: "5", pianoKey: 6 },{ pcKey: "t", pianoKey: 7 },{ pcKey: "y", pianoKey: 8 },{ pcKey: "7", pianoKey: 9 },{ pcKey: "u", pianoKey: 10 },{ pcKey: "8", pianoKey: 11 },{ pcKey: "i", pianoKey: 12 },{ pcKey: "9", pianoKey: 13 },{ pcKey: "o", pianoKey: 14 },{ pcKey: "p", pianoKey: 15 },{ pcKey: "-", pianoKey: 16 },{ pcKey: "@", pianoKey: 17 },{ pcKey: "^", pianoKey: 18 },{ pcKey: "[", pianoKey: 19 },{ pcKey: "z", pianoKey: 12 },{ pcKey: "s", pianoKey: 13 },{ pcKey: "x", pianoKey: 14 },{ pcKey: "c", pianoKey: 15 },{ pcKey: "f", pianoKey: 16 },{ pcKey: "v", pianoKey: 17 },{ pcKey: "g", pianoKey: 18 },{ pcKey: "b", pianoKey: 19 },{ pcKey: "n", pianoKey: 20 },{ pcKey: "j", pianoKey: 21 },{ pcKey: "m", pianoKey: 22 },{ pcKey: "k", pianoKey: 23 },{ pcKey: ",", pianoKey: 24 },{ pcKey: "l", pianoKey: 25 },{ pcKey: ".", pianoKey: 26 },{ pcKey: "/", pianoKey: 27 },{ pcKey: ":", pianoKey: 28 },{ pcKey: "\\", pianoKey: 29 }
]                                   // PCキーとピアノ鍵盤番号の紐づけ
const pianoSounds = []              // Audioオブジェクト        
const touchKeyNumlist = []          // タッチ中の鍵盤番号リスト
let clickedKeyNum = null            // クリック中の鍵盤番号リスト
const isKeyPressing = new Array(30) // ピアノ鍵盤ごとの押下状態
isKeyPressing.fill(false)           // 初期値 = false            
const intervalIds = new Array(30)   // 各オーディオフェードアウトのインターバルID
intervalIds.fill(null)              // 初期値 = null         
const pianoWrap = document.getElementById("piano-wrap")     // 鍵盤全体
const whiteKeys = document.querySelectorAll(".white-key")   // 白鍵
const blackKeys = document.querySelectorAll(".black-key")   // 黒鍵
const selectMusicTitle = document.getElementById("playBackTitle")
let modeChange = "1"
let inputKey = []
let recordFlag
let playBackFlag
let noiseFlag

//コンストラクタ関数を作成
/*
const music = function() {
	//select追加
	const option = document.createElement('option');
	option.value = musicTitle
	option.textContent = musicTitle
	selectMusicTitle.appendChild(option)
}

//インスタンスを作成
const title = new music();
console.log(yamada);//Person {firstName: "Yamada", birthYear: 1988}


function musicChangeId(num){
		modeChange = num
}
*/
// ピアノ鍵盤を押下した時の処理

//record

function playBackStart(){
	playBackFlag = "true"
    var send_data = new XMLHttpRequest() 
	send_data.open('POST', '/pikopiko/PlayBack')
	send_data.setRequestHeader('content-type', 'application/x-www-form-urlencoded;charset=UTF-8')
	send_data.send("&playBackFlag="+playBackFlag)
	console.log(playBackFlag,intervalTime)
}
function playBackStop(){
	playBackFlag = "false"
    var send_data = new XMLHttpRequest() 
	send_data.open('POST', '/pikopiko/PlayBack')
	send_data.setRequestHeader('content-type', 'application/x-www-form-urlencoded;charset=UTF-8')
	send_data.send("&playBackFlag="+playBackFlag)
	console.log(playBackFlag)
}




function recordStart(){
	recordFlag = "true"
	console.log(recordFlag)
}

function recordStop(){
	var intervalElement = document.getElementById("intervalTime")
	var timeElement = document.getElementById("Time")
	var sampleElement = document.getElementById("sampleRate")
	var titleElement = document.getElementById("musicTitle")
	var intervalTime = String(intervalElement.value)
	var time = String(timeElement.value)
	var sampleRate = String(sampleElement.value)
	var musicTitle = String(titleElement.value)
	recordFlag = "false"
    var send_data = new XMLHttpRequest() 
	send_data.open('POST', '/pikopiko/Recording')
	send_data.setRequestHeader('content-type', 'application/x-www-form-urlencoded;charset=UTF-8')
	send_data.send("&recordFlag="+recordFlag+"&noiseFlag="+noiseFlag+"&intervalTime="+intervalTime+"&time="+time+"&sampleRate="+sampleRate+"&musicTitle="+musicTitle)
	
	console.log(recordFlag,intervalTime,time,sampleRate,musicTitle)
//	sendRecord()
}

function noiseStart(){
	
	var intervalElement = document.getElementById("intervalTime")
	var timeElement = document.getElementById("Time")
	var sampleElement = document.getElementById("sampleRate")
	var intervalTime = String(intervalElement.value)
	var time = String(timeElement.value)
	var sampleRate = String(sampleElement.value)
	noiseFlag = "true"
    var send_data = new XMLHttpRequest()
	send_data.open('POST', '/pikopiko/Noise')
	send_data.setRequestHeader('content-type', 'application/x-www-form-urlencoded;charset=UTF-8')
	send_data.send("&noiseFlag="+noiseFlag+"&intervalTime="+intervalTime+"&time="+time+"&sampleRate="+sampleRate)
	console.log(noiseFlag,intervalTime,time,sampleRate)
}
function noiseStop(){
	noiseFlag = "false"
    var send_data = new XMLHttpRequest() 
	send_data.open('POST', '/pikopiko/Noise')
	send_data.setRequestHeader('content-type', 'application/x-www-form-urlencoded;charset=UTF-8')
	send_data.send("&noiseFlag="+noiseFlag)
}

/*
function sendRecord(){
        var send_data = new XMLHttpRequest() 
		send_data.open('POST', '/pikopiko/Piano')
		send_data.setRequestHeader('content-type', 'application/x-www-form-urlencoded;charset=UTF-8')
		send_data.send("skeyNum="+0+"&flag="+false+"&modeChange="+modeChange+"&startTime="+0)
		console.log(skeyNum,flag,modeChange,startTime)
}
*/
/*
function recording(keyNum){
	if(recordFlag){
		setKeyInput(keyNum)
	}
}

function setKeyInput(keyNum){
	inputKey.push(keyNum)
	console.log(inputKey)
}
*/

 class Record{
	pressPianoKey(keyNum){
	    if ( !isKeyPressing[keyNum] ){
	//		let element = document.getElementById('musicChangeId');
	//		element.options[1].selected = true;
			//musicmode取得
			/*
			let element = document.getElementsByClassName('musicChangeId');
			if(element == null){
				var modeChange = "1"
			}else{			
			var modeChange = String(element.dataset.value)
			}
			*/

	        var send_data = new XMLHttpRequest() 
			send_data.open('POST', '/pikopiko/Recording')
	        // 鍵盤を離している場合のみ続行(長押しによる連打防止)
	        isKeyPressing[keyNum] = true
	        document.querySelector(`[data-key-num="${keyNum}"]`).classList.add("pressing")
	        var skeyNum = String(keyNum)
	        var flag = String(isKeyPressing[keyNum])
			send_data.setRequestHeader('content-type', 'application/x-www-form-urlencoded;charset=UTF-8')
			send_data.send("skeyNum="+skeyNum+"&flag="+flag+"&modeChange="+modeChange+"&recordFlag="+recordFlag)
			console.log(skeyNum,flag,modeChange,recordFlag)
	   }
	}
	releasePianoKey(keyNum){
    if ( isKeyPressing[keyNum] ){
		//musicmode取得
		/*
		let element = document.getElementById('musicChangeId');
		if(element==null){
			var modeChange = "1"
		}else{			
			var modeChange = String(element.dataset.value)
		}
		*/
        // 鍵盤を押している場合のみ続行
        isKeyPressing[keyNum] = false
        document.querySelector(`[data-key-num="${keyNum}"]`).classList.remove("pressing")
        var skeyNum = String(keyNum)
        var flag = String(isKeyPressing[keyNum])
        var send_data = new XMLHttpRequest() 

		send_data.open('POST', '/pikopiko/Recording')
		send_data.setRequestHeader('content-type', 'application/x-www-form-urlencoded;charset=UTF-8')
		send_data.send("skeyNum="+skeyNum+"&flag="+flag+"&modeChange="+modeChange+"&recordFlag="+recordFlag)
		console.log(skeyNum,flag,modeChange,recordFlag)
//        soundStop(keyNum)
	    }
	}
	/*
	recordFlag(){
        var send_data = new XMLHttpRequest() 
		send_data.open('POST', '/pikopiko/Piano')
		send_data.setRequestHeader('content-type', 'application/x-www-form-urlencoded;charset=UTF-8')
		send_data.send("recordFlag="+recordFlag)
		console.log(recordFlag)
	}
	*/
}

class Main{
 pressPianoKey(keyNum){
    if ( !isKeyPressing[keyNum] ){
//		let element = document.getElementById('musicChangeId');
//		element.options[1].selected = true;
		//musicmode取得
		/*
		let element = document.getElementsByClassName('musicChangeId');
		if(element == null){
			var modeChange = "1"
		}else{			
		var modeChange = String(element.dataset.value)
		}
		*/
        var send_data = new XMLHttpRequest() 
		send_data.open('POST', '/pikopiko/Piano')
        // 鍵盤を離している場合のみ続行(長押しによる連打防止)
        isKeyPressing[keyNum] = true
        document.querySelector(`[data-key-num="${keyNum}"]`).classList.add("pressing")
        var skeyNum = String(keyNum)
        var flag = String(isKeyPressing[keyNum])
        var startTime = String(0)
		send_data.setRequestHeader('content-type', 'application/x-www-form-urlencoded;charset=UTF-8')
		send_data.send("skeyNum="+skeyNum+"&flag="+flag+"&modeChange="+modeChange+"&startTime="+startTime)
		console.log(skeyNum,flag,modeChange,startTime)
   }
}

// ピアノ鍵盤をはなした時の処理
 releasePianoKey(keyNum){
    if ( isKeyPressing[keyNum] ){
		//musicmode取得
		/*
		let element = document.getElementById('musicChangeId');
		if(element==null){
			var modeChange = "1"
		}else{			
			var modeChange = String(element.dataset.value)
		}
		*/
        // 鍵盤を押している場合のみ続行
        isKeyPressing[keyNum] = false
        document.querySelector(`[data-key-num="${keyNum}"]`).classList.remove("pressing")
        var skeyNum = String(keyNum)
        var flag = String(isKeyPressing[keyNum])
        var endTime = String(0)

        var send_data = new XMLHttpRequest() 
		send_data.open('POST', '/pikopiko/Piano')
		send_data.setRequestHeader('content-type', 'application/x-www-form-urlencoded;charset=UTF-8')
		send_data.send("skeyNum="+skeyNum+"&flag="+flag+"&modeChange="+modeChange+"&endTime="+endTime)
		console.log(skeyNum,flag,modeChange,endTime)
//        soundStop(keyNum)
    }
}
	
}
var main = new Main()
var record = new Record()

// 初期処理
// Audioオブジェクトを作成セット
/*
for ( i = 0; i <= 13; i++ ){
/*	with (CollectionsAndFiles) {
  var files = new LinkedHashSet();
  files.add(new File("Plop"));
  files.add(new File("Foo"));
  files.add(new File("w00t.js"));
}

	var sound = java.type("DigitalSoundGenerator")
	with(CollectionsAndFiles){
		var sourceDataLine = new SourceDataLine();	
	}
	var sourceDataLineArray = Java.type("sourceDataLine[]");
	var sdla = new sourceDataLineArray(13);
	sdla[i] = sound.sound(sound.getFreq(i),1000,0.8);
//	var audioFormat = new AudioFormat(SAMPLE_RATE,8,1,true,false);
//	var sourceDataLine = AudioSystem.getSourceDataLine(audioFormat);
/*	var sourceDataLine = new SourceDataLine();
	var sourceDataLineArray = Java.type("sourceDataLine[]");
	var sdla = new sourceDataLineArray(30);
	sdla[i].open(audioFormat);
	sdla[i].start();
	sdla[i].write = sound.sound(sound.getFreq(i),1000,0.8);
	sdla[i].close();
	}
	*/
// タッチ対応判定
if (window.ontouchstart === null) {
    // タッチ対応：タッチイベントのリスナーをセット
    pianoWrap.addEventListener("touchstart", function(){ handleTouchEvents(event) })
    pianoWrap.addEventListener("touchmove", function(){ handleTouchEvents(event) })
    pianoWrap.addEventListener("touchend", function(){ handleTouchEvents(event) })
    pianoWrap.addEventListener("touchcancel", function(){ handleTouchEvents(event) }) 
} else {
    // タッチ非対応：マウスイベントのリスナーをセット
    pianoWrap.addEventListener("mousedown", function(){ handleMouseEvents(event) })
    pianoWrap.addEventListener("mouseup", function(){ handleMouseEvents(event) })
    window.addEventListener("mousemove", function(){ handleMouseEvents(event) })
} 

// 座標(x,y)に応じた鍵盤番号を取得
function getKeyNum(x, y){
    // 黒鍵とタッチ箇所が重なるかチェック
    for ( let j = 0; j < blackKeys.length; j++ ){
        const KeyRect = blackKeys[j].getBoundingClientRect()
        if ( x >= window.pageXOffset + KeyRect.left  &&
             x <= window.pageXOffset + KeyRect.right &&
             y >= window.pageYOffset + KeyRect.top   &&
             y <= window.pageYOffset + KeyRect.bottom ){
            // タッチした鍵盤番号をセット
            return Number( blackKeys[j].dataset.keyNum )
        }
    } 
    // 白鍵とタッチ箇所が重なるかチェック
    for ( let j = 0; j < whiteKeys.length; j++ ){
        const KeyRect = whiteKeys[j].getBoundingClientRect()
        if ( x >= window.pageXOffset + KeyRect.left  &&
             x <= window.pageXOffset + KeyRect.right &&
             y >= window.pageYOffset + KeyRect.top   &&
             y <= window.pageYOffset + KeyRect.bottom ){
            // タッチした鍵盤番号をセット
            return Number( whiteKeys[j].dataset.keyNum )
        }
    }
    // ピアノ外のタッチの場合
    return null
}

// タッチイベント発生時の処理
function handleTouchEvents(event){

    if (typeof event.cancelable !== 'boolean' || event.cancelable) {
        event.preventDefault();
    }
    const BeforeKeyNumlist = JSON.parse(JSON.stringify(touchKeyNumlist)) 
    touchKeyNumlist.length = 0
    // 各接触ポイントから押下中の鍵盤番号リストを作成
    for ( let i = 0; i < event.touches.length; i++ ){
        const x = event.touches[i].pageX 
        const y = event.touches[i].pageY 
        let keyNum = getKeyNum(x, y)
        if ( keyNum !== null ){
            if ( !touchKeyNumlist.includes(keyNum) ){
                // リストに存在しなければ鍵盤番号をセット
                touchKeyNumlist.push(keyNum)
            }
        }
    } 
    // 新リストのみに存在 => 鍵盤を押下した処理
    for ( let i = 0; i < touchKeyNumlist.length; i++ ){
        if ( !BeforeKeyNumlist.includes(touchKeyNumlist[i]) ){ 
			if(recordFlag == "true"){
					record.pressPianoKey(touchKeyNumlist[i])
			}else{
		            main.pressPianoKey(touchKeyNumlist[i]) 		
			}
        }
    }
    // 旧リストのみに存在 => 鍵盤をはなした処理
    for ( let i = 0; i < BeforeKeyNumlist.length; i++ ){
        if ( !touchKeyNumlist.includes(BeforeKeyNumlist[i]) ){
			if(recordFlag == "true"){
		            record.releasePianoKey(BeforeKeyNumlist[i]) 		
			}else{
		            main.releasePianoKey(BeforeKeyNumlist[i]) 		
			}
        }
    }

}

// マウスイベント発生時の処理
function handleMouseEvents(event){
    // 左クリック以外は対象外
    if ( event.which !== 1 ){ return }
    const x = event.pageX 
    const y = event.pageY 
    let keyNum
    switch ( event.type ){
        case "mousedown":
            keyNum = getKeyNum(x, y)
            if ( keyNum !== null ){
	                if(recordFlag=="true"){
						record.pressPianoKey(keyNum)
					}else{
						main.pressPianoKey(keyNum)						
					}
				 }
            clickedKeyNum = keyNum
            break
        case "mouseup":
            if ( clickedKeyNum !== null ){
                keyNum = getKeyNum(x, y)
                if ( keyNum !== null ){ 
	                if(recordFlag=="true"){
							record.releasePianoKey(keyNum)
						}else{
							main.releasePianoKey(keyNum) 
						}	
					}
                clickedKeyNum = null
            }
            break
        case "mousemove":
            keyNum = getKeyNum(x, y)
            if ( keyNum !== null ){
                // マウスポインタ位置が直前の鍵盤以外の鍵盤上の場合
                if ( keyNum !== clickedKeyNum ){ 
					if(recordFlag == "true"){
	                    record.releasePianoKey(clickedKeyNum)
	                    record.pressPianoKey(keyNum) 						
					}else{
	                    main.releasePianoKey(clickedKeyNum)
	                    main.pressPianoKey(keyNum) 						
					}
                    clickedKeyNum = keyNum
                }
            } else {
                // マウスポインタ位置が鍵盤外の場合
                if(recordFlag=="true"){
	                    record.pressPianoKey(clickedKeyNum)
				}else{
	                main.releasePianoKey(clickedKeyNum)
				}
                clickedKeyNum = null
            }
            break
    }
}

// PCkeydown時の処理
document.onkeydown = function(event) {
    // 鍵盤番号を取得
    const obj = keyMap.find( (item) => item.pcKey === event.key )
    if ( typeof obj !== "undefined" ){
        // keyMapに含まれるキーの場合は後続処理実行
	 if(recordFlag=="true"){
		record.pressPianoKey(obj.pianoKey)
		}else{
	       main.pressPianoKey(obj.pianoKey)		
		}
    } 
}

// PCkeyup時の処理
document.onkeyup = function(event) {
    // 鍵盤番号を取得
    const obj = keyMap.find( (item) => item.pcKey === event.key )
    if ( typeof obj !== "undefined" ){
        // keyMapに含まれるキーの場合は後続処理実行
     if(recordFlag=="true"){
		record.releasePianoKey(obj.pianoKey)
	}else{
       main.releasePianoKey(obj.pianoKey)
	}
}


}