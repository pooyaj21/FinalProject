package Project.Ui;

import Project.Logic.Board;
import Project.Logic.DataBase.BoardDatabase;
import Project.Logic.DataBase.BoardManager;
import Project.Logic.DataBase.ProjectManager;
import Project.Logic.FeatureAccess;
import Project.Logic.Project;
import Project.Util.GeneralController;
import Project.Util.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditBoardPanel extends JPanel {
    Board board;
    Project project;
    ProjectPanel projectPanel;
    ProjectManager projectManager = ProjectManager.getInstance();
    BoardManager boardManager = BoardManager.getInstance();
    JLabel nameLabel = new JLabel("Name:");
    JTextField nameField = new JTextField();
    RoundedButton submit = new RoundedButton("Submit", 15, Color.blue, Color.white, 12);
    RoundedButton delete = new RoundedButton("Delete", 15, Color.red, Color.white, 12);
    JLabel nameErrorLabel = new JLabel();

    public EditBoardPanel(Board board, ProjectPanel projectPanel) {
        this.projectPanel=projectPanel;
        this.board = board;
        setSize(770, 680);
        setLayout(null);

        nameLabel.setBounds(175, 130, 75, 25);
        nameField.setBounds(225, 130, 200, 25);
        nameErrorLabel.setBounds(225, 155, 200, 25);


        delete.setBounds(430, 300, 100, 25);
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BoardDatabase.getInstance().removeBoard(board);
                projectManager.removeBoardFromProject(project, board);
                projectPanel.kanbanButton.doClick();
                setVisible(false);
            }
        });
        delete.setVisible(projectPanel.getUser().getRole().hasAccess(FeatureAccess.DELETE_BOARD));
        submit.setBounds(550, 300, 100, 25);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameErrorLabel.setText("");
                if (GeneralController.getInstance().isEmpty(nameField.getText())) {
                    nameErrorLabel.setForeground(Color.red);
                    nameErrorLabel.setText("Enter a name");
                } else {
                    boardManager.editBoardName(board, nameField.getText());
                    update();
                    projectPanel.kanbanButton.doClick();
                }
            }
        });


        add(nameLabel);
        add(nameField);
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
