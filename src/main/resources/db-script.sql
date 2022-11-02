CREATE TABLE customers(
                          id VARCHAR(36) PRIMARY KEY ,
                          name VARCHAR(100) NOT NULL ,
                          address VARCHAR(300) NOT NULL
);

INSERT INTO customers (id,name,address)
VALUES (UUID(),'Manelka','panadura'),
       (UUID(),'Kasun','Panadura'),
       (UUID(),'Madhushan','Kandy'),
       (UUID(),'pradeep','Rathnapura'),
       (UUID(),'Isanka','Matara'),
       (UUID(),'Nuwan','Colombo');

CREATE TABLE  items(
                       stock INT NOT NULL ,
                       unit_price DECIMAL(5,2) NOT NULL ,
                       description VARCHAR(300) NOT NULL ,
                       code VARCHAR(36) PRIMARY KEY
);

CREATE TABLE `order`(
                        id VARCHAR(36) PRIMARY KEY ,
                        date DATE NOT NULL ,
                        customer_id VARCHAR(36) NOT NULL ,
                        CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES customers (id)

);

CREATE TABLE order_detail(
                             order_id VARCHAR(36) NOT NULL ,
                             item_code VARCHAR(36) NOT NULL ,
                             qty INT NOT NULL ,
                             unit_price DECIMAL(5,2) NOT NULL ,
                             CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES `order`(id),
                             CONSTRAINT fk_code FOREIGN KEY (item_code) REFERENCES items (code),
                             PRIMARY KEY (order_id,item_code)
);

INSERT INTO items (stock, unit_price, description, code)
VALUES(10,150.50,'Chocolate',UUID()),
      (50,700.00,'RedBull',UUID()),
      (8,250.00,'CocaCola(1l)',UUID()),
      (55,150.00,'LifeBoy',UUID());

INSERT INTO `order` (id, date, customer_id) VALUES
    (UUID(),current_date,'fb50279a-5906-11ed-8944-845cf329fce3');