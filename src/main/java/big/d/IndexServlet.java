package big.d;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "IndexServlet", urlPatterns = {"/index"})
public class IndexServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try {

            request.setAttribute("intro", "Welcome to the Big D's Ferry Management System");
            request.setAttribute("createReservationPath", "/createReservation");
            request.setAttribute("getReservationPath", "/getReservation");

            request.getRequestDispatcher("index.jsp").forward(request, response);

        } catch (IOException | ServletException e) {

            out.println("<h2>" + e.getMessage() + "</h2>");
            out.print("<hr/");
            out.print("<pre>");
            e.printStackTrace(out);
            out.println("</pre>");
        }
    }
}
