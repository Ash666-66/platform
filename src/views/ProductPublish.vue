<template>
  <div class="product-publish-container">
    <h2>发布商品</h2>
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
        <el-button type="primary" @click="publish">发布商品</el-button>
        <el-button @click="resetForm">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      form: {
        name: '',
        description: '',
        price: '',
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
          { type: 'number', min: 0.01, message: '价格必须大于 0', trigger: 'blur' }
        ],
        categoryId: [
          { required: true, message: '请选择商品分类', trigger: 'blur' }
        ]
      },
      categories: [],
      imageList: []
    }
  },
  mounted() {
    this.loadCategories();
  },
  methods: {
    loadCategories() {
      this.$axios.get('/api/category/list')
        .then(response => {
          if (response.data.success) {
            this.categories = response.data.categories;
          }
        })
        .catch(error => {
          this.$message.error('加载分类失败：' + error.message);
        });
    },
    handleImageChange(file) {
      // 这里可以处理图片上传逻辑，暂时只是预览
      this.imageList.push(URL.createObjectURL(file.raw));
      // 实际项目中需要将图片上传到服务器，然后将图片URL保存到form.images
      this.form.images = this.imageList.join(',');
    },
    publish() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          // 获取当前用户ID
          const user = JSON.parse(localStorage.getItem('user'));
          this.form.userId = user.id;
          
          this.$axios.post('/api/product/publish', this.form)
            .then(response => {
              if (response.data.success) {
                this.$message.success('发布成功');
                this.$router.push('/my-products');
              } else {
                this.$message.error(response.data.message);
              }
            })
            .catch(error => {
              this.$message.error('发布失败：' + error.message);
            });
        }
      });
    },
    resetForm() {
      this.$refs.form.resetFields();
      this.imageList = [];
    }
  }
}
</script>

<style scoped>
.product-publish-container {
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

.image-preview {
  margin-top: 10px;
  display: flex;
  flex-wrap: wrap;
}

.preview-image {
  width: 100px;
  height: 100px;
  margin-right: 10px;
  margin-bottom: 10px;
}
</style>