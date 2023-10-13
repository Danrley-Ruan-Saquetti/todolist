package com.esliph.todolist.utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapperImpl;

public class Utils {
    public static void copyNonNullProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    public static String[] getNullPropertyNames(Object source) {
        var src = new BeanWrapperImpl(source);

        var pds = src.getPropertyDescriptors();

        var emptyNames = new HashSet<>();

        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());

            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }

        var result = new String[emptyNames.size()];

        return emptyNames.toArray(result);
    }
}