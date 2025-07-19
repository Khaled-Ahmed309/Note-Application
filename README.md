# Notes Management API

The **Notes Management API** is a backend-only system built with Java Spring Boot. It provides RESTful APIs that allow users and admins to create, update, delete, and view notes, with advanced security features such as JWT authentication, profile update, and password reset.

---

## 🚀 Features

- Add, create, delete, and update notes by users and admin.
- Forgot password functionality.
- Update user profile information.
- Secure operations with JWT token authentication.
- Data storage with PostgreSQL.
- RESTful endpoints, ready for integration with any frontend.
- Role-based access for users and admins.

---

## ⚙️ Technologies Used

- **Java 21**
- **Spring Boot**
- **PostgreSQL**
- **JWT for Authentication & Authorization**

---

## ℹ️ Getting Started

1. **Install Java 21** on your machine.
2. **Set up a PostgreSQL database** and create a dedicated database for the project.
3. Configure your database connection details in the `application.properties` or `application.yml` file.
4. Run the project with:
   ```bash
   ./mvnw spring-boot:run
   ```
5. Test the APIs using Postman or any API testing tool.

---

## 🎯 Example: Login Request (Postman)


![Screenshot 2025-06-17 011534](https://github.com/user-attachments/assets/5fe8c978-237b-4938-a09e-afa84b9ee267)

---
## 🔐 Forgot Password API Example

This is an example of sending a POST request to the `/api/auth/forgot-password` endpoint using Postman.  
After sending the email, you'll receive a reset code sent to your email address.

![Forgot Password API Example](Screenshot%202025-07-17%20224852.png)

## 🔗 Open Resources

- All resources are open for use and modification.
- You are welcome to contribute or use this project for educational or commercial purposes according to the project license.

---

## 👤 Author

- **Name:** Khaled Ahmed Fathy  
- **Email:** khaledelsbaey5@gmail.com

---

## 📄 License

This project is open source. You can add your license here (e.g., MIT or Apache 2.0).

---

> For questions or contributions, feel free to contact me via the email above.
