package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Recorder implements Serializable {
 private int id;
 private String userName;
 private String title;
 private int mode;
 private ArrayList<Integer> orderKeys;
 private ArrayList<Integer> orderTimes;
 

 public Recorder(String title,String useName,int mode,String orderKeys,String orderTimes) {
	// TODO 自動生成されたコンストラクター・スタブ
	// まずstrArraySubに1文字多い配列を作る
	 	String[] strArrayKeys = new String[orderKeys.length()];	 
		// 変数strの長さ分回す
		for (int i = 0; i < orderKeys.length(); i++) {
		    // strの先頭から1文字ずつString型にして取り出す
		    String str = String.valueOf(orderKeys.charAt(i));
		    // 配列に順番に格納する
		    strArrayKeys[i] = str;
		}
	 	String[] strArrayTimes = new String[orderTimes.length()];	 
		// 変数strの長さ分回す
		for (int i = 0; i < orderTimes.length(); i++) {
		    // strの先頭から1文字ずつString型にして取り出す
		    String str = String.valueOf(orderTimes.charAt(i));
		    // 配列に順番に格納する
		    strArrayTimes[i] = str;
		}
		 this.userName = useName;
		 this.title = title;
		 this.mode = mode;
		 this.orderKeys.toArray(strArrayKeys);
		 this.orderTimes.toArray(strArrayTimes);
}

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public ArrayList<Integer> getOrderKeys() {
	return orderKeys;
}
public void setOrderKeys(ArrayList<Integer> orderKeys) {
	this.orderKeys = orderKeys;
}
public ArrayList<Integer> getOrderTimes() {
	return orderTimes;
}
public void setOrderTimes(ArrayList<Integer> orderTimes) {
	this.orderTimes = orderTimes;
}
 
}
