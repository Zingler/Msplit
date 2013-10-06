package com.msplit;

public class Util {
	public static String formatTimerString(int time) {
		int t = Math.abs(time);
		int hours = t / (60 * 60 * 10);
		t %= 60 * 60 * 10;
		int minutes = t / (60 * 10);
		t %= 60 * 10;
		int seconds = t / 10;
		t %= 10;
		int hundreths = t;
		String s = String.format("%02d:%02d:%02d.%d", hours, minutes, seconds, hundreths);
		return time >= 0 ? s : "-" + s;
	}

	public static String formatTimerStringNoZeros(int time) {
		return formatTimerStringNoZeros(time, false);
	}

	public static String formatTimerStringNoZeros(int time, boolean alwaysHaveSign) {
		int t = Math.abs(time);
		int hours = t / (60 * 60 * 10);
		t %= 60 * 60 * 10;
		int minutes = t / (60 * 10);
		t %= 60 * 10;
		int seconds = t / 10;
		t %= 10;
		int tenths = t;

		boolean found = false;
		boolean first = true;
		String text = "";
		if (hours > 0 || found) {
			text += String.format("%" + (first ? "" : "02") + "d:", hours);
			found = true;
			first = false;
		}
		if (minutes > 0 || found) {
			text += String.format("%" + (first ? "" : "02") + "d:", minutes);
			found = true;
			first = false;
		}

		text += String.format("%" + (first ? "" : "02") + "d.", seconds);
		found = true;
		first = false;

		text += String.format("%" + "d", tenths);

		if (time > 0) {
			return alwaysHaveSign ? "+" + text : text;
		} else if (time < 0) {
			return "-" + text;
		} else {
			return text;
		}
	}
}
