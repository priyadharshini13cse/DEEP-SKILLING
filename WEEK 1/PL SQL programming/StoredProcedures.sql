-- Create Tables
CREATE TABLE SavingsAccounts (
    account_id NUMBER PRIMARY KEY,
    balance NUMBER
);

CREATE TABLE Employees (
    emp_id NUMBER PRIMARY KEY,
    emp_name VARCHAR2(50),
    department VARCHAR2(50),
    salary NUMBER
);

CREATE TABLE Accounts (
    account_id NUMBER PRIMARY KEY,
    balance NUMBER
);

-- Insert Sample Data
INSERT INTO SavingsAccounts VALUES (1, 10000);
INSERT INTO SavingsAccounts VALUES (2, 15000);

INSERT INTO Employees VALUES (101, 'Ram', 'IT', 50000);
INSERT INTO Employees VALUES (102, 'Priya', 'IT', 45000);
INSERT INTO Employees VALUES (103, 'Kumar', 'HR', 40000);

INSERT INTO Accounts VALUES (1, 20000);
INSERT INTO Accounts VALUES (2, 10000);

COMMIT;

-- Scenario 1: Process Monthly Interest
CREATE OR REPLACE PROCEDURE ProcessMonthlyInterest
IS
BEGIN
    UPDATE SavingsAccounts
    SET balance = balance + (balance * 0.01);
    COMMIT;
END;
/

-- Execute Procedure 1
BEGIN
    ProcessMonthlyInterest;
END;
/

-- Scenario 2: Update Employee Bonus
CREATE OR REPLACE PROCEDURE UpdateEmployeeBonus(
    dept IN VARCHAR2,
    bonus_percent IN NUMBER
)
IS
BEGIN
    UPDATE Employees
    SET salary = salary + (salary * bonus_percent / 100)
    WHERE department = dept;
    COMMIT;
END;
/

-- Execute Procedure 2
BEGIN
    UpdateEmployeeBonus('IT', 10);
END;
/

-- Scenario 3: Transfer Funds
CREATE OR REPLACE PROCEDURE TransferFunds(
    source_acc IN NUMBER,
    target_acc IN NUMBER,
    amount IN NUMBER
)
IS
    source_balance NUMBER;
BEGIN
    SELECT balance INTO source_balance
    FROM Accounts
    WHERE account_id = source_acc;

    IF source_balance >= amount THEN
        UPDATE Accounts
        SET balance = balance - amount
        WHERE account_id = source_acc;

        UPDATE Accounts
        SET balance = balance + amount
        WHERE account_id = target_acc;

        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Transfer Successful');
    ELSE
        DBMS_OUTPUT.PUT_LINE('Insufficient Balance');
    END IF;
END;
/

-- Execute Procedure 3
SET SERVEROUTPUT ON;

BEGIN
    TransferFunds(1, 2, 5000);
END;
/

-- Check Output
SELECT * FROM SavingsAccounts;
SELECT * FROM Employees;
SELECT * FROM Accounts;