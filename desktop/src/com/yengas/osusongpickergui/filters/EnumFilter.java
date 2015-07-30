package com.yengas.osusongpickergui.filters;

import com.yengas.osusongpicker.BeatmapAttribute;
import com.yengas.osusongpicker.criterias.Criteria;
import com.yengas.osusongpicker.db.Beatmap;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Yengas on 25.07.2015.
 */
public class EnumFilter extends Filter {
    private ArrayList<ComboBox<Object>> choiceInputs;
    private HashMap<Object, Integer> ordinals;
    private Object[] enums;

    public EnumFilter(BeatmapAttribute attribute) {
        super(attribute);
        choiceInputs = new ArrayList<ComboBox<Object>>();
        ordinals = new HashMap<Object, Integer>();
        enums = attribute.getType().getEnumConstants();

        for(int i = 0; i < enums.length; ++i) {
            ordinals.put(enums[i], Integer.valueOf(i));
        }
        super.initialize();
    }

    @Override
    public void updateInputs(Criteria criteria) {
        choiceInputs.clear();

        for(int i = 0; i < criteria.getArgumentCount(); i++){
            ComboBox<Object> choices = new ComboBox<Object>();

            choices.setPrefWidth(150.0D / criteria.getArgumentCount());
            for(Object enm : enums) choices.getItems().add(enm);
            choices.getSelectionModel().select(0);
            choiceInputs.add(choices);
        }
    }

    @Override
    public ArrayList<Node> getInputs() {
        return (ArrayList<Node>) choiceInputs.clone();
    }

    @Override
    public Object[] getInputValues() {
        Integer[] values = new Integer[choiceInputs.size()];

        for(int i = 0; i < values.length; ++i){
            values[i] = ordinals.get(choiceInputs.get(i).getSelectionModel().getSelectedItem());
        }

        return values;
    }
}
