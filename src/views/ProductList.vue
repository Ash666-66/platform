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
const mockData = require('../mock/data');
export default {
  data: function() {
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
  mounted: function() {
    this.loadCategories();
    this.loadProducts();
  },
  methods: {
    loadCategories: function() {
      // 使用模拟数据
      this.categories = mockData.categories;
      this.$message.success('加载分类成功', { duration: 500 });
    },
    loadProducts: function() {
      // 使用模拟数据
      this.products = mockData.products;
      this.total = this.products.length;
      this.$message.success('加载商品成功', { duration: 500 });
    },
    search: function() {
      // 使用模拟数据进行搜索和筛选
      var filteredProducts = mockData.products;
      
      if (this.keyword) {
        filteredProducts = filteredProducts.filter(function(p) {
          return p.name.includes(this.keyword) || p.description.includes(this.keyword);
        }.bind(this));
      }
      
      if (this.categoryId) {
        filteredProducts = filteredProducts.filter(function(p) {
          return p.categoryId === parseInt(this.categoryId);
        }.bind(this));
      }
      
      this.products = filteredProducts;
      this.total = this.products.length;
      this.$message.success('搜索和筛选成功');
    },
    viewDetail: function(id) {
      this.$router.push('/product-detail/' + id);
    },
    handleSizeChange: function(val) {
      this.pageSize = val;
      this.loadProducts();
    },
    handleCurrentChange: function(val) {
      this.currentPage = val;
      this.loadProducts();
    }
  }
}
</script>

<style scoped>
.product-list-container {
  width: 1200px;
  margin: 30px auto;
  padding: 30px;
  border-radius: 8px;
  background-color: #ffffff;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.1);
}

h2 {
  text-align: center;
  margin-bottom: 30px;
  font-size: 24px;
  color: #303133;
  font-weight: 600;
}

.search-bar {
  margin-bottom: 30px;
  display: flex;
  align-items: center;
  background-color: #f5f7fa;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.product-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 24px;
  margin-bottom: 30px;
}

.product-card {
  height: 380px;
  display: flex;
  flex-direction: column;
  border-radius: 8px;
  overflow: hidden;
  transition: all 0.3s ease;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
}

.product-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.15);
}

.product-image {
  width: 100%;
  height: 220px;
  object-fit: cover;
  transition: all 0.3s ease;
}

.product-card:hover .product-image {
  transform: scale(1.05);
}

.product-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  padding: 16px;
}

.product-info h3 {
  font-size: 16px;
  font-weight: 600;
  margin-bottom: 8px;
  color: #303133;
  line-height: 1.4;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}

.product-price {
  font-size: 20px;
  font-weight: 700;
  color: #ff4d4f;
  margin-bottom: 12px;
}

.product-description {
  font-size: 14px;
  color: #606266;
  margin-bottom: 16px;
  flex: 1;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  line-height: 1.5;
}

.product-card .el-button {
  margin-top: auto;
  width: 100%;
  transition: all 0.3s ease;
}

.product-card .el-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.pagination {
  display: flex;
  justify-content: center;
  margin-top: 30px;
}

.pagination .el-pagination {
  display: flex;
  align-items: center;
}

.pagination .el-pagination__sizes {
  margin-right: 16px;
}

.pagination .el-pagination__total {
  margin-right: 16px;
  color: #606266;
}

.pagination .el-pager li {
  margin: 0 4px;
  border-radius: 4px;
  transition: all 0.3s ease;
}

.pagination .el-pager li:hover {
  color: #409EFF;
  border-color: #409EFF;
}

.pagination .el-pager li.active {
  background-color: #409EFF;
  border-color: #409EFF;
  color: white;
}

.pagination .el-pagination__prev, .pagination .el-pagination__next {
  border-radius: 4px;
  transition: all 0.3s ease;
}

.pagination .el-pagination__prev:hover, .pagination .el-pagination__next:hover {
  color: #409EFF;
  border-color: #409EFF;
}
</style>