INSERT INTO customers (customer_id, customer_firstname, customer_lastname, customer_email, customer_phone, customer_tax_number, customer_password, customer_payment_method, discount_percentage)
VALUES 
(1, 'Johan', 'Andersen', 'johan.andersen@gmail.com', '5551234567', 'A1234567', 'J0h@nAnd3rsen', 'card', '00.00'),
(2, 'Matteo', 'Rossi', 'matteo.rossi@yahoo.com', '5552345678', 'B2345678', 'M@tte0Rossi', 'cash', '00.00'),
(3, 'Pierre', 'Dupont', 'pierre.dupont@gmail.com', '5553456789', 'C3456789', 'P13rreDup0nt', 'bill', '00.00'),
(4, 'Miguel', 'Garcia', 'miguel.garcia@yahoo.com', '5554567890', 'D4567890', 'M1gu3lG@rc1a', 'card', '00.00'),
(5, 'Andrei', 'Popescu', 'andrei.popescu@gmail.com', '5555678901', 'E5678901', 'Andr31P0p3scu', 'cheque', '00.00'),
(6, 'Hans', 'Schmidt', 'hans.schmidt@yahoo.com', '5556789012', 'F6789012', 'H@nsSchm1dt', 'other', '00.00'),
(7, 'Jakob', 'Jensen', 'jakob.jensen@gmail.com', '5557890123', 'G7890123', 'J@kobJ3nsen', 'card', '00.00'),
(8, 'Vladimir', 'Ivanov', 'vladimir.ivanov@yahoo.com', '5558901234', 'H8901234', 'Vl@dim1rIv@n0v', 'cash', '00.00'),
(9, 'Rafael', 'Fernandez', 'rafael.fernandez@gmail.com', '5559012345', 'I9012345', 'R@fa3lF3rn@nd3z', 'card', '00.00'),
(10, 'Luca', 'Ricci', 'luca.ricci@yahoo.com', '5550123456', 'J0123456', 'Luc@R1cc1', 'cheque', '00.00'),
(11, 'Ole', 'Olsen', 'ole.olsen@gmail.com', '5551234567', 'K1234567', '0l3Ols3n', 'card', '00.00'),
(12, 'Hugo', 'Garcia', 'hugo.garcia@yahoo.com', '5552345678', 'L2345678', 'Hug0G@rc1a', 'bill', '00.00'),
(13, 'Gustav', 'Andersson', 'gustav.andersson@gmail.com', '5553456789', 'M3456789', 'Gust@vAnd3rss0n', 'card', '00.00'),
(14, 'Marco', 'Rizzo', 'marco.rizzo@yahoo.com', '5554567890', 'N4567890', 'M@rc0Rizz0', 'cash', '00.00'),
(15, 'Alexandre', 'Lefevre', 'alexandre.lefevre@gmail.com', '5555678901', 'O5678901', 'Al3x@ndr3L3f3vr3', 'card', '00.00'),
(16, 'David', 'Martinez', 'david.martinez@yahoo.com', '5556789012', 'P6789012', 'D@v1dM@rt1n3z', 'card', '00.00'),
(17, 'Jan', 'Novak', 'jan.novak@gmail.com', '5557890123', 'Q7890123', 'J@nN0v@k', 'cheque', '00.00'),
(18, 'Antonio', 'Silva', 'antonio.silva@yahoo.com', '5558901234', 'R8901234', 'Ant0n10S1lv@', 'other', '00.00'),
(19, 'Mikhail', 'Kuznetsov', 'mikhail.kuznetsov@gmail.com', '5559012345', 'S9012345', 'M1kh@1lKuzn3ts0v', 'card', '00.00'),
(20, 'Thomas', 'Müller', 'thomas.muller@yahoo.com', '5550123456', 'T0123456', 'Th0m@sMüll3r', 'cash', '00.00'),
(21, 'Konstantinos', 'Papadopoulos', 'konstantinos.papadopoulos@gmail.com', '5551234567', 'U1234567', 'K0nst@nt1n0sP@p@d0p0ul0s', 'card', '00.00'),
(22, 'Lukas', 'Novotny', 'lukas.novotny@yahoo.com', '5552345678', 'V2345678', 'Luk@sNov0tny', 'cheque', '00.00'),
(23, 'Filip', 'Nowak', 'filip.nowak@gmail.com', '5553456789', 'W3456789', 'F1l1pN0w@k', 'other', '00.00'),
(24, 'Giovanni', 'Ricci', 'giovanni.ricci@yahoo.com', '5554567890', 'X4567890', 'G10v@nn1R1cc1', 'bill', '00.00'),
(25, 'Andreas', 'Johansson', 'andreas.johansson@gmail.com', '5555678901', 'Y5678901', 'Andr3@sJ0h@nss0n', 'card', '00.00');

INSERT INTO address (address_id, address_country, address_state, address_postal_code, address_district, address_avenue, address_street, address_rest) 
VALUES 
(1, 'United States', 'California', '90001', 'Downtown', 'Main', 'Oak Street', 'Apt 101'),
(2, 'Canada', 'Ontario', 'M5A 1Z9', 'Yorkville', 'King', 'Queen Street', 'Suite 200'),
(3, 'United Kingdom', 'England', 'SW1A 1AA', 'Westminster', 'Downing', 'Whitehall', 'Floor 3'),
(4, 'Australia', 'New South Wales', '2000', 'Sydney CBD', 'George', 'Martin Place', 'Level 4'),
(5, 'Germany', 'Bavaria', '80331', 'Altstadt-Lehel', 'Marienplatz', 'Dienerstrasse', 'Room 5'),
(6, 'France', 'Île-de-France', '75001', 'Paris 1er Arrondissement', 'Rue de Rivoli', 'Place Vendôme', 'Apartment 3B'),
(7, 'Italy', 'Lombardy', '20121', 'Milan', 'Corso Venezia', 'Via della Spiga', 'Unit 10'),
(8, 'Spain', 'Community of Madrid', '28001', 'Madrid', 'Paseo de la Castellana', 'Calle de Serrano', 'Piso 2'),
(9, 'Brazil', 'São Paulo', '01310-000', 'Consolação', 'Avenida Paulista', 'Rua Augusta', 'Apartamento 301'),
(10, 'Japan', 'Tokyo', '100-0001', 'Chiyoda', 'Chiyoda', 'Kokyo', 'Room 501'),
(11, 'China', 'Beijing', '100000', 'Dongcheng', 'Wangfujing', 'Dongdan', 'Unit 601'),
(12, 'India', 'Maharashtra', '400001', 'Mumbai', 'Nariman Point', 'Marine Drive', 'Flat 402'),
(13, 'South Korea', 'Seoul', '100-011', 'Jung', 'Myeong-dong', 'Namdaemun-ro', 'Room 701'),
(14, 'Mexico', 'Mexico City', '06000', 'Cuauhtémoc', 'Paseo de la Reforma', 'Avenida Juárez', 'Departamento 803'),
(15, 'Russia', 'Moscow', '101000', 'Tverskoy', 'Tverskaya', 'Kremlin Embankment', 'Flat 902'),
(16, 'Nigeria', 'Lagos', '100001', 'Lagos Island', 'Broad Street', 'Marina', 'Apartment 1004'),
(17, 'Saudi Arabia', 'Riyadh', '11415', 'Al Olaya', 'King Fahd Road', 'Olaya Street', 'Floor 12'),
(18, 'South Africa', 'Gauteng', '2000', 'Johannesburg', 'Jan Smuts Avenue', 'Sandton', 'Suite 1402'),
(19, 'Turkey', 'Istanbul', '34433', 'Şişli', 'Cumhuriyet', 'Taksim Square', 'Kat 5'),
(20, 'Argentina', 'Buenos Aires', 'C1002AAQ', 'San Nicolás', 'Avenida Corrientes', 'Obelisco', 'Departamento 1801'),
(21, 'Egypt', 'Cairo Governorate', '11511', 'Nasr City', 'Abbas Al Akkad', 'City Stars', 'Flat 2001'),
(22, 'Indonesia', 'Jakarta', '10110', 'Menteng', 'Jalan MH Thamrin', 'Sudirman Central Business District', 'Unit 2201'),
(23, 'Pakistan', 'Sindh', '75530', 'Karachi', 'I.I. Chundrigar Road', 'Clifton', 'Apartment 2401'),
(24, 'Bangladesh', 'Dhaka Division', '1000', 'Dhaka', 'Motijheel', 'Gulistan', 'Flat 2602'),
(25, 'Philippines', 'Metro Manila', '1000', 'Manila', 'Taft Avenue', 'Roxas Boulevard', 'Unit 2801');


INSERT INTO customer_address (customer_id_fk1, address_id_fk1)
VALUES 
('1', '1'),
('2', '2'),
('3', '3'),
('4', '4'),
('5', '5'),
('6', '6'),
('7', '7'),
('8', '8'),
('9', '9'),
('10', '10'),
('11', '11'),
('12', '12'),
('13', '13'),
('14', '14'),
('15', '15'),
('16', '16'),
('17', '17'),
('18', '18'),
('19', '19'),
('20', '20'),
('21', '21'),
('22', '22'),
('23', '23'),
('24', '24'),
('25', '25');

INSERT INTO furniture_items (name, category_id, supplier_id, price, stock_quantity) VALUES 
('Elegant Wardrobe', 1, 1, 785.24, 542),
('Vintage Nightstand', 2, 2, 324.67, 678),
('Contemporary Bed', 5, 3, 1236.58, 854),
('Modern Desk', 6, 4, 549.78, 1236),
('Rustic Wardrobe', 1, 1, 867.39, 921),
('Classic Nightstand', 2, 2, 457.32, 315),
('Industrial Bed', 5, 3, 1587.43, 432),
('Scandinavian Desk', 6, 4, 639.51, 789),
('Antique Wardrobe', 1, 1, 1123.76, 108),
('Mid-Century Nightstand', 2, 2, 789.45, 567),
('French Bed', 5, 3, 1892.34, 643),
('Glass Desk', 6, 4, 845.63, 421),
('Sleek Wardrobe', 1, 1, 932.89, 276),
('Minimalist Nightstand', 2, 2, 287.91, 894),
('Platform Bed', 5, 3, 1346.79, 732),
('Executive Desk', 6, 4, 978.56, 1500),
('Country Wardrobe', 1, 1, 786.25, 639),
('Floating Nightstand', 2, 2, 372.85, 433),
('Canopy Bed', 5, 3, 1998.99, 321),
('Corner Desk', 6, 4, 587.28, 1057);

INSERT INTO furniture_categories (category_id, name) 
VALUES 
-- Wardrobe Category (Category ID 1)
(1, 'Wardrobe'),
-- Nightstand Category (Category ID 2)
(2, 'Nightstand'),
-- Bed Category (Category ID 3)
(3, 'Bed'),
-- Desk Category (Category ID 4)
(4, 'Desk');

INSERT INTO furniture_suppliers (supplier_id, name, contact_person, phone, email)
VALUES 
-- Suppliers 1-5
(1, 'European Furnishings Ltd', 'John Smith', '1234567890', 'john.smith@europeanfurnishings.com'),
(2, 'EuroStyle Furnishings', 'Maria Garcia', '2345678901', 'maria.garcia@eurostyle.com'),
(3, 'Scandinavian Design Imports', 'Hans Nielsen', '3456789012', 'hans.nielsen@scandinaviandesign.com'),
(4, 'French Furniture Creations', 'Sophie Dubois', '4567890123', 'sophie.dubois@frenchfurniture.com'),
(5, 'Italian Interiors Srl', 'Giovanni Rossi', '5678901234', 'giovanni.rossi@italianinteriors.it'),
-- Suppliers 6-10
(6, 'Nordic Woodcraft', 'Erik Hansen', '6789012345', 'erik.hansen@nordicwoodcraft.com'),
(7, 'German Furniture Solutions GmbH', 'Andreas Müller', '7890123456', 'andreas.mueller@germanfurnituresolutions.com'),
(8, 'Swiss Style Furnishings SA', 'Anna Müller', '8901234567', 'anna.mueller@swissstylefurnishings.com'),
(9, 'Spanish Furnishings SL', 'Juan López', '9012345678', 'juan.lopez@spanishfurnishings.es'),
(10, 'Danish Design House', 'Lars Jensen', '0123456789', 'lars.jensen@danishdesignhouse.dk'),
-- Suppliers 11-15
(11, 'Belgian Furniture Creations', 'Sophie Declercq', '1023456789', 'sophie.declercq@belgianfurniture.be'),
(12, 'Dutch Design Furnishings BV', 'Pieter van der Berg', '1234567890', 'pieter.vanderberg@dutchdesignfurnishings.nl'),
(13, 'Austrian Alpine Furnishings', 'Markus Schneider', '2345678901', 'markus.schneider@austrianalpinefurnishings.at'),
(14, 'Portuguese Furniture Imports', 'Ana Silva', '3456789012', 'ana.silva@portuguesefurnitureimports.pt'),
(15, 'Finnish Furniture Co.', 'Mika Virtanen', '4567890123', 'mika.virtanen@finnishfurniture.fi'),
-- Suppliers 16-20
(16, 'Polish Woodcrafters', 'Jan Kowalski', '5678901234', 'jan.kowalski@polishwoodcrafters.pl'),
(17, 'Hungarian Heritage Furnishings', 'Ágnes Nagy', '6789012345', 'agnes.nagy@hungarianheritagefurnishings.hu'),
(18, 'Czech Republic Furniture Exporters', 'Pavel Novák', '7890123456', 'pavel.novak@czechfurniture.cz'),
(19, 'Greek Island Furnishings', 'Nikos Papadopoulos', '8901234567', 'nikos.papadopoulos@greekislandfurnishings.gr'),
(20, 'Irish Interior Designs', 'Siobhan Murphy', '9012345678', 'siobhan.murphy@irishinteriordesigns.ie'),
-- Suppliers 21-25
(21, 'Estonian Elegance Furnishings', 'Kristjan Tamm', '0123456789', 'kristjan.tamm@estonianelegancefurnishings.ee'),
(22, 'Latvian Luxury Furniture', 'Anna Ozola', '1234567890', 'anna.ozola@latvianluxuryfurniture.lv'),
(23, 'Lithuanian Living Solutions', 'Tomas Petrauskas', '2345678901', 'tomas.petrauskas@lithuanianlivingsolutions.lt'),
(24, 'Slovakian Style Furnishings', 'Miroslav Kováč', '3456789012', 'miroslav.kovac@slovakianstylefurnishings.sk'),
(25, 'Luxembourg Furniture Concepts', 'Sophie Müller', '4567890123', 'sophie.mueller@luxembourgfurniture.lu');

INSERT INTO furniture_items (name, category_id, supplier_id, price, stock_quantity) 
VALUES 
-- Wardrobes (Category ID 1)
('Elegant Wardrobe', 1, 1, 785.24, 542),
('Rustic Wardrobe', 1, 1, 867.39, 921),
('Antique Wardrobe', 1, 1, 1123.76, 108),
('Sleek Wardrobe', 1, 1, 932.89, 276),
('Country Wardrobe', 1, 1, 786.25, 639),
-- Nightstands (Category ID 2)
('Vintage Nightstand', 2, 2, 324.67, 678),
('Classic Nightstand', 2, 2, 457.32, 315),
('Mid-Century Nightstand', 2, 2, 789.45, 567),
('Minimalist Nightstand', 2, 2, 287.91, 894),
('Floating Nightstand', 2, 2, 372.85, 433),
-- Beds (Category ID 3)
('Contemporary Bed', 3, 3, 1236.58, 854),
('Industrial Bed', 3, 3, 1587.43, 432),
('French Bed', 3, 3, 1892.34, 643),
('Platform Bed', 3, 3, 1346.79, 732),
('Canopy Bed', 3, 3, 1998.99, 321),
-- Desks (Category ID 4)
('Modern Desk', 4, 4, 549.78, 1236),
('Scandinavian Desk', 4, 4, 639.51, 789),
('Glass Desk', 4, 4, 845.63, 421),
('Executive Desk', 4, 4, 978.56, 1500),
('Corner Desk', 4, 4, 587.28, 1057);

INSERT INTO workers (worker_id, worker_name, worker_surname, worker_phone, worker_email, worker_date_of_birth, worker_department, worker_password, worker_ssn)
VALUES
-- Worker 1
(1, 'Lukas', 'Müller', '1234567890', 'lukas.muller@example.com', '1991/02/19', 'sales', 'password1', '123456789'),
-- Worker 2
(2, 'Sophie', 'Schneider', '2345678901', 'sophie.schneider@example.com', '1989/05/10', 'accounting', 'password2', '234567890'),
-- Worker 3
(3, 'Piotr', 'Nowak', '3456789012', 'piotr.nowak@example.com', '1994/09/27', 'driver', 'password3', '345678901'),
-- Worker 4
(4, 'Ana', 'Silva', '4567890123', 'ana.silva@example.com', '1985/12/15', 'laborer', 'password4', '456789012'),
-- Worker 5
(5, 'Maxim', 'Ivanov', '5678901234', 'maxim.ivanov@example.com', '1987/07/03', 'executive', 'password5', '567890123'),
-- Worker 6
(6, 'Elena', 'Petrova', '6789012345', 'elena.petrova@example.com', '1990/03/22', 'IT', 'password6', '678901234'),
-- Worker 7
(7, 'Thomas', 'Schmidt', '7890123456', 'thomas.schmidt@example.com', '1983/08/11', 'sales', 'password7', '789012345'),
-- Worker 8
(8, 'Sophie', 'Dupont', '8901234567', 'sophie.dupont@example.com', '1993/01/28', 'accounting', 'password8', '890123456'),
-- Worker 9
(9, 'Antonio', 'Ricci', '9012345678', 'antonio.ricci@example.com', '1986/06/17', 'driver', 'password9', '901234567'),
-- Worker 10
(10, 'Maria', 'Garcia', '0123456789', 'maria.garcia@example.com', '1988/10/04', 'laborer', 'password10', '012345678'),
-- Worker 11
(11, 'Jan', 'Novak', '1234567890', 'jan.novak@example.com', '1992/04/20', 'executive', 'password11', '123456789'),
-- Worker 12
(12, 'Anastasia', 'Ivanova', '2345678901', 'anastasia.ivanova@example.com', '1984/11/09', 'IT', 'password12', '234567890'),
-- Worker 13
(13, 'Giovanni', 'Rossi', '3456789012', 'giovanni.rossi@example.com', '1989/07/14', 'sales', 'password13', '345678901'),
-- Worker 14
(14, 'Martina', 'Müller', '4567890123', 'martina.muller@example.com', '1995/02/02', 'accounting', 'password14', '456789012'),
-- Worker 15
(15, 'Mariusz', 'Kowalski', '5678901234', 'mariusz.kowalski@example.com', '1982/05/18', 'driver', 'password15', '567890123'),
-- Worker 16
(16, 'Elise', 'Dubois', '6789012345', 'elise.dubois@example.com', '1987/09/30', 'laborer', 'password16', '678901234'),
-- Worker 17
(17, 'Sven', 'Eriksson', '7890123456', 'sven.eriksson@example.com', '1991/03/06', 'executive', 'password17', '789012345'),
-- Worker 18
(18, 'Sophia', 'Meier', '8901234567', 'sophia.meier@example.com', '1986/12/12', 'IT', 'password18', '890123456'),
-- Worker 19
(19, 'José', 'González', '9012345678', 'jose.gonzalez@example.com', '1984/08/25', 'sales', 'password19', '901234567'),
-- Worker 20
(20, 'Marie', 'Martin', '0123456789', 'marie.martin@example.com', '1988/11/07', 'accounting', 'password20', '012345678'),
-- Worker 21
(21, 'Andrzej', 'Duda', '1234567890', 'andrzej.duda@example.com', '1993/06/16', 'driver', 'password21', '123456789'),
-- Worker 22
(22, 'Chiara', 'Rossi', '2345678901', 'chiara.rossi@example.com', '1985/04/23', 'laborer', 'password22', '234567890'),
-- Worker 23
(23, 'Alexander', 'Schulz', '3456789012', 'alexander.schulz@example.com', '1990/08/19', 'executive', 'password23', '345678901'),
-- Worker 24
(24, 'Natalia', 'Kovač', '4567890123', 'natalia.kovac@example.com', '1989/01/12', 'IT', 'password24', '456789012'),
-- Worker 25
(25, 'Lucas', 'Andersen', '5678901234', 'lucas.andersen@example.com', '1983/07/28', 'sales', 'password25', '567890123');

