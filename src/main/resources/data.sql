-- ##########################
-- TABLE: User
-- ##########################
INSERT INTO users (id, name, email, password ) VALUES
                                                       (1, 'Alice Johnson', 'alice@mail.com', 'password'),
                                                       (2, 'Bob Smith', 'bob@mail.com', 'password'),
                                                       (3, 'Carol White', 'carol@mail.com', 'password'),
                                                       (4, 'David Brown', 'david@mail.com', 'password'),
                                                       (5, 'Eva Green', 'eva@mail.com', 'password');

-- ##########################
-- TABLE: Project
-- ##########################
INSERT INTO project (id, name, description, user_id) VALUES
                                                (1, 'Website Redesign', 'Refaire le design du site web', 1),
                                                (2, 'Mobile App', 'Développement de l’application mobile', 1),
                                                (3, 'Marketing Campaign', 'Campagne marketing Q2', 2);

-- ##########################
-- TABLE: Task
-- ##########################
INSERT INTO task (id, title, description, status, priority, deadline, completed_at, project_id) VALUES
                                                                                                   (1, 'Design homepage', 'Créer la maquette de la page d’accueil', 'TODO', 'HIGH', '2026-03-25', NULL, 1),
                                                                                                   (2, 'Implement login', 'Ajouter la connexion utilisateur', 'DOING', 'MEDIUM', '2026-03-27', NULL, 2),
                                                                                                   (3, 'Set up database', 'Installer et configurer la DB', 'TODO', 'HIGH', '2026-03-26', NULL, 2),
                                                                                                   (4, 'Create ad banners', 'Créer les visuels pour la campagne', 'TODO', 'LOW', '2026-03-30', NULL, 3),
                                                                                                   (5, 'Test mobile app', 'Tester toutes les fonctionnalités', 'TODO', 'MEDIUM', '2026-03-28', NULL, 2);

-- ##########################
-- TABLE: Tag
-- ##########################
INSERT INTO tag (id, name, user_id) VALUES
                                        (1, 'Urgent', 1),
                                        (2, 'Frontend', 1),
                                        (3, 'Backend', 2),
                                        (4, 'Marketing', 5),
                                        (5, 'Bug', 3);

-- ##########################
-- TABLE: TaskTag (N-N)
-- ##########################
INSERT INTO task_tag (id, task_id, tag_id) VALUES
                                              (1, 1, 1),
                                              (2, 1, 2),
                                              (3, 2, 3),
                                              (4, 3, 3),
                                              (5, 4, 4),
                                              (6, 5, 5),
                                              (7, 2, 1);