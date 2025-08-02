<template>
  <div class="app sidebar-mini rtl">
    <!-- Navbar -->
    <header class="app-header">
      <ul class="app-nav">
        <li>
          <a class="app-nav__item" href="/order">
            <i class='bx bx-log-out bx-rotate-180'></i>
          </a>
        </li>
      </ul>
    </header>

    <main class="app app-ban-hang">
      <div class="row">
        <div class="col-md-12">
          <div class="app-title">
            <ul class="app-breadcrumb breadcrumb">
              <li class="breadcrumb-item"><a href="#"><b>POS bán hàng</b></a></li>
            </ul>
            <div id="clock"></div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-md-8">
          <div class="tile">
            <h3 class="tile-title">Phần mềm bán hàng</h3>
            <input type="text" id="myInput" ref="myInput" @input="myFunction"
                   placeholder="Nhập mã sản phẩm hoặc tên sản phẩm để tìm kiếm...">
            <div class="du--lieu-san-pham">
              <table class="table table-hover table-bordered">
                <thead>
                <tr>
                  <th class="so--luong">Tên sản phẩm</th>
                  <th class="so--luong">Màu sắc</th>
                  <th class="so--luong">Danh mục</th>
                  <th class="so--luong">Size</th>
                  <th class="so--luong">Số lượng</th>
                  <th class="so--luong">Giá</th>
                  <th class="so--luong">Trạng thái</th>
                </tr>
                </thead>
                <tbody>
                <tr v-if="variations.length === 0">
                  <td class="so--luong" colspan="7">Không tìm thấy sản phẩm nào.</td>
                </tr>
                <tr v-for="variation in variations" :key="variation.variantId" @click="addToCart(variation)">
                  <td class="so--luong">{{ variation.productName }}</td>
                  <td class="so--luong">
                    <div class="color-box"
                         :style="{ backgroundColor: variation.colorName, width: '30px', height: '30px', borderRadius: '4px' }">
                    </div>
                  </td>
                  <td class="so--luong">{{ variation.categoryName }}</td>
                  <td class="so--luong">{{ variation.sizeName }}</td>
                  <td class="so--luong">{{ variation.quantity }}</td>
                  <td class="so--luong">{{ formatCurrency(variation.additionalPrice) }}</td>
                  <td class="so--luong">
                    <span :class="{'badge bg-success': variation.active, 'badge bg-danger': !variation.active}">
                      {{ variation.active ? "Còn hàng" : "Hết hàng" }}
                    </span>
                  </td>
                </tr>
                </tbody>
              </table>
            </div>
            <div class="alert">
              <div class="pagination">
                <button
                    class="page-button"
                    :disabled="currentPage === 0"
                    @click="changePage(currentPage - 1)"
                >
                  Previous
                </button>
                <span>Page {{ currentPage + 1 }} of {{ totalPages }}</span>
                <button
                    class="page-button"
                    :disabled="currentPage >= totalPages - 1"
                    @click="changePage(currentPage + 1)"
                >
                  Next
                </button>
              </div>
            </div>
            <div class="tile mt-3">
              <h3 class="tile-title">Giỏ hàng</h3>
              <table class="table table-hover table-bordered">
                <thead>
                <tr>
                  <th>Tên biến thể</th>
                  <th>Số lượng</th>
                  <th>Size</th>
                  <th>Màu sắc</th>
                  <th>Giá bán</th>
                  <th>Tổng</th>
                  <th>Hành động</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="item in cart" :key="item.id">
                  <td>{{ item.productName }}</td>

                  <td>
                    <div class="input-group input-group-sm">
                      <button class="btn btn-outline-secondary" @click="decreaseQuantity(item)">-</button>
                      <input
                          type="number"
                          class="form-control text-center"
                          min="1"
                          v-model.number="item.quantity"
                          @change="validateQuantity(item)"
                      />
                      <button class="btn btn-outline-secondary" @click="increaseQuantity(item)">+</button>
                    </div>
                  </td>
                  <td>{{ item.sizeName }}</td>
                  <td class="so--luong">
                    <div class="color-box"
                         :style="{ backgroundColor: item.colorName, width: '30px', height: '30px', borderRadius: '4px' }">
                    </div>
                  </td>
                  <td>{{ formatCurrency(item.additionalPrice) }}</td>
                  <td>{{ formatPrice(item.quantity * item.additionalPrice) }}</td>
                  <td>
                    <button class="btn btn-danger btn-sm" @click="removeFromCart(item)">Xóa</button>
                  </td>
                </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
        <div class="col-md-4">
          <div class="tile">
            <h3 class="tile-title">Thông tin thanh toán</h3>
            <div class="row">
              <div class="form-group col-md-12">
                <label class="control-label">Khách hàng </label>
                <select class="form-control" v-model="selectedCustomer">
                  <option v-for="customer in sortedCustomers" :key="customer.id" :value="customer">
                    {{ customer.name }}
                  </option>
                </select>
              </div>

              <div class="form-group col-md-12">
                <label class="control-label">Ghi chú đơn hàng</label>
                <textarea class="form-control" rows="4" placeholder="Ghi chú thêm đơn hàng"
                          v-model="orderNote"></textarea>
              </div>
            </div>
            <div class="row">
              <div class="form-group col-md-12">
                <label class="control-label">Hình thức thanh toán</label>
                <select class="form-control" v-model="paymentMethod" required>
                  <option value="1">cod</option>
                  <option value="2">credit_card</option>
                  <option value="3">paypal</option>
                  <option value="4">bank_transfer</option>
                </select>
              </div>
              <div class="form-group col-md-6">
                <label class="control-label">Tạm tính tiền hàng: </label>
                <p class="control-all-money-tamtinh">= {{ formatPrice(totalAmount) }} VNĐ</p>
              </div>
              <div class="form-group col-md-6">
                <label class="control-label">Tổng cộng thanh toán: </label>
                <p class="control-all-money-total">= {{ formatPrice(finalAmount) }} VNĐ</p>
              </div>
              <div class="form-group col-md-6">
                <label class="control-label">Khách hàng đưa tiền (F8): </label>
                <input class="form-control" style="width: 100%" type="number" v-model="amountReceived"
                       placeholder="Nhập số tiền khách đưa">
              </div>

              <div class="form-group col-md-6">
                <label class="control-label">Trả lại khách hàng: </label>
                <p class="control-all-money"> {{ formatPrice(changeDue) }} VNĐ</p>
              </div>
              <div class="tile-footer col-md-12">
                <button class="btn btn-primary luu-san-pham" type="button" @click="saveOrder">Lưu đơn hàng</button>
                <button class="btn btn-primary luu-san-pham" type="button" @click="cancel">Quay về</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      variations: [],
      customers: [],
      default_customer: {},
      cart: [],
      selectedEmployee: '',
      currentPage: 0, // Số trang hiện tại
      pageSize: 4, // Số sản phẩm mỗi trang
      orderNote: '',
      codevoucher: '',
      status: '',
      paymentMethod: '',
      discount: 0,
      amountReceived: 0,
    };
  },
  computed: {
    totalAmount() {
      return this.cart.reduce((total, item) => {
        return total + (item.additionalPrice * item.quantity);
      }, 0);
    },
    finalAmount() {
      return this.totalAmount - this.discount;
    },
    changeDue() {
      return Math.max(this.amountReceived - this.finalAmount, 0); // Không để số âm
    },
    sortedCustomers() {
      if (!this.customers.length) return [];
      const lastCustomer = this.customers[this.customers.length - 1]; // Lấy phần tử cuối cùng
      const remainingCustomers = this.customers.slice(0, -1); // Lấy các phần tử còn lại
      return [lastCustomer, ...remainingCustomers]; // Đưa phần tử cuối lên đầu
    }
  },
  mounted() {
    this.fetchProducts(this.currentPage, this.pageSize); // Gọi hàm để lấy tất cả sản phẩm khi component được mount
    this.fetchCustomer(); // Gọi hàm để lấy tất cả khách hàng khi component được mount
  },
  methods: {
    selectid(id) {
      console.log("Biến thể: " + id);
    },

    async myFunction() {
      const searchKeyword = this.$refs.myInput.value; // Lấy giá trị từ ô tìm kiếm
      console.log(searchKeyword);
      try {
        if (!searchKeyword.trim()) {
          // Nếu input tìm kiếm bị xóa trắng, gọi fetchProducts để lấy lại toàn bộ sản phẩm
          return this.fetchProducts(this.currentPage, this.pageSize);
        }

        const response = await axios.get(`http://localhost:8080/MiniatureCrafts/result/${searchKeyword}`);
        this.variations = [...response.data.content];
        console.log("Dữ liệu sản phẩm:", this.variations);
      } catch (error) {
        console.error("Lỗi khi lấy sản phẩm:", error);
      }
    },

    async fetchProducts(page, size) {
      try {
        const response = await axios.get(`http://localhost:8080/api/variant?page=${page}&size=${size}`);
        this.variations = response.data.content; // Lấy danh sách sản phẩm
        this.totalPages = response.data.totalPages; // Tổng số trang
        console.log("Dữ liệu sản phẩm:", this.variations);
      } catch (error) {
        console.error("Có lỗi xảy ra khi lấy dữ liệu sản phẩm:", error);
      }
    },
    async fetchCustomer() {
      try {
        const response = await axios.get(`http://localhost:8080/admin/customer/result/all`);
        this.customers = response.data;

        if (this.customers.length > 0) {
          this.selectedCustomer = this.customers[this.customers.length - 1]; // Gán phần tử cuối cùng
        }
      } catch (error) {
        console.error("Lỗi khi lấy danh sách khách hàng:", error);
      }
    },
    increaseQuantity(item) {
      const maxQuantity = item.originalQuantity ?? item.quantityAvailable ?? item.max ?? 0;

      // Nếu tồn kho được lưu sẵn trong item
      if (item.quantity < maxQuantity) {
        item.quantity += 1;
      } else {
        alert('Vượt quá số lượng tồn kho!');
      }
    },

    decreaseQuantity(item) {
      if (item.quantity > 1) {
        item.quantity -= 1;
      }
    },
    validateQuantity(item) {
      const maxQuantity = item.originalQuantity ?? item.quantityAvailable ?? item.max ?? 0;

      if (!item.quantity || item.quantity < 1) {
        item.quantity = 1;
      } else if (item.quantity > maxQuantity) {
        alert('Vượt quá số lượng tồn kho!');
        item.quantity = maxQuantity;
      }
    },

    removeFromCart(item) {
      this.cart = this.cart.filter(product => product.variantid !== item.variantid);
    },

    addToCart(variant) {
      const existingProduct = this.cart.find(item => item.variantid === variant.variantId);

      if (existingProduct) {
        if (existingProduct.quantity >= variant.quantity) {
          alert('Sản phẩm đã đạt số lượng tối đa trong kho!');
          return;
        } else {
          existingProduct.quantity += 1;
        }
      } else {
        this.cart.push({
          ...variant,
          quantity: 1,
          originalQuantity: variant.quantity,
          variantid: variant.id,
        });
      }
    },


    parsePrice(priceString) {
      // Chuyển đổi giá từ chuỗi sang số
      return parseFloat(priceString.replace(/\./g, '').replace(' ₫', ''));
    },

    formatPrice(price) {
      console.log("loi o formatPrice: " + price);
      // Định dạng giá thành chuỗi
      return price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".") + " ₫";
    },

    changePage(page) {
      if (page < 0 || page >= this.totalPages) return; // Kiểm tra giới hạn trang
      this.currentPage = page;
      this.fetchProducts(this.currentPage, this.pageSize); // Tải dữ liệu trang mới
    },

    formatCurrency(value) {
      // Chuyển đổi giá trị thành chuỗi và định dạng với dấu phân cách hàng nghìn
      return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, '.') + ' ₫';
    },

    getImagesUrl(images) {
      return images
          ? `http://localhost:8080/upload/images/${images.cd_Images}`
          : "/img/default.jpg";
    },

    cancel() {
      this.$router.push('/');
    },

    mapPaymentMethod(id) {
      switch (id) {
        case "1":
          return "credit_card";
        case "2":
          return "cod";
        case "3":
          return "paypal";
        case "4":
          return "bank_transfer";
        default:
          return "cod";
      }
    },

    async saveOrder() {
      if (this.cart.length === 0) {
        alert("Giỏ hàng trống! Vui lòng thêm sản phẩm trước khi lưu đơn hàng.");
        return;
      }

      const orderData = {
        userId: 1,

        orderDate: new Date(),
        subtotal: this.cart.reduce((sum, item) => sum + (item.additionalPrice * item.quantity), 0),
        discount_amount: this.discount || 0,
        shippingFee: 0,
        totalAmount:
            this.cart.reduce((sum, item) => sum + (item.additionalPrice * item.quantity), 0) - (this.discount || 0),

        orderStatus: "pending",
        paymentMethod: this.mapPaymentMethod(this.paymentMethod),
        paymentStatus: "unpaid",
        voucherCode: this.codevoucher || null,
        notes: this.orderNote || "",

        orderItems: this.cart.map(item => ({
          variantId: item.variantId,
          quantity: item.quantity,
          unitPrice: item.additionalPrice,
          // total_price: item.additionalPrice * item.quantity
        }))
      };

      console.log("✅ Order Data gửi backend:", orderData);

      try {
        const response = await axios.post("http://localhost:8080/api/orders/create", orderData);

        if (response.status === 201 || response.status === 200) {
          alert("Đơn hàng đã được lưu thành công!");
          this.fetchProducts(this.currentPage, this.pageSize);
          this.cart = [];
          this.orderNote = '';
          this.paymentMethod = '';
          this.selectedCustomer = null;
        } else {
          alert("Không thể lưu đơn hàng. Vui lòng thử lại!");
        }
      } catch (error) {
        console.error("Lỗi khi lưu đơn hàng:", error);
        alert("Đơn hàng thiếu phương thức thanh toán hoặc khách hàng!");
      }
    }

  }
};
</script>

<style>
.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 15px;
  gap: 12px;
}

.page-button {
  padding: 6px 16px;
  font-size: 14px;
  font-weight: 600;
  background-color: #007bff; /* Bootstrap primary */
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background 0.2s ease;
}

.page-button:hover {
  background-color: #0056b3;
}

.page-button:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}

.pagination span {
  font-weight: bold;
  font-size: 15px;
}

</style>