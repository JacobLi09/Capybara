import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.*;

public class Lottery extends JPanel {
    private JTextField capybaraAmountField;
    private JTextField[] numberFields;
    private JTextArea resultArea;
    private JLabel currentAmountLabel;
    private Random random;
    private DecimalFormat df;

    private Player p;
    private JPanel container;
    private CardLayout cardLayout;

    public Lottery(Player player, JPanel container, CardLayout cardLayout) {
        this.p = player;
        this.container = container;
        this.cardLayout = cardLayout;
        this.random = new Random();
        this.df = new DecimalFormat("#.##");

        setLayout(null);
        setPreferredSize(new Dimension(1620, 880));

        // Background
        Image bg = new ImageIcon(getClass().getResource("/Lottery.png")).getImage();
        JPanel bgPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            }
        };
        bgPanel.setLayout(null);
        bgPanel.setBounds(0, 0, 1620, 880);
        add(bgPanel);

        // Current balance
        currentAmountLabel = new JLabel("Current Capybaras: " + p.getTotalCapyNum());
        currentAmountLabel.setFont(new Font("Arial", Font.BOLD, 18));
        currentAmountLabel.setBounds(300, 30, 400, 30);
        bgPanel.add(currentAmountLabel);

        // Numbers label
        JLabel numbersLabel = new JLabel("Enter 1-5 numbers (1-99):");
        numbersLabel.setBounds(300, 80, 250, 25);
        bgPanel.add(numbersLabel);

        // Number fields
        numberFields = new JTextField[5];
        for (int i = 0; i < 5; i++) {
            numberFields[i] = new JTextField();
            numberFields[i].setBounds(300 + (i * 60), 110, 50, 30);
            bgPanel.add(numberFields[i]);
        }

        // Amount field
        JLabel amountLabel = new JLabel("Capybaras to gamble:");
        amountLabel.setBounds(300, 150, 200, 25);
        bgPanel.add(amountLabel);

        capybaraAmountField = new JTextField();
        capybaraAmountField.setBounds(500, 150, 80, 30);
        bgPanel.add(capybaraAmountField);

        // Results
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(resultArea);
        scroll.setBounds(50, 200, 500, 200);
        bgPanel.add(scroll);

        // Buttons
        JButton playBtn = new JButton("PLAY");
        playBtn.setBounds(600, 200, 150, 40);
        playBtn.addActionListener(e -> playLottery());
        bgPanel.add(playBtn);

        JButton clearBtn = new JButton("CLEAR");
        clearBtn.setBounds(600, 250, 150, 40);
        clearBtn.addActionListener(e -> clearFields());
        bgPanel.add(clearBtn);

        JButton backBtn = new JButton("BACK");
        backBtn.setBounds(600, 300, 150, 40);
        backBtn.addActionListener(e -> cardLayout.show(container, "GambleGUI"));
        bgPanel.add(backBtn);
    }

    private void clearFields() {
        capybaraAmountField.setText("");
        for (JTextField f : numberFields) f.setText("");
        resultArea.setText("");
    }

    private void playLottery() {
        int gambleAmount;
        try {
            gambleAmount = Integer.parseInt(capybaraAmountField.getText().trim());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Enter a valid wager!");
            return;
        }

        if (gambleAmount <= 0 || gambleAmount > p.getTotalCapyNum()) {
            JOptionPane.showMessageDialog(this, "Invalid wager!");
            return;
        }

        ArrayList<Integer> userNumbers = getUserNumbers();
        if (userNumbers.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter at least one number!");
            return;
        }

        ArrayList<Integer> winningNumbers = generateWinningNumbers(userNumbers.size());
        ArrayList<Integer> matches = findMatches(userNumbers, winningNumbers);

        StringBuilder result = new StringBuilder();
        result.append("Your numbers: ").append(userNumbers).append("\n");
        result.append("Winning numbers: ").append(winningNumbers).append("\n");

        if (matches.isEmpty()) {
            p.removeCapy(gambleAmount);
            result.append("No matches! You lost ").append(gambleAmount).append(" capybaras.\n");
        } else {
            int winnings = calculateWinnings(gambleAmount, matches.size(), result);
            p.setTotalCapyNum(p.getTotalCapyNum() - gambleAmount + winnings);
        }

        result.append("New balance: ").append(p.getTotalCapyNum()).append(" capybaras");
        resultArea.setText(result.toString());
        currentAmountLabel.setText("Current Capybaras: " + p.getTotalCapyNum());
        clearFields();
    }

    private ArrayList<Integer> getUserNumbers() {
        ArrayList<Integer> nums = new ArrayList<>();
        for (JTextField f : numberFields) {
            if (!f.getText().trim().isEmpty()) nums.add(Integer.parseInt(f.getText().trim()));
        }
        return nums;
    }

    private ArrayList<Integer> generateWinningNumbers(int count) {
        ArrayList<Integer> nums = new ArrayList<>();
        for (int i = 0; i < count; i++) nums.add(random.nextInt(99) + 1);
        return nums;
    }

    private ArrayList<Integer> findMatches(ArrayList<Integer> userNumbers, ArrayList<Integer> winningNumbers) {
        ArrayList<Integer> matches = new ArrayList<>();
        for (int n : userNumbers) if (winningNumbers.contains(n)) matches.add(n);
        return matches;
    }

    private int calculateWinnings(int gambleAmount, int matchCount, StringBuilder result) {
        double multiplier = 1 + (0.1 + random.nextDouble() * 0.4);
        double winMultiplier = multiplier * matchCount;
        int winnings = (int) (gambleAmount * winMultiplier);
        result.append("Matches: ").append(matchCount).append("\n");
        result.append("Multiplier: ").append(df.format(winMultiplier)).append("x\n");
        result.append("You won: ").append(winnings).append(" capybaras!\n");
        return winnings;
    }
}
