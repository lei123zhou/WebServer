项目重构
进行功能拆分,讲ClientHandler中处理请求和响应客户端的细节工作全部拆分出去

第一步:拆分响应客户端的工作
实现:
1:在com.webserver.http包中新建类:HttpResponse响应对象
该类的每一个实例用于表示给客户端发送的一个响应内容
2:在响应对象中定义一个响应中应当包含的三部分信息(状态行,响应头,响应正文)
对应的属性
3:定义方法flush,用于发送响应内容
第二步:拆分处理请求的工作
实现:
1:新建包:com.webserver.servlet
2:在servlet包中新建类:DispatcherServlet,并定义service方法处理
请求的工作
3:将ClientHandler中处理请求的工作拆分到DispatcherServlet的service
方法中