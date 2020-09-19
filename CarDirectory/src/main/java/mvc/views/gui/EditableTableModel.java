package mvc.views.gui;

import javax.swing.table.DefaultTableModel;
import java.util.*;

public class EditableTableModel extends DefaultTableModel {

    private Map<Integer, Boolean> columnEditable = new HashMap<>();

    public EditableTableModel() {
    }

    public EditableTableModel(int rowCount, int columnCount) {
        super(rowCount, columnCount);
    }

    public EditableTableModel(Vector<?> columnNames, int rowCount) {
        super(columnNames, rowCount);
    }

    public EditableTableModel(Object[] columnNames, int rowCount) {
        super(columnNames, rowCount);
    }

    public EditableTableModel(Vector<? extends Vector> data, Vector<?> columnNames) {
        super(data, columnNames);
    }

    public EditableTableModel(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return columnEditable.get(column) != null ? columnEditable.get(column) : true;
    }

    public void setColumnEditable(int column, boolean editable) {
        columnEditable.put(column, editable);
    }


}
