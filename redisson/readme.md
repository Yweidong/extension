redisson上锁

#链接文档
https://blog.csdn.net/turbo_zone/article/details/83422215


Redisson是一个在Redis的基础上实现的Java驻内存数据网格（In-Memory Data Grid）。
它不仅提供了一系列的分布式的Java常用对象，还提供了许多分布式服务。其中包括(BitSet, Set, Multimap, SortedSet, Map, List, Queue, BlockingQueue, Deque, BlockingDeque, Semaphore, Lock, AtomicLong, CountDownLatch, Publish / Subscribe, Bloom filter, Remote service, Spring cache, Executor service, Live Object service, Scheduler service) Redisson提供了使用Redis的最简单和最便捷的方法。

Redisson的宗旨是促进使用者对Redis的关注分离（Separation of Concern），从而让使用者能够将精力更集中地放在处理业务逻辑上。

#可重入锁
###手动加/释放锁
RLock lock = redisson.getLock("anyLock");

lock.lock();

...

&nbsp;lock.unlock();

### 自动释放锁
// 加锁以后10秒钟自动解锁

// 无需调用unlock方法手动解锁

lock.lock(10, TimeUnit.SECONDS);

// 尝试加锁，最多等待100秒，上锁以后10秒自动解锁

boolean res = lock.tryLock(100, 10, TimeUnit.SECONDS);
...
lock.unlock();

#公平锁
RLock fairLock = redisson.getFairLock("anyLock");

// 最常见的使用方法

fairLock.lock();

...

fairLock.unlock();

### 自动释放锁
// 10秒钟以后自动解锁

// 无需调用unlock方法手动解锁

fairLock.lock(10, TimeUnit.SECONDS);

// 尝试加锁，最多等待100秒，上锁以后10秒自动解锁

boolean res = fairLock.tryLock(100, 10, TimeUnit.SECONDS);

...

fairLock.unlock();

#其他科参见文档。。。


