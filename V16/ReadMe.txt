完成用户登录业务
登录流程:
1:用户在首页点击超链接来到登录页面
2:在登录页面输入用户名和密码并点击登录按钮进行登陆
3:服务端处理登陆业务兵响应登录结果页面(成功或失败)

实现:
1:在webApps/MyWeb目录下准备三个页面
    1.1登录页面,login.html
        该页面form表单action="./loginUser" method="post"
    1.2:登录成功提示页面,login_success.html
        居中显示一个行字:登陆成功,欢迎回来
    1.3:登录失败提示页面,login_fail.html
        居中显示一行字:登录失败,用户名或密码不正确
2:在com.webserver.servlet包中新建处理登陆业务类:LoginServlet
    实现service方法,完成登录业务
    要求:只有用户名和密码都正确才响应登录成功页面.
    否则都响应登录失败页面(没有此用户或密码错误)
3:在DispatcherServlet中添加一个分支.判断请求如果是请求登录就实例化
    LoginServlet并调用其Service方法处理即可