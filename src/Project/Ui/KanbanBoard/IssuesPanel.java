package Project.Ui.KanbanBoard;

import Project.Logic.*;
import Project.Logic.DataBase.SQL.BoardDataBaseSql;
import Project.Logic.DataBase.SQL.CrossTabel.BoardIssuesDataBaseSql;
import Project.Logic.DataBase.SQL.IssueDataBaseSql;
import Project.Util.DateUtil;
import Project.Util.RoundedButton;
import Project.Util.RoundedPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class IssuesPanel extends JPanel {
    private final JTextArea title = new JTextArea();
    private final JTextArea theText = new JTextArea();

    private final JPanel settingPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int width = getWidth();
            int height = getHeight();
            Shape shape = new RoundRectangle2D.Double(0, 0, width, height, 30, 30);
            g2.setColor(Color.white);
            g2.fill(shape);
            g2.setColor(Color.BLACK);
            g2.dispose();
        }
    };
    Issue issue;
    private final RoundedButton setting = new RoundedButton("⋮", 30, Color.white, Color.BLACK, 22);
    User user;
    private boolean canMove = false;
    private CategoryPanel currentColumn;
    private Point offset;
    private boolean isSettingOpen = false;


    public IssuesPanel(CategoryPanel categoryPanel, KanbanBoardPanel kanbanBoardPanel, Issue issue) {
        this.issue = issue;
        currentColumn = categoryPanel;
        setBackground(Color.white);
        setLayout(null);
        setTitle("The Title");
        setText("");
        add(settingPanel);
        settingPanel.setVisible(false);
        settingPanel.setLayout(null);

        title.setBounds(10, 30, 160, 20);
        title.setEditable(false);
        title.setFocusable(false);
        title.setOpaque(false);
        add(title);

        theText.setBounds(10, 50, 160, 70);
        theText.setFont(new Font("assets/Montserrat-ExtraLight.ttf", Font.PLAIN, 10));
        theText.setEditable(false);
        theText.setFocusable(false);
        title.setOpaque(false);
        add(theText);


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

        setComponentZOrder(settingPanel, 0);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(Color.WHITE); // Set background color to white
        setOpaque(false);


        kanbanBoardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point clickPoint = e.getPoint();
                SwingUtilities.convertPointToScreen(clickPoint, kanbanBoardPanel);
                SwingUtilities.convertPointFromScreen(clickPoint, IssuesPanel.this);
                if (!IssuesPanel.this.contains(clickPoint)) {
                    settingPanel.setVisible(false);
                    isSettingOpen = false;
                    toggleEditMode(false);
                }
            }
        });
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
                    canMove = user.getRole().hasAccess(FeatureAccess.MOVE_EVERYWHERE) || user == issue.getUser();
                } else if (whichColumn >= 1 && whichColumn < 2) {
                    canMove = user.getRole().hasAccess(FeatureAccess.MOVE_EVERYWHERE) || user == issue.getUser();
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
                    IssueDataBaseSql.getInstance().editIssue(issue.getId(),issue.getDescription(), DateUtil.timeOfNow(),
                            issue.getType().toString(),issue.getPriority().toString(),Status.TODO.toString());
                } else if (whichColumn >= 1 && whichColumn < 2 && canMove) {
                    newColumn = kanbanBoardPanel.inProgress;
                    IssueDataBaseSql.getInstance().editIssue(issue.getId(),issue.getDescription(), DateUtil.timeOfNow(),
                            issue.getType().toString(),issue.getPriority().toString(),Status.IN_PROGRESS.toString());
                } else if (whichColumn >= 2 && whichColumn < 3 && canMove) {
                    newColumn = kanbanBoardPanel.qa;
                    IssueDataBaseSql.getInstance().editIssue(issue.getId(),issue.getDescription(), DateUtil.timeOfNow(),
                            issue.getType().toString(),issue.getPriority().toString(),Status.QA.toString());
                } else if (whichColumn >= 3 && whichColumn < 4 && user.getRole().hasAccess(FeatureAccess.MOVE_EVERYWHERE) || user.getRole().hasAccess(FeatureAccess.MOVE_FROM_QA)) {
                    newColumn = kanbanBoardPanel.done;
                    IssueDataBaseSql.getInstance().editIssue(issue.getId(),issue.getDescription(), DateUtil.timeOfNow(),
                            issue.getType().toString(),issue.getPriority().toString(),Status.DONE.toString());
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

    private void toggleEditMode(boolean editMode) {
        if (editMode) {
            title.setEditable(true);
            theText.setEditable(true);
            title.setFocusable(true);
            theText.setFocusable(true);
        } else {
            title.setEditable(false);
            theText.setEditable(false);
            title.setFocusable(false);
            theText.setFocusable(false);
        }
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

    public void setText(String text) {
        theText.setText(text);
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public void setUser(User user) {
        this.user = user;
        setting.setVisible(user.getRole().hasAccess(FeatureAccess.DELETE_ISSUES));
    }

    public void setCurrentColumn(CategoryPanel currentColumn) {
        this.currentColumn = currentColumn;
    }
}



