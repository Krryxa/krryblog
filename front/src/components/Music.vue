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
import { getMusic } from '@/service'
export default {
  data() {
    return {
      // basePath: window.location.origin + '/',
      basePath: process.env.API_ROOT,
      isPlay: false,
      firstTime: true, // 是否是第一次播放
      musicList: ['I Could Be the One-Donna Lewis.mp4'],
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
  created() {
    this.getMusic()
  },
  mounted() {},
  methods: {
    async getMusic() {
      let { result } = await getMusic()
      this.musicList = result.data.map(ele => ele.title)
      this.musicList = this.randomArray(this.musicList)
      this.init()
    },
    init() {
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
