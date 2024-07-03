# Inventory Management System

This project is a simple inventory management system for a stationery shop using Vert.x and MongoDB. It allows the shop owner to perform CRUD operations on the inventory items.

## Endpoints

- `POST /api/items`: Add a new stationery item.
- `GET /api/items/:itemId`: Retrieve an item by `itemId`.
- `PUT /api/items/:itemId`: Update an existing item.
- `DELETE /api/items/:itemId`: Delete an item by `itemId`.

## Running the Project

1. Ensure MongoDB is running locally on port 27017.
2. Build and run the Vert.x application.

## Dependencies

- Vert.x
- MongoDB
- SLF4J
