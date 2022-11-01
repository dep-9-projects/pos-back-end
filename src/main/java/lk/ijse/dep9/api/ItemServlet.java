package lk.ijse.dep9.api;

import jakarta.annotation.Resource;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.ijse.dep9.api.util.HttpServlet2;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "ItemServlet", value = "/items/*", loadOnStartup = 0)
public class ItemServlet extends HttpServlet2 {

    @Resource(lookup = "java:/comp/env/jdbc/pos-db")
    private DataSource pool ;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getPathInfo() ==null || request.getPathInfo().equals("/")){
            String query = request.getParameter("q");
            String size = request.getParameter("size");
            String page = request.getParameter("page");

            if (query != null && size != null && page != null){
                if (!size.matches("\\d+") || !page.matches("\\d+")){
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST,"invalid page or size");
                }else {
                    /* Search paginated items */
                }
            }else {
                /* load all Items */
            }
        }else {
            Matcher matcher = Pattern.compile("/([A-Fa-f0-9]{8}(-[A-Fa-f0-9]{4}){3}-[A-Fa-f0-9]{12})?/").matcher(request.getPathInfo());
            if (matcher.matches()){
                /* get Item Details Method */
            }else {
                response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED,"Invalid URL");
            }
        }
    }


    @Override
    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().println("<h1>items-doPatch()</h1>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("<h1>items-doPost()</h1>");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("<h1>items-doDelete()</h1>");
    }
}
