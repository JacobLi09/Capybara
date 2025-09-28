

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GameScreen extends JPanel {

    private Image background;
    private List<Capybara> capybaras = new ArrayList<>();
    private Timer timer;

    private final int PANEL_WIDTH = 1620;
    private final int PANEL_HEIGHT = 880;

    // ✅ New constructor that matches your Menu
    public GameScreen(JPanel container, CardLayout cardLayout) {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        // Load background
        background = loadImage("GameScreen.png");

        // Add some capybaras at random positions near the center
        for (int i = 0; i < 5; i++) {
            int startX = PANEL_WIDTH / 2 + (int)(Math.random() * 200 - 100);
            int startY = PANEL_HEIGHT / 2 + (int)(Math.random() * 200 - 100);
            capybaras.add(new Capybara(startX, startY));
        }

        // Timer to update movement and repaint
        timer = new Timer(30, e -> {
            Rectangle centerBounds = new Rectangle(
                    PANEL_WIDTH / 3, PANEL_HEIGHT / 3,
                    PANEL_WIDTH / 3, PANEL_HEIGHT / 3
            );
            for (Capybara c : capybaras) {
                c.move(centerBounds);
            }
            repaint();
        });
        timer.start();
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

        // Draw capybaras
        for (Capybara c : capybaras) {
            c.draw(g);
        }
    }
}
