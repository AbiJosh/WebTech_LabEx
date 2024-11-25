import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class TodoServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        Connection conn = null;
        Statement stmt = null;

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection to the database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/todo_list", "root", "");

            // Create SQL statement
            stmt = conn.createStatement();
            String sql = "SELECT * FROM tasks"; // Query to get all tasks
            ResultSet rs = stmt.executeQuery(sql);

            // HTML structure and styles
            out.println("<!DOCTYPE html>");
            out.println("<html><head><title>Your To-Do List</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; background-color: #f3f4f6; padding: 20px; margin: 0; }");
            out.println("h1 { text-align: center; color: #333; }");
            out.println("table { width: 80%; margin: auto; border-collapse: collapse; background: #fff; box-shadow: 0 0 10px rgba(0,0,0,0.1); }");
            out.println("th, td { padding: 10px; text-align: left; border: 1px solid #ddd; }");
            out.println("th { background-color: #4caf50; color: white; }");
            out.println("tr:nth-child(even) { background-color: #f2f2f2; }");
            out.println("tr:hover { background-color: #ddd; }");
            out.println("a { display: block; text-align: center; margin: 20px auto; width: fit-content; padding: 10px 20px; text-decoration: none; color: white; background-color: #007BFF; border-radius: 5px; }");
            out.println("a:hover { background-color: #0056b3; }");
            out.println("</style></head><body>");
            out.println("<h1>Your To-Do List</h1>");

            // Display tasks in an HTML table
            out.println("<table>");
            out.println("<tr><th>Description</th><th>Due Date</th></tr>");

            while (rs.next()) {
                // Retrieve values
                String description = rs.getString("description");
                String dueDate = rs.getString("due_date");

                // Display each task as a table row
                out.println("<tr>");
                out.println("<td>" + description + "</td>");
                out.println("<td>" + dueDate + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("<a href='add_task.html'>Back to Add Task</a>");
            out.println("</body></html>");

            // Close the result set
            rs.close();
        } catch (Exception e) {
            out.println("<p style='color:red;text-align:center;'>Error: " + e.getMessage() + "</p>");
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection to the database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/todo_list", "root", "");

            // Get input values from the request
            String description = request.getParameter("description");
            String dueDate = request.getParameter("due_date");

            // Insert the task into the database
            String sql = "INSERT INTO tasks (description, due_date) VALUES (?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, description);
            pstmt.setString(2, dueDate);
            pstmt.executeUpdate();

            // HTML structure and styles
            out.println("<!DOCTYPE html>");
            out.println("<html><head><title>Task Added</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; background-color: #e8f5e9; text-align: center; padding: 20px; margin: 0; }");
            out.println("h1 { color: #388e3c; }");
            out.println("a { display: inline-block; margin-top: 20px; padding: 10px 20px; text-decoration: none; color: white; background-color: #007BFF; border-radius: 5px; }");
            out.println("a:hover { background-color: #0056b3; }");
            out.println("</style></head><body>");
            out.println("<h1>Task Added Successfully</h1>");
            out.println("<a href='TodoServlet'>Back to Task List</a>");
            out.println("</body></html>");
        } catch (Exception e) {
            out.println("<p style='color:red;text-align:center;'>Error: " + e.getMessage() + "</p>");
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
