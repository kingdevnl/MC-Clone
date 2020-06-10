package nl.kingdev.jcraft.utils;


public class Logger {

    private String name;
    private boolean isDebug = java.lang.management.ManagementFactory.getRuntimeMXBean().
            getInputArguments().toString().indexOf("jdwp") >= 0;

    public Logger(String name) {
        this.name = name;
    }

    public Logger(Class c) {
        this.name = c.getSimpleName();
    }

    public void info(Object o) {
        String callerName = new Exception().getStackTrace()[1].getClassName();
        System.out.println(String.format("[%s] [Info] [%s] > %s", name, callerName, o.toString()));
    }

    public void error(Object o) {
        String callerName = new Exception().getStackTrace()[1].getClassName();
        System.err.println(String.format("[%s] [Error]  [%s] > %s", name, callerName, o.toString()));
    }

    public void debug(Object o) {
        if (isDebug) {
            String callerName = new Exception().getStackTrace()[1].getClassName();
            System.out.println(String.format("[%s] [Debug] [%s] > %s", name, callerName, o.toString()));

        }
    }
}
