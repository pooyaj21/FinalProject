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

    public boolean isEmpty(String a) {
        return a.isEmpty();
    }
}
