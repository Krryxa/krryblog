<template>
  <section>
    <!--播放器-->
    <audio ref="audio" @ended="next"></audio>
    <div class="music" :class="{ playing: isPlay }" :title="isPlay ? '暂停' : '播放'" @click="!isTransition && (isPlay ? pause() : play())">
      <img src="@/assets/pic/music.svg" width="25" height="25">
    </div>
    <div class="title" :class="!firstTime && (isPlay ? 'titleIn' : 'titleOut')">{{ musicTitle }}</div>
  </section>
</template>

<script>
export default {
  data () {
    return {
      // basePath: window.location.origin + '/',
      basePath: 'https://ainyi.com/',
      isPlay: false,
      firstTime: true, // 是否是第一次播放
      musicList: [
        'Don Coi(Instrumental).mp4',
        `Girl's Talk.mp4`,
        'if....mp4',
        'Lazy Afternoon.mp4',
        'tear in pink rain.mp4',
        '李幸倪- 月球下的人.mp4',
        '李幸倪,郭伟亮- 双双.mp4',
        '谢安琪- 年度之歌.mp4',
        '银临,鸦青-湘桥月.mp4',
      ],
      isTransition: false,
      currIndex: 0,
    };
  },
  computed: {
    audio () {
      return this.$refs.audio;
    },
    musicTitle () {
      let allTitle = this.musicList[this.currIndex];
      return allTitle.substr(0, allTitle.lastIndexOf('.'));
    },
    musicLen () {
      return this.musicList.length;
    },
    musicLink () {
      return `${this.basePath}music/${this.musicList[this.currIndex]}`;
    },
  },
  created () {
  },
  mounted () {
    this.init();
  },
  methods: {
    init () {
      this.musicList = this.randomArray(this.musicList);
      this.audio.src = this.musicLink;
      this.audio.volume = 0.5;
    },
    play () {
      this.firstTime = false;
      this.audio.play();
      this.isPlay = true;
    },
    pause () {
      this.audio.pause();
      this.isPlay = false;
    },
    next () {
      this.pause();
      this.isTransition = true;
      setTimeout(() => {
        this.currIndex = this.currIndex === this.musicLen - 1 ? 0 : ++this.currIndex;
        this.audio.src = this.musicLink;
        this.play();
        this.isTransition = false;
      }, 1000);
    },
    randomArray (arr) {
      return arr.sort(() => 0.5 - Math.random());
    },
  },
  components: {
  },
};
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
    right: 66px;
    margin-top: 3.5px;
    position: absolute;
    color:rgba(255, 97, 92, 1);
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
