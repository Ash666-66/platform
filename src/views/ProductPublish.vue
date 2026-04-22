<template>
  <div class="product-publish-container">
    <h2>{{ isEdit ? '编辑商品' : '发布商品' }}</h2>
    <el-form :model="form" :rules="rules" ref="form" label-width="100px">
      <el-form-item label="商品名称" prop="name">
        <el-input v-model="form.name" placeholder="请输入商品名称"></el-input>
      </el-form-item>
      <el-form-item label="商品描述" prop="description">
        <el-input v-model="form.description" type="textarea" rows="4" placeholder="请输入商品描述"></el-input>
      </el-form-item>
      <el-form-item label="商品价格" prop="price">
        <el-input v-model="form.price" type="number" placeholder="请输入商品价格"></el-input>
      </el-form-item>
      <el-form-item label="商品分类" prop="categoryId">
        <el-select v-model="form.categoryId" placeholder="请选择商品分类">
          <el-option v-for="category in categories" :key="category.id" :label="category.name" :value="category.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="商品图片" prop="images">
        <el-upload
          class="upload-demo"
          action="#"
          :on-change="handleImageChange"
          :auto-upload="false"
          :multiple="true"
        >
          <el-button type="primary">选择图片</el-button>
          <div slot="tip" class="el-upload__tip">请选择商品图片</div>
        </el-upload>
        <div class="image-preview" v-if="imageList.length > 0">
          <el-image v-for="(image, index) in imageList" :key="index" :src="image" class="preview-image"></el-image>
        </div>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="publish">{{ isEdit ? '保存修改' : '发布商品' }}</el-button>
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
      isEdit: false,
      form: {
        id: null,
        name: '',
        description: '',
        price: 0,
        categoryId: '',
        images: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入商品名称', trigger: 'blur' },
          { min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur' }
        ],
        description: [
          { required: true, message: '请输入商品描述', trigger: 'blur' },
          { min: 5, max: 500, message: '长度在 5 到 500 个字符', trigger: 'blur' }
        ],
        price: [
          { required: true, message: '请输入商品价格', trigger: 'blur' },
          { validator: function(rule, value, callback) {
              var numValue = Number(value);
              if (isNaN(numValue) || numValue <= 0) {
                callback(new Error('价格必须大于 0'));
              } else {
                callback();
              }
            }, trigger: 'blur' }
        ],
        categoryId: [
          { required: true, message: '请选择商品分类', trigger: 'blur' }
        ]
      },
      categories: [],
      imageList: []
    }
  },
  mounted: function() {
    this.loadCategories();
    var id = this.$route.query.id || this.$route.params.id;
    if (id) {
      this.isEdit = true;
      this.loadProduct(id);
    }
  },
  methods: {
    loadCategories: function() {
      // 使用模拟数据
      this.categories = mockData.categories;
      this.$message.success('加载分类成功', { duration: 500 });
    },
    handleImageChange: function(file) {
      // 这里可以处理图片上传逻辑，暂时只是预览
      this.imageList.push(URL.createObjectURL(file.raw));
      // 实际项目中需要将图片上传到服务器，然后将图片URL保存到form.images
      this.form.images = this.imageList.join(',');
    },
    loadProduct: function(id) {
      // 使用模拟数据加载商品信息
      var product = mockData.products.find(function(p) {
        return p.id === parseInt(id);
      });
      if (product) {
        this.form = {
          id: product.id,
          name: product.name,
          description: product.description,
          price: product.price,
          categoryId: product.categoryId,
          images: product.images
        };
        // 处理图片列表
        if (product.images) {
          this.imageList = product.images.split(',');
        }
      } else {
        this.$message.error('商品不存在');
        this.$router.push('/my-products');
      }
    },
    publish: function() {
      this.$refs.form.validate(function(valid) {
        if (valid) {
          // 获取当前用户ID
          var user = JSON.parse(localStorage.getItem('user'));
          
          if (this.isEdit) {
            // 使用模拟数据更新商品
            var index = mockData.products.findIndex(function(p) {
              return p.id === this.form.id;
            }.bind(this));
            if (index !== -1) {
              var product = mockData.products[index];
              product.name = this.form.name;
              product.description = this.form.description;
              product.price = Number(this.form.price);
              product.images = this.form.images;
              product.categoryId = Number(this.form.categoryId);
              product.updatedAt = new Date();
              this.$message.success('修改成功');
              this.$router.push('/my-products');
            } else {
              this.$message.error('商品不存在');
            }
          } else {
            // 使用模拟数据发布商品
            var newProduct = {
              id: mockData.products.length + 1,
              name: this.form.name,
              description: this.form.description,
              price: Number(this.form.price),
              images: this.form.images,
              categoryId: Number(this.form.categoryId),
              sellerId: user.id,
              status: 1,
              createdAt: new Date(),
              updatedAt: new Date()
            };
            
            mockData.products.push(newProduct);
            this.$message.success('发布成功');
            this.$router.push('/my-products');
          }
        }
      }.bind(this));
    },
    resetForm: function() {
      this.$refs.form.resetFields();
      this.imageList = [];
    }
  }
}
</script>

<style scoped>
.product-publish-container {
  width: 800px;
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

.el-form {
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.el-form-item {
  margin-bottom: 20px;
}

.el-form-item__label {
  font-size: 15px;
  font-weight: 500;
  color: #303133;
}

.el-input,
.el-select,
.el-textarea {
  width: 100%;
  border-radius: 4px;
  transition: all 0.3s ease;
}

.el-input:focus,
.el-select:focus,
.el-textarea:focus {
  box-shadow: 0 0 0 2px rgba(64, 158, 255, 0.2);
  border-color: #409EFF;
}

.image-preview {
  margin-top: 16px;
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
  padding: 16px;
  background-color: #ffffff;
  border-radius: 4px;
  border: 1px solid #e4e7ed;
}

.preview-image {
  width: 120px;
  height: 120px;
  border-radius: 4px;
  object-fit: cover;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.preview-image:hover {
  transform: scale(1.05);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.el-upload {
  margin-bottom: 16px;
}

.el-upload__tip {
  font-size: 14px;
  color: #909399;
  margin-top: 8px;
}

.el-form-item:last-child {
  display: flex;
  justify-content: center;
  gap: 16px;
  margin-top: 30px;
}

.el-button {
  padding: 10px 24px;
  font-size: 15px;
  border-radius: 4px;
  transition: all 0.3s ease;
}

.el-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.el-button--primary {
  background-color: #409EFF;
  border-color: #409EFF;
}

.el-button--primary:hover {
  background-color: #66b1ff;
  border-color: #66b1ff;
}

/* 响应式设计 */
@media (max-width: 800px) {
  .product-publish-container {
    width: 95%;
    padding: 20px;
  }
  
  .preview-image {
    width: 100px;
    height: 100px;
  }
}
</style>