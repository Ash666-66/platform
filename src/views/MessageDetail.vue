<template>
  <div class="message-detail-container">
    <h2>消息详情</h2>
    <el-card v-if="message" class="message-card">
      <div class="message-content">
        <el-descriptions :column="1">
          <el-descriptions-item label="发送者">{{ sender?.username || '未知' }}</el-descriptions-item>
          <el-descriptions-item label="内容">{{ message.content }}</el-descriptions-item>
          <el-descriptions-item label="类型">
            <el-tag v-if="message.type === 1" type="info">系统消息</el-tag>
            <el-tag v-else-if="message.type === 2" type="warning">交易消息</el-tag>
            <el-tag v-else type="success">聊天消息</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="状态">
            <el-tag v-if="message.status === 0" type="danger">未读</el-tag>
            <el-tag v-else type="success">已读</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="发送时间">{{ message.createdAt }}</el-descriptions-item>
        </el-descriptions>
        <div class="action-buttons" v-if="message.status === 0">
          <el-button type="primary" @click="markAsRead">标记已读</el-button>
        </div>
      </div>
    </el-card>
    <el-empty v-else description="消息不存在"></el-empty>
  </div>
</template>

<script>
export default {
  data() {
    return {
      message: null,
      sender: null
    }
  },
  mounted() {
    this.loadMessageDetail();
  },
  methods: {
    loadMessageDetail() {
      const id = this.$route.params.id;
      this.$axios.get(`/api/message/detail/${id}`)
        .then(response => {
          if (response.data.success) {
            this.message = response.data.message;
            this.loadSenderInfo(this.message.senderId);
          } else {
            this.$message.error(response.data.message);
          }
        })
        .catch(error => {
          this.$message.error('加载消息详情失败：' + error.message);
        });
    },
    loadSenderInfo(senderId) {
      // 这里需要一个获取用户信息的API，暂时模拟
      this.sender = {
        username: '用户123'
      };
    },
    markAsRead() {
      this.$axios.put(`/api/message/mark-as-read/${this.message.id}`)
        .then(response => {
          if (response.data.success) {
            this.$message.success('标记已读成功');
            this.loadMessageDetail();
          } else {
            this.$message.error(response.data.message);
          }
        })
        .catch(error => {
          this.$message.error('标记已读失败：' + error.message);
        });
    }
  }
}
</script>

<style scoped>
.message-detail-container {
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

.message-content {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.action-buttons {
  margin-top: 20px;
  display: flex;
  gap: 10px;
}
</style>