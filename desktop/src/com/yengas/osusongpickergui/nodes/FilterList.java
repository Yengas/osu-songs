package com.yengas.osusongpickergui.nodes;

import java.util.ArrayList;

import com.yengas.osusongpickergui.filters.Filter;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;

import com.yengas.osusongpicker.BeatmapAttribute;
import com.yengas.osusongpicker.SearchOption;

public class FilterList extends VBox {

	private ObservableList<BeatmapAttribute> avaliable;
	private Button addFilter;

	public FilterList(){
		avaliable = FXCollections.observableArrayList(BeatmapAttribute.values());
		addFilter = new Button("+");

		avaliable.addListener((ListChangeListener.Change<? extends BeatmapAttribute> change) -> {
			addFilter.setDisable(avaliable.size() == 0);
		});

		addFilter.setOnAction((mouseEvent) -> {
			ContextMenu contextMenu = new ContextMenu();

			for(BeatmapAttribute attribute : avaliable){
				MenuItem item = new MenuItem(attribute.name());

				item.setOnAction((menuEvent) -> {
					addFilter(attribute);
				});
				contextMenu.getItems().add(item);
			}

			addFilter.setContextMenu(contextMenu);
			contextMenu.show(addFilter, Side.RIGHT, 0, 0);
		});

		this.getChildren().add(addFilter);
		addFilter(BeatmapAttribute.Title, BeatmapAttribute.LastPlayed, BeatmapAttribute.SongLength);
	}

	public void addFilter(BeatmapAttribute... attributes){
		if(attributes == null) return;

		for(BeatmapAttribute attribute : attributes) {
			if(attribute == null || !avaliable.contains(attribute)) continue;
			Filter filter = Filter.createFilter(attribute);

			filter.setOnCloseClicked((mouseEvent) -> {
				this.getChildren().remove(filter);
				avaliable.add(attribute);
			});

			this.avaliable.remove(attribute);
			this.getChildren().add(this.getChildren().size() - 1, filter);
		}
	}

	public SearchOption[] getSearchOptions(){
		ArrayList<SearchOption> options = new ArrayList<SearchOption>();
		
		for(Node node : this.getChildren()){
			if(node instanceof Filter){
				SearchOption option = ((Filter)node).getOption();
				
				if(option != null) options.add(option);
			}
		}
		
		return options.toArray(new SearchOption[ options.size() ]);
	}
}
