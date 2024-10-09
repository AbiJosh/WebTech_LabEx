import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    public static class Task {
        private String title;
        private boolean completed;

        public Task(String title, boolean completed) {
            this.title = title;
            this.completed = completed;
        }

        public String getTitle() {
            return title;
        }

        public boolean isCompleted() {
            return completed;
        }
    }

    private static List<Task> tasks = new ArrayList<>();

    static {
        tasks.add(new Task("Buy groceries", false));
        tasks.add(new Task("Complete project report", true));
        tasks.add(new Task("Clean the house", false));
        tasks.add(new Task("Book doctor appointment", true));
        tasks.add(new Task("Finish reading a book", false));
    }

    public static List<Task> getTasks(String status) {
        List<Task> filteredTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (status.equals("all") ||
                (status.equals("completed") && task.isCompleted()) ||
                (status.equals("pending") && !task.isCompleted())) {
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }

    public static String getHtmlTasks(String status) {
        List<Task> filteredTasks = getTasks(status);
        StringBuilder html = new StringBuilder();

        html.append("<ul>");
        for (Task task : filteredTasks) {
            String taskClass = task.isCompleted() ? "completed" : "pending";
            html.append("<li class='" + taskClass + "'>" + task.getTitle() + "</li>");
        }
        html.append("</ul>");

        return html.toString();
    }
}
