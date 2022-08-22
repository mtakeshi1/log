package dev.mccue.log.alpha.bean;

import dev.mccue.log.alpha.Log;

import java.lang.invoke.MethodHandles;
import java.util.List;

public final class BeanLog {
    private BeanLog() {}

    public static List<Log.Entry> beanToEntries(MethodHandles.Lookup lookup, Object o) {
        try {
            lookup.findGetter(o.getClass(), "", o.getClass());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return List.of();
    }
}
