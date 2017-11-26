package mgr;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class HttpURLConnection {
	private final String USER_AGENT = "Mozilla/5.0";

	public static void main(String[] args) throws Exception {

		//v=1
		//tid=UA-100414421-1
		//t - akcja wykonana na stronie cid - id uzykownika dzialajacego na stronie
		//dp example dp=%2Fhangers%3Fprod%3D231 = /hangers?prod=231
		//dp /index.php/oobozie/regulamin-obozu = %2Findex.php%2Foobozie%2Fregulamin-obozu
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("https://www.google-analytics.com/debug/collect?v=1&t=pageview&tid=UA-100414421-1&cid=d0abe39e-ba32-4d6a-a70e-65e8345a4bce&dp=%2Findex.php%2Foobozie%2Fregulamin-obozu");
		List <NameValuePair> postParameters = new ArrayList <NameValuePair>();
        postParameters.add(new BasicNameValuePair("content", "options"));
        postParameters.add(new BasicNameValuePair("constr", "Chart"));
		httpPost.setEntity(new UrlEncodedFormEntity(postParameters));
		CloseableHttpResponse response = httpclient.execute(httpPost);

		try {
		    System.out.println(response.getStatusLine());
		    HttpEntity entity = response.getEntity();
		    InputStream inputStream = entity.getContent();
		    String json = IOUtils.toString(inputStream,StandardCharsets.UTF_8);
		    System.out.println(json);
		    Gson gson = new Gson();
		    JsonData jsonData = gson.fromJson(json, JsonData.class);
		    System.out.println(jsonData.getHitParsingResults().get(0).getValid());
		    /*
		    ObjectMapper mapper = new ObjectMapper();
		    Map<String, Object> jsonMap = mapper.readValue(inputStream, Map.class);
		    System.out.println(jsonMap.toString());
		    System.out.println(jsonMap.get("hitParsingResult"));
		    */
		    
		  
/*		    JSONParser jsonParser = new JSONParser();
//		    JSONObject jsonObject = (JSONObject)jsonParser.parse(new InputStreamReader(inputStream, "UTF-8"));
//		    JSONObject myResponse = jsonObject.getJSONObject("hitParsingResult");
//		    JSONArray tsmresponse = (JSONArray) myResponse.get("listTsm");
/*		    
		    ArrayList<String> list = new ArrayList<String>();

		    for(int i=0; i<tsmresponse.length(); i++){
		        list.add(tsmresponse.getJSONObject(i).getString("name"));
		    }

		    System.out.println(list);
		    // do something useful with the response body
		    // and ensure it is fully consumed
*/
		    EntityUtils.consume(entity);
		} finally {
		    response.close();
		}
	}
}
