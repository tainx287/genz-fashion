# 👟 GenZ Fashion E-Commerce Website
> 👗 Website bán hàng thời trang dành cho thế hệ Gen Z – năng động, hiện đại và yêu công nghệ.  
> Powered by **GenZStyle** | Made with ❤️ by FPT Polytechnic Dev Team.

---

## 📌 Mục lục
- [💡 Giới thiệu](#giới-thiệu)
- [📈 Tính năng chính](#tính-năng-chính)
- [🧱 Kiến trúc dự án](#kiến_trúc_dự_án)
- [⚙️ Công nghệ sử dụng](#công-nghệ-sử-dụng)
- [🚀 Cài đặt & chạy thử](#cài-đặt--chạy-thử)
  - [Backend (Spring Boot)](#backend-spring-boot)
  - [Frontend (Vue)](#frontend-vue)
- [🗂️ Cấu trúc thư mục](#cấu-trúc-thư-mục)
- [🔒 Chính sách & Bảo mật](#chính-sách--bảo-mật) 
- [📊 Phân tích & mở rộng](#phân-tích--mở-rộng)
- [🧑‍💻 Team phát triển](#team-phát-triển)
- [🤝 Đóng góp](#đóng-góp)
- [📜 Giấy phép](#giấy-phép)

## 🎯 Giới thiệu

**GenZ Fashion** là nền tảng thương mại điện tử giúp bạn trẻ Gen Z dễ dàng khám phá và mua sắm các sản phẩm thời trang phong cách **streetwear**, **techwear**, và nhiều xu hướng độc đáo.

Trang web tích hợp giao diện hiện đại, trải nghiệm mượt mà, bộ lọc thông minh, và các hình thức thanh toán linh hoạt. Được phát triển theo chuẩn **kiến trúc Layered MVC**, dự án hướng tới trải nghiệm **mua sắm số hóa toàn diện** cho thế hệ mới.

---

## 🚀 Tính năng chính

### 👥 Người dùng
- Đăng ký/Đăng nhập (hỗ trợ Social Login: Facebook, Google)
- Duyệt & lọc sản phẩm theo danh mục, màu, size, giá
- Thêm sản phẩm vào giỏ, thanh toán nhiều hình thức (COD, ví điện tử)
- Quản lý đơn hàng, đánh giá sản phẩm
- Nhận thông báo trạng thái đơn hàng
- Tích hợp chatbot hỗ trợ

### 🛠️ Admin
- CRUD sản phẩm
- Quản lý đơn hàng và người dùng
- Tạo chương trình khuyến mãi
- Dashboard thống kê: doanh thu, đơn hàng, lượt truy cập

---

## 🧱 Kiến trúc dự án

Dự án được thiết kế theo mô hình:

- `Layered MVC` cho Backend
- `Component-based` cho Frontend (React)
- Tách biệt rõ ràng giữa `Business Logic`, `Presentation`, và `Data Access Layer`

> Reusable logic thông qua: `BaseService<T>`, `GenericRepository<T>`, `AOP` để giảm thiểu boilerplate code.

---

## ⚙️ Công nghệ sử dụng

| Layer       | Công nghệ sử dụng                                    |
|-------------|------------------------------------------------------|
| Frontend    | VueJS 3, Bootstrap, Axios, Figma (UI/UX Design)      |
| Backend     | Java 21, Spring Boot 3.4.5, Spring Security, JPA     |
| Database    | Microsoft SQL Sever                                  |
| Auth        | JWT, OAuth2 (Google, Facebook Login)                 |
| DevOps      | GitHub Actions, Docker (optional), Firebase Hosting  |

---

## 🚀 Cài đặt & Chạy thử dự án

### 1.🧰 Yêu cầu hệ thống
- Java 17 trở lên
- Maven 3.8+
- Node.js 16+ và npm hoặc Yarn
- Github desktop
- SQL sever hoặc Docker

### 2. 📦 Clone dự án
git clone https://github.com/your-username/genz-fashion.git
cd genz-fashion

### 3.🛠️ Backend - `EcommerceApplication.yml`
cd backend
./mvnw clean install
./mvnw spring-boot:run

### 4.💻 Frondend - `EcommerceApplication.yml`
cd frontend
npm install
npm run dev

### 5.🐳 Docker
docker-compose up --build

### 6.🌐 Truy cập
- Frontend: http://localhost:5173
- Backend API: http://localhost:8080/api
- Swagger: http://localhost:8080/swagger-ui/index.html

### 7.🧪 Cấu hình Biến Môi Trường
- Frontend (.env):
VITE_API_URL=http://localhost:8080  # Trỏ đến backend

- Backend (.yaml):
server:
  port: 8080
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/your_database
    username: your-username
    password: your-password
  jpa:
    hibernate:
      ddl-auto: update
jwt:
  secret: your-secret-key
  expirationMs: 86400000

---

## 🗂️Cấu trúc thư mục
```plaintext
ecommerce-project/
├── backend/                        # Spring Boot (Java)
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/ecommerce/
│   │   │   │   ├── GenZFashionApplication.java
│   │   │   │   ├── aop/                      # Aspect-Oriented Programming (logging, security...)
│   │   │   │   │   ├── LoggingAspect.java
│   │   │   │   │   ├── SecurityAspect.java
│   │   │   │   │   └── TransactionAspect.java
│   │   │   │   ├── config/                   # Spring, Security, Swagger config
│   │   │   │   │   ├── SecurityConfig.java
│   │   │   │   │   ├── WebMvcConfig.java
│   │   │   │   │   └── SwaggerConfig.java
│   │   │   │   ├── controller/               # API entrypoints
│   │   │   │   │   ├── AuthController.java
│   │   │   │   │   ├── ProductController.java
│   │   │   │   │   ├── OrderController.java
│   │   │   │   │   ├── CartController.java
│   │   │   │   │   ├── AdminController.java
│   │   │   │   │   └── PromotionController.java
│   │   │   │   ├── dto/                      # Data Transfer Objects
│   │   │   │   │   ├── ProductDto.java
│   │   │   │   │   ├── OrderDto.java
│   │   │   │   │   ├── UserDto.java
│   │   │   │   │   └── AuthDto.java
│   │   │   │   ├── entity/                   # JPA Entities
│   │   │   │   │   ├── User.java
│   │   │   │   │   ├── Product.java
│   │   │   │   │   ├── Order.java
│   │   │   │   │   ├── Cart.java
│   │   │   │   │   ├── Review.java
│   │   │   │   │   ├── Promotion.java
│   │   │   │   │   └── Notification.java
│   │   │   │   ├── repository/               # JPA Repositories
│   │   │   │   │   ├── UserRepository.java
│   │   │   │   │   ├── ProductRepository.java
│   │   │   │   │   ├── OrderRepository.java
│   │   │   │   │   ├── CartRepository.java
│   │   │   │   │   ├── PromotionRepository.java
│   │   │   │   │   └── GenericRepository<T>.java
│   │   │   │   ├── service/                  # Business Logic
│   │   │   │   │   ├── AuthService.java
│   │   │   │   │   ├── ProductService.java
│   │   │   │   │   ├── OrderService.java
│   │   │   │   │   ├── CartService.java
│   │   │   │   │   ├── ReviewService.java
│   │   │   │   │   └── PromotionService.java
│   │   │   │   ├── service/impl/             # Service Implementations
│   │   │   │   │   ├── AuthServiceImpl.java
│   │   │   │   │   ├── ProductServiceImpl.java
│   │   │   │   │   ├── OrderServiceImpl.java
│   │   │   │   │   ├── CartServiceImpl.java
│   │   │   │   │   └── PromotionServiceImpl.java
│   │   │   │   ├── exception/                # Exception Handling
│   │   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   │   ├── CustomException.java
│   │   │   │   │   ├── ApiError.java
│   │   │   │   │   ├── BaseException.java
│   │   │   │   │   ├── ValidationExceptionHandler.java
│   │   │   │   │   ├── SecurityException.java
│   │   │   │   │   └── ErrorResponse.java
│   │   │   │   ├── strategy/                 # Strategy Pattern for promotions
│   │   │   │   │   ├── PromotionStrategy.java
│   │   │   │   │   ├── PercentageDiscount.java
│   │   │   │   │   ├── FixedAmountDiscount.java
│   │   │   │   │   └── VipCustomerDiscount.java
│   │   │   │   ├── mapper/                   # MapStruct
│   │   │   │   │   └── ProductMapper.java
│   │   │   │   └── util/                     # Shared utilities
│   │   │   │       └── JwtUtils.java
│   │   └── resources/
│   │       ├── application.yml               # Configurations
│   │       ├── static/
│   │       └── templates/
│   └── test/
│       └── com/example/ecommerce/
│           └── ProductServiceTests.java
│
├── frontend/                       # Vue 3 + Bootstrap + Vite
│   ├── public/
│   │   ├── index.html
│   │   ├── favicon.ico
│   │   └── assets/
│   │       ├── icons/
│   │       ├── images/
│   │       └── styles/
│   ├── src/
│   │   ├── api/
│   │   │   ├── axios.js
│   │   │   ├── auth.api.js
│   │   │   └── product.api.js
│   │   ├── assets/
│   │   │   ├── scss/
│   │   │   │   ├── _variables.scss
│   │   │   │   └── _bootstrap-overrides.scss
│   │   │   └── fonts/
│   │   ├── components/
│   │   │   ├── atoms/
│   │   │   ├── molecules/
│   │   │   └── organisms/
│   │   ├── composables/
│   │   │   ├── useAuth.js
│   │   │   └── useApi.js
│   │   ├── layouts/
│   │   │   ├── DefaultLayout.vue
│   │   │   └── AuthLayout.vue
│   │   ├── router/
│   │   │   └── index.js
│   │   ├── stores/
│   │   │   ├── auth.store.js
│   │   │   └── cart.store.js
│   │   ├── views/
│   │   │   ├── auth/
│   │   │   │   ├── Login.vue
│   │   │   │   └── Register.vue
│   │   │   ├── product/
│   │   │   │   ├── ProductList.vue
│   │   │   │   └── ProductDetail.vue
│   │   │   └── Home.vue
├── README.md
├── docs/                # Tài liệu bổ sung, UML, kiến trúc hệ thống
├── database/
│   ├── schema.sql       # SQL khởi tạo DB
│   └── seed.sql         # Dữ liệu mẫu để test
├── migrations/          # Nếu dùng tool migration
├── .gitignore
└── LICENSE
---

## 🔒 Chính sách & Bảo mật
1. **Nhánh chính (`main`)**  
   - Không push trực tiếp → Luôn tạo nhánh mới (ví dụ: `feature/login`).  
   - Merge vào `main` qua Pull Request (tự review hoặc nhờ mentor).  

2. **Commit an toàn**  
   - Tiêu đề bằng tiếng Anh (ví dụ: `Fix login button color`).  
   - Không commit file `.env`/`node_modules`.  

3. **Backup code**  
   - Push lên GitHub thường xuyên để tránh mất dữ liệu.
 
## 📊 Phân tích & mở rộng
- Đánh tag `v1.0.0` cho bản cuối cùng.  
- Xóa nhánh thừa sau khi merge (ví dụ: `feature/login`).

## 🧑‍💻 Thành viên nhóm | Team phát triển

| Tên thành viên      | Vai trò                          |
|---------------------|----------------------------------|
| Nguyễn Xuân Tài     | Backend Lead - Kiến trúc hệ thống|
| Phạm Minh Huyền     | Frontend Developer - Vue UI      |
| Vũ Nguyễn Đức Bách  | DevOps + Database                |
| Văn Thị Ngọc Hà     | UI/UX Designer                   |
| Nguyễn Văn Hùng     | Product Owner - Viết tài liệu    |
|---------------------|----------------------------------|

## 🤝 Đóng góp
Chúng tôi luôn chào đón những đóng góp từ cộng đồng!
Fork repo này, tạo branch mới và PR
Thực hiện theo convention đã thiết lập trong repo

## 📝 Giấy phép
MIT License © 2025 GenZStyle.
Dự án chỉ phục vụ mục đích học tập – không dùng cho mục đích thương mại nếu chưa có sự cho phép từ nhóm phát triển.
