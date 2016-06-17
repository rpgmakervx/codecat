package org.code4j.codecat.tests.monitor.pojo;/**
 * Description : 
 * Created by YangZH on 16-6-16
 *  下午9:25
 */

import java.util.ArrayList;
import java.util.List;

/**
 * Description :
 * Created by YangZH on 16-6-16
 * 下午9:25
 */

public class User implements IUser{

    private String name;
    private List<String> numbers = new ArrayList<>();

    public User(){
        System.out.println("init without param");
    }

    public User(String name) {
        System.out.println("init with param : "+name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addNumber(String number){
        numbers.add(number);
    }

    public void showNumbers(){
        for (String number:numbers){
            System.out.println("number : "+number);
        }
        sayHello();
    }

    public void sayHello(){
        System.out.println("hello world!");
    }
}
