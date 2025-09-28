

import java.awt.*;
import java.net.URL;
import javax.swing.*;

public class Capybara {
    private int x, y;
    private int dx, dy;
    private Image sprite;
    private int size = 80; // capybara size

    public Capybara(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        loadSprite();
        pickNewDirection();
    }

    private void loadSprite() {
        URL url = getClass().getResource("/Capy.png"); // your capybara image
        if (url != null) {
            sprite = new ImageIcon(url).getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
        }
    }

    public void move(Rectangle bounds) {
        x += dx;
        y += dy;

        // stay inside the center bounds
        if (!bounds.contains(x, y, size, size)) {
            x -= dx;
            y -= dy;
            pickNewDirection();
        }

        // sometimes change direction
        if (Math.random() < 0.01) {
            pickNewDirection();
        }
    }

    private void pickNewDirection() {
        dx = (int)(Math.random() * 3) - 1; // -1, 0, 1
        dy = (int)(Math.random() * 3) - 1;
        dx *= 2; // speed
        dy *= 2;
    }

    public void draw(Graphics g) {
        if (sprite != null) {
            g.drawImage(sprite, x, y, null);
        } else {
            g.setColor(Color.ORANGE);
            g.fillOval(x, y, size, size); // placeholder if image not found
        }
    }
}
