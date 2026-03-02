#  Student Management System – Spring Boot & Thymeleaf

---

## 1. Thông tin nhóm

| STT | Họ và tên     | MSSV    | Email                                                                 |
| --- | ------------- | ------- | --------------------------------------------------------------------- |
| 1   | Lê Quốc Kiệt  | 2311767 | [kiet.lequoc7923@hcmut.edu.vn](mailto:kiet.lequoc7923@hcmut.edu.vn)   |

---

## 2. Public URL của Web Service (Lab 5)

Ứng dụng đã được deploy và có thể truy cập tại:

 **https://student-manager-api-cmzp.onrender.com/students**

Trang web hiển thị danh sách sinh viên, hỗ trợ tìm kiếm, xem chi tiết, thêm mới, chỉnh sửa và xóa sinh viên theo mô hình **SSR (Server Side Rendering)**.

---

## 3. Hướng dẫn chạy dự án ở môi trường local

### 3.1. Yêu cầu hệ thống

- Java JDK 17 trở lên  
- Maven 3.9+  
- PostgreSQL (hoặc sử dụng DB đã deploy trên Render)  
- IDE: IntelliJ IDEA / VS Code (khuyến nghị IntelliJ IDEA)

---

### 3.2. Các bước chạy dự án

#### **Bước 1: Clone source code**

```bash
git clone https://github.com/Sharky-165/student-manager.git
cd student-manager
````

#### **Bước 2: Cấu hình Database**

Trong file `application.properties` (hoặc `application.yml`):

```properties
spring.datasource.url=jdbc:postgresql://<host>:<port>/<database>
spring.datasource.username=<username>
spring.datasource.password=<password>

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

#### **Bước 3: Build & chạy ứng dụng**

```bash
mvn clean install
mvn spring-boot:run
```

#### **Bước 4: Truy cập hệ thống**

```
http://localhost:8080/students
```

---

## 4. Trả lời câu hỏi lý thuyết trong Lab

### 4.1. Ràng buộc Khóa Chính (Primary Key)

Hiện tượng: Database trả về lỗi UNIQUE constraint failed hoặc duplicate key value khi cố gắng chèn một bản ghi có id đã tồn tại trong hệ thống.

Giải thích: Khóa chính (Primary Key) là định danh mỗi hàng trong bảng. Phải **không trùng lặp** và **không NULL**. Vi phạm ràng buộc này gây sai lệch dữ liệu và phá vỡ tính toàn vẹn thực thể.

---

### 4.2. Toàn vẹn dữ liệu (Constraints)

Thí nghiệm & Kết quả:

* Chèn sinh viên thiếu cột name sẽ thành công nếu cột cho phép *NULL*.
* Nếu có ràng buộc NOT NULL → database chặn thao tác.

Ảnh hưởng:

* Nếu không kiểm soát chặt chẽ → Java nhận giá trị null → gây lỗi *NullPointerException*.
* Cần ràng buộc dữ liệu đồng bộ ở cả Database và Application (Hibernate Validation).

---

### 4.3. Cấu hình Hibernate – Dữ liệu bị mất khi restart

Hiện tượng: Dữ liệu bị xóa sạch sau mỗi lần chạy lại app.
Nguyên nhân: sử dụng

```
spring.jpa.hibernate.ddl-auto=create
```

→ Hibernate xóa toàn bộ bảng và tạo lại schema mới khi ứng dụng khởi động.

Giải pháp: Chuyển sang:

```
spring.jpa.hibernate.ddl-auto=update
```

→ chỉ cập nhật cấu trúc, **không làm mất dữ liệu**.

---

## 5. Screenshot và mô tả các module trong Lab 4

---

###  Giao diện xem toàn bộ sinh viên

<img width="1917" height="909" src="https://github.com/user-attachments/assets/fd35a68b-5169-4784-a28a-07bacf55a920" />

**Đường dẫn (URL):**

```
/students
```

* Hiển thị danh sách sinh viên dạng bảng
* Thông tin gồm: ID, Họ tên, Email, Tuổi
* Có ô tìm kiếm theo tên
* Nút “Add” để tạo sinh viên
* Nhấn vào từng sinh viên để xem chi tiết
* Sinh viên dưới 18 tuổi được tô màu đỏ

---

###  Giao diện chi tiết sinh viên

<img width="1915" height="962" src="https://github.com/user-attachments/assets/af9a0ebe-637c-4eed-9857-c77a53e09d2c" />

**Đường dẫn (URL):**

```
/students/{id}
```

Chức năng:

* Hiển thị chi tiết sinh viên (ID, Họ và Tên, Email, Tuổi)
* Nút "Back" quay về danh sách
* Nút “Save” lưu chỉnh sửa
* Nút “Delete” xóa sinh viên

---

###  Giao diện thêm mới sinh viên

<img width="1917" height="910" src="https://github.com/user-attachments/assets/2d32a6fd-8bd1-41b6-afde-36e01a3d1cbc" />

**Đường dẫn (URL):**

```
/students/0
```

* Form nhập liệu: Họ tên, Email, Tuổi
* Giá trị mặc định được set trong form

---

###  Giao diện chỉnh sửa & xóa sinh viên

<img width="1917" height="909" src="https://github.com/user-attachments/assets/dc798ca2-bac3-4dff-b995-7de9203bead3" />

**Đường dẫn (URL):**

```
/students/{id}
```

* Hiển thị thông tin sinh viên
* Nút Back / Save / Delete
* Điều hướng hợp lý sau thao tác

---

###  Chức năng tìm kiếm sinh viên

<img width="1916" height="964" src="https://github.com/user-attachments/assets/28156a9e-07b8-4361-b8ef-43b83c802daf" />

**Đường dẫn (URL):**

```
/students?keyword={name}
```

**Mô tả chức năng:**

* Tìm kiếm theo tên thông qua ô nhập liệu
* Thực hiện bằng phương thức GET
* Nếu không nhập từ khóa → hiển thị toàn bộ sinh viên
* Trả về danh sách dạng bảng

**Luồng xử lý:**

1. Người dùng nhập từ khóa → nhấn *Tìm*
2. Controller nhận `keyword`
3. Service gọi Repository truy vấn
4. Render kết quả bằng Thymeleaf

