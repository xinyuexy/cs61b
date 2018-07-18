package synthesizer;

/**
 * Created by 11519 on 2018/7/17.
 */
public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T> {
    protected int fillCount;
    protected int capacity;

    @Override
    public int capacity() {
        return capacity;
    }

    @Override
    public int fillCount() {
        return fillCount;
    }

    @Override
    public boolean isEmpty() {
        return fillCount() == 0;
    }

    @Override
    public boolean isFull() {
        return fillCount() == capacity();
    }

    @Override
    public abstract T peek();

    @Override
    public abstract T dequeue();

    @Override
    public abstract void enqueue(T x);
}
