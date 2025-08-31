package singleton;

public class LazySingleton {

    private static LazySingleton instance;

    private LazySingleton() {

    }

    public static LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }
    
    public static void main(String[] args) {

        LazySingleton obj1 = LazySingleton.getInstance();
        LazySingleton obj2 = LazySingleton.getInstance();
        System.out.println(obj1 == obj2);
        System.out.println(obj1.hashCode());
        System.out.println(obj2.hashCode());
    }
}
