import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.Random;
import java.net.URL;

public class Slots extends JPanel {

    private Player player;
    private JPanel container;
    private CardLayout cardLayout;

    private JLabel currentAmountLabel;
    private JTextField betAmountField;
    private JLabel[] reelLabels;
    private JTextArea resultArea;
    private JButton spinButton;

    private Random random;
    private DecimalFormat df;

    private final String[] SYMBOLS = {"🐹", "🥕", "💧", "🎩", "🌟", "💰"};
    private final String JACKPOT_SYMBOL = "💰";
    private final double TWO_MATCH_MULTIPLIER = 2.0;
    private final double THREE_MATCH_MULTIPLIER = 10.0;
    private final double JACKPOT_MULTIPLIER = 50.0;

    public Slots(Player player, JPanel container, CardLayout cardLayout) {
        this.player = player;
        this.container = container;
        this.cardLayout = cardLayout;
        this.random = new Random();
        this.df = new DecimalFormat("#.##");

        setLayout(null);
        setPreferredSize(new Dimension(1620, 880));

        // Background
        URL bgUrl = getClass().getResource("/Slots.png");
        Image bg = bgUrl != null ? new ImageIcon(bgUrl).getImage() : null;

        JPanel bgPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (bg != null) g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            }
        };
        bgPanel.setLayout(null);
        bgPanel.setBounds(0, 0, 1620, 880);
        add(bgPanel);

        // Current balance
        currentAmountLabel = new JLabel("Current Capybaras: " + player.getTotalCapyNum());
        currentAmountLabel.setFont(new Font("Arial", Font.BOLD, 18));
        currentAmountLabel.setBounds(700, 50, 400, 30); // center-ish
        bgPanel.add(currentAmountLabel);

        // Bet amount label
        JLabel betLabel = new JLabel("Bet amount:");
        betLabel.setFont(new Font("Arial", Font.BOLD, 16));
        betLabel.setBounds(650, 100, 120, 25);
        bgPanel.add(betLabel);

        // Bet amount field
        betAmountField = new JTextField();
        betAmountField.setBounds(770, 100, 100, 25);
        bgPanel.add(betAmountField);

        // Reels
        reelLabels = new JLabel[3];
        for (int i = 0; i < 3; i++) {
            reelLabels[i] = new JLabel("🐹", SwingConstants.CENTER);
            reelLabels[i].setFont(new Font("Arial", Font.BOLD, 48));
            reelLabels[i].setOpaque(true);
            reelLabels[i].setBackground(new Color(0,0,0,150));
            reelLabels[i].setForeground(Color.WHITE);
            reelLabels[i].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
            reelLabels[i].setBounds(650 + i*120, 150, 100, 100);
            bgPanel.add(reelLabels[i]);
        }

        // Results area
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 14));
        resultArea.setBackground(new Color(255,255,255,220));
        JScrollPane scroll = new JScrollPane(resultArea);
        scroll.setBounds(600, 270, 400, 200);
        bgPanel.add(scroll);

        // Buttons
        spinButton = new JButton("SPIN");
        spinButton.setBounds(650, 500, 100, 40);
        spinButton.addActionListener(e -> spinReels());
        bgPanel.add(spinButton);

        JButton clearBtn = new JButton("CLEAR");
        clearBtn.setBounds(770, 500, 100, 40);
        clearBtn.addActionListener(e -> clearFields());
        bgPanel.add(clearBtn);

        // Back button
        JButton backBtn = new JButton("BACK");
        backBtn.setBounds(20, 20, 150, 50);
        backBtn.addActionListener(e -> cardLayout.show(container, "GambleGUI"));
        bgPanel.add(backBtn);
    }

    private void clearFields() {
        betAmountField.setText("");
        resultArea.setText("");
        for (JLabel reel : reelLabels) {
            reel.setText("🐹");
        }
    }

    private void spinReels() {
        int betAmount;
        try {
            betAmount = Integer.parseInt(betAmountField.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter a valid bet amount!");
            return;
        }

        if (betAmount <= 0 || betAmount > player.getTotalCapyNum()) {
            JOptionPane.showMessageDialog(this, "Invalid bet amount!");
            return;
        }

        // Spin
        String[] results = new String[3];
        for (int i = 0; i < 3; i++) {
            results[i] = SYMBOLS[random.nextInt(SYMBOLS.length)];
            reelLabels[i].setText(results[i]);
        }

        // Calculate winnings
        int winnings = 0;
        String message = "";
        if (results[0].equals(JACKPOT_SYMBOL) && results[1].equals(JACKPOT_SYMBOL) && results[2].equals(JACKPOT_SYMBOL)) {
            winnings = (int)(betAmount * JACKPOT_MULTIPLIER);
            message = "JACKPOT! 🎉";
        } else if (results[0].equals(results[1]) && results[1].equals(results[2])) {
            winnings = (int)(betAmount * THREE_MATCH_MULTIPLIER);
            message = "Big Win! 3 matching symbols!";
        } else if (results[0].equals(results[1]) || results[1].equals(results[2]) || results[0].equals(results[2])) {
            winnings = (int)(betAmount * TWO_MATCH_MULTIPLIER);
            message = "Small Win! 2 matching symbols!";
        } else {
            winnings = 0;
            message = "No win. Try again!";
        }

        player.setTotalCapyNum(player.getTotalCapyNum() - betAmount + winnings);

        // Display results
        resultArea.setText("Spin Results: " + results[0] + " " + results[1] + " " + results[2] + "\n"
                + message + "\n"
                + "Wager: " + betAmount + "  |  Winnings: " + winnings + "\n"
                + "New Balance: " + player.getTotalCapyNum());

        currentAmountLabel.setText("Current Capybaras: " + player.getTotalCapyNum());
        clearFields();
    }
}
