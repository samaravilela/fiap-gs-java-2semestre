# Guia de Teste dos Endpoints CRUD

Este documento explica como testar os endpoints CREATE, UPDATE e DELETE que estão implementados no projeto.

## ✅ Endpoints Implementados

Todos os recursos possuem os seguintes endpoints:

### Recursos Disponíveis
- **Usuarios** - `/api/usuarios`
- **Tutores** - `/api/tutores`
- **Cursos** - `/api/cursos`
- **Aulas de Curso** - `/api/aulas-curso`
- **Oficinas** - `/api/oficinas` (apenas CREATE e READ)
- **Serviços de Oficinas** - `/api/oficina-servicos`
- **Mentorias** - `/api/mentorias`
- **Pontos de Recarga** - `/api/pontos-recarga`

### CREATE (POST)
- **POST** `/api/{recurso}` - Cria um novo registro

### READ (GET)
- **GET** `/api/{recurso}` - Lista todos os registros (com filtros opcionais)
- **GET** `/api/{recurso}/{id}` - Busca por ID

### UPDATE (PUT)
- **PUT** `/api/{recurso}/{id}` - Atualiza um registro existente
- ⚠️ **Oficinas:** UPDATE não disponível

### DELETE (DELETE)
- **DELETE** `/api/{recurso}/{id}` - Remove um registro
- ⚠️ **Oficinas:** DELETE não disponível

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
  "titulo": "Curso Completo de Baterias VHE",
  "descricao": "Aprenda tudo sobre baterias de veículos híbridos e elétricos",
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
  "titulo": "Curso Completo de Baterias VHE - Atualizado",
  "descricao": "Aprenda tudo sobre baterias de veículos híbridos e elétricos - Nova versão",
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
SELECT * FROM T_ZYNT_USUARIOS;

-- Verificar cursos criados
SELECT * FROM T_ZYNT_CURSOS;

-- Verificar aulas de curso
SELECT * FROM T_ZYNT_AULAS_CURSO;

-- Verificar tutores criados
SELECT * FROM T_ZYNT_TUTORES;

-- Verificar mentorias criadas
SELECT * FROM T_ZYNT_MENTORIAS;

-- Verificar oficinas criadas
SELECT * FROM T_ZYNT_OFICINAS;

-- Verificar serviços de oficinas
SELECT * FROM T_ZYNT_OFICINA_SERVICOS;

-- Verificar pontos de recarga
SELECT * FROM T_ZYNT_PONTOS_RECARGA;
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
  -d '{"titulo":"Curso Teste","descricao":"Descrição do curso","ativo":"S"}'

# UPDATE
curl -X PUT http://localhost:8080/api/cursos/1 \
  -H "Content-Type: application/json" \
  -d '{"titulo":"Curso Atualizado","descricao":"Nova descrição","ativo":"S"}'

# DELETE
curl -X DELETE http://localhost:8080/api/cursos/1
```

### Aulas de Curso
```bash
# CREATE (requer cursoId)
curl -X POST http://localhost:8080/api/aulas-curso \
  -H "Content-Type: application/json" \
  -d '{"cursoId":1,"titulo":"Aula Teste","descricao":"Descrição da aula","url":"https://www.youtube.com/watch?v=exemplo","ativo":"S"}'

# UPDATE
curl -X PUT http://localhost:8080/api/aulas-curso/1 \
  -H "Content-Type: application/json" \
  -d '{"titulo":"Aula Atualizada","descricao":"Nova descrição","url":"https://www.youtube.com/watch?v=novo","ativo":"S"}'

# DELETE
curl -X DELETE http://localhost:8080/api/aulas-curso/1

# LISTAR por curso
curl -X GET "http://localhost:8080/api/aulas-curso?cursoId=1"
```

### Tutores
```bash
# CREATE
curl -X POST http://localhost:8080/api/tutores \
  -H "Content-Type: application/json" \
  -d '{"nome":"Tutor Teste","especialidade":"Sistemas de Baterias VHE","email":"tutor@test.com","telefone":"11999999999","ativo":"S"}'

# UPDATE
curl -X PUT http://localhost:8080/api/tutores/1 \
  -H "Content-Type: application/json" \
  -d '{"nome":"Tutor Atualizado","especialidade":"Diagnóstico e Reparo VHE","email":"tutor2@test.com","telefone":"11888888888","ativo":"S"}'

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
  -d '{"nomeEmpreendimento":"Oficina Teste","cnpj":"12345678000190","nomeEmpresa":"Empresa Teste","localizacao":"São Paulo, SP","especialidade":"VHE Completo"}'

# READ (listar todas)
curl -X GET http://localhost:8080/api/oficinas

# READ (buscar por ID)
curl -X GET http://localhost:8080/api/oficinas/1

# READ (buscar por cidade)
curl -X GET http://localhost:8080/api/oficinas/cidade/São%20Paulo

# READ (buscar por estado)
curl -X GET http://localhost:8080/api/oficinas/estado/SP

# APROVAR oficina
curl -X POST http://localhost:8080/api/oficinas/1/aprovar

# ⚠️ UPDATE e DELETE não estão disponíveis para Oficinas
```

### Serviços de Oficinas
```bash
# CREATE (requer oficinaId)
curl -X POST http://localhost:8080/api/oficina-servicos \
  -H "Content-Type: application/json" \
  -d '{"oficinaId":1,"nome":"Troca de Bateria VHE","descricao":"Serviço especializado de substituição de baterias","ativo":"S"}'

# UPDATE
curl -X PUT http://localhost:8080/api/oficina-servicos/1 \
  -H "Content-Type: application/json" \
  -d '{"nome":"Troca de Bateria VHE - Atualizado","descricao":"Nova descrição","ativo":"S"}'

# DELETE
curl -X DELETE http://localhost:8080/api/oficina-servicos/1

# LISTAR por oficina
curl -X GET "http://localhost:8080/api/oficina-servicos?oficinaId=1"
```

### Pontos de Recarga
```bash
# CREATE
curl -X POST http://localhost:8080/api/pontos-recarga \
  -H "Content-Type: application/json" \
  -d '{"nome":"Estação Rápida SP","endereco":"Rua Augusta, 2000 - Consolação, São Paulo - SP","tipoRecarga":"DC"}'

# UPDATE
curl -X PUT http://localhost:8080/api/pontos-recarga/1 \
  -H "Content-Type: application/json" \
  -d '{"nome":"Estação Rápida SP - Atualizada","endereco":"Nova Rua, 100 - Centro, São Paulo - SP","tipoRecarga":"AC_DC"}'

# DELETE
curl -X DELETE http://localhost:8080/api/pontos-recarga/1

# LISTAR por tipo
curl -X GET "http://localhost:8080/api/pontos-recarga?tipo=DC"

# BUSCAR por termo
curl -X GET "http://localhost:8080/api/pontos-recarga?busca=São Paulo"
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

5. **Estrutura do Banco:** Todas as tabelas possuem o prefixo `T_ZYNT_` (ex: `T_ZYNT_USUARIOS`, `T_ZYNT_CURSOS`)

6. **Campos Removidos:**
   - **Curso:** Não possui mais `duracao`, `formato` e `preco`
   - **Oficina:** Não possui mais campo `servicos` (use `/api/oficina-servicos`)

7. **Novos Recursos:**
   - **Aulas de Curso:** Possuem campo `url` para vídeos do YouTube
   - **Serviços de Oficinas:** Tabela separada para serviços oferecidos por oficinas
   - **Pontos de Recarga:** Simplificado (nome, endereco, tipoRecarga)

8. **Restrições:**
   - **Oficinas:** Apenas CREATE e READ disponíveis (sem UPDATE e DELETE)

9. **Verificação:** Após cada operação, você pode verificar no banco de dados Oracle se os dados foram persistidos corretamente.

## 🔍 Troubleshooting

Se os dados não estiverem sendo persistidos:

1. Verifique se a aplicação está rodando: `mvn quarkus:dev`
2. Verifique os logs da aplicação para erros
3. Verifique a conexão com o banco Oracle
4. Verifique se as tabelas existem no banco
5. Verifique os logs do Hibernate (SQL executado)




