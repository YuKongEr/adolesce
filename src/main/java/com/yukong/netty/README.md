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

###3.1.1优点

- 可以被用户自定义的缓冲区类型扩展
- 通过内置的复合缓冲区类型实现了透明的零拷贝
- 容量可以按需增长
- 读写模式切换不需要切换（ByteBuffer需要调用flip()切换）
- 读写使用了不同的索引
- 支持方法链式调用
- 支持引用计数
- 支持池化

### 3.1.2实现
内部维护了两个索引:一个用于读取，一个用于写入。
当你从 ByteBuf 读取时， 它的 readerIndex 将会被递增已经被读取的字节数。
同样地，当你写入 ByteBuf 时，它的 writerIndex 也会被递增。

```html
 * <pre>
 *      +-------------------+------------------+------------------+
 *      | discardable bytes |  readable bytes  |  writable bytes  |
 *      |                   |     (CONTENT)    |                  |
 *      +-------------------+------------------+------------------+
 *      |                   |                  |                  |
 *      0      <=      readerIndex   <=   writerIndex    <=    capacity
 * </pre>
```


    名称以 read 或者 write 开头的 ByteBuf 方法，将会推进其对应的索引，而名称以 set 或 者 get 开头的操作则不会。后面的这些方法将在作为一个参数传入的一个相对索引上执行操作。 可以指定 ByteBuf 的最大容量。试图移动写索引(即 writerIndex)超过这个值将会触
    发一个异常1。(默认的限制是 Integer.MAX_VALUE。)

### 3.1.3类型

####1、堆缓冲区

​        最常见的就是堆缓冲区，将数据存储在jvm的堆空间中，这种模式被称为支撑数组(backing array)它能在没有使用池化的情况下提供快速的分配和释放。非常适合于有遗留的数据需要处理的情况。 

```java
ByteBuf heapBuf = .....;
// 检查ByteBuf是否有支撑数组 
if (heapBuf.hasArray()) {
    // 如果有 则获取其支撑数组
    byte[] array = heapBuf.array();
}
```

####2、直接缓冲区

  直接缓冲区是另外一种 ByteBuf 模式。我们期望用于对象创建的内存分配永远都来自于堆 中，但这并不是必须的——NIO 在 JDK 1.4 中引入的 ByteBuffer 类允许 JVM 实现通过本地调 用来分配内存。这主要是为了避免在每次调用本地 I/O 操作之前(或者之后)将缓冲区的内容复 制到一个中间缓冲区(或者从中间缓冲区把内容复制到缓冲区)。 

ByteBuffer的Javadoc1明确指出:“直接缓冲区的内容将驻留在常规的会被垃圾回收的堆 之外。”这也就解释了为何直接缓冲区对于网络数据传输是理想的选择。如果你的数据包含在一 个在堆上分配的缓冲区中，那么事实上，在通过套接字发送它之前，JVM将会在内部把你的缓冲 区复制到一个直接缓冲区中。 

直接缓冲区的主要缺点是，相对于基于堆的缓冲区，它们的分配和释放都较为昂贵。如果你 正在处理遗留代码，你也可能会遇到另外一个缺点:因为数据不是在堆上，所以你不得不进行一 次复制 

```java
 ByteBuf directBuf = ...;
 // 检查 ByteBuf 是否由数 组支撑。如果不是，则 这是一个直接缓冲区
 if (!directBuf.hasArray()) {
    //获取可读 字节数
    int length = directBuf.readableBytes();
    // 分配一个新的数组来保存 具有该长度的字节数据
    byte[] array = new byte[length]; 
    // 将字节复制到该数组
    directBuf.getBytes(directBuf.readerIndex(), array);
 }
```

#### 3、复合缓冲区

第三种也是最后一种模式使用的是复合缓冲区，它为多个 ByteBuf 提供一个聚合视图。在 这里你可以根据需要添加或者删除 ByteBuf 实例，这是一个 JDK 的 ByteBuffer 实现完全缺 失的特性。 

Netty 通过一个 ByteBuf 子类——CompositeByteBuf——实现了这个模式，它提供了一 个将多个缓冲区表示为单个合并缓冲区的虚拟表示 

> CompositeByteBuf中的ByteBuf实例可能同时包含直接内存分配和非直接内存分配。
> 如果其中只有一个实例，那么对 CompositeByteBuf 上的 hasArray()方法的调用将返回该组
> 件上的 hasArray()方法的值;否则它将返回 false。

为了举例说明，让我们考虑一下一个由两部分——头部和主体——组成的将通过 HTTP 协议
传输的消息。这两部分由应用程序的不同模块产生，将会在消息被发送的时候组装。该应用程序
可以选择为多个消息重用相同的消息主体。当这种情况发生时，对于每个消息都将会创建一个新
的头部。

```java
CompositeByteBuf messageBuf = Unpooled.compositeBuffer(); ByteBuf headerBuf = ...; // can be backing or direct 
ByteBuf bodyBuf = ...; // can be backing or direct 
// 将 ByteBuf 实例追加 到 CompositeByteBuf
messageBuf.addComponents(headerBuf, bodyBuf);
//  删除位于索引位置为 0 第一个组件)的 ByteBuf
messageBuf.removeComponent(0); // remove the header
for (ByteBuf buf : messageBuf) {
	System.out.println(buf.toString()); 
}
```

### 3.1.4 字节操作

#### 1、随机访问索引

与java字节数组一样，`ByteBuf`开始的索引是0，结束的索引是`capacity()-1` 

```java
ByteBuf buffer = ...;
for (int i = 0; i < buffer.capacity(); i++) {
        byte b = buffer.getByte(i);
System.out.println((char)b); }
```

需要注意的是，使用索引访问`ByteBuf`的操作并不会改变`writeIndex\readIndex`，如果有需要可以通过`readIndex(index)`和`writeIndex(index)`来手动改变两者。

#### 2、可丢弃字节

通过`discardReadBytes()`丢弃已读数据，扩大可以写数据容量，，但是请注意，这将极有可能会导致内存复制，因为可读字节必须被移动到缓冲区的开始位置。我们建议只在有真正需要的时候才这样做，例如，当内存非常宝贵的时候。



#### 3、可读字节

默认`ByteBuf`的readIndex索引为0，调用read或skip开头的方法都会让readIndex增加已读字节数。

如果被调用的方法需要一个 ByteBuf 参数作为写入的目标，并且没有指定目标索引参数， 那么该目标缓冲区的 writerIndex 也将被增加，例如: 

```java
    readBytes(ByteBuf dest);
```

如果尝试在缓冲区的可读字节数已经耗尽时从中读取数据，那么将会引发一个` IndexOutOfBoundsException。` 我们可以通过`isReadable()`方法来判断是否还有可读的字节。

```java
ByteBuf buf = ...;
while(buf.isReadable()) {
    System.out.println(buffer.readByte());
}
```

#### 4、可写数据

默认`ByteBuf`的writeIndex索引为0，调用write开头的方法都会让writeIndex增加已写数据字节数。

如果被调用的方法需要一个ByteBuf参数作为读取的目标，并且没有指定目标索引的参数，那么该目标缓冲区的readIndex也将被增加，例如：

```java
writeBytes(ByteBuf dest); 
```

如果尝试往目标写入超过目标容量的数据，将会引发一个IndexOutOfBoundException。

```java
ByteBuf buffer = ...;
while (buffer.writableBytes() >= 4) {
	buffer.writeInt(random.nextInt()); 
}
```

#### 5、索引管理

- 可以通过`markReadIndex()`标记readIndex,通过`resetReadIndex()`来复原readIndex，这与`InputStream`的mark与reset方法一致。同理也有`markWriteIndex()`与`resetWriteIndex()`
- 可以通过`readIndex()`与`writeIndex()`来移动索引试图将任何一个索引设置到一个无效的位置都将导致一个 IndexOutOfBoundsException。
- 可以通过调用`clear()`方法来清楚`readIndex`与`writeIndex`使其都为0，但是这并不会清楚内容。

#### 6、查找操作

在`ByteBuf`中有多种可以用来确定指定值的索引的方法。最简单的是使用indexOf()方法。 较复杂的查找可以通过那些需要一个`ByteBufProcessor`作为参数的方法达成。这个接口只定 义了一个方法: 

```
    boolean process(byte value)
```

它将检查输入值是否是正在查找的值。 ByteBufProcessor针对一些常见的值定义了许多便利的方法。假设你的应用程序需要和 所谓的包含有以NULL结尾的内容的Flash套接字集成。调用 forEachByte(ByteBufProcessor.FIND_NUL) 

将简单高效地消费该 Flash 数据，因为在处理期间只会执行较少的边界检查。 

使用 ByteBufProcessor 来寻找\r

```java
ByteBuf buffer = ...;
int index = buffer.forEachByte(ByteBufProcessor.FIND_CR);
```

#### 7、派生缓冲区

派生缓冲区为 ByteBuf 提供了以专门的方式来呈现其内容的视图。这类视图是通过以下方 法被创建的: 

- duplicate();

- slice(); 
- slice(int, int)
- Unpooled.unmodifiableBuffer(...);
- order(ByteOrder);
- readSlice(int)。

这些方法都会返回一个新的`ByteBuf`实例，它有自己的`readIndex writeIndex` 但是需要注意的是它们共享同一个字节数组，那么意味着它的内容，它对应的源实例也会被修改。

> ByteBuf 复制 如果需要一个现有缓冲区的真实副本，请使用 copy()或者 copy(int, int)方
> 法。不同于派生缓冲区，由这个调用所返回的 ByteBuf 拥有独立的数据副本。

**切片**

```java

Charset utf8 = Charset.forName("UTF-8");

ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8); 
//  创建该 ByteBuf 从索 引 0 开始到索引 15 结束的一个新切片
ByteBuf sliced = buf.slice(0, 15); 
//  将打印 “Netty in Action”
System.out.println(sliced.toString(utf8));
// 修改索引0处的字节
buf.setByte(0, (byte)'J');
//将会成功，因为数据是共享的，对其中 一个所做的更改对另外一个也是可见的
assert buf.getByte(0) == sliced.getByte(0);
```



**副本**

```java
Charset utf8 = Charset.forName("UTF-8");
ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8); 
// 创建该 ByteBuf 从索 引 0 开始到索引 15 结束的分段的副本
ByteBuf copy = buf.copy(0, 15); 
//  将打印 “Netty in Action”
System.out.println(copy.toString(utf8));
// 修改索引0处的字节
buf.setByte(0, (byte) 'J'); 
// 将会成功，因为数据不是共享的
assert buf.getByte(0) != copy.getByte(0);
```

除了修改原始 ByteBuf 的切片或者副本的效果以外，这两种场景是相同的。只要有可能，
使用 slice()方法来避免复制内存的开销。



#### 8、读\写操作

前面我们提到，读写操作有两类。

- `get()`和`set()`操作，从指定索引开始，不会改变索引值。
- `read()`和`write()`操作，从指定所以开始，并且会根据已经访问过的字节数进行调整。

![image-20181227113428425](/Users/yukong/Documents/ideaProjects/adolesce/src/main/java/com/yukong/netty/README.assets/image-20181227113428425-5881668.png)



![image-20181227113446947](/Users/yukong/Documents/ideaProjects/adolesce/src/main/java/com/yukong/netty/README.assets/image-20181227113446947-5881687.png)

![image-20181227113602971](/Users/yukong/Documents/ideaProjects/adolesce/src/main/java/com/yukong/netty/README.assets/image-20181227113602971-5881763.png)

![image-20181227113613455](/Users/yukong/Documents/ideaProjects/adolesce/src/main/java/com/yukong/netty/README.assets/image-20181227113613455-5881773.png)



![image-20181227113642382](/Users/yukong/Documents/ideaProjects/adolesce/src/main/java/com/yukong/netty/README.assets/image-20181227113642382-5881802.png)

![image-20181227113650274](/Users/yukong/Documents/ideaProjects/adolesce/src/main/java/com/yukong/netty/README.assets/image-20181227113650274-5881810.png)

###  3.1.5 ByteBuf 分配 

#### 1、按需分配

为了降低分配和释放内存的消耗，`Netty`通过`ByteBufAllocator`实现了`ByteBuf`的池化，它可以用来分配我们所描述过的任意类型的 ByteBuf 实例。使用池化是特定于应用程序的决定，其并不会以任何方式改变 ByteBuf API(的语义)。

![image-20181227114924403](/Users/yukong/Documents/ideaProjects/adolesce/src/main/java/com/yukong/netty/README.assets/image-20181227114924403-5882564.png)

我们可以通过`Channel`或者绑定到`ChannelHandler`的`ChannelHandlerContext`获取到`ByteBufAllocator`的实例的引用。

```java
Channel channel = ...;
ByteBufAllocator allocator = channel.alloc(); ....
ChannelHandlerContext ctx = ...; 
ByteBufAllocator allocator2 = ctx.alloc();
```



在`netty`中有两种`ByteBufAllocator`的实现

- `PooledByteBufAllocator` 提供池化的`ByteBuf`实例对象以提供性能与减少内存碎片。
- `UnpooledByteBufAllocator` 提供不池化的`ByteBuf`对象，每次调用都会返回一个新的实例。

#### 2、Unpooled 缓冲区

可能某些情况下，你未能获取一个到 ByteBufAllocator 的引用。对于这种情况，Netty 提
供了一个简单的称为 Unpooled 的工具类，它提供了静态的辅助方法来创建未池化的 ByteBuf
实例。

![image-20181227134933861](/Users/yukong/Documents/ideaProjects/adolesce/src/main/java/com/yukong/netty/README.assets/image-20181227134933861-5889773.png)

