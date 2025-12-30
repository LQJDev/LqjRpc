package com.lqj.server;

import com.lqj.bean.URL;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author 李岐鉴
 * @Date 2025/12/29
 * @Description 注册服务到注册中心
 */
public class MapRemoteRegister {

    private static Map<String, List<URL>> remoteMap = new HashMap<>();

    static {
        try {
            Map<String, List<URL>> mapByFile = getMapByFile();
            if (null != mapByFile) {
                remoteMap.putAll(mapByFile);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void register(String instanceName, URL url) {
        remoteMap.computeIfAbsent(instanceName, k -> new ArrayList<URL>()).add(url);
        saveFile();
    }

    public static List<URL> getURLs(String interfaceName) throws IOException, ClassNotFoundException {
        return remoteMap.get(interfaceName);
    }

    public static Map<String, List<URL>> getMapByFile() throws IOException, ClassNotFoundException {
        Object o = new ObjectInputStream(Files.newInputStream(Paths.get("./temp.txt"))).readObject();
        return (Map<String, List<URL>>) o;
    }

    public static void saveFile() {
         try {
            new ObjectOutputStream(Files.newOutputStream(Paths.get("./temp.txt"))).writeObject(remoteMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
