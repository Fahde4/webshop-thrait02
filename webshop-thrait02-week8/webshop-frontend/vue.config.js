const { defineConfig } = require('@vue/cli-service')

module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port: 8106
  },
  pages: {
    index: {
      // entry for the page
      entry: 'src/main.js',
      title: 'Web Shop',
    },
  }
})
