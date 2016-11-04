package gavin.net;

import java.util.List;

public class PagingResponse extends BaseResponse
{
    private boolean hasMore;

    private List<?> list;

    public boolean isHasMore()
    {
        return hasMore;
    }

    public void setHasMore(boolean hasMore)
    {
        this.hasMore = hasMore;
    }

    public List<?> getList()
    {
        return list;
    }

    public void setList(List<?> list)
    {
        this.list = list;
    }
}
