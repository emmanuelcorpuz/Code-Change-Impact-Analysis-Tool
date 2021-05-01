
package components;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


class SyncPipe implements Runnable
{


	public SyncPipe(InputStream istrm) {
		istrm_ = istrm;
	}


	public void run() {


		BufferedReader stdout = new BufferedReader(new InputStreamReader(istrm_));
		String line;
		try {
			while((line = stdout.readLine()) != null) 
			{
				System.out.println(line);
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	private final InputStream istrm_;
}