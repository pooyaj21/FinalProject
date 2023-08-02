package Project.Ui;

import Project.Logic.*;
import Project.Logic.DataBase.ProjectManager;
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
    private final ProjectManager projectManager= ProjectManager.getInstance();
    private Project project;
    private User user;

    public IssueTrackerPanel(Project project,User user) {
        this.project=project;
        setSize(780, 600);
        setLayout(new BorderLayout());
        tableModel = new DefaultTableModel(new Object[]{"Description", "Type", "Priority", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };
        table = new JTable(tableModel) {
            @Override
            public TableCellEditor getCellEditor(int row, int column) {
                int modelColumn = convertColumnIndexToModel(column);
                if (modelColumn == 1 || modelColumn == 2 || modelColumn == 3) {
                    return null;
                }
                return super.getCellEditor(row, column);
            }
        };
        setupComboBoxEditors(); // Set up combo box editors for Type, Priority, and Status columns
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

                        openEditDialog(description, type, priority, status);
                    }
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Issue");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addIssue();
            }
        });
        buttonPanel.add(addButton);
        add(buttonPanel, BorderLayout.SOUTH);

        addFirstTime();
    }

    public void addFirstTime(){
        ArrayList<Issue> issues = projectManager.getIssuesByProject(project);
        for (Issue issue : issues) {
            String[] rowData = {
                    issue.getDescription(),
                    EnumChanger.toString(issue.getType()),
                    EnumChanger.toString(issue.getPriority()),
                    EnumChanger.toString(issue.getStatus())
            };
            tableModel.addRow(rowData);
        }
    }
    private void setupComboBoxEditors() {
        JComboBox<Type> typeComboBox = new JComboBox<>(Type.values());
        JComboBox<Priority> priorityComboBox = new JComboBox<>(Priority.values());
        JComboBox<Status> statusComboBox = new JComboBox<>(Status.values());

        table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(typeComboBox));
        table.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(priorityComboBox));
        table.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(statusComboBox));
    }

    private void addIssue() {
        // Create a dialog to get issue details
        JDialog addIssueDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add Issue", true);
        addIssueDialog.setLayout(new GridLayout(5, 2));

        JTextField descriptionField = new JTextField();
        JComboBox<String> typeComboBox = new JComboBox<>(EnumChanger.toStringArray(Type.values()));
        JComboBox<String> priorityComboBox = new JComboBox<>(EnumChanger.toStringArray(Priority.values()));
        JComboBox<String> statusComboBox = new JComboBox<>(EnumChanger.toStringArray(Status.values()));

        addIssueDialog.add(new JLabel("Description:"));
        addIssueDialog.add(descriptionField);
        addIssueDialog.add(new JLabel("Type:"));
        addIssueDialog.add(typeComboBox);
        addIssueDialog.add(new JLabel("Priority:"));
        addIssueDialog.add(priorityComboBox);
        addIssueDialog.add(new JLabel("Status:"));
        addIssueDialog.add(statusComboBox);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String description = descriptionField.getText();
                Type type =  Type.values()[typeComboBox.getSelectedIndex()];
                Priority priority =  Priority.values()[priorityComboBox.getSelectedIndex()];
                Status status = Status.values()[statusComboBox.getSelectedIndex()];
                Issue issue = new Issue(description);
                issue.setType(type);
                issue.setPriority(priority);
                issue.setStatus(status);
                projectManager.createIssue(project,issue);
                String[] rowData = {description, EnumChanger.toString(type), EnumChanger.toString(priority), EnumChanger.toString(status)};
                tableModel.addRow(rowData);
                addIssueDialog.dispose();
            }
        });

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
    private void openEditDialog(String description, String type, String priority, String status) {
        JDialog editIssueDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Edit Issue", true);
        editIssueDialog.setLayout(new GridLayout(5, 2));

        JTextField descriptionField = new JTextField(description);
        JComboBox<String> typeComboBox = new JComboBox<>(EnumChanger.toStringArray(Type.values()));
        JComboBox<String> priorityComboBox = new JComboBox<>(EnumChanger.toStringArray(Priority.values()));
        JComboBox<String> statusComboBox = new JComboBox<>(EnumChanger.toStringArray(Status.values()));

        typeComboBox.setSelectedItem(type);
        priorityComboBox.setSelectedItem(priority);
        statusComboBox.setSelectedItem(status);

        editIssueDialog.add(new JLabel("Description:"));
        editIssueDialog.add(descriptionField);
        editIssueDialog.add(new JLabel("Type:"));
        editIssueDialog.add(typeComboBox);
        editIssueDialog.add(new JLabel("Priority:"));
        editIssueDialog.add(priorityComboBox);
        editIssueDialog.add(new JLabel("Status:"));
        editIssueDialog.add(statusComboBox);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newDescription = descriptionField.getText();
                String newType = typeComboBox.getSelectedItem().toString();
                String newPriority = priorityComboBox.getSelectedItem().toString();
                String newStatus = statusComboBox.getSelectedItem().toString();

                Issue issue= projectManager.getIssuesByProject(project).get(table.getSelectedRow());
                issue.setDescription(descriptionField.getText());
                issue.setType(Type.values()[ typeComboBox.getSelectedIndex()]);
                issue.setPriority(Priority.values()[ priorityComboBox.getSelectedIndex()]);
                issue.setStatus(Status.values()[ statusComboBox.getSelectedIndex()]);

                // Update the table with the edited data
                table.setValueAt(newDescription, table.getSelectedRow(), 0);
                table.setValueAt(newType, table.getSelectedRow(), 1);
                table.setValueAt(newPriority, table.getSelectedRow(), 2);
                table.setValueAt(newStatus, table.getSelectedRow(), 3);

                editIssueDialog.dispose();
                table.clearSelection();
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editIssueDialog.dispose();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        editIssueDialog.add(buttonPanel);
        editIssueDialog.pack();
        editIssueDialog.setLocationRelativeTo(this);
        editIssueDialog.setVisible(true);
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
