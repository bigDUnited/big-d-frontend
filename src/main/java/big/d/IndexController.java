package big.d;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dtos.JourneysDTO;
import dtos.LocationDTO;
import dtos.ReservationSummaryDTO;
import dtos.RouteDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.*;
import utilities.HttpServerGeneralUtils;
import utils.RequestsObject;
import utils.StaticStrings;

@WebServlet(name = "IndexController", urlPatterns = {"/reservation"})
public class IndexController extends HttpServlet {

    private RequestsObject reqObj;
    private StaticStrings ss;
    private Thread backendMockThread;
    private HttpServerGeneralUtils utils;

    public void init() throws ServletException {
        backendMockThread = new Thread(new BackendMockHttpServer(new String[0]));
        backendMockThread.start();

        int loadingTime = 1000;
        try {
            Thread.sleep(loadingTime);
        } catch (InterruptedException ex) {
            System.out.println("Sleep failed, go to sleep, please : " + ex);
        }

        reqObj = new RequestsObject();
        ss = new StaticStrings();
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

        //RO stands for response object eg. Locations Response Object
        Object locationsRO = reqObj.get(ss.GET_LOCATIONS_URL);
        if (locationsRO instanceof String) {

            Type listType = new TypeToken<List<LocationDTO>>() {
            }.getType();
            locations = new Gson().fromJson((String) locationsRO, listType);

            //Only search for routes info if the location id is set
            if (locationId != null && !locationId.isEmpty()) {
                if (utils.isNumeric(locationId)) {
                    Object routesRO = reqObj.get(ss.GET_ROUTES_URL + locationId);
                    if (routesRO instanceof String) {

                        Type listRouteType = new TypeToken<List<RouteDTO>>() {
                        }.getType();
                        routes = new Gson().fromJson((String) routesRO,
                                listRouteType);

                        //Only search for journeys info if the routeId id is set
                        if (routeId != null && !routeId.isEmpty()) {
                            if (utils.isNumeric(routeId)) {
                                Object journeysRO = reqObj.get(ss.GET_JOURNEY_URL + routeId);

                                if (journeysRO instanceof String) {
                                    journeys = new Gson().fromJson((String) journeysRO,
                                            JourneysDTO.class);

                                    //Only post reservation if  vehicleType, numOfPeople 
                                    //and journeyId are set
                                    if (vehicleType != null && !vehicleType.isEmpty()
                                            && numOfPeople != null && !numOfPeople.isEmpty()
                                            && journeyId != null && !journeyId.isEmpty()) {

                                        if (utils.isNumeric(numOfPeople) && utils.isNumeric(journeyId)) {
                                            
                                            //postParams should have similar structure to : 
                                            //{ "journeyId": 5, "numberOfPeople": 3, "vehicleType": "Car" }
                                            String postParams = "{ \"journeyId\": " + journeyId
                                                    + ", \"numberOfPeople\": " + numOfPeople
                                                    + ", \"vehicleType\": \"" + vehicleType + "\" }";

                                            Object resSumRO = reqObj.post(ss.POST_RESERVATION_URL,
                                                    postParams);

                                            if (resSumRO instanceof String) {
                                                reservationSummary = new Gson().fromJson(
                                                        (String) resSumRO,
                                                        ReservationSummaryDTO.class);
                                            } else {
                                                plausableError += "***Oopsy Daisy*** The "
                                                        + "Reservation summary response"
                                                        + " is :" + ((Boolean) resSumRO);
                                            }
                                        } else {
                                            plausableError += "***Oopsy Daisy*** vehicleType "
                                                    + "or numOfPeople or journeyId was not set"
                                                    + "correctly";
                                        }
                                    }
                                } else {
                                    plausableError += "***Oopsy Daisy*** The Journey response"
                                            + " is :" + ((Boolean) journeysRO);
                                }
                            } else {
                                plausableError += "***Oopsy Daisy*** Route ID was not set "
                                        + "or was not an integer :" + routeId;
                            }
                        }
                    } else {
                        plausableError += "***Oopsy Daisy*** The Routes response is :"
                                + ((Boolean) routesRO);
                    }
                } else {
                    plausableError += "***Oopsy Daisy*** Location ID was not set or "
                            + "was not an integer :" + locationId;
                }
            }
        } else {
            plausableError += "***Oopsy Daisy*** The Location response is :"
                    + ((Boolean) locationsRO);
        }

        //set encoding of response - MUST BE CALLED BEFORE response.getWriter()
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        try {
            if (plausableError.isEmpty()) {
                request.setAttribute("locations", locations);
                if (locationId != null && !locationId.isEmpty()) {
                    request.setAttribute("routes", routes);

                    if (routeId != null && !routeId.isEmpty()) {
                        request.setAttribute("journeys", journeys);

                        if (vehicleType != null && !vehicleType.isEmpty()
                                && numOfPeople != null && !numOfPeople.isEmpty()
                                && journeyId != null && !journeyId.isEmpty()) {

                            request.setAttribute("summary", reservationSummary);
                        }
                    }
                }
            } else {
                request.setAttribute("errorObject", plausableError);
            }

            request.getRequestDispatcher("reservation.jsp").forward(request, response);
        } catch (Exception e) {
            out.println("<h2>" + e.getMessage() + "</h2>");
            out.print("<hr/");
            out.print("<pre>");
            e.printStackTrace(out);
            out.println("</pre>");
        }

    }

}
