-- ##########################
-- TABLE: User
-- ##########################
INSERT INTO users (id, name, email, password, role ) VALUES
                                                       (default, 'Alice Johnson', 'alice@mail.com', 'password', 'ADMIN'),
                                                       (default, 'Bob Smith', 'bob@mail.com', 'password', 'USER'),
                                                       (default, 'Carol White', 'carol@mail.com', 'password', 'USER'),
                                                       (default, 'David Brown', 'david@mail.com', 'password', 'USER'),
                                                       (default, 'Eva Green', 'eva@mail.com', 'password', 'USER');

-- ##########################
-- TABLE: Project
-- ##########################
INSERT INTO project (id, title, description, user_id) VALUES
                                                (default, 'Website Redesign', 'Refaire le design du site web', 1),
                                                (default, 'Mobile App', 'Développement de l’application mobile', 1),
                                                (default, 'Marketing Campaign', 'Campagne marketing Q2', 2);

-- ##########################
-- TABLE: Task
-- ##########################
INSERT INTO task (id, title, description, status, priority, deadline, completed_at, project_id) VALUES
                                                                                                   (default, 'Design homepage', 'Créer la maquette de la page d’accueil', 'TODO', 'HIGH', '2026-03-25', NULL, 1),
                                                                                                   (default, 'Implement login', 'Ajouter la connexion utilisateur', 'DOING', 'MEDIUM', '2026-03-27', NULL, 2),
                                                                                                   (default, 'Set up database', 'Installer et configurer la DB', 'TODO', 'HIGH', '2026-03-26', NULL, 2),
                                                                                                   (default, 'Create ad banners', 'Créer les visuels pour la campagne', 'TODO', 'LOW', '2026-03-30', NULL, 3),
                                                                                                   (default, 'Test mobile app', 'Tester toutes les fonctionnalités', 'TODO', 'MEDIUM', '2026-03-28', NULL, 2);

-- ##########################
-- TABLE: Tag
-- ##########################
INSERT INTO tag (id, name, user_id) VALUES
                                        (default, 'Urgent', 1),
                                        (default, 'Frontend', 1),
                                        (default, 'Backend', 2),
                                        (default, 'Marketing', 5),
                                        (default, 'Bug', 3);

-- ##########################
-- TABLE: TaskTag (N-N)
-- ##########################
INSERT INTO task_tag (id, task_id, tag_id) VALUES
                                              (default, 1, 1),
                                              (default, 1, 2),
                                              (default, 2, 3),
                                              (default, 3, 3),
                                              (default, 4, 4),
                                              (default, 5, 5),
                                              (default, 2, 1);

