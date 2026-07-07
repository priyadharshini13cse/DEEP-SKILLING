# Introduction to HQL and JPQL

- **HQL** – Hibernate Query Language
- **JPQL** – Java Persistence Query Language
- Both are object‑oriented query languages that resemble SQL but operate on **entities** instead of tables.
- **JPQL** is a **subset** of HQL. Every valid JPQL query is also valid HQL, but HQL offers additional capabilities.
- Supported DML operations for both:
  - `SELECT`
  - `UPDATE`
  - `DELETE`
- **HQL** additionally supports `INSERT` statements (JPQL does not).

## Example Entity
```java
@Entity
public class Employee {
    @Id @GeneratedValue
    private Long id;
    private String name;
    // getters & setters
}
```

## JPQL SELECT Example
```java
String jpql = "SELECT e FROM Employee e WHERE e.name = :name";
List<Employee> employees = em.createQuery(jpql, Employee.class)
                           .setParameter("name", "John")
                           .getResultList();
```

## HQL INSERT Example (available only in HQL)
```java
String hqlInsert = "INSERT INTO Employee (name) SELECT s.name FROM Staff s WHERE s.active = true";
em.createQuery(hqlInsert).executeUpdate();
```

## HQL UPDATE Example
```java
String hqlUpdate = "UPDATE Employee e SET e.name = :newName WHERE e.id = :id";
em.createQuery(hqlUpdate)
  .setParameter("newName", "Jane")
  .setParameter("id", 1L)
  .executeUpdate();
```

## HQL DELETE Example
```java
String hqlDelete = "DELETE FROM Employee e WHERE e.id = :id";
em.createQuery(hqlDelete)
  .setParameter("id", 1L)
  .executeUpdate();
```

> **Note**: When using HQL `INSERT`, the target entity must be mapped as an `@Entity` and the source can be any other entity or a sub‑select.

---
*Prepared for the **Spring Data JPA with Hibernate** course*
