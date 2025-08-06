

### ✅ README Section (Docker-Only Setup)

```markdown
# Notes Management API

The **Notes Management API** is a backend-only system built with Java Spring Boot. It provides RESTful APIs that allow users and admins to create, update, delete, and view notes, with advanced security features such as JWT authentication, profile updates, and password resets.

---

## 🚀 Features

- Create, update, delete, and view notes (users & admins).
- Forgot password functionality via email.
- JWT-based secure authentication and role management.
- PostgreSQL (latest) used as database.
- Fully containerized using Docker.

---

## ⚙️ Technologies Used

- **Java 22**
- **Spring Boot**
- **PostgreSQL (latest)**
- **JWT for Authentication**
- **Docker**

---

## 🐳 Running with Docker

> This project runs entirely inside Docker.  
> All Docker-related files (e.g., `Dockerfile`, `docker-compose.yml`) are inside the `notes/` folder.

### 📁 Folder Structure

```

Note-Application/
└── notes/
├── Dockerfile
├── docker-compose.yml
├── src/
└── ...

````

---

### 🧪 Steps to Run

1. Open your terminal and navigate to the root project directory:

   ```bash
   cd Note-Application/notes
````

2. Build and run the containers using Docker Compose:

   ```bash
   docker-compose up --build
   ```

3. Once the containers are running, the backend will be available at:

   ```
   http://localhost:8080
   ```

---

## 🎯 Example: Login Request (Postman)

![Login Screenshot](https://github.com/user-attachments/assets/5fe8c978-237b-4938-a09e-afa84b9ee267)

---

## 🔐 Forgot Password API Example

Use `/api/auth/forgot-password` to initiate the password reset flow.
You’ll receive a reset code via email after posting a valid request.

![Forgot Password API Example](Screenshot%202025-07-17%20224852.png)

---

## 👤 Author

* **Name:** Khaled Ahmed Fathy
* **Email:** [khaledelsbaey5@gmail.com](mailto:khaledelsbaey5@gmail.com)

---

## 📄 License

This project is open source. Add your license here (e.g., MIT or Apache 2.0).

---

> For support or contributions, feel free to contact me at the email above.

```

---

Let me know if you also want me to write or review the `Dockerfile` and `docker-compose.yml` contents inside `notes/`.
```
