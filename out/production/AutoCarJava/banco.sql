CREATE DATABASE IF NOT EXISTS autocar_java;
USE autocar_java;

CREATE TABLE IF NOT EXISTS cliente (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    telefone VARCHAR(20),
    email VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS funcionario (
    id_funcionario INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    matricula VARCHAR(20) NOT NULL UNIQUE,
    cargo VARCHAR(60),
    telefone VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS veiculo (
    id_veiculo INT AUTO_INCREMENT PRIMARY KEY,
    marca VARCHAR(60) NOT NULL,
    modelo VARCHAR(80) NOT NULL,
    ano INT NOT NULL,
    preco DECIMAL(12,2) NOT NULL,
    situacao VARCHAR(20) NOT NULL DEFAULT 'Disponível'
);

CREATE TABLE IF NOT EXISTS venda (
    id_venda INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,
    id_funcionario INT NOT NULL,
    id_veiculo INT NOT NULL UNIQUE,
    data_venda DATE NOT NULL,
    valor DECIMAL(12,2) NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente),
    FOREIGN KEY (id_funcionario) REFERENCES funcionario(id_funcionario),
    FOREIGN KEY (id_veiculo) REFERENCES veiculo(id_veiculo)
);
