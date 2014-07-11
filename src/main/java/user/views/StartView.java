package user.views;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.server.WebBrowser;
import com.vaadin.ui.*;
import user.components.*;
import user.logic.Autorization;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class StartView extends VerticalLayout implements View {

    private HorizontalLayout panelComponents;
    private Autorization autorization;
    private Navigator navigator;
    private TextField login;
    private AbstractTextField password;
    private String sessionID;


    public StartView(Navigator navigator, Autorization autorization) {
        this.autorization=autorization;
        this.navigator=navigator;
        setSizeFull();
        addStyleName("startPage");
        Page.getCurrent().setTitle("Login");
        VerticalLayout loginLayout = getPanelComponents(false);
        addComponent(loginLayout);
        setComponentAlignment(loginLayout, Alignment.MIDDLE_CENTER);

    }



    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        if (autorization.isAutorized()){
            navigator.navigateTo("logs");
        }
        else{
            password.setValue("Пароль");
            login.setValue("Login");
        }
        Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();
        String s=null;
        for (int i = 0; i < cookies.length; i++) {
            if ("JSESSIONID".equals(cookies[i].getName())){
                s=cookies[i].getValue();
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
        if ("found".equals(needValue)){
            navigator.navigateTo("logs");
        }
    }

    public VerticalLayout getPanelComponents(boolean chekValue) {
        VerticalLayout panelComponents =new VerticalLayout();
        panelComponents.setStyleName("loginLayout");
        panelComponents.setWidth("200px");

        VerticalLayout panelFields =new VerticalLayout();

        login = new LoginTextField(autorization).getLoginComponent();

        password=new MyPasswordField().getPasswordComponent(false, "Пароль", autorization);

        panelFields.addComponent(login);
        panelFields.addComponent(separator());
        panelFields.addComponent(password);

        panelFields.setComponentAlignment(login,Alignment.MIDDLE_CENTER);
        panelFields.setComponentAlignment(password,Alignment.MIDDLE_CENTER);

        EnterCheckBox checkBox = new EnterCheckBox(panelFields, autorization);

        EnterButton enterButton = new EnterButton(navigator,autorization);

        Label welcome = new Label("Welcome");
        welcome.addStyleName("welcome");
        welcome.setWidth("150px");

        panelComponents.addComponent(separator());
        panelComponents.addComponent(welcome);
        panelComponents.addComponent(separator());
        panelComponents.addComponent(panelFields);
        panelComponents.addComponent(separator());
        panelComponents.addComponent(checkBox);
        panelComponents.addComponent(separator());
        panelComponents.addComponent(enterButton);
        panelComponents.addComponent(separator());

        panelComponents.setComponentAlignment(welcome, Alignment.MIDDLE_CENTER);;
        panelComponents.setComponentAlignment(panelFields, Alignment.MIDDLE_CENTER);;
        panelComponents.setComponentAlignment(checkBox, Alignment.MIDDLE_CENTER);
        panelComponents.setComponentAlignment(enterButton, Alignment.MIDDLE_CENTER);
        return panelComponents;
    }

    private Component separator() {
        Label sep = new Label();
        sep.setHeight("10px");
        sep.setWidth("200px");
        return sep;
    }
}
