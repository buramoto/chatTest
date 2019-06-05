package NettyChat.netty_chat;

import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

public class ChatServerHandler extends SimpleChannelInboundHandler<String> {
	
	private static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
	@Override
	protected void channelRead0(ChannelHandlerContext arg0, String message) throws Exception {
		// TODO Auto-generated method stub
		Channel incoming = arg0.channel();
		for(Channel channel: channels) {
			if(channel != incoming) {
				channel.writeAndFlush("[" + incoming.remoteAddress() + "]" + message + "\n");
			}
			
		}
	}
	//Placeholder
	@Override
	public void handlerAdded(ChannelHandlerContext arg0) throws Exception {
		// TODO Auto-generated method stub
		Channel incoming = arg0.channel();
		for(Channel channel : channels) {
			channel.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + "has joined. \n");
		}
		channels.add(arg0.channel());
	}
	
	@Override
	public void handlerRemoved(ChannelHandlerContext arg0) throws Exception {
		// TODO Auto-generated method stub
		Channel incoming = arg0.channel();
		for(Channel channel : channels) {
			channel.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + "has left. \n");
		}
		channels.remove(arg0.channel());
	}
	
	
}
