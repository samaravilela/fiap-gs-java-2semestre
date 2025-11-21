-- =====================================================
-- Script SQL Completo - Zyntra HE Backend
-- Sistema: Zyntra HE Backend
-- Banco: PostgreSQL
-- 
-- Este arquivo contém:
-- 1. Script DDL - Criação da estrutura do banco de dados
-- 2. Script DML - Popular tabelas com dados
-- 3. Script DQL - Consultas para tomada de decisão
-- =====================================================

-- =====================================================
-- PARTE 1: SCRIPT DDL - CRIAÇÃO DA ESTRUTURA
-- =====================================================

-- Criar banco de dados (execute separadamente se necessário)
-- CREATE DATABASE zyntra_he;
-- \c zyntra_he;

-- =====================================================
-- Tabela: usuarios
-- Descrição: Armazena informações dos usuários do sistema
-- PK: id
-- UK: email
-- NN: email, senha, nome
-- CK: validação de tamanho mínimo de senha
-- =====================================================
CREATE TABLE IF NOT EXISTS usuarios (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    nome VARCHAR(255) NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ck_senha_min_length CHECK (LENGTH(senha) >= 6)
);

-- Índice para busca rápida por email
CREATE INDEX IF NOT EXISTS idx_usuarios_email ON usuarios(email);

-- =====================================================
-- Tabela: tutores
-- Descrição: Armazena informações dos tutores
-- PK: id
-- UK: email
-- NN: nome, especialidade, email, telefone
-- CK: validação de formato de email e telefone
-- =====================================================
CREATE TABLE IF NOT EXISTS tutores (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    especialidade VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    telefone VARCHAR(50) NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ativo BOOLEAN DEFAULT TRUE,
    CONSTRAINT ck_tutor_email_format CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$'),
    CONSTRAINT ck_tutor_telefone CHECK (LENGTH(telefone) >= 10)
);

-- Índice para busca rápida por email
CREATE INDEX IF NOT EXISTS idx_tutores_email ON tutores(email);
-- Índice para busca por especialidade
CREATE INDEX IF NOT EXISTS idx_tutores_especialidade ON tutores(especialidade);
-- Índice para busca por status ativo
CREATE INDEX IF NOT EXISTS idx_tutores_ativo ON tutores(ativo);

-- =====================================================
-- Tabela: cursos
-- Descrição: Armazena informações dos cursos oferecidos
-- PK: id
-- NN: titulo
-- CK: validação de URL (se informada)
-- =====================================================
CREATE TABLE IF NOT EXISTS cursos (
    id BIGSERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT,
    duracao VARCHAR(100),
    formato VARCHAR(100),
    preco VARCHAR(50),
    url VARCHAR(500),
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ativo BOOLEAN DEFAULT TRUE,
    CONSTRAINT ck_curso_url_format CHECK (url IS NULL OR url ~* '^https?://.*')
);

-- Índice para busca por título
CREATE INDEX IF NOT EXISTS idx_cursos_titulo ON cursos(titulo);
-- Índice para busca por status ativo
CREATE INDEX IF NOT EXISTS idx_cursos_ativo ON cursos(ativo);

-- =====================================================
-- Tabela: oficinas
-- Descrição: Armazena informações das oficinas/empreendimentos
-- PK: id
-- UK: cnpj
-- NN: nome_empreendimento, cnpj, nome_empresa, localizacao, servicos
-- CK: validação de CNPJ (14 dígitos), status, avaliação (0-5)
-- =====================================================
CREATE TABLE IF NOT EXISTS oficinas (
    id BIGSERIAL PRIMARY KEY,
    nome_empreendimento VARCHAR(255) NOT NULL,
    cnpj VARCHAR(14) NOT NULL UNIQUE,
    nome_empresa VARCHAR(255) NOT NULL,
    localizacao TEXT NOT NULL,
    servicos TEXT NOT NULL,
    cidade VARCHAR(100),
    estado VARCHAR(2),
    especialidade VARCHAR(255),
    avaliacao DECIMAL(3,2) DEFAULT 0.0,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'PENDENTE',
    CONSTRAINT ck_oficina_cnpj_length CHECK (LENGTH(cnpj) = 14 AND cnpj ~ '^[0-9]+$'),
    CONSTRAINT ck_oficina_status CHECK (status IN ('PENDENTE', 'APROVADA', 'REJEITADA', 'INATIVA')),
    CONSTRAINT ck_oficina_avaliacao CHECK (avaliacao >= 0.0 AND avaliacao <= 5.0),
    CONSTRAINT ck_oficina_estado CHECK (estado IS NULL OR LENGTH(estado) = 2)
);

-- Índice para busca por CNPJ
CREATE INDEX IF NOT EXISTS idx_oficinas_cnpj ON oficinas(cnpj);
-- Índice para busca por status
CREATE INDEX IF NOT EXISTS idx_oficinas_status ON oficinas(status);
-- Índice para busca por cidade
CREATE INDEX IF NOT EXISTS idx_oficinas_cidade ON oficinas(cidade);
-- Índice para busca por estado
CREATE INDEX IF NOT EXISTS idx_oficinas_estado ON oficinas(estado);

-- =====================================================
-- Tabela: mentorias
-- Descrição: Armazena informações das mentorias agendadas
-- PK: id
-- FK: tutor_id (referencia tutores.id), usuario_id (referencia usuarios.id)
-- NN: nome_completo, cpf, email, telefone, data
-- CK: validação de CPF (11 dígitos), status, formato de email
-- =====================================================
CREATE TABLE IF NOT EXISTS mentorias (
    id BIGSERIAL PRIMARY KEY,
    nome_completo VARCHAR(255) NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    email VARCHAR(255) NOT NULL,
    telefone VARCHAR(50) NOT NULL,
    data DATE NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'AGENDADA',
    tutor_id BIGINT,
    usuario_id BIGINT,
    CONSTRAINT ck_mentoria_cpf_length CHECK (LENGTH(cpf) = 11 AND cpf ~ '^[0-9]+$'),
    CONSTRAINT ck_mentoria_status CHECK (status IN ('AGENDADA', 'CONFIRMADA', 'CANCELADA', 'REALIZADA')),
    CONSTRAINT ck_mentoria_email_format CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$'),
    CONSTRAINT fk_mentoria_tutor FOREIGN KEY (tutor_id) REFERENCES tutores(id) ON DELETE SET NULL,
    CONSTRAINT fk_mentoria_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

-- Índice para busca por CPF
CREATE INDEX IF NOT EXISTS idx_mentorias_cpf ON mentorias(cpf);
-- Índice para busca por email
CREATE INDEX IF NOT EXISTS idx_mentorias_email ON mentorias(email);
-- Índice para busca por data
CREATE INDEX IF NOT EXISTS idx_mentorias_data ON mentorias(data);
-- Índice para busca por status
CREATE INDEX IF NOT EXISTS idx_mentorias_status ON mentorias(status);
-- Índice para busca por tutor
CREATE INDEX IF NOT EXISTS idx_mentorias_tutor ON mentorias(tutor_id);
-- Índice para busca por usuário
CREATE INDEX IF NOT EXISTS idx_mentorias_usuario ON mentorias(usuario_id);

-- =====================================================
-- Comentários nas Tabelas
-- =====================================================
COMMENT ON TABLE usuarios IS 'Tabela de usuários do sistema';
COMMENT ON TABLE tutores IS 'Tabela de tutores disponíveis';
COMMENT ON TABLE cursos IS 'Tabela de cursos oferecidos';
COMMENT ON TABLE oficinas IS 'Tabela de oficinas/empreendimentos cadastrados';
COMMENT ON TABLE mentorias IS 'Tabela de mentorias agendadas';

-- =====================================================
-- Comentários nas Colunas Principais
-- =====================================================
COMMENT ON COLUMN usuarios.email IS 'Email único do usuário';
COMMENT ON COLUMN usuarios.senha IS 'Senha criptografada (SHA-256)';
COMMENT ON COLUMN oficinas.cnpj IS 'CNPJ único da empresa (14 dígitos)';
COMMENT ON COLUMN oficinas.status IS 'Status da oficina: PENDENTE, APROVADA, REJEITADA, INATIVA';
COMMENT ON COLUMN mentorias.cpf IS 'CPF do mentorado (11 dígitos)';
COMMENT ON COLUMN mentorias.data IS 'Data agendada para a mentoria';
COMMENT ON COLUMN mentorias.status IS 'Status da mentoria: AGENDADA, CONFIRMADA, CANCELADA, REALIZADA';
COMMENT ON COLUMN mentorias.tutor_id IS 'Referência ao tutor responsável pela mentoria';
COMMENT ON COLUMN mentorias.usuario_id IS 'Referência ao usuário que agendou a mentoria';

-- =====================================================
-- Triggers para atualização automática de data_atualizacao
-- =====================================================

-- Função para atualizar data_atualizacao
CREATE OR REPLACE FUNCTION update_data_atualizacao()
RETURNS TRIGGER AS $$
BEGIN
    NEW.data_atualizacao = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger para tabela usuarios
CREATE TRIGGER trigger_update_usuarios
    BEFORE UPDATE ON usuarios
    FOR EACH ROW
    EXECUTE FUNCTION update_data_atualizacao();

-- Trigger para tabela tutores
CREATE TRIGGER trigger_update_tutores
    BEFORE UPDATE ON tutores
    FOR EACH ROW
    EXECUTE FUNCTION update_data_atualizacao();

-- Trigger para tabela cursos
CREATE TRIGGER trigger_update_cursos
    BEFORE UPDATE ON cursos
    FOR EACH ROW
    EXECUTE FUNCTION update_data_atualizacao();

-- Trigger para tabela oficinas
CREATE TRIGGER trigger_update_oficinas
    BEFORE UPDATE ON oficinas
    FOR EACH ROW
    EXECUTE FUNCTION update_data_atualizacao();

-- =====================================================
-- FIM DA PARTE 1: SCRIPT DDL
-- =====================================================

-- =====================================================
-- PARTE 2: SCRIPT DML - POPULAR TABELAS COM DADOS
-- =====================================================

-- =====================================================
-- Tabela: usuarios
-- Inserindo 10 usuários
-- =====================================================
INSERT INTO usuarios (email, senha, nome) VALUES
('admin@zyntra.com', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 'Administrador Sistema'),
('maria.silva@email.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'Maria Silva'),
('joao.santos@email.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'João Santos'),
('ana.costa@email.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'Ana Costa'),
('pedro.oliveira@email.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'Pedro Oliveira'),
('carla.ferreira@email.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'Carla Ferreira'),
('lucas.rodrigues@email.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'Lucas Rodrigues'),
('julia.almeida@email.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'Julia Almeida'),
('rafael.martins@email.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'Rafael Martins'),
('fernanda.lima@email.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'Fernanda Lima'),
('gabriel.souza@email.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'Gabriel Souza')
ON CONFLICT (email) DO NOTHING;

-- =====================================================
-- Tabela: tutores
-- Inserindo 10 tutores
-- =====================================================
INSERT INTO tutores (nome, especialidade, email, telefone, ativo) VALUES
('Prof. Carlos Mendes', 'Empreendedorismo', 'carlos.mendes@zyntra.com', '(11) 98765-4321', true),
('Dra. Patricia Santos', 'Marketing Digital', 'patricia.santos@zyntra.com', '(11) 98765-4322', true),
('Prof. Roberto Alves', 'Gestão Financeira', 'roberto.alves@zyntra.com', '(11) 98765-4323', true),
('Dra. Luciana Costa', 'Inovação e Tecnologia', 'luciana.costa@zyntra.com', '(11) 98765-4324', true),
('Prof. Fernando Lima', 'Vendas e Negociação', 'fernando.lima@zyntra.com', '(11) 98765-4325', true),
('Dra. Mariana Rocha', 'Planejamento Estratégico', 'mariana.rocha@zyntra.com', '(11) 98765-4326', true),
('Prof. André Pereira', 'Recursos Humanos', 'andre.pereira@zyntra.com', '(11) 98765-4327', true),
('Dra. Beatriz Nunes', 'Comunicação Empresarial', 'beatriz.nunes@zyntra.com', '(11) 98765-4328', true),
('Prof. Thiago Barbosa', 'Operações e Logística', 'thiago.barbosa@zyntra.com', '(11) 98765-4329', true),
('Dra. Camila Freitas', 'Sustentabilidade', 'camila.freitas@zyntra.com', '(11) 98765-4330', true)
ON CONFLICT (email) DO NOTHING;

-- =====================================================
-- Tabela: cursos
-- Inserindo 10 cursos
-- =====================================================
INSERT INTO cursos (titulo, descricao, duracao, formato, preco, url, ativo) VALUES
('Empreendedorismo do Zero', 'Curso completo para iniciantes em empreendedorismo', '40 horas', 'Online', 'R$ 299,90', 'https://zyntra.com/cursos/empreendedorismo-zero', true),
('Marketing Digital Avançado', 'Estratégias avançadas de marketing digital para negócios', '60 horas', 'Online', 'R$ 499,90', 'https://zyntra.com/cursos/marketing-digital', true),
('Gestão Financeira para PMEs', 'Aprenda a gerenciar as finanças da sua empresa', '30 horas', 'Presencial', 'R$ 399,90', 'https://zyntra.com/cursos/gestao-financeira', true),
('Inovação e Tecnologia', 'Como inovar usando tecnologia no seu negócio', '50 horas', 'Híbrido', 'R$ 599,90', 'https://zyntra.com/cursos/inovacao-tecnologia', true),
('Vendas e Negociação', 'Técnicas de vendas e negociação para aumentar resultados', '35 horas', 'Online', 'R$ 349,90', 'https://zyntra.com/cursos/vendas-negociacao', true),
('Planejamento Estratégico', 'Desenvolva estratégias eficazes para seu negócio', '45 horas', 'Presencial', 'R$ 549,90', 'https://zyntra.com/cursos/planejamento-estrategico', true),
('Gestão de Pessoas', 'Como liderar e gerenciar equipes de alta performance', '40 horas', 'Online', 'R$ 449,90', 'https://zyntra.com/cursos/gestao-pessoas', true),
('Comunicação Empresarial', 'Melhore a comunicação interna e externa da empresa', '25 horas', 'Online', 'R$ 299,90', 'https://zyntra.com/cursos/comunicacao-empresarial', true),
('Operações e Logística', 'Otimize processos operacionais e logísticos', '55 horas', 'Híbrido', 'R$ 649,90', 'https://zyntra.com/cursos/operacoes-logistica', true),
('Sustentabilidade Empresarial', 'Implemente práticas sustentáveis no seu negócio', '30 horas', 'Online', 'R$ 399,90', 'https://zyntra.com/cursos/sustentabilidade', true)
ON CONFLICT DO NOTHING;

-- =====================================================
-- Tabela: oficinas
-- Inserindo 10 oficinas
-- =====================================================
INSERT INTO oficinas (nome_empreendimento, cnpj, nome_empresa, localizacao, servicos, cidade, estado, especialidade, avaliacao, status) VALUES
('Tech Solutions', '12345678000190', 'Tech Solutions Ltda', 'Av. Paulista, 1000 - Bela Vista', 'Desenvolvimento de software, consultoria em TI', 'São Paulo', 'SP', 'Tecnologia', 4.5, 'APROVADA'),
('Eco Verde', '23456789000101', 'Eco Verde Empreendimentos', 'Rua das Flores, 200 - Centro', 'Consultoria ambiental, produtos sustentáveis', 'Rio de Janeiro', 'RJ', 'Sustentabilidade', 4.8, 'APROVADA'),
('Gourmet Express', '34567890000112', 'Gourmet Express Comércio', 'Av. Atlântica, 500 - Copacabana', 'Catering, eventos corporativos', 'Rio de Janeiro', 'RJ', 'Gastronomia', 4.2, 'APROVADA'),
('Fit Life', '45678901000123', 'Fit Life Academia', 'Rua Augusta, 1500 - Consolação', 'Academia, personal trainer, nutrição', 'São Paulo', 'SP', 'Saúde e Bem-estar', 4.6, 'APROVADA'),
('Arte & Design', '56789012000134', 'Arte & Design Studio', 'Rua Oscar Freire, 800 - Jardins', 'Design gráfico, web design, branding', 'São Paulo', 'SP', 'Design', 4.7, 'APROVADA'),
('Construção Plus', '67890123000145', 'Construção Plus Construções', 'Av. Brasil, 2000 - Mooca', 'Construção civil, reformas, projetos', 'São Paulo', 'SP', 'Construção', 4.3, 'APROVADA'),
('Moda Elegante', '78901234000156', 'Moda Elegante Confecções', 'Rua 25 de Março, 500 - Centro', 'Confecção de roupas, moda feminina', 'São Paulo', 'SP', 'Moda', 4.4, 'APROVADA'),
('Educação Kids', '89012345000167', 'Educação Kids Escola', 'Av. Faria Lima, 3000 - Itaim', 'Ensino infantil, atividades extracurriculares', 'São Paulo', 'SP', 'Educação', 4.9, 'APROVADA'),
('Beleza & Estilo', '90123456000178', 'Beleza & Estilo Salão', 'Rua dos Três Irmãos, 600 - Butantã', 'Cabeleireiro, estética, manicure', 'São Paulo', 'SP', 'Beleza', 4.5, 'APROVADA'),
('Transporte Rápido', '01234567000189', 'Transporte Rápido Logística', 'Av. Marginal Tietê, 5000 - Barra Funda', 'Transporte de cargas, logística', 'São Paulo', 'SP', 'Logística', 4.1, 'PENDENTE')
ON CONFLICT (cnpj) DO NOTHING;

-- =====================================================
-- Tabela: mentorias
-- Inserindo 10 mentorias
-- Observação: tutor_id e usuario_id referenciam IDs das tabelas anteriores
-- =====================================================
INSERT INTO mentorias (nome_completo, cpf, email, telefone, data, status, tutor_id, usuario_id) VALUES
('Maria Silva', '12345678901', 'maria.silva@email.com', '(11) 98765-1234', CURRENT_DATE + INTERVAL '7 days', 'AGENDADA', 1, 2),
('João Santos', '23456789012', 'joao.santos@email.com', '(11) 98765-2345', CURRENT_DATE + INTERVAL '10 days', 'CONFIRMADA', 2, 3),
('Ana Costa', '34567890123', 'ana.costa@email.com', '(11) 98765-3456', CURRENT_DATE + INTERVAL '14 days', 'AGENDADA', 3, 4),
('Pedro Oliveira', '45678901234', 'pedro.oliveira@email.com', '(11) 98765-4567', CURRENT_DATE + INTERVAL '5 days', 'CONFIRMADA', 4, 5),
('Carla Ferreira', '56789012345', 'carla.ferreira@email.com', '(11) 98765-5678', CURRENT_DATE + INTERVAL '21 days', 'AGENDADA', 5, 6),
('Lucas Rodrigues', '67890123456', 'lucas.rodrigues@email.com', '(11) 98765-6789', CURRENT_DATE + INTERVAL '3 days', 'CONFIRMADA', 6, 7),
('Julia Almeida', '78901234567', 'julia.almeida@email.com', '(11) 98765-7890', CURRENT_DATE + INTERVAL '28 days', 'AGENDADA', 7, 8),
('Rafael Martins', '89012345678', 'rafael.martins@email.com', '(11) 98765-8901', CURRENT_DATE - INTERVAL '5 days', 'REALIZADA', 8, 9),
('Fernanda Lima', '90123456789', 'fernanda.lima@email.com', '(11) 98765-9012', CURRENT_DATE - INTERVAL '10 days', 'REALIZADA', 9, 10),
('Gabriel Souza', '01234567890', 'gabriel.souza@email.com', '(11) 98765-0123', CURRENT_DATE + INTERVAL '12 days', 'AGENDADA', 1, 2)
ON CONFLICT DO NOTHING;

-- =====================================================
-- FIM DA PARTE 2: SCRIPT DML
-- =====================================================

-- =====================================================
-- PARTE 3: SCRIPT DQL - CONSULTAS PARA TOMADA DE DECISÃO
-- =====================================================

-- =====================================================
-- CONSULTA 1: Consulta Simples
-- SELECT/FROM/WHERE/ORDER BY
-- Objetivo: Listar todos os cursos ativos ordenados por título
-- para facilitar a busca e apresentação no catálogo
-- =====================================================
SELECT 
    id,
    titulo,
    descricao,
    duracao,
    formato,
    preco,
    url,
    ativo
FROM cursos
WHERE ativo = true
ORDER BY titulo ASC;

-- =====================================================
-- CONSULTA 2: Consulta com Junção de Tabelas
-- SELECT/FROM/WHERE/ORDER BY com JOIN
-- Objetivo: Listar todas as mentorias agendadas com informações
-- do tutor responsável e do usuário que agendou, ordenadas por data
-- para gerenciamento de agenda e acompanhamento
-- =====================================================
SELECT 
    m.id AS mentoria_id,
    m.nome_completo AS mentorado,
    m.email AS email_mentorado,
    m.telefone AS telefone_mentorado,
    m.data AS data_mentoria,
    m.status AS status_mentoria,
    t.nome AS tutor_nome,
    t.especialidade AS tutor_especialidade,
    t.email AS tutor_email,
    t.telefone AS tutor_telefone,
    u.nome AS usuario_nome,
    u.email AS usuario_email
FROM mentorias m
LEFT JOIN tutores t ON m.tutor_id = t.id
LEFT JOIN usuarios u ON m.usuario_id = u.id
WHERE m.status IN ('AGENDADA', 'CONFIRMADA')
ORDER BY m.data ASC, m.nome_completo ASC;

-- =====================================================
-- CONSULTA 3: Consulta com Função de Grupo e Agrupamento
-- SELECT com GROUP BY e funções agregadas
-- Objetivo: Contar quantas oficinas existem por estado e calcular
-- a média de avaliação por estado para análise de distribuição
-- geográfica e qualidade dos serviços
-- =====================================================
SELECT 
    estado,
    COUNT(*) AS total_oficinas,
    ROUND(AVG(avaliacao), 2) AS media_avaliacao,
    MIN(avaliacao) AS menor_avaliacao,
    MAX(avaliacao) AS maior_avaliacao
FROM oficinas
WHERE estado IS NOT NULL
GROUP BY estado
ORDER BY total_oficinas DESC, media_avaliacao DESC;

-- =====================================================
-- CONSULTA 4: Consulta com Função de Grupo, Agrupamento,
-- Filtro (HAVING) e Junção de Tabelas
-- SELECT com GROUP BY, HAVING e JOIN
-- Objetivo: Identificar tutores que têm mais de 2 mentorias agendadas
-- ou confirmadas, com a média de avaliação das oficinas relacionadas
-- à especialidade do tutor, para identificar tutores mais demandados
-- e avaliar a qualidade dos serviços relacionados
-- =====================================================
SELECT 
    t.id AS tutor_id,
    t.nome AS tutor_nome,
    t.especialidade AS tutor_especialidade,
    COUNT(m.id) AS total_mentorias,
    COUNT(CASE WHEN m.status = 'AGENDADA' THEN 1 END) AS mentorias_agendadas,
    COUNT(CASE WHEN m.status = 'CONFIRMADA' THEN 1 END) AS mentorias_confirmadas,
    ROUND(AVG(o.avaliacao), 2) AS media_avaliacao_oficinas_especialidade
FROM tutores t
LEFT JOIN mentorias m ON t.id = m.tutor_id AND m.status IN ('AGENDADA', 'CONFIRMADA')
LEFT JOIN oficinas o ON t.especialidade = o.especialidade
WHERE t.ativo = true
GROUP BY t.id, t.nome, t.especialidade
HAVING COUNT(m.id) > 2
ORDER BY total_mentorias DESC, media_avaliacao_oficinas_especialidade DESC;

-- =====================================================
-- CONSULTAS ADICIONAIS (Bônus)
-- =====================================================

-- Consulta adicional 1: Oficinas aprovadas por cidade
-- Objetivo: Identificar cidades com mais oficinas aprovadas
SELECT 
    cidade,
    estado,
    COUNT(*) AS total_oficinas_aprovadas,
    ROUND(AVG(avaliacao), 2) AS media_avaliacao
FROM oficinas
WHERE status = 'APROVADA' AND cidade IS NOT NULL
GROUP BY cidade, estado
ORDER BY total_oficinas_aprovadas DESC;

-- Consulta adicional 2: Mentorias por status e período
-- Objetivo: Análise de agendamentos por status
SELECT 
    status,
    COUNT(*) AS quantidade,
    MIN(data) AS primeira_data,
    MAX(data) AS ultima_data
FROM mentorias
GROUP BY status
ORDER BY quantidade DESC;

-- =====================================================
-- FIM DA PARTE 3: SCRIPT DQL
-- =====================================================

-- =====================================================
-- FIM DO SCRIPT SQL COMPLETO
-- =====================================================

