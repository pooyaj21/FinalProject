package Project.Ui.Board;

import Project.Logic.Board;
import Project.Logic.DataBase.SQL.BoardDataBaseSql;
import Project.Ui.Project.ProjectPanel;
import Project.Util.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditBoardPanel extends JPanel {
    Board board;
    ProjectPanel projectPanel;
    JLabel nameLabel = new JLabel("Name:");
    JTextField nameField = new JTextField();
    RoundedButton submit = new RoundedButton("Submit", 15, Color.blue, Color.white, 12);
    RoundedButton delete = new RoundedButton("Delete", 15, Color.red, Color.white, 12);
    JLabel nameErrorLabel = new JLabel("Enter a name");

    public EditBoardPanel(Board board, ProjectPanel projectPanel) {
        this.projectPanel = projectPanel;
        this.board = board;
        setSize(770, 680);
        setLayout(null);
        nameLabel.setBounds(125, 200, 75, 25);
        nameField.setBounds(170, 200, 200, 25);
        nameErrorLabel.setBounds(170, 225, 200, 25);
        nameErrorLabel.setVisible(false);
        nameErrorLabel.setFont(new Font(null, Font.ITALIC, 10));

        submit.setBounds(170, 250, 100, 25);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameErrorLabel.setVisible(false);
                if (nameField.getText().isEmpty()) {
                    nameErrorLabel.setForeground(Color.red);
                    nameErrorLabel.setVisible(true);
                } else {
                    BoardDataBaseSql.getInstance().editBoard(board.getId(), nameField.getText());
                    update();
                    projectPanel.kanbanButton.doClick();
                }
            }
        });

        delete.setBounds(280, 250, 100, 25);
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BoardDataBaseSql.getInstance().deleteBoard(board.getId());
                projectPanel.kanbanButton.doClick();
                setVisible(false);
            }
        });

        add(nameLabel);
        add(nameField);
        add(nameErrorLabel);
        add(submit);
        add(delete);

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

    public void update() {
        nameField.setText(board.getName());
    }
}
