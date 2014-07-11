package user.components;


import com.vaadin.ui.*;

import java.io.*;
import java.util.Properties;
import java.util.Scanner;

public class Setting extends HorizontalLayout{
    private String valueKey;

    public String getValueKey() {
        return valueKey;
    }

    public void setValueKey(String valueKey) {
        this.valueKey = valueKey;
    }

    public Setting(final Properties properties, final String propertyKey, String propertyName, String valueKey) {
        this.valueKey=valueKey;
        setSizeFull();
        Button apply = new Button("Apply");
        Label prName = new Label(propertyName);
        addComponent(prName);
        final TextField value = new TextField();
        value.setValue(properties.getProperty(propertyKey));

        apply.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                properties.setProperty(propertyKey,value.getValue());
                String conf = Thread.currentThread().getContextClassLoader().getResource("configs.txt").getPath();
                File file = new File(conf);
                String s="";
                String toFile="";
                try {
                    Scanner in = new Scanner(file);

                    while(in.hasNext()){
                        s=in.nextLine();
                        String substrings[]=s.split(" ");
                        if (substrings[0].equals(propertyKey)){
                            toFile+=propertyKey+" = "+value.getValue()+"\n";
                        }
                        else{
                            toFile += s+"\n";
                        }
                    }
                    System.out.println(file.getAbsolutePath());
                    System.out.println(s);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    FileWriter fw = new FileWriter(file,true);
                    fw.write(toFile);
                    fw.flush();
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        setHeight("40px");
        setWidth("400px");
        addComponent(value);
        addComponent(apply);
        setExpandRatio(prName,4.0f);
        setExpandRatio(value,5.0f);
        setExpandRatio(apply, 2.0f);
        setComponentAlignment(prName, Alignment.MIDDLE_CENTER);
        setComponentAlignment(value,Alignment.MIDDLE_CENTER);
        setComponentAlignment(apply,Alignment.MIDDLE_CENTER);
        addStyleName("selectedMenuItem");
    }
}
