package gavin.net;

import gavin.model.AppVersion;

public class AppVersionResponse extends BaseResponse
{

    private AppVersion data;

    public AppVersion getData() {
        return data;
    }

    public void setData(AppVersion data) {
        this.data = data;
    }

}
