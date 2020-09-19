package mvc.views;

import mvc.EventListener;
import mvc.controllers.SwingController;
import mvc.models.CarModel;
import mvc.models.ModelState;
import mvc.models.Row;
import mvc.views.gui.CarFrame;
import mvc.views.gui.CarsTable;
import mvc.views.gui.EditableTableModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sql.models.car.Car;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;


public class SwingView implements EventListener {
    private static final Logger LOGGER = LogManager.getLogger(SwingView.class.getName());

    private CarModel carModel;
    private SwingController controller;

    private CarFrame appFrame;
    private JButton addButton;
    private JButton removeButton;
    private int countButtons = 2;
    private JPanel mainPanel;
    private JPanel buttonsPanel;
    private CarsTable carsTable;
    public EditableTableModel tableModel;


    public SwingView(CarModel carModel, SwingController controller) {
        this.carModel = carModel;
        this.controller = controller;
        carModel.registerObserver("list", this);
    }

    public void createView() {
        EventQueue.invokeLater(() -> {
            LOGGER.info("Start createView");
            setSwingSettings();
            LOGGER.info("Finish createView");
        });
    }

    private void setSwingSettings() {
        appFrame = new CarFrame();
        mainPanel = new JPanel(new BorderLayout());
        appFrame.getContentPane().add(mainPanel);
        createButtonsPanel();
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
        tableModel = new EditableTableModel();
        tableModel.setColumnEditable(0, false);
        carsTable = new CarsTable(tableModel);

        JScrollPane scrollTable = new JScrollPane(carsTable,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainPanel.add(scrollTable, BorderLayout.CENTER);
        appFrame.setVisible(true);
        addActionListeners();
    }

    private void createButtonsPanel() {
        buttonsPanel = new JPanel(new GridLayout(0, countButtons, 5, 5));

        addButton = new JButton("Add");
        removeButton = new JButton("Remove");

        buttonsPanel.add(addButton);
        buttonsPanel.add(removeButton);
    }

    private void addActionListeners() {
        // addButton.addActionListener(e -> ));
        // removeButton.addActionListener(e -> controller.remove());
        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (carModel.getState() != ModelState.VIEWING) {
                    int row = e.getFirstRow();
                    Car editCar = new Car();
                    int columnIter = 0;
                    for (String columnName : carModel.getTableColumnNames()) {
                        Object value = tableModel.getValueAt(row, columnIter);
                        columnIter+=1;
                        editCar.getConsumer(columnName).accept(value);
                    }
                    controller.edit(editCar);
                }
            }
        });
    }


    @Override
    public void update() {

        EventQueue.invokeLater(() -> {
            switch (carModel.getState()) {

                case VIEWING -> {
                    configureTable();
                }
            }
        });

    }

    private void configureTable() {
        for (String columnName : carModel.getTableColumnNames()) {
            tableModel.addColumn(columnName);
        }
        for (Row row : carModel.getRows()) {
            tableModel.addRow(row.toArray());
        }
        controller.setState(ModelState.WAITING);
        carsTable.updateUI();
    }

}
