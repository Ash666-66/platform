import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/views/Login'
import Register from '@/views/Register'
import ProductList from '@/views/ProductList'
import ProductDetail from '@/views/ProductDetail'
import ProductPublish from '@/views/ProductPublish'
import MyProducts from '@/views/MyProducts'
import BuyerTransactions from '@/views/BuyerTransactions'
import SellerTransactions from '@/views/SellerTransactions'
import TransactionDetail from '@/views/TransactionDetail'
import MessageList from '@/views/MessageList'
import MessageDetail from '@/views/MessageDetail'
import ReviewAdd from '@/views/ReviewAdd'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'ProductList',
      component: ProductList
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    },
    {
      path: '/product-detail/:id',
      name: 'ProductDetail',
      component: ProductDetail
    },
    {
      path: '/product-publish',
      name: 'ProductPublish',
      component: ProductPublish
    },
    {
      path: '/my-products',
      name: 'MyProducts',
      component: MyProducts
    },
    {
      path: '/buyer-transactions',
      name: 'BuyerTransactions',
      component: BuyerTransactions
    },
    {
      path: '/seller-transactions',
      name: 'SellerTransactions',
      component: SellerTransactions
    },
    {
      path: '/transaction-detail/:id',
      name: 'TransactionDetail',
      component: TransactionDetail
    },
    {
      path: '/message-list',
      name: 'MessageList',
      component: MessageList
    },
    {
      path: '/message-detail/:id',
      name: 'MessageDetail',
      component: MessageDetail
    },
    {
      path: '/review-add',
      name: 'ReviewAdd',
      component: ReviewAdd
    }
  ]
})