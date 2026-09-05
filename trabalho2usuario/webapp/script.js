const formulario = document.getElementById("formCadastro");

const nome = document.getElementById("nome");
const email = document.getElementById("email");
const telefone = document.getElementById("telefone");
const cep = document.getElementById("cep");

const logradouro = document.getElementById("logradouro");
const numero = document.getElementById("numero");
const cidade = document.getElementById("cidade");
const estado = document.getElementById("estado");

const mensagem = document.getElementById("mensagem");


// Validação do formulário
formulario.addEventListener("submit", function(event) {

    event.preventDefault();

    mensagem.textContent = "";
    mensagem.style.color = "red";

    if (nome.value.trim().length < 3) {
        mensagem.textContent = "Digite um nome válido.";
        nome.focus();
        return;
    }

    if (!email.checkValidity()) {
        mensagem.textContent = "Digite um email válido.";
        email.focus();
        return;
    }

    const telefoneNumeros = telefone.value.replace(/\D/g, "");

    if (telefoneNumeros.length < 10 || telefoneNumeros.length > 11) {
        mensagem.textContent = "Digite um telefone válido.";
        telefone.focus();
        return;
    }

    const cepNumeros = cep.value.replace(/\D/g, "");

    if (cepNumeros.length !== 8) {
        mensagem.textContent = "Digite um CEP válido.";
        cep.focus();
        return;
    }

    if (logradouro.value === "") {
        mensagem.textContent = "Consulte o CEP antes de cadastrar.";
        cep.focus();
        return;
    }

    const dados = new URLSearchParams(new FormData(formulario));
    dados.set("endereco", logradouro.value);
    dados.delete("logradouro");
    dados.set("acao", "inserir");

    fetch("usuarios", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8"
        },
        body: dados
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP ${response.status}`);
            }
            return response.json();
        })
        .then(resultado => {
            if (!resultado.sucesso) {
                throw new Error("O servidor recusou o cadastro.");
            }

            mensagem.style.color = "green";
            mensagem.textContent = "Cadastro realizado com sucesso!";
            formulario.reset();
        })
        .catch(erro => {
            mensagem.style.color = "red";
            mensagem.textContent = "Não foi possível salvar o cadastro.";
            console.error(erro);
        });

});


// Consulta automática do CEP
cep.addEventListener("blur", function() {

    const cepNumeros = cep.value.replace(/\D/g, "");

    if (cepNumeros.length !== 8) {
        mensagem.style.color = "red";
        mensagem.textContent = "Digite um CEP com 8 números.";
        return;
    }

    mensagem.style.color = "black";
    mensagem.textContent = "Consultando endereço...";

    fetch(`https://viacep.com.br/ws/${cepNumeros}/json/`)
        .then(response => response.json())
        .then(dados => {

            if (dados.erro) {
                mensagem.style.color = "red";
                mensagem.textContent = "CEP não encontrado.";
                return;
            }

            logradouro.value = dados.logradouro;
            cidade.value = dados.localidade;
            estado.value = dados.uf;

            mensagem.style.color = "green";
            mensagem.textContent = "Endereço encontrado automaticamente!";

        })
        .catch(erro => {

            mensagem.style.color = "red";
            mensagem.textContent = "Não foi possível consultar o CEP.";

            console.error(erro);
        });

});
