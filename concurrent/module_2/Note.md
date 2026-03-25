https://docs.oracle.com/javase/tutorial/essential/concurrency/locksync.html


Synchronization centers around a built-in mechanism called the intrinsic lock (or monitor). This lock serves two main purposes: it makes sure threads take turns using an object (exclusive access) and guarantees that everyone sees the most up-to-date data (visibility through happens-before rules).

Every single object has its own built-in lock. If a thread wants to safely read or change an object's data, it must first acquire (grab) this lock, do its job, and then release (let go of) the lock. The time during which the thread holds the lock is called ownership.

Because there is only one lock per object, if one thread owns it, no other thread can take it. Any other thread that tries will simply be blocked (forced to wait in line) until the lock is available.

When the current thread finally releases the lock, it creates a strict "happens-before" sequence. This rule guarantees that the action of releasing the lock completely finishes before the next thread can acquire it, ensuring the next thread always works with freshly updated data, not stale leftovers.

TODO: 
https://docs.oracle.com/javase/tutorial/essential/concurrency/locksync.html
https://docs.oracle.com/javase/tutorial/essential/concurrency/guardmeth.html
https://www.coursera.org/learn/concurrent-programming-in-java/lecture/TA8C0/1-3-unstructured-locks