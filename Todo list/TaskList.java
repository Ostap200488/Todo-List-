import java.io.*;
import java.time.LocalDate;

public class TaskList {
    private Task head;

    public TaskList() { this.head = null; }

    public void addTask(String description, LocalDate dueDate, String priority) {
        Task newTask = new Task(description, dueDate, priority);
        if (head == null || priority.compareTo(head.getPriority()) < 0) {
            newTask.setNext(head);
            head = newTask;
        } else {
            Task temp = head;
            while (temp.getNext() != null && priority.compareTo(temp.getNext().getPriority()) >= 0) {
                temp = temp.getNext();
            }
            newTask.setNext(temp.getNext());
            temp.setNext(newTask);
        }
        saveTasks();
    }

    public boolean markTaskAsCompleted(String description) {
        Task temp = head;
        while (temp != null) {
            if (temp.getDescription().equalsIgnoreCase(description)) {
                temp.markAsCompleted();
                saveTasks();
                return true;
            }
            temp = temp.getNext();
        }
        return false;
    }

    public boolean removeTask(String description) {
        if (head == null) return false;

        if (head.getDescription().equalsIgnoreCase(description)) {
            head = head.getNext();
            saveTasks();
            return true;
        }

        Task temp = head;
        while (temp.getNext() != null) {
            if (temp.getNext().getDescription().equalsIgnoreCase(description)) {
                temp.setNext(temp.getNext().getNext());
                saveTasks();
                return true;
            }
            temp = temp.getNext();
        }
        return false;
    }

    public void displayTasks() {
        if (head == null) {
            System.out.println("No tasks available.");
            return;
        }
        System.out.println("Pending Tasks:");
        Task temp = head;
        while (temp != null) {
            if (!temp.isCompleted()) System.out.println(temp);
            temp = temp.getNext();
        }

        System.out.println("Completed Tasks:");
        temp = head;
        while (temp != null) {
            if (temp.isCompleted()) System.out.println(temp);
            temp = temp.getNext();
        }
    }

    private void saveTasks() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("tasks.dat"))) {
            out.writeObject(head);
        } catch (IOException e) {
            System.out.println("Error saving tasks.");
        }
    }

    public void loadTasks() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("tasks.dat"))) {
            head = (Task) in.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No saved tasks found.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading tasks.");
        }
    }
}