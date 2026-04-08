<template>
  <div class="buyer-transactions-container">
    <h2>我的购买</h2>
    <el-table :data="transactions" style="width: 100%">
      <el-table-column prop="productName" label="商品名称" width="200"></el-table-column>
      <el-table-column prop="price" label="价格" width="100"></el-table-column>
      <el-table-column prop="status" label="状态" width="120">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === 1" type="info">待确认</el-tag>
          <el-tag v-else-if="scope.row.status === 2" type="info">已确认</el-tag>
          <el-tag v-else-if="scope.row.status === 3" type="warning">待付款</el-tag>
          <el-tag v-else-if="scope.row.status === 4" type="success">已付款</el-tag>
          <el-tag v-else-if="scope.row.status === 5" type="info">待发货</el-tag>
          <el-tag v-else-if="scope.row.status === 6" type="info">已发货</el-tag>
          <el-tag v-else-if="scope.row.status === 7" type="warning">待收货</el-tag>
          <el-tag v-else-if="scope.row.status === 8" type="success">已完成</el-tag>
          <el-tag v-else type="danger">已取消</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createdAt" label="创建时间" width="180"></el-table-column>
      <el-table-column label="操作" width="150">
        <template slot-scope="scope">
          <el-button size="small" @click="viewDetail(scope.row.id)">查看详情</el-button>
          <el-button size="small" v-if="scope.row.status === 3" type="primary" @click="pay(scope.row.id)">付款</el-button>
          <el-button size="small" v-if="scope.row.status === 7" type="primary" @click="confirmReceipt(scope.row.id)">确认收货</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      transactions: []
    }
  },
  mounted() {
    this.loadBuyerTransactions();
  },
  methods: {
    loadBuyerTransactions() {
      const user = JSON.parse(localStorage.getItem('user'));
      this.$axios.get(`/api/transaction/buyer/${user.id}`)
        .then(response => {
          if (response.data.success) {
            this.transactions = response.data.transactions;
            // 这里需要加载商品信息，暂时模拟
            this.transactions.forEach(transaction => {
              transaction.productName = '商品名称';
            });
          }
        })
        .catch(error => {
          this.$message.error('加载交易记录失败：' + error.message);
        });
    },
    viewDetail(id) {
      this.$router.push(`/transaction-detail/${id}`);
    },
    pay(id) {
      this.$axios.put(`/api/transaction/update-status/${id}?status=4`)
        .then(response => {
          if (response.data.success) {
            this.$message.success('付款成功');
            this.loadBuyerTransactions();
          } else {
            this.$message.error(response.data.message);
          }
        })
        .catch(error => {
          this.$message.error('付款失败：' + error.message);
        });
    },
    confirmReceipt(id) {
      this.$axios.put(`/api/transaction/update-status/${id}?status=8`)
        .then(response => {
          if (response.data.success) {
            this.$message.success('确认收货成功');
            this.loadBuyerTransactions();
          } else {
            this.$message.error(response.data.message);
          }
        })
        .catch(error => {
          this.$message.error('确认收货失败：' + error.message);
        });
    }
  }
}
</script>

<style scoped>
.buyer-transactions-container {
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
</style>