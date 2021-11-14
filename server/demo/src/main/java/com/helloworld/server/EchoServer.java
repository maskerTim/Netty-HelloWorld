package com.helloworld.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public final class EchoServer{

    // port for listening
    static final int PORT = 12000;

    public static void main(String[] args) throws Exception{
        /**
         * Create boss and worker groups. Boss accepts connection from client.
         * Worker handles further commuication through connection.
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup) // Set boss and worker group
                .channel(NioServerSocketChannel.class) // use NIO to accept new connection
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        // TODO Auto-generated method stub
                        ChannelPipeline p = ch.pipeline();
                        /*
                        * Socket/channel communication happens in byte streams. String decoder &
                        * encoder helps conversion between bytes & String.
                        */
                        p.addLast(new StringDecoder());
                        p.addLast(new StringEncoder());
                        p.addLast(new WordCountHandler());

                        // This is our custom server handler which will have logic
                        p.addLast(new EchoServerHandler());
                        
                    }
                });
            
            // Start server
            ChannelFuture f = b.bind(PORT).sync();
            System.out.println("Echo Server started. Ready to accept clients.");

            // Wait until the server socket is closed.
            f.channel().closeFuture().sync();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            // Shut down all event loops to terminate all threads.
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}