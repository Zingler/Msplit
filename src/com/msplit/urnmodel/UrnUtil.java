package com.msplit.urnmodel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.content.Context;
import android.util.Log;

public class UrnUtil {
	private final String SPLIT_DIR = "splits";
	private Context context;
	private Gson gson;
	private File splitDir;

	
	public UrnUtil(Context c){
		this.context = c;
		this.gson = new Gson();
		splitDir = context.getDir(SPLIT_DIR, Context.MODE_PRIVATE);
		Log.d("File", splitDir.toString());
	}
	
	public Urn sampleUrn(){
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
	
	public void save(Urn list) throws IOException{
		list.sort();
		String output = gson.toJson(list);
		File f = new File(splitDir+"/"+list.getFilename()); 
		FileOutputStream fos = new FileOutputStream(f);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		Log.d("save",f.toString());
		oos.writeObject(output);
		oos.close();
	}
	
	public Urn load(String name) throws IOException, ClassNotFoundException{
		File f = new File(splitDir+"/"+name);
		FileInputStream fos = new FileInputStream(f);
		ObjectInputStream oos = new ObjectInputStream(fos);
		String result = (String)oos.readObject();
		oos.close();
		Urn list = gson.fromJson(result, Urn.class);
		Log.d("load", f.toString());
		list.sort();
		return list;
	}
	
	public String[] listUrns(){
		return splitDir.list();
	}
}
