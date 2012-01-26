-- USE herbalife
/*************************************************************************************************************************************************************************************************************/

DROP TABLE IF EXISTS HL_CATEGORIA_STATUS;
CREATE  TABLE IF NOT EXISTS HL_CATEGORIA_STATUS (
  CD_STATUS INTEGER NOT NULL AUTO_INCREMENT ,
  NM_STATUS VARCHAR(60) NOT NULL ,
  DS_STATUS VARCHAR(200) NULL ,
  CREATED TIMESTAMP NOT NULL DEFAULT now() ,
  PRIMARY KEY (CD_STATUS)
);

INSERT INTO HL_CATEGORIA_STATUS ( NM_STATUS, DS_STATUS ) VALUES
( 'Ativa', 'Categoria Ativa' ),
( 'Inativa', 'Categoria Inativa' );

/*************************************************************************************************************************************************************************************************************/

DROP TABLE IF EXISTS HL_CATEGORIA ;
CREATE  TABLE IF NOT EXISTS HL_CATEGORIA (
  CD_CATEGORIA INTEGER NOT NULL AUTO_INCREMENT ,
  CD_PAI INTEGER NULL ,
  NM_CATEGORIA VARCHAR(60) NOT NULL ,
  DS_CATEGORIA VARCHAR(200) NULL ,
  STATUS INTEGER NOT NULL ,
  CREATED TIMESTAMP NOT NULL,
  PRIMARY KEY (CD_CATEGORIA)
);

/*************************************************************************************************************************************************************************************************************/

DROP TABLE IF EXISTS HL_PRODUTO_STATUS ;
CREATE  TABLE IF NOT EXISTS HL_PRODUTO_STATUS (
  CD_STATUS INTEGER NOT NULL AUTO_INCREMENT ,
  NM_STATUS VARCHAR(60) NOT NULL ,
  DS_STATUS VARCHAR(200) NULL ,
  CREATED TIMESTAMP NOT NULL DEFAULT now() ,
  PRIMARY KEY (CD_STATUS)
);

INSERT INTO HL_PRODUTO_STATUS ( NM_STATUS, DS_STATUS ) VALUES
( 'Ativo', 'Produto Ativo' ),
( 'Inativo', 'Produto Inativo' );

/*************************************************************************************************************************************************************************************************************/

DROP TABLE IF EXISTS HL_PRODUTO ;
CREATE  TABLE IF NOT EXISTS HL_PRODUTO (
  CD_PRODUTO INTEGER NOT NULL AUTO_INCREMENT ,
  CD_CATEGORIA INTEGER NOT NULL ,
  NM_PRODUTO VARCHAR(60) NOT NULL ,
  DS_PRODUTO_CURTA VARCHAR(200) NULL ,
  DS_PRODUTO_LONGA BLOB NULL ,
  VL_PRODUTO DECIMAL(14,4) NOT NULL ,
  STATUS INTEGER NOT NULL ,
  CREATED TIMESTAMP NOT NULL DEFAULT now() ,
  PRIMARY KEY (CD_PRODUTO)
);

/*************************************************************************************************************************************************************************************************************/

DROP TABLE IF EXISTS HL_PRODUTO_IMAGEM ;
CREATE  TABLE IF NOT EXISTS HL_PRODUTO_IMAGEM (
  CD_IMAGEM INTEGER NOT NULL AUTO_INCREMENT ,
  CD_PRODUTO INTEGER NOT NULL ,
  NM_IMAGEM VARCHAR(60) NOT NULL ,
  DS_PATH VARCHAR(255) NULL ,
  DS_CONTENT LONGBLOB NULL ,
  DS_TYPE VARCHAR(60) NULL ,
  NU_SIZE DECIMAL(10,0) NULL ,
  NU_WIDTH DECIMAL(10,0) NULL ,
  NU_HEIGHT DECIMAL(10,0) NULL ,
  CREATED TIMESTAMP NOT NULL DEFAULT now() ,
  PRIMARY KEY (CD_IMAGEM)
);

/*************************************************************************************************************************************************************************************************************/

DROP TABLE IF EXISTS HL_CLIENTE ;
CREATE  TABLE IF NOT EXISTS HL_CLIENTE (
  CD_CLIENTE INTEGER NOT NULL AUTO_INCREMENT ,
  NM_CLIENTE VARCHAR(60) NOT NULL ,
  DS_EMAIL VARCHAR(200) NOT NULL ,
  DS_LOGIN VARCHAR(60) NOT NULL ,
  DS_SENHA VARCHAR(60) NOT NULL ,
  NU_DDD_RES VARCHAR(5) NULL ,
  NU_DDD_CEL VARCHAR(5) NULL ,
  NU_DDD_COM VARCHAR(5) NULL ,
  NU_TEL_RES VARCHAR(20) NULL ,
  NU_TEL_CEL VARCHAR(20) NULL ,
  NU_TEL_COM VARCHAR(20) NULL ,
  CREATED TIMESTAMP NOT NULL DEFAULT now(),
  PRIMARY KEY (CD_CLIENTE)
);

/*************************************************************************************************************************************************************************************************************/
DROP TABLE IF EXISTS HL_CLIENTE_ENDERECO ;
CREATE  TABLE IF NOT EXISTS HL_CLIENTE_ENDERECO (
  CD_ENDERECO INTEGER NOT NULL AUTO_INCREMENT ,
  CD_CLIENTE INTEGER NOT NULL ,
  NM_ENDERECO VARCHAR(60) NOT NULL ,
  DS_ENDERECO VARCHAR(100) NOT NULL ,
  NU_ENDERECO DECIMAL(10,0) NOT NULL ,
  DS_COMPLEMENTO VARCHAR(60) NULL ,
  DS_BAIRRO VARCHAR(60) NOT NULL ,
  DS_CIDADE VARCHAR(60) NOT NULL ,
  NU_CEP VARCHAR(15) NOT NULL ,
  SG_UF VARCHAR(3) NOT NULL ,
  CREATED TIMESTAMP NOT NULL DEFAULT now() ,
  PRIMARY KEY (CD_ENDERECO)
);

/*************************************************************************************************************************************************************************************************************/

DROP TABLE IF EXISTS HL_COMPRA_STATUS ;
CREATE  TABLE IF NOT EXISTS HL_COMPRA_STATUS (
  CD_STATUS INTEGER NOT NULL AUTO_INCREMENT ,
  NM_STATUS VARCHAR(60) NOT NULL ,
  DS_STATUS VARCHAR(200) NULL ,
  CREATED TIMESTAMP NOT NULL DEFAULT now() ,
  PRIMARY KEY (CD_STATUS)
);

/*************************************************************************************************************************************************************************************************************/

DROP TABLE IF EXISTS HL_COMPRA ;
CREATE  TABLE IF NOT EXISTS HL_COMPRA (
  CD_COMPRA INTEGER NOT NULL AUTO_INCREMENT ,
  CD_CLIENTE INTEGER NOT NULL ,
  CD_ENDERECO INTEGER NOT NULL ,
  QT_ITENS DECIMAL(10,0) NOT NULL ,
  VL_COMPRA DECIMAL(14,4) NOT NULL ,
  VL_DESCONTO DECIMAL(14,4) NOT NULL ,
  VL_TOTAL DECIMAL(14,4) NOT NULL ,
  DS_OBSERVACAO VARCHAR(200) NULL ,
  STATUS INTEGER NOT NULL ,
  CREATED TIMESTAMP NOT NULL DEFAULT now() ,
  PRIMARY KEY (CD_COMPRA)
);

/*************************************************************************************************************************************************************************************************************/

DROP TABLE IF EXISTS HL_COMPRA_ITEM ;
CREATE  TABLE IF NOT EXISTS HL_COMPRA_ITEM (
  CD_COMPRA INTEGER NOT NULL ,
  CD_PRODUTO INTEGER NOT NULL ,
  QT_PRODUTO DECIMAL(10,0) NOT NULL ,
  VL_UNITARIO DECIMAL(14,4) NOT NULL ,
  VL_DESCONTO DECIMAL(14,4) NOT NULL ,
  VL_TOTAL DECIMAL(14,4) NOT NULL ,
  CREATED TIMESTAMP NOT NULL DEFAULT now() ,
  PRIMARY KEY (CD_COMPRA, CD_PRODUTO)
);
