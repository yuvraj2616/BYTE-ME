# ByteMe System

## Overview
**ByteMe** is a Java-based food ordering management system that provides an interactive platform for customers and administrators in a restaurant environment. It simplifies the process of managing food orders, allowing customers to register, browse a menu, place orders, and view their order history. Administrators can efficiently manage menu items, process orders, and track sales data.

This system is built to enhance customer experience with features like VIP upgrades and a review system, while also offering robust tools for administrators to ensure smooth operations.



## Table of Contents
- [Features](#features)
  - [Customer Features](#customer-features)
  - [Admin Features](#admin-features)
- [Installation](#installation)
  - [Prerequisites](#prerequisites)
  - [Steps to Install](#steps-to-install)
- [Usage](#usage)
  - [Example of Customer Registration](#example-of-customer-registration)
- [Code Structure](#code-structure)
  - [Key Classes](#key-classes)
  - [Class Responsibilities](#class-responsibilities)
- [Sample Workflows](#sample-workflows)
  - [Customer Workflow](#customer-workflow)
  - [Admin Workflow](#admin-workflow)
- [Future Enhancements](#future-enhancements)


## Features

### Customer Features
- **Registration and Login**: Customers can create an account with their name and VIP status, and log in using their unique ID or name for easy access.
- **Menu Browsing**: A categorized menu display allows customers to browse available food and beverage items.
- **Order Management**:
  - **Add to Cart**: Customers can add items to their cart and modify quantities before placing an order.
  - **Order History**: View all past orders, including details and statuses.
  - **Cancel Orders**: Customers have the flexibility to cancel pending orders if necessary.
- **VIP Upgrades**: Customers can upgrade their accounts to VIP status to unlock additional perks.
- **Review System**: After trying a menu item, customers can leave a review to help others with their choices.

### Admin Features
- **Secure Login**: Administrators log in using a password, ensuring secure access to management features.
- **Menu Management**: Admins have full control over the menu, including the ability to add, update, and remove items as needed.
- **Order Processing**: Admins can:
  - **View and Update Order Statuses**: Manage orders by marking them as complete, pending, or canceled.
  - **Process Refunds**: Refunds are available for specific scenarios.
  - **Generate Daily Sales Reports**: View daily summaries for sales metrics and item popularity.
- **Sales Tracking**: Track total sales volume, popular menu items, and customer activity based on order history.

## Installation

### Prerequisites
- **Java Development Kit (JDK)**: Ensure the latest version of JDK is installed on your machine. You can download it from the [Oracle website](https://www.oracle.com/java/technologies/javase-downloads.html) or use [OpenJDK](https://openjdk.java.net/).

### Steps to Install
1. Clone the repository:
    ```bash
    git clone <repository-url>
    cd ByteMe
    ```

2. Compile the code:
    ```bash
    javac ByteMe.java
    ```

3. Run the application:
    ```bash
    java ByteMe
    ```

## Usage

1. **Launch** the application to access the main menu.
2. **Choose** between Admin Login or Customer Login based on your role.
3. **Follow prompts** for actions based on your role:
    - As a customer, you can register, log in, browse the menu, manage your cart, and place orders.
    - As an admin, you can manage menu items, view orders, update statuses, and review daily sales reports.

### Example of Customer Registration
Here’s a snippet of how a new customer is registered in the system:
```java
public void registerCustomer(String name, boolean isVIP) {
    Customer newCustomer = new Customer(name, isVIP);
    customers.put(newCustomer.getCustomerID(), newCustomer);
    System.out.println("Customer registered: " + name + " (ID: " + newCustomer.getCustomerID() + ")");
}

## Code Structure

### Key Classes
The application is organized into several classes, each handling specific aspects of the system’s functionality:

- **ByteMe**: The main class responsible for managing user interactions, customer registration, and login.
- **Admin**: Handles all administrative tasks, including menu management, order processing, and sales reporting.
- **Customer**: Manages customer account details and functionalities like order history and VIP status.
- **MenuItem**: Represents individual menu items with properties like name, price, category, availability, and customer reviews.
- **Order**: Represents each customer order, including items ordered, order status, and total price.

### Class Responsibilities

| Class      | Responsibilities                                              |
|------------|---------------------------------------------------------------|
| **ByteMe** | Manages main program flow and user interactions               |
| **Admin**  | Manages menu items, processes orders, and generates reports   |
| **Customer** | Manages customer profiles, order history, and VIP perks     |
| **MenuItem** | Defines menu item properties and customer reviews           |
| **Order** | Tracks order details, items, and total costs                   |

Each class is designed with methods to perform operations relevant to the role, ensuring a clear separation of responsibilities.

## Sample Workflows

### Customer Workflow
1. **Register or Login**: Access the system with a unique ID.
2. **Browse Menu**: View items available in different categories.
3. **Add to Cart**: Select items and specify quantities.
4. **Place Order**: Confirm and pay to complete the order.
5. **View Order History**: Track past orders and statuses.
6. **Leave Reviews**: Add reviews for ordered items.

### Admin Workflow
1. **Login**: Access the admin panel securely.
2. **Manage Menu**: Add, update, or remove items.
3. **Process Orders**: Update order statuses, manage refunds.
4. **Track Sales**: Generate and view daily reports.

### Future Enhancements

Potential enhancements could include:

- **Discounts and Promotions**: Ability for admins to set discounts on menu items.
- **Loyalty Program**: Enhanced benefits for frequent or VIP customers.
- **Enhanced Security**: Improved login methods and data encryption.
- **Data Analytics**: Advanced analytics for customer insights and menu performance.
