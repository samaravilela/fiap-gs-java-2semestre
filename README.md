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

### Aulas de Curso
- `POST /api/aulas-curso` - Criar aula
- `GET /api/aulas-curso` - Listar todas (cursoId, ativos opcional)
- `GET /api/aulas-curso/{id}` - Buscar por ID
- `PUT /api/aulas-curso/{id}` - Atualizar
- `DELETE /api/aulas-curso/{id}` - Remover

### Serviços de Oficinas
- `POST /api/oficina-servicos` - Criar serviço
- `GET /api/oficina-servicos` - Listar todos (oficinaId, ativos opcional)
- `GET /api/oficina-servicos/{id}` - Buscar por ID
- `PUT /api/oficina-servicos/{id}` - Atualizar
- `DELETE /api/oficina-servicos/{id}` - Remover

### Pontos de Recarga
- `POST /api/pontos-recarga` - Criar ponto de recarga
- `GET /api/pontos-recarga` - Listar todos (tipo, busca opcional)
- `GET /api/pontos-recarga/{id}` - Buscar por ID
- `PUT /api/pontos-recarga/{id}` - Atualizar
- `DELETE /api/pontos-recarga/{id}` - Remover

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
        -Long id
        -String email
        -String senha
        -String nome
        -LocalDateTime dataCriacao
        -LocalDateTime dataAtualizacao
    }
    
    class Mentoria {
        -Long id
        -String nomeCompleto
        -String cpf
        -String email
        -String telefone
        -LocalDate data
        -StatusMentoria status
        -Tutor tutor
        -Usuario usuario
    }
    
    class Tutor {
        -Long id
        -String nome
        -String especialidade
        -String email
        -String telefone
        -String ativo
    }
    
    class Oficina {
        -Long id
        -String nomeEmpreendimento
        -String cnpj
        -String localizacao
        -StatusOficina status
        -List~OficinaServico~ servicos
    }
    
    class Curso {
        -Long id
        -String titulo
        -String descricao
        -String preco
        -List~AulaCurso~ aulas
    }
    
    class AulaCurso {
        -Long id
        -String titulo
        -String descricao
        -String url
        -Curso curso
    }
    
    class OficinaServico {
        -Long id
        -String nome
        -String descricao
        -Oficina oficina
    }
    
    class PontoRecarga {
        -Long id
        -String nome
        -String endereco
        -TipoRecarga tipoRecarga
    }
    
    class UsuarioDAO {
        +criar(Usuario)
        +buscarPorId(Long) Usuario
        +buscarPorEmail(String) Usuario
        +listarTodos() List~Usuario~
        +atualizar(Usuario)
        +remover(Long)
    }
    
    class MentoriaDAO {
        +criar(Mentoria)
        +buscarPorId(Long) Mentoria
        +listarTodos() List~Mentoria~
        +buscarPorEmail(String) List~Mentoria~
        +atualizar(Mentoria)
        +remover(Long)
    }
    
    class TutorDAO {
        +criar(Tutor)
        +buscarPorId(Long) Tutor
        +listarTodos() List~Tutor~
        +listarAtivos() List~Tutor~
        +atualizar(Tutor)
        +remover(Long)
    }
    
    class OficinaDAO {
        +criar(Oficina)
        +buscarPorId(Long) Oficina
        +buscarPorCnpj(String) Oficina
        +listarTodos() List~Oficina~
        +buscarPorTermo(String) List~Oficina~
        +atualizar(Oficina)
        +remover(Long)
    }
    
    class CursoDAO {
        +criar(Curso)
        +buscarPorId(Long) Curso
        +listarTodos() List~Curso~
        +listarAtivos() List~Curso~
        +atualizar(Curso)
        +remover(Long)
    }
    
    class AulaCursoDAO {
        +criar(AulaCurso)
        +buscarPorId(Long) AulaCurso
        +listarTodos() List~AulaCurso~
        +buscarPorCursoId(Long) List~AulaCurso~
        +listarAtivas() List~AulaCurso~
        +atualizar(AulaCurso)
        +remover(Long)
    }
    
    class OficinaServicoDAO {
        +criar(OficinaServico)
        +buscarPorId(Long) OficinaServico
        +listarTodos() List~OficinaServico~
        +buscarPorOficinaId(Long) List~OficinaServico~
        +listarAtivos() List~OficinaServico~
        +atualizar(OficinaServico)
        +remover(Long)
    }
    
    class PontoRecargaDAO {
        +criar(PontoRecarga)
        +buscarPorId(Long) PontoRecarga
        +listarTodos() List~PontoRecarga~
        +buscarPorTipo(TipoRecarga) List~PontoRecarga~
        +buscarPorTermo(String) List~PontoRecarga~
        +atualizar(PontoRecarga)
        +remover(Long)
    }
    
    class UsuarioService {
        +criar(Usuario) Usuario
        +autenticar(String, String) Usuario
        +listarTodos() List~Usuario~
        +buscarPorId(Long) Usuario
        +atualizar(Usuario) boolean
        +deletar(Long) boolean
        -criptografarSenha(String) String
    }
    
    class MentoriaService {
        +criar(Mentoria) Mentoria
        +buscarPorId(Long) Mentoria
        +listarTodos() List~Mentoria~
        +buscarPorEmail(String) List~Mentoria~
        +atualizar(Mentoria) boolean
        +cancelar(Long) boolean
        +deletar(Long) boolean
    }
    
    class TutorService {
        +criar(Tutor) Tutor
        +buscarPorId(Long) Tutor
        +listarTodos() List~Tutor~
        +listarAtivos() List~Tutor~
        +atualizar(Tutor) boolean
        +deletar(Long) boolean
    }
    
    class OficinaService {
        +criar(Oficina) Oficina
        +buscarPorId(Long) Oficina
        +listarTodos() List~Oficina~
        +buscarPorTermo(String) List~Oficina~
        +atualizar(Oficina) boolean
        +aprovar(Long) boolean
        +deletar(Long) boolean
    }
    
    class CursoService {
        +criar(Curso) Curso
        +buscarPorId(Long) Curso
        +listarTodos() List~Curso~
        +listarAtivos() List~Curso~
        +atualizar(Curso) boolean
        +deletar(Long) boolean
    }
    
    class AulaCursoService {
        +criar(AulaCurso) AulaCurso
        +buscarPorId(Long) AulaCurso
        +listarTodos() List~AulaCurso~
        +buscarPorCursoId(Long) List~AulaCurso~
        +listarAtivas() List~AulaCurso~
        +atualizar(AulaCurso) boolean
        +deletar(Long) boolean
    }
    
    class OficinaServicoService {
        +criar(OficinaServico) OficinaServico
        +buscarPorId(Long) OficinaServico
        +listarTodos() List~OficinaServico~
        +buscarPorOficinaId(Long) List~OficinaServico~
        +listarAtivos() List~OficinaServico~
        +atualizar(OficinaServico) boolean
        +deletar(Long) boolean
    }
    
    class PontoRecargaService {
        +criar(PontoRecarga) PontoRecarga
        +buscarPorId(Long) PontoRecarga
        +listarTodos() List~PontoRecarga~
        +buscarPorTipo(TipoRecarga) List~PontoRecarga~
        +buscarPorTermo(String) List~PontoRecarga~
        +atualizar(PontoRecarga) boolean
        +deletar(Long) boolean
    }
    
    class UsuarioResource {
        +criar(Usuario) Response
        +login(Map) Response
        +listarTodos() Response
        +buscarPorId(Long) Response
        +atualizar(Long, Usuario) Response
        +deletar(Long) Response
    }
    
    class MentoriaResource {
        +criar(Mentoria) Response
        +listarTodos(String) Response
        +buscarPorId(Long) Response
        +atualizar(Long, Mentoria) Response
        +deletar(Long) Response
        +cancelar(Long) Response
    }
    
    class TutorResource {
        +criar(Tutor) Response
        +listarTodos(Boolean) Response
        +buscarPorId(Long) Response
        +atualizar(Long, Tutor) Response
        +deletar(Long) Response
    }
    
    class OficinaResource {
        +criar(Oficina) Response
        +listarTodos(String) Response
        +buscarPorId(Long) Response
        +buscarPorCidade(String) Response
        +buscarPorEstado(String) Response
        +atualizar(Long, Oficina) Response
        +aprovar(Long) Response
        +deletar(Long) Response
    }
    
    class CursoResource {
        +criar(Curso) Response
        +listarTodos(Boolean) Response
        +buscarPorId(Long) Response
        +atualizar(Long, Curso) Response
        +deletar(Long) Response
    }
    
    class AulaCursoResource {
        +criar(AulaCurso) Response
        +listarTodos(Long, Boolean) Response
        +buscarPorId(Long) Response
        +atualizar(Long, AulaCurso) Response
        +deletar(Long) Response
    }
    
    class OficinaServicoResource {
        +criar(OficinaServico) Response
        +listarTodos(Long, Boolean) Response
        +buscarPorId(Long) Response
        +atualizar(Long, OficinaServico) Response
        +deletar(Long) Response
    }
    
    class PontoRecargaResource {
        +criar(PontoRecarga) Response
        +listarTodos(String, String) Response
        +buscarPorId(Long) Response
        +atualizar(Long, PontoRecarga) Response
        +deletar(Long) Response
    }
    
    class BusinessRuleException {
        +BusinessRuleException(String)
    }
    
    class ValidationException {
        +ValidationException(String)
    }
    
    class ResourceNotFoundException {
        +ResourceNotFoundException(String)
    }
    
    class DatabaseException {
        +DatabaseException(String)
    }
    
    %% Relacionamentos entre Entidades
    Mentoria --> Tutor : ManyToOne
    Mentoria --> Usuario : ManyToOne
    AulaCurso --> Curso : ManyToOne
    OficinaServico --> Oficina : ManyToOne
    Curso --> AulaCurso : OneToMany
    Oficina --> OficinaServico : OneToMany
    
    %% Relacionamentos DAO -> Entity
    UsuarioDAO ..> Usuario : uses
    MentoriaDAO ..> Mentoria : uses
    TutorDAO ..> Tutor : uses
    OficinaDAO ..> Oficina : uses
    CursoDAO ..> Curso : uses
    AulaCursoDAO ..> AulaCurso : uses
    OficinaServicoDAO ..> OficinaServico : uses
    PontoRecargaDAO ..> PontoRecarga : uses
    
    %% Relacionamentos Service -> DAO
    UsuarioService --> UsuarioDAO : @Inject
    MentoriaService --> MentoriaDAO : @Inject
    MentoriaService --> UsuarioDAO : @Inject
    TutorService --> TutorDAO : @Inject
    OficinaService --> OficinaDAO : @Inject
    CursoService --> CursoDAO : @Inject
    AulaCursoService --> AulaCursoDAO : @Inject
    AulaCursoService --> CursoDAO : @Inject
    OficinaServicoService --> OficinaServicoDAO : @Inject
    OficinaServicoService --> OficinaDAO : @Inject
    PontoRecargaService --> PontoRecargaDAO : @Inject
    
    %% Relacionamentos Service -> Entity
    UsuarioService ..> Usuario : uses
    MentoriaService ..> Mentoria : uses
    MentoriaService ..> Usuario : uses
    TutorService ..> Tutor : uses
    OficinaService ..> Oficina : uses
    CursoService ..> Curso : uses
    AulaCursoService ..> AulaCurso : uses
    AulaCursoService ..> Curso : uses
    OficinaServicoService ..> OficinaServico : uses
    OficinaServicoService ..> Oficina : uses
    PontoRecargaService ..> PontoRecarga : uses
    
    %% Relacionamentos Resource -> Service
    UsuarioResource --> UsuarioService : @Inject
    MentoriaResource --> MentoriaService : @Inject
    TutorResource --> TutorService : @Inject
    OficinaResource --> OficinaService : @Inject
    CursoResource --> CursoService : @Inject
    AulaCursoResource --> AulaCursoService : @Inject
    OficinaServicoResource --> OficinaServicoService : @Inject
    PontoRecargaResource --> PontoRecargaService : @Inject
    
    %% Relacionamentos Service -> Exception
    UsuarioService ..> BusinessRuleException : throws
    UsuarioService ..> ValidationException : throws
    UsuarioService ..> ResourceNotFoundException : throws
    MentoriaService ..> BusinessRuleException : throws
    MentoriaService ..> ValidationException : throws
    MentoriaService ..> ResourceNotFoundException : throws
    TutorService ..> ValidationException : throws
    TutorService ..> ResourceNotFoundException : throws
    OficinaService ..> BusinessRuleException : throws
    OficinaService ..> ValidationException : throws
    OficinaService ..> ResourceNotFoundException : throws
    CursoService ..> ValidationException : throws
    CursoService ..> ResourceNotFoundException : throws
    AulaCursoService ..> ValidationException : throws
    AulaCursoService ..> ResourceNotFoundException : throws
    OficinaServicoService ..> ValidationException : throws
    OficinaServicoService ..> ResourceNotFoundException : throws
    PontoRecargaService ..> ValidationException : throws
    PontoRecargaService ..> ResourceNotFoundException : throws
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




