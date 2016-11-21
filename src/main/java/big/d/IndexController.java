package big.d;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
        
        List<RouteDTO> routes = new ArrayList();
        if (locationId != null && !locationId.isEmpty()) {
            String routesString = gr.sendGet("http://localhost:8084/api/getRoutes/" + locationId);
            
            Type listRouteType = new TypeToken<List<RouteDTO>>() {
            }.getType();
            routes = new Gson().fromJson(routesString, listRouteType);
        } else {
            System.out.println("Location is not defined!!!");
        }
        
        for (int i = 0; i < routes.size(); i++) {
            System.out.println(i + " > " + routes.get(i));
        }
        
        PrintWriter out = response.getWriter();
        try {
            request.setAttribute("locations", locations);
            if (locationId != null && !locationId.isEmpty()) {
                request.setAttribute("routes", routes);
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
