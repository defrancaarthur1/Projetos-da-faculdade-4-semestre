package br.com.trabalho2Usuario.controller;

import br.com.trabalho2Usuario.dao.UsuarioDAO;
import br.com.trabalho2Usuario.model.Usuario;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/usuarios")
public class UsuarioServlet extends HttpServlet {
    private UsuarioDAO dao;
    private Gson gson;

    @Override
    public void init() {
        dao = new UsuarioDAO();
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            List<Usuario> usuarios = dao.listar();
            response.getWriter().write(gson.toJson(usuarios));
        } catch (SQLException exception) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao buscar usuários.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            String acao = request.getParameter("acao");
            if ("excluir".equals(acao)) {
                dao.excluir(Integer.parseInt(request.getParameter("id")));
            } else if ("atualizar".equals(acao)) {
                Usuario usuario = criarUsuario(request);
                usuario.setId(Integer.parseInt(request.getParameter("id")));
                dao.atualizar(usuario);
            } else {
                dao.inserir(criarUsuario(request));
            }
            response.getWriter().write("{\"sucesso\":true}");
        } catch (Exception exception) {
            getServletContext().log("Erro ao salvar usuário", exception);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"sucesso\":false,\"erro\":\"Erro interno ao salvar usuário. Consulte o log do servidor.\"}");
        }
    }

    private Usuario criarUsuario(HttpServletRequest request) {
        Usuario usuario = new Usuario();
        usuario.setNome(request.getParameter("nome"));
        usuario.setEmail(request.getParameter("email"));
        usuario.setTelefone(request.getParameter("telefone"));
        usuario.setCep(request.getParameter("cep"));
        usuario.setEndereco(request.getParameter("endereco"));
        usuario.setNumero(request.getParameter("numero"));
        usuario.setCidade(request.getParameter("cidade"));
        usuario.setEstado(request.getParameter("estado"));
        return usuario;
    }
}
