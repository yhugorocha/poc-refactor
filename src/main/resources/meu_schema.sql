CREATE TABLE tb_endereco(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    cep VARCHAR(15),
    logradouro VARCHAR(100),
    bairro VARCHAR(100),
    localidade VARCHAR(100),
    uf VARCHAR(100),
    complemento VARCHAR(100),
    numero VARCHAR(10)
);

CREATE TABLE tb_solicitante(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    cpf VARCHAR(15),
    nome VARCHAR(100),
    rg VARCHAR(10),
    dtnascimento TIMESTAMP,
    telefone VARCHAR(20),
    email VARCHAR(100),
    endereco_id INTEGER REFERENCES tb_endereco (ID),
    dtregistro TIMESTAMP
);

CREATE TABLE tb_regiao(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    sigla VARCHAR(20),
    nome VARCHAR(50)
);
CREATE TABLE tb_usuario(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    login VARCHAR(50),
    senha VARCHAR(250),
    admin boolean
);

CREATE TABLE tb_gestor_ra(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50),
    email VARCHAR(35),
    cpf VARCHAR(15),
    telefone VARCHAR(20),
    id_regiao INTEGER REFERENCES tb_regiao (ID)
);
CREATE TABLE tb_quadra(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50),
    qtd_pessoas INTEGER(10),
    foto BLOB,
    id_regiao INTEGER REFERENCES tb_regiao (ID),
    endereco_id INTEGER REFERENCES tb_endereco (ID)
)


CREATE TABLE tb_semana(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    dia VARCHAR(50)
)

CREATE TABLE tb_horario(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    hora VARCHAR(50)
)

CREATE TABLE tb_reserva(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    id_solicitante INTEGER REFERENCES tb_solicitante (id),
    id_quadra INTEGER REFERENCES tb_quadra (id),
    dt_inicio TIMESTAMP,
    dt_final TIMESTAMP,
    dt_registro TIMESTAMP,
    id_semana INTEGER REFERENCES tb_semana (id),
    id_horario INTEGER REFERENCES tb_horario (id),
    status VARCHAR(10)
);


INSERT INTO db_api_agenda.tb_semana
(dia)
VALUES("Segunda-Feira"),
      ("Terça-Feira"),
      ("Quarta-Feira"),
      ("Quinta-Feira"),
      ("Sexta-Feira")

INSERT INTO db_api_agenda.tb_horario
(hora)
VALUES('10h as 12h'),
	  ('12h as 14h'),
	  ('14h as 16h'),
	  ('16h as 18h'),
	  ('18h as 20h')
