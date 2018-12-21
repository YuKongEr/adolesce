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

### 2.1.1 jdk OIO

`JdkOioServer.java`

```java
package com.yukong.netty.compare;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: yukong
 * @date: 2018/12/18 15:52
 */
public class JdkOioServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(6666);
        try{
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection form" + clientSocket);
                ExecutorService executorService = Executors.newCachedThreadPool();
                executorService.execute(() -> {
                    try {
                        OutputStream outputStream = clientSocket.getOutputStream();
                        outputStream.write("World\n".getBytes());
                        outputStream.flush();
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

```

`JdkOioClient.java`

```java
package com.yukong.netty.compare;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author: yukong
 * @date: 2018/12/18 16:43
 */
public class JdkOioClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("127.0.0.1", 6666));
        System.out.println("connection server");
        System.out.println("Hello");
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        System.out.println(reader.readLine());
    }

}

```

### 2.1.2 jdk NIO

`JdkNioServer.java`

```java
package com.yukong.netty.compare;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;

/**
 * @author: yukong
 * @date: 2018/12/18 16:52
 */
public class JdkNioServer {



    /*标识数字*/
    private  int flag = 0;
    /*缓冲区大小*/
    private  int BLOCK = 4096;
    /*接受数据缓冲区*/
    private  ByteBuffer sendbuffer = ByteBuffer.allocate(BLOCK);
    /*发送数据缓冲区*/
    private  ByteBuffer receivebuffer = ByteBuffer.allocate(BLOCK);
    private  Selector selector;

    public JdkNioServer(int port) throws IOException {
        // 打开服务器套接字通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 服务器配置为非阻塞
        serverSocketChannel.configureBlocking(false);
        // 检索与此通道关联的服务器套接字
        ServerSocket serverSocket = serverSocketChannel.socket();
        // 进行服务的绑定
        serverSocket.bind(new InetSocketAddress(port));
        // 通过open()方法找到Selector
        selector = Selector.open();
        // 注册到selector，等待连接
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("Server Start----6969:");
    }


    // 监听
    private void listen() throws IOException {
        while (true) {
            // 选择一组键，并且相应的通道已经打开
            selector.select();
            // 返回此选择器的已选择键集。
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                handleKey(selectionKey);
            }
        }
    }

    // 处理请求
    private void handleKey(SelectionKey selectionKey) throws IOException {
        // 接受请求
        ServerSocketChannel server = null;
        SocketChannel client = null;
        String receiveText;
        String sendText;
        int count=0;
        // 测试此键的通道是否已准备好接受新的套接字连接。
        if (selectionKey.isAcceptable()) {
            // 返回为之创建此键的通道。
            server = (ServerSocketChannel) selectionKey.channel();
            // 接受到此通道套接字的连接。
            // 此方法返回的套接字通道（如果有）将处于阻塞模式。
            client = server.accept();
            // 配置为非阻塞
            client.configureBlocking(false);
            // 注册到selector，等待连接
            client.register(selector, SelectionKey.OP_READ);
        } else if (selectionKey.isReadable()) {
            // 返回为之创建此键的通道。
            client = (SocketChannel) selectionKey.channel();
            //将缓冲区清空以备下次读取
            receivebuffer.clear();
            //读取服务器发送来的数据到缓冲区中
            count = client.read(receivebuffer);
            if (count > 0) {
                receiveText = new String( receivebuffer.array(),0,count);
                System.out.println("服务器端接受客户端数据--:"+receiveText);
                client.register(selector, SelectionKey.OP_WRITE);
            }
        } else if (selectionKey.isWritable()) {
            //将缓冲区清空以备下次写入
            sendbuffer.clear();
            // 返回为之创建此键的通道。
            client = (SocketChannel) selectionKey.channel();
            sendText="message from server--" + flag++;
            //向缓冲区中输入数据
            sendbuffer.put(sendText.getBytes());
            //将缓冲区各标志复位,因为向里面put了数据标志被改变要想从中读取数据发向服务器,就要复位
            sendbuffer.flip();
            //输出到通道
            client.write(sendbuffer);
            System.out.println("服务器端向客户端发送数据--："+sendText);
            client.register(selector, SelectionKey.OP_READ);
        }
    }

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        int port = 6969;
        JdkNioServer server = new JdkNioServer(port);
        server.listen();
    }
}

```

`JdkNioClient.java`

```java
package com.yukong.netty.compare;

import com.sun.org.apache.bcel.internal.generic.Select;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author: yukong
 * @date: 2018/12/18 16:52
 */
public class JdkNioClient {

    /*标识数字*/
    private static int flag = 0;
    /*缓冲区大小*/
    private static int BLOCK = 4096;
    /*接受数据缓冲区*/
    private static ByteBuffer sendbuffer = ByteBuffer.allocate(BLOCK);
    /*发送数据缓冲区*/
    private static ByteBuffer receivebuffer = ByteBuffer.allocate(BLOCK);
    /*服务器端地址*/
    private final static InetSocketAddress SERVER_ADDRESS = new InetSocketAddress(
            "localhost", 6969);

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        // 打开socket通道
        SocketChannel socketChannel = SocketChannel.open();
        // 设置为非阻塞方式
        socketChannel.configureBlocking(false);
        // 打开选择器
        Selector selector = Selector.open();
        // 注册连接服务端socket动作
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        // 连接
        socketChannel.connect(SERVER_ADDRESS);
        // 分配缓冲区大小内存

        Set<SelectionKey> selectionKeys;
        Iterator<SelectionKey> iterator;
        SelectionKey selectionKey;
        SocketChannel client;
        String receiveText;
        String sendText;
        int count=0;

        while (true) {
            //选择一组键，其相应的通道已为 I/O 操作准备就绪。
            //此方法执行处于阻塞模式的选择操作。
            selector.select();
            //返回此选择器的已选择键集。
            selectionKeys = selector.selectedKeys();
            //System.out.println(selectionKeys.size());
            iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                selectionKey = iterator.next();
                if (selectionKey.isConnectable()) {
                    System.out.println("client connect");
                    client = (SocketChannel) selectionKey.channel();
                    // 判断此通道上是否正在进行连接操作。
                    // 完成套接字通道的连接过程。
                    if (client.isConnectionPending()) {
                        client.finishConnect();
                        System.out.println("完成连接!");
                        sendbuffer.clear();
                        sendbuffer.put("Hello,Server".getBytes());
                        sendbuffer.flip();
                        client.write(sendbuffer);
                    }
                    client.register(selector, SelectionKey.OP_READ);
                } else if (selectionKey.isReadable()) {
                    client = (SocketChannel) selectionKey.channel();
                    //将缓冲区清空以备下次读取
                    receivebuffer.clear();
                    //读取服务器发送来的数据到缓冲区中
                    count=client.read(receivebuffer);
                    if(count>0){
                        receiveText = new String( receivebuffer.array(),0,count);
                        System.out.println("客户端接受服务器端数据--:"+receiveText);
                        client.register(selector, SelectionKey.OP_WRITE);
                    }

                } else if (selectionKey.isWritable()) {
                    sendbuffer.clear();
                    client = (SocketChannel) selectionKey.channel();
                    sendText = "message from client--" + (flag++);
                    sendbuffer.put(sendText.getBytes());
                    //将缓冲区各标志复位,因为向里面put了数据标志被改变要想从中读取数据发向服务器,就要复位
                    sendbuffer.flip();
                    client.write(sendbuffer);
                    System.out.println("客户端向服务器端发送数据--："+sendText);
                    client.register(selector, SelectionKey.OP_READ);
                }
            }
            selectionKeys.clear();
        }
    }

}

```

### 2.1.3 netty OIO

`NettyOioServer.java`

```java
package com.yukong.netty.compare;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.channel.socket.oio.OioServerSocketChannel;

import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author: yukong
 * @date: 2018/12/19 10:01
 */
public class NettyOioServer {

    public static void main(String[] args) throws InterruptedException {
        final ByteBuf buf = Unpooled.unmodifiableBuffer(Unpooled.copiedBuffer("Hi\n".getBytes()));
        EventLoopGroup group = new OioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(group)
                .channel(OioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(7070))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                ctx.writeAndFlush(buf.duplicate()).addListener(ChannelFutureListener.CLOSE);
                            }
                        });
                    }
                });
        ChannelFuture channelFuture = serverBootstrap.bind().sync();
        channelFuture.channel().closeFuture().sync();
    }

}

```

`NettyOioClient.java`

```java
package com.yukong.netty.compare;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.oio.OioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author: yukong
 * @date: 2018/12/19 10:25
 */
public class NettyOioClient {

    public static void main(String[] args) throws InterruptedException {
        final ByteBuf buf = Unpooled.unmodifiableBuffer(Unpooled.copiedBuffer("Hi\n".getBytes()));
        EventLoopGroup group = new OioEventLoopGroup();
        Bootstrap client = new Bootstrap();
        client.group(group)
                .channel(OioSocketChannel.class)
                .remoteAddress(new InetSocketAddress(7070))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                System.out.println("connect");
                                ctx.writeAndFlush(buf.duplicate()).addListener(ChannelFutureListener.CLOSE);
                            }
                        });
                    }
                });
        ChannelFuture channelFuture = client.connect().sync();
        channelFuture.channel().closeFuture().sync();
    }

}

```

### 2.1.4 netty NIO

`NettyNioServer.java`

```java
package com.yukong.netty.compare;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.oio.OioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author: yukong
 * @date: 2018/12/19 10:01
 */
public class NettyNioServer {

    public static void main(String[] args) throws InterruptedException {
        final ByteBuf buf = Unpooled.unmodifiableBuffer(Unpooled.copiedBuffer("Hi\n".getBytes()));
        EventLoopGroup group = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(group)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(7070))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                ctx.writeAndFlush(buf.duplicate()).addListener(ChannelFutureListener.CLOSE);
                            }
                        });
                    }
                });
        ChannelFuture channelFuture = serverBootstrap.bind().sync();
        channelFuture.channel().closeFuture().sync();
    }

}

```

`NettyNioClient.java`

```java
package com.yukong.netty.compare;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.channel.socket.oio.OioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author: yukong
 * @date: 2018/12/19 10:25
 */
public class NettyNioClient {

    public static void main(String[] args) throws InterruptedException {
        final ByteBuf buf = Unpooled.unmodifiableBuffer(Unpooled.copiedBuffer("Hi\n".getBytes()));
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap client = new Bootstrap();
        client.group(group)
                .channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress(7070))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                System.out.println("connect");
                                ctx.writeAndFlush(buf.duplicate()).addListener(ChannelFutureListener.CLOSE);
                            }
                        });
                    }
                });
        ChannelFuture channelFuture = client.connect().sync();
        channelFuture.channel().closeFuture().sync();
    }

}

```

# 三、 Netty的数据容器

## 3.1 ByteBuf

优点

- 可以被用户自定义的缓冲区类型扩展
- 通过内置的复合缓冲区类型实现了透明的零拷贝
- 容量可以按需增长
- 读写模式切换不需要切换（ByteBuffer需要调用flip()切换）
- 读写使用了不同的索引
- 支持方法链式调用
- 支持引用计数
- 支持池化

实现

