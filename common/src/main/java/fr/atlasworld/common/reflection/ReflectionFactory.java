package fr.atlasworld.common.reflection;

public class ReflectionFactory<C> {
    public static final StackWalker STACK_WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    public static boolean classExists(String name) {
        try {
            Class.forName(name, false, STACK_WALKER.getCallerClass().getClassLoader());
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
