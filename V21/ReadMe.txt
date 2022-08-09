重构代码
前面的版本有一个不变之处,每当我们总是添加一个Servlet
然后定义service方法(这是JAVA EE的标准),但是实际上很多个Servlet
处理的工作都是对同一组数据进行的,比如:注册,登录,显示用户信息的操作都是对
用户数据进行的.因此我们将这些操作合并到同一个类中

实现:
1:新建一个包:com.webserver.controller
2:将所有和用户相关的操作都定义在类：UserController中,用不同的方法来
完成不同的操作.将文章操作都定义在:ArticleController中
生成二维码的操作放在ToolsController中
3:修改DispatcherServlet,在不同的分支中完成对这些Controller方法的
调用完成原因的功能
