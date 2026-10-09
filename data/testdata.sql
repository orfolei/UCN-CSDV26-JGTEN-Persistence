use "DMA-CSD-V26_10407700"

-- Kør efter persistencedb.sql. Scriptet kan køres igen: det tømmer tabellerne først.
-- id'er sættes manuelt (IDENTITY_INSERT), så fremmednøglerne altid passer.

DELETE FROM OrderLineItem;
DELETE FROM SaleOrder;
DELETE FROM Invoice;
DELETE FROM Freight;
DELETE FROM [customer];
DELETE FROM city;
DELETE FROM [stock];
DELETE FROM [warehouse];
DELETE FROM SupplierProduct;
DELETE FROM Supplier;
DELETE FROM GunReplica;
DELETE FROM Clothing;
DELETE FROM Equipment;
DELETE FROM Price;
DELETE FROM [Product];

INSERT INTO city (zipCode, city) VALUES
(9000, 'Aalborg'),
(8000, 'Aarhus C'),
(5000, 'Odense C'),
(2100, 'København Ø'),
(7100, 'Vejle');

-- reservedQuantity = antal på ordrer med deliveryState 'pending' (ordre 3 og 4)
SET IDENTITY_INSERT [Product] ON;
INSERT INTO [Product] (id, productNumber, "name", reservedQuantity) VALUES
(1, 1001, 'Cowboyhat', 0),
(2, 1002, 'Læderjakke', 1),
(3, 1003, 'Cowboystøvler', 0),
(4, 2001, 'Sadel', 1),
(5, 2002, 'Lasso', 2),
(6, 2003, 'Sporer', 0),
(7, 3001, 'Colt Single Action', 0),
(8, 3002, 'Winchester 1873', 0),
(9, 9999, 'Bandana', 0); -- har ingen pris: findByNumber skal give null
SET IDENTITY_INSERT [Product] OFF;

SET IDENTITY_INSERT Clothing ON;
INSERT INTO Clothing (id, "size", color, product_id) VALUES
(1, 'L', 'Brun', 1),
(2, 'M', 'Sort', 2),
(3, '43', 'Brun', 3);
SET IDENTITY_INSERT Clothing OFF;

SET IDENTITY_INSERT Equipment ON;
INSERT INTO Equipment (id, material, style, product_id) VALUES
(1, 'Læder', 'Western', 4),
(2, 'Hamp', 'Klassisk', 5),
(3, 'Stål', 'Texas', 6);
SET IDENTITY_INSERT Equipment OFF;

SET IDENTITY_INSERT GunReplica ON;
INSERT INTO GunReplica (id, calibre, material, product_id) VALUES
(1, '.45', 'Metal', 7),
(2, '.44-40', 'Træ/Metal', 8);
SET IDENTITY_INSERT GunReplica OFF;

-- Produkt 1 og 4 har to priser (nyeste skal vælges).
-- Produkt 3 har en fremtidig pris (må IKKE vælges før 2027).
SET IDENTITY_INSERT Price ON;
INSERT INTO Price (id, "timestamp", price, product_id) VALUES
(1, '2026-01-01 08:00:00', 299.95, 1),
(2, '2026-06-01 08:00:00', 349.95, 1),
(3, '2026-01-01 08:00:00', 1499.00, 2),
(4, '2026-01-01 08:00:00', 899.00, 3),
(5, '2026-01-01 08:00:00', 2499.00, 4),
(6, '2026-08-15 08:00:00', 2199.00, 4),
(7, '2026-01-01 08:00:00', 149.00, 5),
(8, '2026-01-01 08:00:00', 399.00, 6),
(9, '2026-01-01 08:00:00', 1899.00, 7),
(10, '2026-01-01 08:00:00', 3499.00, 8),
(11, '2027-01-01 08:00:00', 999.00, 3);
SET IDENTITY_INSERT Price OFF;

SET IDENTITY_INSERT Supplier ON;
INSERT INTO Supplier (id, "name", "address", country, phoneNo, email) VALUES
(1, 'Texas Leather Co.', '12 Main Street, Austin', 'USA', 51234567, 'sales@texasleather.com'),
(2, 'Nordisk Rideudstyr', 'Industrivej 4, Vejle', 'Danmark', 75821234, 'ordre@nordiskride.dk'),
(3, 'Replica Arms GmbH', 'Waffenstraße 9, Suhl', 'Tyskland', 36812345, 'info@replica-arms.de');
SET IDENTITY_INSERT Supplier OFF;

INSERT INTO SupplierProduct (supplier_id, product_id) VALUES
(1, 1), (1, 2), (1, 3), (1, 4),
(2, 4), (2, 5), (2, 6),
(3, 7), (3, 8);

INSERT INTO [warehouse] (id, "name", "description") VALUES
(1, 'Hovedlager', 'Aalborg - alle varer'),
(2, 'Butikslager', 'Vejle - lille lager i butikken');

-- Produkt 1 ligger på begge lagre (stock summeres).
-- Produkt 2 og 7 ligger under minStock. Produkt 7 har kun 1 stk: bestil 2 => "Ikke tilstrækkeligt lager".
INSERT INTO [stock] (id, quantity, product_id, minStock, warehouse_id) VALUES
(1, 20, 1, 5, 1),
(2, 5, 1, 2, 2),
(3, 3, 2, 5, 1),
(4, 12, 3, 4, 1),
(5, 6, 4, 2, 1),
(6, 40, 5, 10, 1),
(7, 15, 6, 5, 2),
(8, 1, 7, 3, 2),
(9, 4, 8, 2, 2);

-- customerType er 'private' eller 'club' (OrderDB.calcDiscount / calcFreight)
SET IDENTITY_INSERT [customer] ON;
INSERT INTO [customer] (id, "name", "address", zipCode, phoneNo, customerType) VALUES
(1, 'Anders Jensen', 'Vesterbro 10', 9000, 20304050, 'private'),
(2, 'Mette Hansen', 'Strøget 22', 8000, 31415926, 'private'),
(3, 'Lars Nielsen', 'Kongensgade 5', 5000, 42424242, 'private'),
(4, 'Sofie Pedersen', 'Østerbrogade 88', 2100, 55667788, 'private'),
(5, 'Aalborg Westernklub', 'Hobrovej 101', 9000, 98123456, 'club'),
(6, 'Vejle Cowboy Club', 'Dæmningen 3', 7100, 75757575, 'club');
SET IDENTITY_INSERT [customer] OFF;

SET IDENTITY_INSERT Freight ON;
INSERT INTO Freight (id, baseCost, freeThreshold) VALUES
(1, 50.00, 2500),   -- standard
(2, 149.00, 5000);  -- express
SET IDENTITY_INSERT Freight OFF;

SET IDENTITY_INSERT Invoice ON;
INSERT INTO Invoice (id, dueDate, paymentDate) VALUES
(1, '2026-09-15', '2026-09-10'), -- betalt
(2, '2026-10-01', NULL),         -- forfalden, ikke betalt
(3, '2026-10-30', NULL);         -- åben
SET IDENTITY_INSERT Invoice OFF;

-- deliveryState skrives med små bogstaver, som OrderDB gør ('pending', 'delivered')
SET IDENTITY_INSERT SaleOrder ON;
INSERT INTO SaleOrder (id, "date", deliveryState, customer_id, freight_id, invoice_id) VALUES
(1, '2026-09-01', 'delivered', 1, 1, 1),
(2, '2026-09-17', 'shipped', 5, 2, 2),
(3, '2026-10-05', 'pending', 3, 1, 3),
(4, '2026-10-06', 'pending', 1, 1, NULL); -- endnu ingen faktura
SET IDENTITY_INSERT SaleOrder OFF;

SET IDENTITY_INSERT OrderLineItem ON;
INSERT INTO OrderLineItem (id, quantity, saleOrder_id, product_id, price_id) VALUES
(1, 1, 1, 1, 1), -- ordre 1: hat til gammel pris
(2, 1, 1, 3, 4), -- ordre 1: støvler
(3, 1, 2, 7, 9), -- ordre 2: Colt (klub, over 1500 => rabat)
(4, 2, 2, 6, 8), -- ordre 2: sporer
(5, 1, 3, 4, 6), -- ordre 3: sadel til ny pris
(6, 2, 3, 5, 7), -- ordre 3: lasso
(7, 1, 4, 2, 3); -- ordre 4: jakke
SET IDENTITY_INSERT OrderLineItem OFF;
