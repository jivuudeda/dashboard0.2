package user.components;

import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import user.logic.Autorization;
import javax.servlet.http.Cookie;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class EnterButton extends Button{

    public EnterButton(final Navigator nav, final Autorization autorization){
        setCaption("Вход");
        addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent clickEvent) {
            if(autorization.isAutorized()){
                File file = new File("/home/user/logs/session.txt");
                try {
                    FileWriter fw=new FileWriter(file);
                    Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();
                    String s=null;
                    for (int i = 0; i < cookies.length; i++) {
                        if ("JSESSIONID".equals(cookies[i].getName())){
                            s=cookies[i].getValue();
                        }
                    }
                    CharSequence cs = s;
                    fw.append(cs);
                    fw.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                nav.navigateTo("logs");
            }
            }
        });
        setClickShortcut(ShortcutAction.KeyCode.ENTER);
    }
}
