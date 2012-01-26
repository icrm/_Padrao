-- USE AC
DECLARE
-- USUARIOS
@DEFAULT_USER                       VARCHAR( 60 ),
@DEFAULT_EMAIL                      VARCHAR( 100 ),
@DEFAULT_PASSWORD                   VARCHAR( 50 ),
@CD_ADMIN_GROUP                     NUMERIC( 10, 0 ),
@CD_USER_STATUS_ATIVO               NUMERIC( 10, 0 ),
@CD_USER                            NUMERIC( 10, 0 ),
@CD_MODULE_STATUS_ATIVO             NUMERIC( 10, 0 ),
@CD_MODULE_PARENT                   NUMERIC( 10, 0 ),
@CD_PAGE_STATUS_ATIVO               NUMERIC( 10, 0 ),
@CD_MODULO_USUARIO                  NUMERIC( 10, 0 ),
@CD_MODULO_MODULO                   NUMERIC( 10, 0 ),
@CD_MODULO_PAGINA                   NUMERIC( 10, 0 ),
@CD_MODULO_GRUPO_USUARIO            NUMERIC( 10, 0 ),
@CD_MODULO_DIRETIVA                 NUMERIC( 10, 0 )

-- CONFIGURACAO DE USUARIOS
SET @DEFAULT_USER       = 'crmadm';
SET @DEFAULT_EMAIL      = 'ticrm@accmm.com.br';
SET @DEFAULT_PASSWORD   = 'c$2mAc)iXy689';

/*************************************************************************************************************************************************************************************************************/

-- DROP TABLE [dbo].[GU_GROUP]
CREATE TABLE [dbo].[GU_GROUP] (
    [CD_GROUP] [NUMERIC]( 10, 0 ) IDENTITY( 1, 1 ),
    [NM_GROUP] [VARCHAR]( 60 ) NOT NULL,
    [DS_GROUP] [VARCHAR]( 200 ),
    [CREATED] [DATETIME] NOT NULL DEFAULT GETDATE(),
    CONSTRAINT [PK_GROUP] PRIMARY KEY ( [CD_GROUP] )
);

INSERT INTO [dbo].[GU_GROUP] ( [NM_GROUP], [DS_GROUP] ) VALUES ( 'Administradores', 'Grupo de Usu�rios Administradores do Sistema. (Acesso Total).' );
SELECT @CD_ADMIN_GROUP = @@IDENTITY

/*************************************************************************************************************************************************************************************************************/

-- DROP TABLE [dbo].[GU_USER_STATUS]
CREATE TABLE [dbo].[GU_USER_STATUS] (
    [CD_STATUS] [NUMERIC]( 10, 0 ) IDENTITY( 1, 1 ),
    [NM_STATUS] [VARCHAR]( 60 ) NOT NULL,
    [DS_STATUS] [VARCHAR]( 200 ),
    [CREATED] [DATETIME] NOT NULL DEFAULT GETDATE(),
    CONSTRAINT [PK_USER_STATUS] PRIMARY KEY ( [CD_STATUS] )
);

INSERT INTO [dbo].[GU_USER_STATUS] ( [NM_STATUS], [DS_STATUS] ) VALUES ( 'Ativo', 'Usu�rio Ativo' );
SELECT @CD_USER_STATUS_ATIVO = @@IDENTITY;
INSERT INTO [dbo].[GU_USER_STATUS] ( [NM_STATUS], [DS_STATUS] ) VALUES ( 'Inativo', 'Usu�rio Inativo' );

/*************************************************************************************************************************************************************************************************************/

-- DROP TABLE [dbo].[GU_USER]
CREATE TABLE [dbo].[GU_USER] (
    [CD_USER]       [NUMERIC]( 10, 0 ) IDENTITY( 1, 1 ),
    [CD_GROUP]      [NUMERIC]( 10, 0 ) NOT NULL,
    [NM_USER]       [VARCHAR]( 60 ) NOT NULL,
    [DS_EMAIL]      [VARCHAR]( 100 ),
    [DS_PASSWORD]   [VARCHAR]( 50 ) NOT NULL,
    [STATUS]        [NUMERIC]( 10, 0 ),
    [CREATED]       [DATETIME] NOT NULL DEFAULT GETDATE(),
    CONSTRAINT [PK_USER] PRIMARY KEY ( [CD_USER] ),
    CONSTRAINT [UK_USER_EMAIL] UNIQUE ( [DS_EMAIL] ),
    CONSTRAINT [FK_USER_STATUS] FOREIGN KEY ( [STATUS] ) REFERENCES [dbo].[GU_USER_STATUS] ( [CD_STATUS] ),
    CONSTRAINT [FK_USER_GROUPS]  FOREIGN KEY ( [CD_GROUP] ) REFERENCES [dbo].[GU_GROUP]( [CD_GROUP] )
);

INSERT INTO [dbo].[GU_USER] ( [CD_GROUP], [NM_USER], [DS_EMAIL], [DS_PASSWORD], [STATUS] ) VALUES ( @CD_ADMIN_GROUP, @DEFAULT_USER, @DEFAULT_EMAIL, SUBSTRING( MASTER.DBO.FN_VARBINTOHEXSTR( HASHBYTES( 'MD5', @DEFAULT_PASSWORD ) ), 3, 32), @CD_USER_STATUS_ATIVO );
SELECT @CD_USER = @@IDENTITY;

/*************************************************************************************************************************************************************************************************************/

-- DROP TABLE [dbo].[GU_GROUP_USER]
CREATE TABLE [dbo].[GU_GROUP_USER] (
    [CD_GROUP] [NUMERIC]( 10, 0 ) NOT NULL,
    [CD_USER] [NUMERIC]( 10, 0 ) NOT NULL,
    [CREATED] [DATETIME] NOT NULL DEFAULT GETDATE(),
    CONSTRAINT [PK_GROUP_USER] PRIMARY KEY ( [CD_GROUP], [CD_USER] ),
    CONSTRAINT [FK_GROUP_USER] FOREIGN KEY ( [CD_GROUP] ) REFERENCES [dbo].[GU_GROUP]( [CD_GROUP] ),
    CONSTRAINT [FK_USER_GROUP] FOREIGN KEY ( [CD_USER] ) REFERENCES [dbo].[GU_USER]( [CD_USER] )
);

INSERT INTO [dbo].[GU_GROUP_USER] ( [CD_GROUP], [CD_USER] ) VALUES ( @CD_ADMIN_GROUP, @CD_USER );

/*************************************************************************************************************************************************************************************************************/

-- DROP TABLE [dbo].[GU_MODULE_STATUS]
CREATE TABLE [dbo].[GU_MODULE_STATUS] (
    [CD_STATUS] [NUMERIC]( 10, 0 ) IDENTITY( 1, 1 ),
    [NM_STATUS] [VARCHAR]( 60 ) NOT NULL,
    [DS_STATUS] [VARCHAR]( 200 ),
    [CREATED] [DATETIME] NOT NULL DEFAULT GETDATE(),
    CONSTRAINT [PK_MODULE_STATUS] PRIMARY KEY ( [CD_STATUS] )
);

INSERT INTO [dbo].[GU_MODULE_STATUS] ( [NM_STATUS], [DS_STATUS] ) VALUES ( 'Ativo', 'M�dulo Ativo' );
SELECT @CD_MODULE_STATUS_ATIVO = @@IDENTITY
INSERT INTO [dbo].[GU_MODULE_STATUS] ( [NM_STATUS], [DS_STATUS] ) VALUES ( 'Inativo', 'M�dulo Inativo' );

/*************************************************************************************************************************************************************************************************************/

-- DROP TABLE [dbo].[GU_MODULE]
CREATE TABLE [dbo].[GU_MODULE] (
    [CD_MODULE]     [NUMERIC]( 10, 0 ) IDENTITY( 1, 1 ),
    [CD_PARENT]     [NUMERIC]( 10, 0 ),
    [NM_MODULE]     [VARCHAR]( 100 ) NOT NULL,
    [DS_MODULE]     [VARCHAR]( 300 ),
    [STATUS]        [NUMERIC]( 10, 0 ) NOT NULL,
    [CREATED]       [DATETIME] NOT NULL DEFAULT GETDATE(),
    CONSTRAINT [PK_MODULE] PRIMARY KEY ( [CD_MODULE] ),
    CONSTRAINT [FK_MODULE_PARENT] FOREIGN KEY ( [CD_PARENT] ) REFERENCES [dbo].[GU_MODULE] ( [CD_MODULE] ),
    CONSTRAINT [FK_MODULE_STATUS] FOREIGN KEY ( [STATUS] ) REFERENCES [dbo].[GU_MODULE_STATUS] ( [CD_STATUS] )
);

-- MODULO DE GESTAO DE USUARIOS
INSERT INTO [dbo].[GU_MODULE] ( [CD_PARENT], [NM_MODULE], [DS_MODULE], [STATUS] ) VALUES ( NULL, 'Gest�o de Usu�rios', 'Agrupamento de M�dulos que comp�em o M�dulo de Gest�o de Usu�rios.', @CD_MODULE_STATUS_ATIVO );
SELECT @CD_MODULE_PARENT = @@IDENTITY
INSERT INTO [dbo].[GU_MODULE] ( [CD_PARENT], [NM_MODULE], [DS_MODULE], [STATUS] ) VALUES ( @CD_MODULE_PARENT, 'Usu�rios', 'M�dulo para cadastro, edi��o e exclus�o de Usu�rios.', @CD_MODULE_STATUS_ATIVO );
SELECT @CD_MODULO_USUARIO = @@IDENTITY
INSERT INTO [dbo].[GU_MODULE] ( [CD_PARENT], [NM_MODULE], [DS_MODULE], [STATUS] ) VALUES ( @CD_MODULE_PARENT, 'M�dulos', 'M�dulo para cadastro, edi��o e exclus�o de M�dulos.', @CD_MODULE_STATUS_ATIVO );
SELECT @CD_MODULO_MODULO = @@IDENTITY
INSERT INTO [dbo].[GU_MODULE] ( [CD_PARENT], [NM_MODULE], [DS_MODULE], [STATUS] ) VALUES ( @CD_MODULE_PARENT, 'P�ginas', 'M�dulo para cadastro, edi��o e exclus�o de P�ginas.', @CD_MODULE_STATUS_ATIVO );
SELECT @CD_MODULO_PAGINA = @@IDENTITY
INSERT INTO [dbo].[GU_MODULE] ( [CD_PARENT], [NM_MODULE], [DS_MODULE], [STATUS] ) VALUES ( @CD_MODULE_PARENT, 'Grupos de Usu�rios', 'M�dulo para cadastro, edi��o e exclus�o de Grupos de Usu�rios', @CD_MODULE_STATUS_ATIVO );
SELECT @CD_MODULO_GRUPO_USUARIO = @@IDENTITY
INSERT INTO [dbo].[GU_MODULE] ( [CD_PARENT], [NM_MODULE], [DS_MODULE], [STATUS] ) VALUES ( @CD_MODULE_PARENT, 'Diretivas', 'M�dulo para cadastro, edi��o e exclus�o de Diretivas de Acesso.', @CD_MODULE_STATUS_ATIVO );
SELECT @CD_MODULO_DIRETIVA = @@IDENTITY

/*************************************************************************************************************************************************************************************************************/

-- DROP TABLE [dbo].[GU_PAGE_STATUS]
CREATE TABLE [dbo].[GU_PAGE_STATUS] (
    [CD_STATUS] [NUMERIC]( 10, 0 ) IDENTITY( 1, 1 ),
    [NM_STATUS] [VARCHAR]( 60 ) NOT NULL,
    [DS_STATUS] [VARCHAR]( 200 ),
    [CREATED] [DATETIME] NOT NULL DEFAULT GETDATE(),
    CONSTRAINT [PK_PAGE_STATUS] PRIMARY KEY ( [CD_STATUS] )
);

INSERT INTO [dbo].[GU_PAGE_STATUS] ( [NM_STATUS], [DS_STATUS] ) VALUES ( 'Ativa', 'P�gina Ativa' );
SELECT @CD_PAGE_STATUS_ATIVO = @@IDENTITY
INSERT INTO [dbo].[GU_PAGE_STATUS] ( [NM_STATUS], [DS_STATUS] ) VALUES ( 'Inativa', 'P�gina Inativa' );

/*************************************************************************************************************************************************************************************************************/

-- DROP TABLE [dbo].[GU_PAGE]
CREATE TABLE [dbo].[GU_PAGE] (
    [CD_PAGE]   [NUMERIC]( 10, 0 ) IDENTITY( 1 ,1 ),
    [CD_MODULE] [NUMERIC]( 10, 0 ) NOT NULL,
    [NM_PAGE]   [VARCHAR]( 100 ) NOT NULL,
    [DS_PAGE]   [VARCHAR]( 300 ),
    [DS_URL]    [VARCHAR]( 100 ) NOT NULL,
    [FG_MAIN]   [CHAR]( 1 ),
    [STATUS]    [NUMERIC]( 10, 0 ),
    [CREATED]   [DATETIME] NOT NULL DEFAULT GETDATE(),
    CONSTRAINT [PK_PAGE] PRIMARY KEY ( [CD_PAGE] ),
    CONSTRAINT [UK_PAGE_URL] UNIQUE ( [DS_URL] ),
    CONSTRAINT [FK_PAGE_MODULE] FOREIGN KEY ( [CD_MODULE] ) REFERENCES [dbo].[GU_MODULE]( [CD_MODULE] ),
    CONSTRAINT [FK_PAGE_STATUS] FOREIGN KEY ( [STATUS] ) REFERENCES [dbo].[GU_PAGE_STATUS] ( [CD_STATUS] )
);

INSERT INTO [dbo].[GU_PAGE] ( [CD_MODULE], [NM_PAGE], [DS_PAGE], [DS_URL], [FG_MAIN], [STATUS] ) VALUES ( @CD_MODULO_USUARIO, 'Cadastro de Usu�rios', 'P�gina de Cadastro de Usu�rios', '/admin/user/add_user.xhtml', 1,  @CD_PAGE_STATUS_ATIVO );
INSERT INTO [dbo].[GU_PAGE] ( [CD_MODULE], [NM_PAGE], [DS_PAGE], [DS_URL], [FG_MAIN], [STATUS] ) VALUES ( @CD_MODULO_MODULO, 'Cadastro de M�dulos', 'P�gina de Cadastro de M�dulos', '/admin/module/add_module.xhtml', 1,  @CD_PAGE_STATUS_ATIVO );
INSERT INTO [dbo].[GU_PAGE] ( [CD_MODULE], [NM_PAGE], [DS_PAGE], [DS_URL], [FG_MAIN], [STATUS] ) VALUES ( @CD_MODULO_PAGINA, 'Cadastro de P�ginas', 'P�gina de Cadastro de P�ginas', '/admin/page/add_page.xhtml', 1,  @CD_PAGE_STATUS_ATIVO );
INSERT INTO [dbo].[GU_PAGE] ( [CD_MODULE], [NM_PAGE], [DS_PAGE], [DS_URL], [FG_MAIN], [STATUS] ) VALUES ( @CD_MODULO_GRUPO_USUARIO, 'Cadastro de Grupos de Usu�rios', 'P�gina de Cadastro de Grupos de Usu�rios', '/admin/group/add_group.xhtml', 1,  @CD_PAGE_STATUS_ATIVO );
INSERT INTO [dbo].[GU_PAGE] ( [CD_MODULE], [NM_PAGE], [DS_PAGE], [DS_URL], [FG_MAIN], [STATUS] ) VALUES ( @CD_MODULO_DIRETIVA, 'Cadastro de Diretivas', 'P�gina de Cadastro de Diretivas', '/admin/policy/add_policy.xhtml', 1,  @CD_PAGE_STATUS_ATIVO );

/*************************************************************************************************************************************************************************************************************/

-- DROP TABLE [dbo].[GU_ACCESS_PROFILE]
CREATE TABLE [dbo].[GU_ACCESS_PROFILE] (
    [CD_PAGE] [NUMERIC]( 10, 0 ) NOT NULL,
    [CD_GROUP] [NUMERIC]( 10, 0 ) NOT NULL,
    [CREATED] [DATETIME] NOT NULL DEFAULT GETDATE(),
    CONSTRAINT [PK_ACCESS_PROFILE] PRIMARY KEY ( [CD_PAGE], [CD_GROUP] ),
    CONSTRAINT [FK_ACCESS_PROFILE_PAGE] FOREIGN KEY ( [CD_PAGE] ) REFERENCES [dbo].[GU_PAGE] ( [CD_PAGE] ),
    CONSTRAINT [FK_ACCESS_PROFILE_GROUP] FOREIGN KEY ( [CD_GROUP] ) REFERENCES [dbo].[GU_GROUP] ( [CD_GROUP] )
);

INSERT  INTO [dbo].[GU_ACCESS_PROFILE] ( [CD_PAGE],[CD_GROUP] )
SELECT  A.[CD_PAGE], B.[CD_GROUP]
FROM    [dbo].[GU_PAGE] A, [dbo].[GU_GROUP] B
WHERE   B.[CD_GROUP] = @CD_ADMIN_GROUP

/*************************************************************************************************************************************************************************************************************/

-- DROP TABLE [dbo].[GU_POLICY]
CREATE TABLE [dbo].[GU_POLICY] (
    [CD_POLICY] [NUMERIC]( 10, 0 ) IDENTITY( 1, 1 ),
    [NM_POLICY] [VARCHAR]( 60 ) NOT NULL,
    [DS_POLICY] [VARCHAR]( 200 ),
    [NM_FUNCTION] [VARCHAR]( 60 ) NOT NULL,
    [CREATED] [DATETIME] NOT NULL DEFAULT GETDATE(),
    CONSTRAINT [PK_POLICY] PRIMARY KEY ( [CD_POLICY] ),
    CONSTRAINT [UK_POLICY_FUNCTION] UNIQUE ( [NM_FUNCTION] )
);

INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Inserir M�dulo', 'Inserir novos M�dulos', 'insertModule' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Alterar M�dulo', 'Alterar as informa��es de um M�dulo Cadastrado', 'editModule' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Excluir M�dulo', 'Excluir M�dulos Cadastrados', 'deleteModule' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Listar M�dulos', 'Listar M�dulos Cadastrados', 'listModule' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Recuperar M�dulo', 'Recuperar um M�dulo espec�fico', 'getModule' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Inserir P�gina', 'Inserir novas P�ginas', 'insertPage' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Alterar P�gina', 'Alterar as informa��es de uma P�gina Cadastrada', 'editPage' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Excluir P�gina', 'Excluir P�ginas Cadastradas', 'deletePage' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Listar P�ginas', 'Listar P�ginas Cadastradas', 'listPage' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Recuperar P�gina', 'Recuperar uma P�gina espec�fica', 'getPage' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Inserir Diretiva', 'Inserir novas Diretivas', 'insertPolicy' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Alterar Diretiva', 'Alterar as informa��es de uma Diretiva Cadastrada', 'editPolicy' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Excluir Diretiva', 'Excluir Diretivas Cadastradas', 'deletePolicy' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Listar Diretivas', 'Listar Diretivas Cadastradas', 'listPolicy' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Recuperar Diretiva', 'Recuperar uma Diretiva espec�fica', 'getPolicy' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Inserir Grupo', 'Inserir novos Grupos', 'insertGroup' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Alterar Grupo', 'Alterar as informa��es de um Grupo Cadastrado', 'editGroup' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Excluir Grupo', 'Excluir Grupos Cadastrados', 'deleteGroup' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Listar Grupos', 'Listar Grupos Cadastrados', 'listGroup' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Recuperar Grupo', 'Recuperar um Grupo espec�fico', 'getGroup' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Inserir Usu�rio', 'Inserir novos Usu�rios', 'insertUser' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Alterar Usu�rio', 'Alterar as informa��es de um Usu�rio Cadastrado', 'editUser' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Excluir Usu�rio', 'Excluir Usu�rios Cadastrados', 'deleteUser' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Listar Usu�rios', 'Listar Usu�rio Cadastrados', 'listUser' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Recuperar Usu�rio', 'Recuperar um Usu�rio espec�fico', 'getUser' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Inserir Status de P�gina', 'Inserir novos Status de P�gina', 'insertPageStatus' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Alterar Status de P�gina', 'Alterar as informa��es de um Status de P�gina Cadastrado', 'editPageStatus' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Excluir Status de P�gina', 'Excluir Status de P�gina Cadastrado', 'deletePageStatus' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Listar Status de P�gina', 'Listar Status de P�gina Cadastrado', 'listPageStatus' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Recuperar Status de P�gina', 'Recuperar um Status de P�gina espec�fico', 'getPageStatus' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Inserir Status de M�dulo', 'Inserir novos Status de M�dulo', 'insertModuleStatus' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Alterar Status de M�dulo', 'Alterar as informa��es de um Status de M�dulo Cadastrado', 'editModuleStatus' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Excluir Status de M�dulo', 'Excluir Status de M�dulo Cadastrado', 'deleteModuleStatus' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Listar Status de M�dulo', 'Listar Status de M�dulo Cadastrado', 'listModuleStatus' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Recuperar Status de M�dulo', 'Recuperar um Status de M�dulo espec�fico', 'getModuleStatus' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Inserir Status do Usu�rio', 'Inserir novos Status de Usu�rio', 'insertUserStatus' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Alterar Status do Usu�rio', 'Alterar as informa��es de um Status de Usu�rio Cadastrado', 'editUserStatus' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Excluir Status do Usu�rio', 'Excluir Status de Usu�rios Cadastrados', 'deleteUserStatus' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Listar Status dos Usu�rios', 'Listar Status de Usu�rio Cadastrados', 'listUserStatus' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Recuperar Status do Usu�rio', 'Recuperar um Status de Usu�rio espec�fico', 'getUserStatus' );


/*************************************************************************************************************************************************************************************************************/

-- DROP TABLE [dbo].[GU_PERMISSION]
CREATE TABLE [dbo].[GU_PERMISSION] (
    [CD_POLICY] [NUMERIC]( 10, 0 ) NOT NULL,
    [CD_GROUP] [NUMERIC]( 10, 0 ) NOT NULL,
    [CREATED] [DATETIME] NOT NULL DEFAULT GETDATE(),
    CONSTRAINT [PK_PERMISSION] PRIMARY KEY ( [CD_POLICY], [CD_GROUP] ),
    CONSTRAINT [FK_PERMISSION_POLICY] FOREIGN KEY ( [CD_POLICY] ) REFERENCES [dbo].[GU_POLICY]( [CD_POLICY] ),
    CONSTRAINT [FK_PERMISSION_GROUP] FOREIGN KEY ( [CD_GROUP] ) REFERENCES [dbo].[GU_GROUP]( [CD_GROUP] )
);

INSERT  INTO [dbo].[GU_PERMISSION] ( [CD_POLICY], [CD_GROUP] )
SELECT  A.[CD_POLICY], B.[CD_GROUP]
FROM    [dbo].[GU_POLICY] A, [dbo].[GU_GROUP] B
WHERE   B.[CD_GROUP] = @CD_ADMIN_GROUP


/*
DROP TABLE [dbo].[GU_PERMISSION]
DROP TABLE [dbo].[GU_POLICY]
DROP TABLE [dbo].[GU_ACCESS_PROFILE]
DROP TABLE [dbo].[GU_PAGE]
DROP TABLE [dbo].[GU_PAGE_STATUS]
DROP TABLE [dbo].[GU_MODULE]
DROP TABLE [dbo].[GU_MODULE_STATUS]
DROP TABLE [dbo].[GU_GROUP_USER]
DROP TABLE [dbo].[GU_USER]
DROP TABLE [dbo].[GU_USER_STATUS]
DROP TABLE [dbo].[GU_GROUP]
*/
