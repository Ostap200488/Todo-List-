import java.io.Serializable;
import java.time.LocalDate;

public class Task implements Serializable {
    private String description;
    private boolean isCompleted;
    private Task next; // Pointer to the next task (Linked List)
    private LocalDate dueDate;
    private String priority; // LOW, MEDIUM, HIGH

    public Task(String description, LocalDate dueDate, String priority) {
        this.description = description;
        this.isCompleted = false;
        this.next = null;
        this.dueDate = dueDate;
        this.priority = priority;
    }

    public String getDescription() { return description; }
    public boolean isCompleted() { return isCompleted; }
    public void markAsCompleted() { this.isCompleted = true; }
    public Task getNext() { return next; }
    public void setNext(Task next) { this.next = next; }
    public LocalDate getDueDate() { return dueDate; }
    public String getPriority() { return priority; }

    @Override
    public String toString() {
        return (isCompleted ? "[✔] " : "[ ] ") + description + 
               " | Due: " + dueDate + 
               " | Priority: " + priority;
    }
}