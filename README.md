# Student Management System – Spring Boot & Thymeleaf

## 1. Thông tin nhóm

| STT | Họ và tên       | MSSV    | Email                                                                 |
| --- | --------------- | ------- | --------------------------------------------------------------------- |
| 1   | Lê Quốc Kiệt | 2311767 | [kiet.lequoc7923@hcmut.edu.vn](mailto:kiet.lequoc7923@hcmut.edu.vn) |


---

## 2. Public URL của Web Service (Lab 5)

Ứng dụng đã được deploy và có thể truy cập tại:

[**[https://student-manager-api-cmzp.onrender.com/students](https://student-manager-api-cmzp.onrender.com/students)**]

Trang web hiển thị danh sách sinh viên, hỗ trợ tìm kiếm, xem chi tiết, thêm mới, chỉnh sửa và xóa sinh viên theo mô hình SSR (Server Side Rendering).

---

## 3. Hướng dẫn chạy dự án ở môi trường local

### 3.1. Yêu cầu hệ thống

- Java JDK 17 trở lên
- Maven 3.9+
- PostgreSQL (hoặc sử dụng cấu hình database đã deploy trên Render)
- IDE: IntelliJ IDEA / VS Code (khuyến nghị IntelliJ IDEA)

---

### 3.2. Các bước chạy dự án

**Bước 1: Clone source code**

```bash
git clone https://github.com/Sharky-165/student-manager.git
cd student-manager
```

**Bước 2: Cấu hình Database**

Trong file `application.properties` (hoặc `application.yml`), cấu hình:

```properties
spring.datasource.url=jdbc:postgresql://<host>:<port>/<database>
spring.datasource.username=<username>
spring.datasource.password=<password>

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

**Bước 3: Build & chạy ứng dụng**

```bash
mvn clean install
mvn spring-boot:run
```

**Bước 4: Truy cập hệ thống**

```
http://localhost:8080/students
```

---

## 4. Trả lời câu hỏi lý thuyết trong Lab

# 4.1. Ràng buộc Khóa Chính (Primary Key)
Hiện tượng: Database trả về lỗi UNIQUE constraint failed hoặc duplicate key value khi cố gắng chèn một bản ghi có id đã tồn tại trong hệ thống.

Giải thích: Khóa chính (Primary Key) đóng vai trò là định danh duy nhất cho mỗi hàng trong bảng. Database bắt buộc giá trị này phải không trùng lặp và không được phép *NULL*. Việc vi phạm ràng buộc này khiến hệ thống không thể phân biệt các bản ghi, dẫn đến sai lệch dữ liệu và phá vỡ tính toàn vẹn thực thể.

# 4.2. Toàn vẹn dữ liệu (Constraints)
Thí nghiệm & Kết quả: Chèn sinh viên thiếu cột name sẽ thành công nếu cột đó cho phép *NULL*. Ngược lại, nếu có ràng buộc NOT NULL, database sẽ chặn hành động này để bảo vệ cấu trúc dữ liệu.

Ảnh hưởng: Nếu không kiểm soát chặt chẽ, phía Java sẽ nhận giá trị null, dễ dẫn đến lỗi *NullPointerException* hoặc sai lệch logic hiển thị. Do đó, việc ràng buộc dữ liệu cần được thực hiện đồng bộ ở cả tầng Database và tầng Application (sử dụng Hibernate Validation).

# 4.3. Cấu hình Hibernate – Dữ liệu bị mất khi restart
Hiện tượng & Nguyên nhân: Dữ liệu bị xóa sạch sau mỗi lần khởi động lại do sử dụng cấu hình ```bash spring.jpa.hibernate.ddl-auto=create```. Chế độ này yêu cầu Hibernate xóa bỏ toàn bộ các bảng cũ và khởi tạo lại schema mới từ đầu mỗi khi ứng dụng chạy.

Giải pháp: Chuyển đổi cấu hình sang ```bash spring.jpa.hibernate.ddl-auto=update```. Ở chế độ này, Hibernate sẽ đối chiếu schema hiện tại với các Entity trong Java để chỉ cập nhật những thay đổi về cấu trúc mà không làm mất dữ liệu hiện có.

## 5. Screenshot và mô tả các module trong Lab 4
*Giao diện xem toàn bộ sinh viên*<img width="1912" height="966" alt="image" src="https://github.com/user-attachments/assets/d419ac67-7107-484e-92a7-4f944e6fe422" />

- Hiển thị danh sách toàn bộ sinh viên dưới dạng bảng.
- Mỗi sinh viên bao gồm các thông tin: ID, họ và tên, email, tuổi.
- Tích hợp ô nhập liệu cho phép tìm kiếm sinh viên theo tên.
- Cung cấp nút “Add” để điều hướng sang trang tạo sinh viên.
- Mỗi dòng dữ liệu có liên kết nhấn vào để truy cập trang thông tin chi tiết của sinh viên tương ứng.
- Các sinh viên có tuổi nhỏ hơn 18 được hiển thị nổi bật nhằm minh họa xử lý logic hiển thị phía server.

*Giao diện thêm sinh viên* <img width="1917" height="910" alt="image" src="https://github.com/user-attachments/assets/2d32a6fd-8bd1-41b6-afde-36e01a3d1cbc" />

- Hiển thị form nhập liệu bao gồm các trường(các giá trị mặc định được set):
  - Họ và Tên
  - Email
  - Tuổi

*Giao diện chỉnh sửa sinh viên, xóa sinh viên* <img width="1917" height="909" alt="image" src="https://github.com/user-attachments/assets/dc798ca2-bac3-4dff-b995-7de9203bead3" />

- Hiển thị đầy đủ thông tin chi tiết của một sinh viên:
  - ID
  - Họ và Tên
  - Email
  - Tuổi
- Cung cấp nút “Chỉnh Sửa” để điều hướng sang trang cập nhật thông tin sinh viên.
- Cung cấp nút “Xóa” sinh viên:
  - Sau khi xóa thành công, hệ thống điều hướng về Trang Danh Sách.
 
  *Công cụ tìm kiếm*
Đường dẫn (URL):
```
GET /students?keyword={name}
```
*Mô tả chức năng:*
- Cho phép người dùng tìm kiếm sinh viên theo *tên* thông qua ô nhập liệu trên Trang Danh Sách.
- Tìm kiếm được thực hiện theo phương thức *GET*, tham số `keyword` được truyền thông qua query string.
- Hệ thống xử lý tìm kiếm phía server và trả về danh sách sinh viên thỏa điều kiện.
- Nếu không nhập từ khóa, hệ thống hiển thị toàn bộ danh sách sinh viên.
- Kết quả tìm kiếm được hiển thị dưới dạng bảng tương tự Trang Danh Sách Sinh Viên.

*Luồng xử lý:*
- Người dùng nhập tên sinh viên vào ô tìm kiếm và nhấn nút *“Tìm”*.
- Controller nhận tham số `keyword` từ request.
- Service gọi Repository để truy vấn dữ liệu phù hợp.
- Kết quả được đưa vào Model và render lại trang danh sách bằng Thymeleaf.



