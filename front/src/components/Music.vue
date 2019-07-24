<template>
  <section>
    <!--播放器-->
    <audio ref="audio" @ended="next(true)"></audio>
    <div
      class="music"
      :class="{ playing: isPlay }"
      :title="isPlay ? '暂停Alt+Enter' : '播放Alt+Enter'"
      @click="!isTransition && (isPlay ? pause() : play())"
    >
      <img src="@/assets/pic/music.svg" width="25" height="25">
    </div>
    <div class="op-btn">
      <span class="next-btn">
        <Icon title="下一首Alt+Right" @click="next(true)" type="ios-skip-forward"/>
      </span>
      <span class="pre-btn">
        <Icon title="上一首Alt+Left" @click="next(false)" type="ios-skip-backward"/>
      </span>
    </div>
    <div class="title" :class="!firstTime && (isPlay ? 'titleIn' : 'titleOut')">{{ musicTitle }}</div>
  </section>
</template>

<script>
export default {
  data() {
    return {
      // basePath: window.location.origin + '/',
      basePath: process.env.API_ROOT,
      isPlay: false,
      firstTime: true, // 是否是第一次播放
      musicList: [
        `Girl's Talk.mp4`,
        'Lazy Afternoon.mp4',
        'tear in pink rain.mp4',
        '李幸倪- 月球下的人.mp4',
        '李幸倪,郭伟亮- 双双.mp4',
        '谢安琪- 年度之歌.mp4',
        '银临,鸦青-湘桥月.mp4',
        '等你下课-周杰伦.m4a',
        '彩虹-周杰伦.m4a',
        '风吹麦浪-李健.m4a',
        '告白气球-周杰伦.m4a',
        '可爱女人-周杰伦.m4a',
        '青花瓷-周杰伦.m4a',
        '说好的幸福呢-周杰伦.m4a',
        '夏天的风-温岚.m4a',
        'Better me- AGA.mp4',
        '圆 - AGA.mp4',
        '一加一 - AGA.mp4',
        'Begin Again - Taylor Swift.mp3',
        '棠梨煎雪 - 银临.mp4',
        '다이아 - 나랑사귈래 응원법(和我交往吧).mp3',
        '流光记 - 银临.mp4',
        '伞下铭 - 洛少爷.mp4',
        '清明梦 - 音阙诗听.mp4',
        'Morrie Note - morrie.mp4',
        'Lullaby - Morrie.mp4',
        '苏打绿 - 他夏了夏天.mp4',
        '苏打绿 - 小情歌.mp4',
        '王菲 - 梦中人.mp4',
        '王菲 - 梦游.mp4',
        '周传雄 - 青花.mp4',
        '房东的猫 - 云烟成雨.mp4',
        '恋愛サーキュレーション - 花澤香菜.mp4',
        '偏爱 - 张芸京.mp4',
        '彼女は旅に出る - 鎖那.mp4',
        '冬眠 - ヨルシカ.mp4',
        'ルミネセンス.mp4',
        '夏恋 - Otokaze.mp4',
        '恋の道 - MACO.mp4',
        '葉桜 - 北乃きい.mp4',
        '玖肆仟 - さくら～あなたに出会えてよかった～（Cover RSP）.mp3',
        'Lifeline.mp4',
        'ツギハギスタッカート.mp4',
        '可惜我是水瓶座 - 杨千嬅.mp4',
        'カラフル - Flower.mp4',
        '叙世 - Aki阿杰、清弄.mp4',
        '日不落 - 蔡依林.mp4',
        'Two Is Better Than One - Boys Like Girls.mp4',
        '川井憲次 - 牢笼中的满月.mp3',
        '花たん - Last Night,Good Night.mp3',
        '九月橙,九玖橙儿 - 《从你的全世界路过》纯音乐插曲.mp3',
        '冥月 - 月西江（Cover MIKU）.mp3',
        '银临 - 不老梦.mp3',
        '银临 - 七夕.mp3',
        'DAISHI DANCE - if.mp3',
        'Flower - 太陽と向日葵.mp3',
        '深海少女.mp3',
        'I Could Be the One-Donna Lewis.mp4',
        '50 Ways to Say Goodbye-Train.mp4'
      ],
      isTransition: false,
      currIndex: 0
    }
  },
  computed: {
    audio() {
      return this.$refs.audio
    },
    musicTitle() {
      let allTitle = this.musicList[this.currIndex]
      return allTitle.substr(0, allTitle.lastIndexOf('.'))
    },
    musicLen() {
      return this.musicList.length
    },
    musicLink() {
      return `${this.basePath}/music/${this.musicList[this.currIndex]}`
    }
  },
  created() {},
  mounted() {
    this.init()
  },
  methods: {
    init() {
      this.musicList = this.randomArray(this.musicList)
      this.audio.src = this.musicLink
      this.audio.volume = 0.5
      document.addEventListener('keydown', e => {
        if (e.altKey) {
          switch (e.keyCode) {
            case 13:
              !this.isTransition && (this.isPlay ? this.pause() : this.play())
              break
            case 39:
              this.next(true)
              break
            case 37:
              this.next(false)
              break
            default:
              break
          }
        }
      })
    },
    play() {
      this.firstTime = false
      this.audio.play()
      this.isPlay = true
    },
    pause() {
      this.audio.pause()
      this.isPlay = false
    },
    next(flag) {
      this.pause()
      this.isTransition = true
      setTimeout(() => {
        // flag 是 true 代表下一曲，false 代表上一曲
        this.currIndex = flag
          ? this.currIndex === this.musicLen - 1
            ? 0
            : ++this.currIndex
          : this.currIndex === 0
            ? this.musicLen - 1
            : --this.currIndex
        this.audio.src = this.musicLink
        this.play()
        this.isTransition = false
      }, 300)
    },
    randomArray(arr) {
      return arr.sort(() => 0.5 - Math.random())
    }
  },
  components: {}
}
</script>

<style lang='scss' scoped>
section {
  width: 1080px;
  position: fixed;
  max-width: 100%;
  top: 78px;
  left: 0;
  right: 0;
  margin: 0 auto;
  z-index: 1010;

  .title {
    visibility: hidden;
    right: 64px;
    margin-top: 3.5px;
    position: absolute;
    color: rgba(255, 97, 92, 1);
  }
  .titleIn {
    visibility: visible;
    animation: bounceInRight 1s linear, outText 10s linear forwards;
  }
  .titleOut {
    visibility: visible;
    animation: bounceOutRight 1s linear forwards;
  }

  .music {
    cursor: url(../assets/pic/cursor.cur), pointer !important;
    position: absolute;
    right: 28px;
    &:hover {
      + .op-btn {
        visibility: visible;
        opacity: 0.76;
      }
    }
    img {
      animation: outBtn 4s linear forwards;
      &:hover {
        animation: inBtn 1s forwards;
      }
    }
  }
  .playing {
    animation: play 1.8s infinite linear;
  }

  .op-btn {
    visibility: hidden;
    transition: 0.5s;
    opacity: 0;
    &:hover {
      visibility: visible;
      opacity: 0.76;
    }
  }

  .next-btn {
    cursor: url(../assets/pic/cursor.cur), pointer !important;
    position: absolute;
    right: 5px;
    width: 30px;
    text-align: right;
    font-size: 19px;
    height: 25px;
    line-height: 20px;
  }
  .pre-btn {
    cursor: url(../assets/pic/cursor.cur), pointer !important;
    position: absolute;
    top: 19px;
    right: 28px;
    height: 30px;
    text-align: center;
    font-size: 19px;
    width: 25px;
    line-height: 32px;
  }
}
@keyframes play {
  100% {
    transform: rotate(360deg);
  }
}
@keyframes outText {
  from {
    opacity: 1;
  }
  to {
    opacity: 0.3;
  }
}
@keyframes outBtn {
  from {
    opacity: 1;
  }
  to {
    opacity: 0.6;
  }
}
@keyframes inBtn {
  from {
    opacity: 0.6;
  }
  to {
    opacity: 1;
  }
}
</style>
