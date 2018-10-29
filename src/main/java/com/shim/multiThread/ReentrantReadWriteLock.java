package com.shim.multiThread;

import com.sun.corba.se.impl.orbutil.concurrent.Sync;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * @类描述：
 * @创建人：xn064961
 * @创建时间：2018/10/16 14:02
 * @版权：Copyright (c) 深圳市牛鼎丰科技有限公司-版权所有.
 */
public class ReentrantReadWriteLock implements ReadWriteLock, Serializable {

    //ReadLock是ReentrantReadWriteLock中的静态内部类，它是读取锁的实现
    private final ReentrantReadWriteLock.ReadLock readerLock;

    //WriteLock是ReentrantReadWriteLock中的静态内部类，它是写入锁的实现
    private final ReentrantReadWriteLock.WriteLock writerLock;

    //Sync是ReentrantReadWriteLock中的静态内部类，它继承了AQS
    //它是读写锁实现的重点，后面深入分析
    final Sync sync;

    //默认使用非公平策略创建对象
    public ReentrantReadWriteLock() {
        this(false);
    }

    //根据指定策略参数创建对象
    public ReentrantReadWriteLock(boolean fair) {
        //FairSync和NonfairSync都继承自Sync，它们主要提供了对读写是否需要被阻塞的检查方法
        sync = fair ? new FairSync() : new NonfairSync();
        readerLock = new ReadLock(this);
        writerLock = new WriteLock(this);
    }

    /**
     * Returns the lock used for reading.
     *
     * @return the lock used for reading.
     */
    @Override
    public ReentrantReadWriteLock.ReadLock readLock() {
        return readerLock;
    }

    /**
     * Returns the lock used for writing.
     *
     * @return the lock used for writing.
     */
    @Override
    public ReentrantReadWriteLock.WriteLock writeLock() {
        return writerLock;
    }


    abstract static class Sync extends AbstractQueuedSynchronizer {
        //常量值
        static final int SHARED_SHIFT   = 16;

        //左移16位后，二进制值是10000000000000000，十进制值是65536
        static final int SHARED_UNIT    = (1 << SHARED_SHIFT);

        //左移16位后再减一，十进制值是65535
        //这个常量值用于标识最多支持65535个递归写入锁或65535个读取锁
        static final int MAX_COUNT      = (1 << SHARED_SHIFT) - 1;

        //左移16位后再减一，二进制值是1111111111111111
        static final int EXCLUSIVE_MASK = (1 << SHARED_SHIFT) - 1;

        //用于计算持有读取锁的线程数
        static int sharedCount(int c) {
            //无符号右移动16位
            //如果c是32位，无符号右移后，得到是高16位的值
            return c >>> SHARED_SHIFT;
        }

        //用于计算写入锁的重入次数
        static int exclusiveCount(int c) {
            //如果c是32位，和1111111111111111做&运算，得到的低16位的值
            return c & EXCLUSIVE_MASK;
        }

        //用于每个线程持有读取锁的计数
        static final class HoldCounter {
            //每个线程持有读取锁的计数
            int count = 0;

            //当前持有读取锁的线程ID
            //这里使用线程ID而没有使用引用，避免垃圾收集器保留，导致无法回收
            final long tid = Thread.currentThread().getId();
        }

        //通过ThreadLocal维护每个线程的HoldCounter
        static final class ThreadLocalHoldCounter
                extends ThreadLocal<HoldCounter> {
            //这里重写了ThreadLocal的initialValue方法
            @Override
            public HoldCounter initialValue() {
                return new HoldCounter();
            }
        }

        //当前线程持有的可重入读取锁的数量，仅在构造方法和readObject方法中被初始化
        //当持有锁的数量为0时，移除此对象
        private transient ThreadLocalHoldCounter readHolds;

        //成功获取读取锁的最近一个线程的计数
        private transient HoldCounter cachedHoldCounter;

        //第一个获得读锁的线程
        private transient Thread firstReader = null;
        //第一个获得读锁的线程持有读取锁的次数
        private transient int firstReaderHoldCount;

        Sync() {
            //构建每个线程的HoldCounter
            readHolds = new ThreadLocalHoldCounter();
            setState(getState()); // ensures visibility of readHolds
        }
    }

    /**
     * Nonfair version of Sync
     */
    static final class NonfairSync extends ReentrantReadWriteLock.Sync {
        private static final long serialVersionUID = -8159625535654395037L;
        final boolean writerShouldBlock() {
            return false; // writers can always barge
        }
        final boolean readerShouldBlock() {
            /* As a heuristic to avoid indefinite writer starvation,
             * block if the thread that momentarily appears to be head
             * of queue, if one exists, is a waiting writer.  This is
             * only a probabilistic effect since a new reader will not
             * block if there is a waiting writer behind other enabled
             * readers that have not yet drained from the queue.
             */

//            return apparentlyFirstQueuedIsExclusive();
            return true;
        }


    }

    /**
     * Fair version of Sync
     */
    static final class FairSync extends ReentrantReadWriteLock.Sync {
        private static final long serialVersionUID = -2274990926593161451L;
        final boolean writerShouldBlock() {
            return hasQueuedPredecessors();
        }
        final boolean readerShouldBlock() {
            return hasQueuedPredecessors();
        }
    }

    public static class ReadLock implements Lock, java.io.Serializable {
        private final Sync sync;

        //通过ReentrantReadWriteLock对象构建ReadLock
        protected ReadLock(ReentrantReadWriteLock lock) {
            //在ReentrantReadWriteLock构造函数中会根据fair参数值选择FairSync或NonfairSync创建不同的对象
            //所以，这里赋值给sync的可能是FairSync类的对象，也可能是NonfairSync类的对象
            sync = lock.sync;
        }

        /**
         * Acquires the lock.
         * <p>If the lock is not available then the current thread becomes
         * disabled for thread scheduling purposes and lies dormant until the
         * lock has been acquired.
         * <p><b>Implementation Considerations</b>
         * <p>A {@code Lock} implementation may be able to detect erroneous use
         * of the lock, such as an invocation that would cause deadlock, and
         * may throw an (unchecked) exception in such circumstances.  The
         * circumstances and the exception type must be documented by that
         * {@code Lock} implementation.
         */
        @Override
        public void lock() {
            // 调用AQS的acquireShared方法
            sync.acquireShared(1);
        }

        /**
         * Acquires the lock unless the current thread is
         * {@linkplain Thread#interrupt interrupted}.
         * <p>Acquires the lock if it is available and returns immediately.
         * <p>If the lock is not available then the current thread becomes
         * disabled for thread scheduling purposes and lies dormant until
         * one of two things happens:
         * <ul>
         * <li>The lock is acquired by the current thread; or
         * <li>Some other thread {@linkplain Thread#interrupt interrupts} the
         * current thread, and interruption of lock acquisition is supported.
         * </ul>
         * <p>If the current thread:
         * <ul>
         * <li>has its interrupted status set on entry to this method; or
         * <li>is {@linkplain Thread#interrupt interrupted} while acquiring the
         * lock, and interruption of lock acquisition is supported,
         * </ul>
         * then {@link InterruptedException} is thrown and the current thread's
         * interrupted status is cleared.
         * <p><b>Implementation Considerations</b>
         * <p>The ability to interrupt a lock acquisition in some
         * implementations may not be possible, and if possible may be an
         * expensive operation.  The programmer should be aware that this
         * may be the case. An implementation should document when this is
         * the case.
         * <p>An implementation can favor responding to an interrupt over
         * normal method return.
         * <p>A {@code Lock} implementation may be able to detect
         * erroneous use of the lock, such as an invocation that would
         * cause deadlock, and may throw an (unchecked) exception in such
         * circumstances.  The circumstances and the exception type must
         * be documented by that {@code Lock} implementation.
         *
         * @throws InterruptedException if the current thread is
         *                              interrupted while acquiring the lock (and interruption
         *                              of lock acquisition is supported).
         */
        @Override
        public void lockInterruptibly() throws InterruptedException {

        }

        /**
         * Acquires the lock only if it is free at the time of invocation.
         * <p>Acquires the lock if it is available and returns immediately
         * with the value {@code true}.
         * If the lock is not available then this method will return
         * immediately with the value {@code false}.
         * <p>A typical usage idiom for this method would be:
         * <pre>
         *      Lock lock = ...;
         *      if (lock.tryLock()) {
         *          try {
         *              // manipulate protected state
         *          } finally {
         *              lock.unlock();
         *          }
         *      } else {
         *          // perform alternative actions
         *      }
         * </pre>
         * This usage ensures that the lock is unlocked if it was acquired, and
         * doesn't try to unlock if the lock was not acquired.
         *
         * @return {@code true} if the lock was acquired and
         * {@code false} otherwise
         */
        @Override
        public boolean tryLock() {
            return false;
        }

        /**
         * Acquires the lock if it is free within the given waiting time and the
         * current thread has not been {@linkplain Thread#interrupt interrupted}.
         * <p>If the lock is available this method returns immediately
         * with the value {@code true}.
         * If the lock is not available then
         * the current thread becomes disabled for thread scheduling
         * purposes and lies dormant until one of three things happens:
         * <ul>
         * <li>The lock is acquired by the current thread; or
         * <li>Some other thread {@linkplain Thread#interrupt interrupts} the
         * current thread, and interruption of lock acquisition is supported; or
         * <li>The specified waiting time elapses
         * </ul>
         * <p>If the lock is acquired then the value {@code true} is returned.
         * <p>If the current thread:
         * <ul>
         * <li>has its interrupted status set on entry to this method; or
         * <li>is {@linkplain Thread#interrupt interrupted} while acquiring
         * the lock, and interruption of lock acquisition is supported,
         * </ul>
         * then {@link InterruptedException} is thrown and the current thread's
         * interrupted status is cleared.
         * <p>If the specified waiting time elapses then the value {@code false}
         * is returned.
         * If the time is
         * less than or equal to zero, the method will not wait at all.
         * <p><b>Implementation Considerations</b>
         * <p>The ability to interrupt a lock acquisition in some implementations
         * may not be possible, and if possible may
         * be an expensive operation.
         * The programmer should be aware that this may be the case. An
         * implementation should document when this is the case.
         * <p>An implementation can favor responding to an interrupt over normal
         * method return, or reporting a timeout.
         * <p>A {@code Lock} implementation may be able to detect
         * erroneous use of the lock, such as an invocation that would cause
         * deadlock, and may throw an (unchecked) exception in such circumstances.
         * The circumstances and the exception type must be documented by that
         * {@code Lock} implementation.
         *
         * @param time the maximum time to wait for the lock
         * @param unit the time unit of the {@code time} argument
         * @return {@code true} if the lock was acquired and {@code false}
         * if the waiting time elapsed before the lock was acquired
         * @throws InterruptedException if the current thread is interrupted
         *                              while acquiring the lock (and interruption of lock
         *                              acquisition is supported)
         */
        @Override
        public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
            return false;
        }

        /**
         * Releases the lock.
         * <p><b>Implementation Considerations</b>
         * <p>A {@code Lock} implementation will usually impose
         * restrictions on which thread can release a lock (typically only the
         * holder of the lock can release it) and may throw
         * an (unchecked) exception if the restriction is violated.
         * Any restrictions and the exception
         * type must be documented by that {@code Lock} implementation.
         */
        @Override
        public void unlock() {

        }

        /**
         * Returns a new {@link Condition} instance that is bound to this
         * {@code Lock} instance.
         * <p>Before waiting on the condition the lock must be held by the
         * current thread.
         * A call to {@link Condition#await()} will atomically release the lock
         * before waiting and re-acquire the lock before the wait returns.
         * <p><b>Implementation Considerations</b>
         * <p>The exact operation of the {@link Condition} instance depends on
         * the {@code Lock} implementation and must be documented by that
         * implementation.
         *
         * @return A new {@link Condition} instance for this {@code Lock} instance
         * @throws UnsupportedOperationException if this {@code Lock}
         *                                       implementation does not support conditions
         */
        @Override
        public Condition newCondition() {
            return null;
        }
    }


    public static class WriteLock implements Lock, java.io.Serializable {
        private final Sync sync;

        //通过ReentrantReadWriteLock对象构建
        protected WriteLock(ReentrantReadWriteLock lock) {
            //在ReentrantReadWriteLock构造函数中会根据fair参数值选择FairSync或NonfairSync创建不同的对象
            //所以，这里赋值给sync的可能是FairSync类的对象，也可能是NonfairSync类的对象
            sync = lock.sync;
        }

        /**
         * Acquires the lock.
         * <p>If the lock is not available then the current thread becomes
         * disabled for thread scheduling purposes and lies dormant until the
         * lock has been acquired.
         * <p><b>Implementation Considerations</b>
         * <p>A {@code Lock} implementation may be able to detect erroneous use
         * of the lock, such as an invocation that would cause deadlock, and
         * may throw an (unchecked) exception in such circumstances.  The
         * circumstances and the exception type must be documented by that
         * {@code Lock} implementation.
         */
        @Override public void lock() {

        }

        /**
         * Acquires the lock unless the current thread is
         * {@linkplain Thread#interrupt interrupted}.
         * <p>Acquires the lock if it is available and returns immediately.
         * <p>If the lock is not available then the current thread becomes
         * disabled for thread scheduling purposes and lies dormant until
         * one of two things happens:
         * <ul>
         * <li>The lock is acquired by the current thread; or
         * <li>Some other thread {@linkplain Thread#interrupt interrupts} the
         * current thread, and interruption of lock acquisition is supported.
         * </ul>
         * <p>If the current thread:
         * <ul>
         * <li>has its interrupted status set on entry to this method; or
         * <li>is {@linkplain Thread#interrupt interrupted} while acquiring the
         * lock, and interruption of lock acquisition is supported,
         * </ul>
         * then {@link InterruptedException} is thrown and the current thread's
         * interrupted status is cleared.
         * <p><b>Implementation Considerations</b>
         * <p>The ability to interrupt a lock acquisition in some
         * implementations may not be possible, and if possible may be an
         * expensive operation.  The programmer should be aware that this
         * may be the case. An implementation should document when this is
         * the case.
         * <p>An implementation can favor responding to an interrupt over
         * normal method return.
         * <p>A {@code Lock} implementation may be able to detect
         * erroneous use of the lock, such as an invocation that would
         * cause deadlock, and may throw an (unchecked) exception in such
         * circumstances.  The circumstances and the exception type must
         * be documented by that {@code Lock} implementation.
         *
         * @throws InterruptedException if the current thread is
         *                              interrupted while acquiring the lock (and interruption
         *                              of lock acquisition is supported).
         */
        @Override public void lockInterruptibly() throws InterruptedException {

        }

        /**
         * Acquires the lock only if it is free at the time of invocation.
         * <p>Acquires the lock if it is available and returns immediately
         * with the value {@code true}.
         * If the lock is not available then this method will return
         * immediately with the value {@code false}.
         * <p>A typical usage idiom for this method would be:
         * <pre>
         *      Lock lock = ...;
         *      if (lock.tryLock()) {
         *          try {
         *              // manipulate protected state
         *          } finally {
         *              lock.unlock();
         *          }
         *      } else {
         *          // perform alternative actions
         *      }
         * </pre>
         * This usage ensures that the lock is unlocked if it was acquired, and
         * doesn't try to unlock if the lock was not acquired.
         *
         * @return {@code true} if the lock was acquired and
         * {@code false} otherwise
         */
        @Override public boolean tryLock() {
            return false;
        }

        /**
         * Acquires the lock if it is free within the given waiting time and the
         * current thread has not been {@linkplain Thread#interrupt interrupted}.
         * <p>If the lock is available this method returns immediately
         * with the value {@code true}.
         * If the lock is not available then
         * the current thread becomes disabled for thread scheduling
         * purposes and lies dormant until one of three things happens:
         * <ul>
         * <li>The lock is acquired by the current thread; or
         * <li>Some other thread {@linkplain Thread#interrupt interrupts} the
         * current thread, and interruption of lock acquisition is supported; or
         * <li>The specified waiting time elapses
         * </ul>
         * <p>If the lock is acquired then the value {@code true} is returned.
         * <p>If the current thread:
         * <ul>
         * <li>has its interrupted status set on entry to this method; or
         * <li>is {@linkplain Thread#interrupt interrupted} while acquiring
         * the lock, and interruption of lock acquisition is supported,
         * </ul>
         * then {@link InterruptedException} is thrown and the current thread's
         * interrupted status is cleared.
         * <p>If the specified waiting time elapses then the value {@code false}
         * is returned.
         * If the time is
         * less than or equal to zero, the method will not wait at all.
         * <p><b>Implementation Considerations</b>
         * <p>The ability to interrupt a lock acquisition in some implementations
         * may not be possible, and if possible may
         * be an expensive operation.
         * The programmer should be aware that this may be the case. An
         * implementation should document when this is the case.
         * <p>An implementation can favor responding to an interrupt over normal
         * method return, or reporting a timeout.
         * <p>A {@code Lock} implementation may be able to detect
         * erroneous use of the lock, such as an invocation that would cause
         * deadlock, and may throw an (unchecked) exception in such circumstances.
         * The circumstances and the exception type must be documented by that
         * {@code Lock} implementation.
         *
         * @param time the maximum time to wait for the lock
         * @param unit the time unit of the {@code time} argument
         * @return {@code true} if the lock was acquired and {@code false}
         * if the waiting time elapsed before the lock was acquired
         * @throws InterruptedException if the current thread is interrupted
         *                              while acquiring the lock (and interruption of lock
         *                              acquisition is supported)
         */
        @Override public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
            return false;
        }

        /**
         * Releases the lock.
         * <p><b>Implementation Considerations</b>
         * <p>A {@code Lock} implementation will usually impose
         * restrictions on which thread can release a lock (typically only the
         * holder of the lock can release it) and may throw
         * an (unchecked) exception if the restriction is violated.
         * Any restrictions and the exception
         * type must be documented by that {@code Lock} implementation.
         */
        @Override public void unlock() {

        }

        /**
         * Returns a new {@link Condition} instance that is bound to this
         * {@code Lock} instance.
         * <p>Before waiting on the condition the lock must be held by the
         * current thread.
         * A call to {@link Condition#await()} will atomically release the lock
         * before waiting and re-acquire the lock before the wait returns.
         * <p><b>Implementation Considerations</b>
         * <p>The exact operation of the {@link Condition} instance depends on
         * the {@code Lock} implementation and must be documented by that
         * implementation.
         *
         * @return A new {@link Condition} instance for this {@code Lock} instance
         * @throws UnsupportedOperationException if this {@code Lock}
         *                                       implementation does not support conditions
         */
        @Override public Condition newCondition() {
            return null;
        }
    }

}
