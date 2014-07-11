package user.components;

import com.vaadin.data.Property;
import com.vaadin.data.util.FilesystemContainer;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import java.util.Scanner;

public class LogsListSelect extends ListSelect{

    public FilesystemContainer getLogsFiles() {
        return logsFiles;
    }

    public void setLogsFiles(FilesystemContainer logsFiles) {
        this.logsFiles = logsFiles;
    }

    private FilesystemContainer logsFiles;

    public LogsListSelect(final Properties properties, final VerticalLayout rightSide, FilenameFilter filter) {
        setSizeFull();
        logsFiles = new FilesystemContainer(new File((String)properties.getProperty("pass")));
        logsFiles.setFilter(filter);
        if(filter == null) {
            FilenameFilter fn = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                File f = new File(dir.getAbsolutePath() + "/" + name);
                if (dir.getAbsolutePath().equals((String) properties.getProperty("pass")) && f.canRead()) {
                    return true;
                } else {
                    return false;
                }
                }
            };
            logsFiles.setFilter(fn);
        }
        Collection files = logsFiles.getItemIds();
        ArrayList<File> logs = new ArrayList();
        logs.addAll(files);

        for (File file :  logs) {
            if (!file.isDirectory()){
                addItems(file.getName());
            }
        }
        setNullSelectionAllowed(false);
        addStyleName("logsSelector");
        addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
            File f = new File((String)properties.getProperty("pass")+"/"+getValue());
            TextArea textArea=(TextArea) rightSide.getComponent(0);
            String s = "";
            Scanner in = null;
            try {
                in = new Scanner(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            while (in.hasNext()){
                s+= in.nextLine() + "\r\n";
            }
            in.close();
            textArea.setValue(s);
            }
        });
    }
}
