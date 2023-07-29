package Project.Util;

import javax.swing.*;
import java.awt.*;

public class CircleLabel extends JLabel {
    private static final int CIRCLE_DIAMETER = 50;

    public CircleLabel(String name) {
        super(capitalizeFirstLetter(name));
        setPreferredSize(new Dimension(CIRCLE_DIAMETER, CIRCLE_DIAMETER));
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        setFont(getFont().deriveFont(Font.BOLD, 20));
    }

    private static String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("name not Found");
        }
        return str.substring(0, 1).toUpperCase();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the circle background
        g.setColor(Color.GRAY);
        g.fillOval(0, 0, CIRCLE_DIAMETER, CIRCLE_DIAMETER);

        // Draw the text
        g.setColor(Color.BLACK);
        FontMetrics metrics = g.getFontMetrics(getFont());
        int x = (getWidth() - metrics.stringWidth(getText())) / 2;
        int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
        g.drawString(getText(), x, y);
    }
}
