package user.layouts;

import com.vaadin.event.LayoutEvents;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;
import user.logic.Autorization;
import user.logic.LogOut;

import java.util.ArrayList;
import java.util.Iterator;

public class MenuItemLayout extends VerticalLayout {

    public MenuItemLayout(final ArrayList<LayoutPage> pages, Autorization autorization, final Navigator navigator,String sessionValue) {
        setSizeFull();
        Iterator iter = pages.iterator();
        addComponent(boarderSeporator());
        final HorizontalLayout menu = new HorizontalLayout();
        menu.setSizeFull();
        menu.setHeight("47px");
        menu.addComponent(boarderSeporator());
        final HorizontalLayout page = new HorizontalLayout();
        page.addComponent(boarderSeporator());
        HorizontalLayout horizontalLayout = (HorizontalLayout) pages.get(0).getLayout();
        horizontalLayout.setSizeFull();
        page.addComponent(horizontalLayout);
        page.addComponent(boarderSeporator());
        page.setExpandRatio(horizontalLayout,1.0f);
        page.setSizeFull();
        final ArrayList<HorizontalLayout> menuItems = new ArrayList<HorizontalLayout>();
        while(iter.hasNext()){
            final LayoutPage layoutPage =(LayoutPage) iter.next();
            Label caption= new Label(layoutPage.getName());
            caption.setWidth("100px");
            final HorizontalLayout menuItem = new HorizontalLayout();
            menuItem.setSizeFull();
            menuItem.addComponent(caption);
            menuItem.setComponentAlignment(caption, Alignment.MIDDLE_CENTER);
            menuItem.setStyleName("pagesMenuItem");
            menuItem.addLayoutClickListener(new LayoutEvents.LayoutClickListener() {
                @Override
                public void layoutClick(LayoutEvents.LayoutClickEvent layoutClickEvent) {
                if (layoutPage.getName().equals("Настройки")){
                    navigator.navigateTo("settings");
                }
                else{
                    page.removeAllComponents();
                    View layout = layoutPage.getLayout();
                    page.addComponent(boarderSeporator());
                    page.addComponent((Component) layout);
                    page.addComponent(boarderSeporator());
                    page.setComponentAlignment((Component) layout, Alignment.MIDDLE_CENTER);
                    page.setExpandRatio((Component) layout,1.0f);
                    Iterator menuItemIterator = menuItems.iterator();
                    while(menuItemIterator.hasNext()){
                        HorizontalLayout mI = (HorizontalLayout) menuItemIterator.next();
                        mI.setStyleName("pagesMenuItem");
                    }
                    menuItem.addStyleName("selectedMenuItem");
                }

                }

            });
            menu.addComponent(menuItem);
            menu.setExpandRatio(menuItem, 1.0f);
            menu.addComponent(seporator());
            menuItems.add(menuItem);
        }
        menuItems.get(0).setStyleName("selectedMenuItem");
        LogOut logOut = new LogOut(navigator,autorization,sessionValue);
        logOut.setWidth("75px");
        logOut.setHeight("47px");
        logOut.setStyleName("pagesMenuItem");
        menu.addComponent(logOut);
        menu.addComponent(boarderSeporator());
        addComponent(menu);
        addComponent(boarderSeporator());
        addComponent(boarderSeporator());
        addComponent(boarderSeporator());
        addComponent(page);
        addComponent(boarderSeporator());
        setExpandRatio(menu,1.0f);
        setExpandRatio(page,10.0f);
    }

    private Label seporator() {
        Label sep = new Label();
        sep.setWidth("1px");
        sep.setHeight("1px");
        return sep;
    }

    private Label boarderSeporator() {
        Label sep = new Label();
        sep.setWidth("5px");
        sep.setHeight("5px");
        return sep;
    }
}
