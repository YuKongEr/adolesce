package com.yukong.netty.channel;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

/**
 * @author: yukong
 * @date: 2018/12/27 14:33
 */
public class Server {

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup bossLoopGroup = new NioEventLoopGroup();
        NioEventLoopGroup clientLoopGroup = new NioEventLoopGroup();


        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossLoopGroup,clientLoopGroup)
                .channel(NioServerSocketChannel.class)
                .localAddress(6767)
                .childHandler((new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        pipeline.addLast("firstIn", new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf buf = (ByteBuf) msg;
                                System.out.println("firstIn " + buf.toString(CharsetUtil.UTF_8));
                                ctx.fireChannelRead(msg);
                            }
                        });
                        pipeline.addLast("secondIn", new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf buf = (ByteBuf) msg;
                                String res = buf.toString(CharsetUtil.UTF_8);
                                System.out.println("secondIn " + res);
                                if(!"channelHandlerContext msg".equalsIgnoreCase(res) &&  !"channelPipeline Msg".equalsIgnoreCase(res)) {
                                    ctx.fireChannelRead(Unpooled.copiedBuffer("channelHandlerContext msg".getBytes()));
                                    ctx.pipeline().fireChannelRead(Unpooled.copiedBuffer("channelPipeline Msg".getBytes()));
                                }
                            }
                        });
                        pipeline.addLast("firstOut", new ChannelOutboundHandlerAdapter(){
                            @Override
                            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                System.out.println("firstOut " + msg.toString());
                                // ctx.writeAndFlush(Unpooled.copiedBuffer(msg.toString(), CharsetUtil.UTF_8));
                                 ctx.writeAndFlush(msg.toString());

                            }
                        });
                        pipeline.addLast("thirdIn", new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf buf = (ByteBuf) msg;
                                System.out.println("thirdIn " + buf.toString(CharsetUtil.UTF_8));
                                ctx.channel().writeAndFlush("channel ye i am netty");
                                ctx.writeAndFlush("channelContext ye i am netty");
                            }
                        });
                        pipeline.addLast("secondOut", new ChannelOutboundHandlerAdapter(){
                            @Override
                            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                System.out.println("secondOut " + msg.toString());
                                ctx.write(msg);
                            }
                        });
                    }
                }));
        // 异步绑定服务器，并且调用sync阻塞等待直到绑定完成
        ChannelFuture channelFuture = serverBootstrap.bind().sync();
        // 获取Channel的closeFuture 并且阻塞到当前线程完成
        channelFuture.channel().closeFuture().sync();

    }


}
