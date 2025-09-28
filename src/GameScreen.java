import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;

public class GameScreen extends JPanel {
    private Image background;
    private List<Capybara> capybaras = new ArrayList<>();
    private Timer timer;
    private Player p;

    public GameScreen(JPanel container, CardLayout cardLayout, Player player) {
        setLayout(null);
        setPreferredSize(new Dimension(1620, 880));
        this.p = player;

        URL url = getClass().getResource("/GameScreen.png");
        if (url != null) background = new ImageIcon(url).getImage();

        // Add capybaras
        for (int i=0; i <= p.getTotalCapyNum(); i++){
        System.out.println(p.getTotalCapyNum());
            capybaras.add(new Capybara(810 + (int)(Math.random()*200-100), 440 + (int)(Math.random()*200-100)));
        }
        // Timer to move capybaras
        timer = new Timer(30, e -> {
            Rectangle bounds = new Rectangle(810-200, 440-200, 400, 400);
            for (Capybara c : capybaras) c.move(bounds);
            repaint();
        });
        timer.start();

        // Gamble button
        JButton gambleButton = new JButton("Gamble");
        gambleButton.setBounds(1400, 20, 150, 50);
        gambleButton.addActionListener(e -> cardLayout.show(container, "GambleGUI"));
        add(gambleButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        for (Capybara c : capybaras) c.draw(g);
    }
}
