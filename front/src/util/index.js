export function slideToogle(dom, duration = 1000) {
  dom.status = dom.status || getComputedStyle(dom, null)['display']
  let flag = dom.status !== 'none' // 保存当前的显示状态，方便进行判断
  dom.status = flag ? 'none' : 'block' // 更改dom上的显示属性
  dom.style.transition = 'height ' + duration / 1000 + 's' // 设置动画时间
  dom.style.overflow = 'hidden' // 保证子元素不外溢
  animateDom(flag, dom, duration) // 操作dom的height属性
}

function animateDom(flag, dom, duration) {
  clearTimeout(dom.tagTimer)
  setData(dom)
  dom.style.height = flag ? dom.tagHeight : '0px'
  setTimeout(() => {
    dom.style.height = flag ? '0px' : dom.tagHeight
  }, 0)
  dom.tagTimer = setTimeout(() => {
    dom.style.display = flag ? 'none' : 'block'
    dom.style.transition = ''
    dom.style.overflow = ''
    dom.style.height = ''
  }, duration)
}

function setData(dom) {
  dom.tagTimer = dom.tagTimer || null
  dom.style.display = 'block'
  dom.tagHeight = dom.tagHeight || dom.clientHeight + 'px'
  dom.style.display = ''
}
