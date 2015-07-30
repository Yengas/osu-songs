package com.yengas.osusongpickergui.utils;

import com.yengas.osusongpicker.OsuSongPicker;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
	public static boolean deleteDirectory(File directory) {
	    if(directory.exists()){
	        File[] files = directory.listFiles();
	        if(null!=files){
	            for(int i=0; i<files.length; i++) {
	                if(files[i].isDirectory()) {
	                    deleteDirectory(files[i]);
	                }
	                else {
	                    files[i].delete();
	                }
	            }
	        }
	    }
	    return(directory.delete());
	}
	
	public static Float timeStringToSeconds(String value){
		float result = 0;
		Pattern pattern = Pattern.compile("([0-9]+)([yMdhms])");
		Matcher m = pattern.matcher(value);
		
		while(m.find()){
			int multiplier = 0;
			
			switch(m.group(2)){
				case "y":
					multiplier = 60 * 60 * 24 * 30 * 12;
					break;
				case "M":
					multiplier = 60 * 60 * 24 * 30;
					break;
				case "d":
					multiplier = 60 * 60 * 24;
					break;
				case "h":
					multiplier = 60 * 60;
					break;
				case "m":
					multiplier = 60;
					break;
				case "s":
					multiplier = 1;
					break;
			}
			
			result += multiplier * Float.parseFloat(m.group(1));
		}

		return result > 0 ? Float.valueOf(result) : null;
	}

	public static File getOsuDirectory(){
		File homeDirectory = new File(System.getProperty("user.home"));
		String[] defaults = new String[]{"C:/Program Files/osu!", "C:/Program Files (x86)/osu!", System.getProperty("user.home") + "/AppData/Local/osu!"};

		for(String defaultDirectoryPath : defaults){
			File defaultDirectory = new File(defaultDirectoryPath);

			if(OsuSongPicker.validateDirectory(defaultDirectory)) return defaultDirectory;
		}

		FileChooser chooser = new FileChooser();

		chooser.setInitialDirectory(homeDirectory);
		chooser.setTitle("Where is osu!.exe?");
		chooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Executable Application", "exe"));
		chooser.setInitialFileName("osu!.exe");

		File chosen = chooser.showOpenDialog(null);
		if(chosen == null) return chosen;
		if(chosen.isDirectory() == false)
			return chosen.getParentFile();
		return chosen;
	}
}
