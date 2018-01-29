package com.recruitee.doclerholding.SimplePingApp.strategy;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.json.JSONObject;

import com.recruitee.doclerholding.SimplePingApp.model.Config;

public class SendReportImp implements SendrReport {
	private static SendReportImp instance;

	public static SendReportImp getInstance() {
		if (instance == null)
			instance = new SendReportImp();
		return instance;
	}

	private SendReportImp() {
	};

	@Override
	public void sendReport(Config config) throws IOException {

		JSONObject parent = new JSONObject();

		parent.put("host", config.getHost());
		parent.put("icmp_ping", config.getLastPingICMP());
		parent.put("tcp_ping", config.getLastPingTCP());
		parent.put("trace", config.getLastTrace());

		sendReport(parent, new URL(config.getReport_url()));

		parent.put("date", new Date());
		parent.put("response-time", config.getPingResponseTime());

		sendReport(parent, new File(config.getReport_file()));
		
		pingTcpReport(config);
		traceReport(config);
	}
	
	@Override
	public void pingTcpReport(Config config) throws IOException{
		JSONObject parent = new JSONObject();

		parent.put("date", config.getDateLastPingTCP());
		parent.put("host", config.getHost());
		parent.put("tcp_ping", config.getLastPingTCP());
		
		sendReport(parent, new File(config.getPingtcp_log_path()+"/"+ config.getHost()+".pingTCP.log"));
		
		
	}

	@Override
	public void traceReport(Config config) throws IOException{
		JSONObject parent = new JSONObject();

		parent.put("date", config.getDateLastPingTCP());
		parent.put("host", config.getHost());
		parent.put("trace", config.getLastTrace());
		
		sendReport(parent, new File(config.getTrace_log_path()+"/"+config.getHost()+".trace.log"));
		
		
	}

	
	@Override
	public void sendReport(JSONObject json, File file) throws IOException {
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// true = append file
			fw = new FileWriter(file.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);

			bw.write(json.toString()+"\n\r");

		} catch (IOException e) {
			throw e;

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}
		}

	}

	@Override
	public void sendReport(JSONObject json, URL url) throws IOException {

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestProperty("Content-Type",
				"application/json; charset=UTF-8");
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setRequestMethod("POST");

		OutputStream os = conn.getOutputStream();
		os.write(json.toString().getBytes("UTF-8"));
		os.close();

		// read the response
		/*
		 * InputStream in = new BufferedInputStream(conn.getInputStream());
		 * String result = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
		 * JSONObject jsonObject = new JSONObject(result);
		 * 
		 * 
		 * in.close();
		 */
		conn.disconnect();

	}

}
