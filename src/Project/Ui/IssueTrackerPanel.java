package Project.Ui;

import Project.Logic.*;
import Project.Logic.DataBase.SQL.BoardDataBaseSql;
import Project.Logic.DataBase.SQL.CrossTabel.BoardIssuesDataBaseSql;
import Project.Logic.DataBase.SQL.CrossTabel.UserProjectDataBaseSql;
import Project.Logic.DataBase.SQL.IssueDataBaseSql;
import Project.Logic.DataBase.SQL.IssuesTransitionSql;
import Project.Util.DateUtil;
import Project.Util.EnumChanger;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class IssueTrackerPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private Project project;
    private User user;

    public IssueTrackerPanel(Project project,User user) {
        this.project=project;
        this.user=user;

        setSize(780, 600);
        setLayout(new BorderLayout());
        tableModel = new DefaultTableModel(new Object[]{"Description", "Type", "Priority", "Status","User"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };
        table = new JTable(tableModel) {
            @Override
            public TableCellEditor getCellEditor(int row, int column) {
                int modelColumn = convertColumnIndexToModel(column);
                if (modelColumn == 1 || modelColumn == 2 || modelColumn == 3|| modelColumn == 4) {
                    return null;
                }
                return super.getCellEditor(row, column);
            }
        };
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow >= 0) {
                        String description = table.getValueAt(selectedRow, 0).toString();
                        String type = table.getValueAt(selectedRow, 1).toString();
                        String priority = table.getValueAt(selectedRow, 2).toString();
                        String status = table.getValueAt(selectedRow, 3).toString();
                        String userName =null;
                        if (table.getValueAt(selectedRow,4)!=null) userName=table.getValueAt(selectedRow,4).toString();
                        if (user.getRole().hasAccess(FeatureAccess.EDIT_BUG)||user.getRole().hasAccess(FeatureAccess.EDIT_ISSUES)) {
                            if (user.getRole().hasAccess(FeatureAccess.EDIT_BUG)&&!type.equals("Bug"))return;
                            openEditDialog(description, type, priority, status,userName);
                        }
                    }
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Issue");
        addButton.setVisible(user.getRole().hasAccess(FeatureAccess.ADD_ISSUES)||user.getRole().hasAccess(FeatureAccess.ADD_BUG));
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addIssue();
            }
        });
        buttonPanel.add(addButton);
        add(buttonPanel, BorderLayout.SOUTH);
        addButton.requestFocusInWindow();

        addFirstTime();
    }

    public void addFirstTime(){
        ArrayList<Issue> issues = IssueDataBaseSql.getInstance().getAllIssuesOfProject(project.getId());
        for (Issue issue : issues) {
            String userName=null;
            if (issue.getUser()!=null)userName=issue.getUser().getFullName();
            String[] rowData = {
                    issue.getDescription(),
                    EnumChanger.toString(issue.getType()),
                    EnumChanger.toString(issue.getPriority()),
                    EnumChanger.toString(issue.getStatus()),
                    userName
            };
            tableModel.addRow(rowData);
        }
    }

    private void addIssue() {
        // Create a dialog to get issue details
        JDialog addIssueDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add Issue", true);
        addIssueDialog.setLayout(new GridLayout(6, 2));


        JTextField descriptionField = new JTextField();
        JComboBox<String> typeComboBox = new JComboBox<>(EnumChanger.toStringArray(Type.values()));
        JComboBox<String> priorityComboBox = new JComboBox<>(EnumChanger.toStringArray(Priority.values()));
        JComboBox<String> userComboBox = new JComboBox<>();
        userComboBox.addItem(null);
        for (User u : UserProjectDataBaseSql.getInstance().getAllUsersOfProject(project.getId())) {userComboBox.addItem(u.getFullName());}


        addIssueDialog.add(new JLabel("Description:"));
        addIssueDialog.add(descriptionField);
        addIssueDialog.add(new JLabel("Type:"));
        if(user.getRole().hasAccess(FeatureAccess.ADD_ISSUES))addIssueDialog.add(typeComboBox);
        else if (user.getRole().hasAccess(FeatureAccess.ADD_BUG))addIssueDialog.add(new JLabel(EnumChanger.toString(Type.BUG)));
        addIssueDialog.add(new JLabel("Priority:"));
        addIssueDialog.add(priorityComboBox);
        addIssueDialog.add(new JLabel("Status:"));
        addIssueDialog.add(new JLabel(EnumChanger.toString(Status.TODO)));
        addIssueDialog.add(new JLabel("User:"));
        addIssueDialog.add(userComboBox);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String description = descriptionField.getText();
                Type type = null;
                if(user.getRole().hasAccess(FeatureAccess.ADD_ISSUES)) type= Type.values()[typeComboBox.getSelectedIndex()];
                else if (user.getRole().hasAccess(FeatureAccess.ADD_BUG)) type=Type.BUG;
                Priority priority =  Priority.values()[priorityComboBox.getSelectedIndex()];
                User selectedUser = null;
                if (userComboBox.getSelectedIndex()>0) selectedUser= UserProjectDataBaseSql.getInstance().getAllUsersOfProject(project.getId()).get(userComboBox.getSelectedIndex()-1);
                Issue issue = new Issue(description);
                issue.setType(type);
                issue.setPriority(priority);
                issue.setStatus(Status.TODO);
                issue.setUser(selectedUser);
                IssueDataBaseSql.getInstance().createIssue(issue,project.getId());
                BoardIssuesDataBaseSql.getInstance().assignIssueToBoard(BoardDataBaseSql.getInstance().getAllBoardsOfProject(project.getId()).get(0).getId()
                        ,IssueDataBaseSql.getInstance().getAllIssuesOfProject(project.getId())
                                .get(IssueDataBaseSql.getInstance().getAllIssuesOfProject(project.getId()).size()-1).getId()
                );
                String userName=null;
                if (selectedUser!=null)userName=selectedUser.getFullName();

                String[] rowData = {description, EnumChanger.toString(type),
                        EnumChanger.toString(priority), EnumChanger.toString(Status.TODO),userName};
                tableModel.addRow(rowData);
                addIssueDialog.dispose();
            }
        });
        addIssueDialog.setResizable(false);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addIssueDialog.dispose();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        addIssueDialog.add(buttonPanel);
        addIssueDialog.pack();
        addIssueDialog.setLocationRelativeTo(this);
        addIssueDialog.setVisible(true);
    }
    private void openEditDialog(String description, String type, String priority, String status,String userName) {
        Issue issue= IssueDataBaseSql.getInstance().getAllIssuesOfProject(project.getId()).get(table.getSelectedRow());
        JDialog editIssueDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Edit Issue", true);
        editIssueDialog.setLayout(new GridLayout(6, 2));

        JTextField descriptionField = new JTextField(description);
        JComboBox<String> typeComboBox = new JComboBox<>(EnumChanger.toStringArray(Type.values()));
        JComboBox<String> priorityComboBox = new JComboBox<>(EnumChanger.toStringArray(Priority.values()));
        JComboBox<String> statusComboBox = new JComboBox<>(EnumChanger.toStringArray(Status.values()));
        JComboBox<String> userComboBox = new JComboBox<>();
        userComboBox.addItem(null);
        for (User u : UserProjectDataBaseSql.getInstance().getAllUsersOfProject(project.getId())) {userComboBox.addItem(u.getFullName());}


        if (user.getRole().hasAccess(FeatureAccess.EDIT_BUG)&&!type.equals(EnumChanger.toString(Type.BUG)))editIssueDialog.dispose();

        typeComboBox.setSelectedItem(type);
        priorityComboBox.setSelectedItem(priority);
        statusComboBox.setSelectedItem(status);
        userComboBox.setSelectedItem(userName);

        editIssueDialog.add(new JLabel("Description:"));
        editIssueDialog.add(descriptionField);
        editIssueDialog.add(new JLabel("Type:"));
        if(user.getRole().hasAccess(FeatureAccess.EDIT_ISSUES))editIssueDialog.add(typeComboBox);
        else if (user.getRole().hasAccess(FeatureAccess.EDIT_BUG))editIssueDialog.add(new JLabel(EnumChanger.toString(Type.BUG)));
        editIssueDialog.add(new JLabel("Priority:"));
        editIssueDialog.add(priorityComboBox);
        editIssueDialog.add(new JLabel("Status:"));
        if (user.getRole().hasAccess(FeatureAccess.EDIT_BUG)&& !status.equals(EnumChanger.toString(Status.QA))) editIssueDialog.add(new JLabel(status));
        else editIssueDialog.add(statusComboBox);
        editIssueDialog.add(new JLabel("User:"));
        editIssueDialog.add(userComboBox);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newDescription = descriptionField.getText();
                String newType = typeComboBox.getSelectedItem().toString();
                String newPriority = priorityComboBox.getSelectedItem().toString();
                String newStatus = statusComboBox.getSelectedItem().toString();
                String newUserName =null;
                if (userComboBox.getSelectedItem()!= null) newUserName = userComboBox.getSelectedItem().toString();
                if (issue.getStatus()!=Status.values()[statusComboBox.getSelectedIndex()]){
                    IssuesTransitionSql.getInstance().createIssueTransition(issue.getId(),issue.getStatus()
                            ,Status.values()[statusComboBox.getSelectedIndex()],DateUtil.timeOfNow());
                }
                issue.setDescription(descriptionField.getText());
                if(user.getRole().hasAccess(FeatureAccess.EDIT_ISSUES)) issue.setType(Type.values()[typeComboBox.getSelectedIndex()]);
                else if (user.getRole().hasAccess(FeatureAccess.EDIT_BUG))issue.setType(Type.BUG);
                issue.setPriority(Priority.values()[ priorityComboBox.getSelectedIndex()]);
                issue.setStatus(Status.values()[ statusComboBox.getSelectedIndex()]);
                if (userComboBox.getSelectedIndex()>0)issue.setUser(UserProjectDataBaseSql.getInstance().getAllUsersOfProject(project.getId()).get(userComboBox.getSelectedIndex()-1));
                else issue.setUser(null);
                issue.setLastUpdateTime(DateUtil.timeOfNow());

                // Update the table with the edited data
                table.setValueAt(newDescription, table.getSelectedRow(), 0);
                table.setValueAt(newType, table.getSelectedRow(), 1);
                table.setValueAt(newPriority, table.getSelectedRow(), 2);
                table.setValueAt(newStatus, table.getSelectedRow(), 3);
                table.setValueAt(newUserName, table.getSelectedRow(), 4);

                IssueDataBaseSql.getInstance().editIssue(issue);

                editIssueDialog.dispose();
                table.clearSelection();
            }

        });
        editIssueDialog.setResizable(false);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editIssueDialog.dispose();
            }
        });

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IssueDataBaseSql.getInstance().removeIssue(issue.getId());
                tableModel.removeRow(table.getSelectedRow());
                editIssueDialog.dispose();
                table.clearSelection();
            }
        });
        deleteButton.setVisible(user.getRole().hasAccess(FeatureAccess.DELETE_ISSUES));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(deleteButton); // Add the delete button


        editIssueDialog.add(buttonPanel);
        editIssueDialog.pack();
        editIssueDialog.setLocationRelativeTo(this);
        editIssueDialog.setVisible(true);
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
