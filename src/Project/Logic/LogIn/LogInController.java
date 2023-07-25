package Project.Logic.LogIn;

public class LogInController {
    private static LogInController instance;

    private LogInController() {
    }

    public static LogInController getInstance() {
        if (instance == null) {
            instance = new LogInController();
        }
        return instance;
    }

}
