package user.pages;

import com.vaadin.event.LayoutEvents;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import user.components.Setting;
import user.logic.Autorization;

import javax.servlet.http.Cookie;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

public class SettingsPage extends VerticalLayout implements View {
    private Properties properties;
    private Autorization aut;
    private Navigator navigator;
    private String sessionValue;

    public SettingsPage(Properties properties, final Navigator navigator, Autorization aut) {
        Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();
        for (int i = 0; i < cookies.length; i++) {
            if ("JSESSIONID".equals(cookies[i].getName())){
                sessionValue=cookies[i].getValue();
            }
        }
        this.navigator=navigator;
        this.aut=aut;
        HorizontalLayout menuLayout = new HorizontalLayout();
        menuLayout.setSizeFull();
        menuLayout.addComponent(seporator());
        this.properties=properties;
        addStyleName("pagesView");
        setSizeFull();
        HorizontalLayout goBack=new HorizontalLayout();
        Label caption = new Label("<GoBack");
        caption.setWidth("100px");
        goBack.addComponent(caption);
        goBack.setStyleName("pagesMenuItem");
        goBack.setSizeFull();
        goBack.setComponentAlignment(caption, Alignment.MIDDLE_CENTER);
        goBack.addLayoutClickListener(new LayoutEvents.LayoutClickListener() {

            @Override
            public void layoutClick(LayoutEvents.LayoutClickEvent layoutClickEvent) {
                navigator.navigateTo("login");
            }
        });
        goBack.addStyleName("pagesMenuItem");
        HorizontalLayout settings = new HorizontalLayout();
        addComponent(seporator());
        menuLayout.addComponent(goBack);
        menuLayout.setExpandRatio(goBack, 1);
        menuLayout.addComponent(seporator());
        addComponent(menuLayout);
        addComponent(seporator());

        Set<String> settingsNames = properties.stringPropertyNames();
        ArrayList<String> settingKeys = new ArrayList();
        settingKeys.addAll(settingsNames);
        Properties nameConvertor = new Properties();

        try {
            nameConvertor.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("nameConvertor.properties"));
        } catch (IOException e) {
        }
        ArrayList <Setting> settingsList = new ArrayList<Setting>();
        for(String setKey : settingKeys){
            if  ("pass".equals(setKey)||"tommyAdress".equals(setKey)||"tommyLogsAdress".equals(setKey)||"sessionPath".equals(setKey)) {
                settingsList.add(new Setting(properties, setKey, nameConvertor.getProperty(setKey),"path"));
            }
            if ("host".equals(setKey)||"user".equals(setKey)||"passwd".equals(setKey)||"port".equals(setKey)){
                settingsList.add(new Setting(properties, setKey, nameConvertor.getProperty(setKey),"server"));
            }
        }
        final ArrayList<HorizontalLayout> settingsItems = new ArrayList<HorizontalLayout>();

        VerticalLayout settingsFiels = new VerticalLayout();
        //settingsFiels.addStyleName("settings");

        VerticalLayout settingKeysLayout = new VerticalLayout();
        HorizontalLayout pathKey = getItemLayout("Настройки путей",settingsFiels,settingsList);
        settingsItems.add(pathKey);
        settingKeysLayout.addComponent(pathKey);

        HorizontalLayout serverKey = getItemLayout("Настройки ssh",settingsFiels,settingsList);
        settingsItems.add(serverKey);

        settingKeysLayout.addComponent(seporator());
        settingKeysLayout.addComponent(serverKey);

        settings.addComponent(seporator());
        settings.addComponent(settingKeysLayout);
        settings.addComponent(seporator());

        settings.addComponent(settingsFiels);
        settings.addComponent(seporator());
        settings.setExpandRatio(settingKeysLayout, 2.0f);
        settings.setExpandRatio(settingsFiels, 7.0f);

        settings.setSizeFull();
        addComponent(settings);

        setExpandRatio(menuLayout,1.0f);
        setExpandRatio(settings,12.0f);
    }

    private HorizontalLayout getItemLayout(String s, final VerticalLayout settingsFiels, final ArrayList <Setting> settingsList) {
        HorizontalLayout valueKey = new HorizontalLayout();
        Label value = new Label(s);
        value.setWidth("100px");
        valueKey.addComponent(value);
        valueKey.setSizeFull();
        valueKey.setHeight("40px");
        valueKey.setStyleName("pagesMenuItem");
        valueKey.setComponentAlignment(value, Alignment.MIDDLE_CENTER);
        String noTime="";
        if (s.equals("Настройки путей")){
            noTime="path";
        }
        if (s.equals("Настройки ssh")){
            noTime="server";
        }
        final String finalNoTime = noTime;
        final HorizontalLayout h1 = new HorizontalLayout();
        final VerticalLayout v1 = new VerticalLayout();
        valueKey.addLayoutClickListener(new LayoutEvents.LayoutClickListener() {
            @Override
            public void layoutClick(LayoutEvents.LayoutClickEvent layoutClickEvent) {
                settingsFiels.removeAllComponents();
                v1.removeAllComponents();
                h1.removeAllComponents();
                v1.addComponent(seporator());
                for (Setting setting: settingsList){
                    if (setting.getValueKey().equals(finalNoTime)){
                        v1.addComponent(setting);
                        v1.addComponent(seporator());
                    }
                }
                h1.addComponent(seporator());
                h1.addComponent(v1);
                h1.addComponent(seporator());
                h1.setStyleName("settings");
                settingsFiels.addComponent(h1);
            }
        });
        return valueKey;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();
        String s=null;
        for (int i = 0; i < cookies.length; i++) {
            if ("JSESSIONID".equals(cookies[i].getName())){
                s=cookies[i].getValue();
                sessionValue=s;
            }
        }
        String needValue = "notFound";
        File file = new File("/home/user/logs/session.txt");
        Scanner in = null;
        try {
            in = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (in.hasNext()){
            if (in.nextLine().equals(s)){
                needValue="found";
            }
        }
        in.close();
        if ("notFound".equals(needValue)){
            if (!aut.isAutorized()){
                navigator.navigateTo("login");
            }
        }
    }

    private Label seporator() {
        Label sep = new Label();
        sep.setHeight("5px");
        sep.setWidth("5px");
        return sep;
    }
}
