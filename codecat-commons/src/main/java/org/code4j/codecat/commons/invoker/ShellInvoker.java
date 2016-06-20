package org.code4j.codecat.commons.invoker;/**
 * Description : 
 * Created by YangZH on 16-6-14
 *  下午7:46
 */

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Description :
 * Created by YangZH on 16-6-14
 * 下午7:46
 */

public class ShellInvoker {
    public static final String STARTUP_SERVER = System.getProperty("user.dir")+"/bootstrap.sh";
    public static final String KILL_SERVER = System.getProperty("user.dir")+"/kill.sh";

    public static void execute(String cmd,String param){
        System.out.println("command --> "+cmd+" "+param);
        try {
            Process process = Runtime.getRuntime().exec(cmd+" "+param);
            System.out.println("-----------shell 运行结果");
            if(process.waitFor() == 0){
                System.out.println("执行成功！"+process.waitFor());
            }else{
                System.out.println("执行失败！" + process.waitFor());
            }
            BufferedInputStream in = new BufferedInputStream(process.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String lineStr;
            StringBuffer sb = new StringBuffer();
            while ((lineStr = br.readLine()) != null) {
                sb.append(lineStr);
            }
            // 关闭输入流
            br.close();
            in.close();
            System.out.println(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static String execute(String cmd, String param){
//        System.out.println("command --> "+cmd+" "+param);
//        try {
//            Process process = Runtime.getRuntime().exec(cmd+" "+param);
//            if(process.waitFor() == 0){
//                System.out.println("执行成功！"+process.waitFor());
//            }else{
//                System.out.println("执行失败！" + process.waitFor());
//            }
//            BufferedInputStream in = new BufferedInputStream(process.getInputStream());
//            BufferedReader br = new BufferedReader(new InputStreamReader(in));
//            String lineStr;
//            StringBuffer sb = new StringBuffer();
//            while ((lineStr = br.readLine()) != null) {
//                sb.append(lineStr);
//            }
//            // 关闭输入流
//            br.close();
//            in.close();
//            return sb.toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
