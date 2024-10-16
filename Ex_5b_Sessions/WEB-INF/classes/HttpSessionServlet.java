import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.List;

public class HttpSessionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Store username in session
        String username = request.getParameter("username");
        HttpSession session = request.getSession();
        session.setAttribute("username", username);
        
        // Retrieve task filter status
        String status = request.getParameter("status");
        
        // Get filtered tasks from TaskManager
        List<TaskManager.Task> tasks = TaskManager.getTasks(status);
        
        // Display filtered tasks along with the username
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
        out.println("<h2>Welcome, " + username + "!</h2>"); 
        out.println("<h2>Filtered Tasks:</h2>"); 
        out.println("<ul>");

        for (TaskManager.Task task : tasks) {
            String taskClass = task.isCompleted() ? "completed" : "pending";
            out.println("<li class='" + taskClass + "'>" + task.getTitle() + "</li>");
        }

        out.println("</ul>");
        out.println("</div>");
        out.println("</body></html>");
    }
}
