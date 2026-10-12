package org.example.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/students")
public class displaystudentservlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head><title>Student Records</title></head>");
        out.println("<body>");

        out.println("<h2>Student Records</h2>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = System.getenv("DB_URL");
            String user = System.getenv("DB_USER");
            String password = System.getenv("DB_PASSWORD");
            if (url == null || user == null || password == null) {
                throw new IllegalStateException("Set DB_URL, DB_USER, and DB_PASSWORD.");
            }

            try (Connection con = DriverManager.getConnection(url, user, password);
                 PreparedStatement ps = con.prepareStatement("SELECT * FROM student");
                 ResultSet rs = ps.executeQuery()) {
                out.println("<table border='1' cellpadding='10'>");
                out.println("<tr><th>ID</th><th>Name</th><th>Marks</th></tr>");

                while (rs.next()) {
                    out.println("<tr>");
                    out.println("<td>" + rs.getInt("id") + "</td>");
                    out.println("<td>" + rs.getString("name") + "</td>");
                    out.println("<td>" + rs.getInt("marks") + "</td>");
                    out.println("</tr>");
                }

                out.println("</table>");
            }
        } catch (Exception e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
        }

        out.println("</body>");
        out.println("</html>");
    }
}

