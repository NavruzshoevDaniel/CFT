package mvc.views;

import mvc.EventListener;
import mvc.controllers.SwingController;
import mvc.models.CarModel;
import mvc.models.ModelState;
import mvc.models.Row;
import mvc.views.gui.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.TypeMismatchException;
import sql.models.car.Car;
import sql.models.car.exceptions.YearException;

import javax.swing.*;
import java.awt.*;
import java.util.*;


public class SwingView implements EventListener {
    private static final Logger LOGGER = LogManager.getLogger(SwingView.class.getName());

    private CarModel carModel;
    private SwingController controller;

    private CarFrame appFrame;
    private JButton addButton;
    private JButton removeButton;
    private int countButtons = 2;
    private JPanel mainPanel;
    private JPanel bottomPanel;
    private JPanel buttonsPanel;
    private CarsTable carsTable;
    public EditableTableModel tableModel;
    public AddingFieldsPanel<HintTextField> addingFieldsPanel;


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

        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        tableModel = new EditableTableModel();
        tableModel.setColumnEditable(0, false);
        carsTable = new CarsTable(tableModel);

        JScrollPane scrollTable = new JScrollPane(carsTable,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainPanel.add(scrollTable, BorderLayout.CENTER);
        appFrame.setVisible(true);
    }

    private void createButtonsPanel() {
        buttonsPanel = new JPanel(new GridLayout(0, countButtons, 5, 5));

        addButton = new JButton("Add");
        removeButton = new JButton("Remove");

        buttonsPanel.add(addButton);
        buttonsPanel.add(removeButton);
    }

    private void addActionListeners() {
        addButton.addActionListener(e -> {
            Car newCar = new Car();
            try {
                for (String columnName : carModel.getTableColumnNames()) {
                    if (!columnName.equals("id")) {
                        String value = addingFieldsPanel.getField(columnName).getText();
                        newCar.getSetter(columnName).accept(value);
                    }
                }
                carModel.addCar(newCar);
                Row row = new Row();
                for (String columnName : carModel.getTableColumnNames()) {
                    row.add(newCar.getGetter(columnName).get());
                }
                tableModel.addRow(row.toArray());
            } catch (NumberFormatException | ClassCastException ex) {
                JOptionPane.showMessageDialog(appFrame,
                        "You should enter all fields and correct input type",
                        "Inane warning",
                        JOptionPane.WARNING_MESSAGE);
            } catch (YearException ex) {
                JOptionPane.showMessageDialog(appFrame, ex.getMessage(),
                        "Inane warning",
                        JOptionPane.WARNING_MESSAGE);
            }
            carModel.setState(ModelState.WAITING);
        });
        removeButton.addActionListener(e -> {
            int[] selectedRows = carsTable.getSelectedRows();
            int firstRow = selectedRows[0];
            for (int row : selectedRows) {
                Car car = getCarRow(firstRow);
                controller.remove(car);
                tableModel.removeRow(firstRow);
                LOGGER.info(car + " has just removed");
            }
            controller.setState(ModelState.WAITING);

        });
        tableModel.addTableModelListener(e -> {
            LOGGER.info(carModel.getState());
            if (carModel.getState() == ModelState.WAITING) {
                int row = e.getFirstRow();
                int col = e.getColumn();
                try {
                    Car car = getCarRow(row);
                    controller.edit(car);
                    LOGGER.info(car + " has just edited");
                } catch (NumberFormatException | ClassCastException
                        | TypeMismatchException ex) {
                    JOptionPane.showMessageDialog(appFrame, "Wrong type Input",
                            "Inane warning",
                            JOptionPane.WARNING_MESSAGE);
                    LOGGER.warn(ex);
                    rollbackCell(row, col);
                } catch (YearException ex) {
                    JOptionPane.showMessageDialog(appFrame, ex.getMessage(),
                            "Inane warning",
                            JOptionPane.WARNING_MESSAGE);
                    rollbackCell(row, col);
                }
                controller.setState(ModelState.WAITING);
            }
        });
    }

    private void rollbackCell(int row, int column) {
        String name = tableModel.getColumnName(column);
        Long id = (Long) tableModel.getValueAt(row, 0);
        Car car = carModel.getById(id);
        tableModel.setValueAt(car.getGetter(name).get(), row, column);
    }

    @Override
    public void update() {

        EventQueue.invokeLater(() -> {
            switch (carModel.getState()) {

                case VIEWING -> {
                    configureTable();
                    configureFields();
                    addActionListeners();
                    bottomPanel.add(buttonsPanel);
                    controller.setState(ModelState.WAITING);
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

    private void configureFields() {
        Map<String, HintTextField> hintTextFieldHashMap = new LinkedHashMap<>();

        for (String columnName : carModel.getTableColumnNames()) {
            if (!columnName.equals("id")) {
                hintTextFieldHashMap.put(columnName, new HintTextField(columnName));
            }
        }
        addingFieldsPanel = new AddingFieldsPanel<>(
                new GridLayout(0, hintTextFieldHashMap.size(), 5, 5),
                hintTextFieldHashMap);
        bottomPanel.remove(buttonsPanel);
        bottomPanel.add(addingFieldsPanel);
    }

    public Car getCarRow(int row) {
        Car rowCar = new Car();
        int columnIter = 0;
        for (String columnName : carModel.getTableColumnNames()) {
            Object value = tableModel.getValueAt(row, columnIter);
            columnIter += 1;
            rowCar.getSetter(columnName).accept(value);
        }
        return rowCar;
    }

}
