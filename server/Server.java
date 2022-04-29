
import java.io.*;
import java.net.*;

public class Server {
	public void starServer(String[] args){
		int List_Number;
		int Max_Member;
		if (Integer.parseInt(args[2]) <= 0 || Integer.parseInt(args[3]) <= 0){
			System.out.println("Error: Invalid Arguments, Server will close.");
			return;
		}
		List_Number = Integer.parseInt(args[2]); // Max lists
		Max_Member = Integer.parseInt(args[3]); // Max members
		ServerSocket server = null;
		String[][] list = new String[List_Number+1][Max_Member+1];

		try {
			server = new ServerSocket(5000); // New Server Started
			//System.out.println("server started successfully");
		} catch (IOException e1) {
// TODO Auto-generated catch block
			e1.printStackTrace();
			//System.out.println("Error: catch an Exception, Failed to initialize");
		}

		Socket client;
		while(true) {
			try {
				File file =new File("log.txt");
				//if file doesnt exists, then create it
				if(!file.exists()){
					boolean success = file.createNewFile();
				}
				assert server != null;
				client = server.accept();
				InputStream is = client.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				String line = br.readLine();
				int count;
				if (line != null) {
					if (line.equals("totals")) {
						PrintStream ps = new PrintStream(client.getOutputStream());
						ps.printf("There are %d list(s), each with a maximum size of %d.\n", List_Number, Max_Member);
						try {
							for (int i = 0; i < List_Number; i++) {
								count = 0;
								for (int j = 0; j < Max_Member; j++)
									if (list[i][j] != null && list[i][j].length() != 0) count++;
								ps.printf("List %d has %d member(s).\n", i + 1, count);
							}
							ps.print("\n");
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
					if (line.equals("list")) {
						String para = br.readLine();
						PrintStream ps = new PrintStream(client.getOutputStream());
						int index = Integer.parseInt(para) - 1 ; // begin with 1
						if(index < 0 || index >= List_Number){
							ps.println("Invalid Parameter!");
							continue;
						}
						for (int i = 0; i < Max_Member; i++) {
							if (list[index][i] != null && list[index][i].length() != 0) {
								ps.println(list[index][i]);
							}
						}
						ps.println("It_is_the_end"); // End mark.
					}
					if (line.equals("join")) {
						try {
							PrintStream ps = new PrintStream(client.getOutputStream());
							String para = br.readLine();
							String name = br.readLine();
							//System.out.println(name);
							int index = Integer.parseInt(para) - 1 ; // Begin From 1
							if(index < 0 || index >= List_Number){
								ps.println("Invalid Parameter!");
								continue;
							}
							int cnt = 0;
							for (int i = 0; i < Max_Member; i++) {
								if (list[index][i] != null && list[index][i].length() != 0)
									cnt++;
							}
							if (cnt < Max_Member) {
								// name = name.replace("\"", "");
								list[index][cnt] = name;
								//System.out.println(cnt);
								ps.println("Success!");
							} else {
								//System.out.println("qwqwq");
								ps.println("Failed!");
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

			}
			catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}
	}

	public static void main(String[] args) {
		Server test = new Server();
		test.starServer(args);
	}

}