package com.xnx3.wangmarket.weixin.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.xnx3.wangmarket.admin.util.AliyunLog;
import com.xnx3.wangmarket.weixin.autoReplyPluginManage.AutoReplyPluginManage;
import com.xnx3.j2ee.controller.BaseController;
import com.xnx3.weixin.bean.MessageReceive;

/**
 * 网市场微信公众号使用的
 * @author 管雷鸣
 */
@Controller
@RequestMapping("/")
public class WeiXinController extends BaseController{

	/**
	 * 微信服务器推送消息，推送到此接口
	 * @throws IOException 
	 * @throws DocumentException 
	 */
	@RequestMapping("weixin${url.suffix}")
	public void weixin(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException{
		MessageReceive message = com.xnx3.wangmarket.weixin.Global.getWeiXinUtil().receiveMessage(request);
		
		AliyunLog.addActionLog("weixinMessage", message.getReceiveBody());
		
		if(request.getQueryString() == null){
			//return "<html><head><meta charset=\"utf-8\"></head><body>浏览器中直接输入网址访问是不行的！这个接口是接收微信服务器返回数据的，得通过微信服务器返回得数据测试才行</body></html>";
		}
		
//		if(message.getMsgType() != null && message.getMsgType().equals(MessageReceive.MSGTYPE_EVENT)){
//			if(message.getEvent() != null && message.getEvent().equals(MessageReceive.EVENT_SUBSCRIBE)){
//				//关注，订阅
//				getWeiXinUtil().autoReplyText(response, message, "欢迎关注“网市场”订阅号，你在使用网市场建站的过程中，遇到什么问题，可随时向我们公众号发送信息进行求助");
//			    return;
//			}
//		}
//		
//		String title = "";
//		if(message.getContent() != null){
//			if(message.getContent().length() < 10){
//				title = message.getContent();
//			}else{
//				title = message.getContent().substring(0, 10);
//			}
//		}else{
//			title = message.getMsgType();
//		}
		
//		
//		//判断是否是用户进行咨询，如发送文字、图片、音频、视频等。若是咨询，则发送邮件提醒，并自动回复用户信息
//		if(message.getMsgType() != null && (message.getMsgType().equals(MessageReceive.MSGTYPE_IMAGE) || message.getMsgType().equals(MessageReceive.MSGTYPE_SHORT_VIDEO) || message.getMsgType().equals(MessageReceive.MSGTYPE_TEXT) || message.getMsgType().equals(MessageReceive.MSGTYPE_VIDEO) || message.getMsgType().equals(MessageReceive.MSGTYPE_VIOCE))){
//			if(message.getContent() != null && message.getContent().equals("要网站")){
//				/**
//				 * 程序可自动处理的
//				 */
//				getWeiXinUtil().autoReplyText(response, message, "在线开通网站：\n http://wang.market/regByPhone.do?inviteid=50 \n另外有多种方式加盟代理或合作，可回复“合作”查看");
//			}else if (message.getContent() != null && message.getContent().equals("合作")) {
//				getWeiXinUtil().autoReplyText(response, message, "详情请看：\n http://www.wang.market/index.html#join \n 另外，如果您感觉其中没有合适的，您可以回复您希望的方式，只要能对我方起到好的作用，我们期待与您的合作！");
//			}else{
//				/**
//				 * 程序不能自动处理，需要人工介入的
//				 */
//				
//				//判断当前咨询是否是在5分钟内。若是在5分钟内对话的，则不发送邮件，也不向客户回复文字
//				Integer lasttime = cacheMap.get(message.getFromUserName());
//				int currentTime = DateUtil.timeForUnix10();
//				//将此次咨询进行缓存
//				cacheMap.put(message.getFromUserName(), currentTime);
//				
//				if(lasttime != null && lasttime + 300 > currentTime){
//					//不用发送通知
//					new MessageReply(message.getFromUserName(), message.getToUserName()).reply(response, "success");
//				}else{
//					//用户第一次咨询，或者超过5分钟时间进行的对话,需要发送邮件通知，并且回复用户提示。
//					List<String> list = getNoticeEmailList();
//					for (int i = 0; i < list.size(); i++) {
//						MailUtil.sendMail(list.get(i), "网市场公众号有人咨询:"+title, message.getContent());
//					}
//					
//					getWeiXinUtil().autoReplyText(response, message, "您的信息已收到，我们将有专人负责处理，请耐心等待。");
//				}
//				
//			}
//			
//		}
//		
		
		/**** 针对微信自动回复插件 ****/
		try {
			AutoReplyPluginManage.autoReply(request, response, message);
		} catch (InstantiationException | IllegalAccessException
				| NoSuchMethodException | SecurityException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 微信服务器配置，过验证
	 */
//	@RequestMapping("weixin")
//	public void verify(HttpServletRequest request, HttpServletResponse response){
//		getWeiXinUtil().joinVerify(request, response);
//	}
	

}
