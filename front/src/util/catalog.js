'use strict';

var _typeof = typeof Symbol === 'function' &&
  typeof Symbol.iterator === 'symbol' ? function (obj) {
    return typeof obj;
  } : function (obj) {
    return obj && typeof Symbol === 'function' &&
      obj.constructor === Symbol &&
      obj !== Symbol.prototype ? 'symbol' : typeof obj;
  };

export default function (opts) {
  var defaultOpts = {
    linkClass: 'cl-link', // 所有目录项都有的类
    linkActiveClass: 'cl-link-active', // active的目录项
    datasetName: 'data-cata-target', // 目录项DOM的attribute存放对应目录的id
    selector: ['h1', 'h2', 'h3', 'h4', 'h5', 'h6'], // 按优先级排序
    // scrollWrapper: null, // 滚动条区域
    activeHook: null, // 激活时候回调
    topMargin: 0,
    bottomMargin: 0,
  };

  var Opt = Object.assign({}, defaultOpts, opts);

  var $content = document.getElementById(Opt.contentEl); // 内容获取区
  // var $scrollWrap = Opt.scrollWrapper; // 滚动条区域
  var $catalog = document.getElementById(Opt.catalogEl); // 目录容器

  // querySelectorAll() 返回所匹配到的所有元素集合；数组：NodeList[]
  // 在内容区获取要生成目录的 H 标签元素数组，allCatalogs：nodeList[]数组形式
  var allCatalogs = $content.querySelectorAll(Opt.selector.join()); // Opt.selector.join() => "h1,h2,h3"

  // 生成目录树
  var tree = getCatalogsTree(allCatalogs);

  try {
    $catalog.innerHTML = '<div class=\'cl-wrapper\'>' + generateHtmlTree(tree, { id: -1 }) + '</div>';
  } catch (e) {
    console.error('error in progress-catalog', e);
  }

  // 事件注册
  $catalog.addEventListener('click', clickHandler);
  window.addEventListener('scroll', simpleScrollHandler);

  /**
   * 滚动事件
   */
  function simpleScrollHandler (el) {
    var scrollToEl = null;
    for (var i = allCatalogs.length - 1; i >= 0; i--) {
      var scrollTop = document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop;
      if (allCatalogs[i].offsetTop <= scrollTop + 30) {
        scrollToEl = allCatalogs[i];
        break;
      }
    }
    if (scrollToEl) {
      setActiveItem(scrollToEl.id);
    } else {
      setActiveItem(null); // 无匹配的元素
    }
  }
  /**
   * 点击事件
   */
  function clickHandler (_ref) {
    var target = _ref.target;

    var datasetId = target.getAttribute(Opt.datasetName);
    // 当前点击的 class 如果包含 Opt.linkClass，就跳到某一目录
    // Element.scrollIntoView() 方法让当前的元素滚动到浏览器窗口的可视区域内
    target.classList.contains(Opt.linkClass) && document.getElementById(datasetId).scrollIntoView({ behavior: 'smooth', block: 'start' });
  }

  /**
   * 获取目录树，生成类似于Vnode的树
   * catalogs NodeList[]
   * @param catalogs
   */
  function getCatalogsTree (catalogs) {
    var title = void 0;
    var tagName = void 0;
    var tree = [];
    var treeItem = {};
    var parentItem = { id: -1 };
    var lastTreeItem = null;
    var id = void 0;

    for (var i = 0; i < catalogs.length; i++) {
      title = catalogs[i].innerText || catalogs[i].textContent; // 获取当前 H 标签的内容名称
      tagName = catalogs[i].tagName; // 获取 H 标签的标签名
      id = 'heading-' + i;
      catalogs[i].id = id; // 给内容区域的 H 标签设置 id
      treeItem = {
        name: title,
        tagName: tagName,
        id: id,
        level: +getLevel(tagName), // H 标签等级
        parent: parentItem,
      };
      if (lastTreeItem) {
        // 当前 treeItem 的 H > 上一个 treeItem 的 H （如 3 > 2）H3 是 H2 的子标题，说明当前 treeItem.parent = 上一个 treeItem
        if (treeItem.level > lastTreeItem.level) {
          treeItem.parent = lastTreeItem;
        } else {
          treeItem.parent = findParent(treeItem, lastTreeItem);
        }
      }
      lastTreeItem = treeItem;
      tree.push(treeItem);
    }
    return tree;
  }

  /**
   * 找到当前节点的父级
   * @param currTreeItem
   * @param lastTreeItem
   * @returns {*|Window}
   */
  function findParent (currTreeItem, lastTreeItem) {
    var lastTreeParent = lastTreeItem.parent;
    // 找到 lastTreeParent 的 H 标签是 currTreeItem 的父标签就停止循环
    // 如：H3 是 H2 的子标题，当 currTreeItem H3 > lastTreeParent H2 就找到了父级 H2
    while (lastTreeParent && currTreeItem.level <= lastTreeParent.level) {
      lastTreeParent = lastTreeParent.parent;
    }
    return lastTreeParent || { id: -1 };
  }

  /**
   *  获取 H 标签的等级，就是获取 H1 中的 1 / H2 中的 2
   * @param tagName
   * @returns {*}
   */
  function getLevel (tagName) {
    return tagName ? tagName.slice(1) : 0;
  }

  /**
   * 生成 DOM 树
   * @param tree
   * @param _parent
   * @return {string}
   */
  function generateHtmlTree (tree, _parent) {
    var ul = void 0;
    var hasChild = false;
    if (tree) {
      ul = '<ul>';
      for (var i = 0; i < tree.length; i++) {
        if (isEqual(tree[i].parent, _parent)) {
          hasChild = true;
          ul += '<li><div class=\'' + Opt.linkClass + ' cl-level-' + tree[i].level + '\' ' + Opt.datasetName + '=\'' + tree[i].id + '\'>' + tree[i].name + '</div>';
          ul += generateHtmlTree(tree, tree[i]); // 递归获取子节点
          ul += '</li>';
        }
      }
      ul += '</ul>';
    }
    return hasChild ? ul : '';
  }

  /**
   * 判断是否是相同节点
   */
  function isEqual (node, node2) {
    return node && node2 &&
      (typeof node === 'undefined' ? 'undefined' : _typeof(node)) === 'object' &&
      (typeof node2 === 'undefined' ? 'undefined' : _typeof(node2)) === 'object' &&
      node.id === node2.id;
  }

  /**
   *  滚动到当前目录，设置选中的目录
   */
  function setActiveItem (id) {
    var _this = this;

    var catas = [].concat(_toConsumableArray($catalog.querySelectorAll('[' + Opt.datasetName + ']')));

    catas.forEach(function (T) {
      if (T.getAttribute(Opt.datasetName) === id) {
        typeof Opt.activeHook === 'function' && !T.classList.contains(Opt.linkActiveClass) && Opt.activeHook.call(_this, T); // 执行 active 钩子
        T.classList.add(Opt.linkActiveClass);
      } else {
        T.classList.remove(Opt.linkActiveClass);
      }
    });
  }
};

// 复制数组
function _toConsumableArray (arr) {
  if (Array.isArray(arr)) {
    for (var i = 0, arr2 = Array(arr.length); i < arr.length; i++) {
      arr2[i] = arr[i];
    }
    return arr2;
  } else {
    return Array.from(arr);
  }
}
