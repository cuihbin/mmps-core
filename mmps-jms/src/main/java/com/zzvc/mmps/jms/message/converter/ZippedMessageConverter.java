package com.zzvc.mmps.jms.message.converter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

/**
 * Message converter that zip the object being send 
 * @author CHB
 *
 */
public class ZippedMessageConverter implements MessageConverter {

	@Override
	public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
		ByteArrayOutputStream bout = new ByteArrayOutputStream(); 
		try {
			GZIPOutputStream gzipout = new GZIPOutputStream(bout);
			ObjectOutputStream oout = new ObjectOutputStream(gzipout);
			oout.writeObject(object);
			oout.flush();
			gzipout.finish();
		} catch (IOException e) {
			throw new MessageConversionException("Cannot convert message object.", e);
		}
		
		BytesMessage message = session.createBytesMessage();
		message.writeBytes(bout.toByteArray());
		return message;
	}

	@Override
	public Object fromMessage(Message message) throws JMSException, MessageConversionException {
		Object object = null;
		if (message instanceof BytesMessage) {
			try {
				BytesMessage bytesMessage = (BytesMessage) message;
				byte[] buff = new byte[(int) bytesMessage.getBodyLength()];
				bytesMessage.readBytes(buff);
				ByteArrayInputStream bin = new ByteArrayInputStream(buff);
				GZIPInputStream gzipin = new GZIPInputStream(bin);
				ObjectInputStream oin = new ObjectInputStream(gzipin);
				object = oin.readObject();
			} catch (IOException e) {
				throw new MessageConversionException("Cannot convert message object.", e);
			} catch (ClassNotFoundException e) {
				throw new MessageConversionException("Cannot convert message object.", e);
			}
		}
		return object;
	}
}
