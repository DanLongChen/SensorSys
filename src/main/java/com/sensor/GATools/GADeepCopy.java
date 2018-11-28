package com.sensor.GATools;

import java.io.*;
import java.util.List;

/**
 * Created by DanLongChen on 2018/11/28
 **/
@SuppressWarnings("unckecked")
public class GADeepCopy{//对List列表进行深度拷贝工具
    public static <T> List<T> deepCopyList(List<T> list) {
        List<T> returnList = null;
        try {
            ByteArrayOutputStream osrc = new ByteArrayOutputStream();
            ObjectOutputStream outputStream = new ObjectOutputStream(osrc);
            outputStream.writeObject(list);

            ByteArrayInputStream isrc = new ByteArrayInputStream(osrc.toByteArray());
            ObjectInputStream inputStream = new ObjectInputStream(isrc);
            returnList = (List<T>) inputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return returnList;
    }
}
