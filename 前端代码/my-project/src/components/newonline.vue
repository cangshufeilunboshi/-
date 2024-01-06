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
                <el-dropdown-item command="b">删除帖子</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </template>

        </van-nav-bar>
      </el-header>
      <el-main style="text-align: center;">
        <p>{{ title }}</p>
        <br>
        <el-contain>
          <el-aside>
            <van-checkbox-group v-model="result" :max="1" direction="horizontal" v-show="hasch"
              v-for="(item, index) in oplist" :key="item">
              <!-- <van-checkbox name="a">一般</van-checkbox>
          <van-checkbox name="b">很差</van-checkbox>
          <van-checkbox name="c">良好</van-checkbox> -->
              <van-checkbox :name="index" style="margin-top: 10px;">{{ item }}</van-checkbox>
            </van-checkbox-group>
          </el-aside>
          <el-main>
            <el-button round type="primary" @click="inseop" v-show="hasch">投票</el-button>
          </el-main>
        </el-contain>
      </el-main>

      <div id="main" style="width: 100%;height:200px;"></div>
    </el-container>

  </div>
</template>

<script>

import * as echarts from 'echarts'
import { Dialog } from 'vant'
import Vue from 'vue'
// 2.暴露组件 export
export default {
  name: 'newonline',
  data () {
    return {
      result: [],
      values: '',
      oplist: '',
      isme: false,
      hasch: true,
      title: this.$route.params.obj.title,
      list:
        [
          {

            name: '很差',
            value: 1
          },
          {
            value: 0,
            name: '一般'
          },
          {
            value: 0,
            name: '良好'
          }
        ]
    }
  },
  // beforeMount () {

  // },
  mounted () {
    let values = []
    var myChart = echarts.init(document.getElementById('main'))
    let that = this
    var username = localStorage.getItem('user')
    if (username === this.$route.params.obj.username) {
      this.isme = true
    }
    // let that = this
    // var username = localStorage.getItem('user')
    var n = this.$route.params.obj.text.split(';')
    var ns = n.length - 1
    n.splice(ns, 1)
    this.oplist = n
    console.log('list:' + this.oplist)
    // let num = n.length - 1
    // console.log('n' + num)
    var formData = new FormData()
    formData.append('username', username)
    formData.append('id', this.$route.params.obj.id)
    formData.append('num', ns)
    this.$axios.post('/getops', formData)
      .then(function (response) {
        console.log(response)
        if (response.data.id === 1) {
          that.hasch = false
        } else {
          that.hasch = true
        }
        let i = 0
        while (i < ns) {
          response.data.text.obj[i].name = that.oplist[i]
          i++
        }
        that.values = response.data.text.obj
        values = that.values
        console.log('values1:' + that.values)
        myChart.setOption({
          tooltip: {
            trigger: 'item'
          },
          series: [
            {
              type: 'pie',
              label: {
                normal: {
                  show: true,
                  formatter: '{b}{d}%'
                  // formatter: '{b}: {c}({d}%)' //自定义显示格式(b:name, c:value, d:百分比)
                }
              },
              data: values
              // [
              //   {

              //     name: '很差',
              //     value: 1
              //   },
              //   {
              //     value: 0,
              //     name: '一般'
              //   },
              //   {
              //     value: 0,
              //     name: '良好'
              //   }
              // ]
            }
          ]
        })
        // console.log('obj:' + response.data.text.obj[0].name)
      })
    // values = this.values
    console.log('list:' + that.list[0].value)
    console.log('values2:' + that.values)
    // console.log(111)
  },
  methods: {
    inseop () {
      console.log(this.values)
      let that = this
      var username = localStorage.getItem('user')
      if (this.result[0] === undefined) {
        Vue.prototype.$message({
          message: '请先选择！',
          type: 'error'
        })
      } else {
        var formData = new FormData()
        formData.append('username', username)
        formData.append('id', this.$route.params.obj.id)
        formData.append('voteop', this.result[0] + 1)
        this.$axios.post('/inseop', formData)
          .then(function (response) {
            console.log(response)
            if (response.data.id === 1) {
              Vue.prototype.$message({
                message: '投票完成',
                type: 'success'
              })
              that.$router.push({ name: 'votes' })
            }
          })
          .catch(function (error) {
            console.log(error)
          })
      }
    },
    goBack () {
      history.back()
    },
    handleCommand (command) {
      if (command === 'b') {
        Dialog.confirm({
          title: '提示',
          message: '确定要删除吗'
        })
          .then(() => {
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
    }
  }
}
</script>
