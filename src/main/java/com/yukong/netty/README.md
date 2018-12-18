用于记录我的学习过程

# 一、netty基础
> 时间 2018年8月22日14:41:11

 学习netty，优点：
   - 用于屏蔽网络编程底层的复杂，提供应用层的api
      使得业务与网络逻辑解耦，
   - 提供高并发高性能网络编程支持
## 1.1 netty入门
   了解原生的java socket api
   通过`ServerSocket`作为服务器，发送socket到客户端`Socket`
   其中`accpet()`方法是阻塞方法.
   缺点：

   - 长时间等待，线程休眠，浪费资源
   - 不用于多个socket客户端。

  了解`netty`的核心组件
  - Channel
  - 回调
  - Future
  - 事件和ChannelHandler

 ## 1.2 编写第一个netty客户端与服务器
 >2018年8月23日10:42:27
 ### 1.2.1 服务端
   - 至少一个`ChannelHandler`---- 该组件实现了服务器对
      从客户端接受的数据的处理，即它就是业务逻辑处理器
   - 引导(`ServerBootsStrap`) ---- 这是配置服务器的启动代码，至少
      ，它会将服务器绑定到了它要监听的连接请求的端口。
  ### 1.2.2 客户端
   1、 连接服务端

   2、 发送一个或者多个消息

   3、 对于每个消息，等待并接受从服务端返回

   4、 关闭连接

   与服务端相同，一个`ChannelHandler`处理客户端业务逻辑
   ，一个引导客户端(`BootStrap`)。

   ## 1.3 Netty的组件与设计
   > 2018年8月28日17:22:49
   - Channel --- Socket
   - EventLoop --- 控制流、多线程处理、并发;
   - ChannelFuture --- 异步通知

   ### 1.3.1 Channel 接口
   在java网络编程中，其基本构造的是`Socket`，Netty的`Channel`提供的api大大的降低了直接使用`Socket`类的复杂性。netty也提供了许多预定义、专门实现的广泛化的类

- EmbeddedChannel
- LocalServerChannel
- NioDatagramChannel
- NioSctPChannel
- NioSocketChannel

   ### 1.3.2 EventLoop 接口

`EventLoop`是netty的核心抽象，用于处理连接的生命周期所发生的事件，其中关于`Channel` 与`EventLoop`、`Thread`以及`EventLoopGroup`的关系如下

- 一个`EventLoopGroup`包含一个或者多个`EventLoop`
- 一个`EventLoop`在它的生命周期内只会绑定一个`Thread`
- 一个`Channel`在它的生命周期内只注册于一个`EventLoop`
- 一个`EventLoop`可以分配个多个`Channel`
- 所有由`EventLoop`处理的IO事件都将由他专有的`Thread`执行

这样就可以保证一个指定的`Channel`的IO操作都是同一个`Thread`执行，消除了同步的需要。

### 1.3.3 ChannelFuture接口

由于netty中所有的操作都是异步的，因此一个操作可能不会理解返回，我们需要一种方法用于在之后某个时间点确定其操作结果，因此，netty提供了`ChannelFuture`接口，其`addListener()`方法注册一个`ChannelFutureListener`，以便在某个操作完成的时候能够得到通知。

## 1.4 ChannelHandler 和 ChannelPipeline

> 时间 2018年12月18日15:10:38

这两个组件从名字上就可以知道作用。

- `ChannelHandler` 主要用于事件被触发时的回调逻辑。
- `ChannelPipeline`主要用于管理各个`ChannelHandler`的逻辑顺序。



### 1.4.1 ChannelHandler接口

在我们开发人员眼中，`ChannelHandler`接口是最重要的组件了，它充当了所有处理入站和出站数据的应用程序逻辑的容器。由于`ChannelHandler`的方法是由网络事件触发的。所以`ChannelHandler`几乎可以处理任何类型的动作，例如数据从一个类型转换成另一个类型，或者是处理转换过程的抛出的异常。

![ChannelHandler主要结构图](/Users/yukong/Documents/ideaProjects/adolesce/src/main/java/com/yukong/netty/README.assets/image-20181218144747217-5115667.png)

从上图中我们可以了解，顶层接口是`ChannelHandler`，因为IO事件分为输入和输出，因此ChannelHandler又具体的分为`ChannelInboundHandler`和`ChannelOutboundHandler `，分别用于某个阶段输入输出事件的处理。

对于`ChannelHandlerAdapter`、`ChannelInboundHandlerAdapter `、`ChannelOutboundHandlerAdapter`，从名字就可以看出来其作用是适配器，适配器是一种设计模式。设想一个，一个接口可能定义很多抽象方法，如果子类直接实现，必定要全部实现这些方法，使得代码很臃肿。由于接口中定义的有些方法是公共的，还有一些方法可能是子类并不关心的，因此通过适配器类，这些方法提供默认的实现。这样的话，在编程的时候，子类只需要覆写自己感兴趣的方法即可。

这提示我们，在使用netty进行编程的时候，对于输入事件的处理，我们应该继承`ChannelInboundHandlerAdapter`类，而不是直接实现`ChannelInboundHandler`接口；反之对于输出事件，我们应该继承`ChannelOutboundHandlerAdapter`类。

在处理channel的IO事件时，我们通常会分成几个阶段。以读取数据为例，通常我们的处理顺序是:

>  处理半包或者粘包问题-->数据的解码(或者说是反序列化)-->数据的业务处理 

可以看到不同的阶段要执行不同的功能，因此通常我们会编写多个ChannelHandler，来实现不同的功能。而且多个ChannelHandler之间的顺序不能颠倒，例如我们必须先处理粘包解包问题，之后才能进行数据的业务处理。

### 1.4.2 ChannelPipeline接口

netty中通过`ChannelPipeline`接口来保证`ChannelHandler`之间的处理顺序。每当`Channel`被创建时，都会自动创建一个与之关联的`ChannelPipeline`对象。`ChannelHandler`的执行顺序与它被添加的顺序所决定。

例如下面的代码：

```java
ChannelPipeline p =....;
p.addLast p.addLast("1", new InboundHandlerA());
p.addLast("2", new InboundHandlerB());
p.addLast("3", new OutboundHandlerA());
p.addLast("4", new OutboundHandlerB());
p.addLast("5", new InboundOutboundHandlerX());
```

当一个入站事件来了，它的顺序是： 1，2，5

当一个出站事件来了，它的顺序是：5，4，3 (注意输出事件的处理器发挥作用的顺序与定义的顺序是相反的)



### 1.4.3 特殊的ChannelHandler编码器与解码器

当你通过netty发送一个消息的时候，就会发生一次数据转换，出站事件会编码，也就是从字节码转换成其它格式，这种格式通常是java对象。 所以编码器是`ChannelOutboundHandler`对象，同理，接受消息时候，会发生一次编码，所以编码器是`ChannelInboundHandler`对象。

![image-20181218153203687](/Users/yukong/Documents/ideaProjects/adolesce/src/main/java/com/yukong/netty/README.assets/image-20181218153203687-5118323.png)



# 二、netty实现oio、nio

大家知道在jdk中OIO(阻塞传输)与NIO(异步传输)的api截然不同。但是在nio中为所有类型传输提供了一个通用api，它比jdk提供的更加简单灵活，可以无缝切换oio，nio模式。

下面通过jdk实现的nio、nio与netty实现的oio、nio来对比。

## 2.1 jdk使用OIO和NIO

