import Vue from 'vue'
import Router from 'vue-router'
import home from '@/components/home'
import getonline from '@/components/getonline'
import newart from '@/components/newart'
import editcon from '@/components/editcon'
import mycon from '@/components/mycon'
import news from '@/components/news'
import newonline from '@/components/newonline'
import votes from '@/components/votes'
import newvote from '@/components/newvote'
// import App from '@/App'
Vue.use(Router)
// const originalPush = Router.prototype.push
// Router.prototype.push = function push (location) {
//   return originalPush.call(this, location).catch(err => err)
// }
export default new Router({
  routes: [
    {
      path: '/home',
      name: 'home',
      component: home
    },
    {
      path: '/votes',
      name: 'votes',
      component: votes
    },
    {
      path: '/getonline',
      name: 'getonline',
      component: getonline
    },
    {
      path: '/news',
      name: 'news',
      component: news
    },
    {
      path: '/newonline',
      name: 'newonline',
      component: newonline
    },
    {
      path: '/newart',
      name: 'newart',
      component: newart
    },
    {
      path: '/newvote',
      name: 'newvote',
      component: newvote
    },
    {
      path: '/mycon',
      name: 'mycon',
      component: mycon
    },
    {
      path: '/editcon',
      name: 'editcon',
      component: editcon
    }
  ]
})
