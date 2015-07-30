package com.yengas.osusongpickergui.filters;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import com.yengas.osusongpicker.BeatmapAttribute;
import com.yengas.osusongpicker.SearchOption;
import com.yengas.osusongpicker.criterias.Criteria;
import com.yengas.osusongpickergui.utils.Utils;

public abstract class Filter extends BorderPane implements EventHandler<ActionEvent> {

	private Label attributeName;
	private ComboBox<Criteria> criterias;
	private BeatmapAttribute attribute;
	private Button closeButton;

	
	public Filter(BeatmapAttribute attribute){
		this.attribute = attribute;
		this.closeButton = new Button("-");
		this.attributeName = new Label(attribute.toString());
		this.criterias = new ComboBox<Criteria>();
		this.criterias.setOnAction(this);
		
		attributeName.setFont(new Font("Tahoma", 14));	
		attributeName.setPadding(new Insets(0, 5, 0, 5));
		attributeName.setPrefWidth(120);
	}

	public abstract void updateInputs(Criteria criteria);
	public abstract ArrayList<Node> getInputs();
	public abstract Object[] getInputValues();
	public void setOnCloseClicked(EventHandler<ActionEvent> handler){
		closeButton.setOnAction(handler);
	}
	
	public SearchOption getOption(){
		Criteria criteria = criterias.getValue();
		criteria.setParameters(this.getInputValues());
		return new SearchOption(attribute, criteria);
	}
	
	protected void initialize(){
		ObservableList<Criteria> criteriaList = FXCollections.observableArrayList();
		Class<? extends Criteria>[] criteriaClasses = attribute.getApplyableCriterias();

		for(Class<? extends Criteria> klass : criteriaClasses)
			try{
				criteriaList.add(klass.newInstance());
			}catch(Exception e){ e.printStackTrace(); }

		criterias.setItems(criteriaList);
		criterias.setValue(criteriaList.get(0));
		this.criteriaChanged(criteriaList.get(0));
	}

	private void criteriaChanged(Criteria newCriteria){
		HBox hBox = new HBox();
		this.getChildren().clear();
		this.updateInputs(newCriteria);
		this.setLeft(attributeName);
		hBox.setPrefWidth(240);
		criterias.setPrefWidth(68);
		
		hBox.getChildren().addAll(criterias);
		for(Node input : this.getInputs()) hBox.getChildren().add(input);
		hBox.getChildren().add(closeButton);
		this.setRight(hBox);
	}

	@Override
	public void handle(ActionEvent event) {	
		this.criteriaChanged(criterias.getValue());
		this.requestFocus();
	}

	public static Filter createFilter(BeatmapAttribute attribute){
		if(attribute.getType() == Float.class)
			return new NumeralFilter(attribute);
		else if(attribute.getType() == String.class)
			return new StringFilter(attribute);
		else if(attribute.getType().isEnum())
			return new EnumFilter(attribute);

		return null;
	}
}
