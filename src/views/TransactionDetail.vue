<template>
  <div class="transaction-detail-container">
    <h2>交易详情</h2>
    <el-card v-if="transaction" class="transaction-card">
      <div class="transaction-content">
        <div class="transaction-info">
          <h3>交易信息</h3>
          <el-descriptions :column="2">
            <el-descriptions-item label="交易编号">{{ transaction.id }}</el-descriptions-item>
            <el-descriptions-item label="交易状态">
              <el-tag v-if="transaction.status === 1" type="info">待确认</el-tag>
              <el-tag v-else-if="transaction.status === 2" type="info">已确认</el-tag>
              <el-tag v-else-if="transaction.status === 3" type="warning">待付款</el-tag>
              <el-tag v-else-if="transaction.status === 4" type="success">已付款</el-tag>
              <el-tag v-else-if="transaction.status === 5" type="info">待发货</el-tag>
              <el-tag v-else-if="transaction.status === 6" type="info">已发货</el-tag>
              <el-tag v-else-if="transaction.status === 7" type="warning">待收货</el-tag>
              <el-tag v-else-if="transaction.status === 8" type="success">已完成</el-tag>
              <el-tag v-else type="danger">已取消</el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="商品名称">{{ product && product.name || '未知' }}</el-descriptions-item>
            <el-descriptions-item label="交易价格">￥{{ transaction.price }}</el-descriptions-item>
            <el-descriptions-item label="创建时间">{{ transaction.createdAt }}</el-descriptions-item>
            <el-descriptions-item label="更新时间">{{ transaction.updatedAt }}</el-descriptions-item>
          </el-descriptions>
        </div>
        <div class="user-info">
          <h3>用户信息</h3>
          <el-descriptions :column="2">
            <el-descriptions-item label="买家">{{ buyer && buyer.username || '未知' }}</el-descriptions-item>
            <el-descriptions-item label="卖家">{{ seller && seller.username || '未知' }}</el-descriptions-item>
            <el-descriptions-item label="买家邮箱">{{ buyer && buyer.email || '未知' }}</el-descriptions-item>
            <el-descriptions-item label="卖家邮箱">{{ seller && seller.email || '未知' }}</el-descriptions-item>
            <el-descriptions-item label="买家电话">{{ buyer && buyer.phone || '未知' }}</el-descriptions-item>
            <el-descriptions-item label="卖家电话">{{ seller && seller.phone || '未知' }}</el-descriptions-item>
          </el-descriptions>
        </div>
        <div class="action-buttons" v-if="user">
          <el-button v-if="user.id === transaction.buyerId && transaction.status === 3" type="primary" @click="pay">付款</el-button>
          <el-button v-if="user.id === transaction.buyerId && transaction.status === 7" type="primary" @click="confirmReceipt">确认收货</el-button>
          <el-button v-if="user.id === transaction.sellerId && transaction.status === 1" type="primary" @click="confirmTransaction">确认交易</el-button>
          <el-button v-if="user.id === transaction.sellerId && transaction.status === 5" type="primary" @click="ship">发货</el-button>
        </div>
      </div>
    </el-card>
    <el-empty v-else description="交易不存在"></el-empty>
  </div>
</template>

<script>
const mockData = require('../mock/data');
export default {
  data: function() {
    return {
      transaction: null,
      product: null,
      buyer: null,
      seller: null,
      user: null
    }
  },
  mounted: function() {
    this.user = JSON.parse(localStorage.getItem('user'));
    this.loadTransactionDetail();
  },
  methods: {
    loadTransactionDetail: function() {
      var id = this.$route.params.id;
      // 使用模拟数据
      var transaction = mockData.transactions.find(function(t) {
        return t.id === parseInt(id);
      });
      if (transaction) {
        this.transaction = transaction;
        this.loadProductInfo(this.transaction.productId);
        this.loadBuyerInfo(this.transaction.buyerId);
        this.loadSellerInfo(this.transaction.sellerId);
        this.$message.success('加载交易详情成功');
      } else {
        this.$message.error('交易不存在');
      }
    },
    loadProductInfo: function(productId) {
      // 使用模拟数据获取商品信息
      var product = mockData.products.find(function(p) {
        return p.id === productId;
      });
      if (product) {
        this.product = product;
      } else {
        this.product = {
          name: '商品名称'
        };
      }
    },
    loadBuyerInfo: function(buyerId) {
      // 这里需要一个获取用户信息的API，暂时模拟
      this.buyer = {
        username: '买家123',
        email: 'buyer@example.com',
        phone: '13900139000'
      };
    },
    loadSellerInfo: function(sellerId) {
      // 这里需要一个获取用户信息的API，暂时模拟
      this.seller = {
        username: '卖家123',
        email: 'seller@example.com',
        phone: '13800138000'
      };
    },
    pay: function() {
      // 使用模拟数据更新交易状态
      var transaction = mockData.transactions.find(function(t) {
        return t.id === this.transaction.id;
      }.bind(this));
      if (transaction) {
        transaction.status = 4;
        this.$message.success('付款成功');
        this.loadTransactionDetail();
      } else {
        this.$message.error('交易不存在');
      }
    },
    confirmReceipt: function() {
      // 使用模拟数据更新交易状态
      var transaction = mockData.transactions.find(function(t) {
        return t.id === this.transaction.id;
      }.bind(this));
      if (transaction) {
        transaction.status = 8;
        this.$message.success('确认收货成功');
        this.loadTransactionDetail();
      } else {
        this.$message.error('交易不存在');
      }
    },
    confirmTransaction: function() {
      // 使用模拟数据更新交易状态
      var transaction = mockData.transactions.find(function(t) {
        return t.id === this.transaction.id;
      }.bind(this));
      if (transaction) {
        transaction.status = 2;
        this.$message.success('确认交易成功');
        this.loadTransactionDetail();
      } else {
        this.$message.error('交易不存在');
      }
    },
    ship: function() {
      // 使用模拟数据更新交易状态
      var transaction = mockData.transactions.find(function(t) {
        return t.id === this.transaction.id;
      }.bind(this));
      if (transaction) {
        transaction.status = 6;
        this.$message.success('发货成功');
        this.loadTransactionDetail();
      } else {
        this.$message.error('交易不存在');
      }
    }
  }
}
</script>

<style scoped>
.transaction-detail-container {
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

.transaction-content {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.transaction-info,
.user-info {
  border: 1px solid #eaeaea;
  padding: 20px;
  border-radius: 5px;
}

h3 {
  margin-bottom: 10px;
  font-size: 16px;
  font-weight: bold;
}

.action-buttons {
  margin-top: 20px;
  display: flex;
  gap: 10px;
}
</style>