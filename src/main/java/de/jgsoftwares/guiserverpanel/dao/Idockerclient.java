package de.jgsoftwares.guiserverpanel.dao;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;
import java.util.List;

/**
 *
 * @author hoscho
 */
public interface Idockerclient {
    public void listContainers();
    public void startdockerclient();
    public void startlanservercontiner(String struncontainer);
    public void restartlanserver(String strestartcontainer);
    public void setsystemtimetolanservertcp();
    
    public void startderbydb(String stderbydb);
    public void starth2db();
    public void startmysqldb();
    
    public void startlandingpage(String stlandingpage);
    public void startopenwrtcloudflaredmz();
    public void startopenwrtopendns();
    public void pullImage(String imageName);
    
    public DockerClient getDockerClient();
    public List<Image> getDockerimages();
    public List<Container> getDockercontainers();
   
    public String getMenuItem(String stcontainername, String stcontainerid);
    
    public void startcontainerdockerclient(String stdockerclient);
    public void stopcontainerdockerclient(String stdockerclient);
    public void restartcontainerdockerlclient(String stdockerclient);
    public void removecontainerdockerclient(String stdockerclient);
    public void loadDockerimage(String url);
    
}
