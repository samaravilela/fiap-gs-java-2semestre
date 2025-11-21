# Correção: INSERT, UPDATE e DELETE no Banco de Dados

## 🔍 Problema Identificado

Você estava vendo apenas SELECTs no banco de dados porque as transações não estavam sendo commitadas corretamente.

## ✅ Correção Aplicada

### 1. Troca de `@PersistenceContext` para `@Inject`

No Quarkus, o `@PersistenceContext` pode não funcionar corretamente com transações. Foi alterado para `@Inject` em todos os DAOs:

**Antes:**
```java
@PersistenceContext
EntityManager entityManager;
```

**Depois:**
```java
@Inject
EntityManager entityManager;
```

### 2. DAOs Corrigidos

- ✅ `UsuarioDAO` - Alterado para `@Inject`
- ✅ `CursoDAO` - Alterado para `@Inject`
- ✅ `TutorDAO` - Alterado para `@Inject`
- ✅ `MentoriaDAO` - Alterado para `@Inject`
- ✅ `OficinaDAO` - Alterado para `@Inject`

### 3. Configuração de Log SQL

Adicionado no `application.properties`:
```properties
quarkus.hibernate-orm.log.sql=true
quarkus.hibernate-orm.log.bind-parameters=true
```

Isso permite ver todos os SQLs executados, incluindo INSERT, UPDATE e DELETE.

## 🧪 Como Verificar

### 1. Verificar Logs da Aplicação

Quando você executar um CREATE, UPDATE ou DELETE, você verá nos logs:

```
Hibernate: INSERT INTO T_USUARIOS (EMAIL, NOME, SENHA, ID) VALUES (?, ?, ?, ?)
Hibernate: UPDATE T_USUARIOS SET EMAIL=?, NOME=?, SENHA=? WHERE ID=?
Hibernate: DELETE FROM T_USUARIOS WHERE ID=?
```

### 2. Verificar no Banco de Dados Oracle

Após executar as operações, verifique diretamente:

```sql
-- Verificar INSERT
SELECT * FROM T_USUARIOS ORDER BY ID DESC;

-- Verificar UPDATE
SELECT * FROM T_USUARIOS WHERE ID = 1;

-- Verificar DELETE
SELECT COUNT(*) FROM T_USUARIOS;
```

### 3. Testar com cURL

```bash
# CREATE - Deve executar INSERT
curl -X POST http://localhost:8080/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{"nome":"Teste","email":"teste@test.com","senha":"123456"}'

# UPDATE - Deve executar UPDATE
curl -X PUT http://localhost:8080/api/usuarios/1 \
  -H "Content-Type: application/json" \
  -d '{"nome":"Teste Atualizado","email":"teste2@test.com","senha":"654321"}'

# DELETE - Deve executar DELETE
curl -X DELETE http://localhost:8080/api/usuarios/1
```

## 📋 Como Funciona Agora

1. **@Transactional nos Services**: Garante que a transação seja iniciada
2. **@Inject EntityManager**: Injeta o EntityManager corretamente no Quarkus
3. **entityManager.persist()**: Adiciona a entidade ao contexto de persistência
4. **entityManager.flush()**: Envia as mudanças para o banco imediatamente
5. **Commit Automático**: O `@Transactional` faz commit automaticamente ao final do método

## ⚠️ Importante

- O `@Transactional` deve estar nos **Services**, não nos DAOs
- O EntityManager deve ser injetado com `@Inject`, não `@PersistenceContext`
- O `flush()` garante que as mudanças sejam enviadas ao banco imediatamente
- O commit acontece automaticamente quando o método `@Transactional` termina com sucesso

## 🔄 Próximos Passos

1. Reinicie a aplicação: `mvn quarkus:dev`
2. Teste um CREATE e verifique os logs
3. Verifique no banco Oracle se o registro foi criado
4. Teste UPDATE e DELETE também

Agora você deve ver os INSERTs, UPDATEs e DELETEs sendo executados no banco de dados!

