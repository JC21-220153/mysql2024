package jp.ac.jc21;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ProductServlet
 */
@WebServlet(urlPatterns = { "/item" })
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	final String dbServer = "192.168.54.231";
	final String dbPort = "3306";
	final String dbName = "test2023";
	final String user = "test2023";
	final String pass = "test2023";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		String url = "jdbc:mysql://"+dbServer+"/"+dbName;
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().append("<h2>Connect to : ").append(url).append("</h2>");
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection  conn = DriverManager.getConnection(url, user, pass);
			
			String sql ="SELECT MAKER_CODE,MAKER_NAME FROM MAKER";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			ResultSet rs = statement.executeQuery();
			
			ArrayList<String[]> result = new ArrayList<>();
			while(rs.next()==true) {
				String[] s = new String[2];
				s[0]=rs.getString("MAKER_NAME");
				s[1]=rs.getString("MAKER_CODE");
				result.add(s);
			}
			
			request.setAttribute("result", result);
			
			String M = request.getParameter("MAKER_CODE");
			if(M == null) {
				
				String sql2 ="SELECT A.PRODUCT_CODE, A.PRODUCT_NAME, B.MAKER_NAME FROM PRODUCT A INNER JOIN MAKER B ON A.MAKER_CODE = B.MAKER_CODE";
				
				PreparedStatement statement2 = conn.prepareStatement(sql2);
				
				ResultSet rs2 = statement2.executeQuery();
				
				ArrayList<String[]> result2 = new ArrayList<>();
				while(rs2.next()==true) {
					String[] s2 = new String[3];
					s2[0]=rs2.getString("A.PRODUCT_CODE");
					s2[1]=rs2.getString("A.PRODUCT_NAME");
					s2[2]=rs2.getString("B.MAKER_NAME");
					result2.add(s2);
				}
				
				request.setAttribute("result2", result2);
			} else {
				
				String sql3 ="SELECT A.PRODUCT_CODE, A.PRODUCT_NAME, B.MAKER_NAME FROM PRODUCT A INNER JOIN MAKER B ON A.MAKER_CODE = B.MAKER_CODE" + " Where B.MAKER_CODE = ?";
				
				PreparedStatement statement3 = conn.prepareStatement(sql3);

				statement3.setString(1,M);
				ResultSet rs3 = statement3.executeQuery();
				
				ArrayList<String[]> result3 = new ArrayList<>();
				while(rs3.next()==true) {
					String[] s3 = new String[3];
					s3[0]=rs3.getString("A.PRODUCT_CODE");
					s3[1]=rs3.getString("A.PRODUCT_NAME");
					s3[2]=rs3.getString("B.MAKER_NAME");
					result3.add(s3);
				}
				
				request.setAttribute("result2", result3);
			}
			
			
			RequestDispatcher rd =
					request.getRequestDispatcher("/WEB-INF/jsp/product.jsp");
			rd.forward(request, response);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}	
	}
}