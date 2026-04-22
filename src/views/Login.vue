<template>
  <div class="login-container">
    <h2>用户登录</h2>
    <el-form :model="form" :rules="rules" ref="form" label-width="80px">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="form.username" placeholder="请输入用户名"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="form.password" type="password" placeholder="请输入密码"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="login">登录</el-button>
        <el-button @click="resetForm">重置</el-button>
      </el-form-item>
      <el-form-item>
        <span>还没有账号？</span>
        <el-link type="primary" @click="goToRegister">立即注册</el-link>
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
        password: ''
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    login: function() {
      this.$refs.form.validate(function(valid) {
        if (valid) {
          // 使用模拟数据进行登录验证
          const user = mockData.users.find(function(u) {
            return u.username === this.form.username && this.form.password === '123456';
          }.bind(this));
          if (user) {
            this.$message.success('登录成功');
            localStorage.setItem('user', JSON.stringify(user));
            this.$router.push('/');
          } else {
            this.$message.error('用户名或密码错误');
          }
        } else {
          this.$message.error('请检查表单信息');
          return false;
        }
      }.bind(this));
    },
    resetForm: function() {
      this.$refs.form.resetFields();
    },
    goToRegister: function() {
      this.$router.push('/register');
    }
  }
}
</script>

<style scoped>
.login-container {
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