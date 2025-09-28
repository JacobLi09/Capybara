import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Menu extends JPanel {

    private Image background;
    private JButton playButton;

    private final int PANEL_WIDTH = 1620;
    private final int PANEL_HEIGHT = 880;

    public Menu(JPanel container, CardLayout cardLayout) {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setLayout(null);

        // Load background image
        background = loadImage("Menu.png");

        // Position of the translucent Play Button (adjust to match background art)
        int playX = 470;
        int playY = 580;
        int playWidth = 680;
        int playHeight = 160;

        playButton = new JButton();
        playButton.setBounds(playX, playY, playWidth, playHeight);
        playButton.setBorderPainted(false);
        playButton.setContentAreaFilled(false);
        playButton.setFocusPainted(false);

        playButton.addActionListener(e -> {
            // Switch to Game screen instead of showing dialog
            cardLayout.show(container, "Game");
        });

        add(playButton);
    }

    private Image loadImage(String fileName) {
        URL url = getClass().getResource("/" + fileName); // safer with /
        if (url == null) {
            System.err.println("Could not find file: " + fileName);
            return null;
        }
        return new ImageIcon(url).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw background filling panel
        if (background != null) {
            g.drawImage(background, 0, 0, PANEL_WIDTH, PANEL_HEIGHT, this);
        }
    }
}
