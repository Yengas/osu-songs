package com.yengas.osusongpickergui.actions;

import javafx.application.Platform;

import com.yengas.osusongpickergui.nodes.SongListView;
import javafx.scene.control.Label;

public class ClearAction extends Action {
	private SongListView list;
	
	public ClearAction(SongListView songList, Label statusLabel) {
		super("Clear list", statusLabel);
		this.list = songList;
	}

	@Override
	public void doWork() {
		Platform.runLater(() -> {
			list.getItems().clear();
			finished();
		});
	}

}
