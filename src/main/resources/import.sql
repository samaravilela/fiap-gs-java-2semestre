-- Script de inicialização do banco de dados
-- Este arquivo é executado automaticamente pelo Hibernate em modo dev

-- Inserir tutores iniciais
INSERT INTO tutores (nome, especialidade, email, telefone, ativo, data_criacao, data_atualizacao) 
VALUES 
('Carlos Silva', 'Sistemas Elétricos e Baterias', 'carlos.silva@zyntrahe.com', '(11) 98765-4321', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Ana Paula Santos', 'Diagnóstico e Reparação VHE', 'ana.santos@zyntrahe.com', '(11) 98765-4322', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Roberto Oliveira', 'Segurança NR10 e Alta Tensão', 'roberto.oliveira@zyntrahe.com', '(11) 98765-4323', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Mariana Costa', 'Gestão Financeira e Precificação', 'mariana.costa@zyntrahe.com', '(11) 98765-4324', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Inserir cursos iniciais
INSERT INTO cursos (titulo, descricao, duracao, formato, preco, ativo, data_criacao, data_atualizacao)
VALUES
('Curso Completo de VHE', 'Capacitação completa em veículos híbridos e elétricos, desde fundamentos até técnicas avançadas de reparação', '120 horas', 'Online + Presencial', 'Consulte valores', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Curso de Segurança NR10', 'Curso de segurança NR10 com foco em práticas seguras em alta tensão para profissionais que trabalham com VHE', '40 horas', 'Online', 'Consulte valores', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Educação Financeira para Mecânicos', 'Palestras e módulos sobre precificação, comportamento profissional e gestão de lucros', '20 horas', 'Online', 'Consulte valores', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Especialização em Baterias VHE', 'Curso especializado em diagnóstico, manutenção e reparo de sistemas de baterias de veículos híbridos e elétricos', '60 horas', 'Online + Presencial', 'Consulte valores', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);




