import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class SnakeEyes extends JPanel {
    private Player player;
    private Image background;
    private JLabel capyCountLabel;
    private JTextField wagerField;
    private JLabel diceLabel1, diceLabel2, resultLabel;
    private int betAmount;

    public SnakeEyes(Player player, JPanel container, CardLayout cardLayout) {
        this.player = player;
        setLayout(null);
        setPreferredSize(new Dimension(1620, 880));

        // Load background
        URL url = getClass().getResource("/SnakeEyes.png");
        if (url != null) background = new ImageIcon(url).getImage();

        // Capy count label (top center)
        capyCountLabel = new JLabel("You have: " + player.getTotalCapyNum() + " capybaras");
        capyCountLabel.setBounds(650, 575, 400, 30);
        capyCountLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(capyCountLabel);

        // Wager field
        wagerField = new JTextField();
        wagerField.setBounds(650, 650, 200, 40);
        add(wagerField);

        // Roll button
        try {
            URL rollUrl = getClass().getResource("/rollerButton.png");
            JButton rollBtn;
            if (rollUrl != null) {
                ImageIcon originalIcon = new ImageIcon(rollUrl);
                Image scaledImage = originalIcon.getImage().getScaledInstance(200, 80, Image.SCALE_SMOOTH);
                rollBtn = new JButton(new ImageIcon(scaledImage));
            } else {
                rollBtn = new JButton("Roll Dice");
            }

            rollBtn.setBounds(650, 700, 200, 80);
            rollBtn.setBorderPainted(false);
            rollBtn.setContentAreaFilled(false);
            rollBtn.setFocusPainted(false);
            rollBtn.addActionListener(e -> placeWager());
            add(rollBtn);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Dice labels
        diceLabel1 = new JLabel();
        diceLabel1.setBounds(340, 310, 150, 150);
        diceLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        add(diceLabel1);

        diceLabel2 = new JLabel();
        diceLabel2.setBounds(1070, 320, 150, 150);
        diceLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        add(diceLabel2);

        // Result label (centered on screen)
        resultLabel = new JLabel("", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 30)); // bigger font for visibility
        resultLabel.setForeground(Color.BLACK);
        add(resultLabel); // We'll set bounds dynamically in paintComponent

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
            backButton.addActionListener(e -> cardLayout.show(container, "GambleGUI"));
            add(backButton);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void rollDice() {
        int dice1 = (int)(Math.random() * 6) + 1;
        int dice2 = (int)(Math.random() * 6) + 1;

        // Load dice images
        diceLabel1.setIcon(loadDiceImage(dice1, diceLabel1.getWidth(), diceLabel1.getHeight()));
        diceLabel2.setIcon(loadDiceImage(dice2, diceLabel2.getWidth(), diceLabel2.getHeight()));

        // Game logic
        if (dice1 == 1 && dice2 == 1) {
            player.addCapy(betAmount * 5);
            resultLabel.setText("Snake Eyes! You win " + (betAmount * 5) + " capybaras!");
        } else if (dice1 == dice2) {
            player.addCapy(betAmount * 2);
            resultLabel.setText("Doubles! You win " + (betAmount * 2) + " capybaras!");
        } else {
            player.removeCapy(betAmount);
            resultLabel.setText("You lose " + betAmount + " capybaras!");
        }

        capyCountLabel.setText("You have: " + player.getTotalCapyNum() + " capybaras");
        wagerField.setText("");

        // Center result label dynamically
        int labelWidth = 800;
        int labelHeight = 50;
        int x = (getWidth() - labelWidth) / 2;
        int y = (getHeight() - labelHeight) / 2;
        resultLabel.setBounds(x, y, labelWidth, labelHeight);
    }

    private ImageIcon loadDiceImage(int number, int width, int height) {
        URL url = getClass().getResource("/" + number + ".png");
        if (url != null) {
            ImageIcon original = new ImageIcon(url);
            Image scaled = original.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        }
        return null;
    }

    private void placeWager() {
        try {
            betAmount = Integer.parseInt(wagerField.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Enter a valid number");
            return;
        }

        if (betAmount <= 0 || betAmount > player.getTotalCapyNum()) {
            JOptionPane.showMessageDialog(this, "Invalid wager");
            return;
        }

        rollDice();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }
}
