package com.yengas.osusongpickergui.actions;

import java.io.File;
import java.util.Optional;
import java.util.TreeSet;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import com.yengas.osusongpicker.db.Beatmap;
import com.yengas.osusongpickergui.export.Exporter;
import com.yengas.osusongpickergui.export.PlaylistExport;
import com.yengas.osusongpickergui.export.ZipExport;
import com.yengas.osusongpickergui.nodes.SongListView;


public class ExportAction extends Action {
	
	private SongListView songList;
	private File baseDirectory;
	private FileChooser fileChooser;
	private Alert exportDialog;
    private	ButtonType folders = new ButtonType("Archive Folders"), songs = new ButtonType("Archive Music"), playList = new ButtonType("Export Playlist");
	private int counter = 0;
	
	public ExportAction(SongListView songList, File baseDirectory, Label statusLabel){
		super("Export", statusLabel);
		this.songList = songList;
		this.baseDirectory = baseDirectory;
		this.fileChooser = new FileChooser();
		this.exportDialog = new Alert(AlertType.CONFIRMATION);
		
		exportDialog.setTitle("Export option");
		exportDialog.setHeaderText("You have multiple choices about what you gonna do with your filtered songs.");
		exportDialog.setContentText("Please select one of them.");
		exportDialog.getButtonTypes().clear();
		exportDialog.getButtonTypes().addAll(folders, songs, playList);
	}
	
	private void initializeChooser(Exporter<?> exporter){
		fileChooser.setTitle("Where to save?");
		fileChooser.setInitialFileName(exporter.getInitialName());
		fileChooser.getExtensionFilters().setAll(new ExtensionFilter("Archive", exporter.getExtension()));
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
	}

	@Override
	public void doWork() {
		counter = 0;
		ObservableList<Beatmap> beatmaps = songList.getItems();
		if(beatmaps.size() == 0){
			finished();
			return;
		}

		Platform.runLater(() -> {
			Optional<ButtonType> type = exportDialog.showAndWait();
			final Exporter<?> archive = type.get() == playList ? new PlaylistExport() : new ZipExport();

			initializeChooser(archive);

			TreeSet<String> mapSet = new TreeSet<String>();
			File outFile = fileChooser.showSaveDialog(null);
			if(outFile == null){
				finished();
				return;
			}

			new Thread(() -> {
				for(Beatmap beatmap : beatmaps){
					if(mapSet.add( beatmap.getSongPath() )){
						// DOWNLOAD LINKS, PLAY LIST EXPORT...
						if(type.get() == folders) archive.add( baseDirectory.getAbsolutePath() + '/' + beatmap.getSongPath() );
						else archive.add( baseDirectory.getAbsolutePath() + '/' + beatmap.getSongPath() + '/' + beatmap.getSoundFile(), beatmap.getBeatmapId() + " " + beatmap.getSoundFile());
					}
				}

				try {
					long start = System.currentTimeMillis();
					archive.setProgressListener((file) -> {
						updateStatus(++counter + "/" + mapSet.size() + " exported.");
					});
					archive.output(outFile);
				} catch (Exception e) {
					e.printStackTrace();
				}

				finished();
			}).start();
		});
	}

}
