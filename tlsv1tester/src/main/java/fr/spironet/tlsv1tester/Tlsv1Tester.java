package fr.spironet.tlsv1tester;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.security.cert.Certificate;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Tlsv1Tester extends HttpServlet {
	private static final long serialVersionUID = -3668902612542858752L;

	public final static String METRIC_NAME = "tlsv1_host_status";

	public static int test(String https_url) {
		URL url;
		int status = 0;
		HttpsURLConnection conn = null;
		try {
			url = new URL(https_url);
			conn = (HttpsURLConnection) url.openConnection();
			conn.connect();
		    if(conn != null) {
		          Certificate[] certs = conn.getServerCertificates();
		          for(Certificate cert : certs) { /* do nothing */ }
                  System.out.println(String.format("%s: OK\n", https_url));
		    }
		} catch (Throwable e) {
			e.printStackTrace();
			status = 1;
		} finally {
		    if (conn != null )
		        conn.disconnect();
		}
		return status;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String,String> env = System.getenv();
		String hosts = env.get("TLSV1_HOSTS");
		if (hosts == null) {
			return;
		}
		OutputStream os = resp.getOutputStream();
		String[] hosts_to_check = hosts.split(",");
		StringBuilder sb = new StringBuilder();
		for (String h : hosts_to_check) {
			int st = Tlsv1Tester.test(h);
			sb.append(String.format("%s{name=\"%s\"} %d\n", METRIC_NAME, h, st));
		}
		os.write(sb.toString().getBytes());
	}
}
