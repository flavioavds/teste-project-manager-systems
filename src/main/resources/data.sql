INSERT INTO tb_usuario(login, senha, nome) VALUES ('convidado', 'manager', 'Usuario convidado');
INSERT INTO tb_usuario(login, senha, nome) VALUES ('admin', 'suporte', 'Gestor');

INSERT INTO roles (usuario_id, roles) VALUES (1, 1);
INSERT INTO roles (usuario_id, roles) VALUES (2, 2);

INSERT INTO tb_pais (nome, sigla, gentilico) VALUES ('Brasil', 'BR', 'Brasileiro');
INSERT INTO tb_pais (nome, sigla, gentilico) VALUES ('Argentina', 'AR', 'Argentino');
INSERT INTO tb_pais (nome, sigla, gentilico) VALUES ('Alemanha', 'AL', 'Alemão');