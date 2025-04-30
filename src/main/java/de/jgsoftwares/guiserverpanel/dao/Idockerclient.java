/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.jgsoftwares.guiserverpanel.dao;

/**
 *
 * @author root
 */
public interface Idockerclient {
    public void listContainers();
    public void startdockerclient();
    public void startlanservercontiner(String struncontainer);
    public void restartlanserver(String strestartcontainer);
    public void setsystemtimetolanservertcp();
    
    public void startderbydb();
    public void starth2db();
    public void startmysqldb();
    
    public void startlandingpage();
    public void startopenwrtcloudflaredmz();
    public void startopenwrtopendns();
    public void pullImage(String imageName);
}
