-- mysql
DROP TABLE IF EXISTS Item;
CREATE TABLE Item(
id INTEGER PRIMARY KEY AUTO_INCREMENT,
title VARCHAR(200) NULL,
time TIMESTAMP NOT NULL,
money FLOAT NOT NULL,
categoryID INTEGER NOT NULL REFERENCES Category(id),
remark TEXT NULL,
user VARCHAR(20) NULL,
address VARCHAR(200) NULL,
sourceFrom CHAR(1) NOT NULL DEFAULT 'D',
createdTime TIMESTAMP(14) NULL
);

DROP TABLE IF EXISTS Category;
CREATE TABLE Category(
id INTEGER PRIMARY KEY,
parentID INTEGER NULL REFERENCES Category(id),
type VARCHAR(20) NOT NULL,
name VARCHAR(50) NOT NULL,
displayOrder INTEGER NOT NULL DEFAULT 0
);

DROP TABLE IF EXISTS Budget;
CREATE TABLE Budget(
id INTEGER PRIMARY KEY AUTO_INCREMENT,
categoryID INTEGER NOT NULL REFERENCES Category(ID),
year INTEGER NOT NULL,
month INTEGER NOT NULL,
money FLOAT NOT NULL
);

DROP TABLE IF EXISTS Overdraw;
CREATE TABLE Overdraw(
id INTEGER PRIMARY KEY AUTO_INCREMENT,
time TIMESTAMP NOT NULL,
money FLOAT NOT NULL,
remark TEXT NULL,
address VARCHAR(200) NULL,
returnTime TIMESTAMP NULL,
returnMoney FLOAT NULL,
returnRemark TEXT NULL,
completed INTEGER NOT NULL DEFAULT 0,
sourceFrom CHAR(1) NOT NULL DEFAULT 'D',
createdTime TIMESTAMP(14) NULL
);

DROP TABLE IF EXISTS ChangeList;
CREATE TABLE ChangeList(
id INTEGER PRIMARY KEY AUTO_INCREMENT,
tableName VARCHAR(50) NOT NULL,
pk INTEGER NOT NULL,
action VARCHAR(20) NOT NULL DEFAULT 'Add',
exchangeTime TIMESTAMP(14) NULL
)

CREATE INDEX Item_idx ON Item (time);

CREATE INDEX Budget_idx ON Budget (Year, Month);
