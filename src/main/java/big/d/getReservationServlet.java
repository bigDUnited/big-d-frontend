package big.d;

import controller.MockController;
import dtos.ReservationSummaryDTO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.HttpServerGeneralUtils;

@WebServlet(name = "getReservationServlet", urlPatterns = {"/getReservation"})
public class getReservationServlet extends HttpServlet {

    private MockController backendMock;
    private HttpServerGeneralUtils utils;

    @Override
    public void init() throws ServletException {
        backendMock = MockController.getInstance();
        utils = new HttpServerGeneralUtils();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Parameters which should be sent from the frontend
        //Request for Stage 1
        String reservationId = request.getParameter("reservationId");

        //Response for Error
        String plausableError = "";

        boolean isReservationLoaded = false;

        ReservationSummaryDTO reservationDTO = null;

        //Only search for routes info if the location id is set
        if (reservationId != null && !reservationId.isEmpty()) {
            if (utils.isNumeric(reservationId)) {

                int actualReservationId = Integer.parseInt(reservationId);
                reservationDTO = backendMock.getReservation(actualReservationId);
                
                if(reservationDTO == null ) {
                    plausableError += "***Oopsy Daisy*** Reservation not found, "
                            + "reservation ID :" + reservationId;
                } else {
                    isReservationLoaded = true;
                }

            } else {
                plausableError += "***Oopsy Daisy*** Reservation ID was not set "
                        + "or was not an integer :" + reservationId;
            }
        }

        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        try {

            if (plausableError.isEmpty()) {

                if (isReservationLoaded) {
                    request.setAttribute("reservation", reservationDTO);
                }

            } else {
                request.setAttribute("errorObject", plausableError);
            }

            request.getRequestDispatcher("getReservation.jsp").forward(request, response);

        } catch (IOException | ServletException e) {

            out.println("<h2>" + e.getMessage() + "</h2>");
            out.print("<hr/");
            out.print("<pre>");
            e.printStackTrace(out);
            out.println("</pre>");
        }
    }
}
