import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class Client {
	private Socket client = null;
	public void starClient(String[] args) {

		try {
			client = new Socket("localhost",5000);
			System.out.println("Client started successfully");
			{
				String str = String.valueOf(args[0]);
				boolean b = !str.equals("totals") && !str.equals("list") && !str.equals("join");
				if (!b) {
					PrintStream ps = new PrintStream(client.getOutputStream());
					ps.println(str);
					for (int i=1; i< args.length; i++) {
						String str1 = String.valueOf(args[i]);
						ps.println(str1);
					}
					if(str.equals("totals")){
						InputStream is = client.getInputStream();
						BufferedReader br = new BufferedReader(new InputStreamReader(is));
						String line;
						line = br.readLine();
						System.out.println(line);
						String[] elem = line.split(" ");
						for(int i = 1; i <= Integer.parseInt(elem[2]); i++){
							line = br.readLine();
							System.out.println(line);
						}
					}
					if(str.equals("list")){
						ps.println(args[1]);
						InputStream is = client.getInputStream();
						BufferedReader br = new BufferedReader(new InputStreamReader(is));
						String line;
						line = br.readLine();
						System.out.println(line);
					}
					if(str.equals("join")){
						InputStream is = client.getInputStream();
						ps.println(args[1]);
						ps.println(args[2]);
						try {
							BufferedReader br = new BufferedReader(new InputStreamReader(is));
							String line;
							line = br.readLine();
							System.out.println(line);
						}
						catch (Exception e){
							e.printStackTrace();
						}
					}
				}
				else {
					System.out.println("Wrong Arguments!\n Server will close.");
					client.close();
					//return;
				}

			}


		} catch (UnknownHostException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	public static void main(String[] args) {
		Client test = new Client();
		test.starClient(args);
	}
}