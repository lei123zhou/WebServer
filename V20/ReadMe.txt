实现显示所有文章列表的动态页面

实现:
1:在首页webapps/myweb/index.html中添加一个超链接
href="./showAllArticle"
2:添加一个生成动态页面的处理类:ShowAllArticleServlet
在servlet方法中生成页面并响应给浏览器

该页面以表格的形式展现所有文章和作者