# Tindahan-ni-Aling-Nena
*Project Title*

    Tindahan ni Aling Nena

*Project Description*

    Tindahan ni Aling Nena is a web-based store management application developed using Java as its backend. It is designed to help micro and small business owners manage their products, sales, inventory, and customer utang.

    The system automatically calculates revenue, cost of goods sold, taxes, gross profit, net profit, and profit margin. It also provides a dashboard and reports to help store owners monitor their business performance and make better pricing and restocking decisions.

*Project Problem*

    Many neighborhood stores in the Philippines still record their sales and customer utang manually using notebooks. Store owners often experience difficulties tracking their actual profit, product performance, inventory, and unpaid customer balances.

    Manual calculations can also lead to errors and make it difficult to determine the correct selling prices and profitability of products. This project addresses these problems by providing an organized system that automatically records transactions and calculates important business information.

*Project Objectives*

    # General Objective

        To develop a web-based store management application with a Java backend that records products, sales, and customer utang while automatically calculating revenue, taxes, profit, and profit margin.

    # Specific Objectives

        - Record products with their cost price, selling price, and stock quantity.

        - Record regular, discounted, and utang sales.

        - Automatically update product stock after every sale.

        - Track customer utang balances and payments.

        - Calculate revenue, cost of goods sold, taxes, gross profit, net profit, and profit margin.

        - Apply Philippine tax rules, including VAT, percentage tax, and excise tax.

        - Provide separate owner and cashier accounts with different permissions.

        - Generate dashboards and reports.

        - Provide a responsive and user-friendly website.

*Target Users*

    1. Micro, Small and Medium Entrepreneurs – Store owners and cashiers who need to manage products, sales, inventory, and customer utang.

    2. Business-Related Course Students – Students who want to practice sales, cost, markup, tax, and profit margin computations using realistic store data.

    3. Online Shop Owners – Online sellers who want to record orders, monitor product prices and stock, and calculate their sales and profits.

*Main Features*

    - User Accounts and Roles – Provides login functionality with separate owner and cashier permissions.

    - Product Management – Allows owners to add, view, update, and remove products, including their cost price, selling price, and stock quantity.

    - Sales Recording – Records regular, discounted, and utang sales while automatically deducting sold quantities from inventory.

    - Utang Management – Tracks customer balances, payment records, due dates, and unpaid sales.

    - Profit and Margin Computation – Automatically calculates revenue, cost of goods sold, gross profit, net profit, and profit margin.

    - Philippine Tax Computation – Calculates applicable VAT or percentage tax and displays excise tax included in the prices of cigarettes and alcoholic products.

    - Dashboard and Reports – Displays total sales, total profit, outstanding utang, best-selling products, and most profitable products.

    - Inventory Monitoring – Displays available product stocks and highlights low-stock items.

    - Responsive Web Interface – Provides a clean and user-friendly interface that works on computers and mobile browsers.

*Technologies Used*

    - Java – Main programming language and backend logic.

    - Spring Boot – Proposed Java framework for the backend.

    - JavaScript – Frontend interaction and input handling.

    - File Storage – Saves and loads application data and tax rates.

    - Visual Studio Code – Development environment.

    - GitHub – Source code management and team collaboration.

*Team Members and Roles*

| Team Member | Role | Responsibilities |
|---|---|---|
| Tier Marone Q. Dela Cruz | Lead Programmer | Coordinates the implementation of Java classes, methods, and OOP features. |
| Nino Arjay M. Deomampo | Project Leader / Documentation | Coordinates tasks, monitors deadlines, maintains the README, testing documentation, screenshots, and assists in checking application functionality. |
| Jules Dave A. Maningas | System Design Lead | Develops system requirements, class designs, UML diagrams, and overall application structure. |

*OOP Concepts Demonstrated*

    1. Classes and Objects – Represents products, inventory, sales, customers, users, tax rates, and sales reports through Java classes and objects.

    2. Encapsulation – Uses private attributes and getters and setters to protect important data such as prices, stock quantities, customer balances, and PINs.

    3. Inheritance – Uses parent and child classes. Examples include RegularSale, DiscountedSale, and CreditSale extending Sale, and Owner and Cashier extending User.

    4. Polymorphism – Allows subclasses to override methods such as computeRevenue(), computeExcise(), canViewProfit(), and canEditProducts().

    5. Abstraction – Uses abstract classes such as Sale and User, together with interfaces such as TaxPolicy and Reportable.

    6. Methods – Organizes system operations through methods such as addProduct(), recordSale(), recordPayment(), computeTax(), computeProfit(), and generateReport().

    7. Exception Handling – Uses try-catch blocks and custom exceptions to handle invalid inputs, insufficient stock, overpayments, and invalid login attempts.

    8. Arrays and Data Handling – Uses ArrayList, arrays, loops, and sorting to manage products, sales history, customers, and users.
