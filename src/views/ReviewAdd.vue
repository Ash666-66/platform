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
export default {
  data() {
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
  mounted() {
    // 从路由参数中获取交易信息
    const { productId, productName, sellerId, sellerName } = this.$route.query;
    this.reviewForm.productId = productId;
    this.reviewForm.productName = productName;
    this.reviewForm.sellerId = sellerId;
    this.reviewForm.sellerName = sellerName;
  },
  methods: {
    submitForm() {
      this.$refs.reviewForm.validate((valid) => {
        if (valid) {
          const user = JSON.parse(localStorage.getItem('user'));
          const review = {
            productId: this.reviewForm.productId,
            reviewerId: user.id,
            reviewedId: this.reviewForm.sellerId,
            content: this.reviewForm.content,
            rating: this.reviewForm.rating
          };
          this.$axios.post('/api/review/add', review)
            .then(response => {
              if (response.data.success) {
                this.$message.success('评价成功');
                this.$router.push('/buyer-transactions');
              } else {
                this.$message.error(response.data.message);
              }
            })
            .catch(error => {
              this.$message.error('评价失败：' + error.message);
            });
        } else {
          return false;
        }
      });
    },
    resetForm() {
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