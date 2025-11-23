# Zyntra HE Backend

Backend da aplicação Zyntra HE desenvolvido com Quarkus, seguindo os padrões de arquitetura em camadas (Model, DAO, BO, Resource).

## Estrutura do Projeto

```
src/main/java/br/com/fiap/
├── model/
│   ├── entity/     # Entidades JPA (Model)
│   └── dao/         # Data Access Object (DAO)
├── service/          # Business Object (Service/BO)
├── resource/        # REST Resources (Controllers)
└── exception/       # Tratamento de exceções
```

## Tecnologias

- **Quarkus 3.6.0** - Framework Java
- **Oracle Database** - Banco de dados
- **Hibernate ORM** - Persistência
- **RESTEasy Reactive** - API REST
- **Bean Validation** - Validações

## Requisitos

- Java 17+
- Maven 3.8+
- Oracle Database 12c+

## Configuração

1. Configure o banco de dados no arquivo `application.properties`:

```properties
quarkus.datasource.username=rm566133
quarkus.datasource.password=280201
quarkus.datasource.jdbc.url=jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL
```

2. Execute os scripts SQL na ordem:
   - Primeiro: `zyntra_he_completo.sql` (cria as tabelas)
   - Depois: `zyntra_he_dados_exemplo.sql` (popula com dados de exemplo)

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

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| **Usuários** |
| `POST` | `/api/usuarios` | Criar usuário |
| `POST` | `/api/usuarios/login` | Autenticar usuário |
| `GET` | `/api/usuarios/{id}` | Buscar usuário por ID |
| `PUT` | `/api/usuarios/{id}` | Atualizar usuário |
| `DELETE` | `/api/usuarios/{id}` | Remover usuário |
| **Mentorias** |
| `POST` | `/api/mentorias` | Criar mentoria |
| `GET` | `/api/mentorias` | Listar todas as mentorias |
| `GET` | `/api/mentorias/{id}` | Buscar mentoria por ID |
| `PUT` | `/api/mentorias/{id}` | Atualizar mentoria |
| `DELETE` | `/api/mentorias/{id}` | Remover mentoria |
| `POST` | `/api/mentorias/{id}/cancelar` | Cancelar mentoria |
| **Oficinas** |
| `POST` | `/api/oficinas` | Criar oficina |
| `GET` | `/api/oficinas` | Listar todas as oficinas (busca opcional) |
| `GET` | `/api/oficinas/{id}` | Buscar oficina por ID |
| `GET` | `/api/oficinas/cidade/{cidade}` | Buscar oficinas por cidade |
| `GET` | `/api/oficinas/estado/{estado}` | Buscar oficinas por estado |
| `PUT` | `/api/oficinas/{id}` | Atualizar oficina |
| `POST` | `/api/oficinas/{id}/aprovar` | Aprovar oficina |
| `DELETE` | `/api/oficinas/{id}` | Remover oficina |
| **Tutores** |
| `POST` | `/api/tutores` | Criar tutor |
| `GET` | `/api/tutores` | Listar todos os tutores (ativos opcional) |
| `GET` | `/api/tutores/{id}` | Buscar tutor por ID |
| `PUT` | `/api/tutores/{id}` | Atualizar tutor |
| `DELETE` | `/api/tutores/{id}` | Remover tutor |
| **Cursos** |
| `POST` | `/api/cursos` | Criar curso |
| `GET` | `/api/cursos` | Listar todos os cursos (ativos opcional) |
| `GET` | `/api/cursos/{id}` | Buscar curso por ID |
| `PUT` | `/api/cursos/{id}` | Atualizar curso |
| `DELETE` | `/api/cursos/{id}` | Remover curso |
| **Aulas de Curso** |
| `POST` | `/api/aulas-curso` | Criar aula |
| `GET` | `/api/aulas-curso` | Listar todas as aulas (cursoId, ativos opcional) |
| `GET` | `/api/aulas-curso/{id}` | Buscar aula por ID |
| `PUT` | `/api/aulas-curso/{id}` | Atualizar aula |
| `DELETE` | `/api/aulas-curso/{id}` | Remover aula |
| **Serviços de Oficinas** |
| `POST` | `/api/oficina-servicos` | Criar serviço |
| `GET` | `/api/oficina-servicos` | Listar todos os serviços (oficinaId, ativos opcional) |
| `GET` | `/api/oficina-servicos/{id}` | Buscar serviço por ID |
| `PUT` | `/api/oficina-servicos/{id}` | Atualizar serviço |
| `DELETE` | `/api/oficina-servicos/{id}` | Remover serviço |
| **Pontos de Recarga** |
| `POST` | `/api/pontos-recarga` | Criar ponto de recarga |
| `GET` | `/api/pontos-recarga` | Listar todos os pontos (tipo, busca opcional) |
| `GET` | `/api/pontos-recarga/{id}` | Buscar ponto por ID |
| `PUT` | `/api/pontos-recarga/{id}` | Atualizar ponto de recarga |
| `DELETE` | `/api/pontos-recarga/{id}` | Remover ponto de recarga |

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
   - `QUARKUS_DATASOURCE_USERNAME` - Usuário do banco Oracle
   - `QUARKUS_DATASOURCE_PASSWORD` - Senha do banco Oracle
   - `QUARKUS_DATASOURCE_JDBC_URL` - URL de conexão JDBC Oracle
   - `CORS_ORIGINS` - Origens permitidas para CORS

2. O Quarkus detecta automaticamente o ambiente e configura a aplicação.

**Exemplo de URL JDBC Oracle:**
```
jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL
```

## Padrões Implementados

- **DAO (Data Access Object)** - Separação de acesso a dados
- **Service Layer** - Regras de negócio e validações
- **MVC** - Separação Model-View-Controller
- **JPA/Hibernate** - Mapeamento objeto-relacional
- **Exception Handling** - Tratamento centralizado de exceções (BusinessRuleException, ValidationException, ResourceNotFoundException)
- **REST** - Princípios REST com verbos HTTP apropriados
- **Bean Validation** - Validações de dados com anotações
- **Dependency Injection** - Injeção de dependências com CDI (@Inject)

## Diagrama de Classes

```mermaid
classDiagram
    class Usuario {
        Long id
        String email
        String senha
        String nome
        LocalDateTime dataCriacao
        LocalDateTime dataAtualizacao
    }
    
    class Tutor {
        Long id
        String nome
        String especialidade
        String email
        String telefone
        String ativo
        LocalDateTime dataCriacao
        LocalDateTime dataAtualizacao
    }
    
    class Curso {
        Long id
        String titulo
        String descricao
        String ativo
        LocalDateTime dataCriacao
        LocalDateTime dataAtualizacao
        List~AulaCurso~ aulas
    }
    
    class AulaCurso {
        Long id
        String titulo
        String descricao
        String url
        String ativo
        LocalDateTime dataCriacao
        LocalDateTime dataAtualizacao
        Curso curso
    }
    
    class Oficina {
        Long id
        String nomeEmpreendimento
        String cnpj
        String nomeEmpresa
        String localizacao
        String cidade
        String estado
        String especialidade
        Double avaliacao
        StatusOficina status
        LocalDateTime dataCriacao
        LocalDateTime dataAtualizacao
        List~OficinaServico~ servicos
    }
    
    class OficinaServico {
        Long id
        String nome
        String descricao
        String ativo
        LocalDateTime dataCriacao
        LocalDateTime dataAtualizacao
        Oficina oficina
    }
    
    class Mentoria {
        Long id
        String nomeCompleto
        String cpf
        String email
        String telefone
        LocalDate data
        StatusMentoria status
        LocalDateTime dataCriacao
        Tutor tutor
        Usuario usuario
    }
    
    class PontoRecarga {
        Long id
        String nome
        String endereco
        TipoRecarga tipoRecarga
        LocalDateTime dataCriacao
        LocalDateTime dataAtualizacao
    }
    
    Usuario --> Mentoria
    Tutor --> Mentoria
    Curso --> AulaCurso
    Oficina --> OficinaServico
```

## Estrutura de Camadas

### Model (Entity)
Contém todas as entidades JPA que representam as tabelas do banco de dados:
- **Usuario** - Usuários do sistema
- **Tutor** - Tutores/mentores disponíveis
- **Curso** - Cursos oferecidos
- **AulaCurso** - Aulas que compõem os cursos (com URL de vídeo)
- **Oficina** - Oficinas cadastradas
- **OficinaServico** - Serviços oferecidos pelas oficinas
- **Mentoria** - Agendamentos de mentorias
- **PontoRecarga** - Pontos de recarga para veículos elétricos/híbridos

### DAO
Responsável por todas as operações de banco de dados (CRUD):
- **UsuarioDAO**, **TutorDAO**, **CursoDAO**, **AulaCursoDAO**
- **OficinaDAO**, **OficinaServicoDAO**, **MentoriaDAO**, **PontoRecargaDAO**

### Service (BO)
Contém as regras de negócio, validações e lógica de negócio:
- Validações de dados
- Regras de negócio (ex: email único, CNPJ único, limite de mentorias)
- Criptografia de senhas (SHA-256)
- Tratamento de exceções personalizadas

### Resource
Endpoints REST que expõem a API para o frontend:
- Seguem padrão RESTful
- Tratamento de erros padronizado
- Suporte a filtros e query parameters

## Estrutura do Banco de Dados

### Tabelas Principais
- **T_ZYNT_USUARIOS** - Usuários do sistema
- **T_ZYNT_TUTORES** - Tutores disponíveis
- **T_ZYNT_CURSOS** - Cursos oferecidos
- **T_ZYNT_AULAS_CURSO** - Aulas dos cursos (com URL de vídeo YouTube)
- **T_ZYNT_OFICINAS** - Oficinas cadastradas
- **T_ZYNT_OFICINA_SERVICOS** - Serviços oferecidos pelas oficinas
- **T_ZYNT_MENTORIAS** - Agendamentos de mentorias
- **T_ZYNT_PONTOS_RECARGA** - Pontos de recarga (nome, endereço, tipo)

### Relacionamentos
- **Mentoria** → **Tutor** (ManyToOne)
- **Mentoria** → **Usuario** (ManyToOne)
- **AulaCurso** → **Curso** (ManyToOne)
- **OficinaServico** → **Oficina** (ManyToOne)

## Licença

Este projeto é parte do trabalho acadêmico da FIAP.




