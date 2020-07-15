<template>
  <div class="audio-icon">
    <div class="column" style="animation-delay: -1.2s;"></div>
    <div class="column"></div>
    <div class="column" style="animation-delay: -1.5s;"></div>
    <div class="column" style="animation-delay: -0.9s;"></div>
    <div class="column" style="animation-delay: -0.6s;"></div>
  </div>
</template>
<style scoped lang="scss">
.audio-icon {
  display: flex;
  width: 20px;
  min-width: 18px;
  height: 18px;
  visibility: hidden;
  opacity: 0;
  transition: 1s;
  transform: rotate(180deg);

  .column {
    width: 2px;
    height: 100%;
    margin-left: 2px;
    background-color: #515a6e;
    animation: play 0.9s linear infinite alternate;
  }
}

@keyframes play {
  from {
    height: 10%;
  }

  to {
    height: 100%;
  }
}
</style>
