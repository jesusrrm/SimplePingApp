package com.recruitee.doclerholding.SimplePingApp;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import com.recruitee.doclerholding.SimplePingApp.model.Config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class InitConfig {

	public static int COUNT_PING_TCP=0;
	public static int COUNT_PING_ICMP=0;
	public static int COUNT_TRACE=0;
	public static List<Config>  LIST_CONFIG = new ArrayList<Config>();
	private String report_url;
	private String report_file;
	private String trace_log_path;
	private String pingtcp_log_path;
	
	public InitConfig(String hostConfig) {

		try {

			File fXmlFile = new File(hostConfig);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList0 = doc.getElementsByTagName("report");


			for (int temp = 0; temp < nList0.getLength(); temp++) {

				Node nNode = nList0.item(temp);


				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
			
					report_url=eElement.getAttribute("id");
					report_file=eElement
							.getElementsByTagName("log-file").item(0)
							.getFirstChild().getNodeValue();
					trace_log_path=eElement
							.getElementsByTagName("trace_log_path").item(0)
							.getFirstChild().getNodeValue();
					pingtcp_log_path=eElement
							.getElementsByTagName("pingtcp_log_path").item(0)
							.getFirstChild().getNodeValue();
				}
			}


			
			NodeList nList = doc.getElementsByTagName("host");


			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);


				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					Config config = new Config();
					config.setReport_file(report_file);
					config.setReport_url(report_url);
					config.setTrace_log_path(trace_log_path);
					config.setPingtcp_log_path(pingtcp_log_path);
					config.setHost(eElement.getAttribute("id"));
					config.setDelayPingICMP(Long.parseLong(eElement
							.getElementsByTagName("pingdelay-icmp").item(0)
							.getFirstChild().getNodeValue()));
					config.setDelayPingTCP(Long.parseLong(eElement
							.getElementsByTagName("pingdelay-tcp").item(0)
							.getFirstChild().getNodeValue()));
					config.setDelayTrace(Long.parseLong(eElement
							.getElementsByTagName("tracedelay").item(0)
							.getFirstChild().getNodeValue()));
					config.setPingTimeOutTCP(Long.parseLong(eElement
							.getElementsByTagName("pingtimeout-tcp").item(0)
							.getFirstChild().getNodeValue()));
					
					
					LIST_CONFIG.add(config);
					

				}
			}

			
			LIST_CONFIG.forEach(System.out::println);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
