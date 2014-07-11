package user.components;

import com.vaadin.data.Property;
import com.vaadin.event.FieldEvents;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import user.logic.Autorization;

public class MyPasswordField extends AbstractTextField {
private AbstractTextField passwordComponent;

    public AbstractTextField getPasswordComponent() {
        return passwordComponent;
    }

    public void setPasswordComponent(AbstractTextField passwordComponent) {
        this.passwordComponent = passwordComponent;
    }

    public AbstractTextField getPasswordComponent(boolean isHidden, String value, final Autorization autorization) {
        if (isHidden) {
            passwordComponent = new TextField();
            passwordComponent.setValue(value);
        }
        else{
            passwordComponent = new PasswordField();
            passwordComponent.setValue(value);
        }
        passwordComponent.addFocusListener(
            new FieldEvents.FocusListener() {
            @Override
            public void focus(FieldEvents.FocusEvent event) {
                if ("Пароль".equals(passwordComponent.getValue())) {
                    passwordComponent.setValue("");
                }
            }
        });
        passwordComponent.addBlurListener(new FieldEvents.BlurListener() {

            @Override
            public void blur(FieldEvents.BlurEvent event) {
            if (passwordComponent.getValue().isEmpty()) {
                passwordComponent.setValue("Пароль");
            }
            }
        });
        passwordComponent.addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                autorization.setPassword(passwordComponent.getValue());
            }
        });
        return passwordComponent;
    }
    
    public MyPasswordField() {
        
    }
    
}
