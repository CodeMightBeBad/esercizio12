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
        //  Returns the keyset from map
        return map.keySet();
    }

    @Override
    public void openNewQueue(Q queue) {
        //  Checks if the key already exists
        if(map.get(queue) != null) {
            throw new IllegalArgumentException();
        }

        //  Creates the new queue
        map.put(queue, new LinkedList<T>());
    }

    @Override
    public boolean isQueueEmpty(Q queue) {
        //  Gets the elements in the specified queue
        Queue<T> elements = map.get(queue);

        //  Checks if the queue exists
        if(elements == null) {
            throw new IllegalArgumentException();
        }

        //  Returns the value of isEmpty() to check if the queue is empty
        return elements.isEmpty();
    }

    @Override
    public void enqueue(T elem, Q queue) {
        //  Gets the elements in the speicified queue
        Queue<T> elements = map.get(queue);

        //  Checks if the queue exists
        if(map.get(queue) == null) {
            throw new IllegalArgumentException();
        }

        //  Adds the element to the queue
        elements.add(elem);
    }

    @Override
    public T dequeue(Q queue) {
        //  Gets the elements in the speicified queue
        Queue<T> elements = map.get(queue);

        //  Checks if the queue exists
        if(elements == null) {
            throw new IllegalArgumentException();
        }

        //  Returns the next element
        return elements.poll();
    }

    @Override
    public Map<Q, T> dequeueOneFromAllQueues() {
        //  Initializes the variables
        Queue<T> elements;
        Map<Q, T> dequeuedElements = new HashMap<>();
        
        //  Goes through all the queues contianed in map
        for(Q q : map.keySet()) {
            //  Gets the set of elements from the specified queue
            elements = map.get(q);

            //  Checks if the queue is empty, if not then polls the elements and adds them to the dequeuedElements map
            if(!elements.isEmpty()) {
                dequeuedElements.put(q, elements.poll());
            }
            else {
                dequeuedElements.put(q, null);
            }
        }

        //  Returns the resulting map of elements
        return dequeuedElements;
    }

    @Override
    public Set<T> allEnqueuedElements() {
        //  Creates the set that will contain all the elements
        Set<T> queuedElements = new HashSet<>();

        //  Goes through all the queues and adds their elements to the final set
        for(Q q : map.keySet()) {
            queuedElements.addAll(map.get(q));
        }

        //  Returns the elements set
        return queuedElements;
    }

    @Override
    public List<T> dequeueAllFromQueue(Q queue) {
        //  Checks if the queue exists
        if(map.get(queue) == null) {
            throw new IllegalArgumentException();
        }

        //  Creates the collection of removed elements and adds them
        List<T> dequeudElements = new ArrayList<>(map.get(queue));

        //  Clears the queue
        map.get(queue).clear();

        //  Returns the removed elements
        return dequeudElements;
    }

    @Override
    public void closeQueueAndReallocate(Q queue) {
        //  Checks if the queue exists
        if(map.get(queue) == null) {
            throw new IllegalArgumentException();
        }

        //  Checks if there is another queue to append the elements to
        if(map.keySet().size() < 2) {
            throw new IllegalStateException();
        }

        //  Gets the elements from the queue and saves them and deletes the queue
        List<T> elements = new ArrayList<>(map.get(queue));
        map.remove(queue);

        //  Gets the first entry key value and appends all the elements to it
        Map.Entry<Q, Queue<T>> entry = map.entrySet().iterator().next();
        map.get(entry.getKey()).addAll(elements);
    }

}
