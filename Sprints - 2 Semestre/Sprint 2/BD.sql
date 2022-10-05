CREATE DATABASE vaguinhas

USE vaguinhas

CREATE TABLE Usuarios(
	id INT PRIMARY KEY IDENTITY(1,1),
	login VARCHAR(15),
	senha VARCHAR(20),
	tipo INT
)

CREATE TABLE Cursos(
	id INT PRIMARY KEY IDENTITY(1,1),
	nome VARCHAR(50)
)


CREATE TABLE Empresas(
	id INT PRIMARY KEY IDENTITY(1,1),
	id_usuario INT REFERENCES Usuarios (id),
	nome_fantasia VARCHAR(128),
	razao_social VARCHAR(128),
	telefone VARCHAR(30),
	email VARCHAR(50),
	logradouro VARCHAR(70),
	cidade VARCHAR(50),
	estado VARCHAR(50),
	pais VARCHAR(50),
	complemento VARCHAR(20),
	bairro VARCHAR(50),
	numero VARCHAR(10),
	cep VARCHAR(12),
	cnpj VARCHAR(14)
)

CREATE TABLE Vagas(
	id INT PRIMARY KEY IDENTITY(1,1),
	id_empresa INT REFERENCES Empresas (id),
	aprovacao BIT,
	titulo VARCHAR(50),
	descricao TEXT,
	ativo BIT
)

CREATE TABLE Alunos(
	id INT PRIMARY KEY IDENTITY(1,1),
	id_usuario INT REFERENCES Usuarios(id),
	id_curso INT REFERENCES Cursos(id),
	rg VARCHAR(15),
	cpf VARCHAR(14),
	ra VARCHAR(13),
	nome VARCHAR(128),
	nome_social VARCHAR(128),
	instituição VARCHAR(128) DEFAULT('Fatec Itapira - "Dr. Ogari de Castro Pacheco'),
	data_inicio DATE,
	data_termino DATE,
	telefone VARCHAR(20),
	email VARCHAR(50),
	data_nasc DATE,
	sexo VARCHAR(15),
	nome_pai VARCHAR(128),
	nome_mae VARCHAR(128),
	nacionalidade VARCHAR(20),
	logradouro VARCHAR(70),
	cidade VARCHAR(50),
	estado VARCHAR(50),
	pais VARCHAR(50),
	complemento VARCHAR(20),
	bairro VARCHAR(50),
	numero VARCHAR(10),
	cep VARCHAR(12)
)


CREATE TABLE Curriculos (
	id INT PRIMARY KEY IDENTITY(1,1),
	id_aluno INT REFERENCES Alunos (id),
	sobre TEXT
)

CREATE TABLE Experiencias (
	id INT PRIMARY KEY IDENTITY(1,1),
	id_curriculo INT REFERENCES Curriculos (id),
	cargo VARCHAR(40),
	nome_empresa VARCHAR(40),
	data_inicio DATE,
	data_termino DATE,
	atual BIT,
	descricao TEXT,
)

CREATE TABLE Formacaos (
	id INT PRIMARY KEY IDENTITY(1,1),
	id_curriculo INT REFERENCES Curriculos (id),
	curso VARCHAR(50),
	instituicao VARCHAR(50),
	diploma VARCHAR(50),
	area VARCHAR(50),
	data_inicio DATE,
	data_termino DATE,
	cursando BIT,
	descricao TEXT
)

CREATE TABLE Habilidades (
	id INT PRIMARY KEY IDENTITY(1,1),
	id_curriculo INT REFERENCES Curriculos (id),
	habilidade VARCHAR(128)
)

CREATE TABLE AlunosVagas (
	id INT PRIMARY KEY IDENTITY(1,1),
	id_aluno INT REFERENCES Alunos (id),
	id_vaga INT REFERENCES Vagas (id)
)

INSERT INTO Usuarios 
VALUES (
	'sla', '123', 1
)

INSERT INTO Usuarios 
VALUES (
	'sla2', '1234', 2
)

INSERT INTO Usuarios 
VALUES (
	'sla3', '12345', 3
)

SELECT * FROM Usuarios

INSERT INTO Cursos
VALUES ('DSM')

SELECT * FROM Cursos

INSERT INTO Alunos (id_usuario, id_curso, rg, cpf, ra, nome, nome_social, instituição, data_inicio, data_termino, telefone, email, data_nasc, sexo, nome_pai, nome_mae, nacionalidade,
logradouro, cidade, estado, pais, complemento, bairro, numero, cep)
VALUES (
	2 , 1,'423', '423', '423', 'Rugo', 'Ogur', 'Fatec Itapira', '2022-02-01', '2024-12-01', '38432174', 'rugo@gmail.com', '01-05-2003', 'Não-Binario',
	'Heitor', 'Claudineia', 'Brasileiro', 'Rua 2', 'Itapira', 'SP', 'Brasil', 'Casa', 'Dale', '24', '154154'
)

SELECT * FROM Alunos

INSERT INTO Curriculos 
VALUES (
	1, 'Brabissimo'
)

SELECT * FROM Curriculos

INSERT INTO Formacaos
VALUES (
	1, 'Fatec Itapira', 'DSM', 'Tecnologo', 'TI', '2022-01-01', '2024-01-01' ,1, 'Ahhhh' 
)

SELECT * FROM Formacaos

INSERT INTO Experiencias
VALUES (
	1, 'Analista', 'Cris', '2022-01-01', '2024-01-01' ,0, 'Analisava'
)

SELECT * FROM Experiencias

INSERT INTO Habilidades 
VALUES (
	1, 'Office'
)

INSERT INTO Habilidades 
VALUES (
	1, 'Redes'
)

SELECT * FROM Habilidades

INSERT INTO Empresas
VALUES(
	1, 'Cris', 'talia', '455', 'cristalia@cristalia.com.br', 'ave', 'maria', 'brasil', 'sao paulo', 'Asfalto', 'Supermercado', '37', '88', '9548548548'
)

SELECT * FROM Empresas

INSERT INTO Vagas 
VALUES (1, 1, 'Anal. de qualidade', 'cachorro', 1)

SELECT * FROM Vagas

INSERT INTO AlunosVagas
VALUES (1,1)

SELECT * FROM AlunosVagas


SELECT a.nome AS 'Nome Aluno' , c.nome AS 'Nome Curso' FROM Alunos a JOIN Cursos c 
ON a.id_curso = c.id





