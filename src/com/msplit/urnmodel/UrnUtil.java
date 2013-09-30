package com.msplit.urnmodel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;

public class UrnUtil {
	private Context context;

	public UrnUtil(Context c){
		this.context = c;
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
		FileOutputStream fos = context.openFileOutput(list.getFilename(), Context.MODE_PRIVATE);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(list);
		oos.close();
	}
	
	public Urn load(String name) throws IOException, ClassNotFoundException{
		FileInputStream fos = context.openFileInput(name);
		ObjectInputStream oos = new ObjectInputStream(fos);
		Urn list = (Urn)oos.readObject();
		oos.close();
		list.sort();
		return list;
	}
	
	public String[] listUrns(){
		return context.fileList();
	}
}
