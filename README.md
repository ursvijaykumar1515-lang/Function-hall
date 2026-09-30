# GrandVenue Function Hall Booking System

## Architecture
- Frontend: HTML + CSS + JavaScript
- Node.js: serves the frontend on port 3000
- Java: REST API on port 8080
- JDBC: Java connects to MySQL
- MySQL: stores halls, food and bookings

## Requirements
1. JDK 17+
2. Maven
3. MySQL 8+
4. Node.js 18+

## 1. Database
Open MySQL Workbench and run `database.sql`.
If your MySQL password is not `root`, edit:
`backend/src/main/java/com/functionhall/Main.java`

Change:
`static final String PASS="root";`

## 2. Start Java JDBC backend
Open terminal inside `backend`:
```bash
mvn clean compile
mvn exec:java
```
Backend: http://localhost:8080/api/status

## 3. Start Node frontend
Open another terminal in the project root:
```bash
npm install
npm start
```
Open:
http://localhost:3000

## Features
- Home page
- Plain function halls
- Halls with chairs
- Hall + chairs + decoration packages
- 22+ vegetarian items
- 22+ non-vegetarian items
- Add/remove cart animation
- Booking form
- Demo payment page
- JDBC/MySQL booking storage
- Node.js frontend server

## Important
The payment screen is a DEMO only. It does not charge a real card.
