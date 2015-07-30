package com.yengas.osusongpickergui.filters;

import com.yengas.osusongpicker.BeatmapAttribute;
import com.yengas.osusongpicker.criterias.Criteria;
import javafx.scene.Node;
import javafx.scene.control.TextField;

import java.util.ArrayList;

/**
 * Created by Yengas on 25.07.2015.
 */
public class StringFilter extends Filter {
    private TextField field;
    public StringFilter(BeatmapAttribute attribute) {
        super(attribute);
        field = new TextField();
        field.setPrefWidth(150);
        super.initialize();
    }

    @Override
    public void updateInputs(Criteria criteria) {
        field.setText("");
    }

    @Override
    public ArrayList<Node> getInputs() {
        ArrayList<Node> list = new ArrayList<Node>();

        list.add(field);
        return list;
    }

    @Override
    public Object[] getInputValues() {
        return new String[]{ field.getText().trim() };
    }
}
