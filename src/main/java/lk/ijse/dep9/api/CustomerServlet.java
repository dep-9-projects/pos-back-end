package lk.ijse.dep9.api;

import jakarta.annotation.Resource;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.ijse.dep9.api.util.HttpServlet2;
import lk.ijse.dep9.dto.CustomerDTO;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "CustomerServlet", value = "/customers/*",loadOnStartup = 0)
public class CustomerServlet extends HttpServlet2 {
    @Resource(lookup = "java:/comp/env/jdbc/pos-db")
    private DataSource pool ;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.getWriter().println("<h1>doGet()</h1>");
        if(request.getPathInfo()==null || request.getPathInfo().equals("/")){
            String query = request.getParameter("q");
            String size = request.getParameter("size");
            String page = request.getParameter("page");

            if(query !=null && size !=null && page !=null){
                if (!size.matches("\\d+") || !page.matches("\\d+")) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Invalid Page or Size");
                }else {
                    //searchPaginatedCustomers(query,Integer.parseInt(size),Integer.parseInt(page),response);


                }

            } else if (query !=null) {
                //searchAllCustomers(query,response);

            } else if (size !=null & page !=null) {
                if (!size.matches("\\d+") || !page.matches("\\d+")) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Invalid Page or Size");
                }else {
                    //loadPaginatedCustomers(Integer.parseInt(size),Integer.parseInt(page),response);


                }

            }else {
                //loadAllCustomers(response);

            }

        }else {
            Pattern pattern = Pattern.compile("^/([A-Fa-f0-9]{8}(-[A-Fa-f0-9]{4}){3}-[A-Fa-f0-9]{12})/?$");
            Matcher matcher = pattern.matcher(request.getPathInfo());

            if (matcher.matches()){
                getCustomerDetails(matcher.group(1),response);

            }else {
                response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED,"Expected valid UUID");

            }


        }

    }

    private void loadAllCustomers(HttpServletResponse response) throws IOException {

        try(Connection connection = pool.getConnection()) {
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM customers");
            ArrayList<CustomerDTO> customers = new ArrayList<>();
            while (rst.next()){
                String id = rst.getString("id");
                String name = rst.getString("name");
                String address = rst.getString("address");

                CustomerDTO customer = new CustomerDTO(id, name, address);
                customers.add(customer);

            }
            Jsonb jsonb = JsonbBuilder.create();
            response.setContentType("application/json");
            jsonb.toJson(customers,response.getWriter());
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Failed to load customers");
        }

    }

    private void loadPaginatedCustomers(int size, int page, HttpServletResponse response) {

    }

    private void searchAllCustomers(String query, HttpServletResponse response) {

    }

    private void searchPaginatedCustomers(String query, int size, int page, HttpServletResponse response) {

    }

    private void getCustomerDetails(String customerId,HttpServletResponse response) throws IOException {
        try (Connection connection = pool.getConnection()) {
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM customers WHERE id=?");
            stm.setString(1,customerId);
            ResultSet rst = stm.executeQuery();

            if (rst.next()){
                String id = rst.getString("id");
                String name = rst.getString("name");
                String address = rst.getString("address");

                response.setContentType("application/json");
                CustomerDTO customer = new CustomerDTO(id, name, address);
                JsonbBuilder.create().toJson(customer,response.getWriter());
            }else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException|IOException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Fail to fetch customer details");
        }
    }




    @Override
    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().println("<h1>customers-doPatch()</h1>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("<h1>customers-doPost()</h1>");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getPathInfo()==null || request.getPathInfo().equals("/")){
            response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);

        }else {
            Pattern pattern = Pattern.compile("^/([A-Fa-f0-9]{8}(-[A-Fa-f0-9]{4}){3}-[A-Fa-f0-9]{12})/?$");
            Matcher matcher = pattern.matcher(request.getPathInfo());

            if(matcher.matches()){
                // Todo delete the member
                deleteCustomer(matcher.group(1),response);


            }else {
                response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED,"Expected valid UUID");
            }

        }

    }
    protected void deleteCustomer(String customerId,HttpServletResponse response){
        try( Connection connection = pool.getConnection()) {
            PreparedStatement stm = connection.prepareStatement("DELETE  FROM customers WHERE id=?");
            stm.setString(1,customerId);
            int affectedRows = stm.executeUpdate();
            if(affectedRows==0){
                response.sendError(HttpServletResponse.SC_NOT_FOUND,"Invalid customer id");

            }else {
                response.sendError(HttpServletResponse.SC_NO_CONTENT);


            }


        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

    }
}
