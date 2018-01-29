package com.recruitee.doclerholding.SimplePingApp.strategy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.recruitee.doclerholding.SimplePingApp.InitConfig;
import com.recruitee.doclerholding.SimplePingApp.model.Config;

public class PingICMP implements Runnable {

	Config config;
int count_id=0;
	public PingICMP() {
		synchronized (this) {
			if (InitConfig.LIST_CONFIG.size() > InitConfig.COUNT_PING_ICMP) {
				config = InitConfig.LIST_CONFIG.get(InitConfig.COUNT_PING_ICMP);
				count_id=InitConfig.COUNT_PING_ICMP;
				++InitConfig.COUNT_PING_ICMP;
			}
		}
	}

	@Override
	public void run() {
		SendrReport sendReport=SendReportImp.getInstance();
		if (config != null && config.getDelayPingICMP() >= 0) {
			while (true) {

				List<String> commands = new ArrayList<String>();
				commands.add("ping");
				commands.add("-c");
				commands.add("5");
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
					while ((s = stdInput.readLine()) != null) {
						if (count++ == 5) {
							count = 0;
							InitConfig.LIST_CONFIG.get(
									count_id)
									.setLastPingICMP(s);
						}
//						System.out.println(s);

					}
					
					
					while ((s = stdError.readLine()) != null) {
						
						 InitConfig.LIST_CONFIG.get(count_id).setLastPingICMP(s);
						 
						 sendReport.sendReport(InitConfig.LIST_CONFIG.get(count_id));
					}

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				try {
					Thread.sleep(config.getDelayPingICMP());
				} catch (Exception e) {
					System.out.println(e);
				}
				
			}
		}
	}

}
