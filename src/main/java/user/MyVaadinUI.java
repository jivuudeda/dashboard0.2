package user;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.*;
import com.vaadin.ui.UI;
import user.logic.Autorization;
import user.views.PagesView;
import user.views.StartView;

import java.io.*;
import java.util.*;

@Theme("mytheme")
@SuppressWarnings("serial")
public class MyVaadinUI extends UI{
    Navigator navigator;

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = MyVaadinUI.class, widgetset = "user.AppWidgetSet")
    public static class Servlet extends VaadinServlet {

    }

    @Override
    protected void init(VaadinRequest request) {
        Autorization autorization = new Autorization();
        Properties properties = new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("conf.properties"));
        } catch (IOException e) {
            System.out.println("zzzzz");
        }
        checkProperties(properties);
        chekSessionFile(properties.getProperty("sessionPath"));

        navigator = new Navigator(this,this);
        navigator.addView("login", new StartView(navigator, autorization));
        navigator.addView("logs", new PagesView(navigator, autorization,properties));
    }

    private void checkProperties(Properties properties) {
        String conf = Thread.currentThread().getContextClassLoader().getResource("configs.txt").getPath();
        File file = new File(conf);
        try {

            FileReader fr = new FileReader(file);
            Scanner in = null;
            try {
                in = new Scanner(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            ArrayList <String> keys = new ArrayList<String>();
            while (in.hasNext()){
                String s=in.nextLine();
                String string[]=s.split(" ");
                keys.add(string[0]);
            }
            if (!keys.contains("pass")){
                writeToConfigs("pass",properties,file);
            }
            else{
                chengeProperties("pass", properties, file);
            }
            if (!keys.contains("sessionPath")){
                writeToConfigs("sessionPath",properties,file);
            }
            else{
                chengeProperties("sessionPath", properties, file);
            }
        } catch (IOException e) {
        }
    }

    private void chengeProperties(String confName, Properties properties, File file) {
        try {
            Scanner in = new Scanner(file);
            while(in.hasNext()){
                String s= in.nextLine();
                System.out.println(s);
                String substrings[]=s.split(" ");
                if (substrings[0].equals(confName)){
                    properties.setProperty(substrings[0],substrings[2]);
                    System.out.println(properties.getProperty(confName));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void writeToConfigs(String confName, Properties properties, File file) {//perepisivaet a ne dozapisivaet
        try {
            FileWriter fw = new FileWriter(file,true);
            FileReader in = new FileReader(file);
            String s="";
            s+=confName+" = "+properties.getProperty(confName);
            fw.write(s+"\n");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void chekSessionFile(String path) {
        File f = new File(path);
        if (!f.exists()){
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
