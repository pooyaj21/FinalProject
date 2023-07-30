package Project.Util;

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


    public boolean isEmpty(String a) {
        return a.trim().isEmpty();
    }

}
