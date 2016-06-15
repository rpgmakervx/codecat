package org.code4j.codecat.pojo;/**
 * Description : 
 * Created by YangZH on 16-6-15
 *  下午12:35
 */

import org.apache.log4j.Logger;
import org.code4j.codecat.constants.Const;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Description :
 * Created by YangZH on 16-6-15
 * 下午12:35
 */
@Deprecated
public class Manifest {

    private Logger logger = Logger.getLogger(Manifest.class);

    private String path;

    private FileReader fileReader;
    private FileWriter fileWriter;

    private Map<String,String> manifestmapper;

    public Manifest(File file){
        try {
            if (!file.exists()){
                file.createNewFile();
            }
            fileReader = new FileReader(file);
            fileWriter = new FileWriter(file);
            manifestmapper = new HashMap<>();
            //init MANIFEST.MF
            if (!file.exists()){
                setKeyValue(Const.CONFIGEXISTS,String.valueOf(Boolean.FALSE));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    public void load(){
        StringBuffer buffer = new StringBuffer();
        String line = "";
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        try {
            while ((line = bufferedReader.readLine()) == null){
                String[] args = line.split(":");
                manifestmapper.put(args[0].trim(),args[1].trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int getIntValue(String key){
        return Integer.valueOf(manifestmapper.get(key));
    }

    public String getString(String key){
        return manifestmapper.get(key);
    }

    public boolean getBoolValue(String key){
        return Boolean.valueOf(manifestmapper.get(key));
    }

    public void setKeyValue(String key,String value){
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        String keyvalue = key.trim()+":"+value.trim();
        try {
            bufferedWriter.write(keyvalue+Const.NEWLINE);
            bufferedWriter.flush();
            logger.info(keyvalue+" has writen into MANIFEST.MF");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

}
