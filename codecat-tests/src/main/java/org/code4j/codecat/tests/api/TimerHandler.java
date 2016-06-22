package org.code4j.codecat.tests.api;/**
 * Description : 
 * Created by YangZH on 16-6-17
 *  上午1:03
 */

import org.code4j.codecat.api.container.HttpContainer;
import org.code4j.codecat.api.container.Path;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description :
 * Created by YangZH on 16-6-17
 * 上午1:03
 */
@Path("/index")
public class TimerHandler extends HttpContainer {
    @Override
    public Object service(Object msg) {
        System.out.println("this is a new method!!");
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}
