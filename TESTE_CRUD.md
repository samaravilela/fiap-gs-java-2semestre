# Guia de Teste dos Endpoints CRUD

Este documento explica como testar os endpoints CREATE, UPDATE e DELETE que estão implementados no projeto.

## ✅ Endpoints Implementados

Todos os recursos (Usuarios, Cursos, Tutores, Mentorias, Oficinas) possuem os seguintes endpoints:

### CREATE (POST)
- **POST** `/api/{recurso}` - Cria um novo registro

### READ (GET)
- **GET** `/api/{recurso}` - Lista todos os registros
- **GET** `/api/{recurso}/{id}` - Busca por ID

### UPDATE (PUT)
- **PUT** `/api/{recurso}/{id}` - Atualiza um registro existente

### DELETE (DELETE)
- **DELETE** `/api/{recurso}/{id}` - Remove um registro

## 🔧 Como Testar

### 1. Usando cURL

#### Criar Usuário (CREATE)
```bash
curl -X POST http://localhost:8080/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva",
    "email": "joao@example.com",
    "senha": "123456"
  }'
```

#### Atualizar Usuário (UPDATE)
```bash
curl -X PUT http://localhost:8080/api/usuarios/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva Atualizado",
    "email": "joao.novo@example.com",
    "senha": "654321"
  }'
```

#### Deletar Usuário (DELETE)
```bash
curl -X DELETE http://localhost:8080/api/usuarios/1
```

### 2. Usando Postman/Insomnia

#### Criar Curso
- **Método:** POST
- **URL:** `http://localhost:8080/api/cursos`
- **Headers:** `Content-Type: application/json`
- **Body:**
```json
{
  "titulo": "Java Avançado",
  "descricao": "Curso completo de Java",
  "duracao": "40 horas",
  "formato": "Online",
  "preco": "299.90",
  "url": "https://example.com/curso-java",
  "ativo": "S"
}
```

#### Atualizar Curso
- **Método:** PUT
- **URL:** `http://localhost:8080/api/cursos/1`
- **Headers:** `Content-Type: application/json`
- **Body:**
```json
{
  "titulo": "Java Avançado - Atualizado",
  "descricao": "Curso completo de Java - Nova versão",
  "duracao": "50 horas",
  "formato": "Presencial",
  "preco": "399.90",
  "url": "https://example.com/curso-java-v2",
  "ativo": "S"
}
```

#### Deletar Curso
- **Método:** DELETE
- **URL:** `http://localhost:8080/api/cursos/1`

### 3. Verificar no Banco de Dados

Após executar os testes, você pode verificar diretamente no banco Oracle:

```sql
-- Verificar usuários criados
SELECT * FROM T_USUARIOS;

-- Verificar cursos criados
SELECT * FROM T_CURSOS;

-- Verificar tutores criados
SELECT * FROM T_TUTORES;

-- Verificar mentorias criadas
SELECT * FROM T_MENTORIAS;

-- Verificar oficinas criadas
SELECT * FROM T_OFICINAS;
```

## 📋 Exemplos de Teste para Cada Recurso

### Usuários
```bash
# CREATE
curl -X POST http://localhost:8080/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{"nome":"Teste","email":"teste@test.com","senha":"123456"}'

# UPDATE
curl -X PUT http://localhost:8080/api/usuarios/1 \
  -H "Content-Type: application/json" \
  -d '{"nome":"Teste Atualizado","email":"teste2@test.com","senha":"654321"}'

# DELETE
curl -X DELETE http://localhost:8080/api/usuarios/1
```

### Cursos
```bash
# CREATE
curl -X POST http://localhost:8080/api/cursos \
  -H "Content-Type: application/json" \
  -d '{"titulo":"Curso Teste","descricao":"Descrição","duracao":"10h","formato":"Online","preco":"100","url":"https://test.com","ativo":"S"}'

# UPDATE
curl -X PUT http://localhost:8080/api/cursos/1 \
  -H "Content-Type: application/json" \
  -d '{"titulo":"Curso Atualizado","descricao":"Nova descrição","duracao":"20h","formato":"Presencial","preco":"200","url":"https://test2.com","ativo":"S"}'

# DELETE
curl -X DELETE http://localhost:8080/api/cursos/1
```

### Tutores
```bash
# CREATE
curl -X POST http://localhost:8080/api/tutores \
  -H "Content-Type: application/json" \
  -d '{"nome":"Tutor Teste","especialidade":"Java","email":"tutor@test.com","telefone":"11999999999","ativo":"S"}'

# UPDATE
curl -X PUT http://localhost:8080/api/tutores/1 \
  -H "Content-Type: application/json" \
  -d '{"nome":"Tutor Atualizado","especialidade":"Python","email":"tutor2@test.com","telefone":"11888888888","ativo":"S"}'

# DELETE
curl -X DELETE http://localhost:8080/api/tutores/1
```

### Mentorias
```bash
# CREATE
curl -X POST http://localhost:8080/api/mentorias \
  -H "Content-Type: application/json" \
  -d '{"nomeCompleto":"Mentoria Teste","cpf":"12345678901","email":"mentoria@test.com","telefone":"11999999999","data":"2024-12-31"}'

# UPDATE
curl -X PUT http://localhost:8080/api/mentorias/1 \
  -H "Content-Type: application/json" \
  -d '{"nomeCompleto":"Mentoria Atualizada","cpf":"12345678901","email":"mentoria2@test.com","telefone":"11888888888","data":"2024-12-30"}'

# DELETE
curl -X DELETE http://localhost:8080/api/mentorias/1
```

### Oficinas
```bash
# CREATE
curl -X POST http://localhost:8080/api/oficinas \
  -H "Content-Type: application/json" \
  -d '{"nomeEmpreendimento":"Oficina Teste","cnpj":"12345678000190","nomeEmpresa":"Empresa Teste","localizacao":"São Paulo, SP","servicos":"Serviços diversos","especialidade":"Tecnologia"}'

# UPDATE
curl -X PUT http://localhost:8080/api/oficinas/1 \
  -H "Content-Type: application/json" \
  -d '{"nomeEmpreendimento":"Oficina Atualizada","cnpj":"12345678000190","nomeEmpresa":"Empresa Nova","localizacao":"Rio de Janeiro, RJ","servicos":"Novos serviços","especialidade":"Inovação"}'

# DELETE
curl -X DELETE http://localhost:8080/api/oficinas/1
```

## ⚠️ Observações Importantes

1. **Transações:** Todos os métodos CREATE, UPDATE e DELETE estão marcados com `@Transactional`, garantindo que as operações sejam commitadas automaticamente no banco de dados.

2. **Flush Explícito:** Adicionamos `entityManager.flush()` em todos os métodos de persistência para garantir que as mudanças sejam enviadas imediatamente ao banco.

3. **Validações:** Todos os endpoints possuem validações de dados antes de persistir.

4. **Respostas HTTP:**
   - **CREATE:** Retorna `201 CREATED` com o objeto criado
   - **UPDATE:** Retorna `200 OK` com o objeto atualizado
   - **DELETE:** Retorna `204 NO CONTENT` (sem corpo)
   - **Erro:** Retorna códigos apropriados (400, 404, 500) com mensagem de erro

5. **Verificação:** Após cada operação, você pode verificar no banco de dados Oracle se os dados foram persistidos corretamente.

## 🔍 Troubleshooting

Se os dados não estiverem sendo persistidos:

1. Verifique se a aplicação está rodando: `mvn quarkus:dev`
2. Verifique os logs da aplicação para erros
3. Verifique a conexão com o banco Oracle
4. Verifique se as tabelas existem no banco
5. Verifique os logs do Hibernate (SQL executado)

