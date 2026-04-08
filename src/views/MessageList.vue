<template>
  <div class="message-list-container">
    <h2>消息中心</h2>
    <el-button type="primary" @click="markAllAsRead" style="margin-bottom: 20px;">全部标记已读</el-button>
    <el-table :data="messages" style="width: 100%">
      <el-table-column prop="senderName" label="发送者" width="150"></el-table-column>
      <el-table-column prop="content" label="内容" width="400">
        <template slot-scope="scope">
          <span v-if="scope.row.status === 0" class="unread-message">{{ scope.row.content }}</span>
          <span v-else>{{ scope.row.content }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="type" label="类型" width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.type === 1" type="info">系统消息</el-tag>
          <el-tag v-else-if="scope.row.type === 2" type="warning">交易消息</el-tag>
          <el-tag v-else type="success">聊天消息</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="status" label="状态" width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === 0" type="danger">未读</el-tag>
          <el-tag v-else type="success">已读</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createdAt" label="时间" width="180"></el-table-column>
      <el-table-column label="操作" width="150">
        <template slot-scope="scope">
          <el-button size="small" @click="viewDetail(scope.row.id)">查看详情</el-button>
          <el-button size="small" v-if="scope.row.status === 0" @click="markAsRead(scope.row.id)">标记已读</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      messages: []
    }
  },
  mounted() {
    this.loadMessages();
  },
  methods: {
    loadMessages() {
      const user = JSON.parse(localStorage.getItem('user'));
      this.$axios.get(`/api/message/receiver/${user.id}`)
        .then(response => {
          if (response.data.success) {
            this.messages = response.data.messages;
            // 这里需要加载发送者信息，暂时模拟
            this.messages.forEach(message => {
              message.senderName = '用户123';
            });
          }
        })
        .catch(error => {
          this.$message.error('加载消息失败：' + error.message);
        });
    },
    viewDetail(id) {
      this.$router.push(`/message-detail/${id}`);
    },
    markAsRead(id) {
      this.$axios.put(`/api/message/mark-as-read/${id}`)
        .then(response => {
          if (response.data.success) {
            this.$message.success('标记已读成功');
            this.loadMessages();
          } else {
            this.$message.error(response.data.message);
          }
        })
        .catch(error => {
          this.$message.error('标记已读失败：' + error.message);
        });
    },
    markAllAsRead() {
      const user = JSON.parse(localStorage.getItem('user'));
      this.$axios.put(`/api/message/mark-all-as-read/${user.id}`)
        .then(response => {
          if (response.data.success) {
            this.$message.success('全部标记已读成功');
            this.loadMessages();
          } else {
            this.$message.error(response.data.message);
          }
        })
        .catch(error => {
          this.$message.error('全部标记已读失败：' + error.message);
        });
    }
  }
}
</script>

<style scoped>
.message-list-container {
  width: 1000px;
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

.unread-message {
  font-weight: bold;
  color: #ff4d4f;
}
</style>