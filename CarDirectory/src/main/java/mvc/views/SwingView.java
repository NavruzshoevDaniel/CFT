package mvc.views;

import mvc.EventListener;
import mvc.controllers.SwingController;
import mvc.models.CarModel;
import sql.models.car.Car;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SwingView implements EventListener {
    private static final Logger logger = Logger.getLogger(SwingView.class.getName());
    private String title = "Car Directory";
    private int frameWidth;
    private int frameHeight;

    private CarModel carModel;
    private SwingController controller;

    private JFrame appFrame;
    private JButton addButton;
    private JButton removeButton;
    private JButton editButton;
    private JPanel mainPanel;
    private JPanel buttonsPanel;
    private JTable table;
    public DefaultListModel<String> listModel;


    public SwingView(CarModel carModel, SwingController controller) {
        this.carModel = carModel;
        this.controller = controller;
        carModel.registerObserver("list", this);
    }

    public void createView() {
        EventQueue.invokeLater(() -> {
            logger.log(Level.INFO, "Start createView");
            setSwingSettings();
            logger.log(Level.INFO, "Finish createView");
        });
    }

    private void setSwingSettings() {
        createFrame();
        createButtonsPanel();
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
        createList();
        //mainPanel.add(cars, BorderLayout.CENTER);
        appFrame.setVisible(true);
    }

    private void createFrame() {
        appFrame = new JFrame(title);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        frameWidth = dimension.width / 3;
        frameHeight = (dimension.height / 5) * 2;
        appFrame.setSize(frameWidth, frameHeight);
        appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        appFrame.setLocationRelativeTo(null);
        mainPanel = new JPanel(new BorderLayout());
        appFrame.getContentPane().add(mainPanel);
    }

    private void createButtonsPanel() {
        buttonsPanel = new JPanel(new GridLayout(0, 3, 5, 5));

        addButton = new JButton("Add");
        removeButton = new JButton("Remove");
        editButton = new JButton("Edit");
        addActionListeners();

        buttonsPanel.add(addButton);
        buttonsPanel.add(removeButton);
        buttonsPanel.add(editButton);
    }

    private void addActionListeners() {
        addButton.addActionListener(e -> controller.add());
        removeButton.addActionListener(e -> controller.remove());
        editButton.addActionListener(e -> controller.edit());
    }

    private void createList() {
        listModel = new DefaultListModel<>();
        table = new JTable();
    }

    @Override
    public void update() {
        switch (carModel.getState()) {

            case LOADING_FROM_DATABASE -> {
                for (Car car : carModel.getCars()) {
                    //cars.add
                }
            }
            case ADDING_TO_DATABASE -> {
            }
            case REMOVING_FROM_DATABASE -> {
            }
            case EDITING_CAR_FROM_DATABASE -> {
            }
        }
    }

}
