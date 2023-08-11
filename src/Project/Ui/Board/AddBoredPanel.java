package Project.Ui.Board;

import Project.Logic.DataBase.SQL.BoardDataBaseSql;
import Project.Logic.DataBase.SQL.CrossTabel.BoardIssuesDataBaseSql;
import Project.Logic.DataBase.SQL.CrossTabel.UserProjectDataBaseSql;
import Project.Logic.DataBase.SQL.IssueDataBaseSql;
import Project.Logic.Issue;
import Project.Logic.Project;
import Project.Logic.User;
import Project.Ui.Project.ProjectPanel;
import Project.Util.GeneralController;
import Project.Util.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AddBoredPanel extends JPanel {
    JLabel nameLabel = new JLabel("Name:");
    JTextField nameField = new JTextField();
    JComboBox<String> userComboBox = new JComboBox<>();
    ArrayList<User> users = new ArrayList<>();
    JLabel userLabel = new JLabel("User:");
    RoundedButton submit = new RoundedButton("Submit", 15, Color.blue, Color.white, 12);
    JLabel userErrorLabel = new JLabel("Chose a User");
    JLabel nameErrorLabel = new JLabel("Enter a name");

    public AddBoredPanel(Project project, ProjectPanel projectPanel) {
        setSize(770, 680);
        setLayout(null);

        nameLabel.setBounds(125, 200, 75, 25);
        nameField.setBounds(170, 200, 200, 25);
        nameErrorLabel.setBounds(170, 225, 200, 25);
        nameErrorLabel.setFont(new Font(null,Font.BOLD,12));
        nameErrorLabel.setVisible(false);

        userLabel.setBounds(125, 250, 75, 25);
        userComboBox.setBounds(170, 250, 200, 25);
        userComboBox.addItem(null);
        for (User user: UserProjectDataBaseSql.getInstance().getAllUsersOfProject(project.getId())) {
            userComboBox.addItem(user.getFullName());
            users.add(user);
        }
        userErrorLabel.setBounds(170, 275, 200, 25);
        userErrorLabel.setVisible(false);

        submit.setBounds(350, 300, 100, 25);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameErrorLabel.setVisible(false);
                userErrorLabel.setVisible(false);
                if (GeneralController.getInstance().isEmpty(nameField.getText())) {
                    nameErrorLabel.setForeground(Color.red);
                    nameErrorLabel.setVisible(true);
                } else if(userComboBox.getSelectedItem()==null) {
                    userErrorLabel.setForeground(Color.red);
                    userErrorLabel.setVisible(true);
                }else {
                    BoardDataBaseSql.getInstance().createBoard(project.getId(), nameField.getText());
                    for (Issue issue : IssueDataBaseSql.getInstance().getAllIssuesOfProject(project.getId())) {
                        if (issue.getUser() != null && issue.getUser().getId() == users.get(userComboBox.getSelectedIndex() - 1).getId()) {
                            BoardIssuesDataBaseSql.getInstance().assignIssueToBoard(
                                    BoardDataBaseSql.getInstance().getAllBoardsOfProject(project.getId())
                                            .get(BoardDataBaseSql.getInstance().getAllBoardsOfProject(project.getId()).size() - 1).getId(), issue.getId());
                        }
                    }
                    projectPanel.kanbanButton.doClick();
                }
            }
        });

        add(nameLabel);
        add(nameField);
        add(nameErrorLabel);
        add(userLabel);
        add(userComboBox);
        add(userErrorLabel);
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
