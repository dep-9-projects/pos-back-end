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

/*pos-db*/