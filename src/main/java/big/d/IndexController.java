package big.d;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dtos.JourneysDTO;
import dtos.LocationDTO;
import dtos.RouteDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.inject.Model;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.*;
import utils.GetRequest;

@WebServlet(name = "IndexController", urlPatterns = {"/reservation"})
public class IndexController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Thread t = new Thread(new BackendMockHttpServer());
        t.start();

        GetRequest gr = new GetRequest();
        String locationString = gr.sendGet("http://localhost:8084/api/getLocations");

        Type listType = new TypeToken<List<LocationDTO>>() {
        }.getType();
        List<LocationDTO> locations = new Gson().fromJson(locationString, listType);

        String locationId = request.getParameter("locationId");
        String routeId = request.getParameter("routeId");

        List<RouteDTO> routes = new ArrayList();
        JourneysDTO journeys = null;
        
        if (locationId != null && !locationId.isEmpty()) {
            String routesString = gr.sendGet("http://localhost:8084/api/getRoutes/" + locationId);

            Type listRouteType = new TypeToken<List<RouteDTO>>() {
            }.getType();
            routes = new Gson().fromJson(routesString, listRouteType);
            
            //only search for journeys if searched for routes
            if (routeId != null && !routeId.isEmpty()) {

                String journeysString = gr.sendGet("http://localhost:8084/api/getJourney/" + routeId);
                System.out.println("journeysString> " + journeysString);
                
                journeys = new Gson().fromJson(journeysString, JourneysDTO.class);
            }
        }

        PrintWriter out = response.getWriter();
        try {
            request.setAttribute("locations", locations);
            if (locationId != null && !locationId.isEmpty()) {
                request.setAttribute("routes", routes);

                if (routeId != null && !routeId.isEmpty()) {
                    request.setAttribute("journeys", journeys);
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
