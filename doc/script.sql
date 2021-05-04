CREATE TABLE carro ( 
	codcarro integer NOT NULL, 
	modelo character varying(50) NOT NULL, 
	fabricante character varying(50) NOT NULL, 
	cor varchar(50) NOT NULL, 
	ano date NOT NULL,
	CONSTRAINT carro_pk PRIMARY KEY (codcarro)
);