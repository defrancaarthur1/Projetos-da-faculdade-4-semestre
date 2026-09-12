package br.com.trabalho2Usuario.controller;

import br.com.trabalho2Usuario.dao.UsuarioDAO;
import br.com.trabalho2Usuario.model.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UsuarioDAO dao;

    @Override
    public void init() {
        dao = new UsuarioDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        if (email == null || email.isBlank()
                || senha == null || senha.isBlank()) {

            response.sendRedirect("login.html?erro=1");
            return;
        }

        try {

            Usuario usuario = dao.login(email, senha);

            if (usuario != null) {

                HttpSession session = request.getSession(true);

                session.setAttribute("usuario", usuario);
                session.setAttribute("perfil", usuario.getPerfil());

                response.sendRedirect("usuarios.html");

            } else {

                response.sendRedirect("login.html?erro=1");
            }

        } catch (SQLException e) {

            e.printStackTrace();

            response.sendError(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "Erro ao realizar login."
            );
        }
    }
}
