package singleton;

public class LazySingletonWithThreadSafe {
    
    private static  volatile LazySingletonWithThreadSafe instance;

    // private constructor to avoid client applications to use constructor

    private LazySingletonWithThreadSafe() {
            if (instance != null) {
                throw new RuntimeException("Use getInstance() method to create");
            }
    }

    public static synchronized LazySingletonWithThreadSafe getInstance() {
        if (instance == null) {
           synchronized (LazySingletonWithThreadSafe.class) {
            if (instance == null) {
                instance = new LazySingletonWithThreadSafe();
            }
           }
        }
        return instance;
    }


    // to avoid creating new instance during deserialization
    protected Object readResolve() {
    return getInstance();
}
    
    public static void main(String[] args) {

        LazySingletonWithThreadSafe obj1 = LazySingletonWithThreadSafe.getInstance();
        LazySingletonWithThreadSafe obj2 = LazySingletonWithThreadSafe.getInstance();
        System.out.println(obj1 == obj2);
        System.out.println(obj1.hashCode());
        System.out.println(obj2.hashCode());
    }
}
