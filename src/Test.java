package Commands;

import java.io.IOException;
import java.util.HashMap;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Envelope;

import Model.Like;

public class Test extends Command {

	@SuppressWarnings("unused")
	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		HashMap<String, Object> props = parameters;
		Channel channel = (Channel) props.get("channel");
		JSONParser parser = new JSONParser();
		try {
			JSONObject messageBody = (JSONObject) parser.parse((String) props.get("body"));
			String target_id = ((String) messageBody.get("uri")).replace("/like/", "");
			
			HashMap<String, Object> requestBodyHash = jsonToMap((JSONObject) messageBody.get("body"));
			AMQP.BasicProperties properties = (AMQP.BasicProperties) props.get("properties");
			AMQP.BasicProperties replyProps = (AMQP.BasicProperties) props.get("replyProps");
			Envelope envelope = (Envelope) props.get("envelope");
			HashMap<String, Object> createdMessage = Like.create(requestBodyHash, target_id);
			JSONObject response = jsonFromMap(createdMessage);
			channel.basicPublish("", properties.getReplyTo(), replyProps, response.toString().getBytes("UTF-8"));
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
package Commands;

import java.io.IOException;
import java.util.HashMap;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Envelope;

import Model.Like;

public class Test extends Command {

	@SuppressWarnings("unused")
	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		HashMap<String, Object> props = parameters;
		Channel channel = (Channel) props.get("channel");
		JSONParser parser = new JSONParser();
		try {
			JSONObject messageBody = (JSONObject) parser.parse((String) props.get("body"));
			String target_id = ((String) messageBody.get("uri")).replace("/like/", "");
			
			HashMap<String, Object> requestBodyHash = jsonToMap((JSONObject) messageBody.get("body"));
			AMQP.BasicProperties properties = (AMQP.BasicProperties) props.get("properties");
			AMQP.BasicProperties replyProps = (AMQP.BasicProperties) props.get("replyProps");
			Envelope envelope = (Envelope) props.get("envelope");
			HashMap<String, Object> createdMessage = Like.create(requestBodyHash, target_id);
			JSONObject response = jsonFromMap(createdMessage);
			channel.basicPublish("", properties.getReplyTo(), replyProps, response.toString().getBytes("UTF-8"));
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
package Commands;

import java.io.IOException;
import java.util.HashMap;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Envelope;

import Model.Like;

public class Test extends Command {

	@SuppressWarnings("unused")
	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		HashMap<String, Object> props = parameters;
		Channel channel = (Channel) props.get("channel");
		JSONParser parser = new JSONParser();
		try {
			JSONObject messageBody = (JSONObject) parser.parse((String) props.get("body"));
			String target_id = ((String) messageBody.get("uri")).replace("/like/", "");
			
			HashMap<String, Object> requestBodyHash = jsonToMap((JSONObject) messageBody.get("body"));
			AMQP.BasicProperties properties = (AMQP.BasicProperties) props.get("properties");
			AMQP.BasicProperties replyProps = (AMQP.BasicProperties) props.get("replyProps");
			Envelope envelope = (Envelope) props.get("envelope");
			HashMap<String, Object> createdMessage = Like.create(requestBodyHash, target_id);
			JSONObject response = jsonFromMap(createdMessage);
			channel.basicPublish("", properties.getReplyTo(), replyProps, response.toString().getBytes("UTF-8"));
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
