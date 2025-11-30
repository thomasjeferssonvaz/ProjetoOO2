###  ProjetoOO2

## Sistema de gerenciamento financeiro pessoal
O nosso sistema consiste de um espaço onde pode-se criar inúmeros usuários para uso simultâneo da mesma base de dados,
assim como diversas contas de bancos para cada usuário, 
permitindo assim uma ampla, 
diversa e fácil gestão das finanças pessoais até da família mais diversa.

## Observações
Módulo de Usuário, app principal(GUI - SERVICE - DAO) e Leia me criado por a2579847 - Thomas Jefersson Vaz
Modulo de Conta, Analitica,Lancamento, Investimento - Camadas (GUI - SERVICE - DAO) criada por a2715031 - Thiago de Lima Augustim

É possível ver o projeto completo e histórico de commits no [link](https://github.com/thomasjeferssonvaz/ProjetoOO2) 

## Instalação
### 1. Download e extração do app.

Para instalar o nosso sistema, primeiro deve-se clonar o repositório ou baixar o código base, 
por questões de segurança não disponibilizamos o código já buildado. Para fazer o download do código clique [aqui](https://github.com/thomasjeferssonvaz/ProjetoOO2/archive/refs/heads/main.zip).


### 2. Instalação e criação da base de dados.
Após baixados e extraídos os arquivos, 
deve-se criar sua base de dados no seu local de preferência, 
para fins de tutorial utilizaremos o localhost como base.

Temos suporte no momento somente para bases de dados MySql, 
para instalar a versão mais recente clique neste [link](https://dev.mysql.com/get/Downloads/MySQL-8.4/mysql-8.4.7-winx64.msi) que o download iniciará automaticamente.

Após devidamente instalado, deve-se conectar ao servidor pelo CLI ou pelo seu gerenciador de base de dados favorito.

Agora conectado a base de dados deve-se executar o código SQL abaixo, 
para criar sua base de dados padrão, 
necessária para o primeiro login com o usuário admin e senha admin, 
assim como demais funções do sistema.

_**P.S: Recomenda-se que após o primeiro login, 
um novo usuário Admin deve ser criado e o usuário padrão desativado.**_
### 2.1. Criação da base de dados.
```SQL
CREATE DATABASE projetooo2
USE projetooo2
       
CREATE TABLE usuario(
id_usuario int PRIMARY KEY AUTO_INCREMENT NOT NULL,
username varchar(255) NOT NULL UNIQUE,
senha varchar(255) NOT NULL,
nome varchar(255) NOT NULL,
dataNascimento date NOT NULL,
sexo varchar(255) NOT NULL,
tipo_usuario varchar(255) NOT NULL,
status varchar(255) NOT NULL
);

INSERT INTO usuario(username, senha, nome, dataNascimento, sexo, tipo_usuario, status) 
VALUES ("admin", "admin", "Admin", '2000-01-01', "Masculino" ,"Admin", "ativo");

CREATE TABLE conta (
    id_conta int NOT NULL PRIMARY KEY,
    nome_banco VARCHAR(30) NOT NULL,
    numero_conta INT NOT NULL UNIQUE,
    agencia INT NOT NULL,
    saldo DOUBLE NOT NULL,
    tipo_conta VARCHAR(20) NOT NULL,
    id_usuario INT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario)
);

create table analitica_financeira(
    id int unsigned auto_increment primary key,
    nome varchar(255) not null unique,
    categoria_tipo VARCHAR(15) not null,
    descricao VARCHAR(255) null,
    recorrencia VARCHAR(15) null,
    id_usuario int not null,
    FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario)
);

create table transacao
    (
        id bigint unsigned auto_increment primary key,
        numero_conta int not null,
        valor decimal(15, 2) not null,
        data_transacao timestamp default current_timestamp() not null,
        tipo varchar(20) not null,
        analitica varchar(50) not null,
        descricao varchar(255) null,
        id_usuario int not null,

        constraint transacao_conta_id_conta_fk
            foreign key (numero_conta) references conta(numero_conta),
        constraint transacao_conta_id_usuario_fk_
            foreign key (id_usuario) references usuario(id_usuario)
    );

create table investimento
(
    id int unsigned auto_increment primary key,
    nome varchar(255) not null,
    tipo varchar(30) not null,
    local varchar(30)   not null,
    aporte_mensal decimal(16,2) not null,   
    id_usuario int not null,
    
    constraint investimento_nome_idusuario_unico
        unique (nome, id_usuario),
    constraint investimento_usuario_id_fk
        foreign key (id_usuario) references usuario (id_usuario)
            on update cascade on delete cascade 
);
                                                                                           
```

### 3. Ajustes de configuração e ambiente.
Após criada a base deve-se alterar o código do /resources/config/database.properties. Colocando o seu dburl, 
assim como alterando o usuário e senha para os configurados no momento da instalação da base de dados.

### 4. Executando o App.
Após todos os ajustes você pode buildar e executar o app utilizando sua IDE favorita ou via CLI.
