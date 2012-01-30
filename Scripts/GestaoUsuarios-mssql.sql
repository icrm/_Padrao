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

INSERT INTO [dbo].[GU_GROUP] ( [NM_GROUP], [DS_GROUP] ) VALUES ( 'Administradores', 'Grupo de Usuários Administradores do Sistema. (Acesso Total).' );
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

INSERT INTO [dbo].[GU_USER_STATUS] ( [NM_STATUS], [DS_STATUS] ) VALUES ( 'Ativo', 'Usuário Ativo' );
SELECT @CD_USER_STATUS_ATIVO = @@IDENTITY;
INSERT INTO [dbo].[GU_USER_STATUS] ( [NM_STATUS], [DS_STATUS] ) VALUES ( 'Inativo', 'Usuário Inativo' );

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

INSERT INTO [dbo].[GU_MODULE_STATUS] ( [NM_STATUS], [DS_STATUS] ) VALUES ( 'Ativo', 'Módulo Ativo' );
SELECT @CD_MODULE_STATUS_ATIVO = @@IDENTITY
INSERT INTO [dbo].[GU_MODULE_STATUS] ( [NM_STATUS], [DS_STATUS] ) VALUES ( 'Inativo', 'Módulo Inativo' );

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
INSERT INTO [dbo].[GU_MODULE] ( [CD_PARENT], [NM_MODULE], [DS_MODULE], [STATUS] ) VALUES ( NULL, 'Gestão de Usuários', 'Agrupamento de Módulos que compõem o Módulo de Gestão de Usuários.', @CD_MODULE_STATUS_ATIVO );
SELECT @CD_MODULE_PARENT = @@IDENTITY
INSERT INTO [dbo].[GU_MODULE] ( [CD_PARENT], [NM_MODULE], [DS_MODULE], [STATUS] ) VALUES ( @CD_MODULE_PARENT, 'Usuários', 'Módulo para cadastro, edição e exclusão de Usuários.', @CD_MODULE_STATUS_ATIVO );
SELECT @CD_MODULO_USUARIO = @@IDENTITY
INSERT INTO [dbo].[GU_MODULE] ( [CD_PARENT], [NM_MODULE], [DS_MODULE], [STATUS] ) VALUES ( @CD_MODULE_PARENT, 'Módulos', 'Módulo para cadastro, edição e exclusão de Módulos.', @CD_MODULE_STATUS_ATIVO );
SELECT @CD_MODULO_MODULO = @@IDENTITY
INSERT INTO [dbo].[GU_MODULE] ( [CD_PARENT], [NM_MODULE], [DS_MODULE], [STATUS] ) VALUES ( @CD_MODULE_PARENT, 'Páginas', 'Módulo para cadastro, edição e exclusão de Páginas.', @CD_MODULE_STATUS_ATIVO );
SELECT @CD_MODULO_PAGINA = @@IDENTITY
INSERT INTO [dbo].[GU_MODULE] ( [CD_PARENT], [NM_MODULE], [DS_MODULE], [STATUS] ) VALUES ( @CD_MODULE_PARENT, 'Grupos de Usuários', 'Módulo para cadastro, edição e exclusão de Grupos de Usuários', @CD_MODULE_STATUS_ATIVO );
SELECT @CD_MODULO_GRUPO_USUARIO = @@IDENTITY
INSERT INTO [dbo].[GU_MODULE] ( [CD_PARENT], [NM_MODULE], [DS_MODULE], [STATUS] ) VALUES ( @CD_MODULE_PARENT, 'Diretivas', 'Módulo para cadastro, edição e exclusão de Diretivas de Acesso.', @CD_MODULE_STATUS_ATIVO );
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

INSERT INTO [dbo].[GU_PAGE_STATUS] ( [NM_STATUS], [DS_STATUS] ) VALUES ( 'Ativa', 'Página Ativa' );
SELECT @CD_PAGE_STATUS_ATIVO = @@IDENTITY
INSERT INTO [dbo].[GU_PAGE_STATUS] ( [NM_STATUS], [DS_STATUS] ) VALUES ( 'Inativa', 'Página Inativa' );

/*************************************************************************************************************************************************************************************************************/

-- DROP TABLE [dbo].[GU_PAGE]
CREATE TABLE [dbo].[GU_PAGE] (
    [CD_PAGE]   [NUMERIC]( 10, 0 ) IDENTITY( 1 ,1 ),
    [CD_MODULE] [NUMERIC]( 10, 0 ) NOT NULL,
    [NM_PAGE]   [VARCHAR]( 100 ) NOT NULL,
    [DS_PAGE]   [VARCHAR]( 300 ),
    [DS_URL]    [VARCHAR]( 100 ) NOT NULL,
    [FG_MAIN]   [CHAR]( 1 ),
    [FG_SHOW_MENU] [CHAR]( 1 ),
    [STATUS]    [NUMERIC]( 10, 0 ),
    [CREATED]   [DATETIME] NOT NULL DEFAULT GETDATE(),
    CONSTRAINT [PK_PAGE] PRIMARY KEY ( [CD_PAGE] ),
    CONSTRAINT [UK_PAGE_URL] UNIQUE ( [DS_URL] ),
    CONSTRAINT [FK_PAGE_MODULE] FOREIGN KEY ( [CD_MODULE] ) REFERENCES [dbo].[GU_MODULE]( [CD_MODULE] ),
    CONSTRAINT [FK_PAGE_STATUS] FOREIGN KEY ( [STATUS] ) REFERENCES [dbo].[GU_PAGE_STATUS] ( [CD_STATUS] )
);

INSERT INTO [dbo].[GU_PAGE] ( [CD_MODULE], [NM_PAGE], [DS_PAGE], [DS_URL], [FG_MAIN], [STATUS] ) VALUES ( @CD_MODULO_USUARIO, 'Cadastro de Usuários', 'Página de Cadastro de Usuários', '/admin/user/add_user.xhtml', 1, 1, @CD_PAGE_STATUS_ATIVO );
INSERT INTO [dbo].[GU_PAGE] ( [CD_MODULE], [NM_PAGE], [DS_PAGE], [DS_URL], [FG_MAIN], [STATUS] ) VALUES ( @CD_MODULO_MODULO, 'Cadastro de Módulos', 'Página de Cadastro de Módulos', '/admin/module/add_module.xhtml', 1, 1, @CD_PAGE_STATUS_ATIVO );
INSERT INTO [dbo].[GU_PAGE] ( [CD_MODULE], [NM_PAGE], [DS_PAGE], [DS_URL], [FG_MAIN], [STATUS] ) VALUES ( @CD_MODULO_PAGINA, 'Cadastro de Páginas', 'Página de Cadastro de Páginas', '/admin/page/add_page.xhtml', 1, 1, @CD_PAGE_STATUS_ATIVO );
INSERT INTO [dbo].[GU_PAGE] ( [CD_MODULE], [NM_PAGE], [DS_PAGE], [DS_URL], [FG_MAIN], [STATUS] ) VALUES ( @CD_MODULO_GRUPO_USUARIO, 'Cadastro de Grupos de Usuários', 'Página de Cadastro de Grupos de Usuários', '/admin/group/add_group.xhtml', 1, 1, @CD_PAGE_STATUS_ATIVO );
INSERT INTO [dbo].[GU_PAGE] ( [CD_MODULE], [NM_PAGE], [DS_PAGE], [DS_URL], [FG_MAIN], [STATUS] ) VALUES ( @CD_MODULO_DIRETIVA, 'Cadastro de Diretivas', 'Página de Cadastro de Diretivas', '/admin/policy/add_policy.xhtml', 1, 1, @CD_PAGE_STATUS_ATIVO );

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

INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Inserir Módulo', 'Inserir novos Módulos', 'insertModule' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Alterar Módulo', 'Alterar as informações de um Módulo Cadastrado', 'editModule' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Excluir Módulo', 'Excluir Módulos Cadastrados', 'deleteModule' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Listar Módulos', 'Listar Módulos Cadastrados', 'listModule' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Recuperar Módulo', 'Recuperar um Módulo específico', 'getModule' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Inserir Página', 'Inserir novas Páginas', 'insertPage' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Alterar Página', 'Alterar as informações de uma Página Cadastrada', 'editPage' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Excluir Página', 'Excluir Páginas Cadastradas', 'deletePage' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Listar Páginas', 'Listar Páginas Cadastradas', 'listPage' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Recuperar Página', 'Recuperar uma Página específica', 'getPage' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Inserir Diretiva', 'Inserir novas Diretivas', 'insertPolicy' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Alterar Diretiva', 'Alterar as informações de uma Diretiva Cadastrada', 'editPolicy' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Excluir Diretiva', 'Excluir Diretivas Cadastradas', 'deletePolicy' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Listar Diretivas', 'Listar Diretivas Cadastradas', 'listPolicy' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Recuperar Diretiva', 'Recuperar uma Diretiva específica', 'getPolicy' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Inserir Grupo', 'Inserir novos Grupos', 'insertGroup' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Alterar Grupo', 'Alterar as informações de um Grupo Cadastrado', 'editGroup' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Excluir Grupo', 'Excluir Grupos Cadastrados', 'deleteGroup' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Listar Grupos', 'Listar Grupos Cadastrados', 'listGroup' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Recuperar Grupo', 'Recuperar um Grupo específico', 'getGroup' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Inserir Usuário', 'Inserir novos Usuários', 'insertUser' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Alterar Usuário', 'Alterar as informações de um Usuário Cadastrado', 'editUser' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Excluir Usuário', 'Excluir Usuários Cadastrados', 'deleteUser' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Listar Usuários', 'Listar Usuário Cadastrados', 'listUser' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Recuperar Usuário', 'Recuperar um Usuário específico', 'getUser' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Inserir Status de Página', 'Inserir novos Status de Página', 'insertPageStatus' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Alterar Status de Página', 'Alterar as informações de um Status de Página Cadastrado', 'editPageStatus' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Excluir Status de Página', 'Excluir Status de Página Cadastrado', 'deletePageStatus' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Listar Status de Página', 'Listar Status de Página Cadastrado', 'listPageStatus' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Recuperar Status de Página', 'Recuperar um Status de Página específico', 'getPageStatus' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Inserir Status de Módulo', 'Inserir novos Status de Módulo', 'insertModuleStatus' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Alterar Status de Módulo', 'Alterar as informações de um Status de Módulo Cadastrado', 'editModuleStatus' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Excluir Status de Módulo', 'Excluir Status de Módulo Cadastrado', 'deleteModuleStatus' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Listar Status de Módulo', 'Listar Status de Módulo Cadastrado', 'listModuleStatus' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Recuperar Status de Módulo', 'Recuperar um Status de Módulo específico', 'getModuleStatus' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Inserir Status do Usuário', 'Inserir novos Status de Usuário', 'insertUserStatus' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Alterar Status do Usuário', 'Alterar as informações de um Status de Usuário Cadastrado', 'editUserStatus' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Excluir Status do Usuário', 'Excluir Status de Usuários Cadastrados', 'deleteUserStatus' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Listar Status dos Usuários', 'Listar Status de Usuário Cadastrados', 'listUserStatus' );
INSERT INTO [dbo].[GU_POLICY] ( [NM_POLICY], [DS_POLICY], [NM_FUNCTION] ) VALUES ( 'Recuperar Status do Usuário', 'Recuperar um Status de Usuário específico', 'getUserStatus' );


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
