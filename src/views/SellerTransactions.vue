<template>
  <div class="seller-transactions-container">
    <h2>我的卖出</h2>
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
          <el-button size="small" v-if="scope.row.status === 1" type="primary" @click="confirmTransaction(scope.row.id)">确认交易</el-button>
          <el-button size="small" v-if="scope.row.status === 5" type="primary" @click="ship(scope.row.id)">发货</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
const mockData = require('../mock/data');
export default {
  data: function() {
    return {
      transactions: []
    }
  },
  mounted: function() {
    this.loadSellerTransactions();
  },
  methods: {
    loadSellerTransactions: function() {
      var user = JSON.parse(localStorage.getItem('user'));
      // 使用模拟数据加载卖家的交易记录
      this.transactions = mockData.transactions.filter(function(t) {
        return t.sellerId === user.id;
      });
      // 加载商品信息
      this.transactions.forEach(function(transaction) {
        var product = mockData.products.find(function(p) {
          return p.id === transaction.productId;
        });
        transaction.productName = product ? product.name : '商品名称';
      });
      this.$message.success('加载交易记录成功', { duration: 500 });
    },
    viewDetail: function(id) {
      this.$router.push('/transaction-detail/' + id);
    },
    confirmTransaction: function(id) {
      // 使用模拟数据更新交易状态
      var transaction = mockData.transactions.find(function(t) {
        return t.id === id;
      });
      if (transaction) {
        transaction.status = 2;
        this.$message.success('确认交易成功');
        this.loadSellerTransactions();
      } else {
        this.$message.error('交易不存在');
      }
    },
    ship: function(id) {
      // 使用模拟数据更新交易状态
      var transaction = mockData.transactions.find(function(t) {
        return t.id === id;
      });
      if (transaction) {
        transaction.status = 6;
        this.$message.success('发货成功');
        this.loadSellerTransactions();
      } else {
        this.$message.error('交易不存在');
      }
    }
  }
}
</script>

<style scoped>
.seller-transactions-container {
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