package Project.Ui.KanbanBoard;

import Project.Logic.DataBase.SQL.IssueDataBaseSql;
import Project.Logic.DataBase.SQL.IssuesTransitionSql;
import Project.Logic.FeatureAccess;
import Project.Logic.Issue;
import Project.Logic.Status;
import Project.Logic.User;
import Project.Util.DateUtil;
import Project.Util.RoundedPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class IssuesPanel extends JPanel {
    private final JLabel title = new JLabel();
    private final JLabel assignedUser = new JLabel();
    Issue issue;
    User user;
    private boolean canMove = false;
    private CategoryPanel currentColumn;
    private Point offset;


    public IssuesPanel(CategoryPanel categoryPanel, KanbanBoardPanel kanbanBoardPanel, Issue issue) {
        this.issue = issue;
        currentColumn = categoryPanel;
        setBackground(Color.white);
        setLayout(null);

        title.setBounds(10, 60, 160, 20);
        assignedUser.setFont(new Font(null,Font.BOLD,12));
        add(title);

        assignedUser.setBounds(90, 100, 80, 25);
        assignedUser.setFont(new Font(null,Font.PLAIN,10));
        add(assignedUser);


        RoundedPanel issueType = new RoundedPanel(issue.getType().getIssuesTypes().getName(), 17,
                issue.getType().getIssuesTypes().getColor(), Color.WHITE, 11);
        issueType.setBounds(10, 5, 50, 20);
        issueType.setFocusable(false);
        add(issueType);

        RoundedPanel issuePriority = new RoundedPanel(issue.getPriority().getIssuesPriority().getName(), 17,
                issue.getPriority().getIssuesPriority().getColor(), Color.WHITE, 11);
        issuePriority.setBounds(65, 5, 50, 20);
        issuePriority.setFocusable(false);
        add(issuePriority);

        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setOpaque(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                offset = e.getPoint();
                getParent().setComponentZOrder(IssuesPanel.this, 0);

                double mouseX = e.getXOnScreen();
                double panelX = kanbanBoardPanel.getLocationOnScreen().getX();
                double columnWidth = kanbanBoardPanel.getWidth() / 4.0;
                double whichColumn = (mouseX - panelX) / columnWidth;
                if (whichColumn < 1) {
                    canMove = user.getRole().hasAccess(FeatureAccess.MOVE_EVERYWHERE) || user.getId() == issue.getUser().getId();
                } else if (whichColumn >= 1 && whichColumn < 2) {
                    canMove = user.getRole().hasAccess(FeatureAccess.MOVE_EVERYWHERE) || user.getId() == issue.getUser().getId();
                } else if (whichColumn >= 2 && whichColumn < 3) {
                    canMove = user.getRole().hasAccess(FeatureAccess.MOVE_EVERYWHERE) || user.getRole().hasAccess(FeatureAccess.MOVE_FROM_QA);
                } else if (whichColumn >= 3 && whichColumn < 4) {
                    canMove = user.getRole().hasAccess(FeatureAccess.MOVE_EVERYWHERE);
                } else {
                    canMove = false;
                }
            }


            @Override
            public void mouseReleased(MouseEvent e) {
                double mouseX = e.getXOnScreen(); // Get the mouse X coordinate on the screen
                double panelX = kanbanBoardPanel.getLocationOnScreen().getX(); // Get the panel's X coordinate on the screen
                double columnWidth = kanbanBoardPanel.getWidth() / 4.0; // Divide the width into 5 equal parts
                double whichColumn = (mouseX - panelX) / columnWidth; // Calculate which column the mouse is released in


                CategoryPanel newColumn = null;

                if (whichColumn < 1 && canMove) {
                    newColumn = kanbanBoardPanel.toDo;
                    IssuesTransitionSql.getInstance().createIssueTransition(issue.getId()
                            ,issue.getStatus(),Status.TODO, DateUtil.timeOfNow());
                    issue.setStatus(Status.TODO);
                    IssueDataBaseSql.getInstance().editIssue(issue);
                } else if (whichColumn >= 1 && whichColumn < 2 && canMove) {
                    newColumn = kanbanBoardPanel.inProgress;
                    IssuesTransitionSql.getInstance().createIssueTransition(issue.getId()
                            ,issue.getStatus(),Status.IN_PROGRESS, DateUtil.timeOfNow());
                    issue.setStatus(Status.IN_PROGRESS);
                    IssueDataBaseSql.getInstance().editIssue(issue);
                } else if (whichColumn >= 2 && whichColumn < 3 && canMove) {
                    newColumn = kanbanBoardPanel.qa;
                    IssuesTransitionSql.getInstance().createIssueTransition(issue.getId()
                            ,issue.getStatus(),Status.QA, DateUtil.timeOfNow());
                    issue.setStatus(Status.QA);
                    IssueDataBaseSql.getInstance().editIssue(issue);
                } else if (whichColumn >= 3 && whichColumn < 4 && user.getRole().hasAccess(FeatureAccess.MOVE_EVERYWHERE) || user.getRole().hasAccess(FeatureAccess.MOVE_FROM_QA)) {
                    newColumn = kanbanBoardPanel.done;
                    IssuesTransitionSql.getInstance().createIssueTransition(issue.getId()
                            ,issue.getStatus(),Status.DONE, DateUtil.timeOfNow());
                    issue.setStatus(Status.DONE);
                    IssueDataBaseSql.getInstance().editIssue(issue);
                } else kanbanBoardPanel.addTask(IssuesPanel.this, currentColumn);


                if (currentColumn != null && currentColumn != newColumn) {
                    kanbanBoardPanel.removeTask(IssuesPanel.this, currentColumn);
                }
                if (newColumn != null && currentColumn != newColumn) {
                    currentColumn = newColumn;
                    kanbanBoardPanel.addTask(IssuesPanel.this, currentColumn);
                }

                kanbanBoardPanel.reset();
            }
        });


        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (canMove) {
                    int x = getX() + e.getX() - offset.x;
                    int y = getY() + e.getY() - offset.y;
                    setLocation(x, y);
                    getParent().repaint();
                }
            }
        });


    }


    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        Shape shape = new RoundRectangle2D.Double(0, 0, width - 1, height - 1, 40, 40);

        g2.setColor(getBackground());
        g2.fill(shape);
        Stroke borderStroke = new BasicStroke(0f);
        g2.setStroke(borderStroke);
        g2.setColor(getForeground());
        g2.draw(shape);

        super.paintComponent(g2);
        g2.dispose();


    }

    public void setTitle(String theTitle) {
        title.setText(theTitle);
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public void setUser(User user) {
        this.user = user;
        if (issue.getUser() != null) assignedUser.setText(issue.getUser().getFullName());
    }
}






