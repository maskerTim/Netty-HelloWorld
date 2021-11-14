package com.helloworld.server;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Server side handler
 */
public class EchoServerHandler extends SimpleChannelInboundHandler<Integer> {

    // List of connected client channels.
	//static final List<Pair<Channel, String>> channels = new ArrayList<Pair<Channel, String>>();
	static final List<Channel> channels = new ArrayList<Channel>();

    /*
	 * Whenever client connects to server through channel, add his channel to the
	 * list of channels.
	 */
    @Override
	public void channelActive(final ChannelHandlerContext ctx) {
		System.out.println("Client accepts- "+ctx);
		channels.add(ctx.channel());
	}

    /*
	 * When a message is received from client, send that message to all channels.
	 * For the sake of simplicity, currently we will send received chat message to
	 * all clients instead of one specific client. This code has scope to improve to
	 * send message to specific client as per senders choice.
	 */
	@Override
	public void channelRead0(ChannelHandlerContext ctx, Integer msg) throws Exception {
		System.out.println("Server received the word count - " + msg);
		for (Channel c : channels) {
			c.writeAndFlush("word count -> " + msg + '\n');
		}
	}

    /*
	 * In case of exception, close channel. One may chose to custom handle exception
	 * & have alternative logical flows.
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		System.out.println("Closing connection for client - " + ctx);
		ctx.close();
	}
}
