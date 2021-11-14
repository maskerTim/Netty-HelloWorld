package com.helloworld.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class WordCountHandler extends SimpleChannelInboundHandler<String> {

    /**
     * Calculate the word count in an article from client
     */

    private int accumulated_count = 0;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        // TODO Auto-generated method stub
        // split the article by white space
        String[] words = msg.split("\\s|\\n");
        System.out.println("passed message:"+msg);
        // make word clean
        for(int i=0; i<words.length; i++){
            words[i] = words[i].replaceAll("\\W","");
        }
        // System.out.println(words.length);
        accumulated_count += words.length;
        // pass by next handler
        ctx.fireChannelRead(accumulated_count);
    }
    
}
