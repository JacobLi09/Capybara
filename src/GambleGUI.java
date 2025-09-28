import javax.swing.*;
import java.awt.*;
import java.net.URL;

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
        JButton lotteryBtn = new JButton();
        lotteryBtn.setBounds(80, 60, 675, 350);
        lotteryBtn.setBorderPainted(false);
        lotteryBtn.setContentAreaFilled(false);
        lotteryBtn.setFocusPainted(false);
        lotteryBtn.addActionListener(e -> cardLayout.show(container, "Lottery"));
        panel.add(lotteryBtn);

        JButton snakeEyesBtn = new JButton();
        snakeEyesBtn.setBounds(800, 50, 675, 350);
        snakeEyesBtn.setBorderPainted(false);
        snakeEyesBtn.setContentAreaFilled(false);
        snakeEyesBtn.setFocusPainted(false);
        snakeEyesBtn.addActionListener(e -> cardLayout.show(container, "SnakeEyes"));
        panel.add(snakeEyesBtn);

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
            backButton.addActionListener(e -> cardLayout.show(container, "GameScreen"));
            panel.add(backButton);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JPanel getPanel() { return panel; }
}
