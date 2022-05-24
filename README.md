```阅读小傅哥的spring教程的笔记代码```

各个模块的设计目标在各个模块的pom文件中可以找到

---
打断点调试读比较好，spring源码有点复杂，自己模仿写的，也有些类的关系记不清

请使用jdk8运行项目，高版本java会导致cglib报错（java9开始引入module，非法访问lib中的一些module是禁止的）

module：
- [1、可以直接new一个对象放在容器里](images/small-spring-step-01.png)
- [2、可以将beanDefinition放在容器里，获取的时候再初始化](images/small-spring-step-02.png)
- [3、可以根据bean的构造函数的参数个性化初始化（之前只能使用默认构造函数）](images/small-spring-step-03.png)
- [4、可以在注入时对没有被构造的属性进行属性的注入](images/small-spring-step-04.png)
- [5、可以根据配置文件进行bean定义（之前需要写代码构造）](images/small-spring-step-05.png)
- [6、可以自定义bean工厂后置处理器和bean前后置处理器](images/small-spring-step-06.png)
- [7、可以自定义初始化和销毁的钩子函数](images/small-spring-step-07.png)
- [8、可以让bean感知到自己创建过程中的一些参与者，这在spring当中叫做aware](images/small-spring-step-08.png)
- [9、可以设置scope，可以与其他框架交接](images/small-spring-step-09.png)
- [10、可以设置事件监听器，也就是listener](images/small-spring-step-10.png)
- [11、可以自定义方法切面增强，但是还没和spring整合起来](images/small-spring-step-11.png)
- [12、可以使用spring的aop功能](images/small-spring-step-12.png)
- [13、可以使用spring配置文件中注入属性和@Component包扫描](images/small-spring-step-13.png)