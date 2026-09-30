package com.functionhall;

import static spark.Spark.*;
import com.google.gson.*;
import java.sql.*;
import java.util.*;

public class Main {
    static final String URL="jdbc:mysql://localhost:3306/function_hall";
    static final String USER="root";
    static final String PASS="root"; // change to your MySQL password

    static Connection db() throws SQLException {
        return DriverManager.getConnection(URL,USER,PASS);
    }

    public static void main(String[] args) {
        port(8080);
        before((req,res)->res.header("Access-Control-Allow-Origin","*"));
        options("/*",(req,res)->{res.header("Access-Control-Allow-Headers","Content-Type");return "";});

        get("/api/status",(req,res)->"{\"ok\":true,\"message\":\"Java JDBC backend is running\"}");

        post("/api/bookings",(req,res)->{
            res.type("application/json");
            JsonObject body=JsonParser.parseString(req.body()).getAsJsonObject();
            String name=body.get("customerName").getAsString();
            String phone=body.get("phone").getAsString();
            String date=body.has("eventDate") && !body.get("eventDate").isJsonNull()?body.get("eventDate").getAsString():null;
            String event=body.get("eventType").getAsString();
            double total=body.get("totalAmount").getAsDouble();

            String sql="INSERT INTO bookings(customer_name,phone,event_date,event_type,total_amount,payment_status) VALUES(?,?,?,?,?,?)";
            try(Connection c=db(); PreparedStatement ps=c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
                ps.setString(1,name); ps.setString(2,phone);
                if(date==null || date.isBlank()) ps.setNull(3,Types.DATE); else ps.setDate(3,Date.valueOf(date));
                ps.setString(4,event); ps.setDouble(5,total); ps.setString(6,"PAID");
                ps.executeUpdate();
                ResultSet rs=ps.getGeneratedKeys(); rs.next();
                long id=rs.getLong(1);
                return new Gson().toJson(Map.of("success",true,"bookingId",id));
            }catch(SQLException ex){
                res.status(500);
                return new Gson().toJson(Map.of("success",false,"error",ex.getMessage()));
            }
        });
        System.out.println("Java JDBC backend running at http://localhost:8080");
    }
}