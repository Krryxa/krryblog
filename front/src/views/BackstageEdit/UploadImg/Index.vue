<template>
  <FormItem label="封面图片：" class="upload-img">
    <Upload
      ref="upload"
      :on-success="handleSuccess"
      :format="['jpg','jpeg','png']"
      accept="image/*"
      :max-size="2048"
      :on-format-error="handleFormatError"
      :on-exceeded-size="handleMaxSize"
      :default-file-list="defaultList"
      type="drag"
      name="imgFile"
      action="/krryblog/krry/uploadCover"
    >
      <div class="upload-icon">
        <Icon type="ios-camera" size="20"></Icon>
      </div>
      <div class="my-upload-list" v-for="(item, index) in uploadList" :key="index">
        <template v-if="item.status === 'finished'">
          <img :src="item.url">
          <div class="my-upload-list-cover">
            <Icon type="ios-eye-outline" @click.native="handleView(item.name)"></Icon>
            <Icon type="ios-trash-outline" @click.native="handleRemove(item)"></Icon>
          </div>
        </template>
        <template v-else>
          <Progress v-if="item.showProgress" :percent="item.percentage" hide-info></Progress>
        </template>
      </div>
    </Upload>
    <Modal title="View Image" v-model="visible" :footer-hide="true" class-name="vertical-center-modal" width="580">
      <img :src="previewImg" style="width: 100%">
    </Modal>
  </FormItem>
</template>

<script>
import { deleteBlogCover } from '@/service'
import { loading } from '@/mixins/loading'
export default {
  props: {
    id: {
      type: Number
    },
    uploadImgUrl: {
      type: String
    },
    imgName: {
      type: String
    },
    defaultList: {
      type: Array
    }
  },
  mixins: [loading],
  data() {
    return {
      uploadList: [],
      visible: false
    }
  },
  computed: {
    previewImg() {
      return window.location.origin + '/krryblog/' + this.uploadImgUrl
    }
  },
  watch: {
    uploadList(newVal) {
      let Eleupload = document.getElementsByClassName('ivu-upload-input')[0]
      if (newVal.length === 0) {
        // 上传列表为空，设置文件上传为可用
        Eleupload.removeAttribute('disabled')
        this.$emit('changeImg', '', '')
      } else {
        Eleupload.setAttribute('disabled', true)
      }
    }
  },
  mounted() {
    this.uploadList = this.$refs.upload.fileList
  },
  methods: {
    handleView(name) {
      this.visible = true
    },
    handleRemove(file) {
      this.$Modal.confirm({
        title: '提示',
        content: '确定要删除博客封面图片吗？',
        loading: true,
        onOk: async () => {
          this.openLoading('Deleting~~')
          let res = await deleteBlogCover(this.id, {
            filePath: this.uploadImgUrl
          })
          this.$Modal.remove()
          if (res === 'success') {
            this.$Message.success('删除成功！')
            // 清空图片区域
            this.$emit('changeImg', '', '')
            const fileList = this.$refs.upload.fileList
            this.$refs.upload.fileList.splice(fileList.indexOf(file), 1)
          } else {
            this.$Message.error('删除失败！')
          }
          this.$Spin.hide()
        }
      })
    },
    handleSuccess(res, file) {
      if (res !== null) {
        this.$emit('changeImg', res.oldName, res.url)
        file.url = window.location.origin + '/krryblog/' + res.url
        file.name = res.oldName
        // 设置文件上传不可用
        let Eleupload = document.getElementsByClassName('ivu-upload-input')[0]
        Eleupload.setAttribute('disabled', true)
      }
    },
    handleFormatError(file) {
      this.$Notice.warning({
        title: 'The file format is incorrect',
        desc:
          'File format of ' +
          file.name +
          ' is incorrect, please select jpg or png.'
      })
    },
    handleMaxSize(file) {
      this.$Notice.warning({
        title: 'Exceeding file size limit',
        desc: 'File  ' + file.name + ' is too large, no more than 2M.'
      })
    }
  },
  components: {}
}
</script>

<style lang='scss' scoped>
.my-upload-list {
  &:hover .my-upload-list-cover {
    display: block;
  }

  display: inline-block;
  width: 224px;
  height: 184px;
  text-align: center;
  line-height: 184px;
  border-radius: 4px;
  overflow: hidden;
  background: #fff;
  position: absolute;
  top: 0;
  left: 0;

  img {
    width: 100%;
    height: 100%;
  }
}
.my-upload-list-cover {
  display: none;
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  background: rgba(0, 0, 0, 0.6);
  cursor: url(../../../assets/pic/pointer.cur), default !important;

  i {
    color: #fff;
    font-size: 28px;
    cursor: url(../../../assets/pic/cursor.cur), pointer !important;
    margin: 0 16px;
  }
}

.ivu-upload {
  margin: 0 auto;
  width: 224px;
  .upload-icon {
    cursor: url(../../../assets/pic/cursor.cur), pointer !important;
    width: 224px;
    height: 184px;
    line-height: 184px;
    i {
      font-size: 36px !important;
    }
  }
}
</style>
<style lang="scss">
.upload-img {
  .ivu-upload-drag {
    position: relative;

    &:hover {
      border-color: #f60 !important;
    }
  }
  .ivu-upload-list {
    margin-top: 0;
    .ivu-upload-list-file > span {
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
      width: 224px;
      display: block;
    }
  }
}
</style>
