import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Calculator")
public class Calculator extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        double num1 = Double.parseDouble(request.getParameter("num1"));
        double num2 = Double.parseDouble(request.getParameter("num2"));
        String operation = request.getParameter("operation");

        double result = 0;
        String operationName = "";
        
        switch (operation) {
            case "add":
                result = num1 + num2;
                operationName = "Addition";
                break;
            case "sub":
                result = num1 - num2;
                operationName = "Subtraction";
                break;
            case "mul":
                result = num1 * num2;
                operationName = "Multiplication";
                break;
            case "div":
                if (num2 != 0) {
                    result = num1 / num2;
                } else {
                    operationName = "Division by zero is undefined.";
                }
                break;
            default:
                operationName = "Invalid Operation";
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        if (!operationName.equals("Division by zero is undefined.") && !operationName.equals("Invalid Operation")) {
            out.println("<h2>" + operationName + " Result: " + result + "</h2>");
        } else {
            out.println("<h2>" + operationName + "</h2>");
        }
        out.println("</body></html>");
    }
}
