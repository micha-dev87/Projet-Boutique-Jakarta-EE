use boutique_db;

-- Insertion des utilisateurs
INSERT INTO clients (nom, email, mot_de_passe) VALUES
('Jean Dupont', 'jean.dupont@email.com', 'motdepasse123'),
('Marie Martin', 'marie.martin@email.com', 'password456');

-- Insertion des produits
INSERT INTO produits (nom, description, prix) VALUES
('Ordinateur portable', 'PC portable 15 pouces, 16GB RAM, 512GB SSD', 999.99),
('Smartphone', 'Smartphone 6.5 pouces, 128GB, 5G', 699.99),
('Tablette', 'Tablette 10 pouces, 64GB, WiFi', 299.99),
('Casque audio', 'Casque sans fil avec réduction de bruit', 199.99),
('Souris sans fil', 'Souris ergonomique avec batterie longue durée', 49.99);