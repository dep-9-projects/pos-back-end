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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

@WebServlet(name = "CustomerServlet", value = "/customers/*",loadOnStartup = 0)
public class CustomerServlet extends HttpServlet2 {
    @Resource(lookup = "java:/comp/env/jdbc/pos-db")
    private DataSource pool ;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.getWriter().println("<h1>doGet()</h1>");
        ArrayList<CustomerDTO> customers = new ArrayList<>();

        try {
            Connection connection = pool.getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM customers");
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
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }

    private void searchPaginatedCustomers(String query, int size, int page, HttpServletResponse response){
        try(Connection connection = pool.getConnection()) {
            String sql = "SELECT COUNT(id) AS count FROM member WHERE id LIKE ? OR name LIKE ? OR address LIKE ? ";



        } catch (SQLException e) {
            throw new RuntimeException(e);
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
        response.getWriter().println("<h1>customers-doDelete()</h1>");
    }
}
