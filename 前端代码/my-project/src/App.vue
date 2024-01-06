<template>
  <div id="app">
    <el-tabs v-model="activeName" @tab-click="handleClick" stretch="true">
      <el-tab-pane label="首页" name="first">
        <!-- <p>
          <router-link to="home"></router-link>
          <router-link to="newonline"></router-link>
        </p> -->
        <!-- <home v-show="isonline" v-bind:message="msg"></home> -->
      </el-tab-pane>

      <el-tab-pane label="学校通知" name="second">
        <!-- <news></news> -->
        <!-- <router-view name="second"></router-view> -->
      </el-tab-pane>
      <el-tab-pane label="投票" name="third">
        <!-- <news></news> -->
        <!-- <router-view name="second"></router-view> -->
      </el-tab-pane>
      <el-tab-pane label="我的" name="fourth">
        <!-- <p>
          <router-link to="mycon"></router-link>

          <router-link to="newart"></router-link>
        </p> -->
        <!-- <mycon></mycon> -->
        <!-- <router-view name="third"></router-view> -->
      </el-tab-pane>

    </el-tabs>

    <router-view v-show="isonline" v-bind:message="msg" style="height: 1000px;"></router-view>

    <!--
    <router-view v-show="isonline" v-bind:message="msg"></router-view> -->
    <!-- <router-link to="/getonline">首页</router-link> -->

    <!-- <el-dialog title="登录" :visible.sync="dialogFormVisible">
          <getonline></getonline>
        </el-dialog> -->

    <van-dialog v-model="dialogFormVisible" title="登录" :show-confirm-button="false" id="p1">
      <!-- <p>{{ dialogFormVisible }}</p> -->
      <van-form @submit="onSubmit">
        <van-field v-model="username" name="用户名" label="用户名(学号)" placeholder="用户名"
          :rules="[{ required: true, message: '请填写用户名' }]" style="margin-top: 10px;" />
        <van-field v-model="password" type="password" name="密码" label="密码" placeholder="密码"
          :rules="[{ required: true, message: '请填写密码' }]" style="margin-top: 10px;" />
        <div style="margin: 16px;">
          <van-button round block type="info" native-type="submit">提交</van-button>

        </div>
      </van-form>
      <van-button round block @click="zhuce()">注册</van-button>
    </van-dialog>
    <van-dialog v-model="zhuceshow" title="注册" :show-confirm-button="false" id="p1">
      <!-- <p>{{ dialogFormVisible }}</p> -->
      <van-form @submit="zconSubmit">
        <van-checkbox-group v-model="result" direction="horizontal" :max="1" style="margin-left: 30%;">
          <van-checkbox name="1" style="margin-top: 10px;">学生</van-checkbox>
          <van-checkbox name="2" style="margin-top: 10px;">老师</van-checkbox>
        </van-checkbox-group>
        <van-field v-model="zcusername" name="用户名" label="用户名(学号)" placeholder="用户名"
          :rules="[{ required: true, message: '请填写用户名' }]" style="margin-top: 10px;" />
        <van-field v-model="zcpassword" type="password" name="密码" label="密码" placeholder="密码"
          :rules="[{ required: true, message: '请填写密码' }]" style="margin-top: 10px;" />
        <van-field v-model="qrpassword" type="password" name="密码" label="确认密码" placeholder="密码"
          :rules="[{ required: true, message: '请确认密码' }]" style="margin-top: 10px;" />
        <div style="margin: 16px;">
          <van-button round block type="info" native-type="submit">提交</van-button>
        </div>
      </van-form>
      <van-button round block @click="turntodl()">登录</van-button>
    </van-dialog>
  </div>
</template>

<script src="https://unpkg.com/vue-router@4"></script>
<script>
// eslint-disable-next-line no-unused-vars
import home from './components/home.vue'
import getonline from './components/getonline.vue'
import newart from './components/newart.vue'
import editcon from './components/editcon.vue'
import Vue from 'vue'
import mycon from './components/mycon.vue'

import * as echarts from 'echarts'
import newonline from './components/newonline.vue'
import router from './router/index.js'

import news from './components/news.vue'
export default {
  // provide: {
  //   for: '111'
  // },
  data () {
    return {
      msg: '',
      activeName: 'first',
      isonline: false,
      dialogFormVisible: false,
      zhuceshow:false,
      username: '',
      password: '',
      zcusername: '',
      zcpassword: '',
      qrpassword: '',
      result:[]
    }
  },
  beforeMount: function () {
    if (this.isonline === false) {
      this.dialogFormVisible = true
      console.log(this.dialogFormVisible)
    }
  },

  methods: {
    zhuce(){
      this.zhuceshow=true
      this.dialogFormVisible=false
    },
    turntodl(){
      this.zhuceshow=false
      this.dialogFormVisible=true
    },
    zconSubmit(){
      let that =this
      //console.log(this.zcusername+this.zcpassword+this.qrpassword+this.result)
      if (this.zcpassword!=this.qrpassword) {
        this.$message.warning("密码与确认密码不一致")
      }
      else{
        var formData = new FormData()
        console.log(this.result)
      formData.append('username', this.zcusername)
      formData.append('password', this.zcpassword)
      formData.append('can', this.result[0])
      this.$axios.post('/zhuce', formData)
        .then(function (response) {
          console.log(response)
          if (response.data.id==1) {
            alert(response.data.text)

          }
          else{
      that.zhuceshow=false
      that.dialogFormVisible=true
          }
        })
      }

    },
    handleClick(values){
      console.log(values.index)
      if (values.index=="0") {
        this.$router.push({ name: 'home' })
      }
if (values.index=="1") {
  this.$router.push({ name: 'news' })
}
if (values.index=="3") {
  this.$router.push({ name: 'mycon' })
}
if (values.index=="2") {
  this.$router.push({ name: 'votes' })
}
    },
    onSubmit (values) {
      // alert(this.username)
      // this.$axios.post('/hello').then((res) => {
      //   console.log(res)
      // })
      let that = this
      var formData = new FormData()
      formData.append('username', this.username)
      formData.append('password', this.password)
      this.$axios.post('/hello', formData)
        .then(function (response) {
          console.log(response)
          if (response.data.id === 1) {
            // console.log(that)
            // App.methods.close()
            that.dialogFormVisible = false
            that.isonline = true
            that.msg = response.data.text
            localStorage.setItem('user',that.username)
            localStorage.setItem('can',response.data.can)
            that.$router.push({ name: 'home' })
            // console.log(response.data.id)
          } else {
            Vue.prototype.$message({
              message: '密码错误',
              type: 'error'
            })
          }
        })
        .catch(function (error) {
          console.log(error)
        })
    }
  },
  components: {
    'getonline': getonline,
    'home': home,
    'newart':newart,
    'editcon':editcon,
    'mycon':mycon,
    'news':news,
    'newonline':newonline,
    router
  } // 然后,在components中写入子组件
  // 然后,在components中写入子组件

}
</script>
