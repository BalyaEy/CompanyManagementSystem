
--CREATE TYPE ODEME_YONTEMI AS ENUM ('nakit','kart','çek','senet','diğer');
CREATE TYPE PAYMENT_METHOD AS ENUM ('cash','card','cheque','bill','other');

ALTER TABLE customers
ADD COLUMN discount_percentage DECIMAL(4, 2);
CREATE TABLE customers (
customer_id serial       PRIMARY KEY,
customer_firstname       varchar(255) ,
customer_lastname        varchar(255) ,
customer_email           varchar(255)          NOT NULL,
customer_phone           char(15)              NOT NULL,
customer_tax_number      char(10)    UNIQUE    NOT NULL,
customer_payment_method  PAYMENT_METHOD        NOT NULL,
customer_password        varchar(255)          NOT NULL
);




CREATE TABLE customer_address (
    customer_id_fk1  INT UNIQUE,
    address_id_fk1   INT UNIQUE,
    PRIMARY KEY (customer_id_fk1, address_id_fk1),
    FOREIGN KEY (customer_id_fk1) REFERENCES customers(customer_id),
    FOREIGN KEY (address_id_fk1) REFERENCES address(address_id)
);

CREATE TABLE address(
address_id    serial PRIMARY KEY,
address_country      varchar(255),
address_state        varchar(255),
address_postal_code  char(5),
address_district     varchar(255),
address_avenue       varchar(255),
address_street       varchar(255),
address_rest         varchar(255)
);

--DROP TYPE shipmentstatus;
--CREATE TYPE KargoDurum AS ENUM ('hazır','kargoda','onaylanmadı','onaylandı');
--CREATE TYPE ShipmentStatus AS ENUM ('ready','shipped','declined','approved');
CREATE TYPE ShipmentStatus AS ENUM ('READY', 'SHIPPED', 'DECLINED', 'APPROVED', 'PENDING');
ALTER TYPE ShipmentStatus ADD VALUE 'AWAITING_CONFIRMATION';
--ALTER TABLE orders ALTER COLUMN order_state TYPE ShipmentStatusNew USING order_state::text::ShipmentStatusNew;
--ALTER TABLE orders ADD column order_state ShipmentStatus;
--ALTER TABLE orders ADD CONSTRAINT fk_customer_address_fk2 FOREIGN KEY (customer_address_id_fk2) REFERENCES customer_address(address_id_fk1);
--ALTER TABLE orders ADD COLUMN customer_address_CustomerIdFK INT NOT NULL;
--ALTER TABLE orders ADD COLUMN customer_address_AddressIdFK INT NOT NULL;
--ALTER TABLE orders ADD COLUMN order_total_amount decimal(10,2) NOT NULL;
CREATE TABLE orders (
order_id serial                      PRIMARY KEY,
customer_id_fk2           int        NOT NULL,
order_state               ShipmentStatus NOT NULL,
order_date                date       NOT NULL,
customer_address_CustomerIdFK           int        NOT NULL,
customer_address_AddressIdFK            int        NOT NULL,
FOREIGN KEY (customer_address_CustomerIdFK, customer_address_AddressIdFK) REFERENCES customer_address(customer_id_fk1, address_id_fk1),
FOREIGN KEY (customer_id_fk2) REFERENCES customers(customer_id)
);

--ALTER TABLE shipments DROP order_id_fk1;
--ALTER TABLE shipments ADD order_id_fk1 int NOT NULL;
--ALTER TABLE shipments ADD CONSTRAINT fk_order_id_fk1 FOREIGN KEY (order_id_fk1) REFERENCES orders(order_id);

CREATE TABLE shipments(
shipment_id serial PRIMARY KEY,
order_id_fk1   int NOT NULL,
shipment_date  date NOT NULL,
FOREIGN KEY (order_id_fk1) REFERENCES orders(order_id)
);

CREATE TYPE departments AS ENUM ('sales','accounting','driver','laborer','executive','IT');
CREATE TABLE workers(
worker_id serial PRIMARY KEY,
worker_name varchar(255)      NOT NULL,
worker_surname varchar(255),
worker_phone char(11)         NOT NULL , 
worker_email varchar(255)     NOT null,
worker_date_of_birth date,
worker_department departments NOT null,
worker_password varchar(255)  NOT NULL,
worker_ssn char(11)           NOT null
);




CREATE TABLE furniture_categories (
    category_id serial PRIMARY KEY,
    name varchar(255) NOT NULL
);

CREATE TABLE furniture_suppliers (
    supplier_id serial PRIMARY KEY,
    name varchar(255) NOT NULL,
    contact_person varchar(255),
    phone varchar(15),
    email varchar(255)
);
ALTER TABLE furniture_items ADD discounted_price decimal(10,2);
CREATE TABLE furniture_items (
    item_id serial PRIMARY KEY,
    name varchar(255) NOT NULL,
    category_id INT,
    supplier_id INT,
    price decimal(10, 2),
    stock_quantity INT,
    CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES furniture_categories(category_id),
    CONSTRAINT fk_supplier FOREIGN KEY (supplier_id) REFERENCES furniture_suppliers(supplier_id)
);

ALTER TABLE furniture_order_items
ADD COLUMN is_selected_for_shipping BOOLEAN DEFAULT FALSE;
CREATE TABLE furniture_order_items (
    order_item_id serial PRIMARY KEY,
    order_id INT,
    item_id INT,
    quantity INT,
    price decimal(10, 2),
    CONSTRAINT fk_orders FOREIGN KEY (order_id) REFERENCES orders(order_id),
    CONSTRAINT fk_item FOREIGN KEY (item_id) REFERENCES furniture_items(item_id)
);















SELECT * FROM customers;
SELECT * FROM customer_address;
SELECT * FROM address;
SELECT * FROM orders ;
SELECT * FROM shipments ;
SELECT * FROM workers;
SELECT * FROM furniture_categories;
SELECT * FROM furniture_suppliers;
SELECT * FROM furniture_items;
SELECT * FROM furniture_order_items;

