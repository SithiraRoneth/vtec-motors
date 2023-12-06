DROP DATABASE IF EXISTS vtec;
CREATE DATABASE vtec;
use vtec;

create table User(
                     user_name VARCHAR(100)PRIMARY KEY ,
                     password VARCHAR(100),
                     e_mail VARCHAR(100)
);
create table Employee(
                         Emp_id VARCHAR(100)PRIMARY KEY,
                         Emp_name VARCHAR(100),
                         Contact_no VARCHAR(100),
                         NIC VARCHAR(100),
                         Job VARCHAR(100),
                         email VARCHAR(100)
);
create table attendance(
                           attendance_id INT AUTO_INCREMENT PRIMARY KEY ,
                           Date DATE NOT NULL ,
                           Emp_Id VARCHAR(100),
                           Emp_Name VARCHAR(100),
                           FOREIGN KEY (Emp_Id) REFERENCES Employee(Emp_id)ON UPDATE CASCADE ON DELETE CASCADE
);
create table Supplier(
                         Supplier_id VARCHAR(100)PRIMARY KEY ,
                         Supplier_name VARCHAR(100),
                         Supplier_ContactNo INT(15)
);
create table Guardian(
                         Guardian_id VARCHAR(100)PRIMARY KEY ,
                         Guardian_name VARCHAR(100),
                         Guardian_ContactNo INT(15),
                         Emp_id VARCHAR(100),
                         FOREIGN KEY (Emp_id)REFERENCES Employee(Emp_id)ON UPDATE CASCADE ON DELETE CASCADE
);
create table Appointments(
                             App_id VARCHAR(100)PRIMARY KEY ,
                             App_date DATE,
                             Guardian_id VARCHAR(100),
                             FOREIGN KEY (Guardian_id)REFERENCES Guardian(Guardian_id)ON UPDATE CASCADE ON DELETE CASCADE
);
create table Vehicles(
                         Vehicle_id VARCHAR(100)PRIMARY KEY ,
                         Vehicle_Type VARCHAR(100),
                         Guardian_id VARCHAR(100),
                         FOREIGN KEY (Guardian_id)REFERENCES Guardian(Guardian_id)ON UPDATE CASCADE ON DELETE CASCADE
);
create table Orders(
                       Order_id VARCHAR(100)PRIMARY KEY ,
                       Order_date DATE,
                       Guardian_id VARCHAR(100),
                       FOREIGN KEY (Guardian_id)REFERENCES Guardian(Guardian_id)ON UPDATE CASCADE ON DELETE CASCADE
);
create table Service(
                        Service_id VARCHAR(100)PRIMARY KEY ,
                        name VARCHAR(100),
                        Service_description VARCHAR(100),
                        Amount VARCHAR(100)
);

create table OrderService(
                             Order_id VARCHAR(100),
                             Service_id VARCHAR(100),
                             price double,
                             FOREIGN KEY (Order_id)REFERENCES Orders(Order_id)ON UPDATE CASCADE ON DELETE CASCADE ,
                             FOREIGN KEY (Service_id)REFERENCES Service(Service_id)ON UPDATE CASCADE ON DELETE CASCADE
);
create table SpareParts(
                           Spare_id VARCHAR(100)PRIMARY KEY ,
                           Spare_type VARCHAR(100),
                           Description VARCHAR(100),
                           price double,
                           Service_name VARCHAR(100),
                           Service_id VARCHAR(100),
                           FOREIGN KEY (Service_id)REFERENCES Service(Service_id)ON UPDATE CASCADE ON DELETE CASCADE
);
create table SpareParts_Supplier(
                                    Supplier_id VARCHAR(100),
                                    Supplier_name VARCHAR(100),
                                    Spare_id VARCHAR(100),
                                    Spare_type VARCHAR(100),
                                    FOREIGN KEY (Supplier_id)REFERENCES Supplier(Supplier_id)ON UPDATE CASCADE ON DELETE CASCADE ,
                                    FOREIGN KEY (Spare_id)REFERENCES SpareParts(Spare_id)ON UPDATE CASCADE ON DELETE CASCADE
);
create table income(
                       income_id INT AUTO_INCREMENT PRIMARY KEY ,
                       description VARCHAR(100),
                       Amount double,
                       year INT,
                       month VARCHAR(100),
                       date DATE
);
create table expenditure(
                            expenditure_id INT AUTO_INCREMENT PRIMARY KEY ,
                            description VARCHAR(100),
                            Amount double,
                            year INT,
                            month VARCHAR(100),
                            date DATE
);
create table salary(
    salary_id INT AUTO_INCREMENT PRIMARY KEY ,
    Emp_id VARCHAR(100),
    Emp_name VARCHAR(100),
    salary_amount double,
    bonus double,
    etf double,
    final_salary double,
    month VARCHAR(100),
    FOREIGN KEY (Emp_id)REFERENCES employee(Emp_id)ON UPDATE CASCADE ON DELETE CASCADE
);

ALTER table employee MODIFY COLUMN NIC VARCHAR(100);

