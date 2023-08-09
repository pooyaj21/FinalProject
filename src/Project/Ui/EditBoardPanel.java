package Project.Ui;

import Project.Logic.*;
import Project.Logic.DataBase.SQL.BoardDataBaseSql;
import Project.Logic.DataBase.SQL.CrossTabel.BoardIssuesDataBaseSql;
import Project.Logic.DataBase.SQL.CrossTabel.UserProjectDataBaseSql;
import Project.Logic.DataBase.SQL.IssueDataBaseSql;
import Project.Util.GeneralController;
import Project.Util.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EditBoardPanel extends JPanel {
    Board board;
    Project project;
    ProjectPanel projectPanel;
    JLabel nameLabel = new JLabel("Name:");
    JTextField nameField = new JTextField();
    RoundedButton submit = new RoundedButton("Submit", 15, Color.blue, Color.white, 12);
    RoundedButton delete = new RoundedButton("Delete", 15, Color.red, Color.white, 12);
    JLabel nameErrorLabel = new JLabel("Enter a name");

    public EditBoardPanel(Board board,Project project , ProjectPanel projectPanel) {
        this.projectPanel=projectPanel;
        this.board = board;
        setSize(770, 680);
        setLayout(null);

        nameLabel.setBounds(175, 80, 75, 25);
        nameField.setBounds(225, 80, 200, 25);
        nameErrorLabel.setBounds(225, 105, 200, 25);
        nameErrorLabel.setVisible(false);



        delete.setBounds(430, 300, 100, 25);
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BoardDataBaseSql.getInstance().deleteBoard(board.getId());
                projectPanel.kanbanButton.doClick();
                setVisible(false);
            }
        });
        delete.setVisible(projectPanel.getUser().getRole().hasAccess(FeatureAccess.DELETE_BOARD));
        submit.setBounds(550, 300, 100, 25);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameErrorLabel.setVisible(false);
                if (GeneralController.getInstance().isEmpty(nameField.getText())) {
                    nameErrorLabel.setForeground(Color.red);
                    nameErrorLabel.setVisible(true);
                }else {
                    BoardDataBaseSql.getInstance().editBoard(board.getId(),nameField.getText());
                    update();
                    projectPanel.kanbanButton.doClick();
                }
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

    public void setProject(Project project) {
        this.project = project;
    }
}
