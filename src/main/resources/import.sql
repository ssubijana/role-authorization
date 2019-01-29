INSERT INTO user (id, name, password) VALUES (1, 'user1', '$2a$04$GJ85Ihcglhbqac2zc3z3A.C3v55FMmN8.qGQ8FlNBCuyLtQ5/TyMO');
INSERT INTO user (id, name, password) VALUES (2, 'user2', '$2a$04$XWpgKkCQaVRuXjB5f1hzt.pDa2NAzntroH3bELICZy8R8Q0L0SShO');
INSERT INTO user (id, name, password) VALUES (3, 'user3', '$2a$04$bmmWXec6YkFIesIxv2gx7.NbxRcMJ4UoRKT9Qoqk09pKegfcr3Zla');

INSERT INTO role (id, description, name) VALUES (1, 'Admin role', 'ADMIN');
INSERT INTO role (id, description, name) VALUES (2, 'User role', 'USER');
INSERT INTO role (id, description, name) VALUES (3, 'Operational role', 'OPERATIONAL');

INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (1, 2);
INSERT INTO user_roles (user_id, role_id) VALUES (2, 3);
INSERT INTO user_roles (user_id, role_id) VALUES (3, 2);