<template>
  <div class="product-list-container">
    <h2>商品列表</h2>
    <div class="search-bar">
      <el-input v-model="keyword" placeholder="请输入商品名称" style="width: 300px; margin-right: 10px;"></el-input>
      <el-button type="primary" @click="search">搜索</el-button>
      <el-select v-model="categoryId" placeholder="选择分类" style="margin-left: 20px;">
        <el-option label="全部分类" value=""></el-option>
        <el-option v-for="category in categories" :key="category.id" :label="category.name" :value="category.id"></el-option>
      </el-select>
    </div>
    <div class="product-grid">
      <el-card v-for="product in products" :key="product.id" class="product-card">
        <img :src="product.images.split(',')[0] || 'https://via.placeholder.com/200'" class="product-image" alt="商品图片">
        <div class="product-info">
          <h3>{{ product.name }}</h3>
          <p class="product-price">￥{{ product.price }}</p>
          <p class="product-description">{{ product.description }}</p>
          <el-button type="primary" @click="viewDetail(product.id)">查看详情</el-button>
        </div>
      </el-card>
    </div>
    <div class="pagination">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[10, 20, 30, 50]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      ></el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      products: [],
      categories: [],
      keyword: '',
      categoryId: '',
      currentPage: 1,
      pageSize: 10,
      total: 0
    }
  },
  mounted() {
    this.loadCategories();
    this.loadProducts();
  },
  methods: {
    loadCategories() {
      this.$axios.get('/api/category/list')
        .then(response => {
          if (response.data.success) {
            this.categories = response.data.categories;
          }
        })
        .catch(error => {
          this.$message.error('加载分类失败：' + error.message);
        });
    },
    loadProducts() {
      this.$axios.get('/api/product/list')
        .then(response => {
          if (response.data.success) {
            this.products = response.data.products;
            this.total = this.products.length;
          }
        })
        .catch(error => {
          this.$message.error('加载商品失败：' + error.message);
        });
    },
    search() {
      if (this.keyword) {
        this.$axios.get(`/api/product/search?keyword=${this.keyword}`)
          .then(response => {
            if (response.data.success) {
              this.products = response.data.products;
              this.total = this.products.length;
            }
          })
          .catch(error => {
            this.$message.error('搜索失败：' + error.message);
          });
      } else if (this.categoryId) {
        this.$axios.get(`/api/product/category/${this.categoryId}`)
          .then(response => {
            if (response.data.success) {
              this.products = response.data.products;
              this.total = this.products.length;
            }
          })
          .catch(error => {
            this.$message.error('搜索失败：' + error.message);
          });
      } else {
        this.loadProducts();
      }
    },
    viewDetail(id) {
      this.$router.push(`/product-detail/${id}`);
    },
    handleSizeChange(val) {
      this.pageSize = val;
      this.loadProducts();
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.loadProducts();
    }
  }
}
</script>

<style scoped>
.product-list-container {
  width: 1000px;
  margin: 50px auto;
  padding: 20px;
  border: 1px solid #eaeaea;
  border-radius: 5px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

h2 {
  text-align: center;
  margin-bottom: 20px;
}

.search-bar {
  margin-bottom: 20px;
  display: flex;
  align-items: center;
}

.product-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
  margin-bottom: 20px;
}

.product-card {
  height: 350px;
  display: flex;
  flex-direction: column;
}

.product-image {
  width: 100%;
  height: 200px;
  object-fit: cover;
  margin-bottom: 10px;
}

.product-info {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.product-name {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 5px;
}

.product-price {
  font-size: 18px;
  font-weight: bold;
  color: #ff4d4f;
  margin-bottom: 10px;
}

.product-description {
  font-size: 14px;
  color: #666;
  margin-bottom: 10px;
  flex: 1;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
}

.pagination {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}
</style>