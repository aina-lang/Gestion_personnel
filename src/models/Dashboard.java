package models;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JPanel {
    private static final int RECTANGLE_WIDTH = 350;
    private static final int RECTANGLE_HEIGHT = 170;
    private static final int MARGIN = 20;
    private static final int SHADOW_SIZE = 8;
    private static final Color SHADOW_COLOR = new Color(0, 0, 0, 50);

    public Dashboard() {
        setPreferredSize(new Dimension(2 * RECTANGLE_WIDTH + 3 * MARGIN, 2 * RECTANGLE_HEIGHT + 3 * MARGIN));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int startX = (getWidth() - (2 * RECTANGLE_WIDTH + MARGIN)) / 2;
        int startY = (getHeight() - (2 * RECTANGLE_HEIGHT + MARGIN)) / 2;

        // Dessin de l'ombre portée
      /*  g.setColor(SHADOW_COLOR);
        g.fillRect(startX + SHADOW_SIZE, startY + SHADOW_SIZE, RECTANGLE_WIDTH, RECTANGLE_HEIGHT);*/

        // Dessin des rectangles
        g.setColor(new Color(0x008E9B));
        g.fillRect(startX, startY, RECTANGLE_WIDTH, RECTANGLE_HEIGHT);

        g.setColor(new Color(0xF9F871));
        g.fillRect(startX + RECTANGLE_WIDTH + MARGIN, startY, RECTANGLE_WIDTH, RECTANGLE_HEIGHT);

        g.setColor(new Color(0x00C9A7));
        g.fillRect(startX, startY + RECTANGLE_HEIGHT + MARGIN, RECTANGLE_WIDTH, RECTANGLE_HEIGHT);

        g.setColor(new Color(0xFF8066));
        g.fillRect(startX + RECTANGLE_WIDTH + MARGIN, startY + RECTANGLE_HEIGHT + MARGIN, RECTANGLE_WIDTH, RECTANGLE_HEIGHT);
    }


}
