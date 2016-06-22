package org.code4j.codecat.api.request;/**
 * Description : 
 * Created by YangZH on 16-6-21
 *  下午3:51
 */

/**
 * Description :
 * Created by YangZH on 16-6-21
 * 下午3:51
 */

public interface Request {

    public void install();

    public String getParam(String key);

    public int getContentLength();

    public String getContentType();

    public int getRemotePort();

    public String getRemoteAddr();

    public String getPath();

    public void setCharacterEncoding(String encoding);

}
