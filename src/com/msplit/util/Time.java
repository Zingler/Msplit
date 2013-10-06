package com.msplit.util;

public class Time {
	public int tenthsecond, second, minute, hour;

	public Time(int t) {
		hour = t / (60 * 60 * 10);
		t %= 60 * 60 * 10;
		minute = t / (60 * 10);
		t %= 60 * 10;
		second = t / 10;
		t %= 10;
		tenthsecond = t;
	}

	public Time(int hour, int minute, int second, int tenthsecond) {
		super();
		this.hour = hour;
		this.minute = minute;
		this.second = second;
		this.tenthsecond = tenthsecond;
	}

	public int toInt() {
		return ((hour * 60 + minute) * 60 + second) * 10 + tenthsecond;
	}
}
