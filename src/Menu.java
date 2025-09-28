import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Menu extends JPanel {
    private Image background;

    public Menu(JPanel container, CardLayout cardLayout) {
        setLayout(null);
        setPreferredSize(new Dimension(1620, 880));

        // Load background
        URL url = getClass().getResource("/Menu.png");
        if (url != null) background = new ImageIcon(url).getImage();

        // Play button
        JButton playButton = new JButton();
        playButton.setBounds(449, 550, 680, 160);
        playButton.setBorderPainted(false);
        playButton.setContentAreaFilled(false);
        playButton.setFocusPainted(false);
        playButton.addActionListener(e -> cardLayout.show(container, "GameScreen"));
        add(playButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }
}
