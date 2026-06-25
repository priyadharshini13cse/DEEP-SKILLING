//Age > 60 → 1% Interest Discount
DECLARE
BEGIN
    FOR c IN (SELECT customer_id, age FROM Customers) LOOP
        IF c.age > 60 THEN
            UPDATE Loans
            SET interest_rate = interest_rate - 1
            WHERE customer_id = c.customer_id;
        END IF;
    END LOOP;
    COMMIT;
END;
//Balance > 10000 → VIP
DECLARE
BEGIN
    FOR c IN (SELECT customer_id, balance FROM Customers) LOOP
        IF c.balance > 10000 THEN
            UPDATE Customers
            SET IsVIP = 'TRUE'
            WHERE customer_id = c.customer_id;
        END IF;
    END LOOP;
    COMMIT;
END;
//Loan Due Within 30 Days → Reminder
SET SERVEROUTPUT ON;

DECLARE
BEGIN
    FOR l IN (
        SELECT customer_id, due_date
        FROM Loans
        WHERE due_date <= SYSDATE + 30
    ) LOOP
        DBMS_OUTPUT.PUT_LINE(
            'Reminder: Customer ' || l.customer_id ||
            ' loan due on ' || l.due_date
        );
    END LOOP;
END;
