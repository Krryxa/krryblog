export function formatKM(_value, _decimal) {
  if (!_value || isNaN(Number(_value))) return 0
  let _k = 1000
  let _m = 10000
  let _d = '1'
  let _y = 100000000
  if (_decimal > 0) {
    for (let i = 0; i < _decimal; i++) {
      _d += '0'
    }
  }
  _d = Number(_d)
  _k = _k / _d
  _m = _m / _d
  _y = _y / _d

  _value = Number(_value)
  if (_value > 100000000) {
    _value = Math.round(_value / _y) / _d + '亿'
  } else if (_value > 100000) {
    _value = Math.round(_value / _m) / _d + 'w'
  } else if (_value > 10000) {
    _value = Math.round(_value / _k) / _d + 'k'
  }
  return _value
}

export function slideToogle(dom, duration = 1000) {
  if (!dom.tagTimer) {
    dom.status = dom.status || dom.style.display
    let flag = dom.status !== 'none' // 保存当前的显示状态，方便进行判断
    dom.status = flag ? 'none' : 'block' // 更改dom上的显示属性
    dom.style.transition = 'height ' + duration / 1000 + 's' // 设置动画时间
    dom.style.overflow = 'hidden' // 保证子元素不外溢
    animateDom(flag, dom, duration) // 操作dom的height属性
  }
}

function animateDom(flag, dom, duration) {
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
    dom.tagTimer = null
  }, duration)
}

function setData(dom) {
  dom.tagTimer = dom.tagTimer || null
  dom.style.display = 'block'
  dom.tagHeight = dom.clientHeight + 'px'
  dom.style.display = ''
}
