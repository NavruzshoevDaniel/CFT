package mvc.views.gui;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;


public class AddingFieldsPanel<T extends JTextField> extends JPanel {
    private Map<String, T> fieldHashMap;

    public AddingFieldsPanel(LayoutManager layout, boolean isDoubleBuffered,
                             HashMap<String, T> fieldHashMap) {
        super(layout, isDoubleBuffered);
        this.fieldHashMap = fieldHashMap;
        init();
    }

    public AddingFieldsPanel(LayoutManager layout, Map<String, T> fieldHashMap) {
        super(layout);
        this.fieldHashMap = fieldHashMap;
        init();
    }

    public AddingFieldsPanel(boolean isDoubleBuffered, Map<String, T> fieldHashMap) {
        super(isDoubleBuffered);
        this.fieldHashMap = fieldHashMap;
        init();
    }

    public AddingFieldsPanel(Map<String, T> fieldHashMap) {
        this.fieldHashMap = fieldHashMap;
        init();
    }


    private void init() {
        fieldHashMap.forEach((key, value) -> add(fieldHashMap.get(key)));
    }

    public JTextField getField(String nameField) {
        return fieldHashMap.get(nameField);
    }
}
