package com.yengas.osusongpickergui.export;

import java.io.File;
import java.util.ArrayList;

public abstract class Exporter<T> {
	protected ArrayList<T> singles;
	protected ExportProgressListener listener;

	public interface ExportProgressListener{
		public void update(File file);
	}

	public void setProgressListener(ExportProgressListener listener){
		this.listener = listener;
	}
	
	public Exporter(){
		this.singles = new ArrayList<T>();
	}
	
	public void add(String path){
		add(path, null, false);
	}
	
	public void add(String path, String name){
		add(path, name, false);
	}
	
	public void add(String path, String name, boolean extract){
		File input = new File(path);
		this.add(input, name, "", extract);
	}
	
	private void add(File input, String name, String base, boolean extract){
		if(name == null) name = input.getName();
		
		if(input.isFile()){
			singles.add(this.getSingle(input, base + name));
		}else if(input.isDirectory()){
			File[] files = input.listFiles();
			
			for(File file : files) this.add(file, null, extract ? base : base + name + '/', false);
		}
	}

	protected void updateProgress(File file){
		if(this.listener != null) this.listener.update(file);
	}

	protected abstract T getSingle(File input, String name);
	public abstract boolean output(File file) throws Exception;
	public abstract String getExtension();
	public abstract String getInitialName();
}
