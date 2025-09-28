import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GameScreen extends JPanel {
    private Image background;
    private List<Capybara> capybaras = new ArrayList<>();
    private Timer timer;
    private Player p;

    public GameScreen(JPanel container, CardLayout cardLayout, Player player) {
        setLayout(null);
        setPreferredSize(new Dimension(1620, 880));
        this.p = player;

        // Load background
        URL bgUrl = getClass().getResource("/GameScreen.png");
        if (bgUrl != null) {
            background = new ImageIcon(bgUrl).getImage();
        } else {
            System.err.println("GameScreen.png not found!");
        }

        // Add capybaras
        for (int i = 0; i < p.getTotalCapyNum(); i++) {
            capybaras.add(new Capybara(
                    810 + (int) (Math.random() * 200 - 100),
                    440 + (int) (Math.random() * 200 - 100)
            ));
        }

        // Timer to move capybaras
        timer = new Timer(30, e -> {
            Rectangle bounds = new Rectangle(810 - 200, 440 - 200, 400, 400);
            for (Capybara c : capybaras) c.move(bounds);
            repaint();
        });
        timer.start();

        // Gamble button
        try {
            URL gambleUrl = getClass().getResource("/GambleButton.png");
            JButton gambleButton;
            if (gambleUrl != null) {
                ImageIcon originalIcon = new ImageIcon(gambleUrl);
                Image scaledImage = originalIcon.getImage().getScaledInstance(150, 60, Image.SCALE_SMOOTH);
                gambleButton = new JButton(new ImageIcon(scaledImage));
            } else {
                gambleButton = new JButton("Gamble");
            }
            gambleButton.setBounds(800, 20, 150, 60);
            gambleButton.setBorderPainted(false);
            gambleButton.setContentAreaFilled(false);
            gambleButton.setFocusPainted(false);
            gambleButton.addActionListener(e -> cardLayout.show(container, "GambleGUI"));
            add(gambleButton);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Back button
        try {
            URL backUrl = getClass().getResource("/Back.png");
            JButton backButton;
            if (backUrl != null) {
                ImageIcon originalIcon = new ImageIcon(backUrl);
                Image scaledImage = originalIcon.getImage().getScaledInstance(150, 60, Image.SCALE_SMOOTH);
                backButton = new JButton(new ImageIcon(scaledImage));
            } else {
                backButton = new JButton("Back");
            }
            backButton.setBounds(20, 20, 150, 60);
            backButton.setBorderPainted(false);
            backButton.setContentAreaFilled(false);
            backButton.setFocusPainted(false);
            backButton.addActionListener(e -> cardLayout.show(container, "Menu"));
            add(backButton);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refreshCapybaras() {
        capybaras.clear();
        for (int i = 0; i < p.getTotalCapyNum(); i++) {
            capybaras.add(new Capybara(
                    810 + (int) (Math.random() * 200 - 100),
                    440 + (int) (Math.random() * 200 - 100)
            ));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        for (Capybara c : capybaras) c.draw(g);
    }
}
