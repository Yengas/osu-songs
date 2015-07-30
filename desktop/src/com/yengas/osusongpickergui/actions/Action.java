package com.yengas.osusongpickergui.actions;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public abstract class Action extends Button implements EventHandler<ActionEvent>{
	private String buttonText;
	private Label statusLabel;

	public Action(String buttonText, Label statusLabel){
		this.buttonText = buttonText;
		this.statusLabel = statusLabel;

		this.setText( buttonText );
		this.setOnAction(this);
	}
	
	public void handle(ActionEvent event){
		new Thread(() -> {
			Platform.runLater(() -> { setDisabled(true); updateStatus("Started."); });
			doWork();
		}).start();
	};

	protected void finished(){
		Platform.runLater(() -> { setDisabled(false); updateStatus("Finished"); });
	}

	protected void updateStatus(String status){
		Platform.runLater(() -> {
			statusLabel.setText(String.format("'%s': %s", buttonText, status));
		});
	}
	
	public abstract void doWork();
}
