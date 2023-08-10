package Project.Util;

import javax.swing.*;
import java.awt.*;

public class GeneralController {
    private static GeneralController instance;

    private GeneralController() {
    }

    public static GeneralController getInstance() {
        if (instance == null) {
            synchronized (GeneralController.class) {
                if (instance == null) {
                    instance = new GeneralController();
                }
            }
        }
        return instance;
    }

    public static String addLineBreaksHTML(String input) {
        if (input != null) {
            StringBuilder output = new StringBuilder();
            int length = input.length();
            int currentIndex = 0;

            while (currentIndex < length) {
                // Check if there is a newline character (\n) in the remaining text
                int nextNewLine = input.indexOf('\n', currentIndex);

                if (nextNewLine != -1 && nextNewLine < currentIndex + 25) {
                    // Append the characters up to the newline and a <br> tag
                    output.append(input, currentIndex, nextNewLine);
                    output.append("<br>");
                    currentIndex = nextNewLine + 1;
                } else if (currentIndex + 25 <= length) {
                    // Find the last space within the lineLength characters
                    int lastSpace = input.lastIndexOf(' ', currentIndex + 25);
                    int breakIndex;
                    if (lastSpace != -1 && lastSpace >= currentIndex) {
                        // Include the word before the last space
                        breakIndex = lastSpace + 1;
                    } else {
                        // No space found, break at lineLength
                        breakIndex = currentIndex + 25;
                    }

                    // Append the characters up to the breakIndex and a <br> tag
                    output.append(input, currentIndex, breakIndex);
                    output.append("<br>");
                    currentIndex = breakIndex;
                } else {
                    // Append the remaining characters
                    output.append(input.substring(currentIndex));
                    currentIndex = length;
                }
            }
            return "<html>" + output + "<html>";
        }
        return null;
    }

    public static int findHighest(int[] numbers) {
        if (numbers.length == 0) {
            throw new IllegalArgumentException("No numbers provided");
        }

        int highest = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] > highest) {
                highest = numbers[i];
            }
        }
        return highest;
    }

    public ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }

    public boolean isEmpty(String a) {
        return a.trim().isEmpty();
    }
    public static boolean emailAuthentication(String email) {
        return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    public static Color generateRandomColor() {
        int red = (int) (Math.random() * 256);
        int green = (int) (Math.random() * 256);
        int blue = (int) (Math.random() * 256);

        return new Color(red, green, blue);
    }
}
