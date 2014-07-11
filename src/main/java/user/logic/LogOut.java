package user.logic;

import com.vaadin.event.LayoutEvents;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by user on 07.07.14.
 */
public class LogOut extends HorizontalLayout {
    public LogOut(final Navigator navigator, final Autorization autorization, final String sessionValue) {
        System.out.println("Peredanoe zna4enie sessii "+sessionValue);
        Label exit = new Label("Выход");
        exit.setWidth("39px");
        addComponent(exit);
        setComponentAlignment(exit, Alignment.MIDDLE_CENTER);
        addLayoutClickListener(new LayoutEvents.LayoutClickListener() {
            @Override
            public void layoutClick(LayoutEvents.LayoutClickEvent layoutClickEvent) {
                File file = new File("/home/user/logs/session.txt");
                Scanner in = null;
                String s="";
                try {
                    in = new Scanner(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                while (in.hasNext()){
                    String nextLine = in.nextLine();
                    if (!nextLine.equals(sessionValue)){
                        s+=nextLine;
                    }
                }
                PrintWriter out = null;
                try {
                    out = new PrintWriter("/home/user/logs/session.txt");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                out.println(s);
                out.close();
                autorization.setName("");
                autorization.setPassword("");
                navigator.navigateTo("login");
            }
        });
    }
}
