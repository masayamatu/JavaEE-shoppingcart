package exercise3object;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SeafoodCartServlet
 */
@WebServlet("/cart")
public class SeafoodCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);
		ArrayList<Seafood> menu = (ArrayList<Seafood>)session.getAttribute("menu");
		HashMap<String,Seafood> cart = (HashMap<String,Seafood>)session.getAttribute("cart");
		String message;
		int sum = 0;
		//カート内の商品数の算出
		if(cart == null || cart.size() == 0) {
			message = "カートに商品が入っていません";
		} else {
			for(String item:cart.keySet()) {
				int price = 0;
				for(int i = 0; i < menu.size(); i++) {
					if(item.equals(menu.get(i).itemName)) {
						price = Integer.parseInt(menu.get(i).price);
					}
				}
				sum += price * cart.get(item).count;
			}
			message = "合計は\\" + sum + "になります";
		}
		request.setAttribute("message", message);
		session.setAttribute("menu",menu);
		session.setAttribute("cart",cart);

		ServletContext context = request.getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher("/seafood_cart.jsp");
		dispatcher.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		request.setCharacterEncoding("UTF-8");
		HttpSession session =request.getSession(false);
		ArrayList<Seafood> menu = (ArrayList<Seafood>)session.getAttribute("menu");
		HashMap<String, Seafood> cart = (HashMap<String, Seafood>)session.getAttribute("cart");
		String change = request.getParameter("change");
		String changeItem = request.getParameter("changeItem");
		//＋-がクリックされた場合
		if(change != null && !change.isEmpty()) {
			if(change.equals("+")) {
				cart.get(changeItem).count++;
			} else {
				cart.get(changeItem).count--;

				if(cart.get(changeItem).count < 0) {
					cart.get(changeItem).count++;
				}
			}
		}
		//個数が手入力された場合
		String changecount = request.getParameter("changecount");
		String alert = null;
		if(changecount != null && !changecount.isEmpty()) {
			try {
				int intcount = Integer.parseInt(changecount);
				cart.get(changeItem).count = intcount;
				if(cart.get(changeItem).count < 0) {
					cart.get(changeItem).count = 0;
				}
			}catch(NumberFormatException e) {
			    alert = "数字を入力して下さい";
			}
		}
		//カートを空にするがクリックされた場合
		String delItem = request.getParameter("delItem");
		if(delItem != null && !delItem.isEmpty()) {
			cart.remove(delItem);
		}
		request.setAttribute("alert",alert);
		session.setAttribute("cart", cart);
		session.setAttribute("menu", menu);
		//doGetで商品個数の処理を行いJSPへレスポンス
		doGet(request,response);
	}
}
