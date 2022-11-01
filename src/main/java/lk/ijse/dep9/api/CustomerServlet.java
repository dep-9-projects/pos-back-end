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

@WebServlet(name = "CustomerServlet", value = "/customers/*",loadOnStartup = 0)
public class CustomerServlet extends HttpServlet2 {
    @Resource(lookup = "java:/comp/env/jdbc/pos-db")
    private DataSource pool ;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {





    }

    private void searchPaginatedCustomers(String query, int size, int page, HttpServletResponse response){
        try(Connection connection = pool.getConnection()) {
            String sql = "SELECT COUNT(id) AS count FROM customers WHERE id LIKE ? OR name LIKE ? OR address LIKE ? ";
            PreparedStatement stm1 = connection.prepareStatement(sql);
            ResultSet rst = stm1.executeQuery();
            rst.next();
            response.setIntHeader("X-Total-Count",rst.getInt("count"));
            PreparedStatement stm2 = connection.prepareStatement("SELECT * FROM customers WHERE id LIKE ? OR name LIKE ? OR address LIKE ?  LIMIT ? OFFSET ?");

            query="%" + query +"%";
            stm2.setString(1, query);
            stm2.setString(2, query);
            stm2.setString(3, query);

            stm1.setString(1, query);
            stm1.setString(2, query);
            stm1.setString(3, query);

            stm2.setInt(3 + 1,size);
            stm2.setInt(3 + 2, (page - 1) * size);

            ResultSet rst2 = stm2.executeQuery();
            ArrayList<CustomerDTO> customers = new ArrayList<>();
            while(rst2.next()){
                String id = rst2.getString("id");
                String name= rst2.getString("name");
                String address = rst2.getString("address");
                customers.add(new CustomerDTO(id,name,address));


            }
            Jsonb jsonb = JsonbBuilder.create();
            response.setContentType("application/json");
            jsonb.toJson(customers, response.getWriter());






        } catch (SQLException | IOException e) {
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
