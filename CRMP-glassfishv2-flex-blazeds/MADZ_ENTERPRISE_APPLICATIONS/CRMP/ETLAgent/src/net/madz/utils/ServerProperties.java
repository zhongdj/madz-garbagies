/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.madz.utils;

import java.util.Properties;

/**
 *
 * @author cn1zd042
 */
public class ServerProperties extends Properties{
    public static final String SERVICE_IP_ADDRESS = "org.omg.CORBA.ORBInitialHost";
    public static final String SERVICE_PORT_NUMBER = "org.omg.CORBA.ORBInitialPort";
    
    private static Properties serverProperties = new Properties();
    
    public static Properties getServerProperties(){
        return serverProperties;
    }
}
