package dk.cphbusiness.choir.frontend;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

@WebServlet(name = "ChoirManagerController", urlPatterns = {"/ChoirManager"})
public class ChoirManagerController extends HttpServlet {
  
//  private ChoirManager manager = new ChoirManagerDummy();

//  @Override
//  protected void service(
//      HttpServletRequest request,
//      HttpServletResponse response
//      ) throws ServletException, IOException {
//    PrintWriter out = response.getWriter();
//    try {
////      Collection<MemberSummary> members = manager.listMembers();
//      request.setAttribute("members", members);
//      request.getRequestDispatcher("member-list.jsp").forward(request, response);
//      }
//    catch (Exception e) {
//      out.println("<h2>"+e.getMessage()+"</h2>");
//      out.print("<hr/><pre>");
//      e.printStackTrace(out);
//      out.println("</pre>");
//      }
//    }
  
  }
