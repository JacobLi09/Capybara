import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Capybara Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1620, 880);

            JPanel container = new JPanel(new CardLayout());
            frame.setContentPane(container);
            CardLayout cl = (CardLayout) container.getLayout();

            Player player = new Player("Player1", 5);

            Menu menu = new Menu(container, cl);
            GameScreen gameScreen = new GameScreen(container, cl, player);
            GambleGUI gambleGUI = new GambleGUI(container, cl);
            SnakeEyes snakeEyes = new SnakeEyes(player, container, cl);

            container.add(menu, "Menu");
            container.add(gameScreen, "GameScreen");
            container.add(gambleGUI.getPanel(), "GambleGUI");
            container.add(snakeEyes, "SnakeEyes");

            cl.show(container, "Menu");
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
