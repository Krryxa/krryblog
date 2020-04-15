<template>
  <section class="music">
    <h1>Music</h1>
    <Upload
      name="musicFile"
      action="/krryblog/krry/uploadMusic"
      :on-success="handleSuccess"
      :format="['mp3','mp4','m4a','acc']"
      accept="audio/*"
    >
      <Button type="success" class="add-button">Upload</Button>
    </Upload>
    <router-link :to="{ name: 'list' }" class="modify-buttom">Go to list</router-link>
    <Table :columns="columns" size="small" :data="musicList"></Table>
  </section>
</template>

<script>
import { loading } from '@/mixins/loading'
import { deleteMusic } from '@/service'
export default {
  props: {
    musicList: {
      type: Array
    }
  },
  mixins: [loading],
  data() {
    return {
      columns: [
        {
          title: 'ID',
          width: 100,
          key: 'id'
        },
        {
          title: 'Title',
          width: 450,
          key: 'title'
        },
        {
          title: 'Size',
          width: 150,
          key: 'size'
        },
        {
          title: 'UploadTime',
          width: 140,
          key: 'createTime'
        },
        {
          title: 'Operation',
          key: 'action',
          width: 238,
          align: 'center',
          render: (h, params) => {
            return h('div', {}, [
              h(
                'a',
                {
                  attrs: {
                    href: 'javascript:void(0);'
                  },
                  class: 'a-button',
                  on: {
                    click: () => {
                      this.beforeRemove(params.row['id'], params.row['title'])
                    }
                  }
                },
                'Delete'
              )
            ])
          }
        }
      ]
    }
  },
  methods: {
    handleSuccess(res, file) {
      if (res !== null) {
        this.$Message.success('Upload successful!')
        this.musicList.pop() // 删除最后一个元素
        this.musicList.unshift({
          createTime: res.createTime,
          id: res.id,
          size: res.size,
          title: res.title
        })
        this.$emit('addCount')
      }
    },
    beforeRemove(id, title) {
      console.log('删除id：' + id, title)
      this.$Modal.confirm({
        title: 'notification~',
        content: `<p>Do you want to delete the music “${title}” ？</p>`,
        okText: 'Confirm',
        cancelText: 'Cancel',
        onOk: () => {
          this.remove(id, 'music/' + title)
        }
      })
    },
    async remove(id, url) {
      this.openLoading('Deleting~~')
      let msg = await deleteMusic(id, {
        filePath: url
      })
      if (msg === 'success') {
        // refresh local data
        this.$emit('deleteMusic', id)
        this.$Message.success('Delete successful!')
      } else {
        this.$Message.error('Error, Failure to delete...')
      }
      this.$Spin.hide()
    }
  },
  components: {}
}
</script>

<style lang='scss' scoped>
section {
  animation: fadeIn 0.6s linear;
  padding: 90px 0 0px;
  width: 1080px;
  margin: 0 auto;

  h1 {
    text-align: center;
    color: #ff5050;
    text-shadow: 1px 1px 2px #adadad;
    font-size: 36px;
  }

  .add-button {
    float: left;
    width: 80px;
    margin-top: -15px;
  }
  .ivu-table-wrapper {
    margin-top: 28px;
  }
  .ivu-upload {
    float: left;
  }
  .modify-buttom {
    float: right;
    margin-left: 18px;
  }
}
</style>
<style lang="scss">
.music {
  .a-button {
    color: #5cadff;
    &:hover {
      color: #2d8cf0 !important;
    }
  }
  .ivu-upload-list {
    float: right;
    width: 200px;
    margin-left: 20px;
    margin-top: 0px;
    height: 10px;

    .ivu-upload-list-file span, .ivu-upload-list-remove, .ivu-upload-list-file-finish {
      display: none;
    }
  }
}
</style>
