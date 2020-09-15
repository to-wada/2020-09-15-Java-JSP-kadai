//���[�U�[�o�^�Ɋւ��郊�N�G�X�g����������R���g���[��
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

        // �t�H���[�h��
        String forwardPath = null;

        // �T�[�u���b�g�N���X�̓�������肷��uaction�v�̒l��
        // ���N�G�X�g�p�����[�^�[����擾(P142)
        String action = request.getParameter("action");

        // �u�o�^�̊J�n�v�����N�G�X�g���ꂽ���̏���
        if (action == null) {
            // �t�H���[�h����w��
            forwardPath = "/registerForm.jsp";
        }

        // �o�^�m�F��ʂ���u�o�^���s�v�����N�G�X�g���ꂽ���̏���
        else if (action.equals("done")) {
            // �Z�b�V�����X�R�[�v�ɕۑ����ꂽ�o�^���[�U�[���擾
            HttpSession session = request.getSession();
            User registerUser = (User) session.getAttribute("registerUser");

            // �o�^�����̌Ăяo��
            RegisterUserLogic logic = new RegisterUserLogic();
            logic.execute(registerUser);

            // �s�v�ƂȂ����Z�b�V�����X�R�[�v���̃C���X�^���X���폜
            session.removeAttribute("registerUser");

            // �o�^��̃t�H���[�h���ݒ�
            forwardPath = "/registerDone.jsp";
        }

        // �ݒ肳�ꂽ�t�H���[�h��Ƀt�H���[�h
        RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ���N�G�X�g�p�����[�^�[�̎擾
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String pass = request.getParameter("pass");

        // �o�^���郆�[�U�[�̏���ݒ�
        User registerUser = new User(id, name, pass);

        // �Z�b�V�����X�R�[�v�ɓo�^���[�U�[��ۑ�
        HttpSession session = request.getSession();
        session.setAttribute("registerUser", registerUser);

        // �t�H���[�h
        RequestDispatcher dispatcher = request.getRequestDispatcher("/registerConfirm.jsp");
        dispatcher.forward(request, response);

    }

}