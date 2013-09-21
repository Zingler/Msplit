package com.example.zsplit.splitmodel;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;

public class SplitListUtil {
	private Context context;

	public SplitListUtil(Context c){
		this.context = c;
	}
	
	public SplitList SampleSplitList(){
		SplitList result = new SplitList();
		result.add(new Split("Escape", 2966));
		result.add(new Split("Kakriko", 3685));
		result.add(new Split("Bottle", 4971));
		result.add(new Split("Deku", 6308));
		result.add(new Split("Gohma", 7648));
		result.add(new Split("Ganandorf", 8366));
		result.add(new Split("Collapse", 9582));
		result.add(new Split("Ganon", 11763));
		return result;
	}
	
	public void save(SplitList list) throws IOException{
		FileOutputStream fos = context.openFileOutput(list.filename, Context.MODE_PRIVATE);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(list);
		oos.close();
	}
	
	public SplitList load(String name) throws IOException, ClassNotFoundException{
		FileInputStream fos = context.openFileInput(name);
		ObjectInputStream oos = new ObjectInputStream(fos);
		SplitList list = (SplitList)oos.readObject();
		oos.close();
		return list;
	}
	
	public String[] listSplits(){
		return context.fileList();
	}
}
