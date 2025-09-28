import javax.swing.*;
import java.awt.*;

public class GambleGUI {
    private JPanel panel;
    private GameScreen gameScreen;  // reference to GameScreen

    public GambleGUI(JPanel container, CardLayout cardLayout, GameScreen gameScreen) {
        this.gameScreen = gameScreen;

        panel = new JPanel() {
            Image bg = new ImageIcon(getClass().getResource("/GambleGUI.png")).getImage();
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null);

        // Buttons
        JButton lotteryBtn = new JButton("Lottery");
        lotteryBtn.setBounds(100, 100, 200, 80);
        lotteryBtn.addActionListener(e -> cardLayout.show(container, "Lottery"));
        panel.add(lotteryBtn);

        JButton snakeEyesBtn = new JButton();
        snakeEyesBtn.setBounds(800, 50, 675, 350);
        snakeEyesBtn.setBorderPainted(false);
        snakeEyesBtn.setContentAreaFilled(false);
        snakeEyesBtn.setFocusPainted(false);
        snakeEyesBtn.addActionListener(e -> cardLayout.show(container, "SnakeEyes"));
        panel.add(snakeEyesBtn);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(20, 20, 150, 50);
        backBtn.addActionListener(e -> {
            gameScreen.refreshCapybaras(); // refresh capybaras when returning
            cardLayout.show(container, "GameScreen");
        });
        panel.add(backBtn);
    }

    public JPanel getPanel() { return panel; }
}
