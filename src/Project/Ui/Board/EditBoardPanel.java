package Project.Ui.Board;

import Project.Logic.*;
import Project.Logic.DataBase.SQL.BoardDataBaseSql;
import Project.Logic.DataBase.SQL.CrossTabel.BoardIssuesDataBaseSql;
import Project.Logic.DataBase.SQL.CrossTabel.UserProjectDataBaseSql;
import Project.Logic.DataBase.SQL.IssueDataBaseSql;
import Project.Ui.Project.ProjectPanel;
import Project.Util.EnumChanger;
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
    JComboBox<String> userComboBox = new JComboBox<>();
    ArrayList<User> users = new ArrayList<>();
    JLabel userLabel = new JLabel("User:");
    RoundedButton submit = new RoundedButton("Submit", 15, Color.blue, Color.white, 12);
    RoundedButton delete = new RoundedButton("Delete", 15, Color.red, Color.white, 12);
    JLabel nameErrorLabel = new JLabel("Enter a name");

    public EditBoardPanel(Board board,Project project , ProjectPanel projectPanel) {
        this.projectPanel=projectPanel;
        this.board = board;
        setSize(770, 680);
        setLayout(null);
        nameLabel.setBounds(125, 100, 75, 25);
        nameField.setBounds(170, 100, 200, 25);

        userLabel.setBounds(125, 150, 75, 25);
        userComboBox.setBounds(170, 150, 200, 25);
        userComboBox.addItem(null);
        for (User user : UserProjectDataBaseSql.getInstance().getAllUsersOfProject(project.getId())) {
            userComboBox.addItem(user.getFullName());
            users.add(user);
        }


        JLabel statusLabel = new JLabel("Status:");
        JComboBox<String> statusComboBox = new JComboBox<>(EnumChanger.toStringArray(Status.values()));
        statusLabel.setBounds(125, 200, 75, 25);
        statusComboBox.setBounds(170, 200, 200, 25);
        statusComboBox.insertItemAt(null, 0);
        statusComboBox.setSelectedIndex(0);


        JLabel typeLabel = new JLabel("Type:");
        JComboBox<String> typeComboBox = new JComboBox<>(EnumChanger.toStringArray(Type.values()));
        typeLabel.setBounds(125, 250, 75, 25);
        typeComboBox.setBounds(170, 250, 200, 25);
        typeComboBox.insertItemAt(null, 0);
        typeComboBox.setSelectedIndex(0);

        JLabel priorityLabel = new JLabel("Priority:");
        JComboBox<String> priorityComboBox = new JComboBox<>(EnumChanger.toStringArray(Priority.values()));
        priorityLabel.setBounds(125, 300, 75, 25);
        priorityComboBox.setBounds(170, 300, 200, 25);
        priorityComboBox.insertItemAt(null, 0);
        priorityComboBox.setSelectedIndex(0);

        submit.setBounds(350, 350, 100, 25);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameErrorLabel.setVisible(false);
                if (GeneralController.getInstance().isEmpty(nameField.getText())) {
                    nameErrorLabel.setForeground(Color.red);
                    nameErrorLabel.setVisible(true);
                }else {
                    Type type = null;
                    Priority priority = null;
                    Status status = null;
                    User userName = null;
                    if (typeComboBox.getSelectedIndex() > 0)
                        type = Type.values()[typeComboBox.getSelectedIndex() - 1];
                    if (priorityComboBox.getSelectedIndex() > 0)
                        priority = Priority.values()[priorityComboBox.getSelectedIndex() - 1];
                    if (statusComboBox.getSelectedIndex() > 0)
                        status = Status.values()[statusComboBox.getSelectedIndex() - 1];
                    if (userComboBox.getSelectedIndex() > 0)
                        userName = UserProjectDataBaseSql.getInstance().getAllUsersOfProject(project.getId()).get(userComboBox.getSelectedIndex()-1);
                    BoardDataBaseSql.getInstance().editBoard(project.getId(), nameField.getText());
                    for (Issue issue:BoardIssuesDataBaseSql.getInstance().getAllIssuesOfBoard(board.getId())) {
                        BoardIssuesDataBaseSql.getInstance().removeIssueFromBoard(issue.getId(),board.getId());
                    }
                    for (Issue issue : IssueDataBaseSql.getInstance().filterIssues(project.getId(), priority, type, status, userName)) {
                        BoardIssuesDataBaseSql.getInstance().assignIssueToBoard(board.getId(), issue.getId());
                    }
                    projectPanel.kanbanButton.doClick();
                }
            }
        });


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
        add(userLabel);
        add(userComboBox);
        add(statusLabel);
        add(statusComboBox);
        add(typeLabel);
        add(typeComboBox);
        add(priorityLabel);
        add(priorityComboBox);
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
