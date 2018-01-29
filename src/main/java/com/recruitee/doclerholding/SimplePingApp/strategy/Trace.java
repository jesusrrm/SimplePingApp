package com.recruitee.doclerholding.SimplePingApp.strategy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.recruitee.doclerholding.SimplePingApp.InitConfig;
import com.recruitee.doclerholding.SimplePingApp.model.Config;

public class Trace implements Runnable {

	Config config;
int count_id=0;
	public Trace() {
		synchronized (this) {
			if (InitConfig.LIST_CONFIG.size() > InitConfig.COUNT_TRACE) {
				config = InitConfig.LIST_CONFIG.get(InitConfig.COUNT_TRACE);
				count_id=InitConfig.COUNT_TRACE;
				++InitConfig.COUNT_TRACE;
			}
		}
	}

	@Override
	public void run() {
		System.out.println("Trace: " + config.getHost());
		 SendrReport sendReport=SendReportImp.getInstance();

		if (config != null && config.getDelayTrace()>= 0) {
			while (true) {

				List<String> commands = new ArrayList<String>();
				commands.add("tracepath");
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
					String trace="";
					while ((s = stdInput.readLine()) != null && s.toUpperCase().indexOf("no reply".toUpperCase())<0) {
						trace=trace.concat(s);	
						
					}
					InitConfig.LIST_CONFIG.get(
							count_id)
							.setLastTrace(trace.trim().replaceAll("  ", " ").replaceAll("  ", " "));
			
					sendReport.traceReport(InitConfig.LIST_CONFIG.get(count_id));
					
					while ((s = stdError.readLine()) != null) {
						
						 InitConfig.LIST_CONFIG.get(count_id).setLastTrace(s.trim().replaceAll("  ", " ").replaceAll("  ", " "));
					
						 sendReport.sendReport(InitConfig.LIST_CONFIG.get(count_id));
					}

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				try {
					Thread.sleep(config.getDelayTrace());
				} catch (Exception e) {
					System.out.println(e);
				}
				
			}
		}
	}

}
