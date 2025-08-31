# design-pattern
It has almost all the design pattern

**Singleton Design Pattern**
✅ 1. BASIC QUESTIONS

**Q1: What is Singleton?**
👉 A design pattern where only one instance of a class exists in the JVM and is globally accessible.

**Q2: Why use it?**
👉 To manage shared resources like:

Logging
Configuration
Caching
Thread pools
DB connection pools

**Q3: Eager vs Lazy Initialization?**

Type	Description
Eager	Instance is created at class load time.
Lazy	Instance is created only when needed (on first call).
Eager is simple, thread-safe, but may waste resources.
Lazy saves memory but needs thread safety.

**Q4: Singleton vs Static Class?**

Feature	Singleton	Static Class
Inheritance	✅ Can implement interfaces	❌ Cannot
Lifecycle	Managed	Always loaded
State	Can be stateful	Usually stateless
✅ 2. THREAD SAFETY

**Q5: How to make Singleton thread-safe?**
👉 Use:
synchronized method
Double-checked locking with volatile
Bill Pugh inner static class
Enum

**Q6: Why use volatile in double-checked locking?**
👉 Prevents instruction reordering that can cause a partially constructed object to be visible to other threads.

✅ 3. PITFALLS AND FIXES
🔸 Reflection

**Q7: How can reflection break Singleton?**
👉 Reflection can bypass private constructor to create multiple instances.

Fix: Add guard in constructor:

private static EagerSingleton instance;

private EagerSingleton() {
    if (instance != null) {
        throw new RuntimeException("Use getInstance()");
    }
}

🔸 Serialization

**Q8: How does serialization break Singleton?**
👉 Deserializing creates a new instance.

Fix: Implement readResolve():

protected Object readResolve() {
    return getInstance();
}

🔸 Cloning

**Q9: How does cloning break Singleton?**
👉 clone() creates a new object.

Fix: Override clone():

@Override
protected Object clone() throws CloneNotSupportedException {
    throw new CloneNotSupportedException();
}

🔸 Class Loaders

**Q10: How can multiple class loaders break Singleton?**
👉 Each class loader can create its own Singleton instance.

Fix: Use a common class loader or Spring context.

🔸 Testing

**Q11: Why is Singleton hard for unit testing?**
👉 Hard to mock or replace. Global state may cause test dependencies.

Fix: Use Dependency Injection (DI) instead of manual Singleton.

🔸 Overuse

**Q12: When should you avoid Singleton?**

Stateful objects (e.g., UserSession)

In distributed systems (each JVM has its own)

When using frameworks like Spring (already manages lifecycle)

✅ 4. CODE PITFALLS & IMPLEMENTATIONS
🧨 Your Code Issue:
public static EagerInitSingleTon getInstance() {
    if (instance == null) {
        return new EagerInitSingleTon(); // BAD
    }
    return instance;
}


Problem: Even in eager init, you're manually creating a new instance inside getInstance() — breaks Singleton.

✅ Correct Implementations:

1. Eager Initialization (Simple, thread-safe)

public class EagerSingleton {
    private static final EagerSingleton instance = new EagerSingleton();
    private EagerSingleton() { }
    public static EagerSingleton getInstance() {
        return instance;
    }
}


2. Lazy Initialization (Not thread-safe)

public class LazySingleton {
    private static LazySingleton instance;
    private LazySingleton() { }
    public static LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }
}


3. Double-Checked Locking (Thread-safe + lazy)

public class ThreadSafeSingleton {
    private static volatile ThreadSafeSingleton instance;
    private ThreadSafeSingleton() { }

    public static ThreadSafeSingleton getInstance() {
        if (instance == null) {
            synchronized (ThreadSafeSingleton.class) {
                if (instance == null) {
                    instance = new ThreadSafeSingleton();
                }
            }
        }
        return instance;
    }
}


4. Bill Pugh Singleton (Best Practice)

public class BillPughSingleton {
    private BillPughSingleton() { }

    private static class Helper {
        private static final BillPughSingleton INSTANCE = new BillPughSingleton();
    }

    public static BillPughSingleton getInstance() {
        return Helper.INSTANCE;
    }
}


5. Enum Singleton (Best for serialization/reflection safety)

public enum EnumSingleton {
    INSTANCE;
    public void doSomething() {
        System.out.println("Doing something...");
    }
}

✅ 5. SCENARIO-BASED QUESTIONS

**Q13: Where would you use Singleton?**
👉 Logging, configuration, cache manager, thread pool manager, DB connection pool, service registries.

**Q14: Should you use Singleton in Microservices?**
👉 Be careful — each service instance has its own Singleton. Use external coordination (e.g., Redis, config servers) for shared state.

**Q15: What if Singleton holds state (e.g., user session)?**
👉 Risky — can lead to race conditions. Keep Singleton stateless.

**Q16: How to use Singleton in distributed systems?**
👉 Use external systems like:

ZooKeeper
Redis
Database locks

**Q17: Can Singleton violate SOLID?**
👉 Yes:

Violates Single Responsibility (does both creation & business logic)

Violates Open/Closed (hard to extend)

**Q18: Do we need Singleton in Spring?**
👉 No — Spring beans are Singleton by default (per ApplicationContext).

**Q19: How would you reduce tight coupling caused by Singleton?**
👉 Use Dependency Injection — inject Singleton instead of calling getInstance() directly.

**Q20: What’s the best Singleton for high concurrency?**
👉 Bill Pugh or Enum — thread-safe, performant, and simple.

✅ 6. ADVANCED / ARCHITECTURE-LEVEL QUESTIONS

**Q21: What are the risks of using Singleton for DB connection?**
👉 If DB is down, Singleton can hold stale/broken state. Use connection pools instead.

**Q22: What other patterns can replace Singleton for global access?**
👉 Alternatives:

Dependency Injection

Service Locator

**Q23: Can Singleton cause memory leaks?**
👉 Yes — if it holds references to objects that should be garbage collected.

✅ SUMMARY FOR LEAD ROLE
As a lead, focus on:
Thread safety & concurrency
Design trade-offs (memory vs safety)
Distributed system behavior
Pitfalls (cloning, serialization, reflection)
Dependency injection vs manual Singleton
When not to use Singleton (stateless vs stateful)

Best implementation based on use-case (Bill Pugh, Enum)
