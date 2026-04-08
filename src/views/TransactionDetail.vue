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
            <el-descriptions-item label="商品名称">{{ product?.name || '未知' }}</el-descriptions-item>
            <el-descriptions-item label="交易价格">￥{{ transaction.price }}</el-descriptions-item>
            <el-descriptions-item label="创建时间">{{ transaction.createdAt }}</el-descriptions-item>
            <el-descriptions-item label="更新时间">{{ transaction.updatedAt }}</el-descriptions-item>
          </el-descriptions>
        </div>
        <div class="user-info">
          <h3>用户信息</h3>
          <el-descriptions :column="2">
            <el-descriptions-item label="买家">{{ buyer?.username || '未知' }}</el-descriptions-item>
            <el-descriptions-item label="卖家">{{ seller?.username || '未知' }}</el-descriptions-item>
            <el-descriptions-item label="买家邮箱">{{ buyer?.email || '未知' }}</el-descriptions-item>
            <el-descriptions-item label="卖家邮箱">{{ seller?.email || '未知' }}</el-descriptions-item>
            <el-descriptions-item label="买家电话">{{ buyer?.phone || '未知' }}</el-descriptions-item>
            <el-descriptions-item label="卖家电话">{{ seller?.phone || '未知' }}</el-descriptions-item>
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
export default {
  data() {
    return {
      transaction: null,
      product: null,
      buyer: null,
      seller: null,
      user: null
    }
  },
  mounted() {
    this.user = JSON.parse(localStorage.getItem('user'));
    this.loadTransactionDetail();
  },
  methods: {
    loadTransactionDetail() {
      const id = this.$route.params.id;
      this.$axios.get(`/api/transaction/detail/${id}`)
        .then(response => {
          if (response.data.success) {
            this.transaction = response.data.transaction;
            this.loadProductInfo(this.transaction.productId);
            this.loadBuyerInfo(this.transaction.buyerId);
            this.loadSellerInfo(this.transaction.sellerId);
          } else {
            this.$message.error(response.data.message);
          }
        })
        .catch(error => {
          this.$message.error('加载交易详情失败：' + error.message);
        });
    },
    loadProductInfo(productId) {
      // 这里需要一个获取商品信息的API，暂时模拟
      this.product = {
        name: '商品名称'
      };
    },
    loadBuyerInfo(buyerId) {
      // 这里需要一个获取用户信息的API，暂时模拟
      this.buyer = {
        username: '买家123',
        email: 'buyer@example.com',
        phone: '13900139000'
      };
    },
    loadSellerInfo(sellerId) {
      // 这里需要一个获取用户信息的API，暂时模拟
      this.seller = {
        username: '卖家123',
        email: 'seller@example.com',
        phone: '13800138000'
      };
    },
    pay() {
      this.$axios.put(`/api/transaction/update-status/${this.transaction.id}?status=4`)
        .then(response => {
          if (response.data.success) {
            this.$message.success('付款成功');
            this.loadTransactionDetail();
          } else {
            this.$message.error(response.data.message);
          }
        })
        .catch(error => {
          this.$message.error('付款失败：' + error.message);
        });
    },
    confirmReceipt() {
      this.$axios.put(`/api/transaction/update-status/${this.transaction.id}?status=8`)
        .then(response => {
          if (response.data.success) {
            this.$message.success('确认收货成功');
            this.loadTransactionDetail();
          } else {
            this.$message.error(response.data.message);
          }
        })
        .catch(error => {
          this.$message.error('确认收货失败：' + error.message);
        });
    },
    confirmTransaction() {
      this.$axios.put(`/api/transaction/update-status/${this.transaction.id}?status=2`)
        .then(response => {
          if (response.data.success) {
            this.$message.success('确认交易成功');
            this.loadTransactionDetail();
          } else {
            this.$message.error(response.data.message);
          }
        })
        .catch(error => {
          this.$message.error('确认交易失败：' + error.message);
        });
    },
    ship() {
      this.$axios.put(`/api/transaction/update-status/${this.transaction.id}?status=6`)
        .then(response => {
          if (response.data.success) {
            this.$message.success('发货成功');
            this.loadTransactionDetail();
          } else {
            this.$message.error(response.data.message);
          }
        })
        .catch(error => {
          this.$message.error('发货失败：' + error.message);
        });
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