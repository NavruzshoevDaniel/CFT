package mvc.views.gui;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import java.awt.*;


public class CarsTable extends JTable {
    private Color selectionColor = new Color(120, 168, 240);
    private JTableHeader header;

    public CarsTable(TableModel dm) {
        super(dm);
        header = getTableHeader();
        header.setFont(new Font("Serif", Font.PLAIN, 15));
        setGridColor(Color.BLACK);
        setSelectionBackground(selectionColor);
    }
}
