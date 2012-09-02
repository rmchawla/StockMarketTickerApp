package jython;

public interface GTrendsInterface {
    
    public void connect(String username, String password);
    public void download_report(String keywords);
    public String csv();
    public String getSelfInfo();
    
}