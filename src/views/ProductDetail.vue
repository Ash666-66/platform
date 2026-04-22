<template>
  <div class="product-detail-container">
    <h2>商品详情</h2>
    <el-card v-if="product" class="product-card">
      <div class="product-content">
        <div class="product-images">
          <el-image v-for="(image, index) in imageList" :key="index" :src="image" class="detail-image"></el-image>
        </div>
        <div class="product-info">
          <h3>{{ product.name }}</h3>
          <p class="product-price">￥{{ product.price }}</p>
          <p class="product-description">{{ product.description }}</p>
          <div class="seller-info">
            <h4>卖家信息</h4>
            <p>用户名：{{ seller && seller.username || '未知' }}</p>
            <p>联系邮箱：{{ seller && seller.email || '未知' }}</p>
            <p>联系电话：{{ seller && seller.phone || '未知' }}</p>
          </div>
          <el-button type="primary" @click="contactSeller" style="margin-top: 20px;">联系卖家</el-button>
        </div>
      </div>
    </el-card>
    <el-empty v-else description="商品不存在"></el-empty>
  </div>
</template>

<script>
const mockData = require('../mock/data');
export default {
  data: function() {
    return {
      product: null,
      seller: null,
      imageList: []
    }
  },
  mounted: function() {
    this.loadProductDetail();
  },
  methods: {
    loadProductDetail: function() {
      var id = this.$route.params.id;
      // 使用模拟数据
      var product = mockData.products.find(function(p) {
        return p.id === parseInt(id);
      });
      if (product) {
        this.product = product;
        this.imageList = this.product.images ? this.product.images.split(',') : [];
        this.loadSellerInfo(this.product.sellerId);
        this.$message.success('加载商品详情成功');
      } else {
        this.$message.error('商品不存在');
      }
    },
    loadSellerInfo: function(userId) {
      // 这里需要一个获取用户信息的API，暂时模拟
      this.seller = {
        username: '卖家123',
        email: 'seller@example.com',
        phone: '13800138000'
      };
    },
    contactSeller: function() {
      // 这里可以跳转到消息页面或者直接打开聊天窗口
      this.$message.info('联系卖家功能待实现');
    }
  }
}
</script>

<style scoped>
.product-detail-container {
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

.product-content {
  display: flex;
  gap: 40px;
  align-items: flex-start;
}

.product-images {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 16px;
  padding: 20px;
  background-color: #f5f7fa;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.detail-image {
  width: 100%;
  height: 400px;
  object-fit: cover;
  border-radius: 8px;
  transition: all 0.3s ease;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.detail-image:hover {
  transform: scale(1.02);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.15);
}

.product-info {
  flex: 1;
  padding: 20px;
  background-color: #ffffff;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.product-info h3 {
  font-size: 24px;
  font-weight: 600;
  margin-bottom: 16px;
  color: #303133;
  line-height: 1.3;
}

.product-price {
  font-size: 32px;
  font-weight: 700;
  color: #ff4d4f;
  margin-bottom: 24px;
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
}

.product-description {
  font-size: 16px;
  line-height: 1.6;
  margin-bottom: 32px;
  color: #606266;
  background-color: #f9f9f9;
  padding: 16px;
  border-radius: 4px;
  border-left: 4px solid #409EFF;
}

.seller-info {
  border-top: 1px solid #e4e7ed;
  padding-top: 24px;
  margin-bottom: 24px;
}

.seller-info h4 {
  margin-bottom: 16px;
  font-size: 18px;
  font-weight: 600;
  color: #303133;
}

.seller-info p {
  margin-bottom: 8px;
  color: #606266;
  font-size: 14px;
  line-height: 1.5;
}

.product-card .el-button {
  width: 100%;
  font-size: 16px;
  padding: 12px;
  transition: all 0.3s ease;
  border-radius: 4px;
}

.product-card .el-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

/* 响应式设计 */
@media (max-width: 1200px) {
  .product-detail-container {
    width: 95%;
    padding: 20px;
  }
  
  .product-content {
    flex-direction: column;
  }
  
  .product-images {
    width: 100%;
  }
  
  .product-info {
    width: 100%;
  }
  
  .detail-image {
    height: 300px;
  }
}
</style>