package cn.zb.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

public class MailHelper {
	/**
	 * 简单的发邮件方式 邮件内容只有标题和邮件内容 支持单个个用户发送
	 *
	 * @param host
	 *            邮件服务器地址
	 * @param username
	 *            连接邮件服务器的用户名称
	 * @param password
	 *            连接邮件服务器的用户密码
	 * @param subject
	 *            邮件的主题
	 * @param contents
	 *            邮件的内容
	 * @param toEmailAddress
	 *            收件人的邮件地址
	 * @param fromEmailAddress
	 *            发件人的邮件地址
	 * @throws EmailException
	 */
	public static void sendSimpleEmail(String host, String username, String password, String subject, String contents,
			String toEmailAddress, String fromEmailAddress) throws EmailException {
		SimpleEmail email = new SimpleEmail();
		email.setHostName(host);
		email.setAuthentication(username, password);
		email.addTo(toEmailAddress);
		email.setFrom(fromEmailAddress, fromEmailAddress);
		email.setSubject(subject);
		email.setContent((Object) contents, "text/plain;charset=GBK");
		email.send();
	}

	/**
	 * 简单的发邮件方式 邮件内容只有标题和邮件内容 支持多个用户批量发送
	 *
	 * @param host
	 *            邮件服务器地址
	 * @param username
	 *            连接邮件服务器的用户名称
	 * @param password
	 *            连接邮件服务器的用户密码
	 * @param subject
	 *            邮件的主题
	 * @param contents
	 *            邮件的内容
	 * @param toEmailAddress
	 *            收件人的邮件地址
	 * @param fromEmailAddress
	 *            发件人的邮件地址
	 * @throws EmailException
	 */
	public static void sendSimpleEmail(String host, String username, String password, String subject, String contents,
			String[] toEmailAddress, String fromEmailAddress) throws EmailException {
		SimpleEmail email = new SimpleEmail();
		email.setHostName(host);
		email.setAuthentication(username, password);
		// 发送给多个人
		for (int i = 0; i < toEmailAddress.length; i++) {
			email.addTo(toEmailAddress[i], toEmailAddress[i]);
		}
		email.setFrom(fromEmailAddress, fromEmailAddress);
		email.setSubject(subject);
		email.setContent((Object) contents, "text/plain;charset=GBK");
		email.send();
	}

	/**
	 * 发送带附件的邮件方式 邮件内容有标题和邮件内容和附件，附件可以是本地机器上的文本，也可以是web上的一个URL 文件，
	 * 当为web上的一个URL文件时，此方法可以将WEB中的URL文件先下载到本地，再发送给收入用户
	 *
	 * @param host
	 *            邮件服务器地址
	 * @param username
	 *            连接邮件服务器的用户名称
	 * @param password
	 *            连接邮件服务器的用户密码
	 * @param subject
	 *            邮件的主题
	 * @param contents
	 *            邮件的内容
	 * @param toEmailAddress
	 *            收件人的邮件地址
	 * @param fromEmailAddress
	 *            发件人的邮件地址
	 * @param multiPaths
	 *            附件文件数组
	 * @throws EmailException
	 */

	public static void sendMultiPartEmail(String host, String username, String password, String subject,
			String contents, String toEmailAddress, String fromEmailAddress, String[] multiPaths)
			throws MalformedURLException, EmailException {
		List<EmailAttachment> attachmentList = new ArrayList<EmailAttachment>();
		if (multiPaths != null) {
			for (int i = 0; i < multiPaths.length; i++) {
				EmailAttachment attachment = new EmailAttachment();
				if (multiPaths[i].indexOf("http") == -1) // 判断当前这个文件路径是否在本地
															// 如果是：setPath 否则
															// setURL;
				{
					attachment.setPath(multiPaths[i]);
				} else {
					attachment.setURL(new URL(multiPaths[i]));
				}
				attachment.setDisposition(EmailAttachment.ATTACHMENT);
				attachment.setDescription("");
				attachmentList.add(attachment);
			}
		}

		// 发送邮件信息
		MultiPartEmail email = new MultiPartEmail();
		email.setHostName(host);
		email.setAuthentication(username, password);
		email.addTo(toEmailAddress);
		email.setFrom(fromEmailAddress, fromEmailAddress);
		email.setSubject(subject);
		email.setMsg(contents); // 注意这个不要使用setContent这个方法 setMsg不会出现乱码
		for (int i = 0; i < attachmentList.size(); i++) // 添加多个附件
		{
			email.attach((EmailAttachment) attachmentList.get(i));
		}
		email.send();
	}

	/**
	 * 发送带附件的邮件方式 邮件内容有标题和邮件内容和附件，附件可以是本地机器上的文本，也可以是web上的一个URL 文件，
	 * 当为web上的一个URL文件时，此方法可以将WEB中的URL文件先下载到本地，再发送给收入用户
	 *
	 * @param host
	 *            邮件服务器地址
	 * @param username
	 *            连接邮件服务器的用户名称
	 * @param password
	 *            连接邮件服务器的用户密码
	 * @param subject
	 *            邮件的主题
	 * @param contents
	 *            邮件的内容
	 * @param toEmailAddress
	 *            收件人的邮件地址数组
	 * @param fromEmailAddress
	 *            发件人的邮件地址
	 * @param multiPaths
	 *            附件文件数组
	 * @throws EmailException
	 */

	public static void sendMultiPartEmail(String host, String username, String password, String subject,
			String contents, String[] toEmailAddress, String fromEmailAddress, String[] multiPaths)
			throws MalformedURLException, EmailException {
		List<EmailAttachment> attachmentList = new ArrayList<EmailAttachment>();
		if (multiPaths != null) {
			for (int i = 0; i < multiPaths.length; i++) {
				EmailAttachment attachment = new EmailAttachment();
				if (multiPaths[i].indexOf("http") == -1) // 判断当前这个文件路径是否在本地
															// 如果是：setPath 否则
															// setURL;
				{
					attachment.setPath(multiPaths[i]);
				} else {
					attachment.setURL(new URL(multiPaths[i]));
				}
				attachment.setDisposition(EmailAttachment.ATTACHMENT);
				attachment.setDescription("");
				attachmentList.add(attachment);
			}
		}

		// 发送邮件信息
		MultiPartEmail email = new MultiPartEmail();
		email.setHostName(host);
		email.setAuthentication(username, password);
		// 发送给多个人
		for (int i = 0; i < toEmailAddress.length; i++) {
			email.addTo(toEmailAddress[i], toEmailAddress[i]);
		}
		email.setFrom(fromEmailAddress, fromEmailAddress);
		email.setSubject(subject);
		email.setMsg(contents); // 注意这个不要使用setContent这个方法 setMsg不会出现乱码
		for (int i = 0; i < attachmentList.size(); i++) // 添加多个附件
		{
			email.attach((EmailAttachment) attachmentList.get(i));
		}
		email.send();
	}

	public static void sendHTMLEmail(String host, String username, String password, String subject, String htmlContents,
			String[] toEmailAddress, String fromEmailAddress) throws Exception {
		Email email = new HtmlEmail();
		email.setHostName(host);
		email.setAuthentication(username, password);
		// 发送给多个人
		for (int i = 0; i < toEmailAddress.length; i++) {
			email.addTo(toEmailAddress[i], toEmailAddress[i]);
		}
		email.setFrom(fromEmailAddress, fromEmailAddress);
		email.setSubject(subject);
		email.setContent((Object) htmlContents, "text/html;charset=utf-8");
		email.setSslSmtpPort("465");

		email.send();
	}

	public static void sendSSLHTMLEmail(String host, final String username, final String password, String subject,
			String htmlContents, String[] toEmailAddress, String fromEmailAddress) throws AddressException, MessagingException {
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", host);
		props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		 Message msg = new MimeMessage(session);
		 msg.setFrom(new InternetAddress(username));
		 Address[] addresses=new Address[toEmailAddress.length];
		 for(int i=0;i<toEmailAddress.length;i++) {
			 addresses[i]=new InternetAddress(toEmailAddress[i]);
		 }
		 msg.setSubject(subject);
		 msg.setContent(htmlContents, "text/html;charset=utf-8");
		 Transport.send(msg, addresses);
	}

	/*public static void main(String[] args) throws Exception {
		try {
			String htmlContent = " \r\n"
					+ " <img src=\"http://dingteam-alibabagroup.oss-cn-hangzhou.aliyuncs.com/pics/news/cn/p180618_1_t.jpg?x-oss-process=image/resize,l_600\"> \r\n"
					+ "\r\n" + "<div class=\"caption\">\r\n"
					+ " 从左至右：中国驻马来西亚大使白天、阿里巴巴集团董事局主席及创始人马云、马来西亚财政部长林冠英、马来西亚通信与多媒体部长哥宾星\r\n" + "</div>\r\n" + "\r\n"
					+ " <img src=\"http://dingteam-alibabagroup.oss-cn-hangzhou.aliyuncs.com/pics/news/cn/p180618_2_t.jpg?x-oss-process=image/resize,l_600\"> \r\n"
					+ "\r\n" + "<div class=\"caption\">\r\n"
					+ " 从左至右：中国驻马来西亚大使白天、阿里巴巴集团董事局主席及创始人马云、马来西亚财政部长林冠英、马来西亚通信与多媒体部长哥宾星，主持阿里巴巴集团办公室开业\r\n" + "</div>\r\n"
					+ "<div class=\"image\"> \r\n"
					+ " <img src=\"http://dingteam-alibabagroup.oss-cn-hangzhou.aliyuncs.com/pics/news/cn/p180618_3_t.jpg?x-oss-process=image/resize,l_600\"> \r\n"
					+ "</div>\r\n" + "<div class=\"caption\">\r\n" + " 阿里巴巴全球化业务总裁（右）赵颖与MDEC首席执行官拿督雅斯敏（左）推出马来西亚周\r\n"
					+ "</div>\r\n"
					+ "<p><b>吉隆坡，2018年6月18日</b> — 阿里巴巴集团今日在马来西亚设立国家办公室，开启阿里巴巴在马来西亚深化战略合作的新篇章。马来西亚是中国以外，第一个世界电子贸易平台（eWTP）的eHub（数字中枢）所在国。</p>\r\n"
					+ "<p>阿里巴巴新成立的马来西亚办公室，位于吉隆坡市中心Vertical Bangsar South。此举标志着，阿里巴巴将继续推动大马的技术发展，支持大马中小企和年轻创业者，为他们提供平台以促进对新市场出口，并将继续提供数字经济培训，帮助中小业和年轻人数字创新，捕捉贸易机会。</p>\r\n"
					+ "<p>阿里巴巴集团董事局主席及创始人马云在吉隆坡阿里巴巴办公室开幕式上表示：“当我们与马来西亚以及其他国家合作时，阿里巴巴着重三个方面 – 与当地合作伙伴推动数字化，帮助小企业和年轻人走向全球化。过去30年来，只有大公司从全球化中受益，想象一下，如果我们能够支持全球6000万小企业，这是阿里巴巴对一个包容和可持续增长的经济体的愿景。”</p>\r\n"
					+ "<p>“与马来西亚政府密切合作，我们将尽可能的支持小企业和年轻人，让他们使用科技，成为当地的佼佼者，并从全球化中受益。这是我们在马来西亚故事的开始，我相信陪我们在这的故事很长，让我们一起编写新篇章。”他补充道。</p>\r\n"
					+ "<p>作为本地“一站式解决方案中心”，阿里巴巴办公室将继续与当地合作伙伴协作，帮助马来西亚中小企掌握全球跨境贸易商机，同时透过云计算服务支持大马的科技创新。</p>\r\n"
					+ "<p>“我想祝贺阿里巴巴在马来西亚开设国家办事处。我们认为这是中马友谊的一个辉煌典范，基于互相尊重和互利，这对两国政府，企业和人民的合作都有好处。我们期待着这种伙伴关系给马来西亚中小企业带来的机会，我相信eWTP将与数字自由贸易区（DFTZ）一起增进更多马来西亚中小企业参与电子商务并增加对亚洲乃至全球其他国家和地区的出口贸易，”马来西亚财政部长林冠英在仪式上对客人说。</p>\r\n"
					+ "<p>“几个世纪以来，中国和马来西亚之间一直保持着深厚的友谊和繁荣的贸易。有关我们双边贸易的强劲数据证明了我们在经济关系上的优势。阿里巴巴马来西亚办事处的成立将是推动中马两国互利合作的另一举措，”中国驻马来西亚大使白天在仪式上说。</p>\r\n"
					+ "<p>去年11月，阿里巴巴成功在马来西亚落地eWTP计划，成立首个海外eHub。这一eWTP试点重点在于帮助大马中小企出口，并且搭建中小企参与全球贸易所需的基础设施，包括电子商务、物流、云计算、移动支付以及科技人才培训等。</p>\r\n"
					+ "<p>eWTP落地后，阿里巴巴在马来西亚推进了多项举措，包括首个区域性的物流枢纽在吉隆坡国际机场航空城（KLIA Aeropolis）中的数字自贸区中心（DFTZ Park），以及马来西亚首个国际公共云平台—阿里云数据中心等，旨在为当地中小企业建立包容且创新的全球贸易基础设施。</p>\r\n"
					+ "<p><b>宣布将在中国推出“马来西亚周”</b></p>\r\n"
					+ "<p>在马来西亚办公室开幕典礼上，阿里巴巴也宣布即将在中国推出“马来西亚周”，这是一项特别的线上推广计划，旨在吸引中国消费者在2018年7月6日至12日期间购买大马商品。该活动将在阿里巴巴旗下主要平台展示一系列“必看”、“必吃”以及“必须体验”的马来西亚产品和旅游业。</p>\r\n"
					+ "<p>马云表示：“马来西亚周是阿里巴巴首次为一个国家推出整整一周的活动。这标志着我们致力于在中国引进和推广马来西亚产品、服务、文化和旅游。 我们希望继续举办这样的长期促销活动，以使马来西亚当地企业能够扩大向中国和全球其他地区的出口。”</p>\r\n"
					+ "<p>在活动期间，将有超过50个马来西亚品牌和一系列的产品类目将被推广，在此期间阿里巴巴集团也会推出一系列令人振奋的促销活动。</p>\r\n"
					+ "<p>‘马来西亚周’是阿里巴巴集团和马来西亚过去一年进行讨论的丰硕结果。阿里巴巴支持大马数字经济发展的其他里程碑包括：</p>\r\n"
					+ "<p style=\"margin-bottom:2px;\"><b>物流</b><br> </p>\r\n" + "<p></p>\r\n"
					+ "<p style=\"margin-bottom:2px;\"><b>云计算</b><br> </p>\r\n" + "<p></p>\r\n"
					+ "<p style=\"margin-bottom:2px;\"><b>培训</b><br> </p>\r\n" + "<p></p>\r\n"
					+ "<p style=\"margin-bottom:2px;\"><b>电子商务</b><br> </p>\r\n" + "<p></p>\r\n"
					+ "<p><b>关于阿里巴巴集团</b><br> 阿里巴巴集团的使命是让天下没有难做的生意。集团旨在构建未来的商务基础设施，其愿景是让客户相会、工作和生活在阿里巴巴，并持续发展最少102年。</p>";
			sendSSLHTMLEmail("smtp.163.com", "chenj3807@163.com", "chenjun0322", "subject", htmlContent,
					new String[] { "76765168@qq.com" }, "chenj3807@163.com");

			System.out.println("sucess");

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getCause());
			e.printStackTrace();
		}
	}*/

}
