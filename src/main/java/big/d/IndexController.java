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
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.*;
import utils.RequestsObject;

@WebServlet(name = "IndexController", urlPatterns = {"/reservation"})
public class IndexController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Thread t = new Thread(new BackendMockHttpServer());
        t.start();

        RequestsObject reqObj = new RequestsObject();
        String locationString = reqObj.get("http://localhost:8084/api/getLocations");

        Type listType = new TypeToken<List<LocationDTO>>() {
        }.getType();
        List<LocationDTO> locations = new Gson().fromJson(locationString, listType);

        String locationId = request.getParameter("locationId");
        String routeId = request.getParameter("routeId");
        String vehicleType = request.getParameter("vehicleType");
        String numOfPeople = request.getParameter("numOfPeople");
        String journeyId = request.getParameter("journeyId");

        List<RouteDTO> routes = new ArrayList();
        JourneysDTO journeys = null;
        ReservationSummaryDTO reservationSummary = null;

        if (locationId != null && !locationId.isEmpty()) {
            String routesString = reqObj.get("http://localhost:8084/api/getRoutes/" + locationId);

            Type listRouteType = new TypeToken<List<RouteDTO>>() {
            }.getType();
            routes = new Gson().fromJson(routesString, listRouteType);

            //only search for journeys if searched for routes
            if (routeId != null && !routeId.isEmpty()) {
                String journeysString = reqObj.get("http://localhost:8084/api/getJourney/" + routeId);
                journeys = new Gson().fromJson(journeysString, JourneysDTO.class);
            }

            //only try to post if searched for routes and journeys before
            if (vehicleType != null && !vehicleType.isEmpty()
                    && numOfPeople != null && !numOfPeople.isEmpty()
                    && journeyId != null && !journeyId.isEmpty()) {
                //"value=1&anotherValue=1"

                //Should be like : { "journeyId": 5, "numberOfPeople": 3, "vehicleType": "Car" }
                String params = "journeyId=" + journeyId + "&numberOfPeople=" + numOfPeople + "&vehicleType=" + vehicleType;
                String reservationSummaryString = reqObj.post("http://localhost:8084/api/makeReservation", params);
                reservationSummary = new Gson().fromJson(reservationSummaryString, ReservationSummaryDTO.class);
            }
        }

        PrintWriter out = response.getWriter();
        try {
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

            request.setCharacterEncoding("UTF-8");
            request.getRequestDispatcher("reservation.jsp").forward(request, response);
        } catch (Exception e) {
            out.println("<h2>" + e.getMessage() + "</h2>");
            out.print("<hr/><pre>");
            e.printStackTrace(out);
            out.println("</pre>");
        }
    }
}
