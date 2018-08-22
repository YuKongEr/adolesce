用于记录我的学习过程
# netty
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