package gavin.model;

public class AppVersion
{
    private String version;
    private String summary;
    private int force_update;
    private String download_url;
    public String getVersion()
    {
        return version;
    }
    public void setVersion(String version)
    {
        this.version = version;
    }
    public String getSummary()
    {
        return summary;
    }
    public void setSummary(String summary)
    {
        this.summary = summary;
    }
    public int getForce_update()
    {
        return force_update;
    }
    public void setForce_update(int force_update)
    {
        this.force_update = force_update;
    }
    public String getDownload_url()
    {
        return download_url;
    }
    public void setDownload_url(String download_url)
    {
        this.download_url = download_url;
    }
}
