package Project.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;


public class RoundedTextField extends JTextField {
    private Color backgroundColor;
    private Color foregroundColor;
    private int cornerRadius;

    public RoundedTextField(int columns,int cornerRadius, Color backgroundColor, Color foregroundColor, int fontSize) {
        super(columns);
        this.backgroundColor = backgroundColor;
        this.foregroundColor = foregroundColor;
        this.cornerRadius = cornerRadius;
        this.setFont(new Font("Arial", Font.PLAIN, fontSize));
        setOpaque(false);
        setForeground(foregroundColor);
        setCaretColor(foregroundColor);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        Shape shape = new RoundRectangle2D.Double(0, 0, width - 1, height - 1, cornerRadius, cornerRadius);

        g2.setColor(backgroundColor);
        g2.fill(shape);
        g2.setColor(backgroundColor);
        g2.draw(shape);

        super.paintComponent(g2);
        g2.dispose();
    }
}