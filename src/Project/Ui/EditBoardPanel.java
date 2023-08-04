package Project.Ui;

import Project.Logic.Board;
import Project.Logic.DataBase.BoardDatabase;
import Project.Logic.DataBase.BoardManager;
import Project.Logic.DataBase.ProjectManager;
import Project.Logic.Project;
import Project.Logic.User;
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
    ProjectManager projectManager = ProjectManager.getInstance();
    BoardManager boardManager =BoardManager.getInstance();
    ArrayList<User> allUsers = new ArrayList<>();
    ArrayList<User> availableUsers = new ArrayList<>();
    ArrayList<User> userInList = new ArrayList<>();
    JLabel nameLabel = new JLabel("Name:");
    JLabel userLabel = new JLabel("Users:");
    JTextField nameField = new JTextField();
    JComboBox<String> userDropdown = new JComboBox<>();
    DefaultListModel<String> infoList = new DefaultListModel<>();
    JList<String> userList = new JList<>(infoList);
    JScrollPane listScrollPane = new JScrollPane(userList);
    RoundedButton addButton = new RoundedButton("Add+", 15, Color.blue, Color.white, 12);
    RoundedButton submit = new RoundedButton("Submit", 15, Color.blue, Color.white, 12);
    RoundedButton delete = new RoundedButton("Delete", 15, Color.red, Color.white, 12);
    JLabel addErrorLabel =new JLabel();
    JLabel nameErrorLabel =new JLabel();
    public EditBoardPanel(Board board,ProjectPanel projectPanel) {
        this.board = board;
        setSize(770, 680);
        setLayout(null);

        nameLabel.setBounds(175, 130, 75, 25);
        nameField.setBounds(225, 130, 200, 25);
        nameErrorLabel.setBounds(225, 155, 200, 25);

        userLabel.setBounds(175, 180, 75, 25);
        userDropdown.setBounds(225, 180, 200, 25);
        addButton.setBounds(425, 180, 100, 25);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addErrorLabel.setText("");
                if (userDropdown.getSelectedItem()==null) {
                    addErrorLabel.setForeground(Color.red);
                    addErrorLabel.setText("First select a Project");
                } else {
                    userInList.add(availableUsers.get(userDropdown.getSelectedIndex() - 1));
                    listUpdate();
                }
            }
        });
        addErrorLabel.setBounds(425, 205, 100, 25);
        addErrorLabel.setFont(new Font(null, Font.ITALIC, 10));


        listScrollPane.setBounds(225, 250, 200, 200);

        delete.setBounds(430, 500, 100, 25);
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BoardDatabase.getInstance().removeBoard(board);
                projectManager.removeBoardFromProject(project,board);
                projectPanel.kanbanButton.doClick();
                setVisible(false);
            }
        });
        submit.setBounds(550, 500, 100, 25);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameErrorLabel.setText("");
                if (GeneralController.getInstance().isEmpty(nameField.getText())) {
                    nameErrorLabel.setForeground(Color.red);
                    nameErrorLabel.setText("Enter a name");
                } else {
                    boardManager.editBoardName(board,nameField.getText());
                    for (User user : userInList) {
                        boardManager.addUserToBoard(board,user);
                    }
                    update();
                    projectPanel.kanbanButton.doClick();
                }
            }
        });


        add(nameLabel);
        add(nameField);
        add(userLabel);
        add(userDropdown);
        add(addButton);
        add(addErrorLabel);
        add(listScrollPane);
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

    public void listUpdate() {
        userDropdown.removeAllItems();
        availableUsers.clear();
        userDropdown.addItem(null);
        for (User user : allUsers) {
            if (!userInList.contains(user) && !boardManager.getMembersOfBoard(board).contains(user)) {
                userDropdown.addItem(user.getFullName());
                availableUsers.add(user);
            }
        }

        infoList.removeAllElements();
        for (User user : boardManager.getMembersOfBoard(board)) {
            infoList.addElement(user.getFullName());
        }
        for (User user : userInList) {
            infoList.addElement(user.getFullName());
        }
    }

    public void update() {
        nameField.setText(board.getName());
        userDropdown.removeAllItems();
        allUsers.addAll(projectManager.getUsersByProject(project));
        userInList = new ArrayList<>();
        availableUsers = new ArrayList<>();
        userDropdown.addItem(null);
        for (User user : allUsers) {
            if (!userInList.contains(user) && !boardManager.getMembersOfBoard(board).contains(user)) {
                userDropdown.addItem(user.getFullName());
                availableUsers.add(user);
            }
        }
        infoList.clear();
        for (User user : boardManager.getMembersOfBoard(board)) {
            infoList.addElement(user.getFullName());
        }
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
