package gavin.net;

import java.io.File;

public class UploadRequest extends BaseRequest
{
    private File file;

    public File getFile()
    {
        return file;
    }

    public void setFile(File file)
    {
        this.file = file;
    }
}
