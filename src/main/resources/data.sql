INSERT INTO tb_usuario(login, senha, nome) VALUES ('convidado', '$2a$12$BhztA7j0FXqTmt8eN3PPreGSABrmFGvWSCnmtseICnSoKWDXv6y2y', 'Usuario convidado');
INSERT INTO tb_usuario(login, senha, nome) VALUES ('admin', '$2a$12$UDSlq3UGnFgjGXFDnsWgBuuwvNz52d4iYmjU8G0tDSG3sxHDoRozO', 'Gestor');

INSERT INTO roles (usuario_id, administrador) VALUES (1, 1);
INSERT INTO roles (usuario_id, administrador) VALUES (2, 2);

INSERT INTO tb_pais (nome, sigla, gentilico) VALUES ('Brasil', 'BR', 'Brasileiro');
INSERT INTO tb_pais (nome, sigla, gentilico) VALUES ('Argentina', 'AR', 'Argentino');
INSERT INTO tb_pais (nome, sigla, gentilico) VALUES ('Alemanha', 'AL', 'Alemão');