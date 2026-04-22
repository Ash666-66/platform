<template>
  <div id="app">
    <el-container>
      <el-header height="60px" class="header">
        <div class="logo">校园二手交易平台</div>
        <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
          <el-menu-item index="/">首页</el-menu-item>
          <el-menu-item index="/product-publish">发布商品</el-menu-item>
          <el-menu-item index="/my-products">我的商品</el-menu-item>
          <el-menu-item index="/buyer-transactions">我的购买</el-menu-item>
          <el-menu-item index="/seller-transactions">我的卖出</el-menu-item>
          <el-menu-item index="/message-list">消息中心</el-menu-item>
          <el-menu-item v-if="!user" index="/login">登录</el-menu-item>
          <el-menu-item v-if="!user" index="/register">注册</el-menu-item>
          <el-menu-item v-if="user" @click="logout">退出登录</el-menu-item>
        </el-menu>
      </el-header>
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </div>
</template>

<script>
export default {
  name: 'App',
  data: function() {
    return {
      activeIndex: '/',
      user: null
    }
  },
  mounted: function() {
    this.checkLoginStatus();
    this.activeIndex = this.$route.path;
  },
  watch: {
    $route: function(to) {
      this.activeIndex = to.path;
      this.checkLoginStatus();
    }
  },
  methods: {
    checkLoginStatus: function() {
      this.user = JSON.parse(localStorage.getItem('user'));
    },
    handleSelect: function(key) {
      this.$router.push(key);
    },
    logout: function() {
      localStorage.removeItem('user');
      this.user = null;
      this.$router.push('/login');
    }
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}

.header {
  background-color: #409EFF;
  color: white;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 30px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
  position: sticky;
  top: 0;
  z-index: 100;
}

.logo {
  font-size: 22px;
  font-weight: bold;
  letter-spacing: 1px;
}

.el-menu-demo {
  background-color: transparent;
  border-bottom: none;
}

.el-menu-item {
  color: #ffffff;
  font-weight: 500;
  font-size: 15px;
  margin: 0 5px;
  transition: all 0.3s ease;
}

.el-menu-item:hover {
  color: #ffffff;
  background-color: rgba(255, 255, 255, 0.2);
  transform: translateY(-2px);
}

.el-menu-item.is-active {
  color: #ffffff;
  font-weight: 600;
  background-color: rgba(255, 255, 255, 0.3);
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.el-main {
  padding: 20px;
}
</style>