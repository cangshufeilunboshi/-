<template>
  <div>
    <el-container>
      <el-header>
        <!-- <el-page-header @back="goBack" :content="title">
        </el-page-header> -->

        <van-nav-bar :title="title" left-text="返回" left-arrow @click-left="goBack">
          <template #right>
            <el-dropdown @command="handleCommand" v-show="isme">
              <span class="el-dropdown-link">
                选项<i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="a">编辑帖子</el-dropdown-item>
                <el-dropdown-item command="b">删除帖子</el-dropdown-item>
              </el-dropdown-menu>

            </el-dropdown>
          </template>

        </van-nav-bar>
      </el-header>
      <el-main>
        <p style="text-indent:2em;">{{ this.$route.params.obj.text }}</p>
        <el-image style="" :src="require('../img/' + this.$route.params.obj.img)"
          :preview-src-list="[require('../img/' + this.$route.params.obj.img)]" :fit="contain"></el-image>
      </el-main>
      <el-footer style="height: 40%;">
        <el-container>
          <el-button style="width: 25%;" type="primary" icon="el-icon-caret-top" @click="up"> 点赞{{
            this.$route.params.obj.up }} </el-button>
          <el-button style=" width: 47%;" type="primary" icon="el-icon-chat-dot-square" @click="comment()">评论</el-button>
          <el-button style="width: 25%;" type="primary" icon="el-icon-share" @click="share()">分享</el-button>
          <van-dialog v-model="show" title="请输入评论" show-cancel-button @confirm="confirm()">
            <van-cell-group>
              <van-field v-model="value" label="评论内容" placeholder="请输入" />
            </van-cell-group>
          </van-dialog>
        </el-container>
        <div style="height: 500px;">
          <van-list v-model="loading" :finished="finished" finished-text="没有更多了" @load="onLoad"
            style="height: 100%;width: 100%;overflow-y: auto;">
            <!-- <van-cell v-for="item in list" :key="item" :title="item" /> -->
            <van-cell value="内容" v-for="car in list" :key="car">
              <!-- 使用 title 插槽来自定义标题 -->
              <span class=" custom-title">{{ car.username }}:{{ car.text }}</span>
              <br>
            </van-cell>
          </van-list>
        </div>
      </el-footer>
    </el-container>

  </div>
</template>
<script>
import { Dialog } from 'vant'
import Vue from 'vue'
// 2.暴露组件 export
export default {
  name: 'getonline',
  data () {
    return {
      list: '',
      loading: false,
      finished: false,
      show: false,
      value: '',
      isme: false,
      title: this.$route.params.obj.title,
      img: this.$route.params.obj.img,
      text: this.$route.params.obj.text

    }
  },
  beforeMount () {
    let that = this
    var username = localStorage.getItem('user')

    if (username === this.$route.params.obj.username) {
      this.isme = true
    }
    console.log(this.isme)
    // let that = this
    const formdata = new FormData()
    formdata.append('id', this.$route.params.obj.id)

    // var username = localStorage.getItem('user')
    // formdata.append('username', username)
    console.log(formdata)
    this.$axios.post('/getcom', formdata)
      .then(function (response) {
        console.log(response)
        that.list = response.data.obj
        console.log('list' + that.list[0])
        // if (response.data.id === 1) {
        //   Vue.prototype.$message({
        //     message: '修改成功',
        //     type: 'success'
        //   })
        //   that.$router.push({ name: 'home' })
        // }
      }).catch(function (error) {
        console.log(error)
      })
  },
  methods: {
    goBack () {
      history.back()
    },
    up () {
      this.$route.params.obj.up++
      let that = this
      var formData = new FormData()
      formData.append('id', this.$route.params.obj.id)
      this.$axios.post('/up', formData)
        .then(function (response) {
          console.log(response)
          that.$message.success(response.data.text)
        }).catch(function (error) {
          console.log(error)
        })
    },
    handleCommand (command) {
      if (command === 'b') {
        Dialog.confirm({
          title: '提示',
          message: '确定要删除吗'
        })
          .then(() => {
            let that = this
            var formData = new FormData()
            formData.append('id', this.$route.params.obj.id)
            this.$axios.post('/delete', formData)
              .then(function (response) {
                console.log(response)
                that.$message.success(response.data.text)
                location.reload()
              }).catch(function (error) {
                console.log(error)
              })
            // on confirm
          })
          .catch(() => {
            // on cancel
          })
      } else {
        this.$router.push({ name: 'editcon', params: { obj: this.$route.params.obj } }).then(function () {
          // console.log(111111)
        })
          .catch(function (error) {
            console.log(error)
          })
      }
    },
    confirm () {
      // let that = this
      console.log(this.value)
      const formdata = new FormData()
      formdata.append('id', this.$route.params.obj.id)

      var username = localStorage.getItem('user')
      formdata.append('username', username)
      formdata.append('text', this.value)
      console.log(formdata)
      this.$axios.post('/insertcom', formdata)
        .then(function (response) {
          console.log(response)
          if (response.data.id === 1) {
            Vue.prototype.$message({
              message: '评论成功',

              type: 'success'
            })

            history.back()
            // that.$router.push({ name: 'home' })
            // that.$router.push({ name: 'getonline', params: { obj: { title: that.title, text: that.text, img: that.img } } }).then(function () {
            //   // console.log(111111)
            // })
            //   .catch(function (error) {
            //     console.log(error)
            //   })
          }
        }).catch(function (error) {
          console.log(error)
        })
    },
    comment () {
      this.show = true
    },
    share () {
      var test = window.location.href
      // 创建input元素
      const el = document.createElement('input')
      // 给input元素赋值需要复制的文本
      el.setAttribute('value', test)
      // 将input元素插入页面
      document.body.appendChild(el)
      // 选中input元素的文本
      el.select()
      // 复制内容到剪贴板
      document.execCommand('copy')
      // 删除input元素
      document.body.removeChild(el)
      Vue.prototype.$message({
        message: '当前网址复制成功',
        type: 'success'
      })
      // alert('当前网址复制成功')
    },
    onLoad () {
      // 异步更新数据
      // setTimeout 仅做示例，真实场景中一般为 ajax 请求
      // setTimeout(() => {
      //   for (let i = 0; i < 10; i++) {
      //     this.list.push(this.list.length + 1)
      //   }

      //   // 加载状态结束
      this.loading = false

      //   // 数据全部加载完成
      //   if (this.list.length >= 2) {
      this.finished = true
      //   }
      // }, 1000)
    }
  }
}
</script>
