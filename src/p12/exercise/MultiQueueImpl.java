package p12.exercise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class MultiQueueImpl<T, Q> implements MultiQueue<T, Q>{
    private Map<Q, Queue<T>> map = new HashMap<>();

    @Override
    public Set<Q> availableQueues() {
        return map.keySet();
    }

    @Override
    public void openNewQueue(Q queue) {
        if(map.get(queue) != null) {
            throw new IllegalArgumentException();
        }

        map.put(queue, new LinkedList<T>());
    }

    @Override
    public boolean isQueueEmpty(Q queue) {
        Queue<T> elements = map.get(queue);

        if(elements == null) {
            throw new IllegalArgumentException();
        }

        return elements.isEmpty();
    }

    @Override
    public void enqueue(T elem, Q queue) {
        Queue<T> elements = map.get(queue);

        if(map.get(queue) == null) {
            throw new IllegalArgumentException();
        }

        elements.add(elem);
    }

    @Override
    public T dequeue(Q queue) {
        Queue<T> elements = map.get(queue);

        if(elements == null) {
            throw new IllegalArgumentException();
        }

        return elements.poll();
    }

    @Override
    public Map<Q, T> dequeueOneFromAllQueues() {
        Map<Q, T> dequeuedElements = new HashMap<>();
        
        for(Q q : map.keySet()) {
            Queue<T> elements = map.get(q);

            if(!elements.isEmpty()) {
                dequeuedElements.put(q, elements.poll());
            }
            else {
                dequeuedElements.put(q, null);
            }
        }

        return dequeuedElements;
    }

    @Override
    public Set<T> allEnqueuedElements() {
        Set<T> queuedElements = new HashSet<>();

        for(Q q : map.keySet()) {
            queuedElements.addAll(map.get(q));
        }

        return queuedElements;
    }

    @Override
    public List<T> dequeueAllFromQueue(Q queue) {
        if(map.get(queue) == null) {
            throw new IllegalArgumentException();
        }

        List<T> dequeudElements = new ArrayList<>(map.get(queue));

        map.get(queue).clear();

        return dequeudElements;
    }

    @Override
    public void closeQueueAndReallocate(Q queue) {
        if(map.get(queue) == null) {
            throw new IllegalArgumentException();
        }

        if(map.keySet().size() < 2) {
            throw new IllegalStateException();
        }

        List<T> elements = new ArrayList<>(map.get(queue));
        map.remove(queue);

        Map.Entry<Q, Queue<T>> entry = map.entrySet().iterator().next();
        map.get(entry.getKey()).addAll(elements);
    }

}
