# Employee Management Desktop

Aplicação desktop para gerenciamento de funcionários, desenvolvida em Java 21, Swing, PostgreSQL e JDBC, utilizando princípios de Clean Architecture.

Projeto desenvolvido como teste prático para uma oportunidade de Programador Java.

## Funcionalidades

- Cadastro de usuários com nome, email e senha.
- Autenticação de usuários.
- Armazenamento de senhas utilizando hash SHA-256.
- Validação de dados e tratamento de erros.
- Cadastro de funcionários com nome, data de admissão, salário e status.
- Listagem de funcionários utilizando JTable.
- Persistência de dados em PostgreSQL.

## Tecnologias

- Java 21
- Java Swing (NetBeans GUI Builder)
- PostgreSQL
- JDBC
- Maven
- Docker Compose
- JUnit 5 e Mockito
- GitHub Actions

## Arquitetura

O projeto utiliza uma organização inspirada em Clean Architecture, separando responsabilidades em quatro camadas:

- **Domain:** entidades, regras de negócio e interfaces dos repositórios.
- **Application:** casos de uso de cadastro, autenticação e listagem.
- **Infrastructure:** implementação dos repositórios JDBC, conexão com PostgreSQL e hash de senhas.
- **Presentation:** interfaces gráficas construídas com Java Swing.

A comunicação entre as camadas utiliza interfaces e injeção de dependências.

## Como executar

### Pré-requisitos

- JDK 21
- Maven
- Docker e Docker Compose
- NetBeans (recomendado para editar as interfaces)

### 1. Clonar o repositório

```bash
git clone https://github.com/MayconVyctor/Employee-Management-Desktop.git
cd Employee-Management-Desktop/Employee-Management
```

### 2. Iniciar o banco de dados

```bash
docker compose up -d
```

O banco PostgreSQL será disponibilizado na porta `5432`.

### 3. Compilar e executar os testes

```bash
mvn clean test
```

### 4. Executar a aplicação

Abra o projeto Maven no NetBeans e execute a classe:

`com.employeecontrol.Main`

A aplicação abrirá a tela de login, com acesso ao cadastro de usuários e ao gerenciamento de funcionários.

## Testes

O projeto inclui testes unitários para os casos de uso e testes de integração para os repositórios JDBC.

## Autor

Maycon Vyctor Rodrigues Costa

GitHub: https://github.com/MayconVyctor
