import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Capybara {
    private int x, y, dx, dy;
    private Image sprite;
    private int size = 80;

    public Capybara(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        loadSprite();
        pickNewDirection();
    }

    private void loadSprite() {
        URL url = getClass().getResource("/Capy.png");
        if (url != null) sprite = new ImageIcon(url).getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
    }

    private void pickNewDirection() {
        dx = ((int)(Math.random()*3)-1) * 2;
        dy = ((int)(Math.random()*3)-1) * 2;
    }

    public void move(Rectangle bounds) {
        x += dx; y += dy;
        if (!bounds.contains(x, y, size, size)) {
            x -= dx; y -= dy;
            pickNewDirection();
        }
        if (Math.random() < 0.01) pickNewDirection();
    }

    public void draw(Graphics g) {
        if (sprite != null) g.drawImage(sprite, x, y, null);
        else { g.setColor(Color.ORANGE); g.fillOval(x, y, size, size); }
    }
}
