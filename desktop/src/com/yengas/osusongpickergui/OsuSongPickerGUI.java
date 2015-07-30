package com.yengas.osusongpickergui;

import java.io.File;

import com.yengas.osusongpickergui.utils.Utils;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.yengas.osusongpicker.OsuSongPicker;
import com.yengas.osusongpickergui.actions.ExportAction;
import com.yengas.osusongpickergui.actions.ClearAction;
import com.yengas.osusongpickergui.actions.FilterAction;
import com.yengas.osusongpickergui.actions.RemoveSongsAction;
import com.yengas.osusongpickergui.nodes.FilterList;
import com.yengas.osusongpickergui.nodes.SongListView;

public class OsuSongPickerGUI extends Application{
	public static void main(String[] args){
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		File osuDirectory = Utils.getOsuDirectory();

		if(OsuSongPicker.validateDirectory(osuDirectory) == false){
			new Alert(Alert.AlertType.ERROR, "Osu directory is not valid.", ButtonType.OK).showAndWait();
			System.exit(-1);
		}

		primaryStage.setTitle("Osu Song Manager");
		OsuSongPicker picker = new OsuSongPicker(osuDirectory.getAbsolutePath());
		
		BorderPane layout = new BorderPane();
		FilterList filterList = new FilterList();
		SongListView songList = new SongListView();
		VBox bottom = new VBox();
		HBox actionList = new HBox();
		Label statusLabel = new Label("osu!songs");

		bottom.setAlignment(Pos.CENTER);
		actionList.setAlignment(Pos.CENTER);
		actionList.getChildren().addAll(new FilterAction(picker, filterList, songList, statusLabel), new ClearAction(songList, statusLabel),
										new RemoveSongsAction(songList, new File(osuDirectory, "Songs"), statusLabel), new ExportAction(songList, new File(osuDirectory, "Songs"), statusLabel));

		bottom.getChildren().addAll(statusLabel, actionList);
		layout.setTop(filterList);
		layout.setCenter(songList);
		layout.setBottom(bottom);
		
		
		primaryStage.setScene(new Scene(layout));
		primaryStage.sizeToScene();
		primaryStage.show();
	}
}