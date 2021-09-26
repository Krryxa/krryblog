const webpack = require("webpack")
const path = require("path")
const { VueLoaderPlugin } = require('vue-loader')
// const { CleanWebpackPlugin } = require('clean-webpack-plugin')
module.exports = {
  mode: 'none',
  externals: {
    // 'vue': 'Vue',
    // 'vuex': 'Vuex',
    // 'axios': 'axios',
    // 'vue-router': 'VueRouter',
    // 'iview': 'iview',
    'headroom': 'headroom',
    'utf-8-validate': 'utf-8-validate',
    bufferutil: 'bufferutil',
    // 'animate': 'animate',
    // 'valine': 'Valine',
    // 'leancloud-storage': 'AV',
    // 'mavon-editor': 'MavonEditor'
  },
  module: {
    rules: [
      {
        test: /\.css$/,
        use: ['vue-style-loader', 'css-loader', 'postcss-loader']
      },
      {
        test: /\.less$/,
        use: ['vue-style-loader', 'css-loader', 'postcss-loader', 'less-loader']
      },
      {
        test: /\.scss$/,
        use: ['vue-style-loader', 'css-loader', 'postcss-loader', 'sass-loader']
      },
      {
        test: /\.js$/,
        loader: 'babel-loader',
        exclude: /node_modules/,
      },
      {
        test: /\.vue$/,
        use: 'vue-loader'
      },
      {
        test: /\.(png|jpe?g|gif|svg|cur)(\?.*)?$/,
        loader: 'file-loader',
        options: {
          esModule: false,
          limit: 10000,
          name: 'img/[name].[hash:7].[ext]'
        }
      },
      {
        test: /\.(mp4|webm|ogg|mp3|wav|flac|aac)(\?.*)?$/,
        loader: 'url-loader',
        options: {
          limit: 10000,
          name: 'media/[name].[hash:7].[ext]'
        }
      },
      {
        test: /\.(woff2?|eot|ttf|otf)(\?.*)?$/,
        loader: 'url-loader',
        options: {
          limit: 10000,
          name: 'fonts/[name].[hash:7].[ext]'
        }
      }
    ]
  },
  plugins: [
    new VueLoaderPlugin(),
    // new CleanWebpackPlugin()
  ],
  resolve: {
    extensions: ['.js', '.vue', '.json'],
    alias: {
      '@': path.join(__dirname, '../src')
    }
  }
}