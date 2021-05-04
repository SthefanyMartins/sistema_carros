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