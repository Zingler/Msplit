package com.msplit.urnmodel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

import com.google.gson.Gson;
import com.msplit.homepage.RecentSplit;
import com.msplit.util.PriorityList;

import android.content.Context;
import android.util.Log;

public class UrnUtil {
	private static UrnUtil instance;
	private final String SPLIT_DIR = "splits";
	private final String MOST_RECENT_SPLITS = "most_recent";
	private Gson gson;
	private File splitDir;
	private PriorityList recentSplits;
	private Context context;

	public static UrnUtil getInstance(Context c) {
		if (instance == null) {
			instance = new UrnUtil(c);
			return instance;
		} else {
			return instance;
		}
	}

	private UrnUtil(Context context) {
		this.gson = new Gson();
		this.context = context;
		splitDir = context.getDir(SPLIT_DIR, Context.MODE_PRIVATE);
		Log.d("file", splitDir.toString());
	}

	public Urn sampleUrn() {
		Urn result = new Urn();
		result.setFilename("Default Urn");
		result.add(new UrnSplit("Escape", 2966));
		result.add(new UrnSplit("Kakriko", 3685));
		result.add(new UrnSplit("Bottle", 4971));
		result.add(new UrnSplit("Deku", 6308));
		result.add(new UrnSplit("Gohma", 7648));
		result.add(new UrnSplit("Ganandorf", 8366));
		result.add(new UrnSplit("Collapse", 9582));
		result.add(new UrnSplit("Ganon", 11763));
		return result;
	}

	public void save(Urn list) throws IOException {
		list.sort();
		String output = gson.toJson(list);
		File f = new File(splitDir + "/" + list.getFilename());
		FileOutputStream fos = new FileOutputStream(f);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		Log.d("file", f.toString());
		oos.writeObject(output);
		oos.close();
		getRecentSplits().putTop(new RecentSplit(list));
		saveRecentSplits();
	}

	public Urn load(String name) throws IOException, ClassNotFoundException {
		File f = new File(splitDir + "/" + name);
		FileInputStream fos = new FileInputStream(f);
		ObjectInputStream oos = new ObjectInputStream(fos);
		String result = (String) oos.readObject();
		oos.close();
		Urn list = gson.fromJson(result, Urn.class);
		Log.d("file", f.toString());
		list.sort();
		getRecentSplits().putTop(new RecentSplit(list));
		saveRecentSplits();
		return list;
	}

	public String[] listUrns() {
		String [] list = splitDir.list();
		Arrays.sort(list);
		return list;
	}

	public PriorityList getRecentSplits() {
		if (recentSplits == null) {
			ObjectInputStream oos;
			try {
				FileInputStream fos = context.openFileInput(MOST_RECENT_SPLITS);
				oos = new ObjectInputStream(fos);
				String result = (String) oos.readObject();
				oos.close();
				PriorityList list = (PriorityList) gson.fromJson(result, PriorityList.class);
				recentSplits = list;
			} catch (Exception e) {
				Log.e("Load", "Was not able to load recent splits : " + e.getMessage());
				recentSplits = new PriorityList();
			}
		}
		return recentSplits;
	}

	public void saveRecentSplits() {
		String output = gson.toJson(recentSplits);
		try {
			FileOutputStream fos = context.openFileOutput(MOST_RECENT_SPLITS, Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(output);
			oos.close();
		} catch (Exception e) {
			Log.e("Load", "Was not able to save recent splits : " + e.getMessage());
		} 
	}

	public void delete(String name) {
		File f = new File(splitDir + "/" + name);
		f.delete();
		RecentSplit r = new RecentSplit();
		r.setFilename(name);
		getRecentSplits().remove(r);
		saveRecentSplits();
	}
}
