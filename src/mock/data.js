// 模拟数据
const mockData = {
  users: [
    { id: 1, username: 'admin', password: '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', email: 'admin@example.com', phone: '13800138000', avatar: '', status: 1, createdAt: new Date(), updatedAt: new Date() }
  ],
  categories: [
    { id: 1, name: '电子产品', parentId: null, createdAt: new Date(), updatedAt: new Date() },
    { id: 2, name: '服装鞋包', parentId: null, createdAt: new Date(), updatedAt: new Date() },
    { id: 3, name: '书籍资料', parentId: null, createdAt: new Date(), updatedAt: new Date() },
    { id: 4, name: '生活用品', parentId: null, createdAt: new Date(), updatedAt: new Date() },
    { id: 5, name: '其他', parentId: null, createdAt: new Date(), updatedAt: new Date() }
  ],
  products: [
    { id: 1, name: 'iPhone 12', description: '九成新，无划痕', price: 4999, images: 'https://via.placeholder.com/200', categoryId: 1, sellerId: 1, status: 1, createdAt: new Date(), updatedAt: new Date() },
    { id: 2, name: 'MacBook Pro', description: '2020款，16GB内存', price: 8999, images: 'https://via.placeholder.com/200', categoryId: 1, sellerId: 1, status: 1, createdAt: new Date(), updatedAt: new Date() },
    { id: 3, name: '二手教材', description: '大学英语教材，几乎全新', price: 20, images: 'https://via.placeholder.com/200', categoryId: 3, sellerId: 1, status: 1, createdAt: new Date(), updatedAt: new Date() }
  ],
  transactions: [
    { id: 1, productId: 1, buyerId: 1, sellerId: 1, price: 4999, status: 1, createdAt: new Date(), updatedAt: new Date() }
  ],
  messages: [
    { id: 1, senderId: 1, receiverId: 1, content: '你好，这个商品还在吗？', type: 3, status: 0, createdAt: new Date() }
  ],
  reviews: [
    { id: 1, productId: 1, reviewerId: 1, reviewedUserId: 1, rating: 5, content: '商品很好，卖家很贴心', createdAt: new Date() }
  ]
};

module.exports = mockData;
