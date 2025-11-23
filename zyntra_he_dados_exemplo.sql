------------------------------------------------------------
-- ZYNTRA HE - DADOS DE EXEMPLO PARA POPULAR O BANCO
-- Projeto: Veículos Híbridos e Elétricos
-- Senha padrão para todos os usuários: 1234567
-- Hash SHA-256: 8bb0cf6eb9b17d0f7d22b456f121257dc1254e1f01665370476383ea776df414
------------------------------------------------------------

------------------------------------------------------------
-- INSERÇÃO DE USUÁRIOS
-- Senha padrão: 1234567 (criptografada em SHA-256)
------------------------------------------------------------
INSERT INTO T_ZYNT_USUARIOS (EMAIL, SENHA, NOME, DATA_CRIACAO, DATA_ATUALIZACAO) VALUES
('joao.silva@email.com', '8bb0cf6eb9b17d0f7d22b456f121257dc1254e1f01665370476383ea776df414', 'João Silva', SYSTIMESTAMP, SYSTIMESTAMP);

INSERT INTO T_ZYNT_USUARIOS (EMAIL, SENHA, NOME, DATA_CRIACAO, DATA_ATUALIZACAO) VALUES
('maria.santos@email.com', '8bb0cf6eb9b17d0f7d22b456f121257dc1254e1f01665370476383ea776df414', 'Maria Santos', SYSTIMESTAMP, SYSTIMESTAMP);

INSERT INTO T_ZYNT_USUARIOS (EMAIL, SENHA, NOME, DATA_CRIACAO, DATA_ATUALIZACAO) VALUES
('carlos.oliveira@email.com', '8bb0cf6eb9b17d0f7d22b456f121257dc1254e1f01665370476383ea776df414', 'Carlos Oliveira', SYSTIMESTAMP, SYSTIMESTAMP);

INSERT INTO T_ZYNT_USUARIOS (EMAIL, SENHA, NOME, DATA_CRIACAO, DATA_ATUALIZACAO) VALUES
('ana.costa@email.com', '8bb0cf6eb9b17d0f7d22b456f121257dc1254e1f01665370476383ea776df414', 'Ana Costa', SYSTIMESTAMP, SYSTIMESTAMP);

INSERT INTO T_ZYNT_USUARIOS (EMAIL, SENHA, NOME, DATA_CRIACAO, DATA_ATUALIZACAO) VALUES
('pedro.ferreira@email.com', '8bb0cf6eb9b17d0f7d22b456f121257dc1254e1f01665370476383ea776df414', 'Pedro Ferreira', SYSTIMESTAMP, SYSTIMESTAMP);

------------------------------------------------------------
-- INSERÇÃO DE TUTORES
------------------------------------------------------------
INSERT INTO T_ZYNT_TUTORES (NOME, ESPECIALIDADE, EMAIL, TELEFONE, DATA_CRIACAO, DATA_ATUALIZACAO, ATIVO) VALUES
('Roberto Mendes', 'Sistemas de Baterias VHE', 'roberto.mendes@zyntrahe.com', '(11) 98765-4321', SYSTIMESTAMP, SYSTIMESTAMP, 'S');

INSERT INTO T_ZYNT_TUTORES (NOME, ESPECIALIDADE, EMAIL, TELEFONE, DATA_CRIACAO, DATA_ATUALIZACAO, ATIVO) VALUES
('Fernanda Lima', 'Diagnóstico de Veículos Elétricos', 'fernanda.lima@zyntrahe.com', '(11) 97654-3210', SYSTIMESTAMP, SYSTIMESTAMP, 'S');

INSERT INTO T_ZYNT_TUTORES (NOME, ESPECIALIDADE, EMAIL, TELEFONE, DATA_CRIACAO, DATA_ATUALIZACAO, ATIVO) VALUES
('Marcos Alves', 'Manutenção de Motores Híbridos', 'marcos.alves@zyntrahe.com', '(11) 96543-2109', SYSTIMESTAMP, SYSTIMESTAMP, 'S');

------------------------------------------------------------
-- INSERÇÃO DE CURSOS
------------------------------------------------------------
INSERT INTO T_ZYNT_CURSOS (TITULO, DESCRICAO, DATA_CRIACAO, DATA_ATUALIZACAO, ATIVO) VALUES
('Curso Completo de Baterias VHE', 
 'Aprenda tudo sobre baterias de veículos híbridos e elétricos, desde a teoria até a manutenção avançada. Módulos práticos com casos reais.',
 SYSTIMESTAMP, SYSTIMESTAMP, 'S');

INSERT INTO T_ZYNT_CURSOS (TITULO, DESCRICAO, DATA_CRIACAO, DATA_ATUALIZACAO, ATIVO) VALUES
('Formação Completa em VHE', 
 'Torne-se um especialista em veículos híbridos e elétricos com nossa formação mais abrangente. Inclui diagnóstico, manutenção e reparação.',
 SYSTIMESTAMP, SYSTIMESTAMP, 'S');

INSERT INTO T_ZYNT_CURSOS (TITULO, DESCRICAO, DATA_CRIACAO, DATA_ATUALIZACAO, ATIVO) VALUES
('Diagnóstico Avançado de Sistemas Elétricos', 
 'Domine as técnicas de diagnóstico em veículos elétricos. Aprenda a usar equipamentos especializados e interpretar códigos de erro.',
 SYSTIMESTAMP, SYSTIMESTAMP, 'S');

------------------------------------------------------------
-- INSERÇÃO DE AULAS DE CURSO
------------------------------------------------------------
-- Aulas do Curso 1 (Baterias VHE)
INSERT INTO T_ZYNT_AULAS_CURSO (CURSO_ID, TITULO, DESCRICAO, URL, ATIVO, DATA_CRIACAO, DATA_ATUALIZACAO) VALUES
(1, 'Introdução às Baterias de Íon-Lítio', 
 'Conceitos fundamentais sobre baterias de íon-lítio usadas em veículos elétricos e híbridos.', 
 'https://www.youtube.com/watch?v=9OVtk6G2TnQ', 
 'S', SYSTIMESTAMP, SYSTIMESTAMP);

INSERT INTO T_ZYNT_AULAS_CURSO (CURSO_ID, TITULO, DESCRICAO, URL, ATIVO, DATA_CRIACAO, DATA_ATUALIZACAO) VALUES
(1, 'Segurança no Manuseio de Baterias', 
 'Protocolos de segurança essenciais para trabalhar com baterias de alta tensão.', 
 'https://www.youtube.com/watch?v=KXWf7c6pX5Y', 
 'S', SYSTIMESTAMP, SYSTIMESTAMP);

INSERT INTO T_ZYNT_AULAS_CURSO (CURSO_ID, TITULO, DESCRICAO, URL, ATIVO, DATA_CRIACAO, DATA_ATUALIZACAO) VALUES
(1, 'Diagnóstico de Problemas em Baterias', 
 'Como identificar e diagnosticar problemas comuns em sistemas de baterias VHE.', 
 'https://www.youtube.com/watch?v=zN7XQVfr4PY', 
 'S', SYSTIMESTAMP, SYSTIMESTAMP);

-- Aulas do Curso 2 (Formação Completa)
INSERT INTO T_ZYNT_AULAS_CURSO (CURSO_ID, TITULO, DESCRICAO, URL, ATIVO, DATA_CRIACAO, DATA_ATUALIZACAO) VALUES
(2, 'Fundamentos de Veículos Híbridos', 
 'Entenda como funcionam os sistemas híbridos e suas principais diferenças em relação aos veículos convencionais.', 
 'https://www.youtube.com/watch?v=3R8Vc0B5Z8E', 
 'S', SYSTIMESTAMP, SYSTIMESTAMP);

INSERT INTO T_ZYNT_AULAS_CURSO (CURSO_ID, TITULO, DESCRICAO, URL, ATIVO, DATA_CRIACAO, DATA_ATUALIZACAO) VALUES
(2, 'Sistemas de Propulsão Elétrica', 
 'Aprofunde-se nos motores elétricos, inversores e sistemas de controle de veículos elétricos.', 
 'https://www.youtube.com/watch?v=QpVxRU6f3-o', 
 'S', SYSTIMESTAMP, SYSTIMESTAMP);

------------------------------------------------------------
-- INSERÇÃO DE OFICINAS
------------------------------------------------------------
INSERT INTO T_ZYNT_OFICINAS (NOME_EMPREENDIMENTO, CNPJ, NOME_EMPRESA, LOCALIZACAO, CIDADE, ESTADO, ESPECIALIDADE, AVALIACAO, DATA_CRIACAO, DATA_ATUALIZACAO, STATUS) VALUES
('Auto Elétrica São Paulo', '12345678000190', 'Auto Elétrica SP Ltda', 'Av. Paulista, 1000 - Bela Vista', 'São Paulo', 'SP', 'Especializada em VHE', 4.5, SYSTIMESTAMP, SYSTIMESTAMP, 'APROVADA');

INSERT INTO T_ZYNT_OFICINAS (NOME_EMPREENDIMENTO, CNPJ, NOME_EMPRESA, LOCALIZACAO, CIDADE, ESTADO, ESPECIALIDADE, AVALIACAO, DATA_CRIACAO, DATA_ATUALIZACAO, STATUS) VALUES
('Híbrido Tech Oficina', '23456789000101', 'Híbrido Tech EIRELI', 'Rua das Flores, 500 - Centro', 'Rio de Janeiro', 'RJ', 'Manutenção de Veículos Híbridos', 4.8, SYSTIMESTAMP, SYSTIMESTAMP, 'APROVADA');

INSERT INTO T_ZYNT_OFICINAS (NOME_EMPREENDIMENTO, CNPJ, NOME_EMPRESA, LOCALIZACAO, CIDADE, ESTADO, ESPECIALIDADE, AVALIACAO, DATA_CRIACAO, DATA_ATUALIZACAO, STATUS) VALUES
('Elétrico Express', '34567890000112', 'Elétrico Express ME', 'Av. Atlântica, 2000 - Copacabana', 'Rio de Janeiro', 'RJ', 'Reparação de Veículos Elétricos', 4.2, SYSTIMESTAMP, SYSTIMESTAMP, 'APROVADA');

------------------------------------------------------------
-- INSERÇÃO DE SERVIÇOS DAS OFICINAS
------------------------------------------------------------
-- Serviços da Oficina 1
INSERT INTO T_ZYNT_OFICINA_SERVICOS (OFICINA_ID, NOME, DESCRICAO, ATIVO, DATA_CRIACAO, DATA_ATUALIZACAO) VALUES
(1, 'Troca de Bateria VHE', 'Substituição completa de baterias de veículos híbridos e elétricos com garantia.', 'S', SYSTIMESTAMP, SYSTIMESTAMP);

INSERT INTO T_ZYNT_OFICINA_SERVICOS (OFICINA_ID, NOME, DESCRICAO, ATIVO, DATA_CRIACAO, DATA_ATUALIZACAO) VALUES
(1, 'Diagnóstico de Sistema Elétrico', 'Análise completa do sistema elétrico de veículos VHE com equipamentos especializados.', 'S', SYSTIMESTAMP, SYSTIMESTAMP);

INSERT INTO T_ZYNT_OFICINA_SERVICOS (OFICINA_ID, NOME, DESCRICAO, ATIVO, DATA_CRIACAO, DATA_ATUALIZACAO) VALUES
(1, 'Manutenção Preventiva VHE', 'Check-up completo e manutenção preventiva para veículos híbridos e elétricos.', 'S', SYSTIMESTAMP, SYSTIMESTAMP);

-- Serviços da Oficina 2
INSERT INTO T_ZYNT_OFICINA_SERVICOS (OFICINA_ID, NOME, DESCRICAO, ATIVO, DATA_CRIACAO, DATA_ATUALIZACAO) VALUES
(2, 'Reparo de Motor Híbrido', 'Reparação especializada em motores híbridos de todas as marcas.', 'S', SYSTIMESTAMP, SYSTIMESTAMP);

INSERT INTO T_ZYNT_OFICINA_SERVICOS (OFICINA_ID, NOME, DESCRICAO, ATIVO, DATA_CRIACAO, DATA_ATUALIZACAO) VALUES
(2, 'Calibração de Sistema Híbrido', 'Ajuste e calibração dos sistemas híbridos para otimização de performance.', 'S', SYSTIMESTAMP, SYSTIMESTAMP);

-- Serviços da Oficina 3
INSERT INTO T_ZYNT_OFICINA_SERVICOS (OFICINA_ID, NOME, DESCRICAO, ATIVO, DATA_CRIACAO, DATA_ATUALIZACAO) VALUES
(3, 'Recuperação de Baterias', 'Serviço de recuperação e recondicionamento de baterias de veículos elétricos.', 'S', SYSTIMESTAMP, SYSTIMESTAMP);

INSERT INTO T_ZYNT_OFICINA_SERVICOS (OFICINA_ID, NOME, DESCRICAO, ATIVO, DATA_CRIACAO, DATA_ATUALIZACAO) VALUES
(3, 'Instalação de Carregador', 'Instalação de carregadores residenciais e comerciais para veículos elétricos.', 'S', SYSTIMESTAMP, SYSTIMESTAMP);

------------------------------------------------------------
-- INSERÇÃO DE MENTORIAS
------------------------------------------------------------
INSERT INTO T_ZYNT_MENTORIAS (NOME_COMPLETO, CPF, EMAIL, TELEFONE, DATA, DATA_CRIACAO, STATUS, TUTOR_ID, USUARIO_ID) VALUES
('João Silva', '12345678901', 'joao.silva@email.com', '(11) 98765-4321', DATE '2025-02-15', SYSTIMESTAMP, 'AGENDADA', 1, 1);

INSERT INTO T_ZYNT_MENTORIAS (NOME_COMPLETO, CPF, EMAIL, TELEFONE, DATA, DATA_CRIACAO, STATUS, TUTOR_ID, USUARIO_ID) VALUES
('Maria Santos', '23456789012', 'maria.santos@email.com', '(11) 97654-3210', DATE '2025-02-20', SYSTIMESTAMP, 'CONFIRMADA', 2, 2);

INSERT INTO T_ZYNT_MENTORIAS (NOME_COMPLETO, CPF, EMAIL, TELEFONE, DATA, DATA_CRIACAO, STATUS, TUTOR_ID, USUARIO_ID) VALUES
('Carlos Oliveira', '34567890123', 'carlos.oliveira@email.com', '(11) 96543-2109', DATE '2025-02-25', SYSTIMESTAMP, 'AGENDADA', 3, 3);

------------------------------------------------------------
-- INSERÇÃO DE PONTOS DE RECARGA
------------------------------------------------------------
INSERT INTO T_ZYNT_PONTOS_RECARGA (NOME, ENDERECO, TIPO_RECARGA, DATA_CRIACAO, DATA_ATUALIZACAO) VALUES
('Posto Shell - Av. Paulista', 'Av. Paulista, 2000 - Bela Vista, São Paulo - SP', 'AC_DC', SYSTIMESTAMP, SYSTIMESTAMP);

INSERT INTO T_ZYNT_PONTOS_RECARGA (NOME, ENDERECO, TIPO_RECARGA, DATA_CRIACAO, DATA_ATUALIZACAO) VALUES
('Shopping Iguatemi - Estacionamento', 'Av. Brigadeiro Faria Lima, 2232 - Pinheiros, São Paulo - SP', 'DC', SYSTIMESTAMP, SYSTIMESTAMP);

INSERT INTO T_ZYNT_PONTOS_RECARGA (NOME, ENDERECO, TIPO_RECARGA, DATA_CRIACAO, DATA_ATUALIZACAO) VALUES
('Centro Comercial Rio Sul', 'Rua Lauro Muller, 116 - Botafogo, Rio de Janeiro - RJ', 'AC', SYSTIMESTAMP, SYSTIMESTAMP);

INSERT INTO T_ZYNT_PONTOS_RECARGA (NOME, ENDERECO, TIPO_RECARGA, DATA_CRIACAO, DATA_ATUALIZACAO) VALUES
('Posto BR - Marginal Tietê', 'Av. Marginal Tietê, 5000 - Santana, São Paulo - SP', 'DC', SYSTIMESTAMP, SYSTIMESTAMP);

INSERT INTO T_ZYNT_PONTOS_RECARGA (NOME, ENDERECO, TIPO_RECARGA, DATA_CRIACAO, DATA_ATUALIZACAO) VALUES
('Aeroporto de Congonhas', 'Av. Washington Luís, s/n - Vila Congonhas, São Paulo - SP', 'AC_DC', SYSTIMESTAMP, SYSTIMESTAMP);

------------------------------------------------------------
-- COMMIT DAS TRANSAÇÕES
------------------------------------------------------------
COMMIT;

------------------------------------------------------------
-- VERIFICAÇÃO DOS DADOS INSERIDOS
------------------------------------------------------------
-- SELECT COUNT(*) FROM T_ZYNT_USUARIOS;        -- Deve retornar 5
-- SELECT COUNT(*) FROM T_ZYNT_TUTORES;         -- Deve retornar 3
-- SELECT COUNT(*) FROM T_ZYNT_CURSOS;          -- Deve retornar 3
-- SELECT COUNT(*) FROM T_ZYNT_AULAS_CURSO;     -- Deve retornar 5
-- SELECT COUNT(*) FROM T_ZYNT_OFICINAS;         -- Deve retornar 3
-- SELECT COUNT(*) FROM T_ZYNT_OFICINA_SERVICOS; -- Deve retornar 7
-- SELECT COUNT(*) FROM T_ZYNT_MENTORIAS;       -- Deve retornar 3
-- SELECT COUNT(*) FROM T_ZYNT_PONTOS_RECARGA;   -- Deve retornar 5

