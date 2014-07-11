package user.components;

import com.jcraft.jsch.*;
import com.vaadin.ui.Button;

import java.io.IOException;
import java.io.InputStream;

public class RestartButton extends Button{
    private String host;
    private String user;
    private String passwd;
    private String adress;
    private int port;
    public RestartButton(String host, String user, String passwd, String adress, int port) {
        this.host=host;
        this.user=user;
        this.passwd=passwd;
        this.adress=adress;
        this.port=port;
        setCaption("Bыполнить перезагрузку сервера");
        addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent clickEvent) {
                restart();
            }
        });
    }

    private void restart() {
        try {
            JSch connector = new JSch();
            String command = "cd "+adress+" && bash rest.sh";
            Session session = connector.getSession(user, host, port);
            session.setPassword(passwd);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            Channel channel=session.openChannel("exec");
            ((ChannelExec)channel).setCommand(command);
            channel.setInputStream(null);
            ((ChannelExec)channel).setErrStream(System.err);
            InputStream in=channel.getInputStream();
            channel.connect();
            byte[] tmp=new byte[1024];
            while(true){
                while(in.available()>0){
                    int i=in.read(tmp, 0, 1024);
                    if(i<0)break;
                }
                if(channel.isClosed()){
                    if(in.available()>0) continue;
                    break;
                }
                try{Thread.sleep(1000);}catch(Exception ee){}
            }
            channel.disconnect();
            session.disconnect();

        } catch (JSchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
