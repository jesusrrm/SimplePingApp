package com.recruitee.doclerholding.SimplePingApp;

import com.recruitee.doclerholding.SimplePingApp.model.Config;
import com.recruitee.doclerholding.SimplePingApp.strategy.PingICMP;
import com.recruitee.doclerholding.SimplePingApp.strategy.PingTCP;
import com.recruitee.doclerholding.SimplePingApp.strategy.Trace;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	if(args.length!=1 ){
    		System.out.println("Please, place the path for the Host config file");
    		System.exit(0);
    	}
        System.out.println( "Hello World! Ping Test" );
        new InitConfig(args[0]);
   for(Config c:InitConfig.LIST_CONFIG){
        new Thread(new PingICMP()).start();
        new Thread(new PingTCP()).start();
        new Thread(new Trace()).start();
   }
    }
}
