import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    private static User[] users = new User[10]; // Array of users (max 10)
    private static int userCount = 0;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== To-Do List Manager =====");
            System.out.println("1. Add User");
            System.out.println("2. Select User");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = getValidIntInput();
            
            switch (choice) {
                case 1:
                    addUser();
                    break;
                case 2:
                    selectUser();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void addUser() {
        if (userCount >= users.length) {
            System.out.println("User limit reached!");
            return;
        }
        System.out.print("Enter user name: ");
        String name = scanner.nextLine().trim();
        for (int i = 0; i < userCount; i++) {
            if (users[i].getName().equalsIgnoreCase(name)) {
                System.out.println("User already exists!");
                return;
            }
        }
        users[userCount++] = new User(name);
        System.out.println("User added successfully.");
    }

    private static void selectUser() {
        if (userCount == 0) {
            System.out.println("No users available. Add a user first.");
            return;
        }

        System.out.println("\nSelect a user:");
        for (int i = 0; i < userCount; i++) {
            System.out.println((i + 1) + ". " + users[i].getName());
        }
        System.out.print("Enter user number: ");

        int userIndex = getValidIntInput() - 1;
        if (userIndex < 0 || userIndex >= userCount) {
            System.out.println("Invalid selection.");
            return;
        }

        User selectedUser = users[userIndex];
        userMenu(selectedUser);
    }

    private static void userMenu(User user) {
        while (true) {
            System.out.println("\n===== To-Do List for " + user.getName() + " =====");
            System.out.println("1. Add Task");
            System.out.println("2. Mark Task as Completed");
            System.out.println("3. Remove Task");
            System.out.println("4. View Tasks");
            System.out.println("5. Go Back");
            System.out.print("Enter your choice: ");

            int choice = getValidIntInput();

            switch (choice) {
                case 1:
                    addTask(user);
                    break;
                case 2:
                    markTaskAsCompleted(user);
                    break;
                case 3:
                    removeTask(user);
                    break;
                case 4:
                    viewTasks(user);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void addTask(User user) {
        System.out.print("Enter task description: ");
        String description = scanner.nextLine().trim();

        System.out.print("Enter due date (YYYY-MM-DD): ");
        LocalDate dueDate;
        while (true) {
            try {
                dueDate = LocalDate.parse(scanner.nextLine().trim(), DateTimeFormatter.ISO_LOCAL_DATE);
                break;
            } catch (DateTimeParseException e) {
                System.out.print("Invalid date format. Please enter again (YYYY-MM-DD): ");
            }
        }

        System.out.print("Enter task priority (High, Medium, Low): ");
        String priority = scanner.nextLine().trim();

        user.getTaskList().addTask(description, dueDate, priority);
        System.out.println("Task added successfully.");
    }

    private static void markTaskAsCompleted(User user) {
        System.out.print("Enter task description to mark as completed: ");
        String description = scanner.nextLine().trim();
        boolean success = user.getTaskList().markTaskAsCompleted(description);
        System.out.println(success ? "Task marked as completed." : "Task not found.");
    }

    private static void removeTask(User user) {
        System.out.print("Enter task description to remove: ");
        String description = scanner.nextLine().trim();
        boolean success = user.getTaskList().removeTask(description);
        System.out.println(success ? "Task removed successfully." : "Task not found.");
    }

    private static void viewTasks(User user) {
        user.getTaskList().displayTasks();
    }

    private static int getValidIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
}
