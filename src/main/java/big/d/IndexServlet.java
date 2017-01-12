package big.d;

import contractinterface.ContractInterface;
import controller.MockController;
import dtos.ReservationSummaryDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.HttpServerGeneralUtils;

@WebServlet(name = "IndexServlet", urlPatterns = {"/index"})
public class IndexServlet extends HttpServlet {

    private ContractInterface backendMock;
    private HttpServerGeneralUtils utils;

    @Override
    public void init() throws ServletException {
        backendMock = MockController.getInstance();
        utils = new HttpServerGeneralUtils();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Response for Error
        String plausableError = "";
        boolean areReservationsLoaded = false;

        List<ReservationSummaryDTO> reservationDTOsList = null;

        //Only search for routes info if the location id is set
        reservationDTOsList = backendMock.getAllReservations();

        if (reservationDTOsList == null || reservationDTOsList.isEmpty()) {
            plausableError += "Reservations not found!";
        } else {
            areReservationsLoaded = true;
        }

        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try {

            request.setAttribute("intro", "Welcome to the Big D's Ferry Management System");
            request.setAttribute("createReservationPath", "/createReservation");
            request.setAttribute("getReservationPath", "/getReservation");

            if (plausableError.isEmpty()) {

                if (areReservationsLoaded) {
                    request.setAttribute("reservations", reservationDTOsList);
                }

            } else {
                request.setAttribute("errorObject", plausableError);
            }

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
