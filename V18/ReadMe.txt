实现动态页面
动态页面:含有动态数据的页面称为动态页面.

动态数据或动态资源指的时:这些数据和资源都不是事先准备好的,二十每次需要使用
时临时有程序生成的
静态资源:事先准备好的,内容不会变得资源
列如:视频文件,动图gif他们都是静态资源
而验证码就是典型的动态资源,每次需要时有程序临时生成

需求:用户请求查看所有注册用户信息,此时我们需要响应给用户据一个htmlk页面,
但是改页面上的所有用户信息不能是固定的,因此每次需要显示该页面时都是由程序
根据最新的用户数据零食生成该页面相应给浏览器、

实现:
1:在com.webserver.servlet包下新建类:ShowAllUserServlet
在service方法在根据所有的注册用户信息生成html并响应给浏览器

2:在首页webApps/myWeb/index.html下添加一个超链接,请求该业务
