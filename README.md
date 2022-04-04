```阅读小傅哥的spring教程的笔记代码```

各个模块的设计目标在各个模块的pom文件中可以找到

---
打断点调试读比较好，spring源码有点复杂，自己模仿写的，也有些类的关系记不清

module：
- 1、直接new一个对象放在容器里
- 2、将beanDefinition放在容器里，注入的时候初始化
- 3、可以根据bean的构造函数的参数个性化初始化（之前只能使用默认构造函数）
- 4、对于在注入时没有被构造的属性进行属性的注入
- 5、可以根据配置文件进行bean定义（之前需要写代码构造）
- 6、可以自定义bean工厂后置处理器和bean前后置处理器
- 7、可以自定义初始化和销毁钩子函数