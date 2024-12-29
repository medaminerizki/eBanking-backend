# E-Banking Application (Backend Only)

This repository contains the backend of an E-Banking application built using Spring Boot. The application allows multiple clients to have multiple accounts (either saving accounts or current accounts). Below are screenshots demonstrating various features of the backend API.

## Features

- **Add Customer**: Create new customers in the system.
- **Manage Accounts**: View accounts associated with a specific customer.
- **Transactions**: Perform operations like crediting, debiting, and transferring money between accounts.
- **API Documentation**: Access detailed API specifications via Swagger.
- **Pagination**: Retrieve paginated account operations.

## Important Note

This project includes only the backend implementation of the E-Banking application. The frontend is not included. You can interact with the API using tools like Postman, Swagger UI, or integrate it with a frontend of your choice.

## Screenshots

1. **Customer Management**:

    - **Add a new customer**:
      ![Add Customer](Screenshots/AddCustomer.png)
    - **View all customers**:
      ![Customers](Screenshots/customers.png)
    - **View customer details by ID**:
      ![Customer By ID](Screenshots/customerById.png)
    - **SQL table representation of customers**:
      ![Customers SQL](Screenshots/customersSQL.png)
    - **Swagger documentation for customer-related endpoints**:
      ![Customers Swagger](Screenshots/customersSwagger.png)

2. **Account Management**:

    - **View all accounts**:
      ![Accounts](Screenshots/accounts.png)
    - **View account details by ID**:
      ![Account By ID](Screenshots/accountById.png)
    - **Account details after a credit operation**:
      ![Account After Credit](Screenshots/AccountAfterCredit.png)
    - **Account details after a debit operation**:
      ![Account After Debit](Screenshots/AccountAfterDebit.png)
    - **Account details after a transfer operation**:
      ![Account After Transfer](Screenshots/accountAfterTransfer.png)
    - **SQL table representation of accounts**:
      ![Accounts SQL](Screenshots/accountsSQL.png)
    - **Swagger documentation for account-related endpoints**:
      ![Accounts Swagger](Screenshots/accountsSwagger.png)

3. **Operations**:

    - **Paginated account operations**:
      ![Operations By Account Page Size](Screenshots/operationsByAccPageSize.png)
    - **SQL table representation of account operations**:
      ![Operations SQL](Screenshots/operationsSQL.png)

4. **Testing**:

    - **Test for crediting an account**:
      ![Test Credit](Screenshots/testCredit.png)
    - **Test for debiting an account**:
      ![Test Debit](Screenshots/testDebit.png)
    - **Test for transferring money between accounts**:
      ![Test Transfer](Screenshots/testTransfert.png)


## Technologies utilisées
- **Spring Boot** : Framework pour le développement backend
- **Spring Data JPA** : Pour l'accès à la base de données
- **H2 Database** : Base de données en mémoire utilisée
- **Lombok** : Pour simplifier la création des beans et réduire le code


## Auteur
Mohamed Amine RIZKI