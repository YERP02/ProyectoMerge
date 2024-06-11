/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectormimerge;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatClient extends JFrame {
    private JTextArea arregloDes;
    private JTextArea arregloAcom;
    private JTextArea contraTam;
    private JTextArea tiempoA;
    private JTextArea tiempoB;
    private ChatServer server;
    private int clientId;
    static int[] combinedArray = null;
    StringBuilder sb = new StringBuilder();
    ForkJoinSortTest fork;
    ExecuteService service;
    String contra= null;
    int conTam=0;

    public ChatClient(int clientId) {
        this.clientId = clientId;
        setupUI();
        connectToServer();
    }

    private void setupUI() {
        setTitle("Cliente RMI");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel label1 = new JLabel("Tamaño del arreglo:");
        label1.setBounds(20, 20, 200, 20);
        add(label1);

        JLabel label5 = new JLabel("Arreglo Desacomodado:");
        label5.setBounds(20, 60, 200, 20);
        add(label5);

        JLabel label2 = new JLabel("Arreglo Organizado:");
        label2.setBounds(20, 240, 200, 20);
        add(label2);

        JLabel label3 = new JLabel("Tiempo:");
        label3.setBounds(20, 420, 200, 20);
        add(label3);

        contraTam = new JTextArea("");
        contraTam.setBounds(220, 20, 100, 20);
        add(contraTam);

        arregloDes = new JTextArea("");
        arregloDes.setEditable(false);
        arregloDes.setLineWrap(true);
        arregloDes.setWrapStyleWord(true);
        JScrollPane scrollPane1 = new JScrollPane(arregloDes);
        scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane1.setBounds(20, 80, 560, 100);
        add(scrollPane1);

        arregloAcom = new JTextArea("");
        arregloAcom.setEditable(false);
        arregloAcom.setLineWrap(true);
        arregloAcom.setWrapStyleWord(true);
        JScrollPane scrollPane12 = new JScrollPane(arregloAcom);
        scrollPane12.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane12.setBounds(20, 260, 560, 100);
        add(scrollPane12);

        tiempoA = new JTextArea("");
        tiempoA.setEditable(false);
        tiempoA.setBounds(20, 440, 150, 20);
        add(tiempoA);

        tiempoB = new JTextArea("");
        tiempoB.setEditable(false);
        tiempoB.setBounds(200, 440, 150, 20);
        add(tiempoB);
        
        JTextArea tiempoC = new JTextArea("");
        tiempoC.setEditable(false);
        tiempoC.setBounds(380, 440, 150, 20);
        add(tiempoC);

        JButton agregarButton = new JButton("Agregar");
        agregarButton.setBounds(350, 20, 100, 20);
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contra = contraTam.getText();
                conTam = Integer.parseInt(contra);
                requestNumbers(conTam);
                
                try {
                    
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingresa un número válido.");
                }
            }
        });
        add(agregarButton);

        JButton secuencial = new JButton("Secuencial");
        secuencial.setBounds(20, 480, 100, 30);
        secuencial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    requestNumbers(conTam);
                    if (combinedArray != null) {
                        MergeSort mergeSort = new MergeSort();
                        mergeSort.main(combinedArray);
                        sb.setLength(0);
                        for (int num : combinedArray) {
                            sb.append(num).append(" ");
                        }
                        arregloAcom.setText(sb.toString());
                        tiempoA.setText(mergeSort.time() + " milisegundos");
                    }
                } catch (Exception es) {
                    es.printStackTrace();
                }
            }
        });
        add(secuencial);

        JButton forkButton = new JButton("ForkJoin");
        forkButton.setBounds(200, 480, 100, 30);
        forkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                requestNumbers(conTam);
                if (combinedArray != null) {
                    double prom = 0;
                    arregloAcom.setText("");
                    fork = new ForkJoinSortTest(combinedArray);
                    arregloAcom.setText(fork.getSortedArrayString());
                    prom = fork.Time();
                    arregloAcom.setText(fork.getSortedArrayString());
                    prom = prom + fork.Time();
                    arregloAcom.setText(fork.getSortedArrayString());
                    prom = prom + fork.Time();
                    prom = prom / 3;
                    tiempoB.setText(prom + " milisegundos");
                }
            }
        });
        add(forkButton);
        
        JButton Execute = new JButton("ExecuteService");
        Execute.setBounds(380, 480, 150, 30);
        Execute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                requestNumbers(conTam);
                if (combinedArray != null) {
                    arregloAcom.setText("");
                    //tiempoA.setText("");
                    //service.Service(num2);
                    service = new ExecuteService(combinedArray);
                    int aux4[] = service.Service(combinedArray);
                    for(int i = 0; i < aux4.length; i++){
                        arregloAcom.append(aux4[i] + " ");
                    }
                    //arregloAcom.setText(service.getSortedArreglo());
                    tiempoC.setText(service.time() + " milisegundos");
                }
               
                
            }
        });
        add(Execute);

        JButton borrar = new JButton("Borrar");
        borrar.setBounds(20, 380, 100, 30);
        borrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                arregloAcom.setText("");
                contraTam.setText("");
                tiempoA.setText("");
                tiempoB.setText("");
                arregloDes.setText("");
                combinedArray = null;
                sb.setLength(0);
                try {
                    server.resetArrays();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        add(borrar);
    }

    private void connectToServer() {
        try {
            Registry registry = LocateRegistry.getRegistry("25.38.135.113", 1099);
            server = (ChatServer) registry.lookup("ChatServer");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestNumbers(int count) {
        try {
            if (arregloDes.getText().isEmpty()) {
                server.requestNumbers(clientId, count);
                int[] clientArray = server.getClientArray(clientId);
                sb.setLength(0); // Clear the StringBuilder
                for (int num : clientArray) {
                    sb.append(num).append(" ");
                }
                arregloDes.setText(sb.toString());
            }

            if (server.readyToSend()) {
                combinedArray = server.getCombinedArray();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChatClient client1 = new ChatClient(0);
            client1.setVisible(true);

            /*ChatClient client2 = new ChatClient(1);
            client2.setVisible(true);*/
        });
    }
}
