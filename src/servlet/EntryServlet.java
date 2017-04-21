package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.google.gson.*;

import util.*;
import menu.*;

/**
 * Servlet implementation class EntryServlet
 */
@WebServlet("/EntryServlet")
public class EntryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EntryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//String buttonClicked = request.getParameter("submitFile");
		HttpSession session = request.getSession();
		StringBuffer sb = new StringBuffer();
		String line = null;
		String filePath = null;
		
		try{
			BufferedReader br = request.getReader();
			while((line=br.readLine())!=null)
			{
				sb.append(line);
			}
		}
		catch(IOException ioe){
			System.out.println("Reqeust Error");
		}
		//String data = sb.toString();
		//System.out.println(data);
		try{
		JsonElement jelement = new JsonParser().parse(sb.toString());
		JsonObject jobject = jelement.getAsJsonObject();
		filePath = jobject.get("filePath").getAsString();
		}
		catch(Exception e)
		{
			System.out.println("Error");
			System.out.println(filePath);
		}

		
		String entryError = "";
		//System.out.println(filePath);
		StringChecker sc = new StringChecker();
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		if(sc.isBlank(filePath))
		{
			entryError="File path can not be empty";
			response.getWriter().write(entryError);
			//session.setAttribute("entryError", entryError);
			//response.sendRedirect("entry_jingxiny.jsp");
			//request.setAttribute("entryError", entryError);
			//request.getRequestDispatcher("entry_jingxiny.jsp").forward(request, response);
		}
		else
		{			
			MainMenu mainMenu = new MainMenu();
			if(!mainMenu.readFile(filePath))
			{
				entryError="Failed to open the file";
				response.getWriter().write(entryError);
				//session.setAttribute("entryError", entryError);
				//response.sendRedirect("entry_jingxiny.jsp");
				//request.setAttribute("entryError", entryError);
				//request.getRequestDispatcher("entry_jingxiny.jsp").forward(request, response);
			}
			else
			{
				ArrayList<String> errorMessage = mainMenu.checkErrors();
				if(errorMessage.isEmpty())
				{
					mainMenu.setupFollowers();
					session.setAttribute("mainMenu",mainMenu);
					//session.removeAttribute("entryError");
					//response.sendRedirect("LoginPage.jsp");
					entryError="None";
					response.getWriter().write(entryError);
				}
				else
				{
					entryError="XML file error:\n" + errorMessage.get(0);
					response.getWriter().write(entryError);
					//session.setAttribute("entryError", entryError);
					//response.sendRedirect("entry_jingxiny.jsp");
					//request.setAttribute("entryError", entryError);
					//request.getRequestDispatcher("entry_jingxiny.jsp").forward(request, response);
				}
			}
				
		}	
	}
	//}


}
