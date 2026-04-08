<template>
  <div class="my-products-container">
    <h2>我的商品</h2>
    <el-button type="primary" @click="goToPublish" style="margin-bottom: 20px;">发布新商品</el-button>
    <el-table :data="products" style="width: 100%">
      <el-table-column prop="name" label="商品名称" width="200"></el-table-column>
      <el-table-column prop="price" label="价格" width="100"></el-table-column>
      <el-table-column prop="status" label="状态" width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === 1" type="success">在售</el-tag>
          <el-tag v-else-if="scope.row.status === 2" type="warning">已售出</el-tag>
          <el-tag v-else type="danger">已下架</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createdAt" label="发布时间" width="180"></el-table-column>
      <el-table-column label="操作" width="150">
        <template slot-scope="scope">
          <el-button size="small" @click="editProduct(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="deleteProduct(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      products: []
    }
  },
  mounted() {
    this.loadMyProducts();
  },
  methods: {
    loadMyProducts() {
      const user = JSON.parse(localStorage.getItem('user'));
      this.$axios.get(`/api/product/user/${user.id}`)
        .then(response => {
          if (response.data.success) {
            this.products = response.data.products;
          }
        })
        .catch(error => {
          this.$message.error('加载商品失败：' + error.message);
        });
    },
    goToPublish() {
      this.$router.push('/product-publish');
    },
    editProduct(product) {
      // 这里可以跳转到编辑页面，暂时只是提示
      this.$message.info('编辑功能待实现');
    },
    deleteProduct(id) {
      this.$confirm('确定要删除该商品吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.delete(`/api/product/delete/${id}`)
          .then(response => {
            if (response.data.success) {
              this.$message.success('删除成功');
              this.loadMyProducts();
            } else {
              this.$message.error(response.data.message);
            }
          })
          .catch(error => {
            this.$message.error('删除失败：' + error.message);
          });
      }).catch(() => {
        // 取消删除
      });
    }
  }
}
</script>

<style scoped>
.my-products-container {
  width: 800px;
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