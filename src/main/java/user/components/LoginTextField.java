package user.components;

import com.vaadin.data.Property;
import com.vaadin.event.FieldEvents;
import com.vaadin.ui.TextField;
import user.logic.Autorization;

public class LoginTextField extends TextField {
private final TextField name;

    public TextField getLoginComponent() {
        return name;
    }

    public LoginTextField(final Autorization autorization) {
        name = new TextField();
        name.setValue("Login");
        name.addFocusListener(
            new FieldEvents.FocusListener() {
            @Override
            public void focus(FieldEvents.FocusEvent event) {
                if ("Login".equals(name.getValue())) {
                    name.setValue("");
                    
                }
            }
        });
        name.addBlurListener( new FieldEvents.BlurListener() {

            @Override
            public void blur(FieldEvents.BlurEvent event) {
            if (name.getValue().isEmpty()) {
                name.setValue("Login");
            }
            }
        });
        name.addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                autorization.setName(name.getValue());
            }
        });
    }
}
