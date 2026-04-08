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
            <p>用户名：{{ seller?.username || '未知' }}</p>
            <p>联系邮箱：{{ seller?.email || '未知' }}</p>
            <p>联系电话：{{ seller?.phone || '未知' }}</p>
          </div>
          <el-button type="primary" @click="contactSeller" style="margin-top: 20px;">联系卖家</el-button>
        </div>
      </div>
    </el-card>
    <el-empty v-else description="商品不存在"></el-empty>
  </div>
</template>

<script>
export default {
  data() {
    return {
      product: null,
      seller: null,
      imageList: []
    }
  },
  mounted() {
    this.loadProductDetail();
  },
  methods: {
    loadProductDetail() {
      const id = this.$route.params.id;
      this.$axios.get(`/api/product/detail/${id}`)
        .then(response => {
          if (response.data.success) {
            this.product = response.data.product;
            this.imageList = this.product.images ? this.product.images.split(',') : [];
            this.loadSellerInfo(this.product.userId);
          } else {
            this.$message.error(response.data.message);
          }
        })
        .catch(error => {
          this.$message.error('加载商品详情失败：' + error.message);
        });
    },
    loadSellerInfo(userId) {
      // 这里需要一个获取用户信息的API，暂时模拟
      this.seller = {
        username: '卖家123',
        email: 'seller@example.com',
        phone: '13800138000'
      };
    },
    contactSeller() {
      // 这里可以跳转到消息页面或者直接打开聊天窗口
      this.$message.info('联系卖家功能待实现');
    }
  }
}
</script>

<style scoped>
.product-detail-container {
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

.product-content {
  display: flex;
  gap: 30px;
}

.product-images {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.detail-image {
  width: 100%;
  height: 300px;
  object-fit: cover;
}

.product-info {
  flex: 1;
}

.product-name {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 10px;
}

.product-price {
  font-size: 28px;
  font-weight: bold;
  color: #ff4d4f;
  margin-bottom: 20px;
}

.product-description {
  font-size: 16px;
  line-height: 1.5;
  margin-bottom: 30px;
  color: #666;
}

.seller-info {
  border-top: 1px solid #eaeaea;
  padding-top: 20px;
}

.seller-info h4 {
  margin-bottom: 10px;
}

.seller-info p {
  margin-bottom: 5px;
  color: #666;
}
</style>