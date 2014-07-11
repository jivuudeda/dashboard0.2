package user.pages;

import com.vaadin.event.FieldEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import user.components.ChooseButton;
import user.components.LogsListSelect;
import user.components.SerchTextField;
import java.util.Properties;


public class LogsPage extends HorizontalLayout implements View {
    public LogsPage(Properties properties) {
        setSizeFull();

        VerticalLayout leftSide = new VerticalLayout();
        VerticalLayout rightSide = new VerticalLayout();

        HorizontalLayout chooserLayout = new HorizontalLayout();
        HorizontalLayout logSelectrLayout = new HorizontalLayout();
        HorizontalLayout searchLayout = new HorizontalLayout();

        TextArea log = new TextArea();
        log.setSizeFull();
        log.addStyleName("log");
        rightSide.addComponent(log);
        rightSide.setSizeFull();

        HorizontalLayout adress = new HorizontalLayout();
        TextField logsAdressTextfield = new TextField();
        logsAdressTextfield.setWidth("90%");
        logsAdressTextfield.setValue(properties.getProperty("pass"));
        adress.addComponent(logsAdressTextfield);
        adress.setComponentAlignment(logsAdressTextfield,Alignment.MIDDLE_CENTER);

        Button choose = new ChooseButton(properties,adress,logSelectrLayout,rightSide);
        adress.setSizeFull();
        chooserLayout.addComponent(adress);
        chooserLayout.addComponent(seporator());
        chooserLayout.addComponent(choose);
        chooserLayout.setComponentAlignment(adress, Alignment.TOP_LEFT);
        chooserLayout.setComponentAlignment(choose, Alignment.TOP_RIGHT);
        chooserLayout.setExpandRatio(adress, 3.0f);
        chooserLayout.setExpandRatio(choose, 1.0f);
        chooserLayout.setSizeFull();

        adress.setStyleName("adressStile");

        SerchTextField searchLogs = new SerchTextField(logSelectrLayout,rightSide,properties);
        searchLayout.addComponent(searchLogs);
        searchLayout.setExpandRatio(searchLogs, 1);
        searchLayout.setStyleName("adressStile");
        searchLayout.setSizeFull();
        searchLayout.setComponentAlignment(searchLogs,Alignment.MIDDLE_CENTER);

        LogsListSelect logSelecter = new LogsListSelect(properties, rightSide, null);
        logSelectrLayout.addComponent(logSelecter);
        logSelectrLayout.setSizeFull();
        logSelectrLayout.setExpandRatio(logSelecter, 1);

        chooserLayout.setComponentAlignment(adress, Alignment.TOP_LEFT);
        chooserLayout.setComponentAlignment(choose, Alignment.BOTTOM_RIGHT);

        leftSide.addComponent(chooserLayout);
        leftSide.setExpandRatio(chooserLayout, 1.0f);
        leftSide.addComponent(seporator());
        leftSide.addComponent(logSelectrLayout);
        leftSide.addComponent(seporator());
        leftSide.addComponent(searchLayout);
        leftSide.setExpandRatio(searchLayout, 1.0f);
        leftSide.setSizeFull();
        leftSide.setExpandRatio(logSelectrLayout, 10.0f);

        addComponent(leftSide);
        addComponent(seporator());
        addComponent(rightSide);

        setExpandRatio(leftSide, 2.0f);
        setExpandRatio(rightSide,10.0f);
    }

    private Label seporator() {
        Label sep = new Label();
        sep.setHeight("5px");
        sep.setWidth("5px");
        return sep;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
