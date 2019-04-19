package Commands;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Envelope;
import Model.Like;

public class RetrieveLike extends Command {

	@SuppressWarnings("unused")
	@Override
    public void execute() {

        HashMap < String, Object > props = parameters;
        Channel channel = (Channel) props.get("channel");
        JSONParser parser = new JSONParser();

        try {
			JSONObject messageBody = (JSONObject) parser.parse((String) props.get("body"));
			String uri = (messageBody).get("uri").toString();
			
			String target_id = "", type = "";
			if(uri.contains("?type=")) {
				target_id = StringUtils.substringBetween(uri, "/like/", "?type=");
				type = ((JSONObject) (messageBody).get("parameters")).get("type").toString();
			}else {
				target_id = StringUtils.removeStart(uri, "/like/");
			}

            AMQP.BasicProperties properties = (AMQP.BasicProperties) props.get("properties");
            AMQP.BasicProperties replyProps = (AMQP.BasicProperties) props.get("replyProps");
            Envelope envelope = (Envelope) props.get("envelope");
			
			ArrayList<HashMap<String, Object>> likes = Like.get(target_id);
            JSONObject response = Command.jsonFromArray(likes, "likes");
            channel.basicPublish("", properties.getReplyTo(), replyProps, response.toString().getBytes("UTF-8"));
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
	public void updateHashMap() throws IOException {
		System.out.println("X");
		File file = new File("src/config");
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		  
		  String st; 
		  HashMap<String, String> config = new HashMap<String, String>();
		  while ((st = br.readLine()) != null) {
			    System.out.println(st); 
			  String[] array = st.split(",");
			  config.put(array[0]+array[1],array[2]);
		  }
		  System.out.println(config);
		 br.close(); 

	}
	public static void updateConfig(String string) 
{ 
		try { 

// Open given file in append mode. 
			BufferedWriter out = new BufferedWriter( 
					new FileWriter("src/config", true)); 
			out.write(string); 
			out.close(); 
		} 
		catch (IOException e) { 
			System.out.println("exception occoured" + e); 
		} 
	} 

}