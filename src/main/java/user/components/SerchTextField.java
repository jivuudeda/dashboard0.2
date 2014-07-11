package user.components;

import com.vaadin.event.FieldEvents;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Properties;

public class SerchTextField extends TextField{

    private FilenameFilter fn;

    public SerchTextField(final HorizontalLayout logSelectrLayout,  final VerticalLayout rightSide, final Properties properties) {
        setWidth("90%");
        setInputPrompt("Search");
        setTextChangeEventMode(TextChangeEventMode.EAGER);
        addTextChangeListener(new FieldEvents.TextChangeListener() {
            public void textChange(final FieldEvents.TextChangeEvent event) {
            createSearch(event.getText(), logSelectrLayout,properties);
            logSelectrLayout.removeAllComponents();
            logSelectrLayout.addComponent(new LogsListSelect(properties, rightSide, fn));
            }
        });
    }

    private void createSearch(final String key, HorizontalLayout logSelect, final Properties properties){
        fn = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
            File f = new File(dir.getAbsolutePath()+"/"+name);
            if (name.toLowerCase().contains(key.toLowerCase())&&dir.getAbsolutePath().equals((String)properties.getProperty("pass"))&& f.canRead()) {
                return true;
            } else {
                return false;
            }
            }
        };
    }
}
