import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Counter extends HttpServlet {
    private int userHitCount; // Tracks total hits across all sessions

    @Override
    public void init() {
        // Initialize hit counter
        userHitCount = 0;
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set response content type
        response.setContentType("text/html");

        // Increment hit counter
        userHitCount++;

        // Generate HTML response
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>User Hit Count</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; background-color: #f9f9f9; color: #333; text-align: center; padding: 50px; }");
        out.println(".counter { margin-top: 50px; padding: 20px; background: #e0f7fa; border: 2px solid #00bcd4; display: inline-block; border-radius: 8px; }");
        out.println("h3 { color: #00796b; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Welcome to User Page</h1>");
        out.println("<div class='counter'>");
        out.println("<h3><b>Page Visits: " + userHitCount + "</b></h3>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward GET requests to doPost
        doPost(request, response);
    }

    @Override
    public void destroy() {
        // Do nothing
    }
}
