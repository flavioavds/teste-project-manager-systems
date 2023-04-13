INSERT INTO tb_usuario (login, senha, nome) VALUES ('convidado', 'manager', 'Usuário convidado');
INSERT INTO tb_usuario (login, senha, nome) VALUES ('admin', 'suporte', 'Gestor');

INSERT INTO roles (usuario_id, administrador) VALUES (1, 0);
INSERT INTO roles (usuario_id, administrador) VALUES (2, 1); 

INSERT INTO tb_pais (nome, sigla, gentilico) VALUES ('Brasil', 'BR', 'Brasileiro');
INSERT INTO tb_pais (nome, sigla, gentilico) VALUES ('Argentina', 'AR', 'Argentino');
INSERT INTO tb_pais (nome, sigla, gentilico) VALUES ('Alemanha', 'AL', 'Alemão');