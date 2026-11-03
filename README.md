# Employee Management Desktop

Sistema desktop de cadastro, autenticação e gerenciamento de funcionários, desenvolvido como parte do **Teste Prático – Vaga Programador**.

A aplicação foi construída utilizando **Java 21, Java Swing, PostgreSQL e JDBC**, com organização baseada nos princípios de **Clean Architecture**, separação de responsabilidades, injeção de dependências e testes automatizados.

## 1. Tecnologias utilizadas

| Tecnologia | Utilização |
|---|---|
| Java 21 | Linguagem principal |
| Java Swing | Interface gráfica desktop |
| Apache NetBeans | Desenvolvimento das interfaces com GUI Builder |
| PostgreSQL | Banco de dados relacional |
| JDBC | Comunicação com o banco utilizando SQL nativo |
| Maven | Gerenciamento de dependências e build |
| Docker Compose | Configuração do ambiente PostgreSQL |
| JUnit 5 | Testes automatizados |
| Mockito | Isolamento de dependências nos testes unitários |
| GitHub Actions | Integração contínua |

## 2. Funcionalidades e requisitos atendidos

### Cadastro de usuários

- Cadastro com nome, email e senha.
- Validação de campos obrigatórios.
- Verificação de email já cadastrado.
- Geração de hash SHA-256 antes do armazenamento da senha.
- Persistência dos dados no PostgreSQL.
- Mensagens de sucesso e erro na interface.

### Autenticação

- Login utilizando email e senha previamente cadastrados.
- Comparação do hash da senha informada com o hash armazenado.
- Tratamento de credenciais inválidas.
- Acesso à tela de gerenciamento de funcionários após autenticação bem-sucedida.

### Gerenciamento de funcionários

- Cadastro de funcionários com nome, data de admissão, salário e status.
- Representação do status como ativo ou inativo.
- Validação de campos obrigatórios, formato de data e salário positivo.
- Persistência dos funcionários no PostgreSQL.
- Exibição dos registros utilizando `JTable`.
- Atualização da listagem após o cadastro ou pelo botão **Refresh List**.

### Tratamento de erros

A aplicação apresenta mensagens ao usuário por meio de `JOptionPane`, tratando situações como:

- Credenciais inválidas.
- Email já cadastrado.
- Campos obrigatórios não preenchidos.
- Data ou salário inválidos.
- Falhas de conexão ou operações com o banco de dados.

## 3. Arquitetura do projeto

O projeto utiliza princípios de Clean Architecture para separar regras de negócio, casos de uso, implementações técnicas e interface gráfica.

```text
Employee-Management-Desktop/
├── .github/
│   └── workflows/
│       └── ci.yml
├── Employee-Management/
│   ├── docker-compose.yml
│   ├── init.sql
│   ├── pom.xml
│   └── src/
│       ├── main/java/com/employeecontrol/
│       │   ├── Main.java
│       │   ├── domain/
│       │   │   ├── entity/
│       │   │   │   ├── User.java
│       │   │   │   └── Employee.java
│       │   │   ├── repository/
│       │   │   │   ├── UserRepository.java
│       │   │   │   └── EmployeeRepository.java
│       │   │   └── exception/
│       │   ├── application/
│       │   │   ├── port/
│       │   │   │   └── PasswordHasher.java
│       │   │   └── usecase/
│       │   │       ├── RegisterUserUseCase.java
│       │   │       ├── LoginUserUseCase.java
│       │   │       ├── RegisterEmployeeUseCase.java
│       │   │       └── ListEmployeesUseCase.java
│       │   ├── infrastructure/
│       │   │   ├── database/
│       │   │   │   └── DatabaseConnection.java
│       │   │   ├── repository/
│       │   │   │   ├── UserRepositoryJdbc.java
│       │   │   │   └── EmployeeRepositoryJdbc.java
│       │   │   ├── security/
│       │   │   │   └── Sha256PasswordHasher.java
│       │   │   └── exception/
│       │   │       └── DatabaseException.java
│       │   └── presentation/
│       │       └── view/
│       │           ├── LoginFrame.java
│       │           ├── RegisterFrame.java
│       │           └── EmployeeFrame.java
│       └── test/java/com/employeecontrol/
├── .gitignore
└── README.md
```

As interfaces gráficas também possuem arquivos `.form`, utilizados pelo GUI Builder do NetBeans.

### Domain

Contém as entidades `User` e `Employee`, suas validações e os contratos de persistência.

As regras de negócio não dependem diretamente do PostgreSQL ou dos componentes Swing.

### Application

Contém os casos de uso responsáveis por coordenar as operações do sistema:

- `RegisterUserUseCase`: cadastro de usuários.
- `LoginUserUseCase`: autenticação.
- `RegisterEmployeeUseCase`: cadastro de funcionários.
- `ListEmployeesUseCase`: consulta dos funcionários cadastrados.

A interface `PasswordHasher` abstrai a operação de geração de hash.

### Infrastructure

Contém os detalhes técnicos da aplicação:

- Conexão com PostgreSQL.
- Implementações JDBC dos repositórios.
- Execução de comandos SQL nativos com `PreparedStatement`.
- Implementação de SHA-256.
- Tratamento de exceções de acesso ao banco.

### Presentation

Contém as interfaces gráficas desenvolvidas com Java Swing e NetBeans GUI Builder.

As telas recebem os casos de uso por injeção de dependências e não executam comandos SQL diretamente.

### Composition Root

A classe `Main.java` é responsável por inicializar a aplicação, criar as implementações concretas e conectar as dependências.

## 4. Banco de dados

O projeto utiliza PostgreSQL com duas tabelas principais.

### Tabela `users`

| Coluna | Tipo | Descrição |
|---|---|---|
| `id` | SERIAL | Identificador |
| `name` | VARCHAR | Nome do usuário |
| `email` | VARCHAR | Email único |
| `password_hash` | VARCHAR(64) | Hash SHA-256 |
| `created_at` | TIMESTAMPTZ | Data de criação |

### Tabela `employees`

| Coluna | Tipo | Descrição |
|---|---|---|
| `id` | SERIAL | Identificador |
| `name` | VARCHAR | Nome do funcionário |
| `admission_date` | DATE | Data de admissão |
| `salary` | DECIMAL(10,2) | Salário |
| `status` | BOOLEAN | Situação ativa ou inativa |
| `created_at` | TIMESTAMPTZ | Data de criação |

As tabelas são definidas por comandos SQL nativos no arquivo `init.sql`.

A persistência utiliza JDBC, sem ORM, com consultas e inserções implementadas diretamente nos repositórios.

## 5. Como executar

### Pré-requisitos

- JDK 21
- Apache Maven
- Docker e Docker Compose
- Apache NetBeans (recomendado)

### 5.1. Clonar o repositório

```bash
git clone https://github.com/MayconVyctor/Employee-Management-Desktop.git
cd Employee-Management-Desktop/Employee-Management
```

### 5.2. Iniciar o PostgreSQL

```bash
docker compose up -d
```

O Docker Compose configura o ambiente PostgreSQL utilizado pela aplicação.

O script `init.sql` define a estrutura das tabelas. Em uma inicialização nova do volume do PostgreSQL, ele deve ser aplicado conforme a configuração do Docker Compose.

### 5.3. Configuração da conexão

A classe `DatabaseConnection` utiliza as seguintes variáveis de ambiente:

| Variável | Valor padrão |
|---|---|
| `DB_URL` | `jdbc:postgresql://localhost:5432/employee_control` |
| `DB_USER` | `postgres` |
| `DB_PASSWORD` | `postgrespassword` |

Os valores podem ser substituídos por variáveis de ambiente do sistema operacional.

### 5.4. Executar os testes

```bash
mvn clean test
```

### 5.5. Iniciar a aplicação

Abra a pasta `Employee-Management` como projeto Maven no NetBeans.

Execute a classe principal:

```text
com.employeecontrol.Main
```

A aplicação abrirá a tela de login. Um novo usuário pode ser criado pelo botão **Register**.

Após a autenticação, será exibida a tela de gerenciamento de funcionários.

## 6. Testes automatizados

O projeto utiliza JUnit 5 e Mockito para verificar os comportamentos dos casos de uso e das implementações técnicas.

Os testes estão divididos entre:

**Testes unitários:** verificam os casos de uso e o hashing de senhas, isolando dependências quando necessário.

**Testes de integração:** verificam operações reais de persistência utilizando JDBC e PostgreSQL.

| Classe de teste | Quantidade |
|---|---:|
| `UserRepositoryJdbcTest` | 4 |
| `EmployeeRepositoryJdbcTest` | 3 |
| `Sha256PasswordHasherTest` | 2 |
| `RegisterUserUseCaseTest` | 3 |
| `LoginUserUseCaseTest` | 1 |
| `RegisterEmployeeUseCaseTest` | 1 |
| `ListEmployeesUseCaseTest` | 2 |
| **Total** | **16** |

Última execução local verificada:

```text
Tests run: 16, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

**Observação:** os testes de integração utilizam PostgreSQL e exigem que o banco esteja disponível. O isolamento entre o banco da aplicação e o banco de testes é uma melhoria planejada.

## 7. Decisões técnicas

### JDBC e SQL nativo

O uso de JDBC permite trabalhar diretamente com consultas SQL, gerenciamento de conexões e mapeamento dos resultados para as entidades Java, atendendo à proposta do desafio.

### Interfaces de repositório

Os casos de uso dependem de contratos, como `UserRepository` e `EmployeeRepository`, em vez das implementações JDBC.

Essa decisão reduz o acoplamento entre as regras da aplicação e os detalhes de persistência.

### Injeção de dependências

As dependências são fornecidas por construtores e organizadas na classe `Main.java`, sem necessidade de um framework de injeção de dependências.

### Hash de senhas

O algoritmo SHA-256 foi utilizado para atender ao requisito explícito do teste prático.

Em sistemas de produção, recomenda-se utilizar algoritmos específicos para armazenamento de senhas, como Argon2id ou bcrypt, com parâmetros apropriados de segurança.

### Interface com NetBeans GUI Builder

As telas foram desenvolvidas com os recursos visuais do NetBeans, utilizando arquivos `.form` para facilitar a manutenção do layout.

## 8. Melhorias futuras

- Isolar o banco de testes do banco de desenvolvimento.
- Implementar um `EmployeeTableModel` uma tabela de funcionarios mais especifica e mais trabalhada.
- Melhorar a validação de formato de email.
- Melhorar a apresentação de datas e valores monetários.
- Adicionar mais testes automatizados para mais cenários de erro.


Projeto desenvolvido para avaliação técnica em processo seletivo para a vaga de Programador Java.