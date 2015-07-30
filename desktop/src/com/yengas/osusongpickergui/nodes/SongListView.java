package com.yengas.osusongpickergui.nodes;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import com.yengas.osusongpicker.db.Beatmap;

public class SongListView extends ListView<Beatmap> implements EventHandler<MouseEvent>{
	
	private static String beatmapViewTitleFormat = "%s [%s] by @%s";
	
	public SongListView(){
		super();
		this.setOnMouseClicked(this);
		this.setItems(FXCollections.observableList(new ArrayList<Beatmap>()));
	}

	@Override
	public void handle(MouseEvent event) {
		if(event.getClickCount() == 2){
			Stage stage = new Stage();
			Beatmap beatmap = this.getSelectionModel().getSelectedItem();

			if(beatmap == null) return;
			stage.setTitle(String.format(beatmapViewTitleFormat, beatmap.getTitle(), beatmap.getDifficulty(), beatmap.getAuthor()));
			stage.setScene(new Scene(new BeatmapView(beatmap)));
			stage.sizeToScene();
			stage.show();
			stage.toFront();
		}
	}
}
