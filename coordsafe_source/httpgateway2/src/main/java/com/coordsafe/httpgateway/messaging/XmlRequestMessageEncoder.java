package com.coordsafe.httpgateway.messaging;

import java.text.SimpleDateFormat;
import org.apache.log4j.Logger;

public class XmlRequestMessageEncoder implements MessageEncoder {
	private static Logger logger = Logger.getLogger("CommServer");

	@Override
	public String encode(CsMessage message) {
		/*Document messageDoc = new Document();
		Element messages = new Element("Messages");
		messageDoc.setDocType(new DocType("Messages"));
		messageDoc.setRootElement(messages);

		Element messageElement = new Element("Message");
			Element header = new Element("Header");
				Element from = new Element("From").addContent(message.getHeader().getFrom());
				Element to = new Element("To");
				Element recipient = new Element("Recipient").addContent(message.getHeader().getTo());
				to.addContent(recipient);
				Element type = new Element("Type").addContent(""+message.getHeader().getType());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Element timeStamp = new Element("TimeStamp").addContent(sdf.format(message.getHeader().getTimeStamp()));
				Element subject = new Element("Subject").addContent(message.getHeader().getSubject());
				Element priority = new Element("Priority").addContent(""+ message.getHeader().getPriority());
			header.addContent(from);
			header.addContent(to);
			header.addContent(type);
			header.addContent(timeStamp);
			header.addContent(subject);
			header.addContent(priority);
			
		messageElement.addContent(header);
		Element body = new Element("Body").addContent("");
		messageElement.addContent(body);
		messages.addContent(messageElement);
		
		XMLOutputter xop = new XMLOutputter();
		xop.getFormat().setEncoding("UTF-8");
		String str = xop.outputString(messageDoc);
		logger.info(str);
		return str;*/
		return null;
		
	}

}

