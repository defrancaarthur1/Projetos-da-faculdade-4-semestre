package br.com.trabalho2Usuario.controller;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/usuarios")
public class PerfilFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("usuario") == null) {
            resp.sendRedirect("login.html");
            return;
        }

        String perfil = (String) session.getAttribute("perfil");

        String metodo = req.getMethod();

        // GET = consulta. Usuário comum pode consultar.
        if ("GET".equalsIgnoreCase(metodo)) {
            chain.doFilter(request, response);
            return;
        }

        // POST, PUT e DELETE = operações administrativas.
        if ("ADMIN".equalsIgnoreCase(perfil)) {
            chain.doFilter(request, response);
            return;
        }

        // Usuário comum tentando executar operação administrativa.
        resp.sendError(
            HttpServletResponse.SC_FORBIDDEN,
            "Acesso negado. Apenas administradores podem realizar esta operação."
        );
    }
}
