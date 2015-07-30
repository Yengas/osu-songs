package com.yengas.osusongpickergui.nodes;

import java.lang.reflect.Field;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import com.yengas.osusongpicker.db.Beatmap;

public class BeatmapView extends VBox {

	public BeatmapView(Beatmap beatmap){
		Field[] fields = Beatmap.class.getDeclaredFields();
		
		for(Field field : fields){
			try{
				boolean accessible = field.isAccessible();
				
				if(!accessible) field.setAccessible(true);
				Object value = field.get(beatmap);
				
				if(value != null){
					String stringValue = value.toString();
					
					this.getChildren().add( new Label(field.getName() + ": " + stringValue.substring(0, Math.min(stringValue.length(), 30)) + (stringValue.length() > 30 ? "..." : "")) );
				}
				
				if(!accessible) field.setAccessible(false);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
}
