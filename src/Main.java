import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Capybara Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1920, 1080);
            frame.setResizable(false);

            CardLayout cardLayout = new CardLayout();
            JPanel container = new JPanel(cardLayout);

            Menu menu = new Menu(container, cardLayout);
            GameScreen gameScreen = new GameScreen(container, cardLayout);

            container.add(menu, "Menu");
            container.add(gameScreen, "Game");

            frame.add(container);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}