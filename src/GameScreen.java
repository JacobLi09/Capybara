import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class GameScreen extends JPanel {

    private Image background;
    private final int PANEL_WIDTH = 1620;
    private final int PANEL_HEIGHT = 880;

    public GameScreen(JPanel container, CardLayout cardLayout) {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setLayout(null);

        // Load background
        background = loadImage("GameScreen.png");

        // Example: Back to Menu button
        JButton backButton = new JButton("Back to Menu");
        backButton.setBounds(50, 50, 200, 60); // top-left
        backButton.addActionListener(e -> cardLayout.show(container, "Menu"));
        add(backButton);
    }

    private Image loadImage(String fileName) {
        URL url = getClass().getResource("/" + fileName);
        if (url == null) {
            System.err.println("Could not find file: " + fileName);
            return null;
        }
        return new ImageIcon(url).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            g.drawImage(background, 0, 0, PANEL_WIDTH, PANEL_HEIGHT, this);
        }
    }
}