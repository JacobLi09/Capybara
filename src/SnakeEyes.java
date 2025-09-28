import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class SnakeEyes extends JPanel {
    private Player player;
    private Image background;
    private JLabel capyCountLabel;
    private JTextField wagerField;
    private int betAmount;

    public SnakeEyes(Player player, JPanel container, CardLayout cardLayout) {
        this.player = player;
        setLayout(null);
        setPreferredSize(new Dimension(1620, 880));

        URL url = getClass().getResource("/SnakeEyes.png");
        if (url != null) background = new ImageIcon(url).getImage();

        capyCountLabel = new JLabel("You have: " + player.getTotalCapyNum() + " capybaras");
        capyCountLabel.setBounds(300, 50, 400, 30);
        add(capyCountLabel);

        wagerField = new JTextField();
        wagerField.setBounds(300, 150, 200, 40);
        add(wagerField);

        JButton rollBtn = new JButton("Roll Dice");
        rollBtn.setBounds(300, 220, 200, 50);
        rollBtn.addActionListener(e -> placeWager());
        add(rollBtn);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(20, 20, 150, 50);
        backBtn.addActionListener(e -> cardLayout.show(container, "GambleGUI"));
        add(backBtn);
    }

    private void rollDice() {
        int dice1 = (int)(Math.random()*6)+1;
        int dice2 = (int)(Math.random()*6)+1;

        if (dice1 == 1 && dice2 == 1) player.addCapy(betAmount*5);
        else if (dice1 == dice2) player.addCapy(betAmount*2);
        else player.removeCapy(betAmount);

        JOptionPane.showMessageDialog(this, "Rolled "+dice1+" & "+dice2+". You now have "+player.getTotalCapyNum()+" capybaras.");
        capyCountLabel.setText("You have: " + player.getTotalCapyNum() + " capybaras");
        wagerField.setText("");
    }

    private void placeWager() {
        try { betAmount = Integer.parseInt(wagerField.getText()); }
        catch(Exception e) { JOptionPane.showMessageDialog(this,"Enter a valid number"); return; }

        if (betAmount <= 0 || betAmount > player.getTotalCapyNum()) {
            JOptionPane.showMessageDialog(this, "Invalid wager"); return;
        }

        rollDice();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }
}
