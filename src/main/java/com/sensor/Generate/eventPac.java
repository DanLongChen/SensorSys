package com.sensor.Generate;

public class eventPac {
	int type;    //0为单播，1为广播
	int sendId;
	int revId;
	int state;
	int round;
	int tranPac;
	int revPac;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getSendId() {
		return sendId;
	}
	public void setSendId(int sendId) {
		this.sendId = sendId;
	}
	public int getRevId() {
		return revId;
	}
	public void setRevId(int revId) {
		this.revId = revId;
	}

	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getRound() {
		return round;
	}
	public void setRound(int round) {
		this.round = round;
	}
	public int getTranPac() {
		return tranPac;
	}
	public void setTranPac(int tranPac) {
		this.tranPac = tranPac;
	}
	public int getRevPac() {
		return revPac;
	}
	public void setRevPac(int revPac) {
		this.revPac = revPac;
	}

	
}
