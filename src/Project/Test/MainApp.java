package Project.Test;

import Project.Ui.AppFrame;

import javax.swing.*;

public class MainApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AppFrame.getInstance();
        });
    }
}
