package com.recruitee.doclerholding.SimplePingApp.strategy;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

import org.json.JSONObject;

import com.recruitee.doclerholding.SimplePingApp.model.Config;

public interface SendrReport {

	void sendReport(JSONObject json, URL url) throws IOException;

	void sendReport(Config config) throws IOException;

	void sendReport(JSONObject json, File file) throws IOException;

	

	void pingTcpReport(Config config) throws IOException;

	void traceReport(Config config) throws IOException;

}
