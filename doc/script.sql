CREATE TABLE carro ( 
	codcarro integer NOT NULL, 
	modelo character varying(50) NOT NULL, 
	fabricante character varying(50) NOT NULL, 
	cor varchar(50) NOT NULL, 
	ano date NOT NULL,
	CONSTRAINT carro_pk PRIMARY KEY (codcarro)
);

CREATE TABLE usuario (
	codusuario integer NOT NULL,
	login character varying(50) NOT NULL,
	senha character varying(50) NOT NULL,
	CONSTRAINT usuario_pk PRIMARY KEY (codusuario)
);

CREATE TABLE usuario_telefone(
	itemtelefone integer NOT NULl,
	numero character varying(13) NOT NULL,
	tipo integer NOT NULL,
	codusuario integer,
	CONSTRAINT telefone_pk PRIMARY KEY (itemtelefone, codusuario),
	FOREIGN KEY (codusuario) REFERENCES usuario(codusuario)
);

Create TABLE usuario_carro(
	codusuario integer NOT NULL,
	codcarro integer NOT NULL,
	CONSTRAINT usuario_carro_pk PRIMARY KEY (codusuario, codcarro),
	FOREIGN KEY (codusuario) REFERENCES usuario(codusuario),
	FOREIGN KEY (codcarro) REFERENCES carro(codcarro)
);