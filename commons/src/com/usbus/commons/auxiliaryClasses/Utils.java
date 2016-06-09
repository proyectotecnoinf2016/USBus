package com.usbus.commons.auxiliaryClasses;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jpmartinez on 08/06/16.
 */
public class Utils {
    public static <T extends Enum<T>> List<T> enumListFromStringList(Class<T> clazz, List<String> values) {
        List<T> list = new ArrayList<>();
        for (String level : values) {
            list.add(Enum.valueOf(clazz, level));
        }
        return list;
    }

    public static <T extends Enum<T>>  List<String> stringListFromEnumList(Class<T> clazz, List<T> values) {
//        List<T> list = new ArrayList<>();
        List<String> list= new ArrayList<>();

        for (T level : values) {
            list.add(level.toString());
        }
        return list;
    }
}
