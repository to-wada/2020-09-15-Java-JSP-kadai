//ユーザー登録に関するリクエストを処理するコントローラ
package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
import servlet.RegisterUserLogic;
import servlet.User;

@WebServlet("/RegisterUser")
public class RegisterUser extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // フォワード先
        String forwardPath = null;

        // サーブレットクラスの動作を決定する「action」の値を
        // リクエストパラメーターから取得(P142)
        String action = request.getParameter("action");

        // 「登録の開始」をリクエストされた時の処理
        if (action == null) {
            // フォワード先を指定
            forwardPath = "/registerForm.jsp";
        }

        // 登録確認画面から「登録実行」をリクエストされた時の処理
        else if (action.equals("done")) {
            // セッションスコープに保存された登録ユーザーを取得
            HttpSession session = request.getSession();
            User registerUser = (User) session.getAttribute("registerUser");

            // 登録処理の呼び出し
            RegisterUserLogic logic = new RegisterUserLogic();
            logic.execute(registerUser);

            // 不要となったセッションスコープ内のインスタンスを削除
            session.removeAttribute("registerUser");

            // 登録後のフォワード先を設定
            forwardPath = "/registerDone.jsp";
        }

        // 設定されたフォワード先にフォワード
        RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // リクエストパラメーターの取得
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String pass = request.getParameter("pass");

        // 登録するユーザーの情報を設定
        User registerUser = new User(id, name, pass);

        // セッションスコープに登録ユーザーを保存
        HttpSession session = request.getSession();
        session.setAttribute("registerUser", registerUser);

        // フォワード
        RequestDispatcher dispatcher = request.getRequestDispatcher("/registerConfirm.jsp");
        dispatcher.forward(request, response);

    }

}