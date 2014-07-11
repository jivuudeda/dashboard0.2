package user.views;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import user.layouts.LayoutPage;
import user.layouts.MenuItemLayout;
import user.logic.Autorization;
import user.pages.LogsPage;
import user.pages.SettingsPage;
import user.pages.TomCatRestart;

import javax.servlet.http.Cookie;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;


public class PagesView extends VerticalLayout implements View {

    private Autorization autorization;
    private Navigator navigator;
    private String sessionValue;
    private Properties properties;

    public PagesView(Navigator nav, Autorization aut, Properties props){

        Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();
        for (int i = 0; i < cookies.length; i++) {
            if ("JSESSIONID".equals(cookies[i].getName())){
                sessionValue=cookies[i].getValue();
            }
        }
        autorization = aut;
        navigator = nav;
        setStyleName("pagesView");
        setSizeFull();
        ArrayList<LayoutPage> pages = new ArrayList<LayoutPage>();

        properties=props;

        LayoutPage page1 = new LayoutPage("Логи",new LogsPage(properties));
        LayoutPage page2 = new LayoutPage("Рестарт сервера",new TomCatRestart(properties));
        LayoutPage page3 = new LayoutPage("Настройки",new SettingsPage(properties));

        pages.add(page1);
        pages.add(page2);
        pages.add(page3);

        MenuItemLayout menuItemLayout = new MenuItemLayout(pages, autorization, navigator, sessionValue);
        menuItemLayout.setSizeFull();
        Label horizontalSeporator = new Label();
        horizontalSeporator.setWidth("10px");
        horizontalSeporator.setHeight("10px");

        VerticalLayout topLayout = new VerticalLayout();
        topLayout.addComponent(menuItemLayout);
        topLayout.setSizeFull();
        addComponent(topLayout);
        setSizeFull();

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
            if (!autorization.isAutorized()){
                navigator.navigateTo("login");
            }
        }
    }
}
