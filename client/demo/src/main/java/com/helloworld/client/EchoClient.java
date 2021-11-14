package com.helloworld.client;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class EchoClient {
	static final String HOST = "127.0.0.1";
	static final int PORT = 12000;
	// static String[] files = {"a1", "a2", "a3", "a4"};
	static BufferedReader bufReader = null;
	
 
	public static void main(String[] args) throws Exception {

		InputStreamReader reader = new InputStreamReader(new FileInputStream("/tmp/articles/a1"), "UTF-8");
		bufReader = new BufferedReader(reader);
		/*
		 * Configure the client.
		 */
 
		// Since this is client, it doesn't need boss group. Create single group.
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group) // Set EventLoopGroup to handle all eventsf for client.
                .channel(NioSocketChannel.class)// Use NIO to accept new connections.
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        /*
                            * Socket/channel communication happens in byte streams. String decoder &
                            * encoder helps conversion between bytes & String.
                            */
                        p.addLast(new StringDecoder());
                        p.addLast(new StringEncoder());

                        // This is our custom client handler which will have logic for chat.
                        p.addLast(new EchoClientHandler());

                    }
                });
 
			// Start the client.
			ChannelFuture a4_f = b.connect(HOST, PORT).sync();
            
			// Send the content of article to server
			String line;
			while((line = bufReader.readLine()) != null){
				System.out.println(line);
				Channel channel = a4_f.sync().channel();
				channel.writeAndFlush(line);
				channel.flush();
			}
 
			// Wait until the connection is closed.
			a4_f.channel().closeFuture().sync();
		} finally {
			// Shut down the event loop to terminate all threads.
			group.shutdownGracefully();
		}
	}
}
