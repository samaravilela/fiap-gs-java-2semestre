# Zyntra HE Backend

Backend da aplicação Zyntra HE desenvolvido com Quarkus, seguindo os padrões de arquitetura em camadas (Model, DAO, BO, Resource).

## Estrutura do Projeto

```
src/main/java/com/zyntrahe/
├── model/          # Entidades JPA (Model)
├── dao/            # Data Access Object (DAO)
├── bo/             # Business Object (BO)
├── resource/       # REST Resources (Controllers)
└── exception/      # Tratamento de exceções
```

## Tecnologias

- **Quarkus 3.6.0** - Framework Java
- **PostgreSQL** - Banco de dados
- **Hibernate ORM** - Persistência
- **RESTEasy Reactive** - API REST
- **Bean Validation** - Validações

## Requisitos

- Java 17+
- Maven 3.8+
- PostgreSQL 12+

## Configuração

1. Configure o banco de dados no arquivo `application.properties`:

```properties
quarkus.datasource.username=postgres
quarkus.datasource.password=postgres
quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/zyntra_he
```

2. Crie o banco de dados:

```sql
CREATE DATABASE zyntra_he;
```

## Executando a Aplicação

### Modo Desenvolvimento

```bash
./mvnw quarkus:dev
```

A aplicação estará disponível em: `https://fiap-gs-java-2semestre.onrender.com`

### Modo Produção

```bash
./mvnw clean package
java -jar target/quarkus-app/quarkus-run.jar
```

## Endpoints da API

### Usuários
- `POST /api/usuarios` - Criar usuário
- `POST /api/usuarios/login` - Autenticar
- `GET /api/usuarios/{id}` - Buscar por ID
- `PUT /api/usuarios/{id}` - Atualizar
- `DELETE /api/usuarios/{id}` - Remover

### Mentorias
- `POST /api/mentorias` - Criar mentoria
- `GET /api/mentorias` - Listar todas
- `GET /api/mentorias/{id}` - Buscar por ID
- `PUT /api/mentorias/{id}` - Atualizar
- `DELETE /api/mentorias/{id}` - Remover
- `POST /api/mentorias/{id}/cancelar` - Cancelar

### Oficinas
- `POST /api/oficinas` - Criar oficina
- `GET /api/oficinas` - Listar todas (com busca opcional)
- `GET /api/oficinas/{id}` - Buscar por ID
- `GET /api/oficinas/cidade/{cidade}` - Buscar por cidade
- `GET /api/oficinas/estado/{estado}` - Buscar por estado
- `PUT /api/oficinas/{id}` - Atualizar
- `POST /api/oficinas/{id}/aprovar` - Aprovar oficina
- `DELETE /api/oficinas/{id}` - Remover

### Tutores
- `POST /api/tutores` - Criar tutor
- `GET /api/tutores` - Listar todos (ativos opcional)
- `GET /api/tutores/{id}` - Buscar por ID
- `PUT /api/tutores/{id}` - Atualizar
- `DELETE /api/tutores/{id}` - Remover

### Cursos
- `POST /api/cursos` - Criar curso
- `GET /api/cursos` - Listar todos (ativos opcional)
- `GET /api/cursos/{id}` - Buscar por ID
- `PUT /api/cursos/{id}` - Atualizar
- `DELETE /api/cursos/{id}` - Remover

## Documentação da API

Acesse a documentação Swagger/OpenAPI em:
- `https://fiap-gs-java-2semestre.onrender.com/api-docs`

## Deploy

### Docker

```bash
docker build -t zyntra-he-backend .
docker run -p 8080:8080 -e DB_HOST=host.docker.internal zyntra-he-backend
```

### Render / Railway

1. Configure as variáveis de ambiente:
   - `DB_HOST` - Host do banco de dados
   - `DB_PORT` - Porta do banco (padrão: 5432)
   - `DB_NAME` - Nome do banco
   - `DB_USERNAME` - Usuário do banco
   - `DB_PASSWORD` - Senha do banco
   - `CORS_ORIGINS` - Origens permitidas para CORS

2. O Quarkus detecta automaticamente o ambiente e configura a aplicação.

## Padrões Implementados

- **DAO (Data Access Object)** - Separação de acesso a dados
- **BO (Business Object)** - Regras de negócio e validações
- **MVC** - Separação Model-View-Controller
- **Factory Pattern** - ConnectionFactory para gerenciamento de conexões
- **Exception Handling** - Tratamento centralizado de exceções
- **REST** - Princípios REST com verbos HTTP apropriados

## Estrutura de Camadas

### Model
Contém todas as entidades JPA que representam as tabelas do banco de dados.

### DAO
Responsável por todas as operações de banco de dados (CRUD).

### BO
Contém as regras de negócio, validações e lógica de negócio.

### Resource
Endpoints REST que expõem a API para o frontend.

## Licença

Este projeto é parte do trabalho acadêmico da FIAP.




