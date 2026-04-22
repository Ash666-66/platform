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

    <!-- 编辑商品对话框 -->
    <el-dialog title="编辑商品" :visible.sync="dialogVisible" width="600px">
      <el-form :model="form" :rules="rules" ref="form" label-width="100px">
        <el-form-item label="商品名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入商品名称"></el-input>
        </el-form-item>
        <el-form-item label="商品价格" prop="price">
          <el-input v-model="form.price" type="number" placeholder="请输入商品价格"></el-input>
        </el-form-item>
        <el-form-item label="商品状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择商品状态">
            <el-option label="在售" value="1"></el-option>
            <el-option label="已售出" value="2"></el-option>
            <el-option label="已下架" value="3"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveEdit">保存修改</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
const mockData = require('../mock/data');
export default {
  data: function() {
    return {
      products: [],
      dialogVisible: false,
      form: {
        id: '',
        name: '',
        price: '',
        status: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入商品名称', trigger: 'blur' }
        ],
        price: [
          { required: true, message: '请输入商品价格', trigger: 'blur' },
          { validator: function(rule, value, callback) {
              var numValue = Number(value);
              if (isNaN(numValue) || numValue <= 0) {
                callback(new Error('请输入有效数字'));
              } else {
                callback();
              }
            }, trigger: 'blur' }
        ],
        status: [
          { required: true, message: '请选择商品状态', trigger: 'blur' }
        ]
      }
    }
  },
  mounted: function() {
    this.loadMyProducts();
  },
  methods: {
    loadMyProducts: function() {
      var user = JSON.parse(localStorage.getItem('user'));
      if (!user) {
        this.$message.error('请先登录');
        this.$router.push('/login');
        return;
      }
      // 使用模拟数据加载用户的商品
      var userId = Number(user.id);
      this.products = mockData.products.filter(function(p) {
        return p.sellerId === userId;
      });
      this.$message.success('加载商品成功', { duration: 500 });
    },
    goToPublish: function() {
      this.$router.push('/product-publish');
    },
    editProduct: function(product) {
      this.form = {
        id: product.id,
        name: product.name,
        price: product.price,
        status: product.status
      };
      this.dialogVisible = true;
    },
    saveEdit: function() {
      this.$refs.form.validate(function(valid) {
        if (valid) {
          // 找到要更新的商品
          var index = mockData.products.findIndex(function(item) {
            return item.id === this.form.id;
          }.bind(this));
          if (index !== -1) {
            // 更新商品信息
            var product = mockData.products[index];
            product.name = this.form.name;
            product.price = Number(this.form.price);
            product.status = Number(this.form.status);
            product.updatedAt = new Date();
            this.$message.success('修改成功', { duration: 500 });
            this.dialogVisible = false;
            this.loadMyProducts();
          } else {
            this.$message.error('商品不存在');
          }
        }
      }.bind(this));
    },
    deleteProduct: function(id) {
      this.$confirm('确定要删除该商品吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        // 使用模拟数据删除商品
        var index = mockData.products.findIndex(function(p) {
          return p.id === id;
        });
        if (index !== -1) {
          mockData.products.splice(index, 1);
          this.$message.success('删除成功');
          this.loadMyProducts();
        } else {
          this.$message.error('商品不存在');
        }
      }.bind(this)).catch(function() {
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