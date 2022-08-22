package dev.mccue.log.alpha.bean;

import dev.mccue.log.alpha.Log;

import java.lang.invoke.MethodHandles;
import java.util.List;

public final class BeanLog {
    private BeanLog() {}

    public static List<Log.Entry> beanToEntries(MethodHandles.Lookup lookup, Object o) {
        try {
            var klass = lookup.lookupClass();
            for (var method : klass.getMethods()) {
                if ((method.getName().startsWith("get") || method.getName().startsWith("is"))
                        && method.getGenericParameterTypes().length == 0) {
                    lookup.findGetter(Void.class, "", Void.class);
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return List.of();
    }
}
