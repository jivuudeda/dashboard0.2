
package user.windows;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class SelectFolderWindow extends VerticalLayout {
    private TextField tf;
    public SelectFolderWindow(Button but) {
        tf = new TextField("введите папку логов");
        tf.setRequired(true);
        tf.setRequiredError("Заполните поле, в случае введения адреса не существующего каталога подключится адрес \" /home/petrik\"");
        
        
        addComponent(tf);
        addComponent(but);
        setComponentAlignment(tf, Alignment.MIDDLE_CENTER);
        setComponentAlignment(but, Alignment.MIDDLE_CENTER);
    }

    public TextField getTf() {
        return tf;
    }
    
}
