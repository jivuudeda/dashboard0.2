package user.components;

import com.vaadin.ui.*;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user on 08.07.14.
 */
public class ChooseButton extends Button {

    public ChooseButton(final Properties properties, final HorizontalLayout adress, final HorizontalLayout logSelectrLayout, final VerticalLayout rightSide) {
        setCaption("Ok");
        setHeight("40px");
        addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent clickEvent) {
                TextField newAdress = (TextField) adress.getComponent(0);

                if (valueChek(newAdress.getValue())) {
                    properties.setProperty("pass", newAdress.getValue());
                    logSelectrLayout.removeAllComponents();
                    logSelectrLayout.addComponent(new LogsListSelect(properties,rightSide,null));
                }
                else{
                    newAdress.setValue("bad");
                }
            }


        });
    }
    private boolean valueChek(String value) {
        Pattern p;
        p = Pattern.compile("^(\\/([0-9A-Za-zА-Яа-я[.]]*))*");
        Matcher m = p.matcher(value);
        return m.matches();
    }
}
