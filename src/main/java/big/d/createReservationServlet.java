package big.d;

import controller.MockController;
import dtos.JourneysDTO;
import dtos.LocationDTO;
import dtos.ReservationSummaryDTO;
import dtos.RouteDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.HttpServerGeneralUtils;

@WebServlet(name = "createReservationServlet", urlPatterns = {"/createReservation"})
public class createReservationServlet extends HttpServlet{
    
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
        String locationId = request.getParameter("locationId");
        //Request for Stage 2
        String routeId = request.getParameter("routeId");
        //Request for Stage 3
        String vehicleType = request.getParameter("vehicleType");
        String numOfPeople = request.getParameter("numOfPeople");
        String journeyId = request.getParameter("journeyId");

        //DTO which should contain the displayable information
        //Response for Stage 0 (on load)
        List<LocationDTO> locations = null;
        //Response for Stage 1
        List<RouteDTO> routes = null;
        //Response for Stage 2
        JourneysDTO journeys = null;
        //Response for Stage 3
        ReservationSummaryDTO reservationSummary = null;

        //Response for Error
        String plausableError = "";

        boolean areLocationsLoaded = false, areRoutesLoaded = false,
                areJourneysLoaded = false, isSummaryLoaded = false;

        locations = backendMock.getLocations();
        areLocationsLoaded = true;

        if (areLocationsLoaded) {
            //Only search for routes info if the location id is set
            if (locationId != null && !locationId.isEmpty()) {
                if (utils.isNumeric(locationId)) {

                    int actualLocationId = Integer.parseInt(locationId);
                    routes = backendMock.getRoutes(actualLocationId);
                    areRoutesLoaded = true;

                } else {
                    plausableError += "***Oopsy Daisy*** Location ID was not set "
                            + "or was not an integer :" + locationId;
                }
            }
        }

        if (areLocationsLoaded && areRoutesLoaded) {
            //Only search for journeys info if the routeId id is set
            if (routeId != null && !routeId.isEmpty()) {
                if (utils.isNumeric(routeId)) {

                    int actualRouteId = Integer.parseInt(routeId);
                    journeys = backendMock.getJourney(actualRouteId);
                    areJourneysLoaded = true;
                } else {
                    plausableError += "***Oopsy Daisy*** Route ID was not set or "
                            + "was not an integer :" + routeId;
                }
            }
        }

        if (areLocationsLoaded && areRoutesLoaded && areJourneysLoaded) {
            //Only post reservation if  vehicleType, numOfPeople and journeyId are set
            if (vehicleType != null && !vehicleType.isEmpty()
                    && numOfPeople != null && !numOfPeople.isEmpty()
                    && journeyId != null && !journeyId.isEmpty()) {

                if (utils.isNumeric(numOfPeople) && utils.isNumeric(journeyId)) {

                    int actualJourneyId = Integer.parseInt(journeyId);
                    int actualNumOfPpl = Integer.parseInt(numOfPeople);
                    reservationSummary = backendMock.makeReservation(actualJourneyId,
                            actualNumOfPpl, vehicleType);
                    isSummaryLoaded = true;

                } else {
                    plausableError += "***Oopsy Daisy*** vehicleType or numOfPeople "
                            + "or journeyId was not set correctly";
                }
            }
        }

        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try {
            if (plausableError.isEmpty()) {
                request.setAttribute("locations", locations);

                if (areLocationsLoaded && areRoutesLoaded) {
                    request.setAttribute("routes", routes);
                }

                if (areLocationsLoaded && areRoutesLoaded && areJourneysLoaded) {
                    request.setAttribute("journeys", journeys);
                }

                if (areLocationsLoaded && areRoutesLoaded && areJourneysLoaded
                        && isSummaryLoaded) {
                    request.setAttribute("summary", reservationSummary);
                }

            } else {
                request.setAttribute("errorObject", plausableError);
            }

            request.getRequestDispatcher("reservation.jsp").forward(request, response);

        } catch (IOException | ServletException e) {

            out.println("<h2>" + e.getMessage() + "</h2>");
            out.print("<hr/");
            out.print("<pre>");
            e.printStackTrace(out);
            out.println("</pre>");
        }
    }
}
