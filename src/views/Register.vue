<template>
  <div class="register-container">
    <h2>用户注册</h2>
    <el-form :model="form" :rules="rules" ref="form" label-width="80px">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="form.username" placeholder="请输入用户名"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="form.password" type="password" placeholder="请输入密码"></el-input>
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="form.email" placeholder="请输入邮箱"></el-input>
      </el-form-item>
      <el-form-item label="手机号" prop="phone">
        <el-input v-model="form.phone" placeholder="请输入手机号"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="register">注册</el-button>
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
      form: {
        username: '',
        password: '',
        email: '',
        phone: ''
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, message: '密码长度至少 6 个字符', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '请输入手机号', trigger: 'blur' },
          { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号格式', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    register: function() {
      this.$refs.form.validate(function(valid) {
        if (valid) {
          // 使用模拟数据进行注册
          const existingUser = mockData.users.find(function(u) {
            return u.username === this.form.username || u.email === this.form.email;
          }.bind(this));
          if (existingUser) {
            this.$message.error('用户名或邮箱已存在');
          } else {
            const newUser = {
              id: mockData.users.length + 1,
              username: this.form.username,
              password: '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', // 模拟加密密码
              email: this.form.email,
              phone: this.form.phone,
              avatar: '',
              status: 1,
              createdAt: new Date(),
              updatedAt: new Date()
            };
            mockData.users.push(newUser);
            this.$message.success('注册成功', 1500);
            this.$router.push('/login');
          }
        } else {
          this.$message.error('请检查表单信息');
          return false;
        }
      }.bind(this));
    },
    resetForm: function() {
      this.$refs.form.resetFields();
    }
  }
}
</script>

<style scoped>
.register-container {
  width: 400px;
  margin: 100px auto;
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