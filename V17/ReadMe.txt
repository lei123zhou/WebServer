实现发表文章功能
流程:
1:用户在首页点击超链接来到发表文章页面
2:在页面上输入三个信息:
    文章标题,作者,文章内容
3:用户点击发表文章按钮提交表单
4:服务端处理后面响应发表文章结果页面

实现:
1:在webApps/myWeb下新建页面
    1.1:发表文章页面:createArticle.html
    form表单action=“createArticle”  method="post"
    表单中有两个输入框分别是标题和作者
    input name="title"
    input name="author"
    以及一个文本输入域
    textarea name="content"
    1.2:文章发表成功页面:createArticle_success.html
    居中显示一行字:发表文章成功

2:在com.webserver.servlet中新建业务处理类:CreateArticleServlet
    并实现service方法
    2.1:在com.webserver.vo包下新建类Article,用于表示一个文章
    需要实现序列化接口,并定义三个String的属性对应标题,作者,正文
    2.2:获取用户表单输入的信息后实例化一个Article,并将序列化保存到
    目录articles(与users目录一样,自行创建)中,
    文件名格式:文章标题.obj

3:在DispatcherServlet中添加分支