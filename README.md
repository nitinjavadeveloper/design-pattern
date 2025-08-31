<div class="markdown prose dark:prose-invert w-full break-words light markdown-new-styling"><p data-start="0" data-end="192">Here's a <strong data-start="9" data-end="94">complete and organized list of all Singleton Design Pattern questions and answers</strong> from your conversation — tailored for a <strong data-star# Singleton Design Pattern — Senior/Lead Java Interview Prep

Here's a **complete and organized list of all Singleton Design Pattern questions and answers** — tailored for a **senior/lead full-stack Java developer** interview prep.

---

## ✅ 1. BASIC QUESTIONS

**Q1: What is Singleton?**  
👉 A design pattern where only **one instance** of a class exists in the JVM and is **globally accessible**.

---

**Q2: Why use it?**  
👉 To manage **shared resources** like:

- Logging
- Configuration
- Caching
- Thread pools
- DB connection pools

---

**Q3: Eager vs Lazy Initialization?**

| Type  | Description                                               |
| ----- | --------------------------------------------------------- |
| Eager | Instance is created at **class load** time.               |
| Lazy  | Instance is created **only when needed** (on first call). |

- Eager is simple, thread-safe, but may waste resources.
- Lazy saves memory but needs thread safety.

---

**Q4: Singleton vs Static Class?**

| Feature     | Singleton                   | Static Class      |
| ----------- | --------------------------- | ----------------- |
| Inheritance | ✅ Can implement interfaces | ❌ Cannot         |
| Lifecycle   | Managed                     | Always loaded     |
| State       | Can be stateful             | Usually stateless |

---

## ✅ 2. THREAD SAFETY

**Q5: How to make Singleton thread-safe?**  
👉 Use:

- `synchronized` method
- **Double-checked locking** with `volatile`
- **Bill Pugh inner static class**
- **Enum**

---

**Q6: Why use `volatile` in double-checked locking?**  
👉 Prevents **instruction reordering** that can cause a **partially constructed object** to be visible to other threads.

---

## ✅ 3. PITFALLS AND FIXES

### 🔸 Reflection

**Q7: How can reflection break Singleton?**  
👉 Reflection can **bypass private constructor** to create multiple instances.

**Fix:** Add guard in constructor:

```java
private static EagerSingleton instance;

private EagerSingleton() {
    if (instance != null) {
        throw new RuntimeException("Use getInstance()");
    }
}
```

---

### 🔸 Serialization

**Q8: How does serialization break Singleton?**  
👉 Deserializing creates a **new instance**.

**Fix:** Implement `readResolve()`:

```java
protected Object readResolve() {
    return getInstance();
}
```

---

### 🔸 Cloning

**Q9: How does cloning break Singleton?**  
👉 `clone()` creates a new object.

**Fix:** Override `clone()`:

```java
@Override
protected Object clone() throws CloneNotSupportedException {
    throw new CloneNotSupportedException();
}
```

---

### 🔸 Class Loaders

**Q10: How can multiple class loaders break Singleton?**  
👉 Each class loader can create its **own Singleton instance**.

**Fix:** Use a **common class loader** or **Spring context**.

---

### 🔸 Testing

**Q11: Why is Singleton hard for unit testing?**  
👉 Hard to **mock or replace**. Global state may cause test dependencies.

**Fix:** Use **Dependency Injection (DI)** instead of manual Singleton.

---

### 🔸 Overuse

**Q12: When should you avoid Singleton?**

- Stateful objects (e.g., UserSession)
- In distributed systems (each JVM has its own)
- When using frameworks like Spring (already manages lifecycle)

---

## ✅ 4. CODE PITFALLS & IMPLEMENTATIONS

### 🧨 Common Code Issue

```java
public static EagerInitSingleTon getInstance() {
    if (instance == null) {
        return new EagerInitSingleTon(); // BAD
    }
    return instance;
}
```

**Problem:** Even in eager init, you're manually creating a new instance inside `getInstance()` — breaks Singleton.

---

### ✅ Correct Implementations

**1. Eager Initialization (Simple, thread-safe)**

```java
public class EagerSingleton {
    private static final EagerSingleton instance = new EagerSingleton();
    private EagerSingleton() { }
    public static EagerSingleton getInstance() {
        return instance;
    }
}
```

---

**2. Lazy Initialization (Not thread-safe)**

```java
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
```

---

**3. Double-Checked Locking (Thread-safe + lazy)**

```java
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
```

---

**4. Bill Pugh Singleton (Best Practice)**

```java
public class BillPughSingleton {
    private BillPughSingleton() { }
    private static class Helper {
        private static final BillPughSingleton INSTANCE = new BillPughSingleton();
    }
    public static BillPughSingleton getInstance() {
        return Helper.INSTANCE;
    }
}
```

---

**5. Enum Singleton (Best for serialization/reflection safety)**

```java
public enum EnumSingleton {
    INSTANCE;
    public void doSomething() {
        System.out.println("Doing something...");
    }
}
```

---

## ✅ 5. SCENARIO-BASED QUESTIONS

**Q13: Where would you use Singleton?**  
👉 Logging, configuration, cache manager, thread pool manager, DB connection pool, service registries.

---

**Q14: Should you use Singleton in Microservices?**  
👉 Be careful — each service instance has its **own Singleton**. Use external coordination (e.g., Redis, config servers) for shared state.

---

**Q15: What if Singleton holds state (e.g., user session)?**  
👉 Risky — can lead to **race conditions**. Keep Singleton **stateless**.

---

**Q16: How to use Singleton in distributed systems?**  
👉 Use external systems like:

- **ZooKeeper**
- **Redis**
- **Database locks**

---

**Q17: Can Singleton violate SOLID?**  
👉 Yes:

- Violates **Single Responsibility** (does both creation & business logic)
- Violates **Open/Closed** (hard to extend)

---

**Q18: Do we need Singleton in Spring?**  
👉 No — Spring beans are **Singleton by default** (per `ApplicationContext`).

---

**Q19: How would you reduce tight coupling caused by Singleton?**  
👉 Use **Dependency Injection** — inject Singleton instead of calling `getInstance()` directly.

---

**Q20: What’s the best Singleton for high concurrency?**  
👉 **Bill Pugh** or **Enum** — thread-safe, performant, and simple.

---

## ✅ 6. ADVANCED / ARCHITECTURE-LEVEL QUESTIONS

**Q21: What are the risks of using Singleton for DB connection?**  
👉 If DB is down, Singleton can hold **stale/broken state**. Use **connection pools** instead.

---

**Q22: What other patterns can replace Singleton for global access?**  
👉 Alternatives:

- **Dependency Injection**
- **Service Locator**

---

**Q23: Can Singleton cause memory leaks?**  
👉 Yes — if it holds references to objects that should be garbage collected.

---

## ✅ SUMMARY FOR LEAD ROLE

As a lead, focus on:

- Thread safety & concurrency
- Design trade-offs (memory vs safety)
- Distributed system behavior
- Pitfalls (cloning, serialization, reflection)
- Dependency injection vs manual Singleton
- When not to use Singleton (stateless vs stateful)
- Best implementation based on use-caset="135" data-end="176">senior/lead full-stack Java developer</strong> interview prep:</p>
<hr data-start="194" data-end="197">
<h2 data-start="199" data-end="222">✅ 1. BASIC QUESTIONS</h2>
<p data-start="224" data-end="361"><strong data-start="224" data-end="250">Q1: What is Singleton?</strong><br data-start="250" data-end="253">
👉 A design pattern where only <strong data-start="284" data-end="300">one instance</strong> of a class exists in the JVM and is <strong data-start="337" data-end="360">globally accessible</strong>.</p>
<hr data-start="363" data-end="366">
<p data-start="368" data-end="429"><strong data-start="368" data-end="387">Q2: Why use it?</strong><br data-start="387" data-end="390">
👉 To manage <strong data-start="403" data-end="423">shared resources</strong> like:</p>
<ul data-start="430" data-end="502">
<li data-start="430" data-end="439">
<p data-start="432" data-end="439">Logging</p>
</li>
<li data-start="440" data-end="455">
<p data-start="442" data-end="455">Configuration</p>
</li>
<li data-start="456" data-end="465">
<p data-start="458" data-end="465">Caching</p>
</li>
<li data-start="466" data-end="480">
<p data-start="468" data-end="480">Thread pools</p>
</li>
<li data-start="481" data-end="502">
<p data-start="483" data-end="502">DB connection pools</p>
</li>
</ul>
<hr data-start="504" data-end="507">
<p data-start="509" data-end="548"><strong data-start="509" data-end="546">Q3: Eager vs Lazy Initialization?</strong></p>
<div class="_tableContainer_1rjym_1"><div tabindex="-1" class="_tableWrapper_1rjym_13 group flex w-fit flex-col-reverse"><table data-start="549" data-end="732" class="w-fit min-w-(--thread-content-width)"><thead data-start="549" data-end="573"><tr data-start="549" data-end="573"><th data-start="549" data-end="558" data-col-size="sm">Type</th><th data-start="558" data-end="573" data-col-size="md">Description</th></tr></thead><tbody data-start="599" data-end="732"><tr data-start="599" data-end="658"><td data-start="599" data-end="611" data-col-size="sm"><strong data-start="601" data-end="610">Eager</strong></td><td data-start="611" data-end="658" data-col-size="md">Instance is created at <strong data-start="636" data-end="650">class load</strong> time.</td></tr><tr data-start="659" data-end="732"><td data-start="659" data-end="671" data-col-size="sm"><strong data-start="661" data-end="669">Lazy</strong></td><td data-start="671" data-end="732" data-col-size="md">Instance is created <strong data-start="693" data-end="713">only when needed</strong> (on first call).</td></tr></tbody></table><div class="sticky end-(--thread-content-margin) h-0 self-end select-none"><div class="absolute end-0 flex items-end" style="height: 32.6562px;"><span class="" data-state="closed"><button aria-label="Copy table" class="hover:bg-token-bg-tertiary text-token-text-secondary my-1 rounded-sm p-1 transition-opacity group-[:not(:hover):not(:focus-within)]:pointer-events-none group-[:not(:hover):not(:focus-within)]:opacity-0"><svg width="20" height="20" viewBox="0 0 20 20" fill="currentColor" xmlns="http://www.w3.org/2000/svg" class="icon"><path d="M12.668 10.667C12.668 9.95614 12.668 9.46258 12.6367 9.0791C12.6137 8.79732 12.5758 8.60761 12.5244 8.46387L12.4688 8.33399C12.3148 8.03193 12.0803 7.77885 11.793 7.60254L11.666 7.53125C11.508 7.45087 11.2963 7.39395 10.9209 7.36328C10.5374 7.33197 10.0439 7.33203 9.33301 7.33203H6.5C5.78896 7.33203 5.29563 7.33195 4.91211 7.36328C4.63016 7.38632 4.44065 7.42413 4.29688 7.47559L4.16699 7.53125C3.86488 7.68518 3.61186 7.9196 3.43555 8.20703L3.36524 8.33399C3.28478 8.49198 3.22795 8.70352 3.19727 9.0791C3.16595 9.46259 3.16504 9.95611 3.16504 10.667V13.5C3.16504 14.211 3.16593 14.7044 3.19727 15.0879C3.22797 15.4636 3.28473 15.675 3.36524 15.833L3.43555 15.959C3.61186 16.2466 3.86474 16.4807 4.16699 16.6348L4.29688 16.6914C4.44063 16.7428 4.63025 16.7797 4.91211 16.8027C5.29563 16.8341 5.78896 16.835 6.5 16.835H9.33301C10.0439 16.835 10.5374 16.8341 10.9209 16.8027C11.2965 16.772 11.508 16.7152 11.666 16.6348L11.793 16.5645C12.0804 16.3881 12.3148 16.1351 12.4688 15.833L12.5244 15.7031C12.5759 15.5594 12.6137 15.3698 12.6367 15.0879C12.6681 14.7044 12.668 14.211 12.668 13.5V10.667ZM13.998 12.665C14.4528 12.6634 14.8011 12.6602 15.0879 12.6367C15.4635 12.606 15.675 12.5492 15.833 12.4688L15.959 12.3975C16.2466 12.2211 16.4808 11.9682 16.6348 11.666L16.6914 11.5361C16.7428 11.3924 16.7797 11.2026 16.8027 10.9209C16.8341 10.5374 16.835 10.0439 16.835 9.33301V6.5C16.835 5.78896 16.8341 5.29563 16.8027 4.91211C16.7797 4.63025 16.7428 4.44063 16.6914 4.29688L16.6348 4.16699C16.4807 3.86474 16.2466 3.61186 15.959 3.43555L15.833 3.36524C15.675 3.28473 15.4636 3.22797 15.0879 3.19727C14.7044 3.16593 14.211 3.16504 13.5 3.16504H10.667C9.9561 3.16504 9.46259 3.16595 9.0791 3.19727C8.79739 3.22028 8.6076 3.2572 8.46387 3.30859L8.33399 3.36524C8.03176 3.51923 7.77886 3.75343 7.60254 4.04102L7.53125 4.16699C7.4508 4.32498 7.39397 4.53655 7.36328 4.91211C7.33985 5.19893 7.33562 5.54719 7.33399 6.00195H9.33301C10.022 6.00195 10.5791 6.00131 11.0293 6.03809C11.4873 6.07551 11.8937 6.15471 12.2705 6.34668L12.4883 6.46875C12.984 6.7728 13.3878 7.20854 13.6533 7.72949L13.7197 7.87207C13.8642 8.20859 13.9292 8.56974 13.9619 8.9707C13.9987 9.42092 13.998 9.97799 13.998 10.667V12.665ZM18.165 9.33301C18.165 10.022 18.1657 10.5791 18.1289 11.0293C18.0961 11.4302 18.0311 11.7914 17.8867 12.1279L17.8203 12.2705C17.5549 12.7914 17.1509 13.2272 16.6553 13.5313L16.4365 13.6533C16.0599 13.8452 15.6541 13.9245 15.1963 13.9619C14.8593 13.9895 14.4624 13.9935 13.9951 13.9951C13.9935 14.4624 13.9895 14.8593 13.9619 15.1963C13.9292 15.597 13.864 15.9576 13.7197 16.2939L13.6533 16.4365C13.3878 16.9576 12.9841 17.3941 12.4883 17.6982L12.2705 17.8203C11.8937 18.0123 11.4873 18.0915 11.0293 18.1289C10.5791 18.1657 10.022 18.165 9.33301 18.165H6.5C5.81091 18.165 5.25395 18.1657 4.80371 18.1289C4.40306 18.0962 4.04235 18.031 3.70606 17.8867L3.56348 17.8203C3.04244 17.5548 2.60585 17.151 2.30176 16.6553L2.17969 16.4365C1.98788 16.0599 1.90851 15.6541 1.87109 15.1963C1.83431 14.746 1.83496 14.1891 1.83496 13.5V10.667C1.83496 9.978 1.83432 9.42091 1.87109 8.9707C1.90851 8.5127 1.98772 8.10625 2.17969 7.72949L2.30176 7.51172C2.60586 7.0159 3.04236 6.6122 3.56348 6.34668L3.70606 6.28027C4.04237 6.136 4.40303 6.07083 4.80371 6.03809C5.14051 6.01057 5.53708 6.00551 6.00391 6.00391C6.00551 5.53708 6.01057 5.14051 6.03809 4.80371C6.0755 4.34588 6.15483 3.94012 6.34668 3.56348L6.46875 3.34473C6.77282 2.84912 7.20856 2.44514 7.72949 2.17969L7.87207 2.11328C8.20855 1.96886 8.56979 1.90385 8.9707 1.87109C9.42091 1.83432 9.978 1.83496 10.667 1.83496H13.5C14.1891 1.83496 14.746 1.83431 15.1963 1.87109C15.6541 1.90851 16.0599 1.98788 16.4365 2.17969L16.6553 2.30176C17.151 2.60585 17.5548 3.04244 17.8203 3.56348L17.8867 3.70606C18.031 4.04235 18.0962 4.40306 18.1289 4.80371C18.1657 5.25395 18.165 5.81091 18.165 6.5V9.33301Z"></path></svg></button></span></div></div></div></div>
<ul data-start="734" data-end="835">
<li data-start="734" data-end="790">
<p data-start="736" data-end="790">Eager is simple, thread-safe, but may waste resources.</p>
</li>
<li data-start="791" data-end="835">
<p data-start="793" data-end="835">Lazy saves memory but needs thread safety.</p>
</li>
</ul>
<hr data-start="837" data-end="840">
<p data-start="842" data-end="878"><strong data-start="842" data-end="876">Q4: Singleton vs Static Class?</strong></p>
<div class="_tableContainer_1rjym_1"><div tabindex="-1" class="_tableWrapper_1rjym_13 group flex w-fit flex-col-reverse"><table data-start="879" data-end="1100" class="w-fit min-w-(--thread-content-width)"><thead data-start="879" data-end="917"><tr data-start="879" data-end="917"><th data-start="879" data-end="889" data-col-size="sm">Feature</th><th data-start="889" data-end="901" data-col-size="sm">Singleton</th><th data-start="901" data-end="917" data-col-size="sm">Static Class</th></tr></thead><tbody data-start="957" data-end="1100"><tr data-start="957" data-end="1012"><td data-start="957" data-end="971" data-col-size="sm">Inheritance</td><td data-start="971" data-end="1000" data-col-size="sm">✅ Can implement interfaces</td><td data-start="1000" data-end="1012" data-col-size="sm">❌ Cannot</td></tr><tr data-start="1013" data-end="1052"><td data-start="1013" data-end="1025" data-col-size="sm">Lifecycle</td><td data-start="1025" data-end="1035" data-col-size="sm">Managed</td><td data-start="1035" data-end="1052" data-col-size="sm">Always loaded</td></tr><tr data-start="1053" data-end="1100"><td data-start="1053" data-end="1061" data-col-size="sm">State</td><td data-start="1061" data-end="1079" data-col-size="sm">Can be stateful</td><td data-start="1079" data-end="1100" data-col-size="sm">Usually stateless</td></tr></tbody></table><div class="sticky end-(--thread-content-margin) h-0 self-end select-none"><div class="absolute end-0 flex items-end" style="height: 32.6562px;"><span class="" data-state="closed"><button aria-label="Copy table" class="hover:bg-token-bg-tertiary text-token-text-secondary my-1 rounded-sm p-1 transition-opacity group-[:not(:hover):not(:focus-within)]:pointer-events-none group-[:not(:hover):not(:focus-within)]:opacity-0"><svg width="20" height="20" viewBox="0 0 20 20" fill="currentColor" xmlns="http://www.w3.org/2000/svg" class="icon"><path d="M12.668 10.667C12.668 9.95614 12.668 9.46258 12.6367 9.0791C12.6137 8.79732 12.5758 8.60761 12.5244 8.46387L12.4688 8.33399C12.3148 8.03193 12.0803 7.77885 11.793 7.60254L11.666 7.53125C11.508 7.45087 11.2963 7.39395 10.9209 7.36328C10.5374 7.33197 10.0439 7.33203 9.33301 7.33203H6.5C5.78896 7.33203 5.29563 7.33195 4.91211 7.36328C4.63016 7.38632 4.44065 7.42413 4.29688 7.47559L4.16699 7.53125C3.86488 7.68518 3.61186 7.9196 3.43555 8.20703L3.36524 8.33399C3.28478 8.49198 3.22795 8.70352 3.19727 9.0791C3.16595 9.46259 3.16504 9.95611 3.16504 10.667V13.5C3.16504 14.211 3.16593 14.7044 3.19727 15.0879C3.22797 15.4636 3.28473 15.675 3.36524 15.833L3.43555 15.959C3.61186 16.2466 3.86474 16.4807 4.16699 16.6348L4.29688 16.6914C4.44063 16.7428 4.63025 16.7797 4.91211 16.8027C5.29563 16.8341 5.78896 16.835 6.5 16.835H9.33301C10.0439 16.835 10.5374 16.8341 10.9209 16.8027C11.2965 16.772 11.508 16.7152 11.666 16.6348L11.793 16.5645C12.0804 16.3881 12.3148 16.1351 12.4688 15.833L12.5244 15.7031C12.5759 15.5594 12.6137 15.3698 12.6367 15.0879C12.6681 14.7044 12.668 14.211 12.668 13.5V10.667ZM13.998 12.665C14.4528 12.6634 14.8011 12.6602 15.0879 12.6367C15.4635 12.606 15.675 12.5492 15.833 12.4688L15.959 12.3975C16.2466 12.2211 16.4808 11.9682 16.6348 11.666L16.6914 11.5361C16.7428 11.3924 16.7797 11.2026 16.8027 10.9209C16.8341 10.5374 16.835 10.0439 16.835 9.33301V6.5C16.835 5.78896 16.8341 5.29563 16.8027 4.91211C16.7797 4.63025 16.7428 4.44063 16.6914 4.29688L16.6348 4.16699C16.4807 3.86474 16.2466 3.61186 15.959 3.43555L15.833 3.36524C15.675 3.28473 15.4636 3.22797 15.0879 3.19727C14.7044 3.16593 14.211 3.16504 13.5 3.16504H10.667C9.9561 3.16504 9.46259 3.16595 9.0791 3.19727C8.79739 3.22028 8.6076 3.2572 8.46387 3.30859L8.33399 3.36524C8.03176 3.51923 7.77886 3.75343 7.60254 4.04102L7.53125 4.16699C7.4508 4.32498 7.39397 4.53655 7.36328 4.91211C7.33985 5.19893 7.33562 5.54719 7.33399 6.00195H9.33301C10.022 6.00195 10.5791 6.00131 11.0293 6.03809C11.4873 6.07551 11.8937 6.15471 12.2705 6.34668L12.4883 6.46875C12.984 6.7728 13.3878 7.20854 13.6533 7.72949L13.7197 7.87207C13.8642 8.20859 13.9292 8.56974 13.9619 8.9707C13.9987 9.42092 13.998 9.97799 13.998 10.667V12.665ZM18.165 9.33301C18.165 10.022 18.1657 10.5791 18.1289 11.0293C18.0961 11.4302 18.0311 11.7914 17.8867 12.1279L17.8203 12.2705C17.5549 12.7914 17.1509 13.2272 16.6553 13.5313L16.4365 13.6533C16.0599 13.8452 15.6541 13.9245 15.1963 13.9619C14.8593 13.9895 14.4624 13.9935 13.9951 13.9951C13.9935 14.4624 13.9895 14.8593 13.9619 15.1963C13.9292 15.597 13.864 15.9576 13.7197 16.2939L13.6533 16.4365C13.3878 16.9576 12.9841 17.3941 12.4883 17.6982L12.2705 17.8203C11.8937 18.0123 11.4873 18.0915 11.0293 18.1289C10.5791 18.1657 10.022 18.165 9.33301 18.165H6.5C5.81091 18.165 5.25395 18.1657 4.80371 18.1289C4.40306 18.0962 4.04235 18.031 3.70606 17.8867L3.56348 17.8203C3.04244 17.5548 2.60585 17.151 2.30176 16.6553L2.17969 16.4365C1.98788 16.0599 1.90851 15.6541 1.87109 15.1963C1.83431 14.746 1.83496 14.1891 1.83496 13.5V10.667C1.83496 9.978 1.83432 9.42091 1.87109 8.9707C1.90851 8.5127 1.98772 8.10625 2.17969 7.72949L2.30176 7.51172C2.60586 7.0159 3.04236 6.6122 3.56348 6.34668L3.70606 6.28027C4.04237 6.136 4.40303 6.07083 4.80371 6.03809C5.14051 6.01057 5.53708 6.00551 6.00391 6.00391C6.00551 5.53708 6.01057 5.14051 6.03809 4.80371C6.0755 4.34588 6.15483 3.94012 6.34668 3.56348L6.46875 3.34473C6.77282 2.84912 7.20856 2.44514 7.72949 2.17969L7.87207 2.11328C8.20855 1.96886 8.56979 1.90385 8.9707 1.87109C9.42091 1.83432 9.978 1.83496 10.667 1.83496H13.5C14.1891 1.83496 14.746 1.83431 15.1963 1.87109C15.6541 1.90851 16.0599 1.98788 16.4365 2.17969L16.6553 2.30176C17.151 2.60585 17.5548 3.04244 17.8203 3.56348L17.8867 3.70606C18.031 4.04235 18.0962 4.40306 18.1289 4.80371C18.1657 5.25395 18.165 5.81091 18.165 6.5V9.33301Z"></path></svg></button></span></div></div></div></div>
<hr data-start="1102" data-end="1105">
<h2 data-start="1107" data-end="1128">✅ 2. THREAD SAFETY</h2>
<p data-start="1130" data-end="1182"><strong data-start="1130" data-end="1172">Q5: How to make Singleton thread-safe?</strong><br data-start="1172" data-end="1175">
👉 Use:</p>
<ul data-start="1183" data-end="1297">
<li data-start="1183" data-end="1206">
<p data-start="1185" data-end="1206"><code data-start="1185" data-end="1199">synchronized</code> method</p>
</li>
<li data-start="1207" data-end="1251">
<p data-start="1209" data-end="1251"><strong data-start="1209" data-end="1235">Double-checked locking</strong> with <code data-start="1241" data-end="1251">volatile</code></p>
</li>
<li data-start="1252" data-end="1286">
<p data-start="1254" data-end="1286"><strong data-start="1254" data-end="1286">Bill Pugh inner static class</strong></p>
</li>
<li data-start="1287" data-end="1297">
<p data-start="1289" data-end="1297"><strong data-start="1289" data-end="1297">Enum</strong></p>
</li>
</ul>
<hr data-start="1299" data-end="1302">
<p data-start="1304" data-end="1480"><strong data-start="1304" data-end="1357">Q6: Why use <code data-start="1318" data-end="1328">volatile</code> in double-checked locking?</strong><br data-start="1357" data-end="1360">
👉 Prevents <strong data-start="1372" data-end="1398">instruction reordering</strong> that can cause a <strong data-start="1416" data-end="1448">partially constructed object</strong> to be visible to other threads.</p>
<hr data-start="1482" data-end="1485">
<h2 data-start="1487" data-end="1513">✅ 3. PITFALLS AND FIXES</h2>
<h3 data-start="1515" data-end="1532">🔸 Reflection</h3>
<p data-start="1533" data-end="1657"><strong data-start="1533" data-end="1576">Q7: How can reflection break Singleton?</strong><br data-start="1576" data-end="1579">
👉 Reflection can <strong data-start="1597" data-end="1627">bypass private constructor</strong> to create multiple instances.</p>
<p data-start="1659" data-end="1693"><strong data-start="1659" data-end="1667">Fix:</strong> Add guard in constructor:</p>
<pre class="overflow-visible!" data-start="1694" data-end="1866"><div class="contain-inline-size rounded-2xl relative bg-token-sidebar-surface-primary"><div class="flex items-center text-token-text-secondary px-4 py-2 text-xs font-sans justify-between h-9 bg-token-sidebar-surface-primary select-none rounded-t-2xl">java</div><div class="sticky top-9"><div class="absolute end-0 bottom-0 flex h-9 items-center pe-2"><div class="bg-token-bg-elevated-secondary text-token-text-secondary flex items-center gap-4 rounded-sm px-2 font-sans text-xs"><button class="flex gap-1 items-center select-none py-1" aria-label="Copy"><svg width="20" height="20" viewBox="0 0 20 20" fill="currentColor" xmlns="http://www.w3.org/2000/svg" class="icon-sm"><path d="M12.668 10.667C12.668 9.95614 12.668 9.46258 12.6367 9.0791C12.6137 8.79732 12.5758 8.60761 12.5244 8.46387L12.4688 8.33399C12.3148 8.03193 12.0803 7.77885 11.793 7.60254L11.666 7.53125C11.508 7.45087 11.2963 7.39395 10.9209 7.36328C10.5374 7.33197 10.0439 7.33203 9.33301 7.33203H6.5C5.78896 7.33203 5.29563 7.33195 4.91211 7.36328C4.63016 7.38632 4.44065 7.42413 4.29688 7.47559L4.16699 7.53125C3.86488 7.68518 3.61186 7.9196 3.43555 8.20703L3.36524 8.33399C3.28478 8.49198 3.22795 8.70352 3.19727 9.0791C3.16595 9.46259 3.16504 9.95611 3.16504 10.667V13.5C3.16504 14.211 3.16593 14.7044 3.19727 15.0879C3.22797 15.4636 3.28473 15.675 3.36524 15.833L3.43555 15.959C3.61186 16.2466 3.86474 16.4807 4.16699 16.6348L4.29688 16.6914C4.44063 16.7428 4.63025 16.7797 4.91211 16.8027C5.29563 16.8341 5.78896 16.835 6.5 16.835H9.33301C10.0439 16.835 10.5374 16.8341 10.9209 16.8027C11.2965 16.772 11.508 16.7152 11.666 16.6348L11.793 16.5645C12.0804 16.3881 12.3148 16.1351 12.4688 15.833L12.5244 15.7031C12.5759 15.5594 12.6137 15.3698 12.6367 15.0879C12.6681 14.7044 12.668 14.211 12.668 13.5V10.667ZM13.998 12.665C14.4528 12.6634 14.8011 12.6602 15.0879 12.6367C15.4635 12.606 15.675 12.5492 15.833 12.4688L15.959 12.3975C16.2466 12.2211 16.4808 11.9682 16.6348 11.666L16.6914 11.5361C16.7428 11.3924 16.7797 11.2026 16.8027 10.9209C16.8341 10.5374 16.835 10.0439 16.835 9.33301V6.5C16.835 5.78896 16.8341 5.29563 16.8027 4.91211C16.7797 4.63025 16.7428 4.44063 16.6914 4.29688L16.6348 4.16699C16.4807 3.86474 16.2466 3.61186 15.959 3.43555L15.833 3.36524C15.675 3.28473 15.4636 3.22797 15.0879 3.19727C14.7044 3.16593 14.211 3.16504 13.5 3.16504H10.667C9.9561 3.16504 9.46259 3.16595 9.0791 3.19727C8.79739 3.22028 8.6076 3.2572 8.46387 3.30859L8.33399 3.36524C8.03176 3.51923 7.77886 3.75343 7.60254 4.04102L7.53125 4.16699C7.4508 4.32498 7.39397 4.53655 7.36328 4.91211C7.33985 5.19893 7.33562 5.54719 7.33399 6.00195H9.33301C10.022 6.00195 10.5791 6.00131 11.0293 6.03809C11.4873 6.07551 11.8937 6.15471 12.2705 6.34668L12.4883 6.46875C12.984 6.7728 13.3878 7.20854 13.6533 7.72949L13.7197 7.87207C13.8642 8.20859 13.9292 8.56974 13.9619 8.9707C13.9987 9.42092 13.998 9.97799 13.998 10.667V12.665ZM18.165 9.33301C18.165 10.022 18.1657 10.5791 18.1289 11.0293C18.0961 11.4302 18.0311 11.7914 17.8867 12.1279L17.8203 12.2705C17.5549 12.7914 17.1509 13.2272 16.6553 13.5313L16.4365 13.6533C16.0599 13.8452 15.6541 13.9245 15.1963 13.9619C14.8593 13.9895 14.4624 13.9935 13.9951 13.9951C13.9935 14.4624 13.9895 14.8593 13.9619 15.1963C13.9292 15.597 13.864 15.9576 13.7197 16.2939L13.6533 16.4365C13.3878 16.9576 12.9841 17.3941 12.4883 17.6982L12.2705 17.8203C11.8937 18.0123 11.4873 18.0915 11.0293 18.1289C10.5791 18.1657 10.022 18.165 9.33301 18.165H6.5C5.81091 18.165 5.25395 18.1657 4.80371 18.1289C4.40306 18.0962 4.04235 18.031 3.70606 17.8867L3.56348 17.8203C3.04244 17.5548 2.60585 17.151 2.30176 16.6553L2.17969 16.4365C1.98788 16.0599 1.90851 15.6541 1.87109 15.1963C1.83431 14.746 1.83496 14.1891 1.83496 13.5V10.667C1.83496 9.978 1.83432 9.42091 1.87109 8.9707C1.90851 8.5127 1.98772 8.10625 2.17969 7.72949L2.30176 7.51172C2.60586 7.0159 3.04236 6.6122 3.56348 6.34668L3.70606 6.28027C4.04237 6.136 4.40303 6.07083 4.80371 6.03809C5.14051 6.01057 5.53708 6.00551 6.00391 6.00391C6.00551 5.53708 6.01057 5.14051 6.03809 4.80371C6.0755 4.34588 6.15483 3.94012 6.34668 3.56348L6.46875 3.34473C6.77282 2.84912 7.20856 2.44514 7.72949 2.17969L7.87207 2.11328C8.20855 1.96886 8.56979 1.90385 8.9707 1.87109C9.42091 1.83432 9.978 1.83496 10.667 1.83496H13.5C14.1891 1.83496 14.746 1.83431 15.1963 1.87109C15.6541 1.90851 16.0599 1.98788 16.4365 2.17969L16.6553 2.30176C17.151 2.60585 17.5548 3.04244 17.8203 3.56348L17.8867 3.70606C18.031 4.04235 18.0962 4.40306 18.1289 4.80371C18.1657 5.25395 18.165 5.81091 18.165 6.5V9.33301Z"></path></svg>Copy code</button></div></div></div><div class="overflow-y-auto p-4" dir="ltr"><code class="whitespace-pre! language-java"><span><span><span class="hljs-keyword">private</span></span><span> </span><span><span class="hljs-keyword">static</span></span><span> EagerSingleton instance;

</span><span><span class="hljs-keyword">private</span></span><span> </span><span><span class="hljs-title function_">EagerSingleton</span></span><span><span class="hljs-params">()</span></span><span> {
</span><span><span class="hljs-keyword">if</span></span><span> (instance != </span><span><span class="hljs-literal">null</span></span><span>) {
</span><span><span class="hljs-keyword">throw</span></span><span> </span><span><span class="hljs-keyword">new</span></span><span> </span><span><span class="hljs-title class_">RuntimeException</span></span><span>(</span><span><span class="hljs-string">"Use getInstance()"</span></span><span>);
}
}
</span></span></code></div></div></pre>

<hr data-start="1868" data-end="1871">
<h3 data-start="1873" data-end="1893">🔸 Serialization</h3>
<p data-start="1894" data-end="1988"><strong data-start="1894" data-end="1941">Q8: How does serialization break Singleton?</strong><br data-start="1941" data-end="1944">
👉 Deserializing creates a <strong data-start="1971" data-end="1987">new instance</strong>.</p>
<p data-start="1990" data-end="2025"><strong data-start="1990" data-end="1998">Fix:</strong> Implement <code data-start="2009" data-end="2024">readResolve()</code>:</p>
<pre class="overflow-visible!" data-start="2026" data-end="2098"><div class="contain-inline-size rounded-2xl relative bg-token-sidebar-surface-primary"><div class="flex items-center text-token-text-secondary px-4 py-2 text-xs font-sans justify-between h-9 bg-token-sidebar-surface-primary select-none rounded-t-2xl">java</div><div class="sticky top-9"><div class="absolute end-0 bottom-0 flex h-9 items-center pe-2"><div class="bg-token-bg-elevated-secondary text-token-text-secondary flex items-center gap-4 rounded-sm px-2 font-sans text-xs"><button class="flex gap-1 items-center select-none py-1" aria-label="Copy"><svg width="20" height="20" viewBox="0 0 20 20" fill="currentColor" xmlns="http://www.w3.org/2000/svg" class="icon-sm"><path d="M12.668 10.667C12.668 9.95614 12.668 9.46258 12.6367 9.0791C12.6137 8.79732 12.5758 8.60761 12.5244 8.46387L12.4688 8.33399C12.3148 8.03193 12.0803 7.77885 11.793 7.60254L11.666 7.53125C11.508 7.45087 11.2963 7.39395 10.9209 7.36328C10.5374 7.33197 10.0439 7.33203 9.33301 7.33203H6.5C5.78896 7.33203 5.29563 7.33195 4.91211 7.36328C4.63016 7.38632 4.44065 7.42413 4.29688 7.47559L4.16699 7.53125C3.86488 7.68518 3.61186 7.9196 3.43555 8.20703L3.36524 8.33399C3.28478 8.49198 3.22795 8.70352 3.19727 9.0791C3.16595 9.46259 3.16504 9.95611 3.16504 10.667V13.5C3.16504 14.211 3.16593 14.7044 3.19727 15.0879C3.22797 15.4636 3.28473 15.675 3.36524 15.833L3.43555 15.959C3.61186 16.2466 3.86474 16.4807 4.16699 16.6348L4.29688 16.6914C4.44063 16.7428 4.63025 16.7797 4.91211 16.8027C5.29563 16.8341 5.78896 16.835 6.5 16.835H9.33301C10.0439 16.835 10.5374 16.8341 10.9209 16.8027C11.2965 16.772 11.508 16.7152 11.666 16.6348L11.793 16.5645C12.0804 16.3881 12.3148 16.1351 12.4688 15.833L12.5244 15.7031C12.5759 15.5594 12.6137 15.3698 12.6367 15.0879C12.6681 14.7044 12.668 14.211 12.668 13.5V10.667ZM13.998 12.665C14.4528 12.6634 14.8011 12.6602 15.0879 12.6367C15.4635 12.606 15.675 12.5492 15.833 12.4688L15.959 12.3975C16.2466 12.2211 16.4808 11.9682 16.6348 11.666L16.6914 11.5361C16.7428 11.3924 16.7797 11.2026 16.8027 10.9209C16.8341 10.5374 16.835 10.0439 16.835 9.33301V6.5C16.835 5.78896 16.8341 5.29563 16.8027 4.91211C16.7797 4.63025 16.7428 4.44063 16.6914 4.29688L16.6348 4.16699C16.4807 3.86474 16.2466 3.61186 15.959 3.43555L15.833 3.36524C15.675 3.28473 15.4636 3.22797 15.0879 3.19727C14.7044 3.16593 14.211 3.16504 13.5 3.16504H10.667C9.9561 3.16504 9.46259 3.16595 9.0791 3.19727C8.79739 3.22028 8.6076 3.2572 8.46387 3.30859L8.33399 3.36524C8.03176 3.51923 7.77886 3.75343 7.60254 4.04102L7.53125 4.16699C7.4508 4.32498 7.39397 4.53655 7.36328 4.91211C7.33985 5.19893 7.33562 5.54719 7.33399 6.00195H9.33301C10.022 6.00195 10.5791 6.00131 11.0293 6.03809C11.4873 6.07551 11.8937 6.15471 12.2705 6.34668L12.4883 6.46875C12.984 6.7728 13.3878 7.20854 13.6533 7.72949L13.7197 7.87207C13.8642 8.20859 13.9292 8.56974 13.9619 8.9707C13.9987 9.42092 13.998 9.97799 13.998 10.667V12.665ZM18.165 9.33301C18.165 10.022 18.1657 10.5791 18.1289 11.0293C18.0961 11.4302 18.0311 11.7914 17.8867 12.1279L17.8203 12.2705C17.5549 12.7914 17.1509 13.2272 16.6553 13.5313L16.4365 13.6533C16.0599 13.8452 15.6541 13.9245 15.1963 13.9619C14.8593 13.9895 14.4624 13.9935 13.9951 13.9951C13.9935 14.4624 13.9895 14.8593 13.9619 15.1963C13.9292 15.597 13.864 15.9576 13.7197 16.2939L13.6533 16.4365C13.3878 16.9576 12.9841 17.3941 12.4883 17.6982L12.2705 17.8203C11.8937 18.0123 11.4873 18.0915 11.0293 18.1289C10.5791 18.1657 10.022 18.165 9.33301 18.165H6.5C5.81091 18.165 5.25395 18.1657 4.80371 18.1289C4.40306 18.0962 4.04235 18.031 3.70606 17.8867L3.56348 17.8203C3.04244 17.5548 2.60585 17.151 2.30176 16.6553L2.17969 16.4365C1.98788 16.0599 1.90851 15.6541 1.87109 15.1963C1.83431 14.746 1.83496 14.1891 1.83496 13.5V10.667C1.83496 9.978 1.83432 9.42091 1.87109 8.9707C1.90851 8.5127 1.98772 8.10625 2.17969 7.72949L2.30176 7.51172C2.60586 7.0159 3.04236 6.6122 3.56348 6.34668L3.70606 6.28027C4.04237 6.136 4.40303 6.07083 4.80371 6.03809C5.14051 6.01057 5.53708 6.00551 6.00391 6.00391C6.00551 5.53708 6.01057 5.14051 6.03809 4.80371C6.0755 4.34588 6.15483 3.94012 6.34668 3.56348L6.46875 3.34473C6.77282 2.84912 7.20856 2.44514 7.72949 2.17969L7.87207 2.11328C8.20855 1.96886 8.56979 1.90385 8.9707 1.87109C9.42091 1.83432 9.978 1.83496 10.667 1.83496H13.5C14.1891 1.83496 14.746 1.83431 15.1963 1.87109C15.6541 1.90851 16.0599 1.98788 16.4365 2.17969L16.6553 2.30176C17.151 2.60585 17.5548 3.04244 17.8203 3.56348L17.8867 3.70606C18.031 4.04235 18.0962 4.40306 18.1289 4.80371C18.1657 5.25395 18.165 5.81091 18.165 6.5V9.33301Z"></path></svg>Copy code</button></div></div></div><div class="overflow-y-auto p-4" dir="ltr"><code class="whitespace-pre! language-java"><span><span><span class="hljs-keyword">protected</span></span><span> Object </span><span><span class="hljs-title function_">readResolve</span></span><span><span class="hljs-params">()</span></span><span> {
    </span><span><span class="hljs-keyword">return</span></span><span> getInstance();
}
</span></span></code></div></div></pre>
<hr data-start="2100" data-end="2103">
<h3 data-start="2105" data-end="2119">🔸 Cloning</h3>
<p data-start="2120" data-end="2198"><strong data-start="2120" data-end="2161">Q9: How does cloning break Singleton?</strong><br data-start="2161" data-end="2164">
👉 <code data-start="2167" data-end="2176">clone()</code> creates a new object.</p>
<p data-start="2200" data-end="2228"><strong data-start="2200" data-end="2208">Fix:</strong> Override <code data-start="2218" data-end="2227">clone()</code>:</p>
<pre class="overflow-visible!" data-start="2229" data-end="2357"><div class="contain-inline-size rounded-2xl relative bg-token-sidebar-surface-primary"><div class="flex items-center text-token-text-secondary px-4 py-2 text-xs font-sans justify-between h-9 bg-token-sidebar-surface-primary select-none rounded-t-2xl">java</div><div class="sticky top-9"><div class="absolute end-0 bottom-0 flex h-9 items-center pe-2"><div class="bg-token-bg-elevated-secondary text-token-text-secondary flex items-center gap-4 rounded-sm px-2 font-sans text-xs"><button class="flex gap-1 items-center select-none py-1" aria-label="Copy"><svg width="20" height="20" viewBox="0 0 20 20" fill="currentColor" xmlns="http://www.w3.org/2000/svg" class="icon-sm"><path d="M12.668 10.667C12.668 9.95614 12.668 9.46258 12.6367 9.0791C12.6137 8.79732 12.5758 8.60761 12.5244 8.46387L12.4688 8.33399C12.3148 8.03193 12.0803 7.77885 11.793 7.60254L11.666 7.53125C11.508 7.45087 11.2963 7.39395 10.9209 7.36328C10.5374 7.33197 10.0439 7.33203 9.33301 7.33203H6.5C5.78896 7.33203 5.29563 7.33195 4.91211 7.36328C4.63016 7.38632 4.44065 7.42413 4.29688 7.47559L4.16699 7.53125C3.86488 7.68518 3.61186 7.9196 3.43555 8.20703L3.36524 8.33399C3.28478 8.49198 3.22795 8.70352 3.19727 9.0791C3.16595 9.46259 3.16504 9.95611 3.16504 10.667V13.5C3.16504 14.211 3.16593 14.7044 3.19727 15.0879C3.22797 15.4636 3.28473 15.675 3.36524 15.833L3.43555 15.959C3.61186 16.2466 3.86474 16.4807 4.16699 16.6348L4.29688 16.6914C4.44063 16.7428 4.63025 16.7797 4.91211 16.8027C5.29563 16.8341 5.78896 16.835 6.5 16.835H9.33301C10.0439 16.835 10.5374 16.8341 10.9209 16.8027C11.2965 16.772 11.508 16.7152 11.666 16.6348L11.793 16.5645C12.0804 16.3881 12.3148 16.1351 12.4688 15.833L12.5244 15.7031C12.5759 15.5594 12.6137 15.3698 12.6367 15.0879C12.6681 14.7044 12.668 14.211 12.668 13.5V10.667ZM13.998 12.665C14.4528 12.6634 14.8011 12.6602 15.0879 12.6367C15.4635 12.606 15.675 12.5492 15.833 12.4688L15.959 12.3975C16.2466 12.2211 16.4808 11.9682 16.6348 11.666L16.6914 11.5361C16.7428 11.3924 16.7797 11.2026 16.8027 10.9209C16.8341 10.5374 16.835 10.0439 16.835 9.33301V6.5C16.835 5.78896 16.8341 5.29563 16.8027 4.91211C16.7797 4.63025 16.7428 4.44063 16.6914 4.29688L16.6348 4.16699C16.4807 3.86474 16.2466 3.61186 15.959 3.43555L15.833 3.36524C15.675 3.28473 15.4636 3.22797 15.0879 3.19727C14.7044 3.16593 14.211 3.16504 13.5 3.16504H10.667C9.9561 3.16504 9.46259 3.16595 9.0791 3.19727C8.79739 3.22028 8.6076 3.2572 8.46387 3.30859L8.33399 3.36524C8.03176 3.51923 7.77886 3.75343 7.60254 4.04102L7.53125 4.16699C7.4508 4.32498 7.39397 4.53655 7.36328 4.91211C7.33985 5.19893 7.33562 5.54719 7.33399 6.00195H9.33301C10.022 6.00195 10.5791 6.00131 11.0293 6.03809C11.4873 6.07551 11.8937 6.15471 12.2705 6.34668L12.4883 6.46875C12.984 6.7728 13.3878 7.20854 13.6533 7.72949L13.7197 7.87207C13.8642 8.20859 13.9292 8.56974 13.9619 8.9707C13.9987 9.42092 13.998 9.97799 13.998 10.667V12.665ZM18.165 9.33301C18.165 10.022 18.1657 10.5791 18.1289 11.0293C18.0961 11.4302 18.0311 11.7914 17.8867 12.1279L17.8203 12.2705C17.5549 12.7914 17.1509 13.2272 16.6553 13.5313L16.4365 13.6533C16.0599 13.8452 15.6541 13.9245 15.1963 13.9619C14.8593 13.9895 14.4624 13.9935 13.9951 13.9951C13.9935 14.4624 13.9895 14.8593 13.9619 15.1963C13.9292 15.597 13.864 15.9576 13.7197 16.2939L13.6533 16.4365C13.3878 16.9576 12.9841 17.3941 12.4883 17.6982L12.2705 17.8203C11.8937 18.0123 11.4873 18.0915 11.0293 18.1289C10.5791 18.1657 10.022 18.165 9.33301 18.165H6.5C5.81091 18.165 5.25395 18.1657 4.80371 18.1289C4.40306 18.0962 4.04235 18.031 3.70606 17.8867L3.56348 17.8203C3.04244 17.5548 2.60585 17.151 2.30176 16.6553L2.17969 16.4365C1.98788 16.0599 1.90851 15.6541 1.87109 15.1963C1.83431 14.746 1.83496 14.1891 1.83496 13.5V10.667C1.83496 9.978 1.83432 9.42091 1.87109 8.9707C1.90851 8.5127 1.98772 8.10625 2.17969 7.72949L2.30176 7.51172C2.60586 7.0159 3.04236 6.6122 3.56348 6.34668L3.70606 6.28027C4.04237 6.136 4.40303 6.07083 4.80371 6.03809C5.14051 6.01057 5.53708 6.00551 6.00391 6.00391C6.00551 5.53708 6.01057 5.14051 6.03809 4.80371C6.0755 4.34588 6.15483 3.94012 6.34668 3.56348L6.46875 3.34473C6.77282 2.84912 7.20856 2.44514 7.72949 2.17969L7.87207 2.11328C8.20855 1.96886 8.56979 1.90385 8.9707 1.87109C9.42091 1.83432 9.978 1.83496 10.667 1.83496H13.5C14.1891 1.83496 14.746 1.83431 15.1963 1.87109C15.6541 1.90851 16.0599 1.98788 16.4365 2.17969L16.6553 2.30176C17.151 2.60585 17.5548 3.04244 17.8203 3.56348L17.8867 3.70606C18.031 4.04235 18.0962 4.40306 18.1289 4.80371C18.1657 5.25395 18.165 5.81091 18.165 6.5V9.33301Z"></path></svg>Copy code</button></div></div></div><div class="overflow-y-auto p-4" dir="ltr"><code class="whitespace-pre! language-java"><span><span><span class="hljs-meta">@Override</span></span><span>
</span><span><span class="hljs-keyword">protected</span></span><span> Object </span><span><span class="hljs-title function_">clone</span></span><span><span class="hljs-params">()</span></span><span> </span><span><span class="hljs-keyword">throws</span></span><span> CloneNotSupportedException {
    </span><span><span class="hljs-keyword">throw</span></span><span> </span><span><span class="hljs-keyword">new</span></span><span> </span><span><span class="hljs-title class_">CloneNotSupportedException</span></span><span>();
}
</span></span></code></div></div></pre>
<hr data-start="2359" data-end="2362">
<h3 data-start="2364" data-end="2384">🔸 Class Loaders</h3>
<p data-start="2385" data-end="2507"><strong data-start="2385" data-end="2441">Q10: How can multiple class loaders break Singleton?</strong><br data-start="2441" data-end="2444">
👉 Each class loader can create its <strong data-start="2480" data-end="2506">own Singleton instance</strong>.</p>
<p data-start="2509" data-end="2570"><strong data-start="2509" data-end="2517">Fix:</strong> Use a <strong data-start="2524" data-end="2547">common class loader</strong> or <strong data-start="2551" data-end="2569">Spring context</strong>.</p>
<hr data-start="2572" data-end="2575">
<h3 data-start="2577" data-end="2591">🔸 Testing</h3>
<p data-start="2592" data-end="2716"><strong data-start="2592" data-end="2640">Q11: Why is Singleton hard for unit testing?</strong><br data-start="2640" data-end="2643">
👉 Hard to <strong data-start="2654" data-end="2673">mock or replace</strong>. Global state may cause test dependencies.</p>
<p data-start="2718" data-end="2789"><strong data-start="2718" data-end="2726">Fix:</strong> Use <strong data-start="2731" data-end="2760">Dependency Injection (DI)</strong> instead of manual Singleton.</p>
<hr data-start="2791" data-end="2794">
<h3 data-start="2796" data-end="2810">🔸 Overuse</h3>
<p data-start="2811" data-end="2854"><strong data-start="2811" data-end="2852">Q12: When should you avoid Singleton?</strong></p>
<ul data-start="2855" data-end="3005">
<li data-start="2855" data-end="2893">
<p data-start="2857" data-end="2893">Stateful objects (e.g., UserSession)</p>
</li>
<li data-start="2894" data-end="2941">
<p data-start="2896" data-end="2941">In distributed systems (each JVM has its own)</p>
</li>
<li data-start="2942" data-end="3005">
<p data-start="2944" data-end="3005">When using frameworks like Spring (already manages lifecycle)</p>
</li>
</ul>
<hr data-start="3007" data-end="3010">
<h2 data-start="3012" data-end="3051">✅ 4. CODE PITFALLS &amp; IMPLEMENTATIONS</h2>
<h3 data-start="3053" data-end="3076">🧨 Your Code Issue:</h3>
<pre class="overflow-visible!" data-start="3077" data-end="3242"><div class="contain-inline-size rounded-2xl relative bg-token-sidebar-surface-primary"><div class="flex items-center text-token-text-secondary px-4 py-2 text-xs font-sans justify-between h-9 bg-token-sidebar-surface-primary select-none rounded-t-2xl">java</div><div class="sticky top-9"><div class="absolute end-0 bottom-0 flex h-9 items-center pe-2"><div class="bg-token-bg-elevated-secondary text-token-text-secondary flex items-center gap-4 rounded-sm px-2 font-sans text-xs"><button class="flex gap-1 items-center select-none py-1" aria-label="Copy"><svg width="20" height="20" viewBox="0 0 20 20" fill="currentColor" xmlns="http://www.w3.org/2000/svg" class="icon-sm"><path d="M12.668 10.667C12.668 9.95614 12.668 9.46258 12.6367 9.0791C12.6137 8.79732 12.5758 8.60761 12.5244 8.46387L12.4688 8.33399C12.3148 8.03193 12.0803 7.77885 11.793 7.60254L11.666 7.53125C11.508 7.45087 11.2963 7.39395 10.9209 7.36328C10.5374 7.33197 10.0439 7.33203 9.33301 7.33203H6.5C5.78896 7.33203 5.29563 7.33195 4.91211 7.36328C4.63016 7.38632 4.44065 7.42413 4.29688 7.47559L4.16699 7.53125C3.86488 7.68518 3.61186 7.9196 3.43555 8.20703L3.36524 8.33399C3.28478 8.49198 3.22795 8.70352 3.19727 9.0791C3.16595 9.46259 3.16504 9.95611 3.16504 10.667V13.5C3.16504 14.211 3.16593 14.7044 3.19727 15.0879C3.22797 15.4636 3.28473 15.675 3.36524 15.833L3.43555 15.959C3.61186 16.2466 3.86474 16.4807 4.16699 16.6348L4.29688 16.6914C4.44063 16.7428 4.63025 16.7797 4.91211 16.8027C5.29563 16.8341 5.78896 16.835 6.5 16.835H9.33301C10.0439 16.835 10.5374 16.8341 10.9209 16.8027C11.2965 16.772 11.508 16.7152 11.666 16.6348L11.793 16.5645C12.0804 16.3881 12.3148 16.1351 12.4688 15.833L12.5244 15.7031C12.5759 15.5594 12.6137 15.3698 12.6367 15.0879C12.6681 14.7044 12.668 14.211 12.668 13.5V10.667ZM13.998 12.665C14.4528 12.6634 14.8011 12.6602 15.0879 12.6367C15.4635 12.606 15.675 12.5492 15.833 12.4688L15.959 12.3975C16.2466 12.2211 16.4808 11.9682 16.6348 11.666L16.6914 11.5361C16.7428 11.3924 16.7797 11.2026 16.8027 10.9209C16.8341 10.5374 16.835 10.0439 16.835 9.33301V6.5C16.835 5.78896 16.8341 5.29563 16.8027 4.91211C16.7797 4.63025 16.7428 4.44063 16.6914 4.29688L16.6348 4.16699C16.4807 3.86474 16.2466 3.61186 15.959 3.43555L15.833 3.36524C15.675 3.28473 15.4636 3.22797 15.0879 3.19727C14.7044 3.16593 14.211 3.16504 13.5 3.16504H10.667C9.9561 3.16504 9.46259 3.16595 9.0791 3.19727C8.79739 3.22028 8.6076 3.2572 8.46387 3.30859L8.33399 3.36524C8.03176 3.51923 7.77886 3.75343 7.60254 4.04102L7.53125 4.16699C7.4508 4.32498 7.39397 4.53655 7.36328 4.91211C7.33985 5.19893 7.33562 5.54719 7.33399 6.00195H9.33301C10.022 6.00195 10.5791 6.00131 11.0293 6.03809C11.4873 6.07551 11.8937 6.15471 12.2705 6.34668L12.4883 6.46875C12.984 6.7728 13.3878 7.20854 13.6533 7.72949L13.7197 7.87207C13.8642 8.20859 13.9292 8.56974 13.9619 8.9707C13.9987 9.42092 13.998 9.97799 13.998 10.667V12.665ZM18.165 9.33301C18.165 10.022 18.1657 10.5791 18.1289 11.0293C18.0961 11.4302 18.0311 11.7914 17.8867 12.1279L17.8203 12.2705C17.5549 12.7914 17.1509 13.2272 16.6553 13.5313L16.4365 13.6533C16.0599 13.8452 15.6541 13.9245 15.1963 13.9619C14.8593 13.9895 14.4624 13.9935 13.9951 13.9951C13.9935 14.4624 13.9895 14.8593 13.9619 15.1963C13.9292 15.597 13.864 15.9576 13.7197 16.2939L13.6533 16.4365C13.3878 16.9576 12.9841 17.3941 12.4883 17.6982L12.2705 17.8203C11.8937 18.0123 11.4873 18.0915 11.0293 18.1289C10.5791 18.1657 10.022 18.165 9.33301 18.165H6.5C5.81091 18.165 5.25395 18.1657 4.80371 18.1289C4.40306 18.0962 4.04235 18.031 3.70606 17.8867L3.56348 17.8203C3.04244 17.5548 2.60585 17.151 2.30176 16.6553L2.17969 16.4365C1.98788 16.0599 1.90851 15.6541 1.87109 15.1963C1.83431 14.746 1.83496 14.1891 1.83496 13.5V10.667C1.83496 9.978 1.83432 9.42091 1.87109 8.9707C1.90851 8.5127 1.98772 8.10625 2.17969 7.72949L2.30176 7.51172C2.60586 7.0159 3.04236 6.6122 3.56348 6.34668L3.70606 6.28027C4.04237 6.136 4.40303 6.07083 4.80371 6.03809C5.14051 6.01057 5.53708 6.00551 6.00391 6.00391C6.00551 5.53708 6.01057 5.14051 6.03809 4.80371C6.0755 4.34588 6.15483 3.94012 6.34668 3.56348L6.46875 3.34473C6.77282 2.84912 7.20856 2.44514 7.72949 2.17969L7.87207 2.11328C8.20855 1.96886 8.56979 1.90385 8.9707 1.87109C9.42091 1.83432 9.978 1.83496 10.667 1.83496H13.5C14.1891 1.83496 14.746 1.83431 15.1963 1.87109C15.6541 1.90851 16.0599 1.98788 16.4365 2.17969L16.6553 2.30176C17.151 2.60585 17.5548 3.04244 17.8203 3.56348L17.8867 3.70606C18.031 4.04235 18.0962 4.40306 18.1289 4.80371C18.1657 5.25395 18.165 5.81091 18.165 6.5V9.33301Z"></path></svg>Copy code</button></div></div></div><div class="overflow-y-auto p-4" dir="ltr"><code class="whitespace-pre! language-java"><span><span><span class="hljs-keyword">public</span></span><span> </span><span><span class="hljs-keyword">static</span></span><span> EagerInitSingleTon </span><span><span class="hljs-title function_">getInstance</span></span><span><span class="hljs-params">()</span></span><span> {
    </span><span><span class="hljs-keyword">if</span></span><span> (instance == </span><span><span class="hljs-literal">null</span></span><span>) {
        </span><span><span class="hljs-keyword">return</span></span><span> </span><span><span class="hljs-keyword">new</span></span><span> </span><span><span class="hljs-title class_">EagerInitSingleTon</span></span><span>(); </span><span><span class="hljs-comment">// BAD</span></span><span>
    }
    </span><span><span class="hljs-keyword">return</span></span><span> instance;
}
</span></span></code></div></div></pre>
<p data-start="3243" data-end="3358"><strong data-start="3243" data-end="3255">Problem:</strong> Even in eager init, you're manually creating a new instance inside <code data-start="3323" data-end="3338">getInstance()</code> — breaks Singleton.</p>
<hr data-start="3360" data-end="3363">
<h3 data-start="3365" data-end="3395">✅ Correct Implementations:</h3>
<p data-start="3397" data-end="3448"><strong data-start="3397" data-end="3446">1. Eager Initialization (Simple, thread-safe)</strong></p>
<pre class="overflow-visible!" data-start="3449" data-end="3678"><div class="contain-inline-size rounded-2xl relative bg-token-sidebar-surface-primary"><div class="flex items-center text-token-text-secondary px-4 py-2 text-xs font-sans justify-between h-9 bg-token-sidebar-surface-primary select-none rounded-t-2xl">java</div><div class="sticky top-9"><div class="absolute end-0 bottom-0 flex h-9 items-center pe-2"><div class="bg-token-bg-elevated-secondary text-token-text-secondary flex items-center gap-4 rounded-sm px-2 font-sans text-xs"><button class="flex gap-1 items-center select-none py-1" aria-label="Copy"><svg width="20" height="20" viewBox="0 0 20 20" fill="currentColor" xmlns="http://www.w3.org/2000/svg" class="icon-sm"><path d="M12.668 10.667C12.668 9.95614 12.668 9.46258 12.6367 9.0791C12.6137 8.79732 12.5758 8.60761 12.5244 8.46387L12.4688 8.33399C12.3148 8.03193 12.0803 7.77885 11.793 7.60254L11.666 7.53125C11.508 7.45087 11.2963 7.39395 10.9209 7.36328C10.5374 7.33197 10.0439 7.33203 9.33301 7.33203H6.5C5.78896 7.33203 5.29563 7.33195 4.91211 7.36328C4.63016 7.38632 4.44065 7.42413 4.29688 7.47559L4.16699 7.53125C3.86488 7.68518 3.61186 7.9196 3.43555 8.20703L3.36524 8.33399C3.28478 8.49198 3.22795 8.70352 3.19727 9.0791C3.16595 9.46259 3.16504 9.95611 3.16504 10.667V13.5C3.16504 14.211 3.16593 14.7044 3.19727 15.0879C3.22797 15.4636 3.28473 15.675 3.36524 15.833L3.43555 15.959C3.61186 16.2466 3.86474 16.4807 4.16699 16.6348L4.29688 16.6914C4.44063 16.7428 4.63025 16.7797 4.91211 16.8027C5.29563 16.8341 5.78896 16.835 6.5 16.835H9.33301C10.0439 16.835 10.5374 16.8341 10.9209 16.8027C11.2965 16.772 11.508 16.7152 11.666 16.6348L11.793 16.5645C12.0804 16.3881 12.3148 16.1351 12.4688 15.833L12.5244 15.7031C12.5759 15.5594 12.6137 15.3698 12.6367 15.0879C12.6681 14.7044 12.668 14.211 12.668 13.5V10.667ZM13.998 12.665C14.4528 12.6634 14.8011 12.6602 15.0879 12.6367C15.4635 12.606 15.675 12.5492 15.833 12.4688L15.959 12.3975C16.2466 12.2211 16.4808 11.9682 16.6348 11.666L16.6914 11.5361C16.7428 11.3924 16.7797 11.2026 16.8027 10.9209C16.8341 10.5374 16.835 10.0439 16.835 9.33301V6.5C16.835 5.78896 16.8341 5.29563 16.8027 4.91211C16.7797 4.63025 16.7428 4.44063 16.6914 4.29688L16.6348 4.16699C16.4807 3.86474 16.2466 3.61186 15.959 3.43555L15.833 3.36524C15.675 3.28473 15.4636 3.22797 15.0879 3.19727C14.7044 3.16593 14.211 3.16504 13.5 3.16504H10.667C9.9561 3.16504 9.46259 3.16595 9.0791 3.19727C8.79739 3.22028 8.6076 3.2572 8.46387 3.30859L8.33399 3.36524C8.03176 3.51923 7.77886 3.75343 7.60254 4.04102L7.53125 4.16699C7.4508 4.32498 7.39397 4.53655 7.36328 4.91211C7.33985 5.19893 7.33562 5.54719 7.33399 6.00195H9.33301C10.022 6.00195 10.5791 6.00131 11.0293 6.03809C11.4873 6.07551 11.8937 6.15471 12.2705 6.34668L12.4883 6.46875C12.984 6.7728 13.3878 7.20854 13.6533 7.72949L13.7197 7.87207C13.8642 8.20859 13.9292 8.56974 13.9619 8.9707C13.9987 9.42092 13.998 9.97799 13.998 10.667V12.665ZM18.165 9.33301C18.165 10.022 18.1657 10.5791 18.1289 11.0293C18.0961 11.4302 18.0311 11.7914 17.8867 12.1279L17.8203 12.2705C17.5549 12.7914 17.1509 13.2272 16.6553 13.5313L16.4365 13.6533C16.0599 13.8452 15.6541 13.9245 15.1963 13.9619C14.8593 13.9895 14.4624 13.9935 13.9951 13.9951C13.9935 14.4624 13.9895 14.8593 13.9619 15.1963C13.9292 15.597 13.864 15.9576 13.7197 16.2939L13.6533 16.4365C13.3878 16.9576 12.9841 17.3941 12.4883 17.6982L12.2705 17.8203C11.8937 18.0123 11.4873 18.0915 11.0293 18.1289C10.5791 18.1657 10.022 18.165 9.33301 18.165H6.5C5.81091 18.165 5.25395 18.1657 4.80371 18.1289C4.40306 18.0962 4.04235 18.031 3.70606 17.8867L3.56348 17.8203C3.04244 17.5548 2.60585 17.151 2.30176 16.6553L2.17969 16.4365C1.98788 16.0599 1.90851 15.6541 1.87109 15.1963C1.83431 14.746 1.83496 14.1891 1.83496 13.5V10.667C1.83496 9.978 1.83432 9.42091 1.87109 8.9707C1.90851 8.5127 1.98772 8.10625 2.17969 7.72949L2.30176 7.51172C2.60586 7.0159 3.04236 6.6122 3.56348 6.34668L3.70606 6.28027C4.04237 6.136 4.40303 6.07083 4.80371 6.03809C5.14051 6.01057 5.53708 6.00551 6.00391 6.00391C6.00551 5.53708 6.01057 5.14051 6.03809 4.80371C6.0755 4.34588 6.15483 3.94012 6.34668 3.56348L6.46875 3.34473C6.77282 2.84912 7.20856 2.44514 7.72949 2.17969L7.87207 2.11328C8.20855 1.96886 8.56979 1.90385 8.9707 1.87109C9.42091 1.83432 9.978 1.83496 10.667 1.83496H13.5C14.1891 1.83496 14.746 1.83431 15.1963 1.87109C15.6541 1.90851 16.0599 1.98788 16.4365 2.17969L16.6553 2.30176C17.151 2.60585 17.5548 3.04244 17.8203 3.56348L17.8867 3.70606C18.031 4.04235 18.0962 4.40306 18.1289 4.80371C18.1657 5.25395 18.165 5.81091 18.165 6.5V9.33301Z"></path></svg>Copy code</button></div></div></div><div class="overflow-y-auto p-4" dir="ltr"><code class="whitespace-pre! language-java"><span><span><span class="hljs-keyword">public</span></span><span> </span><span><span class="hljs-keyword">class</span></span><span> </span><span><span class="hljs-title class_">EagerSingleton</span></span><span> {
    </span><span><span class="hljs-keyword">private</span></span><span> </span><span><span class="hljs-keyword">static</span></span><span> </span><span><span class="hljs-keyword">final</span></span><span> </span><span><span class="hljs-type">EagerSingleton</span></span><span> </span><span><span class="hljs-variable">instance</span></span><span> </span><span><span class="hljs-operator">=</span></span><span> </span><span><span class="hljs-keyword">new</span></span><span> </span><span><span class="hljs-title class_">EagerSingleton</span></span><span>();
    </span><span><span class="hljs-keyword">private</span></span><span> </span><span><span class="hljs-title function_">EagerSingleton</span></span><span><span class="hljs-params">()</span></span><span> { }
    </span><span><span class="hljs-keyword">public</span></span><span> </span><span><span class="hljs-keyword">static</span></span><span> EagerSingleton </span><span><span class="hljs-title function_">getInstance</span></span><span><span class="hljs-params">()</span></span><span> {
        </span><span><span class="hljs-keyword">return</span></span><span> instance;
    }
}
</span></span></code></div></div></pre>
<hr data-start="3680" data-end="3683">
<p data-start="3685" data-end="3731"><strong data-start="3685" data-end="3729">2. Lazy Initialization (Not thread-safe)</strong></p>
<pre class="overflow-visible!" data-start="3732" data-end="4014"><div class="contain-inline-size rounded-2xl relative bg-token-sidebar-surface-primary"><div class="flex items-center text-token-text-secondary px-4 py-2 text-xs font-sans justify-between h-9 bg-token-sidebar-surface-primary select-none rounded-t-2xl">java</div><div class="sticky top-9"><div class="absolute end-0 bottom-0 flex h-9 items-center pe-2"><div class="bg-token-bg-elevated-secondary text-token-text-secondary flex items-center gap-4 rounded-sm px-2 font-sans text-xs"><button class="flex gap-1 items-center select-none py-1" aria-label="Copy"><svg width="20" height="20" viewBox="0 0 20 20" fill="currentColor" xmlns="http://www.w3.org/2000/svg" class="icon-sm"><path d="M12.668 10.667C12.668 9.95614 12.668 9.46258 12.6367 9.0791C12.6137 8.79732 12.5758 8.60761 12.5244 8.46387L12.4688 8.33399C12.3148 8.03193 12.0803 7.77885 11.793 7.60254L11.666 7.53125C11.508 7.45087 11.2963 7.39395 10.9209 7.36328C10.5374 7.33197 10.0439 7.33203 9.33301 7.33203H6.5C5.78896 7.33203 5.29563 7.33195 4.91211 7.36328C4.63016 7.38632 4.44065 7.42413 4.29688 7.47559L4.16699 7.53125C3.86488 7.68518 3.61186 7.9196 3.43555 8.20703L3.36524 8.33399C3.28478 8.49198 3.22795 8.70352 3.19727 9.0791C3.16595 9.46259 3.16504 9.95611 3.16504 10.667V13.5C3.16504 14.211 3.16593 14.7044 3.19727 15.0879C3.22797 15.4636 3.28473 15.675 3.36524 15.833L3.43555 15.959C3.61186 16.2466 3.86474 16.4807 4.16699 16.6348L4.29688 16.6914C4.44063 16.7428 4.63025 16.7797 4.91211 16.8027C5.29563 16.8341 5.78896 16.835 6.5 16.835H9.33301C10.0439 16.835 10.5374 16.8341 10.9209 16.8027C11.2965 16.772 11.508 16.7152 11.666 16.6348L11.793 16.5645C12.0804 16.3881 12.3148 16.1351 12.4688 15.833L12.5244 15.7031C12.5759 15.5594 12.6137 15.3698 12.6367 15.0879C12.6681 14.7044 12.668 14.211 12.668 13.5V10.667ZM13.998 12.665C14.4528 12.6634 14.8011 12.6602 15.0879 12.6367C15.4635 12.606 15.675 12.5492 15.833 12.4688L15.959 12.3975C16.2466 12.2211 16.4808 11.9682 16.6348 11.666L16.6914 11.5361C16.7428 11.3924 16.7797 11.2026 16.8027 10.9209C16.8341 10.5374 16.835 10.0439 16.835 9.33301V6.5C16.835 5.78896 16.8341 5.29563 16.8027 4.91211C16.7797 4.63025 16.7428 4.44063 16.6914 4.29688L16.6348 4.16699C16.4807 3.86474 16.2466 3.61186 15.959 3.43555L15.833 3.36524C15.675 3.28473 15.4636 3.22797 15.0879 3.19727C14.7044 3.16593 14.211 3.16504 13.5 3.16504H10.667C9.9561 3.16504 9.46259 3.16595 9.0791 3.19727C8.79739 3.22028 8.6076 3.2572 8.46387 3.30859L8.33399 3.36524C8.03176 3.51923 7.77886 3.75343 7.60254 4.04102L7.53125 4.16699C7.4508 4.32498 7.39397 4.53655 7.36328 4.91211C7.33985 5.19893 7.33562 5.54719 7.33399 6.00195H9.33301C10.022 6.00195 10.5791 6.00131 11.0293 6.03809C11.4873 6.07551 11.8937 6.15471 12.2705 6.34668L12.4883 6.46875C12.984 6.7728 13.3878 7.20854 13.6533 7.72949L13.7197 7.87207C13.8642 8.20859 13.9292 8.56974 13.9619 8.9707C13.9987 9.42092 13.998 9.97799 13.998 10.667V12.665ZM18.165 9.33301C18.165 10.022 18.1657 10.5791 18.1289 11.0293C18.0961 11.4302 18.0311 11.7914 17.8867 12.1279L17.8203 12.2705C17.5549 12.7914 17.1509 13.2272 16.6553 13.5313L16.4365 13.6533C16.0599 13.8452 15.6541 13.9245 15.1963 13.9619C14.8593 13.9895 14.4624 13.9935 13.9951 13.9951C13.9935 14.4624 13.9895 14.8593 13.9619 15.1963C13.9292 15.597 13.864 15.9576 13.7197 16.2939L13.6533 16.4365C13.3878 16.9576 12.9841 17.3941 12.4883 17.6982L12.2705 17.8203C11.8937 18.0123 11.4873 18.0915 11.0293 18.1289C10.5791 18.1657 10.022 18.165 9.33301 18.165H6.5C5.81091 18.165 5.25395 18.1657 4.80371 18.1289C4.40306 18.0962 4.04235 18.031 3.70606 17.8867L3.56348 17.8203C3.04244 17.5548 2.60585 17.151 2.30176 16.6553L2.17969 16.4365C1.98788 16.0599 1.90851 15.6541 1.87109 15.1963C1.83431 14.746 1.83496 14.1891 1.83496 13.5V10.667C1.83496 9.978 1.83432 9.42091 1.87109 8.9707C1.90851 8.5127 1.98772 8.10625 2.17969 7.72949L2.30176 7.51172C2.60586 7.0159 3.04236 6.6122 3.56348 6.34668L3.70606 6.28027C4.04237 6.136 4.40303 6.07083 4.80371 6.03809C5.14051 6.01057 5.53708 6.00551 6.00391 6.00391C6.00551 5.53708 6.01057 5.14051 6.03809 4.80371C6.0755 4.34588 6.15483 3.94012 6.34668 3.56348L6.46875 3.34473C6.77282 2.84912 7.20856 2.44514 7.72949 2.17969L7.87207 2.11328C8.20855 1.96886 8.56979 1.90385 8.9707 1.87109C9.42091 1.83432 9.978 1.83496 10.667 1.83496H13.5C14.1891 1.83496 14.746 1.83431 15.1963 1.87109C15.6541 1.90851 16.0599 1.98788 16.4365 2.17969L16.6553 2.30176C17.151 2.60585 17.5548 3.04244 17.8203 3.56348L17.8867 3.70606C18.031 4.04235 18.0962 4.40306 18.1289 4.80371C18.1657 5.25395 18.165 5.81091 18.165 6.5V9.33301Z"></path></svg>Copy code</button></div></div></div><div class="overflow-y-auto p-4" dir="ltr"><code class="whitespace-pre! language-java"><span><span><span class="hljs-keyword">public</span></span><span> </span><span><span class="hljs-keyword">class</span></span><span> </span><span><span class="hljs-title class_">LazySingleton</span></span><span> {
    </span><span><span class="hljs-keyword">private</span></span><span> </span><span><span class="hljs-keyword">static</span></span><span> LazySingleton instance;
    </span><span><span class="hljs-keyword">private</span></span><span> </span><span><span class="hljs-title function_">LazySingleton</span></span><span><span class="hljs-params">()</span></span><span> { }
    </span><span><span class="hljs-keyword">public</span></span><span> </span><span><span class="hljs-keyword">static</span></span><span> LazySingleton </span><span><span class="hljs-title function_">getInstance</span></span><span><span class="hljs-params">()</span></span><span> {
        </span><span><span class="hljs-keyword">if</span></span><span> (instance == </span><span><span class="hljs-literal">null</span></span><span>) {
            instance = </span><span><span class="hljs-keyword">new</span></span><span> </span><span><span class="hljs-title class_">LazySingleton</span></span><span>();
        }
        </span><span><span class="hljs-keyword">return</span></span><span> instance;
    }
}
</span></span></code></div></div></pre>
<hr data-start="4016" data-end="4019">
<p data-start="4021" data-end="4073"><strong data-start="4021" data-end="4071">3. Double-Checked Locking (Thread-safe + lazy)</strong></p>
<pre class="overflow-visible!" data-start="4074" data-end="4531"><div class="contain-inline-size rounded-2xl relative bg-token-sidebar-surface-primary"><div class="flex items-center text-token-text-secondary px-4 py-2 text-xs font-sans justify-between h-9 bg-token-sidebar-surface-primary select-none rounded-t-2xl">java</div><div class="sticky top-9"><div class="absolute end-0 bottom-0 flex h-9 items-center pe-2"><div class="bg-token-bg-elevated-secondary text-token-text-secondary flex items-center gap-4 rounded-sm px-2 font-sans text-xs"><button class="flex gap-1 items-center select-none py-1" aria-label="Copy"><svg width="20" height="20" viewBox="0 0 20 20" fill="currentColor" xmlns="http://www.w3.org/2000/svg" class="icon-sm"><path d="M12.668 10.667C12.668 9.95614 12.668 9.46258 12.6367 9.0791C12.6137 8.79732 12.5758 8.60761 12.5244 8.46387L12.4688 8.33399C12.3148 8.03193 12.0803 7.77885 11.793 7.60254L11.666 7.53125C11.508 7.45087 11.2963 7.39395 10.9209 7.36328C10.5374 7.33197 10.0439 7.33203 9.33301 7.33203H6.5C5.78896 7.33203 5.29563 7.33195 4.91211 7.36328C4.63016 7.38632 4.44065 7.42413 4.29688 7.47559L4.16699 7.53125C3.86488 7.68518 3.61186 7.9196 3.43555 8.20703L3.36524 8.33399C3.28478 8.49198 3.22795 8.70352 3.19727 9.0791C3.16595 9.46259 3.16504 9.95611 3.16504 10.667V13.5C3.16504 14.211 3.16593 14.7044 3.19727 15.0879C3.22797 15.4636 3.28473 15.675 3.36524 15.833L3.43555 15.959C3.61186 16.2466 3.86474 16.4807 4.16699 16.6348L4.29688 16.6914C4.44063 16.7428 4.63025 16.7797 4.91211 16.8027C5.29563 16.8341 5.78896 16.835 6.5 16.835H9.33301C10.0439 16.835 10.5374 16.8341 10.9209 16.8027C11.2965 16.772 11.508 16.7152 11.666 16.6348L11.793 16.5645C12.0804 16.3881 12.3148 16.1351 12.4688 15.833L12.5244 15.7031C12.5759 15.5594 12.6137 15.3698 12.6367 15.0879C12.6681 14.7044 12.668 14.211 12.668 13.5V10.667ZM13.998 12.665C14.4528 12.6634 14.8011 12.6602 15.0879 12.6367C15.4635 12.606 15.675 12.5492 15.833 12.4688L15.959 12.3975C16.2466 12.2211 16.4808 11.9682 16.6348 11.666L16.6914 11.5361C16.7428 11.3924 16.7797 11.2026 16.8027 10.9209C16.8341 10.5374 16.835 10.0439 16.835 9.33301V6.5C16.835 5.78896 16.8341 5.29563 16.8027 4.91211C16.7797 4.63025 16.7428 4.44063 16.6914 4.29688L16.6348 4.16699C16.4807 3.86474 16.2466 3.61186 15.959 3.43555L15.833 3.36524C15.675 3.28473 15.4636 3.22797 15.0879 3.19727C14.7044 3.16593 14.211 3.16504 13.5 3.16504H10.667C9.9561 3.16504 9.46259 3.16595 9.0791 3.19727C8.79739 3.22028 8.6076 3.2572 8.46387 3.30859L8.33399 3.36524C8.03176 3.51923 7.77886 3.75343 7.60254 4.04102L7.53125 4.16699C7.4508 4.32498 7.39397 4.53655 7.36328 4.91211C7.33985 5.19893 7.33562 5.54719 7.33399 6.00195H9.33301C10.022 6.00195 10.5791 6.00131 11.0293 6.03809C11.4873 6.07551 11.8937 6.15471 12.2705 6.34668L12.4883 6.46875C12.984 6.7728 13.3878 7.20854 13.6533 7.72949L13.7197 7.87207C13.8642 8.20859 13.9292 8.56974 13.9619 8.9707C13.9987 9.42092 13.998 9.97799 13.998 10.667V12.665ZM18.165 9.33301C18.165 10.022 18.1657 10.5791 18.1289 11.0293C18.0961 11.4302 18.0311 11.7914 17.8867 12.1279L17.8203 12.2705C17.5549 12.7914 17.1509 13.2272 16.6553 13.5313L16.4365 13.6533C16.0599 13.8452 15.6541 13.9245 15.1963 13.9619C14.8593 13.9895 14.4624 13.9935 13.9951 13.9951C13.9935 14.4624 13.9895 14.8593 13.9619 15.1963C13.9292 15.597 13.864 15.9576 13.7197 16.2939L13.6533 16.4365C13.3878 16.9576 12.9841 17.3941 12.4883 17.6982L12.2705 17.8203C11.8937 18.0123 11.4873 18.0915 11.0293 18.1289C10.5791 18.1657 10.022 18.165 9.33301 18.165H6.5C5.81091 18.165 5.25395 18.1657 4.80371 18.1289C4.40306 18.0962 4.04235 18.031 3.70606 17.8867L3.56348 17.8203C3.04244 17.5548 2.60585 17.151 2.30176 16.6553L2.17969 16.4365C1.98788 16.0599 1.90851 15.6541 1.87109 15.1963C1.83431 14.746 1.83496 14.1891 1.83496 13.5V10.667C1.83496 9.978 1.83432 9.42091 1.87109 8.9707C1.90851 8.5127 1.98772 8.10625 2.17969 7.72949L2.30176 7.51172C2.60586 7.0159 3.04236 6.6122 3.56348 6.34668L3.70606 6.28027C4.04237 6.136 4.40303 6.07083 4.80371 6.03809C5.14051 6.01057 5.53708 6.00551 6.00391 6.00391C6.00551 5.53708 6.01057 5.14051 6.03809 4.80371C6.0755 4.34588 6.15483 3.94012 6.34668 3.56348L6.46875 3.34473C6.77282 2.84912 7.20856 2.44514 7.72949 2.17969L7.87207 2.11328C8.20855 1.96886 8.56979 1.90385 8.9707 1.87109C9.42091 1.83432 9.978 1.83496 10.667 1.83496H13.5C14.1891 1.83496 14.746 1.83431 15.1963 1.87109C15.6541 1.90851 16.0599 1.98788 16.4365 2.17969L16.6553 2.30176C17.151 2.60585 17.5548 3.04244 17.8203 3.56348L17.8867 3.70606C18.031 4.04235 18.0962 4.40306 18.1289 4.80371C18.1657 5.25395 18.165 5.81091 18.165 6.5V9.33301Z"></path></svg>Copy code</button></div></div></div><div class="overflow-y-auto p-4" dir="ltr"><code class="whitespace-pre! language-java"><span><span><span class="hljs-keyword">public</span></span><span> </span><span><span class="hljs-keyword">class</span></span><span> </span><span><span class="hljs-title class_">ThreadSafeSingleton</span></span><span> {
    </span><span><span class="hljs-keyword">private</span></span><span> </span><span><span class="hljs-keyword">static</span></span><span> </span><span><span class="hljs-keyword">volatile</span></span><span> ThreadSafeSingleton instance;
    </span><span><span class="hljs-keyword">private</span></span><span> </span><span><span class="hljs-title function_">ThreadSafeSingleton</span></span><span><span class="hljs-params">()</span></span><span> { }

    </span><span><span class="hljs-keyword">public</span></span><span> </span><span><span class="hljs-keyword">static</span></span><span> ThreadSafeSingleton </span><span><span class="hljs-title function_">getInstance</span></span><span><span class="hljs-params">()</span></span><span> {
        </span><span><span class="hljs-keyword">if</span></span><span> (instance == </span><span><span class="hljs-literal">null</span></span><span>) {
            </span><span><span class="hljs-keyword">synchronized</span></span><span> (ThreadSafeSingleton.class) {
                </span><span><span class="hljs-keyword">if</span></span><span> (instance == </span><span><span class="hljs-literal">null</span></span><span>) {
                    instance = </span><span><span class="hljs-keyword">new</span></span><span> </span><span><span class="hljs-title class_">ThreadSafeSingleton</span></span><span>();
                }
            }
        }
        </span><span><span class="hljs-keyword">return</span></span><span> instance;
    }

}
</span></span></code></div></div></pre>

<hr data-start="4533" data-end="4536">
<p data-start="4538" data-end="4582"><strong data-start="4538" data-end="4580">4. Bill Pugh Singleton (Best Practice)</strong></p>
<pre class="overflow-visible!" data-start="4583" data-end="4880"><div class="contain-inline-size rounded-2xl relative bg-token-sidebar-surface-primary"><div class="flex items-center text-token-text-secondary px-4 py-2 text-xs font-sans justify-between h-9 bg-token-sidebar-surface-primary select-none rounded-t-2xl">java</div><div class="sticky top-9"><div class="absolute end-0 bottom-0 flex h-9 items-center pe-2"><div class="bg-token-bg-elevated-secondary text-token-text-secondary flex items-center gap-4 rounded-sm px-2 font-sans text-xs"><button class="flex gap-1 items-center select-none py-1" aria-label="Copy"><svg width="20" height="20" viewBox="0 0 20 20" fill="currentColor" xmlns="http://www.w3.org/2000/svg" class="icon-sm"><path d="M12.668 10.667C12.668 9.95614 12.668 9.46258 12.6367 9.0791C12.6137 8.79732 12.5758 8.60761 12.5244 8.46387L12.4688 8.33399C12.3148 8.03193 12.0803 7.77885 11.793 7.60254L11.666 7.53125C11.508 7.45087 11.2963 7.39395 10.9209 7.36328C10.5374 7.33197 10.0439 7.33203 9.33301 7.33203H6.5C5.78896 7.33203 5.29563 7.33195 4.91211 7.36328C4.63016 7.38632 4.44065 7.42413 4.29688 7.47559L4.16699 7.53125C3.86488 7.68518 3.61186 7.9196 3.43555 8.20703L3.36524 8.33399C3.28478 8.49198 3.22795 8.70352 3.19727 9.0791C3.16595 9.46259 3.16504 9.95611 3.16504 10.667V13.5C3.16504 14.211 3.16593 14.7044 3.19727 15.0879C3.22797 15.4636 3.28473 15.675 3.36524 15.833L3.43555 15.959C3.61186 16.2466 3.86474 16.4807 4.16699 16.6348L4.29688 16.6914C4.44063 16.7428 4.63025 16.7797 4.91211 16.8027C5.29563 16.8341 5.78896 16.835 6.5 16.835H9.33301C10.0439 16.835 10.5374 16.8341 10.9209 16.8027C11.2965 16.772 11.508 16.7152 11.666 16.6348L11.793 16.5645C12.0804 16.3881 12.3148 16.1351 12.4688 15.833L12.5244 15.7031C12.5759 15.5594 12.6137 15.3698 12.6367 15.0879C12.6681 14.7044 12.668 14.211 12.668 13.5V10.667ZM13.998 12.665C14.4528 12.6634 14.8011 12.6602 15.0879 12.6367C15.4635 12.606 15.675 12.5492 15.833 12.4688L15.959 12.3975C16.2466 12.2211 16.4808 11.9682 16.6348 11.666L16.6914 11.5361C16.7428 11.3924 16.7797 11.2026 16.8027 10.9209C16.8341 10.5374 16.835 10.0439 16.835 9.33301V6.5C16.835 5.78896 16.8341 5.29563 16.8027 4.91211C16.7797 4.63025 16.7428 4.44063 16.6914 4.29688L16.6348 4.16699C16.4807 3.86474 16.2466 3.61186 15.959 3.43555L15.833 3.36524C15.675 3.28473 15.4636 3.22797 15.0879 3.19727C14.7044 3.16593 14.211 3.16504 13.5 3.16504H10.667C9.9561 3.16504 9.46259 3.16595 9.0791 3.19727C8.79739 3.22028 8.6076 3.2572 8.46387 3.30859L8.33399 3.36524C8.03176 3.51923 7.77886 3.75343 7.60254 4.04102L7.53125 4.16699C7.4508 4.32498 7.39397 4.53655 7.36328 4.91211C7.33985 5.19893 7.33562 5.54719 7.33399 6.00195H9.33301C10.022 6.00195 10.5791 6.00131 11.0293 6.03809C11.4873 6.07551 11.8937 6.15471 12.2705 6.34668L12.4883 6.46875C12.984 6.7728 13.3878 7.20854 13.6533 7.72949L13.7197 7.87207C13.8642 8.20859 13.9292 8.56974 13.9619 8.9707C13.9987 9.42092 13.998 9.97799 13.998 10.667V12.665ZM18.165 9.33301C18.165 10.022 18.1657 10.5791 18.1289 11.0293C18.0961 11.4302 18.0311 11.7914 17.8867 12.1279L17.8203 12.2705C17.5549 12.7914 17.1509 13.2272 16.6553 13.5313L16.4365 13.6533C16.0599 13.8452 15.6541 13.9245 15.1963 13.9619C14.8593 13.9895 14.4624 13.9935 13.9951 13.9951C13.9935 14.4624 13.9895 14.8593 13.9619 15.1963C13.9292 15.597 13.864 15.9576 13.7197 16.2939L13.6533 16.4365C13.3878 16.9576 12.9841 17.3941 12.4883 17.6982L12.2705 17.8203C11.8937 18.0123 11.4873 18.0915 11.0293 18.1289C10.5791 18.1657 10.022 18.165 9.33301 18.165H6.5C5.81091 18.165 5.25395 18.1657 4.80371 18.1289C4.40306 18.0962 4.04235 18.031 3.70606 17.8867L3.56348 17.8203C3.04244 17.5548 2.60585 17.151 2.30176 16.6553L2.17969 16.4365C1.98788 16.0599 1.90851 15.6541 1.87109 15.1963C1.83431 14.746 1.83496 14.1891 1.83496 13.5V10.667C1.83496 9.978 1.83432 9.42091 1.87109 8.9707C1.90851 8.5127 1.98772 8.10625 2.17969 7.72949L2.30176 7.51172C2.60586 7.0159 3.04236 6.6122 3.56348 6.34668L3.70606 6.28027C4.04237 6.136 4.40303 6.07083 4.80371 6.03809C5.14051 6.01057 5.53708 6.00551 6.00391 6.00391C6.00551 5.53708 6.01057 5.14051 6.03809 4.80371C6.0755 4.34588 6.15483 3.94012 6.34668 3.56348L6.46875 3.34473C6.77282 2.84912 7.20856 2.44514 7.72949 2.17969L7.87207 2.11328C8.20855 1.96886 8.56979 1.90385 8.9707 1.87109C9.42091 1.83432 9.978 1.83496 10.667 1.83496H13.5C14.1891 1.83496 14.746 1.83431 15.1963 1.87109C15.6541 1.90851 16.0599 1.98788 16.4365 2.17969L16.6553 2.30176C17.151 2.60585 17.5548 3.04244 17.8203 3.56348L17.8867 3.70606C18.031 4.04235 18.0962 4.40306 18.1289 4.80371C18.1657 5.25395 18.165 5.81091 18.165 6.5V9.33301Z"></path></svg>Copy code</button></div></div></div><div class="overflow-y-auto p-4" dir="ltr"><code class="whitespace-pre! language-java"><span><span><span class="hljs-keyword">public</span></span><span> </span><span><span class="hljs-keyword">class</span></span><span> </span><span><span class="hljs-title class_">BillPughSingleton</span></span><span> {
    </span><span><span class="hljs-keyword">private</span></span><span> </span><span><span class="hljs-title function_">BillPughSingleton</span></span><span><span class="hljs-params">()</span></span><span> { }

    </span><span><span class="hljs-keyword">private</span></span><span> </span><span><span class="hljs-keyword">static</span></span><span> </span><span><span class="hljs-keyword">class</span></span><span> </span><span><span class="hljs-title class_">Helper</span></span><span> {
        </span><span><span class="hljs-keyword">private</span></span><span> </span><span><span class="hljs-keyword">static</span></span><span> </span><span><span class="hljs-keyword">final</span></span><span> </span><span><span class="hljs-type">BillPughSingleton</span></span><span> </span><span><span class="hljs-variable">INSTANCE</span></span><span> </span><span><span class="hljs-operator">=</span></span><span> </span><span><span class="hljs-keyword">new</span></span><span> </span><span><span class="hljs-title class_">BillPughSingleton</span></span><span>();
    }

    </span><span><span class="hljs-keyword">public</span></span><span> </span><span><span class="hljs-keyword">static</span></span><span> BillPughSingleton </span><span><span class="hljs-title function_">getInstance</span></span><span><span class="hljs-params">()</span></span><span> {
        </span><span><span class="hljs-keyword">return</span></span><span> Helper.INSTANCE;
    }

}
</span></span></code></div></div></pre>

<hr data-start="4882" data-end="4885">
<p data-start="4887" data-end="4953"><strong data-start="4887" data-end="4951">5. Enum Singleton (Best for serialization/reflection safety)</strong></p>
<pre class="overflow-visible!" data-start="4954" data-end="5097"><div class="contain-inline-size rounded-2xl relative bg-token-sidebar-surface-primary"><div class="flex items-center text-token-text-secondary px-4 py-2 text-xs font-sans justify-between h-9 bg-token-sidebar-surface-primary select-none rounded-t-2xl">java</div><div class="sticky top-9"><div class="absolute end-0 bottom-0 flex h-9 items-center pe-2"><div class="bg-token-bg-elevated-secondary text-token-text-secondary flex items-center gap-4 rounded-sm px-2 font-sans text-xs"><button class="flex gap-1 items-center select-none py-1" aria-label="Copy"><svg width="20" height="20" viewBox="0 0 20 20" fill="currentColor" xmlns="http://www.w3.org/2000/svg" class="icon-sm"><path d="M12.668 10.667C12.668 9.95614 12.668 9.46258 12.6367 9.0791C12.6137 8.79732 12.5758 8.60761 12.5244 8.46387L12.4688 8.33399C12.3148 8.03193 12.0803 7.77885 11.793 7.60254L11.666 7.53125C11.508 7.45087 11.2963 7.39395 10.9209 7.36328C10.5374 7.33197 10.0439 7.33203 9.33301 7.33203H6.5C5.78896 7.33203 5.29563 7.33195 4.91211 7.36328C4.63016 7.38632 4.44065 7.42413 4.29688 7.47559L4.16699 7.53125C3.86488 7.68518 3.61186 7.9196 3.43555 8.20703L3.36524 8.33399C3.28478 8.49198 3.22795 8.70352 3.19727 9.0791C3.16595 9.46259 3.16504 9.95611 3.16504 10.667V13.5C3.16504 14.211 3.16593 14.7044 3.19727 15.0879C3.22797 15.4636 3.28473 15.675 3.36524 15.833L3.43555 15.959C3.61186 16.2466 3.86474 16.4807 4.16699 16.6348L4.29688 16.6914C4.44063 16.7428 4.63025 16.7797 4.91211 16.8027C5.29563 16.8341 5.78896 16.835 6.5 16.835H9.33301C10.0439 16.835 10.5374 16.8341 10.9209 16.8027C11.2965 16.772 11.508 16.7152 11.666 16.6348L11.793 16.5645C12.0804 16.3881 12.3148 16.1351 12.4688 15.833L12.5244 15.7031C12.5759 15.5594 12.6137 15.3698 12.6367 15.0879C12.6681 14.7044 12.668 14.211 12.668 13.5V10.667ZM13.998 12.665C14.4528 12.6634 14.8011 12.6602 15.0879 12.6367C15.4635 12.606 15.675 12.5492 15.833 12.4688L15.959 12.3975C16.2466 12.2211 16.4808 11.9682 16.6348 11.666L16.6914 11.5361C16.7428 11.3924 16.7797 11.2026 16.8027 10.9209C16.8341 10.5374 16.835 10.0439 16.835 9.33301V6.5C16.835 5.78896 16.8341 5.29563 16.8027 4.91211C16.7797 4.63025 16.7428 4.44063 16.6914 4.29688L16.6348 4.16699C16.4807 3.86474 16.2466 3.61186 15.959 3.43555L15.833 3.36524C15.675 3.28473 15.4636 3.22797 15.0879 3.19727C14.7044 3.16593 14.211 3.16504 13.5 3.16504H10.667C9.9561 3.16504 9.46259 3.16595 9.0791 3.19727C8.79739 3.22028 8.6076 3.2572 8.46387 3.30859L8.33399 3.36524C8.03176 3.51923 7.77886 3.75343 7.60254 4.04102L7.53125 4.16699C7.4508 4.32498 7.39397 4.53655 7.36328 4.91211C7.33985 5.19893 7.33562 5.54719 7.33399 6.00195H9.33301C10.022 6.00195 10.5791 6.00131 11.0293 6.03809C11.4873 6.07551 11.8937 6.15471 12.2705 6.34668L12.4883 6.46875C12.984 6.7728 13.3878 7.20854 13.6533 7.72949L13.7197 7.87207C13.8642 8.20859 13.9292 8.56974 13.9619 8.9707C13.9987 9.42092 13.998 9.97799 13.998 10.667V12.665ZM18.165 9.33301C18.165 10.022 18.1657 10.5791 18.1289 11.0293C18.0961 11.4302 18.0311 11.7914 17.8867 12.1279L17.8203 12.2705C17.5549 12.7914 17.1509 13.2272 16.6553 13.5313L16.4365 13.6533C16.0599 13.8452 15.6541 13.9245 15.1963 13.9619C14.8593 13.9895 14.4624 13.9935 13.9951 13.9951C13.9935 14.4624 13.9895 14.8593 13.9619 15.1963C13.9292 15.597 13.864 15.9576 13.7197 16.2939L13.6533 16.4365C13.3878 16.9576 12.9841 17.3941 12.4883 17.6982L12.2705 17.8203C11.8937 18.0123 11.4873 18.0915 11.0293 18.1289C10.5791 18.1657 10.022 18.165 9.33301 18.165H6.5C5.81091 18.165 5.25395 18.1657 4.80371 18.1289C4.40306 18.0962 4.04235 18.031 3.70606 17.8867L3.56348 17.8203C3.04244 17.5548 2.60585 17.151 2.30176 16.6553L2.17969 16.4365C1.98788 16.0599 1.90851 15.6541 1.87109 15.1963C1.83431 14.746 1.83496 14.1891 1.83496 13.5V10.667C1.83496 9.978 1.83432 9.42091 1.87109 8.9707C1.90851 8.5127 1.98772 8.10625 2.17969 7.72949L2.30176 7.51172C2.60586 7.0159 3.04236 6.6122 3.56348 6.34668L3.70606 6.28027C4.04237 6.136 4.40303 6.07083 4.80371 6.03809C5.14051 6.01057 5.53708 6.00551 6.00391 6.00391C6.00551 5.53708 6.01057 5.14051 6.03809 4.80371C6.0755 4.34588 6.15483 3.94012 6.34668 3.56348L6.46875 3.34473C6.77282 2.84912 7.20856 2.44514 7.72949 2.17969L7.87207 2.11328C8.20855 1.96886 8.56979 1.90385 8.9707 1.87109C9.42091 1.83432 9.978 1.83496 10.667 1.83496H13.5C14.1891 1.83496 14.746 1.83431 15.1963 1.87109C15.6541 1.90851 16.0599 1.98788 16.4365 2.17969L16.6553 2.30176C17.151 2.60585 17.5548 3.04244 17.8203 3.56348L17.8867 3.70606C18.031 4.04235 18.0962 4.40306 18.1289 4.80371C18.1657 5.25395 18.165 5.81091 18.165 6.5V9.33301Z"></path></svg>Copy code</button></div></div></div><div class="overflow-y-auto p-4" dir="ltr"><code class="whitespace-pre! language-java"><span><span><span class="hljs-keyword">public</span></span><span> </span><span><span class="hljs-keyword">enum</span></span><span> </span><span><span class="hljs-title class_">EnumSingleton</span></span><span> {
    INSTANCE;
    </span><span><span class="hljs-keyword">public</span></span><span> </span><span><span class="hljs-keyword">void</span></span><span> </span><span><span class="hljs-title function_">doSomething</span></span><span><span class="hljs-params">()</span></span><span> {
        System.out.println(</span><span><span class="hljs-string">"Doing something..."</span></span><span>);
    }
}
</span></span></code></div></div></pre>
<hr data-start="5099" data-end="5102">
<h2 data-start="5104" data-end="5136">✅ 5. SCENARIO-BASED QUESTIONS</h2>
<p data-start="5138" data-end="5282"><strong data-start="5138" data-end="5177">Q13: Where would you use Singleton?</strong><br data-start="5177" data-end="5180">
👉 Logging, configuration, cache manager, thread pool manager, DB connection pool, service registries.</p>
<hr data-start="5284" data-end="5287">
<p data-start="5289" data-end="5481"><strong data-start="5289" data-end="5340">Q14: Should you use Singleton in Microservices?</strong><br data-start="5340" data-end="5343">
👉 Be careful — each service instance has its <strong data-start="5389" data-end="5406">own Singleton</strong>. Use external coordination (e.g., Redis, config servers) for shared state.</p>
<hr data-start="5483" data-end="5486">
<p data-start="5488" data-end="5624"><strong data-start="5488" data-end="5548">Q15: What if Singleton holds state (e.g., user session)?</strong><br data-start="5548" data-end="5551">
👉 Risky — can lead to <strong data-start="5574" data-end="5593">race conditions</strong>. Keep Singleton <strong data-start="5610" data-end="5623">stateless</strong>.</p>
<hr data-start="5626" data-end="5629">
<p data-start="5631" data-end="5716"><strong data-start="5631" data-end="5684">Q16: How to use Singleton in distributed systems?</strong><br data-start="5684" data-end="5687">
👉 Use external systems like:</p>
<ul data-start="5717" data-end="5765">
<li data-start="5717" data-end="5732">
<p data-start="5719" data-end="5732"><strong data-start="5719" data-end="5732">ZooKeeper</strong></p>
</li>
<li data-start="5733" data-end="5744">
<p data-start="5735" data-end="5744"><strong data-start="5735" data-end="5744">Redis</strong></p>
</li>
<li data-start="5745" data-end="5765">
<p data-start="5747" data-end="5765"><strong data-start="5747" data-end="5765">Database locks</strong></p>
</li>
</ul>
<hr data-start="5767" data-end="5770">
<p data-start="5772" data-end="5819"><strong data-start="5772" data-end="5809">Q17: Can Singleton violate SOLID?</strong><br data-start="5809" data-end="5812">
👉 Yes:</p>
<ul data-start="5820" data-end="5938">
<li data-start="5820" data-end="5894">
<p data-start="5822" data-end="5894">Violates <strong data-start="5831" data-end="5856">Single Responsibility</strong> (does both creation &amp; business logic)</p>
</li>
<li data-start="5895" data-end="5938">
<p data-start="5897" data-end="5938">Violates <strong data-start="5906" data-end="5921">Open/Closed</strong> (hard to extend)</p>
</li>
</ul>
<hr data-start="5940" data-end="5943">
<p data-start="5945" data-end="6065"><strong data-start="5945" data-end="5985">Q18: Do we need Singleton in Spring?</strong><br data-start="5985" data-end="5988">
👉 No — Spring beans are <strong data-start="6013" data-end="6037">Singleton by default</strong> (per <code data-start="6043" data-end="6063">ApplicationContext</code>).</p>
<hr data-start="6067" data-end="6070">
<p data-start="6072" data-end="6235"><strong data-start="6072" data-end="6137">Q19: How would you reduce tight coupling caused by Singleton?</strong><br data-start="6137" data-end="6140">
👉 Use <strong data-start="6147" data-end="6171">Dependency Injection</strong> — inject Singleton instead of calling <code data-start="6210" data-end="6225">getInstance()</code> directly.</p>
<hr data-start="6237" data-end="6240">
<p data-start="6242" data-end="6368"><strong data-start="6242" data-end="6298">Q20: What’s the best Singleton for high concurrency?</strong><br data-start="6298" data-end="6301">
👉 <strong data-start="6304" data-end="6317">Bill Pugh</strong> or <strong data-start="6321" data-end="6329">Enum</strong> — thread-safe, performant, and simple.</p>
<hr data-start="6370" data-end="6373">
<h2 data-start="6375" data-end="6422">✅ 6. ADVANCED / ARCHITECTURE-LEVEL QUESTIONS</h2>
<p data-start="6424" data-end="6586"><strong data-start="6424" data-end="6489">Q21: What are the risks of using Singleton for DB connection?</strong><br data-start="6489" data-end="6492">
👉 If DB is down, Singleton can hold <strong data-start="6529" data-end="6551">stale/broken state</strong>. Use <strong data-start="6557" data-end="6577">connection pools</strong> instead.</p>
<hr data-start="6588" data-end="6591">
<p data-start="6593" data-end="6681"><strong data-start="6593" data-end="6662">Q22: What other patterns can replace Singleton for global access?</strong><br data-start="6662" data-end="6665">
👉 Alternatives:</p>
<ul data-start="6682" data-end="6730">
<li data-start="6682" data-end="6708">
<p data-start="6684" data-end="6708"><strong data-start="6684" data-end="6708">Dependency Injection</strong></p>
</li>
<li data-start="6709" data-end="6730">
<p data-start="6711" data-end="6730"><strong data-start="6711" data-end="6730">Service Locator</strong></p>
</li>
</ul>
<hr data-start="6732" data-end="6735">
<p data-start="6737" data-end="6858"><strong data-start="6737" data-end="6779">Q23: Can Singleton cause memory leaks?</strong><br data-start="6779" data-end="6782">
👉 Yes — if it holds references to objects that should be garbage collected.</p>
<hr data-start="6860" data-end="6863">
<h2 data-start="6865" data-end="6891">✅ SUMMARY FOR LEAD ROLE</h2>
<p data-start="6893" data-end="6913">As a lead, focus on:</p>
<ul data-start="6915" data-end="7214">
<li data-start="6915" data-end="6944">
<p data-start="6917" data-end="6944">Thread safety &amp; concurrency</p>
</li>
<li data-start="6945" data-end="6983">
<p data-start="6947" data-end="6983">Design trade-offs (memory vs safety)</p>
</li>
<li data-start="6984" data-end="7013">
<p data-start="6986" data-end="7013">Distributed system behavior</p>
</li>
<li data-start="7014" data-end="7061">
<p data-start="7016" data-end="7061">Pitfalls (cloning, serialization, reflection)</p>
</li>
<li data-start="7062" data-end="7104">
<p data-start="7064" data-end="7104">Dependency injection vs manual Singleton</p>
</li>
<li data-start="7105" data-end="7156">
<p data-start="7107" data-end="7156">When not to use Singleton (stateless vs stateful)</p>
</li>
<li data-start="7157" data-end="7214">
<p data-start="7159" data-end="7214">Best implementation based on use-case (Bill Pugh, Enum)</p>
</li>
</ul>
</div>

---

# Builder Design Pattern — Senior/Lead Java Interview Prep

## ✅ 1. BASIC QUESTIONS

**Q1: What is the Builder Pattern?**  
👉 A creational pattern that lets you construct complex objects step by step, separating construction from representation.

---

**Q2: Why use Builder Pattern?**  
👉 To avoid telescoping constructors and make object creation readable and flexible, especially for objects with many optional parameters.

---

**Q3: Builder vs Factory?**  
| Feature | Builder | Factory |
|-----------------|-------------------------------|--------------------------|
| Purpose | Step-by-step construction | Object creation |
| Complexity | Handles complex construction | Handles object selection |
| Immutability | Often creates immutable object | Not required |

---

## ✅ 2. USAGE & PITFALLS

**Q4: When should you use Builder?**  
👉 When an object has many optional parameters or needs to be immutable.

---

**Q5: Common pitfalls with Builder?**

- Forgetting to make the target class immutable.
- Not validating required fields before build.

---

## ✅ 3. CODE EXAMPLE

```java
public class User {
    private final String name;
    private final String email;
    private final String phone;

    private User(Builder builder) {
        this.name = builder.name;
        this.email = builder.email;
        this.phone = builder.phone;
    }

    public static class Builder {
        private String name;
        private String email;
        private String phone;

        public Builder setName(String name) { this.name = name; return this; }
        public Builder setEmail(String email) { this.email = email; return this; }
        public Builder setPhone(String phone) { this.phone = phone; return this; }
        public User build() { return new User(this); }
    }
}
```

---

# Factory Design Pattern — Senior/Lead Java Interview Prep

## ✅ 1. BASIC QUESTIONS

**Q1: What is the Factory Pattern?**  
👉 A creational pattern that provides an interface for creating objects in a superclass, but allows subclasses to alter the type of objects that will be created.

---

**Q2: Why use Factory Pattern?**  
👉 To decouple object creation from usage, and to manage and encapsulate the instantiation logic.

---

**Q3: Factory vs Abstract Factory?**  
| Feature | Factory Method | Abstract Factory |
|-----------------|-------------------------|-------------------------|
| Products | One product | Families of products |
| Extensibility | Subclass for new type | Subclass for new family |

---

## ✅ 2. USAGE & PITFALLS

**Q4: When should you use Factory?**  
👉 When you need to delegate the instantiation logic to subclasses or when object creation is complex.

---

**Q5: Common pitfalls with Factory?**

- Too many subclasses for each product.
- Overcomplicating simple object creation.

---

## ✅ 3. CODE EXAMPLE

```java
interface Shape {
    void draw();
}

class Circle implements Shape {
    public void draw() { System.out.println("Circle"); }
}

class Square implements Shape {
    public void draw() { System.out.println("Square"); }
}

class ShapeFactory {
    public static Shape getShape(String type) {
        if ("circle".equalsIgnoreCase(type)) return new Circle();
        if ("square".equalsIgnoreCase(type)) return new Square();
        throw new IllegalArgumentException("Unknown type");
    }
}
```

---

# Adapter Design Pattern — Senior/Lead Java Interview Prep

## ✅ 1. BASIC QUESTIONS

**Q1: What is the Adapter Pattern?**  
👉 A structural pattern that allows objects with incompatible interfaces to work together by wrapping an existing class with a new interface.

---

**Q2: Why use Adapter Pattern?**  
👉 To integrate classes that couldn’t otherwise work together due to incompatible interfaces.

---

**Q3: Adapter vs Decorator?**  
| Feature | Adapter | Decorator |
|-----------------|---------------------------|--------------------------|
| Purpose | Interface compatibility | Add responsibilities |
| Focus | Conversion | Extension |

---

## ✅ 2. USAGE & PITFALLS

**Q4: When should you use Adapter?**  
👉 When you want to use an existing class, but its interface does not match what you need.

---

**Q5: Common pitfalls with Adapter?**

- Overusing adapters can make code harder to understand.
- Adapter can hide incompatible design.

---

## ✅ 3. CODE EXAMPLE

```java
// Target interface
interface MediaPlayer {
    void play(String filename);
}

// Adaptee
class VLCPlayer {
    public void playVLC(String filename) {
        System.out.println("Playing VLC: " + filename);
    }
}

// Adapter
class VLCAdapter implements MediaPlayer {
    private VLCPlayer vlcPlayer = new VLCPlayer();
    public void play(String filename) {
        vlcPlayer.playVLC(filename);
    }
}
```

---
