use "DMA-CSD-V26_10407700"

CREATE TABLE [Product](
id int IDENTITY(1,1) PRIMARY KEY NOT NULL,
productNumber int,
"name" varchar(64),
reservedQuantity int,
)

CREATE TABLE Price(
id int IDENTITY(1,1) PRIMARY KEY NOT NULL,
"timestamp" datetime,
price float,
product_id int,

	CONSTRAINT fk_price_product_id
	FOREIGN KEY(product_id)
	REFERENCES [Product](id),
)

CREATE TABLE Equipment(
id int IDENTITY(1,1) NOT NULL,
material varchar(64),
style varchar(64),
product_id int,

	CONSTRAINT fk_equipment_product_id
	FOREIGN KEY(product_id)
	REFERENCES [Product](id),
)

CREATE TABLE Clothing(
id int identity(1,1) NOT NULL,
"size" varchar(16),
color varchar(16),
product_id int,

	CONSTRAINT fk_clothing_product_id
	FOREIGN KEY(product_id)
	REFERENCES [Product](id),
)

CREATE TABLE GunReplica(
id int identity(1,1) not null,
calibre varchar(16),
material varchar(16),
product_id int,

	CONSTRAINT fk_gunreplica_product_id
	FOREIGN KEY(product_id)
	REFERENCES [Product](id),
)

CREATE TABLE Supplier(
id int identity(1,1) PRIMARY KEY not null,
"name" varchar(64),
"address" varchar(64),
country varchar(64),
phoneNo int,
email varchar(64),


)

CREATE TABLE SupplierProduct(
supplier_id int,
product_id int,

	CONSTRAINT fk_supplier_id
	FOREIGN KEY(supplier_id)
	REFERENCES Supplier(id),

	CONSTRAINT fk_supplierproduct_product_id
	FOREIGN KEY(product_id)
	REFERENCES [Product](id),
)

CREATE TABLE [stock](
id int NOT NULL,
quantity int,
product_id int,
minStock int,
warehouse_id int,
)

CREATE TABLE [warehouse](
id int NOT NULL,
"name" varchar(64),
"description" varchar(128),
)

CREATE TABLE city(
zipCode int PRIMARY KEY NOT NULL,
city varchar(64)
)

CREATE TABLE [customer](
id int IDENTITY(1,1) PRIMARY KEY NOT NULL,
"name" varchar(64),
"address" varchar(64),
zipCode int,
phoneNo int,
CONSTRAINT fk_customer_zipcode
	FOREIGN KEY(zipCode)
	REFERENCES city (zipCode)
	ON DELETE CASCADE
	ON UPDATE CASCADE
)

CREATE TABLE Freight(
id int IDENTITY(1,1) PRIMARY KEY NOT NULL,
baseCost float,
freeThreshold int,
)

CREATE TABLE Invoice(
id int IDENTITY(1,1) PRIMARY KEY NOT NULL,
dueDate date,
paymentDate date
)


CREATE TABLE SaleOrder(
id int IDENTITY(1,1) NOT NULL,
"date" date,
deliveryState varchar(16),
customer_id int,
freight_id int,
invoice_id int,

CONSTRAINT pk_saleorder_id PRIMARY KEY(id),

	CONSTRAINT fk_customer_id 
	FOREIGN KEY(customer_id) 
	REFERENCES customer(id),

	CONSTRAINT fk_saleorder_freight_id
	FOREIGN KEY(freight_id)
	REFERENCES Freight(id),

	CONSTRAINT fk_saleorder_invoice_id
	FOREIGN KEY(invoice_id)
	REFERENCES Invoice(id)
)

CREATE TABLE OrderLineItem(
id int IDENTITY(1,1) PRIMARY KEY NOT NULL,
quantity int,
saleOrder_id int,
product_id int,
price_id int,

	CONSTRAINT fk_orderlineitem_saleOrder_id
	FOREIGN KEY(saleOrder_id)
	REFERENCES SaleOrder(id),

	CONSTRAINT fk_orderlineitem_product_id
	FOREIGN KEY(product_id)
	REFERENCES [Product](id),

	CONSTRAINT fk_orderlineitem_price_id
	FOREIGN KEY(price_id)
	REFERENCES Price(id),
)