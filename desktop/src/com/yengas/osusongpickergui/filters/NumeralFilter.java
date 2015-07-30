package com.yengas.osusongpickergui.filters;

import com.yengas.osusongpicker.BeatmapAttribute;
import com.yengas.osusongpicker.criterias.Criteria;
import com.yengas.osusongpickergui.utils.Utils;
import javafx.scene.Node;
import javafx.scene.control.TextField;

import java.util.ArrayList;

/**
 * Created by Yengas on 25.07.2015.
 */
public class NumeralFilter extends Filter {
    private ArrayList<TextField> inputs;

    public NumeralFilter(BeatmapAttribute attribute) {
        super(attribute);
        inputs = new ArrayList<TextField>();
        super.initialize();
    }

    @Override
    public void updateInputs(Criteria newCriteria) {
        inputs.clear();

		for(int i = 0; i < newCriteria.getArgumentCount(); i++){
			TextField input = new TextField();

			input.setPrefWidth(150.0D / newCriteria.getArgumentCount());
			input.textProperty().addListener((observable, oldValue, newValue) -> {
				if(!newValue.isEmpty() && !isInputValid(newValue)) input.setText(oldValue);
			});

			this.inputs.add( input );
		}
    }

    @Override
    public ArrayList<Node> getInputs() {
        return (ArrayList<Node>) inputs.clone();
    }

    @Override
    public Object[] getInputValues() {
        Float[] parameters = new Float[inputs.size()];

        for(int i = 0; i < inputs.size(); i++){
            TextField input = inputs.get(i);
            Float value = this.getValueFromString(input.getText());

            if(value == null) return null;
            parameters[i] = value;
        }

        return parameters;
    }

    private boolean isInputValid(String input){
        return input.matches("[-+]?[0-9]*\\.?[0-9]*") || input.matches("[0-9yhMdms ]+");
    }

    private Float getValueFromString(String value){
        if(value.isEmpty() || !isInputValid(value)) return null;
        value = value.replaceAll(" ", "");

        if(value.matches("[-+]?[0-9]*\\.?[0-9]*"))
            return Float.parseFloat(value);
        else if(value.matches("[0-9yMdhms]+")){
            return Utils.timeStringToSeconds(value);
        }

        return null;
    }
}
