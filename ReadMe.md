# Project Name: Byte Me Canteen Management System

## Overview
This project is a Java-based console application that simulates a Canteen Management System. The system is designed to serve different types of users: Admin, VIP customers, and Regular customers. It allows customers to browse the menu, add items to their cart, place orders, and track orders. Admins can manage the menu, track orders, and generate sales reports.

## Features
- **Admin Functionalities:** Add, update, and remove menu items, track orders, generate daily sales reports, and view all users.
- **Customer Functionalities:** Browse menu, add items to cart, place orders, view pending orders, cancel orders, re-order past meals, and access VIP benefits.
- **VIP Benefits:** VIP customers have access to exclusive menu items, receive discounts, and have priority in order processing.

## Assumptions
The following assumptions were made during the development of this project:
1. **Single Service Instances**: MenuService, OrderManager, and CustomerService should each have a single global instance to ensure consistency across the application.
2. **Centralized Authentication**: The `AuthenticationManager` is a universal class for handling login and signup operations, available globally.
3. **VIP Membership Upgrade**: When a Regular customer becomes a VIP, an automatic logout is not triggered, requiring the user to log out and log in again to access VIP functionalities.
4. **Order Handling**: Orders from VIP customers are processed before Regular customer orders. The system handles orders in a first-come, first-served manner while prioritizing VIP orders.
5. **Pending Orders with Removed Items**: If a menu item is removed, all pending orders containing that item are automatically denied.
6. **Menu Accessibility**: VIP and Regular customers can only access menu items through `CustomerService` to ensure consistent access control, rather than interacting with `MenuService` directly.
7. **Refund Mechanism**: When an order is canceled, refunds are automatically processed by the `OrderManager`.
8. **Item Availability**: Only available items are shown for VIP-exclusive items.
9. **Fried Rice vs. Ice Cream**: Menu items are distinguished based on their names, ensuring no conflicts (e.g., "fried rice" is not treated the same as "ice cream" because both have "ice" in it.).
10. **Order Item Implementation**: The `OrderItem` class is used to manage menu items and quantities for each order.

## Issues Encountered and Resolutions
The following issues were encountered during development, along with their current statuses:
1. **Centralized Service Access**: Ensured that VIP and Regular customers access the menu via `CustomerService` - *resolved*.
2. **Universal Authentication Clashing with CustomerService**: Managed universal access to `AuthenticationManager` while ensuring consistent interactions with `CustomerService` - *resolved*.
3. **Order Item Implementation**: The `OrderItem` class has been implemented to handle menu items within orders - *pending*.
4. **Constructor Dependency Injection for Services**: Resolved dependency injection issues by using global service instances, avoiding repeated argument passing during runtime - *resolved*.
5. **VIP Stats Not Showing**: Corrected issues with VIP statistics visibility - *resolved*.
6. **Sales Report Issue**: Fixed a bug causing sales reports to display zero sales after significant activity - *resolved*.
7. **Removing Items Affecting Orders**: Implemented logic to deny all pending orders that contain items removed from the menu - *implemented*.
8. **Refunds and Order Cancellations**: Refunds are automatically processed when an order is canceled - *implemented*.
9. **Track Order Status**: Added order status tracking for customers to see if their orders are canceled or denied.
10. **Adding Items to Cart**: Improved the process of adding VIP-exclusive items to allow back-to-back additions - *resolved*.

## How to Run the Project
### Prerequisites
- **Java Development Kit (JDK)**: Version 8 or above.
- **Java IDE or Terminal**: Any IDE (like IntelliJ, Eclipse) or terminal for running Java applications.

### Steps to Run the Project
1. **Clone the Repository**
   ```
   git clone <repository_url> // altough this is private as of now
   ```
2. **Navigate to the Project Directory**
   ```
   cd iiitd-canteen-management
   ```
3. **Compile the Java Files**
   ```
   javac *.java
   ```
4. **Run the Application**
   ```
   java Main
   ```
5. **Login or Signup**
    - On launching, choose whether to login or signup.
    - Following are pre-existing users.
    - Admin, VIP, and Regular customer options are available.
    - Admin Credentials:
    - - ID - A1, Password - Admin@1
    - - ID - A2, Password - Admin@2
    - VIP Customer Credentials
    - - ID - V1, Password - VIP@1
    - - ID - V2, Password - VIP@2
    - Regular Customer Credentials
    - - ID - R1, Password - Reg@1
    - - ID - R2, Password - Reg@2

6. **Admin Functionalities**
    - Use the admin menu to add, update, or remove menu items, track orders, and generate reports.

7. **Customer Functionalities**
    - VIP and Regular customers can browse the menu, add items to their cart, place orders, and more.
    

## Contact
For any questions or support, please contact the project author at [hasnain23325@iiitd.ac.in]().

