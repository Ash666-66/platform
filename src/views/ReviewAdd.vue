<template>
  <div class="review-add-container">
    <h2>评价交易</h2>
    <el-form :model="reviewForm" :rules="rules" ref="reviewForm" label-width="80px">
      <el-form-item label="商品名称">
        <el-input v-model="reviewForm.productName" disabled></el-input>
      </el-form-item>
      <el-form-item label="卖家名称">
        <el-input v-model="reviewForm.sellerName" disabled></el-input>
      </el-form-item>
      <el-form-item label="评分" prop="rating">
        <el-rate v-model="reviewForm.rating" :max="5" show-score></el-rate>
      </el-form-item>
      <el-form-item label="评价内容" prop="content">
        <el-input type="textarea" v-model="reviewForm.content" rows="4"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm">提交评价</el-button>
        <el-button @click="resetForm">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
const mockData = require('../mock/data');
export default {
  data: function() {
    return {
      reviewForm: {
        productId: '',
        productName: '',
        sellerId: '',
        sellerName: '',
        rating: 5,
        content: ''
      },
      rules: {
        rating: [
          { required: true, message: '请选择评分', trigger: 'change' }
        ],
        content: [
          { required: true, message: '请输入评价内容', trigger: 'blur' }
        ]
      }
    }
  },
  mounted: function() {
    // 从路由参数中获取交易信息
    var query = this.$route.query;
    this.reviewForm.productId = query.productId;
    this.reviewForm.productName = query.productName;
    this.reviewForm.sellerId = query.sellerId;
    this.reviewForm.sellerName = query.sellerName;
  },
  methods: {
    submitForm: function() {
      this.$refs.reviewForm.validate(function(valid) {
        if (valid) {
          var user = JSON.parse(localStorage.getItem('user'));
          var review = {
            id: mockData.reviews.length + 1,
            productId: this.reviewForm.productId,
            reviewerId: user.id,
            reviewedUserId: this.reviewForm.sellerId,
            content: this.reviewForm.content,
            rating: this.reviewForm.rating,
            createdAt: new Date()
          };
          // 使用模拟数据提交评价
          mockData.reviews.push(review);
          this.$message.success('评价成功');
          this.$router.push('/buyer-transactions');
        } else {
          return false;
        }
      }.bind(this));
    },
    resetForm: function() {
      this.$refs.reviewForm.resetFields();
    }
  }
}
</script>

<style scoped>
.review-add-container {
  width: 600px;
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