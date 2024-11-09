

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

/**
 * Servlet implementation class Juego
 */
@WebServlet("/Juego")
public class Juego extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String jugador = request.getParameter("jugador");
		int numeroAleatorio = (int) Math.floor((Math.random() * (100 - 1 + 1)) + 1);
		int intento = 0;
		
		HttpSession session = request.getSession();
		session.setAttribute("jugador", jugador);
		session.setAttribute("numero", Integer.toString(numeroAleatorio));
		session.setAttribute("intentos", Integer.toString(intento));
		
		response.setContentType("text/html");
		ServletOutputStream out = response.getOutputStream();		
		out.println("<html><body style=\"text-align: center; margin-top: 20%;\">");
		out.println("<form method=\"POST\" action=\"Juego\"><h4>");
		out.println("Introduce un número del 1 al 100: <input type=\"text\" name=\"intento\">");
		out.println("<input type=\"submit\" value=\"JUGAR\"></h4></form><br>");
		out.println("</body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int numeroAleatorio = Integer.parseInt((String)session.getAttribute("numero"));
		int intento = Integer.parseInt((String)session.getAttribute("intentos"));
		
		intento ++;
		session.setAttribute("intentos", Integer.toString(intento));
		
		int numero; 
		
		try {
			numero =  Integer.parseInt(request.getParameter("intento"));
		} catch (NumberFormatException e){
			numero = -1;
		}
		
		ServletOutputStream out = response.getOutputStream();
		
		if (numero == numeroAleatorio) {
			String jugador = (String)session.getAttribute("jugador");
			
			response.setContentType("text/html");
			out.println("<html><body style=\"text-align: center; margin-top: 20%;\"><h2>");
			out.println("¡ Felicidades " + jugador + " !</h2>");
			out.println("Has adivinado el numero en " + intento + " intentos<br>");
			out.println("<a href=\"index.html\" style=\"margin.top: 2%; width: 50%; font-size: large\">");
			out.println("Volver a Jugar</a></body></html>");

		} else if (numero > 101 || numero < 0){
			response.setContentType("text/html");
			out.println("<html><body style=\"text-align: center; margin-top: 20%;\">");
			out.println("Lo siento, no has jugado correctamente<br><br>");
			out.println("<a href=\"index.html\" style=\"margin.top: 2%; width: 50%; font-size: large\">");
			out.println("Volver a Jugar</a></body></html>");
			
		} else {
			response.setContentType("text/html");
			out.println("<html><body style=\"text-align: center; margin-top: 20%;\">");
			out.println("<form method=\"POST\" action=\"Juego\"><h4>");
			out.println("Introduce un número del 1 al 100: <input type=\"text\" name=\"intento\">");
			out.println("<input type=\"submit\" value=\"JUGAR\"></h4></form><br>");

			if (numero < numeroAleatorio) {
				out.println("PISTA: El numero secreto es mayor al introducido");
			} else {
				out.println("PISTA: El numero secreto es menor al introducido");
			}
			out.println("</body></html>");
		}
	}

}
