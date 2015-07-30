package com.yengas.osusongpickergui.actions;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

import javafx.application.Platform;
import javafx.collections.ObservableList;

import javax.swing.JOptionPane;

import com.yengas.osusongpicker.db.Beatmap;
import com.yengas.osusongpickergui.nodes.SongListView;
import com.yengas.osusongpickergui.utils.Utils;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;

public class RemoveSongsAction extends Action{
	
	private SongListView songList;
	private File baseDirectory;
	private Alert dialog;

	public RemoveSongsAction(SongListView songList, File baseDirectory, Label statusLabel){
		super("Remove Songs", statusLabel);
		this.songList = songList;
		this.baseDirectory = baseDirectory;
		dialog = new Alert(Alert.AlertType.CONFIRMATION, "All of the song folders beloging to the beatmaps on the list will be deleted. Are you sure?", ButtonType.YES, ButtonType.NO);
		dialog.setHeaderText("Remove Songs");
		dialog.setTitle("Warning! Removing songs!");
	}
	
	@Override
	public void doWork() {
		Platform.runLater(() -> {
			Optional<ButtonType> result = dialog.showAndWait();

			new Thread(() -> {
				if(result.get() == ButtonType.YES){
					ArrayList<Beatmap> toDelete = new ArrayList<Beatmap>();
					ObservableList<Beatmap> beatmaps = songList.getItems();

					for(Beatmap beatmap : beatmaps){
						File directory = new File(baseDirectory, beatmap.getSongPath());
						boolean deleted = Utils.deleteDirectory(directory);

						if(deleted || !directory.exists()) toDelete.add(beatmap);
					}

					Platform.runLater(() -> {
						songList.getItems().removeAll(toDelete);
					});
				}
				finished();
			}).start();
		});
	}

}
