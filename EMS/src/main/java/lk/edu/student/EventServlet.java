package lk.edu.student;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/event")
public class EventServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/eventdb","root","Ijse@1234");
            ResultSet resultSet = connection.prepareStatement("select * from event").executeQuery();
            //creates a list by including multiple maps
            List<Map<String,String>> elist = new ArrayList<>();
            while(resultSet.next()){//go for the next row until the rows end
                //creates a new map for each row...this each map holds one row in a table with these column values
                Map<String,String> event = new HashMap<>();
                event.put("eid", resultSet.getString("eid"));//put column val into the map
                event.put("ename", resultSet.getString("ename"));
                event.put("edescription", resultSet.getString("edescription"));
                event.put("edate", resultSet.getString("edate"));
                event.put("eplace", resultSet.getString("eplace"));
                elist.add(event);
            }
            resp.setContentType("application/json");
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(resp.getWriter(),elist);//sends the list
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/eventdb","root","Ijse@1234");
            PreparedStatement preparedStatement = connection.prepareStatement("insert into event(eid,ename,edescription,edate,eplace) values(?,?,?,?,?)");
            ObjectMapper mapper = new ObjectMapper();
            Map<String,String> event = mapper.readValue(req.getReader(), Map.class);
            preparedStatement.setString(1, event.get("eid"));
            preparedStatement.setString(2, event.get("ename"));
            preparedStatement.setString(3, event.get("edescription"));
            preparedStatement.setString(4, event.get("edate"));
            preparedStatement.setString(5, event.get("eplace"));
            int rows = preparedStatement.executeUpdate();
            resp.setContentType("application/json");
            mapper.writeValue(resp.getWriter(),event);

//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        resp.setHeader("Access-Control-Allow-Headers", "content-type");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

}