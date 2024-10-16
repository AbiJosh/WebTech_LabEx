import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.List;

public class CookiesSessionServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve username and password from the request
        String username = request.getParameter("username");
        String password = request.getParameter("password"); // Assuming you are checking this

        // Validate username and password (basic example)
        if (username != null && !username.isEmpty() && password != null && password.length() >= 4) {
            // Create a cookie for the username
            Cookie cookie = new Cookie("username", username);
            cookie.setMaxAge(60 * 60); // Cookie will last for 1 hour
            response.addCookie(cookie);

            // Redirect to display tasks directly
            displayTasks(username, response);
        } else {
            // Handle invalid login case
            response.sendRedirect("form.html?error=invalid");
        }
    }

    private void displayTasks(String username, HttpServletResponse response) throws IOException {
        // Retrieve task filter status (optional, you can set a default)
        String status = "all"; // Change this if you want to filter tasks based on some criteria

        // Get filtered tasks from TaskManager
        List<TaskManager.Task> tasks = TaskManager.getTasks(status);

        // Display filtered tasks along with the username
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Your Tasks</title>");
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
        out.println("<h2>Welcome, " + username + "!</h2>"); 
        out.println("<h2>Your Tasks:</h2>"); 
        out.println("<ul>");

        for (TaskManager.Task task : tasks) {
            String taskClass = task.isCompleted() ? "completed" : "pending";
            out.println("<li class='" + taskClass + "'>" + task.getTitle() + "</li>");
        }

        out.println("</ul>");
        out.println("</div>");
        out.println("</body></html>");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // This method can be removed if not needed, as we're handling everything in doPost
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }
}
