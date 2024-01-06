<template>
  <div>
    <el-container>
      <el-header style="height: 100px;">
        <el-container style="display:flex;justify-content:space-between;">
          <el-avatar icon="el-icon-user-solid"></el-avatar>
          <p style="width: 50%;margin-right: 20px;">{{ username }}</p>
        </el-container>
      </el-header>
      <el-main>
        <el-button type="primary" @click="editpass">修改密码</el-button>
        <el-button type="primary" @click="newcon">发布帖子</el-button>
        <el-button type="primary" @click="newvote" v-show="iste">发布投票</el-button>
        <van-dialog v-model="show" title="修改密码" show-cancel-button @confirm="confirm()">

          <van-field v-model="value1" label="输入旧密码" placeholder="请输入" type="password" />
          <van-field v-model="value2" label="输入新密码" placeholder="请输入" type="password" />
          <van-field v-model="value3" label="再次输入新密码" placeholder="请输入" type="password" />
        </van-dialog>
      </el-main>
    </el-container>
  </div>
</template>
<script>
// import Vue from 'vue'
// 2.暴露组件 export
export default {
  name: 'newart',
  data () {
    return {
      username: '',
      iste: '',
      show: false,
      value1: '',
      value2: '',
      value3: ''
    }
  },
  beforeMount () {
    var username = localStorage.getItem('user')

    this.username = username
  },
  mounted () {
    let that = this
    var can = localStorage.getItem('can')

    if (can === '1') {
      that.iste = false
    } else {
      if (can === '2') {
        that.iste = true
      }
    }
  },
  methods: {
    editpass () {
      this.show = true
    },
    newcon () {
      this.$router.push({ name: 'newart' }).then(function () {
        // console.log(111111)
      })
    },
    newvote () {
      this.$router.push({ name: 'newvote' }).then(function () {
        // console.log(111111)
      })
    },
    confirm () {
      if (this.value1 === this.value2) {
        this.$message('旧密码与新密码过于相似,修改失败')
      } else {
        if (this.value3 !== this.value2) {
          this.$message('新密码不一致,修改失败')
        } else {
          var username = localStorage.getItem('user')
          console.log(username)
          var formData = new FormData()
          formData.append('username', username)
          formData.append('password', this.value1)
          formData.append('newpassword', this.value3)
          this.$axios.post('/mmxg', formData)
            .then(function (response) {
              console.log(response)
            })
        }
      }
    }
  }
}
</script>
