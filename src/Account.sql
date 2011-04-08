DROP TABLE IF EXISTS Account;
CREATE TABLE Item(
ID INTEGER PRIMARY KEY AUTOINCREMENT,
Title VARCHAR(200) NOT NULL,
Date DATETIME NOT NULL,
Money FLOAT NOT NULL,
CategoryID INT NOT NULL REFERENCES Category(ID),
Remark TEXT NULL,
User VARCHAR(20) NULL,
Address VARCHAR(200) NULL
);

DROP TABLE IF EXISTS Category;
CREATE TABLE Category(
ID INT PRIMARY KEY,
ParentID INT NULL REFERENCES Category(ID),
Type VARCHAR(20) NOT NULL Check ( Type = 'Expenditure' OR Type = 'Income'),
Name VARCHAR(50) NOT NULL
);

DROP TABLE IF EXISTS Budget;
CREATE TABLE Budget(
ID INTEGER PRIMARY KEY AUTOINCREMENT,
CategoryID INT NOT NULL REFERENCES Category(ID),
Year INT NOT NULL,
Month INT NOT NULL,
Color75 CHAR(7) NOT NULL default '#FFFF00',
Color90 CHAR(7) NOT NULL default '#FF0033',
Money FLOAT NOT NULL
);

DROP INDEX IF EXISTS Item_idx;
CREATE INDEX Item_idx ON Item (Date);

DROP INDEX IF EXISTS Budget_idx;
CREATE INDEX Budget_idx ON Budget (Year, Month);

-- ��������
INSERT INTO Category(ID, Type, Name)
VALUES(100, 'Income', 'ְҵ����');
INSERT INTO Category
VALUES(101, 100, 'Income', '����');
INSERT INTO Category
VALUES(102, 100, 'Income', '����');

INSERT INTO Category(ID, Type, Name)
VALUES(200, 'Expenditure', '����');
INSERT INTO Category
VALUES(201, 200, 'Expenditure', '��Ӱ');

INSERT INTO Category(ID, Type, Name)
VALUES(300, 'ʳ����');
INSERT INTO Category
VALUES(301, 300, 'Expenditure', '�߲�');
INSERT INTO Category
VALUES(302, 300, 'Expenditure', '���');
INSERT INTO Category
VALUES(302, 300, 'Expenditure', 'ÿ������');

INSERT INTO Category(ID, Type, Name)
VALUES(400, 'Expenditure', 'ס����');
INSERT INTO Category
VALUES(401, 400, 'Expenditure', '��ҵ�����');
INSERT INTO Category
VALUES(402, 400, 'Expenditure', '����ˮ��');

INSERT INTO Category(ID, Type, Name)
VALUES(500, 'Expenditure', '������');
INSERT INTO Category
VALUES(501, 500, 'Expenditure', 'Ь��');
INSERT INTO Category
VALUES(502, 500, 'Expenditure', '����');
INSERT INTO Category
VALUES(503, 500, 'Expenditure', '�·�');

INSERT INTO Category(ID, Type, Name)
VALUES(600, 'Expenditure', '��ͨ��');
INSERT INTO Category
VALUES(601, 600, 'Expenditure', '����');
INSERT INTO Category
VALUES(602, 600, 'Expenditure', '������');
