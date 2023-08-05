package Project.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedPanel extends JPanel {

    private final Color foregroundColor;
    private final int cornerRadius;
    private Color backgroundColor;
    private String text;

    public RoundedPanel(String text, int cornerRadius, Color backgroundColor, Color foregroundColor, int fontSize) {
        this.text = text;
        this.backgroundColor = backgroundColor;
        this.foregroundColor = foregroundColor;
        this.cornerRadius = cornerRadius;
        setFont(new Font("Arial", Font.PLAIN, fontSize));
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int width = getWidth();
        int height = getHeight();
        Shape shape = new RoundRectangle2D.Double(0, 0, width - 1, height - 1, cornerRadius, cornerRadius);
        g2.setColor(backgroundColor);
        g2.fill(shape);
        g2.setColor(foregroundColor);
        FontMetrics fontMetrics = g2.getFontMetrics();
        int textX = (width - fontMetrics.stringWidth(text)) / 2;
        int textY = (height - fontMetrics.getHeight()) / 2 + fontMetrics.getAscent();
        g2.drawString(text, textX, textY);
        g2.dispose();
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setTheText(String text) {
        this.text=text;
    }
}
