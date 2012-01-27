-- USE herbalife
/*************************************************************************************************************************************************************************************************************/

DROP TABLE IF EXISTS GU_GROUP;
CREATE TABLE IF NOT EXISTS GU_GROUP (
    CD_GROUP INTEGER AUTO_INCREMENT,
    NM_GROUP VARCHAR( 60 ) NOT NULL,
    DS_GROUP VARCHAR( 200 ),
    CREATED TIMESTAMP NOT NULL DEFAULT now(),
    PRIMARY KEY ( CD_GROUP )
);

INSERT INTO GU_GROUP ( NM_GROUP, DS_GROUP ) VALUES
( 'Administradores', 'Grupo de Usuários Administradores do Sistema. (Acesso Total).' );

/*************************************************************************************************************************************************************************************************************/

DROP TABLE IF EXISTS GU_USER_STATUS;
CREATE TABLE IF NOT EXISTS GU_USER_STATUS (
    CD_STATUS INTEGER AUTO_INCREMENT,
    NM_STATUS VARCHAR( 60 ) NOT NULL,
    DS_STATUS VARCHAR( 200 ),
    CREATED TIMESTAMP NOT NULL DEFAULT now(),
    PRIMARY KEY ( CD_STATUS )
);

INSERT INTO GU_USER_STATUS ( NM_STATUS, DS_STATUS ) VALUES
( 'Ativo', 'Usuário Ativo' ),
( 'Inativo', 'Usuário Inativo' );

/*************************************************************************************************************************************************************************************************************/

DROP TABLE IF EXISTS GU_USER;
CREATE TABLE IF NOT EXISTS GU_USER (
    CD_USER       INTEGER AUTO_INCREMENT,
    CD_GROUP      INTEGER NOT NULL,
    NM_USER       VARCHAR( 60 ) NOT NULL,
    DS_EMAIL      VARCHAR( 100 ),
    DS_PASSWORD   VARCHAR( 50 ) NOT NULL,
    STATUS        INTEGER NOT NULL,
    CREATED       TIMESTAMP NOT NULL DEFAULT now(),
    PRIMARY KEY ( CD_USER ),
    UNIQUE ( DS_EMAIL )
);

SELECT  @CD_ADMIN_GROUP := CD_GROUP
FROM    GU_GROUP
WHERE   NM_GROUP = 'Administradores';

SELECT  @CD_USER_STATUS_ATIVO := CD_STATUS
FROM    GU_USER_STATUS
WHERE   NM_STATUS = 'Ativo';

INSERT INTO GU_USER ( CD_GROUP, NM_USER, DS_EMAIL, DS_PASSWORD, STATUS, CREATED ) VALUES
( @CD_ADMIN_GROUP, 'Reinaldo', 'reinaldooli@gmail.com', MD5('A123451'), @CD_USER_STATUS_ATIVO, now() );

/*************************************************************************************************************************************************************************************************************/

DROP TABLE IF EXISTS GU_GROUP_USER;
CREATE TABLE IF NOT EXISTS GU_GROUP_USER (
    CD_GROUP INTEGER NOT NULL,
    CD_USER INTEGER NOT NULL,
    CREATED TIMESTAMP NOT NULL DEFAULT now(),
    PRIMARY KEY ( CD_GROUP, CD_USER )
);

SELECT  @CD_ADMIN_USER := CD_USER
FROM    GU_USER
WHERE   DS_EMAIL = 'reinaldooli@gmail.com';

INSERT INTO GU_GROUP_USER ( CD_GROUP, CD_USER ) VALUES
( @CD_ADMIN_GROUP, @CD_ADMIN_USER );

/*************************************************************************************************************************************************************************************************************/

DROP TABLE IF EXISTS GU_MODULE_STATUS;
CREATE TABLE IF NOT EXISTS GU_MODULE_STATUS (
    CD_STATUS INTEGER AUTO_INCREMENT,
    NM_STATUS VARCHAR( 60 ) NOT NULL,
    DS_STATUS VARCHAR( 200 ),
    CREATED TIMESTAMP NOT NULL DEFAULT now(),
    PRIMARY KEY ( CD_STATUS )
);

INSERT INTO GU_MODULE_STATUS ( NM_STATUS, DS_STATUS ) VALUES
( 'Ativo', 'Módulo Ativo' ),
( 'Inativo', 'Módulo Inativo' );

/*************************************************************************************************************************************************************************************************************/

DROP TABLE IF EXISTS GU_MODULE;
CREATE TABLE IF NOT EXISTS GU_MODULE (
    CD_MODULE     INTEGER AUTO_INCREMENT,
    CD_PARENT     INTEGER,
    NM_MODULE     VARCHAR( 100 ) NOT NULL,
    DS_MODULE     VARCHAR( 300 ),
    STATUS        INTEGER NOT NULL,
    CREATED       TIMESTAMP NOT NULL DEFAULT now(),
    PRIMARY KEY ( CD_MODULE )
);

SELECT  @CD_ST_MODULO_ATIVO := CD_STATUS
FROM    GU_MODULE_STATUS
WHERE   NM_STATUS = 'Ativo';
INSERT INTO GU_MODULE ( CD_PARENT, NM_MODULE, DS_MODULE, STATUS ) VALUES
( NULL, 'Gestão de Usuários', 'Agrupamento de Módulos que compõem o Módulo de Gestão de Usuários.', @CD_ST_MODULO_ATIVO );

SELECT  @CD_MODULE_PARENT := CD_MODULE
FROM    GU_MODULE
WHERE   NM_MODULE = 'Gestão de Usuários';
INSERT INTO GU_MODULE ( CD_PARENT, NM_MODULE, DS_MODULE, STATUS ) VALUES 
( @CD_MODULE_PARENT, 'Usuários', 'Módulo para cadastro, edição e exclusão de Usuários.', @CD_ST_MODULO_ATIVO );
SELECT  @CD_MODULO_USUARIO := CD_MODULE
FROM    GU_MODULE
WHERE   NM_MODULE = 'Usuários';

INSERT INTO GU_MODULE ( CD_PARENT, NM_MODULE, DS_MODULE, STATUS ) VALUES
( @CD_MODULE_PARENT, 'Módulos', 'Módulo para cadastro, edição e exclusão de Módulos.', @CD_ST_MODULO_ATIVO );
SELECT  @CD_MODULO_MODULO := CD_MODULE
FROM    GU_MODULE
WHERE   NM_MODULE = 'Módulos';

INSERT INTO GU_MODULE ( CD_PARENT, NM_MODULE, DS_MODULE, STATUS ) VALUES
( @CD_MODULE_PARENT, 'Páginas', 'Módulo para cadastro, edição e exclusão de Páginas.', @CD_ST_MODULO_ATIVO );
SELECT  @CD_MODULO_PAGINA := CD_MODULE
FROM    GU_MODULE
WHERE   NM_MODULE = 'Páginas';

INSERT INTO GU_MODULE ( CD_PARENT, NM_MODULE, DS_MODULE, STATUS ) VALUES
( @CD_MODULE_PARENT, 'Grupos de Usuários', 'Módulo para cadastro, edição e exclusão de Grupos de Usuários', @CD_ST_MODULO_ATIVO );
SELECT  @CD_MODULO_GRUPO_USUARIO := CD_MODULE
FROM    GU_MODULE
WHERE   NM_MODULE = 'Grupos de Usuários';

INSERT INTO GU_MODULE ( CD_PARENT, NM_MODULE, DS_MODULE, STATUS ) VALUES
( @CD_MODULE_PARENT, 'Diretivas', 'Módulo para cadastro, edição e exclusão de Diretivas de Acesso.', @CD_ST_MODULO_ATIVO );
SELECT  @CD_MODULO_DIRETIVA := CD_MODULE
FROM    GU_MODULE
WHERE   NM_MODULE = 'Diretivas';


/*************************************************************************************************************************************************************************************************************/

DROP TABLE IF EXISTS GU_PAGE_STATUS;
CREATE TABLE IF NOT EXISTS GU_PAGE_STATUS (
    CD_STATUS INTEGER AUTO_INCREMENT,
    NM_STATUS VARCHAR( 60 ) NOT NULL,
    DS_STATUS VARCHAR( 200 ),
    CREATED TIMESTAMP NOT NULL DEFAULT now(),
    PRIMARY KEY ( CD_STATUS )
);

INSERT INTO GU_PAGE_STATUS ( NM_STATUS, DS_STATUS ) VALUES
( 'Ativa', 'Página Ativa' ),
( 'Inativa', 'Página Inativa' );

/*************************************************************************************************************************************************************************************************************/

DROP TABLE IF EXISTS GU_PAGE;
CREATE TABLE IF NOT EXISTS GU_PAGE (
    CD_PAGE   INTEGER AUTO_INCREMENT,
    CD_MODULE INTEGER NOT NULL,
    NM_PAGE   VARCHAR( 100 ) NOT NULL,
    DS_PAGE   VARCHAR( 300 ),
    DS_URL    VARCHAR( 100 ) NOT NULL,
    FG_MAIN   CHAR( 1 ),
    FG_SHOW_MENU CHAR( 1 ),
    STATUS    INTEGER,
    CREATED   TIMESTAMP NOT NULL DEFAULT now(),
    PRIMARY KEY ( CD_PAGE ),
    UNIQUE ( DS_URL )
);

SELECT  @CD_PAGE_STATUS_ATIVO := CD_STATUS
FROM    GU_PAGE_STATUS
WHERE   NM_STATUS = 'Ativa';
INSERT INTO GU_PAGE ( CD_MODULE, NM_PAGE, DS_PAGE, DS_URL, FG_MAIN, FG_SHOW_MENU, STATUS ) VALUES 
( @CD_MODULO_USUARIO, 'Cadastro de Usuários', 'Página de Cadastro de Usuários', '/admin/user/add_user.xhtml', 1, 1, @CD_PAGE_STATUS_ATIVO ),
( @CD_MODULO_MODULO, 'Cadastro de Módulos', 'Página de Cadastro de Módulos', '/admin/module/add_module.xhtml', 1, 1, @CD_PAGE_STATUS_ATIVO ),
( @CD_MODULO_PAGINA, 'Cadastro de Páginas', 'Página de Cadastro de Páginas', '/admin/page/add_page.xhtml', 1, 1, @CD_PAGE_STATUS_ATIVO ),
( @CD_MODULO_GRUPO_USUARIO, 'Cadastro de Grupos de Usuários', 'Página de Cadastro de Grupos de Usuários', '/admin/group/add_group.xhtml', 1, 1, @CD_PAGE_STATUS_ATIVO ),
( @CD_MODULO_DIRETIVA, 'Cadastro de Diretivas', 'Página de Cadastro de Diretivas', '/admin/policy/add_policy.xhtml', 1, 1, @CD_PAGE_STATUS_ATIVO );

/*************************************************************************************************************************************************************************************************************/

DROP TABLE IF EXISTS GU_ACCESS_PROFILE;
CREATE TABLE IF NOT EXISTS GU_ACCESS_PROFILE (
    CD_PAGE INTEGER NOT NULL,
    CD_GROUP INTEGER NOT NULL,
    CREATED TIMESTAMP NOT NULL DEFAULT now(),
    PRIMARY KEY ( CD_PAGE, CD_GROUP )
);

INSERT  INTO GU_ACCESS_PROFILE ( CD_PAGE, CD_GROUP )
SELECT  A.CD_PAGE, B.CD_GROUP
FROM    GU_PAGE A, GU_GROUP B
WHERE   B.NM_GROUP = 'Administradores';


/*************************************************************************************************************************************************************************************************************/

DROP TABLE IF EXISTS GU_POLICY;
CREATE TABLE IF NOT EXISTS GU_POLICY (
    CD_POLICY INTEGER AUTO_INCREMENT,
    NM_POLICY VARCHAR( 60 ) NOT NULL,
    DS_POLICY VARCHAR( 200 ),
    NM_FUNCTION VARCHAR( 60 ) NOT NULL,
    CREATED TIMESTAMP NOT NULL DEFAULT now(),
    PRIMARY KEY ( CD_POLICY ),
    UNIQUE ( NM_FUNCTION )
);

INSERT INTO GU_POLICY ( NM_POLICY, DS_POLICY, NM_FUNCTION ) VALUES
( 'Inserir Módulo', 'Inserir novos Módulos', 'insertModule' ),
( 'Alterar Módulo', 'Alterar as informações de um Módulo Cadastrado', 'editModule' ),
( 'Excluir Módulo', 'Excluir Módulos Cadastrados', 'deleteModule' ),
( 'Listar Módulos', 'Listar Módulos Cadastrados', 'listModule' ),
( 'Recuperar Módulo', 'Recuperar um Módulo específico', 'getModule' ),
( 'Inserir Página', 'Inserir novas Páginas', 'insertPage' ),
( 'Alterar Página', 'Alterar as informações de uma Página Cadastrada', 'editPage' ),
( 'Excluir Página', 'Excluir Páginas Cadastradas', 'deletePage' ),
( 'Listar Páginas', 'Listar Páginas Cadastradas', 'listPage' ),
( 'Recuperar Página', 'Recuperar uma Página específica', 'getPage' ),
( 'Inserir Diretiva', 'Inserir novas Diretivas', 'insertPolicy' ),
( 'Alterar Diretiva', 'Alterar as informações de uma Diretiva Cadastrada', 'editPolicy' ),
( 'Excluir Diretiva', 'Excluir Diretivas Cadastradas', 'deletePolicy' ),
( 'Listar Diretivas', 'Listar Diretivas Cadastradas', 'listPolicy' ),
( 'Recuperar Diretiva', 'Recuperar uma Diretiva específica', 'getPolicy' ),
( 'Inserir Grupo', 'Inserir novos Grupos', 'insertGroup' ),
( 'Alterar Grupo', 'Alterar as informações de um Grupo Cadastrado', 'editGroup' ),
( 'Excluir Grupo', 'Excluir Grupos Cadastrados', 'deleteGroup' ),
( 'Listar Grupos', 'Listar Grupos Cadastrados', 'listGroup' ),
( 'Recuperar Grupo', 'Recuperar um Grupo específico', 'getGroup' ),
( 'Inserir Usuário', 'Inserir novos Usuários', 'insertUser' ),
( 'Alterar Usuário', 'Alterar as informações de um Usuário Cadastrado', 'editUser' ),
( 'Excluir Usuário', 'Excluir Usuários Cadastrados', 'deleteUser' ),
( 'Listar Usuários', 'Listar Usuário Cadastrados', 'listUser' ),
( 'Recuperar Usuário', 'Recuperar um Usuário específico', 'getUser' ),
( 'Inserir Status de Página', 'Inserir novos Status de Página', 'insertPageStatus' ),
( 'Alterar Status de Página', 'Alterar as informações de um Status de Página Cadastrado', 'editPageStatus' ),
( 'Excluir Status de Página', 'Excluir Status de Página Cadastrado', 'deletePageStatus' ),
( 'Listar Status de Página', 'Listar Status de Página Cadastrado', 'listPageStatus' ),
( 'Recuperar Status de Página', 'Recuperar um Status de Página específico', 'getPageStatus' ),
( 'Inserir Status de Módulo', 'Inserir novos Status de Módulo', 'insertModuleStatus' ),
( 'Alterar Status de Módulo', 'Alterar as informações de um Status de Módulo Cadastrado', 'editModuleStatus' ),
( 'Excluir Status de Módulo', 'Excluir Status de Módulo Cadastrado', 'deleteModuleStatus' ),
( 'Listar Status de Módulo', 'Listar Status de Módulo Cadastrado', 'listModuleStatus' ),
( 'Recuperar Status de Módulo', 'Recuperar um Status de Módulo específico', 'getModuleStatus' ),
( 'Inserir Status do Usuário', 'Inserir novos Status de Usuário', 'insertUserStatus' ),
( 'Alterar Status do Usuário', 'Alterar as informações de um Status de Usuário Cadastrado', 'editUserStatus' ),
( 'Excluir Status do Usuário', 'Excluir Status de Usuários Cadastrados', 'deleteUserStatus' ),
( 'Listar Status dos Usuários', 'Listar Status de Usuário Cadastrados', 'listUserStatus' ),
( 'Recuperar Status do Usuário', 'Recuperar um Status de Usuário específico', 'getUserStatus' );


/*************************************************************************************************************************************************************************************************************/

DROP TABLE IF EXISTS GU_PERMISSION;
CREATE TABLE IF NOT EXISTS GU_PERMISSION (
    CD_POLICY INTEGER NOT NULL,
    CD_GROUP INTEGER NOT NULL,
    CREATED TIMESTAMP NOT NULL DEFAULT now(),
    PRIMARY KEY ( CD_POLICY, CD_GROUP )
);

INSERT  INTO GU_PERMISSION ( CD_POLICY, CD_GROUP )
SELECT  A.CD_POLICY, B.CD_GROUP
FROM    GU_POLICY A, GU_GROUP B
WHERE   B.NM_GROUP = 'Administradores';

/*************************************************************************************************************************************************************************************************************/