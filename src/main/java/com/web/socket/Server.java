package com.web.socket;

import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;

@Getter
@Setter
public class Server extends Thread {
    ServerSocket serverSocket; // Определяется переменная serverSocket
    public Server() {
        try {
            /*
             * Создание объекта ServerSocket, который принимает запросы
             * соединения от клиентов через порт 1001
             */
            serverSocket = new ServerSocket(1001);
            System.out.println(serverSocket);
        } catch (IOException e) {
            fail(e, "Could not start server.");
        }
        System.out.println("Сервер запущен . . .");
        /* Стартует поток */
        this.start();
    }
    public static void fail(Exception e, String str) {
        System.out.println(str + "." + e);
    }

    private String EX12(String[] data){
        String result = "";
        for (String num: data) {
            if (num.contains("-")){
                result += " - ";
            } else {
                result += " + ";
            }
        }
        return  result;
    }

    private String EX15(String[] mass, String[] mass1){
        String result = "";
        double sum = 0;
        for (String num:mass) {
            double temp = Double.parseDouble(num);
            sum += temp;
        }
        result += (sum/mass.length);
        for (String num:mass1) {
            double temp = Double.parseDouble(num);
            sum += temp;
        }
        result += " "+sum/mass1.length;
        return result;
    }

    double gcdByEuclidesAlgorithm(double n1, double n2) {
        if (n2 == 0) {
            return n1;
        }
        return gcdByEuclidesAlgorithm(n2, n1 % n2);
    }

    private String EX18(String[] nums){
        double a = Double.parseDouble(nums[0]);
        double b = Double.parseDouble(nums[1]);
        double c = Double.parseDouble(nums[2]);
        double d = Double.parseDouble(nums[3]);
        double e = Double.parseDouble(nums[4]);
        double f = Double.parseDouble(nums[5]);
        double g = Double.parseDouble(nums[6]);
        double h = Double.parseDouble(nums[7]);
        return ""+gcdByEuclidesAlgorithm(a, b)+" "+gcdByEuclidesAlgorithm(c, d)+" "+gcdByEuclidesAlgorithm(e, f)+" "+gcdByEuclidesAlgorithm(g, h);
    }

    private double findCathet(double leg, double cathet){
        return Math.sqrt(Math.pow(leg, 2) - Math.pow(cathet, 2));
    }

    private String EX21(String[] nums){
        double leg = Double.parseDouble(nums[0]);
        double cathet = Double.parseDouble(nums[1]);
        return ""+findCathet(leg, cathet);
    }

    private double G(double a, double b){
        return ((2*a)+(b*b))/((a*b*2)+(b*5));
    }

    private double Y(double t, double s){
        return (G(12, s)+G(t, s)-G((2*s-1), (s*t)));
    }

    private String EX24(String[] nums){
        double s = Double.parseDouble(nums[0]);
        double t = Double.parseDouble(nums[1]);
        return ""+ Y(s, t);
    }

    public void run() {
        try {
            while (true) {
                /* Принимаются запросы от клиентов */
                Socket client = serverSocket.accept();
                /*
                * Создается объект соединение, чтобы управлять
                взаимодействием
                * клиента с сервером
                */
                //Connection con = new Connection(client);

                BufferedReader fromClient = new BufferedReader(new
                        InputStreamReader(
                        client.getInputStream()));
                PrintStream toClient = new
                        PrintStream(client.getOutputStream());
                toClient.println("Выбирите задачу: 1) 12; 2) 15; 3) 18; 4) 21; 5) 24.");
                String newLine = fromClient.readLine();
                if (newLine.equals("1")){
                    toClient.println("Выбрана задача №1. Введите три числа:");
                    String s = fromClient.readLine();
                    String[] nums = s.split(" ");
                    if(nums.length == 8) {
                        toClient.println("Ответ: " + EX12(nums));
                    }
                    else{
                        toClient.println("Не правильное количество чисел!");
                    }
                }
                if(newLine.equals("2")){
                    toClient.println("Выбрана задача №2. Введите первый массив:");
                    String s = fromClient.readLine();
                    String[] nums = s.split(" ");
                    toClient.println("Введите второй массив:");
                    String s1 = fromClient.readLine();
                    String[] nums1 = s1.split(" ");
                    toClient.println("Ответ: "+EX15(nums, nums1));
                }
                if(newLine.equals("3")){
                    toClient.println("Выбрана задача №3. Введите четыре пары чисел:");
                    String s = fromClient.readLine();
                    String[] nums = s.split(" ");
                    if(nums.length == 8){
                        toClient.println("Ответ: "+EX18(nums));
                    }
                    else{
                        toClient.println("Не правильное количество чисел!");
                    }
                }
                if(newLine.equals("4")){
                    toClient.println("Выбрана задача №4. Введите гипотенузу и катет:");
                    String s = fromClient.readLine();
                    String[] nums = s.split(" ");
                    toClient.println("Ответ: "+EX21(nums));
                }
                if(newLine.equals("5")){
                    toClient.println("Выбрана задача №5. Введите два числа:");
                    String s = fromClient.readLine();
                    String[] nums = s.split(" ");
                    if(nums.length == 2) {
                        toClient.println("Ответ: " + EX24(nums));
                    }
                    else{
                        toClient.println("Не правильное количество чисел!");
                    }
                }
            }
        } catch (IOException e) {
            fail(e, "Not listening");
        }
    }
    public static void main(String args[]) {
        /* Запускается сервер */
        new Server();
    }
}