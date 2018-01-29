package com.recruitee.doclerholding.SimplePingApp.strategy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.recruitee.doclerholding.SimplePingApp.InitConfig;
import com.recruitee.doclerholding.SimplePingApp.model.Config;

public class PingTCP implements Runnable {

	Config config;
	int count_id=0;

	public PingTCP() {
		synchronized (this) {
			if (InitConfig.LIST_CONFIG.size() >= InitConfig.COUNT_PING_TCP) {
				config = InitConfig.LIST_CONFIG.get(InitConfig.COUNT_PING_TCP);
				count_id=InitConfig.COUNT_PING_TCP;
				++InitConfig.COUNT_PING_TCP;
			}
		}
	}

	@Override
	public void run() {
//curl -I --connect-timeout 5 https://example.com/
		SendrReport sendReport=SendReportImp.getInstance();
		 
		if (config != null && config.getDelayPingTCP() >= 0) {
			while (true) {

				List<String> commands = new ArrayList<String>();
				commands.add("curl");
				commands.add("-I");
				commands.add("--connect-timeout");
				commands.add(config.getPingTimeOutTCP()+"");
				commands.add(config.getHost());

				String s = null;

				ProcessBuilder pb = new ProcessBuilder(commands);
				Process process;
				try {
					process = pb.start();

					BufferedReader stdInput = new BufferedReader(
							new InputStreamReader(process.getInputStream()));
					BufferedReader stdError = new BufferedReader(
							new InputStreamReader(process.getErrorStream()));
					int count = 0;
					Date init=new Date();
					while ((s = stdInput.readLine()) != null) {
						if (count++ == 0) {
							InitConfig.LIST_CONFIG.get(
									count_id)
									.setLastPingTCP(s);
							InitConfig.LIST_CONFIG.get(
									count_id)
									.setPingResponseTime(new Date().getTime()- init.getTime());
							InitConfig.LIST_CONFIG.get(
									count_id)
									.setDateLastPingTCP( init);
						}
						
						//System.out.println(s);

					}
					sendReport.pingTcpReport(InitConfig.LIST_CONFIG.get(count_id));
					
					if(InitConfig.LIST_CONFIG.get(count_id).getLastPingTCP().indexOf("OK")<0){
					//System.out.println(InitConfig.LIST_CONFIG.get(count_id).getLastPingTCP());
						 
						 sendReport.sendReport(InitConfig.LIST_CONFIG.get(count_id));
					}
					

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				try {
					Thread.sleep(config.getDelayPingTCP());
				} catch (Exception e) {
					System.out.println(e);
				}
				
			}
		}
	}

}
