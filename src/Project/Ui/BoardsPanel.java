package Project.Ui;

import Project.Logic.Board;
import Project.Logic.DataBase.SQL.BoardDataBaseSql;
import Project.Logic.DataBase.SQL.CrossTabel.ProjectBoardDataBaseSql;
import Project.Logic.FeatureAccess;
import Project.Logic.Project;
import Project.Logic.User;
import Project.Util.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardsPanel extends JScrollPane {

    public BoardsPanel(Project project, User user, ProjectPanel projectPanel) {
        setSize(new Dimension(780, 600));
        setBorder(null);
        int cols = 4;
        int panelWidth = (getWidth() / cols) - 16;
        int panelHeight = 100;
        int hGap = 10;
        int vGap = 10;

        JPanel contentPanel = new JPanel(null);

        int numberOfPanels = BoardDataBaseSql.getInstance().getAllBoardsOfProject(project.getId()).size() + 1;
        for (int i = 0; i < numberOfPanels; i++) {
            int row = i / cols;
            int colum = i % cols;
            int x = colum * (panelWidth + hGap) + 5;
            int y = row * (panelHeight + vGap) + 5;

            if (numberOfPanels - 1 == i) {
                RoundedButton addBoardButton = new RoundedButton("ADD+",
                        15, Color.WHITE, Color.BLACK, 15);
                addBoardButton.setBounds(x, y, panelWidth, panelHeight);
                contentPanel.add(addBoardButton);
                addBoardButton.setVisible(user.getRole().hasAccess(FeatureAccess.ADD_BOARD));
                addBoardButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        projectPanel.showAddBoardsPAnel();
                    }
                });
            } else {
                Board board = BoardDataBaseSql.getInstance().getAllBoardsOfProject(project.getId()).get(i);
                RoundedButton boardButton = new RoundedButton(board.getName(),
                        15, Color.LIGHT_GRAY, Color.BLACK, 12);
                boardButton.setBounds(x, y, panelWidth, panelHeight);
                contentPanel.add(boardButton);
                boardButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        projectPanel.showKanBanBoardsPanel(board);
                    }

                });
                if (user.getRole().hasAccess(FeatureAccess.EDIT_BOARD)&&board!=BoardDataBaseSql.getInstance().getAllBoardsOfProject(project.getId()).get(0)) {
                    boardButton.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            if (SwingUtilities.isRightMouseButton(e)) {
                                projectPanel.showEditBoardsPanel(board);
                            }
                        }
                    });
                }
            }
        }
        int calculatedHeight = ((panelHeight + vGap) * numberOfPanels / cols) + 100;
        contentPanel.setPreferredSize(new Dimension(770, calculatedHeight));

        setViewportView(contentPanel);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }
}
