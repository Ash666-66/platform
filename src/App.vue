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
  data() {
    return {
      activeIndex: '/',
      user: null
    }
  },
  mounted() {
    this.user = JSON.parse(localStorage.getItem('user'));
    this.activeIndex = this.$route.path;
  },
  watch: {
    $route: function(to) {
      this.activeIndex = to.path;
    }
  },
  methods: {
    handleSelect(key) {
      this.$router.push(key);
    },
    logout() {
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
  padding: 0 20px;
}

.logo {
  font-size: 20px;
  font-weight: bold;
}

.el-menu-demo {
  background-color: transparent;
  border-bottom: none;
}

.el-menu-item {
  color: white;
}

.el-menu-item:hover {
  background-color: rgba(255, 255, 255, 0.2);
}

.el-menu-item.is-active {
  color: white;
  background-color: rgba(255, 255, 255, 0.3);
}

.el-main {
  padding: 20px;
}
</style>