package Project.Ui.KanbanBoard;

import Project.Logic.Status;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CategoryPanel extends JPanel {
    ArrayList<TaskPanel> tasks;
    int lowestY = 10;
    private Status status;
    public CategoryPanel(Status status) {
        this.status=status;
        tasks = new ArrayList<>();
        setVisible(true);
        setLayout(null);
    }

    public void addTask(TaskPanel task) {
        tasks.add(task);
    }

    public void removeTask(TaskPanel task) {
        tasks.remove(task);
    }

    public void showTasks() {
        lowestY = 0;
        int categoryPanelX = getX();
        setBackground(null);

        for (TaskPanel task : tasks) {
            task.setBounds(categoryPanelX + 10, lowestY, 180, 130);
            lowestY += task.getHeight() + 5;
        }
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        int width = super.getPreferredSize().width;
        int height = calculateTotalHeight();
        return new Dimension(width, height);
    }
    private int calculateTotalHeight() {
        int totalHeight = 10;
        for (TaskPanel task : tasks) {
            totalHeight += task.getHeight() + 5; // Adjust the spacing between tasks
        }
        return totalHeight;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
