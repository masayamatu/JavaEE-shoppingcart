package exercise3object;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * Servlet implementation class SeafoodFormServlet
 */
@WebServlet("/form")
public class SeafoodFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(true);
		HashMap<String, Seafood> cart = null;
		ArrayList<Seafood> menu = new ArrayList<>();
		//メニューの読み込みを行いmenuにメニューデータを格納
		FileOperator file = new FileOperator("\\miyata\\exercise3object\\src\\exercise3object\\seafoodmenu.txt");
		TreeMap<String,String[]> readmenu = file.SeafoodMenuRead();
		int count = 0;
		for(String name:readmenu.keySet()) {
			String[] content = readmenu.get(name);
			Seafood temp = new Seafood(content[0],content[1],content[2]);
			menu.add(temp);
		}
		//カートの初期化
		if(session != null) {
			cart = (HashMap<String, Seafood>)session.getAttribute("cart");
		}
		String message;
		if(cart != null && !cart.isEmpty()) {
			int sumitem = 0;
			for(String item:cart.keySet()) {
				sumitem += cart.get(item).count;
			}
			message = "カートに" + sumitem +"個の商品が入っています";
		} else {
			message = "商品を選んでください";
		}
		request.setAttribute("message", message);
		session.setAttribute("menu", menu);

		ServletContext context = getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher("/seafood_form.jsp");
		dispatcher.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);
		ArrayList<Seafood> menu = (ArrayList<Seafood>)session.getAttribute("menu");
		String itemName = request.getParameter("itemName");
		System.out.println(itemName);
		String message;
		HashMap<String, Seafood> cart = (HashMap<String, Seafood>)session.getAttribute("cart");
		//カートがヌルの場合インスタンス化
		if(cart == null) {
			cart = new HashMap<String, Seafood>();
		}
		if(cart.get(itemName) != null) {
			cart.get(itemName).count++;
			int sumitem = 0;
			for(String item:cart.keySet()) {
				sumitem += cart.get(item).count;
			}
			message = "カートに" + sumitem + "個の商品が入っています";
		}else if(itemName != null && cart.get(itemName) == null){
			for(int i = 0; i < menu.size(); i++) {
				if(itemName.equals(menu.get(i).itemName)) {
					cart.put(itemName,menu.get(i));
				}
			}
			int sumitem = 0;
			for(String item:cart.keySet()) {
				sumitem += cart.get(item).count;
			}
			message = "カートに" + sumitem + "個の商品が入っています";
		}else {
			cart.clear();
			cart = new HashMap<String, Seafood>();
			message = "カートを空にしました。";
		}
		session.setAttribute("cart", cart);
	    session.setAttribute("menu", menu);
		request.setAttribute("message", message);

		ServletContext context = request.getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher("/seafood_form.jsp");
		dispatcher.forward(request, response);
	}

}
