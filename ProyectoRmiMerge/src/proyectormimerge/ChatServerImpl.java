/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectormimerge;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChatServerImpl extends UnicastRemoteObject implements ChatServer {
    private final List<int[]> clientArrays;
    private final List<Integer> clientRequests;

    protected ChatServerImpl() throws RemoteException {
        super();
        clientArrays = new ArrayList<>();
        clientRequests = new ArrayList<>();
    }

    @Override
    public synchronized void requestNumbers(int clientId, int count) throws RemoteException {
        ensureCapacity(clientId);

        Random random = new Random();
        int[] numbers = new int[count];
        for (int i = 0; i < count; i++) {
            numbers[i] = random.nextInt(1000);
        }
        clientArrays.set(clientId, numbers);
        clientRequests.set(clientId, count);

        notifyAll();
    }

    private synchronized void ensureCapacity(int clientId) {
        while (clientRequests.size() <= clientId) {
            clientRequests.add(0);
            clientArrays.add(new int[0]);
        }
    }

    @Override
    public synchronized int[] getClientArray(int clientId) throws RemoteException {
        ensureCapacity(clientId);
        return clientArrays.get(clientId);
    }

    @Override
    public synchronized int[] getCombinedArray() throws RemoteException {
        while (!readyToSend()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();  // Re-interrupt the current thread
                throw new RemoteException("Thread interrupted", e);
            }
        }

        int totalSize = clientRequests.stream().mapToInt(Integer::intValue).sum();
        int[] combinedArray = new int[totalSize];
        int index = 0;
        for (int[] clientArray : clientArrays) {
            System.arraycopy(clientArray, 0, combinedArray, index, clientArray.length);
            index += clientArray.length;
        }
        return combinedArray;
    }

    @Override
    public synchronized boolean readyToSend() throws RemoteException {
        return clientRequests.stream().allMatch(count -> count > 0);
    }

    @Override
    public synchronized void resetArrays() throws RemoteException {
        clientArrays.clear();
        clientRequests.clear();
    }

    public static void main(String[] args) {
        try {
            ChatServerImpl server = new ChatServerImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("ChatServer", server);
            System.out.println("Chat server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}