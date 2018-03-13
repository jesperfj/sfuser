package com.frejo.sfuser;

import com.force.api.*;

import java.io.*;
import java.util.List;
import java.util.Properties;


/**
 * Created by jjoergensen on 3/21/17.
 */
public class Main {
    public static void main(String[] args) {
        if("login".equals(args[0])) {
            login();
        } else if("reports".equals(args[0])) {
            reports(args[1]);
        }
    }

    public static void login() {
        Console console = System.console();
        String login = console.readLine("Login: ");
        char[] password = console.readPassword("Password: ");

        try {
            ApiSession session = Auth.authenticate(new ApiConfig()
                    .setUsername(login)
                    .setPassword(new String(password)));
            Properties p = new Properties();
            p.setProperty("endpoint", session.getApiEndpoint());
            p.setProperty("token", session.getAccessToken());
            File file = new File(".sfsession");
            FileOutputStream fileOut = null;
            fileOut = new FileOutputStream(file);
            p.store(fileOut,"");
            fileOut.close();
        } catch(AuthException e) {
            System.out.println("Login failed: "+e);
        } catch (Exception e) {
            System.out.println("Problem saving session file: "+e);
        }
    }

    public static void reports(String manager) {
        ForceApi api = new ForceApi(new ApiConfig(), loadSession());
        reportsRecursive(api, manager, "");
    }

    public static void reportsRecursive(ForceApi api, String manager, String indent) {
        if(indent.length() > 40) {
            // a safety check
            throw new RuntimeException("recursion too deep");
        }
        List<User> result = api.query("SELECT Username, Name, Title FROM User WHERE Manager.Username = '"+manager+"' AND isActive = true",
                User.class).getRecords();
        for(User u: result) {
            //System.out.println(indent+u.username+ " -- "+ u.name+ " -- "+ u.title);
            System.out.println("\""+u.username + "\",\"" + u.title+ "\"," + indent + "\""+u.name+"\"");
            reportsRecursive(api, u.username, indent+",");
        }
    }

    public static ApiSession loadSession() {
        try {
            Properties p = new Properties();
            File file = new File(".sfsession");
            FileInputStream fileIn = new FileInputStream(file);
            p.load(fileIn);
            fileIn.close();
            return new ApiSession(p.getProperty("token"), p.getProperty("endpoint"));
        } catch (Exception e) {
            System.out.println("Problem loading session file: "+e);
            throw new RuntimeException(e);
        }
    }
}
