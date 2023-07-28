package Project.Ui;

import Project.Logic.DataBase.ProjectManager;
import Project.Logic.DataBase.UserDatabase;
import Project.Logic.Project;
import Project.Logic.Role;
import Project.Logic.User;
import Project.Util.GeneralController;
import Project.Util.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CreateProjectPanel extends JPanel {
    JLabel nameLabel = new JLabel("Name:");
    JLabel productOwnerLabel = new JLabel("Product Owner:");
    JLabel descriptionLabel = new JLabel("Description:");
    JTextField nameField = new JTextField();
    JComboBox<String> productOwner = new JComboBox<>();
    JTextArea descriptionArea = new JTextArea();
    RoundedButton submit = new RoundedButton("Submit", 15, Color.blue, Color.white, 12);
    JLabel nameErrorLabel = new JLabel();
    JLabel productOwnerErrorLabel = new JLabel();
    ArrayList<User> productOwners = new ArrayList<>();
    UserDatabase userDatabase = UserDatabase.getInstance();
    ProjectManager projectManager = ProjectManager.getInstance();
    public CreateProjectPanel(int x, int y) {
        setBounds(x, y, 600, 600);
        setLayout(null);
        setFocusable(true);
        nameLabel.setBounds(75, 100, 75, 25);
        productOwnerLabel.setBounds(75, 175, 150, 25);
        descriptionLabel.setBounds(75, 250, 100, 25);

        nameErrorLabel.setBounds(170, 125, 200, 25);
        nameErrorLabel.setFont(new Font(null, Font.ITALIC, 10));
        productOwnerErrorLabel.setBounds(170, 200, 200, 25);
        productOwnerErrorLabel.setFont(new Font(null, Font.ITALIC, 10));

        nameField.setBounds(170, 100, 200, 25);
        productOwner.setBounds(170, 175, 200, 25);
        descriptionArea.setBounds(170, 250, 400, 150);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
        InputMap inputMap = getInputMap(condition);
        ActionMap actionMap = getActionMap();

        String enterKey = "enterKey";
        inputMap.put(KeyStroke.getKeyStroke("ENTER"), enterKey);
        actionMap.put(enterKey, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submit.doClick();
            }
        });

        productOwner.addItem(null);
        for (User user : userDatabase.getUsers()) {
            if (user.getRole()==Role.PROJECT_OWNER){
                productOwners.add(user);
                productOwner.addItem(user.getFullName());
            }
        }

        submit.setBounds(400, 500, 100, 25);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameErrorLabel.setText("");
                productOwnerErrorLabel.setText("");
                boolean isNameFine = false;
                boolean isProductOwnerFine = false;
                if (GeneralController.getInstance().isEmpty(nameField.getText())){
                    nameErrorLabel.setForeground(Color.red);
                    nameErrorLabel.setText("Enter a Name");
                }else isNameFine=true;
                if (productOwner.getSelectedIndex()==-1){
                    productOwnerErrorLabel.setForeground(Color.red);
                    productOwnerErrorLabel.setText("Must select a ProductOwner");
                }else isProductOwnerFine = true;

                if (isNameFine&&isProductOwnerFine){
                    Project project = new Project(nameField.getText());
                    project.setDescription(descriptionArea.getText());
                    projectManager.createProject(project);
                    projectManager.addMemberToProject(project,productOwners.get(productOwner.getSelectedIndex()-1));
                    ProjectManagementPanel.getInstance(0,0).repaint();
                    ProjectManagementPanel.getInstance(0,0).drawProjects();
                    nameField.setText("");
                    descriptionArea.setText("");
                    productOwner.setSelectedIndex(0);
                }
            }
        });


        add(nameLabel);
        add(productOwnerLabel);
        add(descriptionLabel);

        add(nameErrorLabel);
        add(productOwnerErrorLabel);


        add(nameField);
        add(productOwner);
        add(descriptionArea);

        add(submit);
    }
}
