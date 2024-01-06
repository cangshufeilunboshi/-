<!-- <template>
  <div>
    <el-header>
      <el-page-header @back="goBack" content="编辑页面">
      </el-page-header>
    </el-header>
    <van-form @submit="onSubmit">
      <van-field v-model="title" name="标题" label="输入标题" placeholder="标题" :rules="[{ required: true, message: '标题' }]" />
      <van-field v-model="text" name="正文" label="正文" placeholder="正文" :rules="[{ required: true, message: '正文' }]" />
      <el-upload class="upload-demo" action="https://jsonplaceholder.typicode.com/posts/" :on-preview="handlePreview"
        :on-remove="handleRemove" :file-list="fileList" list-type="picture">
        <el-button size="small" type="primary">点击上传修改图片</el-button>
        <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
      </el-upload>
      <div style="margin: 16px;">
        <van-button round block type="info" native-type="submit">提交</van-button>
      </div>
    </van-form>
  </div>
</template>
<script>
// import Vue from 'vue'
// 2.暴露组件 export
export default {
  name: 'newart',
  data () {
    return {
      fileList: [{ name: 'food.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100' }],
      title: this.$route.params.obj.title,
      text: this.$route.params.obj.text
    }
  },
  methods: {
    goBack () {
      this.$router.push({ name: 'home' })
    }
  }
}
</script> -->
<template>
  <div>
    <el-header>
      <el-page-header @back="goBack" :content="content">
      </el-page-header>
    </el-header>
    <van-form @submit="onSubmit">
      <van-field v-model="title" name="标题" label="输入标题" placeholder="标题" :rules="[{ required: true, message: '标题' }]"
        maxlength="10" />
      <van-field v-model="text" name="正文" label="正文" placeholder="正文" :rules="[{ required: true, message: '正文' }]" />

      <el-upload name="文件" class="avatar-uploader" action="#" :show-file-list="true" :limit="1" list-type="picture"
        :http-request="uploadImg" :file-list="fileList">
        <el-button size="small" type="primary">点击上传</el-button>
        <!-- <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div> -->
      </el-upload>

      <div style="margin: 16px;">
        <van-button round block type="info" native-type="submit">提交</van-button>
      </div>
    </van-form>
  </div>
</template>
<script>
import Vue from 'vue'
// 2.暴露组件 export
export default {
  name: 'editcon',
  data () {
    return {
      text: this.$route.params.obj.text,
      title: this.$route.params.obj.title,
      img: this.$route.params.obj.img,
      content: '编辑帖子',
      fileList: [{ url: require('../img/' + this.$route.params.obj.img) }]
    }
  },
  methods: {

    goBack () {
      history.back()
    },
    uploadImg (f) {
      let that = this
      // console.log(f.file)
      const file = f.file
      const form = new FormData()
      form.append('upfile', file)
      this.$axios.post('/upload', form)
        .then(function (response) {
          // console.log(response)
          console.log('图片链接是' + response.data)
          that.img = response.data
          that.$route.params.obj.img = response.data
          that.$route.params.obj.title = that.title
          that.$route.params.obj.text = that.text
          // console.log(that.img)
        })
        .catch(function (error) {
          console.log(error)
        })
    },
    onSubmit (values) {
      console.log(values)

      let that = this
      const formdata = new FormData()
      formdata.append('id', this.$route.params.obj.id)
      formdata.append('title', this.title)
      formdata.append('text', this.text)
      formdata.append('img', this.img)
      // var username = localStorage.getItem('user')
      // formdata.append('username', username)
      console.log(formdata)
      this.$axios.post('/upart', formdata)
        .then(function (response) {
          console.log(response)
          if (response.data.id === 1) {
            Vue.prototype.$message({
              message: '修改成功',
              type: 'success'
            })
            that.$router.push({ name: 'home' })
          }
        }).catch(function (error) {
          console.log(error)
        })
    }
  }
}
</script>
