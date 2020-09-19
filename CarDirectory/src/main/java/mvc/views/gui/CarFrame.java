package mvc.views.gui;

import javax.swing.*;
import java.awt.*;

public class CarFrame extends JFrame {
    private final String TITLE = "Car Directory";
    private int frameWidth;
    private int frameHeight;
    private JPanel mainPanel;

    public CarFrame() throws HeadlessException {
        init();
    }

    private void init() {
        setTitle(TITLE);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        frameWidth = dimension.width / 3;
        frameHeight = (dimension.height / 5) * 2;
        setSize(frameWidth, frameHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        mainPanel = new JPanel(new BorderLayout());
        getContentPane().add(mainPanel);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

}
