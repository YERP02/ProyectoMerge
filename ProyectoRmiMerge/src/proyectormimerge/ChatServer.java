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

public interface ChatServer extends Remote {
    void requestNumbers(int clientId, int count) throws RemoteException;
    int[] getClientArray(int clientId) throws RemoteException;
    int[] getCombinedArray() throws RemoteException;
    boolean readyToSend() throws RemoteException;
    void resetArrays() throws RemoteException;
}


