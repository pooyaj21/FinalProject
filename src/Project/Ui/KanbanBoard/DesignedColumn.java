package Project.Ui.KanbanBoard;

import Project.Logic.Status;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DesignedColumn extends JPanel {
    CircularLabel numberTask = new CircularLabel("", 60, Color.white, Color.BLACK, 13);

    public DesignedColumn(String name, Color colorDot) {
        setLayout(null);
        JLabel header = new JLabel(name);
        JLabel theDot = new JLabel("•");
        header.setFont(new Font("assets/Montserrat-ExtraLight.ttf", Font.PLAIN, 15));
        header.setHorizontalAlignment(SwingConstants.LEFT);
        theDot.setFont(new Font("assets/Montserrat-ExtraLight.ttf", Font.PLAIN, 18));
        theDot.setForeground(colorDot);
        theDot.setHorizontalAlignment(SwingConstants.LEFT);
        setBackground(null);
        setLayout(null);
        theDot.setBounds(25, 0, 10, 50);
        header.setBounds(40, 7, 180, 40);


        numberTask.setBounds(name.length()*5+75, 12, 35, 30);
        add(numberTask);
        add(header);
        add(theDot);

        setVisible(true);
    }
}
