package user.layouts;

import com.vaadin.navigator.View;

public class LayoutPage {
    private String name;
    private View layout;

    public LayoutPage(String name, View layout){
        this.name=name;
        this.layout=layout;
    }

    public View getLayout() {
        return layout;
    }

    public void setLayout(View layout) {
        this.layout = layout;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
