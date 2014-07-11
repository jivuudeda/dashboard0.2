package user.pages;

import com.vaadin.data.util.FilesystemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import user.components.RestartButton;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import java.util.Scanner;


public class TomCatRestart extends HorizontalLayout implements View {
    public TomCatRestart(Properties properties) {
        setSizeFull();

        VerticalLayout buttonAndTextArea= new VerticalLayout();
        buttonAndTextArea.setSizeFull();

        String host=properties.getProperty("host");
        String user=properties.getProperty("user");
        String passwd=properties.getProperty("passwd");
        String adress=properties.getProperty("tommyAdress");
        int port=Integer.parseInt(properties.getProperty("port"));

        RestartButton restart = new RestartButton(host, user, passwd, adress, port);
        TextArea catalinaLog = new TextArea();
        catalinaLog.setValue(getCatalinaLog(properties));

        catalinaLog.setSizeFull();
        catalinaLog.addStyleName("log");
        buttonAndTextArea.addComponent(restart);
        buttonAndTextArea.addComponent(seporator());
        buttonAndTextArea.addComponent(catalinaLog);

        buttonAndTextArea.setExpandRatio(restart,1.0f);
        buttonAndTextArea.setExpandRatio(catalinaLog,10.f);

        addComponent(buttonAndTextArea);
    }

    private String getCatalinaLog(final Properties properties) {
        FilesystemContainer logsFiles = new FilesystemContainer(new File((String)properties.getProperty("tommyLogsAdress")));
        FilenameFilter fn = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
            File f = new File(dir.getAbsolutePath() + "/" + name);
            String a=properties.getProperty("tommyLogsAdress")+f.getName();
            String b=f.getAbsolutePath();
            if (a.equals(b)&&f.canRead()) {
                return true;
            } else {
                return false;
            }
            }
        };
        logsFiles.setFilter(fn);
        Collection files = logsFiles.getItemIds();
        ArrayList<File> logs = new ArrayList();
        logs.addAll(files);
        String s ="";
        File maxF;
        ArrayList<File> clearLogs = new ArrayList();
        for (File file :  logs) {
            if (!file.isDirectory()){
                if (file.getName().substring(0,8).equals("catalina")&&file.getName().length()==23){
                    if (checkValue(file.getName().substring(8,23))){
                        clearLogs.add(file);
                    }
                }
            }
        }
        maxF=clearLogs.get(0);
        for (File file :  clearLogs) {
            int fileYear = Integer.parseInt(file.getName().substring(9, 13));
            int maxYear = Integer.parseInt(maxF.getName().substring(9, 13));
            int fileMonth = Integer.parseInt(file.getName().substring(14,16));
            int maxMonth = Integer.parseInt(maxF.getName().substring(14, 16));
            System.out.println(Integer.parseInt(file.getName().substring(14,16))+"  "+Integer.parseInt(file.getName().substring(17, 19)));
            int fileDay = Integer.parseInt(file.getName().substring(17, 19));
            int maxDay = Integer.parseInt(maxF.getName().substring(17, 19));
            if (fileYear>maxYear){
                maxF=file;
            }
            if (fileYear==maxYear&&fileMonth>maxMonth){
                maxF=file;
            }
            if (fileYear==maxYear&&fileMonth==maxMonth&&fileDay>maxDay){
                maxF=file;
            }
        }
        String fileContain="";
        try {
            Scanner in = new Scanner(maxF);
            while (in.hasNext()){
                fileContain+=in.nextLine()+"\n";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return  fileContain;
    }

    private boolean checkValue(String date) {
        //esli sil'no zaho4et's'a dobavit' regul'arnoe virojenie proverki
        return true;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }

    private Label seporator() {
        Label sep = new Label();
        sep.setHeight("5px");
        sep.setWidth("5px");
        return sep;
    }
}
