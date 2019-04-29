# krryblog

> Statement: Front-end and back-end configuration files, package.json have been ignored

## tec
1. vue、iview、markdown
2. java、ssm、maven
3. mysql

## Build Setup

``` bash
# install dependencies
npm install

# serve with hot reload at localhost:8080
npm run dev

# build for production with minification
npm run build

# build for production and view the bundle analyzer report
npm run build --report

# run unit tests
npm run unit

# run e2e tests
npm run e2e

# run all tests
npm test
```

## requirement analysis
Code specification：eslint

The main content of personal music blog is divided into three modules: administrator-tourists-music, multi-interface blog system.

1. Tourist module, which is composed of browsing, searching and commenting blog, visitors can browse blog through blog homepage, classification or search function, and can also comment on each blog.

2. Administrator module, this module is composed of personal information management, publishing and editing blog. Administrators can publish, manage and edit blog in the background interface (support Markdown).

3. Music module, this module is composed of music player. Anyone who visits this blog can choose, play and suspend the background music of the blog platform.


## Installation dependency
```bash
npm install stylus-loader css-loader style-loader --save-dev
npm install axios
npm install vuex --save
npm install sass-loader --save
npm install node-sass --save
```
- This project uses iView
```bash
npm install iview --save
npm install iview-loader --save-dev
```
- iView 将标签渲染为原生 html 标签时，由于这些标签是自闭合的，所以有 end 标签会报错，在 .eslintrc.js 多配置一项：
```js
'vue/no-parsing-error': [2, { 'x-invalid-end-tag': false }],
```

- 置顶图标使用了阿里巴巴 iconfont 字体图片
- 其他均使用 iView 官方字体图标

鼠标格式
```js
// 需要在 webpack.base.conf.js 中设置多一个 rules cur
{
  test: /\.(png|jpe?g|gif|svg|cur)(\?.*)?$/,
  loader: 'url-loader',
  options: {
    limit: 10000,
    name: utils.assetsPath('img/[name].[hash:7].[ext]')
  }
},
```
1. 实现前后端分离项目，易维护
2. 查询博客功能，可根据博客的==名称==、==描述==、==标签==、==分类==查询博客
3. 回到顶部功能
4. 根据滚动条上滑出现吸顶盒（头部），下滑隐藏
5. 博客详情页以 markdown 语法展示和保存，保存两份，一份是原始 markdown 语法，一份是转义后的 html
6. 所有页面都使用 fadeIn 动画显示 animation: fadeIn .6s linear;
7. 博客详情页图片点击放大、自动生成目录
8. 做一个后台系统，存放所有的博客文章，包括状态为“不发布”的《关于》和《友情链接》文章，这样可编辑和管理所有博客
9. 使用骨架屏，数据异步加载完成前友好显示一个骨架屏，增强用户体验（在模板中模拟空数据来生成，当真实数据加载完成，将模拟数据替换成真实数据；可保持加载时原有的动画）
10. 使用逻辑删除，isDelete 设置为 1，表示已删除（数据珍贵，不能物理删除）
11. 移动端响应式（实现非常顺滑的小菜单的控制隐藏（点击显示，鼠标移开隐藏）这样就可以做到在移动端点击关闭按钮可以关闭菜单，点击菜单之外的区域也可以关闭菜单）
12. Valine 评论系统接入，任何人均可对每篇博客进行评论
13. 歌曲播放，并且在点击跳转页面仍然不会打断歌曲播放
14. 路由跳转的时候，做一个顶部的加载条显示


##  图片长方形：280*230
1. 因为使用 background 来装载博客图片，使用 filter: blur(3px); 使背景图片虚化，当准备做图片懒加载的时候，使用了 ::before{} 在被选元素的前面插入内容，且设置 z-index: -1，使用 content 属性定义插入内容的文本信息，这样子来做图片懒加载，当背景图片加载完成，会自动覆盖 ::before{} 的内容
2. 点击分页的时候，加载数据完成后，section 先 display: none，再 display: block; 才会显示动画，显示 block 时使用 setTimeout 延迟一下
```js
// 共用组件，每次数据变化产生过渡效果
this.$refs.blogSection.style['display'] = 'none';
setTimeout(() => {
  this.$refs.blogSection.style['display'] = 'block';
}, 0);
```

## 问题
1. 如果出现mybatis查询中文没有结果集的情况，或者查询中文出现报错，在jdbc.propersities文件里的db.url=数据库地址加上?useUnicode=true&characterEncoding=UTF-8
2. 若页面已经被 keepAlive 了，那将获取路由参数的钩子应该是 activated，created 和 mounted 钩子无法获取
3. 子组件的 props 的 default 属性，只在父组件没有传递该 props 的情况下才生效，有可能该子组件被很多父组件引用，有些父组件会传递 props 属性，有些没有，没有传递 props 属性的子组件，就会用到 default 属性
4. 骨架屏的制作：（本项目使用方法 2 ）
（1）自制模板，进入路由展示，加载数据后替换真实组件<br>
（2）使用图片模板，就要使用 ps<br>
（3）模拟数据字段，在真实模板模拟异步加载的数据字段，但是数据基本为空，当真实数据加载完成，将模拟数据替换成真实数据（可保留之前自定义的动画）
5. 监听对象 {} 变化的问题，可以使用深度监听：
```js
mounted () {
  // 这里使用深度监听 blog 对象的属性变化
  this.$watch('blog', this.getCatalog, {
    deep: true,
  });
},
```
6. 移动端不支持十六进制的透明度，会失效，所以要在设置颜色的同时设置透明度就用 rgba 的形式设置
7. iview 的对话框 modal 在点击确定后会自动关闭，有时需要在验证表单通过后再关闭对话框，但是官方对话框点了确定，不管怎么样都会关闭对话框，解决方法：
使用 slot 重写底部按钮
```html
<Modal title="退款" v-model="showRejectModal">
   <!--重点就是下面的代码了-->
   <div slot="footer">
   	<Button type="text" size="large" @click="showRejectModal=false">取消</Button>
   	<Button type="primary" size="large" @click="doSomething">确定</Button>
   </div>
</Modal>
```
8. 很多页面使用到 loading 动画，放入 mixins 中混入需要的页面
9. 博客点击数的统计，写在后台，当调用查询博客详情页的接口时，点击数 +1
```java
  /**
	 * 获取博客详情页
	 * @return
	 */
	public HashMap<String, Object> getBlogDetail(int id){
		
		Blog newBlog = new Blog();
		
		Blog blog = blogMapper.getBlogDetail(id);
		
		HashMap<String, Object> resData = new HashMap<>();
		
		if (blog != null) {
			// 设置点击量+1
			int hit = blog.getHit();
			blog.setHit(++hit);
			newBlog.setHit(hit);
			newBlog.setId(id);
			
			blogMapper.updateBlog(newBlog);
			
			// 处理查询出timestamp时间类型多了个 .0  的问题
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 设置日期格式
			try {
				Date createData = df.parse(blog.getCreateTime());
				Date updateData = df.parse(blog.getUpdateTime());
				String createTime = df.format(createData);
				String updateTime = df.format(updateData);
				blog.setCreateTime(createTime);
				blog.setUpdateTime(updateTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			resData.put("status", 200);
		} else {
			resData.put("status", 404);
		}
		resData.put("data", blog);
		
		return resData;
	}
```
10. 博客评论数的统计，点击提交评论，验证码通过时，后端调用 updateBlog 接口，根据传递的参数只有 comment，就不更改 updateTime
11. 登录时查询用户名，mysql 数据库的 user 表 name 字段设置成 utf-8_bin 才可以区分英文大小写查询

## 部署
1. 文件路径出错：在 config 的 index.js 下 build 的设置：assetsPublicPath: ‘./’,
注意，如果是 history 路由 此文件，此字段设置成 assetsPublicPath: ‘/’
原因如下：
```
'./' 是指用户所在的当前目录（相对路径）
'/' 是指根目录，也就是项目的根目录
对于 hash 模式，根路径是固定的，就是项目的根目录
但是 history 模式下，以 / 开头的嵌套路径会被当作根路径
所以当使用 './' 引入文件，就会找不到文件了
因为文件本身就是在项目根目录下的，并不在嵌套 history 的路径目录下
```
2. 字体路径出错：
```
真实路径应该是
xxx/static/fonts/icomoon.0125455.woff 
浏览器实际加载路径为：
xxx/static/css/static/fonts/icomoon.0125455.woff 
解决方法：
webpack 配置问题
在 build/webpack.prod.conf.js 中 extract :true 改为 fasle 即可
```
3. css 添加的背景中引用的图片路径出错
在 build 文件夹下 找 utils.js 配置加上 publicPath: '../../',
```js
// Extract CSS when that option is specified
// (which is the case during production build)
if (options.extract) {
  return ExtractTextPlugin.extract({
    use: loaders,
    publicPath: '../../', // 加上这一行
    fallback: 'vue-style-loader'
  })
} else {
  return ['vue-style-loader'].concat(loaders)
}
```
4. 自定义动画不执行，在 vue 项目中找到 build 文件夹下的 vue-loader.conf.js，将 extract：isProduction  改为 extract：false
5. 开发环境和生产环境的接口地址问题
在 config 下 dev.env.js 是配置开发环境，prod.env.js 是配置生产环境<br>
开发环境使用 proxyTable 代理接口，所以不用管接口问题<br>
生产环境时，与线上的接口不产生跨域问题，所以在 prod.env.js 配置：
```js
'use strict'
module.exports = {
  NODE_ENV: '"production"',
  API_ROOT: '"https://ainyi.com"'
}

```
所以在 axios 配置：
```js
axios.defaults.baseURL = process.env.NODE_ENV === 'production' ? process.env.API_ROOT : '';
```

6. 将 vue 打包后输出的 dist 文件夹下面的文件拷贝到 后台接口项目目录结构的 src/main/webapp/ 下面

7. 把所有的 console.log 删除