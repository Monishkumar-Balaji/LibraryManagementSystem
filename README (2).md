
# Llibrary Management System

To create an interactive library management system in Java designed to streamline library operations and enhance the user experience for both librarians,faculties and students. The system should provide a range of features to manage library resources efficiently and support library users with easy access to information and services.

Features
Librarian Login: Enables the librarian to add or remove books, update stock, and manage user accounts.
Faculty Login: Allows faculty members to borrow and return books.
Student Login: Enables students to borrow and return books.
Database Management: All data is stored in a MySQL database for persistent storage.
## Run Locally


Java JDK - Version 8 or later

Eclipse IDE or any other java development IDE- For development and running the project

MySQL Database - Version 8.0 or later

JDBC Connector for MySQL - Required to connect the Java application with the MySQL database
```

## Setup Instructions

Clone the Repository: Download or clone the project files to your local system.

Database Setup:

Open MySQL and create a new database for the library management system.

Use the provided SQL script to create the following tables:

Categories: Stores book categories.
Books: Contains details about each book (title, author, publication year, category, and stock).
Users: Stores login information and user roles (librarian, faculty, or student).
BorrowedBooks: Tracks which books have been borrowed and by whom.

Update Database Configuration:
In the project, locate the database configuration file (or connection details in the code).
Update the database URL, username, and password according to your MySQL setup.
Import Project into Eclipse:

Open Eclipse IDE.
Go to File > Import > Existing Projects into Workspace.
Select the folder containing the project files and import it.
Add JDBC Connector:

Download the JDBC connector for MySQL if not already downloaded.
Right-click on the project in Eclipse, go to Build Path > Add External Archives, and add the JDBC connector .jar file.
Run the Project:

Locate the main class file and run it as a Java application.
Follow the menu-driven options displayed on the console to access different functionalities.