import java.util.*;

public class OnlineExaminationSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Map<String, User> users = new HashMap<>();
    private static User currentUser;
    private static final List<Question> questions = Arrays.asList(
            new Question("What is the capital of France?", Arrays.asList("Paris", "Berlin", "Rome", "Madrid"), 0),
            new Question("What is 2 + 2?", Arrays.asList("3", "4", "5", "6"), 1),
            new Question("What is the capital of India?", Arrays.asList("New Dehli", "Rome", "Florence", "Milan"), 0),
            new Question("What is 8 / 2?", Arrays.asList("3", "5", "4", "6"), 2),
            new Question("Which language you are working now?", Arrays.asList("Java", "C", "C++", "Python"), 0)
    );
    private static boolean timeUp = false;

    public static void main(String[] args) {
        initializeUsers();
        while (true) {
            login();
            mainMenu();
        }
    }

    private static void initializeUsers() {
        users.put("Quadir", new User("Quadir", "78692"));
        users.put("Mass", new User("Mass", "12345"));
    }

    private static void login() {
        while (true) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            if (users.containsKey(username) && users.get(username).getPassword().equals(password)) {
                currentUser = users.get(username);
                System.out.println("Login successful. Welcome " + username);
                break;
            } else {
                System.out.println("Invalid username or password. Try again.");
            }
        }
    }

    private static void mainMenu() {
        while (true) {
            System.out.println("\n1. Update Profile\n2. Change Password\n3. Take Exam\n4. Logout");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> updateProfile();
                case 2 -> changePassword();
                case 3 -> takeExam();
                case 4 -> {
                    currentUser = null;
                    System.out.println("Logged out successfully.");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void updateProfile() {
        System.out.print("Enter new username: ");
        String newUsername = scanner.nextLine();
        users.remove(currentUser.getUsername());
        currentUser.setUsername(newUsername);
        users.put(newUsername, currentUser);
        System.out.println("Profile updated successfully.");
    }

    private static void changePassword() {
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();
        currentUser.setPassword(newPassword);
        System.out.println("Password changed successfully.");
    }

    private static void takeExam() {
        int score = 0;
        timeUp = false;

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timeUp = true;
                System.out.println("\nTime's up! Auto-submitting your answers.");
            }
        }, 60 * 1000); // 1 minute timer

        for (Question question : questions) {
            if (timeUp) {
                break;
            }

            System.out.println("\n" + question.getQuestion());
            List<String> options = question.getOptions();
            for (int i = 0; i < options.size(); i++) {
                System.out.println((i + 1) + ". " + options.get(i));
            }

            System.out.print("Choose your answer (1-" + options.size() + "): ");
            int answer = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (answer - 1 == question.getCorrectAnswerIndex()) {
                score++;
            }
        }

        timer.cancel();
        System.out.println("Exam completed. Your score is " + score + "/" + questions.size());
    }
}

class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

class Question {
    private final String question;
    private final List<String> options;
    private final int correctAnswerIndex;

    public Question(String question, List<String> options, int correctAnswerIndex) {
        this.question = question;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }
}
