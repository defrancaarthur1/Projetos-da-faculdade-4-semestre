DROP DATABASE IF EXISTS trabalho5;
CREATE DATABASE trabalho5
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE trabalho5;

CREATE TABLE usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    telefone VARCHAR(20) NOT NULL,
    cep VARCHAR(10),
    endereco VARCHAR(150),
    numero VARCHAR(10),
    cidade VARCHAR(100),
    estado VARCHAR(50),
    senha VARCHAR(60)NOT NULL,
    perfil VARCHAR(20) NOT NULL DEFAULT 'USUARIO'
);

DROP USER IF EXISTS 'trabalho5_app'@'localhost';

CREATE USER 'trabalho5_app'@'localhost'
IDENTIFIED BY 'trabalho5_senha';

GRANT ALL PRIVILEGES ON trabalho5.*
TO 'trabalho5_app'@'localhost';

FLUSH PRIVILEGES;
