import java.util.*;

public class Simulator {
    private int producers;
    private int consumers;
    private int requestPerConsumer;
    private int requestDelay;
    private int responseDelay;
    private double responseDelayVariance;
    private int timeout;
    private int threshold;
    private Map<Integer, Integer> requests; // key: id, value: time
    private int handlingStrategy;

    public Simulator(int producers, int consumers, int requestPerConsumer, int requestDelay, int responseDelay, double responseDelayVariance, int timeout, int threshold) {
        this.producers = producers;
        this.consumers = consumers;
        this.requestPerConsumer = requestPerConsumer;
        this.requestDelay = requestDelay;
        this.responseDelay = responseDelay;
        this.responseDelayVariance = responseDelayVariance;
        this.timeout = timeout;
        this.threshold = threshold;
    }

    private void addRequest(int requestId) {
        requests.put(requestId, 0);
        if (requests.size() < threshold) {
            if (handlingStrategy == 1) {
                System.out.println("Change strategy to queue...");
                handlingStrategy = 0; // queue
            }
        } else {
            if (handlingStrategy == 0) {
                System.out.println("Change strategy to stack...");
                handlingStrategy = 1; // stack
            }
        }
    }

    private int popRequest() {
        if (requests.size() == 0) return -1;
        int requestId = (Integer) requests.keySet().toArray()[0];
        int requestTime = requests.get(requestId);
        for (int i : requests.keySet()) {
            int tempTime = requests.get(i);
            if (handlingStrategy == 1) {
                if (tempTime < requestTime) {
                    requestId = i;
                    requestTime = tempTime;
                }
            } else {
                if (tempTime > requestTime) {
                    requestId = i;
                    requestTime = tempTime;
                }
            }
        }
        requests.remove(requestId);
        return requestId;
    }

    public void simulate() {
        simulate(2147483647);
    }

    public void simulate(int maxTime) {
        requests = new LinkedHashMap<>();
        int requestHandled = 0;
        int requestMissed = 0;
        int time = 0;
        int requestRemain = requestPerConsumer;
        int requestId = 1;
        Map<Integer, Integer> producerValidMap = new LinkedHashMap<>();
        for (int i = 1; i <= producers; i++) {
            producerValidMap.put(i, -1);
        }
        while (time < maxTime) {
            for (int i = 1; i <= producers; i++) { // responding
                int temp = producerValidMap.get(i) - 1;
                if (temp == 0) {
                    producerValidMap.put(i, -1);
                    System.out.printf("[%d ms] Producer %d responses\n", time, i);
                    requestHandled++;
                } else if (temp > 0) {
                    producerValidMap.put(i, temp);
                }
            }
            if (time % requestDelay == 0 && requestRemain > 0) {
                for (int i = 1; i <= consumers; i++) { // sending
                    System.out.printf("[%d ms] Consumer %d sends Request #%d\n", time, i, requestId);
                    addRequest(requestId);
                    requestId++;
                }
                requestRemain--;
            }
            for (int i = 1; i <= producers; i++) { // receiving
                if (requests.size() > 0 && producerValidMap.get(i) == -1) {
                    int receiveId = popRequest();
                    int responseTime = (int) (responseDelay * (1 - responseDelayVariance + new Random().nextDouble() * responseDelayVariance * 2));
                    producerValidMap.put(i, responseTime);
                    System.out.printf("[%d ms] Producer %d receives Request #%d, will consuming %d ms\n", time, i, receiveId, responseTime);
                }
            }
            if (requests.size() > 0) {
                Set<Integer> set = requests.keySet();
                Set<Integer> removeSet = new HashSet<>();
                for (Integer key : set) {
                    int temp = requests.get(key) + 1;
                    requests.put(key, temp);
                    if (temp >= timeout) {
                        removeSet.add(key);
                        requestMissed++;
                        System.out.printf("[%d ms] Request #%d timed out\n", time, key);
                    }
                }
                for (int key : removeSet) {
                    requests.remove(key);
                }
            }
            if (requestHandled + requestMissed >= requestPerConsumer * consumers) {
                System.out.printf("[%d ms] All requests finished. %d Handled, %d Timeout\n", time, requestHandled, requestMissed);
                break;
            }
            time++;
        }
    }
}
