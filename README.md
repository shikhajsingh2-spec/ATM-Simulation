# ATM Simulation Project (Core Java + JDBC)

A console-based ATM simulation system built using **Core Java** and **MySQL**.
It provides a role-based login system for **Admin** and **User** with features like:
- User registration & login
- Balance inquiry
- Cash deposit & withdrawal
- Transaction history
- Admin dashboard to manage users

## Technologies
- Core Java
- JDBC
- MySQL Database

## Setup & Run
1. Create a MySQL database using `atm_db.sql`.
2. Update database credentials inside the code (if needed).
3. Compile and run:
   ```bash
   javac -cp .:mysql-connector.jar src/*.java
   java -cp .:mysql-connector.jar src.Main
