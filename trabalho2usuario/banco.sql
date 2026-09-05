DROP DATABASE IF EXISTS trabalho2Usuario;
CREATE DATABASE trabalho2Usuario
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE trabalho2Usuario;

CREATE TABLE usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    telefone VARCHAR(20) NOT NULL,
    cep VARCHAR(10),
    endereco VARCHAR(150),
    numero VARCHAR(10),
    cidade VARCHAR(100),
    estado VARCHAR(50)
);

DROP USER IF EXISTS 'trabalho2_app'@'localhost';

CREATE USER 'trabalho2_app'@'localhost'
IDENTIFIED BY 'trabalho2_senha';

GRANT ALL PRIVILEGES ON trabalho2Usuario.*
TO 'trabalho2_app'@'localhost';

FLUSH PRIVILEGES;
