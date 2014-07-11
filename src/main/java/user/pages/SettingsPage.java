package user.pages;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import java.util.Properties;

public class SettingsPage extends HorizontalLayout implements View {
    private Properties properties;

    public SettingsPage(Properties properties) {
        this.properties=properties;
        addComponent(new Label("1"));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
