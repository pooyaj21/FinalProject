package Project.Ui;

import Project.Logic.Board;
import Project.Logic.DataBase.SQL.BoardDataBaseSql;
import Project.Logic.Project;
import Project.Util.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddBoredPanel extends JPanel {
    JLabel nameLabel = new JLabel("Name:");
    JTextField nameField = new JTextField();
    RoundedButton submit = new RoundedButton("Submit", 15, Color.blue, Color.white, 12);

    public AddBoredPanel(Project project,ProjectPanel projectPanel) {
        setSize(770, 680);
        setLayout(null);

        nameLabel.setBounds(125, 250, 75, 25);
        nameField.setBounds(170, 250, 200, 25);
        submit.setBounds(350, 300, 100, 25);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BoardDataBaseSql.getInstance().createBoard(project.getId(),nameField.getText());
                projectPanel.kanbanButton.doClick();
            }
        });

        add(nameLabel);
        add(nameField);
        add(submit);

        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        String enterKey = "enterKey";
        inputMap.put(KeyStroke.getKeyStroke("ENTER"), enterKey);
        actionMap.put(enterKey, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submit.doClick();
            }
        });
    }
}
