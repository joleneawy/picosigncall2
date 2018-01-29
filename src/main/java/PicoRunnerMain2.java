import java.io.BufferedInputStream;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

@SuppressWarnings("deprecation")
public class PicoRunnerMain2 {
	public static void main(String[] args) {
		
		String picoid = null;
		String meetingRoomId = null;
		
		if(args.length==0){
			System.exit(0);
		}

		if(args.length==2){
			picoid = args[0];
			meetingRoomId = args[1];
		
			if((picoid==null || picoid.equalsIgnoreCase(""))||(meetingRoomId==null || meetingRoomId.equalsIgnoreCase(""))){
				System.exit(0);
			}
			
			System.out.println("picoid: "+picoid);
			System.out.println("meetingRoomId: "+meetingRoomId);
		}

		@SuppressWarnings("resource")
		HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpGet httpGetRequest = new HttpGet("http://localhost:8080/pico/rest/generate/"+picoid+"/"+meetingRoomId);
			HttpResponse httpResponse = httpClient.execute(httpGetRequest);

			System.out.println("----------------------------------------");
			System.out.println(httpResponse.getStatusLine());
			System.out.println("----------------------------------------");

			HttpEntity entity = httpResponse.getEntity();

			byte[] buffer = new byte[1024];
			if (entity != null) {
				InputStream inputStream = entity.getContent();
				try {
					int bytesRead = 0;
					BufferedInputStream bis = new BufferedInputStream(inputStream);
					while ((bytesRead = bis.read(buffer)) != -1) {
						String chunk = new String(buffer, 0, bytesRead);
						System.out.println(chunk);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						inputStream.close();
					} catch (Exception ignore) {
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
	}

}
