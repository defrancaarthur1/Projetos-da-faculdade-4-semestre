const tabela = document.getElementById("usuarios");
const mensagem = document.getElementById("mensagem");
const atualizar = document.getElementById("atualizar");

function escaparHtml(valor) {
    return String(valor ?? "")
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;")
        .replaceAll("'", "&#039;");
}

function carregarUsuarios() {
    mensagem.textContent = "Carregando usuários...";
    tabela.replaceChildren();

    fetch("usuarios")
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP ${response.status}`);
            }
            return response.json();
        })
        .then(usuarios => {
            if (usuarios.length === 0) {
                mensagem.textContent = "Nenhum usuário cadastrado.";
                return;
            }

            mensagem.textContent = `${usuarios.length} usuário(s) cadastrado(s).`;
            tabela.innerHTML = usuarios.map(usuario => `
                <tr>
                    <td>${escaparHtml(usuario.nome)}</td>
                    <td>${escaparHtml(usuario.email)}</td>
                    <td>${escaparHtml(usuario.telefone)}</td>
                    <td>${escaparHtml(usuario.endereco)}${usuario.numero ? `, ${escaparHtml(usuario.numero)}` : ""}</td>
                    <td>${escaparHtml(usuario.cidade)}</td>
                    <td>${escaparHtml(usuario.estado)}</td>
                </tr>
            `).join("");
        })
        .catch(erro => {
            mensagem.textContent = "Não foi possível carregar os usuários.";
            console.error(erro);
        });
}

atualizar.addEventListener("click", carregarUsuarios);
carregarUsuarios();
