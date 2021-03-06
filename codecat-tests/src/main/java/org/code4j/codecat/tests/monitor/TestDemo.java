package org.code4j.codecat.tests.monitor; /**
 * Description : 
 * Created by YangZH on 16-6-16
 *  上午1:06
 */

import net.contentobjects.jnotify.JNotify;
import org.code4j.codecat.commons.constants.Const;
import org.code4j.codecat.commons.realserver.IRealServer;
import org.code4j.codecat.commons.util.PortCounter;
import org.code4j.codecat.commons.util.XmlUtil;
import org.code4j.codecat.listener.Listener;
import org.code4j.codecat.realserver.dynamicproxy.factory.ProxyFactory;
import org.code4j.codecat.realserver.load.JarLoader;
import org.code4j.codecat.realserver.server.RealServer;
import org.code4j.codecat.tests.monitor.dyproxy.Handler;
import org.code4j.codecat.tests.monitor.pojo.IUser;
import org.code4j.codecat.tests.monitor.pojo.User;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description :
 * Created by YangZH on 16-6-16
 * 上午1:06
 */

public class TestDemo {

    @Test
    public void testProperties() throws IOException {
        Properties properties = new Properties();
//        System.out.println(String.valueOf(Boolean.TRUE));
        properties.load(new FileInputStream("/home/code4j/osproject/codecat/bin/../apps/app.properties"));
//        properties.load(new FileInputStream("/home/code4j/osproject/codecat/bin/../apps/app.properties"));
        System.out.println("ConfigExists = "+properties.getProperty("ConfigExists"));
        System.out.println("PluginExists = "+properties.getProperty("PluginExists"));
//
//        properties.setProperty("ConfigExists","true");
//        properties.setProperty("PluginExists",properties.getProperty("PluginExists"));
//        properties.store(new FileOutputStream("/home/code4j/osproject/codecat/bin/../apps/app.properties"),"");
    }

    @Test
    public void testReflection(){
        Class clazz = User.class;
        try {
            User user = (User) clazz.newInstance();
            Field field = clazz.getDeclaredField("numbers");
            field.setAccessible(true);
            List<String> numbers = (List<String>) field.get(user);
            numbers.add("123456");
            user.showNumbers();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testReflection2(){
        try {
            // 创建一个URL数组
            URL[] urls = new URL[]{new URL("file:/home/code4j/osproject/codecat/apps/User.class")};
            // 以默认的ClassLoader作为父ClassLoader，创建URLClassLoader
            URLClassLoader classLoader = new URLClassLoader(urls);
            Class<? extends IUser> clazz =
                    (Class<? extends IUser>) classLoader.loadClass(User.class.getName());
            IUser user = clazz.newInstance();
            user.sayHello();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testReflection3() throws Exception {
        Class<? extends User> clazz = User.class;
        System.out.println(clazz == User.class);
        Constructor<? extends User> constructor = clazz.getConstructor(String.class);
        constructor.newInstance("xingtianyu");
    }

    @Test
    public void testProxy(){
        User user = new User("");
        user.addNumber("xxxx");
        user.addNumber("yyyy");
        user.addNumber("zzzz");
        Handler handler = new Handler(user);
        IUser u = (IUser) Proxy.newProxyInstance(
                user.getClass().getClassLoader(),
                user.getClass().getInterfaces(),
                handler);

        u.showNumbers();
        System.out.println("\n--------------\n");
        u.sayHello();
    }

    @Test
    public void testRealServerDelegate() throws InterruptedException {
        ProxyFactory factory = new ProxyFactory();
        RealServer server = new RealServer();
        IRealServer delegateServer = factory.getProxy(server
                ,new JarLoader("/home/code4j/osproject/codecat/apps/"
                ,"handler.jar"),"org.code4j.test.TimerHandler");
        delegateServer.initHandler();
        delegateServer.setup();
        delegateServer.launch(PortCounter.incr());
    }

    @Test
    public void testUrlLoad(){
        try {
            // 创建一个URL数组
            URL[] urls = new URL[]{new URL("file:/home/code4j/osproject/codecat/apps/handler.jar")};
            // 以默认的ClassLoader作为父ClassLoader，创建URLClassLoader
            URLClassLoader classLoader = new URLClassLoader(urls);
            Class clazz = (Class) classLoader.loadClass("org.code4j.test.TimerHandler");
            System.out.println(clazz.getResource(Const.APPXML).getPath());
            XmlUtil util = new XmlUtil(clazz.getResourceAsStream(Const.APPXML));
            System.out.println(util.getTextByTagName(Const.ROOT_PATH));
//
// 创建一个设置JDBC连接属性的Properties对象
//            Properties props = new Properties();
//            // 至少需要为该对象传入user和password两个属性
//            props.setProperty("user" , "root");
//            props.setProperty("password" , "xingtianyu");
//            // 调用Driver对象的connect方法来取得数据库连接
//            Connection conn = driver.connect("jdbc:mysql://localhost:3306/mysql" , props);
//            Statement statement = (Statement) conn.createStatement();
//            System.out.println(statement.execute("show databases"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testReg(){
        Pattern pattern = Pattern.compile(".*\\.(class|jar)");
        Matcher matcher = pattern.matcher("xxx.text.jars");
        System.out.println(matcher.matches());

        String path = "/root/user/index";
        System.out.println();

        System.out.println(path.substring(path.lastIndexOf(path.split(File.separator)[1])+path.split(File.separator)[1].length()));;
    }

    @Test
    public void testJar() throws IOException {
        Pattern pattern = Pattern.compile(Const.APPXML);
        JarFile jarFile = new JarFile("/home/code4j/osproject/codecat/apps/handler.jar");
        Enumeration<JarEntry> entrys = jarFile.entries();
        Matcher matcher = null;
        while (entrys.hasMoreElements()) {
            JarEntry jarEntry = entrys.nextElement();
            String filename = jarEntry.getName();
            if (matcher == null){
                matcher = pattern.matcher(filename);
            }else{
                matcher.reset(filename);
            }
            if (matcher.matches()){
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(jarFile.getInputStream(jarEntry)));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null){
                    buffer.append(line);
                }
                System.out.println(buffer.toString());
            }

        }
    }

    @Test
    public void testSeperator(){
        String[] segement = "/handler/index".split(File.separator);
        System.out.println(segement[0]);

//        Map<String,String> map = new HashMap<>();
//        map.put("","xingtianyu");
//        System.out.println(map.get(""));
//        String plugin_name = "/dd/jar.jar";
//        System.out.println(plugin_name.substring(0, plugin_name.lastIndexOf(Const.DOT)));
    }




    @Test
    public synchronized void testNotify() throws Exception {
        int mask = JNotify.FILE_CREATED |
                JNotify.FILE_DELETED |
                JNotify.FILE_MODIFIED |
                JNotify.FILE_RENAMED;
        boolean watchSubtree = true;
        int watchID = JNotify.addWatch("/home/code4j/osproject/codecat/apps", mask, watchSubtree, new Listener());
        wait();
    }
}
