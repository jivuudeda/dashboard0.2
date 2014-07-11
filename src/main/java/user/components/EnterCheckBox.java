package user.components;


import com.vaadin.data.Property;
import com.vaadin.ui.*;
import user.logic.Autorization;


public class EnterCheckBox extends CheckBox{
    public EnterCheckBox (final VerticalLayout panelFields, final Autorization autorization){
        setWidth("100px");
        setCaption("показать");
        final TextField login = (TextField) panelFields.getComponent(0);
        final Label sep = (Label) panelFields.getComponent(1);
        final AbstractTextField password= (AbstractTextField) panelFields.getComponent(2);
        addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
            if (getValue()){
                String pas = ((AbstractTextField) panelFields.getComponent(2)).getValue();
                setCaption("скрыть  ");
                panelFields.removeAllComponents();
                panelFields.addComponent(login);
                 panelFields.addComponent(sep);
                final AbstractTextField newPassword=new MyPasswordField().getPasswordComponent(getValue(), pas, autorization);
                panelFields.addComponent(newPassword);
                panelFields.setComponentAlignment(login,Alignment.MIDDLE_CENTER);
                panelFields.setComponentAlignment(newPassword,Alignment.MIDDLE_CENTER);
            }
            else{
                String pas = ((AbstractTextField) panelFields.getComponent(2)).getValue();
                setCaption("показать");
                panelFields.removeAllComponents();
                panelFields.addComponent(login);
                panelFields.addComponent(sep);
                final AbstractTextField newPassword=new MyPasswordField().getPasswordComponent(getValue(),pas, autorization);
                panelFields.addComponent(newPassword);
                panelFields.setComponentAlignment(login,Alignment.MIDDLE_CENTER);
                panelFields.setComponentAlignment(newPassword,Alignment.MIDDLE_CENTER);
            }
            }
        });
    }
}
