package com.yengas.osusongpickergui.actions;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.collections.ObservableList;

import com.yengas.osusongpicker.OsuSongPicker;
import com.yengas.osusongpicker.SearchOption;
import com.yengas.osusongpicker.db.Beatmap;
import com.yengas.osusongpickergui.nodes.FilterList;
import com.yengas.osusongpickergui.nodes.SongListView;
import javafx.scene.control.Label;

public class FilterAction extends Action {
	private OsuSongPicker picker;
	private FilterList filterList;
	private SongListView songList;
	
	public FilterAction(OsuSongPicker picker, FilterList filterList, SongListView songList, Label statusLabel){
		super("Filter and add", statusLabel);
		this.picker = picker;
		this.filterList = filterList;
		this.songList = songList;
	}

	@Override
	public void doWork() {
		SearchOption[] options = filterList.getSearchOptions();
		ArrayList<Beatmap> result = picker.pick(options);
		
		Platform.runLater(() -> {
			ObservableList<Beatmap> items = songList.getItems();
			
			items.removeAll( result );
			items.addAll( result );
			finished();
		});
	}

}
