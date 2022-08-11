利用反射机制对DispatcherServlet处理业务部分进行重构.做到将来添加新
业务(无论是新添加一个Controller类还是在某个Controller类中添加了新的
业务方法)DispatcherServlet都不会再做任何修改

思想:
首先设计一个注解:@Controller.该注解用来修饰所有的业务处理类,便于让
DispatcherServlet筛选类使用.
每个Controller在用于处理某个请求的业务方法在使用注解:@RequestMapping
修饰,并且该注解传入一个参数用于标明该方法处理的请求路径,例如:
@RequestMapping("/myWeb/regUser")
public void reg(HttpRequest request，HttpResponse respone)
这样一来,DispatherServlet可以同来垓注解筛选请求的方法,并根据该参数
的值某个请求哪个方法来处理了

实现:
1:新建一个包:com.webserver.core.annotation
2:新建两个注解@Controller和@RequestMapping
