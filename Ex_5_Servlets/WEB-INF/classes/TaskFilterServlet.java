import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TaskFilterServlet extends HttpServlet {
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String status = request.getParameter("status");
        List<Task> tasks = getTasks(status);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Filtered Tasks</title>");
        out.println("<style>");
        out.println("body {font-family: Arial, sans-serif; background-color: #f4f4f4; color: #333;}");
        out.println(".container {width: 80%; margin: 50px auto; background: white; padding: 20px; border-radius: 10px; box-shadow: 0px 4px 12px rgba(0,0,0,0.1);}");
        out.println("h2 {color: #2c3e50; text-align: center;}");
        out.println("ul {list-style-type: none; padding: 0;}");
        out.println("li {padding: 10px; margin: 10px 0; background-color: #ecf0f1; border-left: 5px solid;}");
        out.println("li.completed {border-color: #27ae60; text-decoration: line-through;}");
        out.println("li.pending {border-color: #e74c3c;}");
        out.println("</style>");
        out.println("</head><body>");
        out.println("<div class='container'>");
        out.println("<h2>Filtered Tasks:</h2>");
        out.println("<ul>");

        for (Task task : tasks) {
            String taskClass = task.isCompleted() ? "completed" : "pending";
            out.println("<li class='" + taskClass + "'>" + task.getTitle() + "</li>");
        }

        out.println("</ul>");
        out.println("</div>");
        out.println("</body></html>");
    }
}
