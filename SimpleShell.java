/**
 * Created by a on 2017/4/9.
 */
//???1.??crtl+c?? 2.???part1?part2?????
//import sun.security.mscapi.KeyStore;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.security.auth.kerberos.KerberosTicket;
import javax.swing.JFrame;

import static java.lang.System.exit;

/*private class Listener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("??")) {
            System.out.println("??????");
            exit(0);
        }
    }*/


public class SimpleShell {
    public static void main(String[] args) throws java.io.IOException {
        String commandLine;

        //????2
        String MyPath = System.getProperty("user.dir");

        //????3
        String history[] = new String[1000];//??????,???1000?
        int histAmt = 0; //??????
        int num, i;//???????

        //KeyEvent e2=null;

        /*MenuBar menubar = new MenuBar();
        MenuShortcut ctMsc = new MenuShortcut(KeyEvent.VK_C, false);//??Ctrl+C ????
        Menu menu = new Menu("??");
        MenuItem ct = new MenuItem("??", ctMsc);
        ActionEvent e2 = null;
        Listener listener = new Listener();

        menubar.add(menu);
        menu.add(ct);
        ct.addActionListener(new Listener());*/

        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

        // we break out with <control><C>
        try {
            while (true) {
                // read what the user entered
                System.out.print("jsh>");

                //listener.actionPerformed(e2);

                commandLine = console.readLine();

                // if the user entered a return, just loop again
                if (commandLine.equals(""))
                    continue;

                //????commandLine?????
                history[histAmt] = commandLine;
                histAmt++;

                //The steps are:
                //(1) parse the input to obtain the command and any parameters
                if (commandLine.equals("history")) { //????history
                    for (i = 0; i < histAmt - 1; ++i)
                        System.out.println(i + " " + history[i]);
                    continue;
                }
                if (commandLine.equals("!!")) {
                    num = histAmt - 2;//??????????????
                    if (num > histAmt - 2 || num < 0) {
                        System.out.println("not in range");
                        continue;
                    }
                    commandLine = history[num];
                }
                if (commandLine.charAt(0) == '!' && Character.isDigit(commandLine.charAt(1))) {//????????
                    String[] tmpNum = commandLine.split("!");//?????commandline
                    num = Integer.parseInt(tmpNum[1]);//???????????
                    if (num > histAmt - 2 || num < 0) {
                        System.out.println("not in range");
                        continue;
                    }
                    //??????
                    commandLine = history[num];
                }//????


                //????????????arrayList???parms?
                ArrayList<String> parms = new ArrayList<String>();
                String[] lineSplit = commandLine.split(" ");
                int size = lineSplit.length;
                parms.add("cmd");
                parms.add("/c");//??cmd????
                for (i = 2; i < size+2; i++) {
                    parms.add(lineSplit[i-2]);
                }

                if(lineSplit[0].equalsIgnoreCase("cd")){
                    if (lineSplit.length == 1) { // ??cd?????
                        MyPath = System.getProperty("user.dir");//????
                        System.out.println(MyPath);
                    }else {
                        String str = lineSplit[1];
                        String MyPath1;
                        MyPath1 = MyPath + '\\' + str;//????
                        File file = new File(MyPath1);
                        if (file.exists()){//????????
                            MyPath = MyPath1;
                            System.out.println(MyPath);
                        } else{
                            System.out.println("File not exist!");
                        }
                    }
                }

                else {//????cd??
                    // ???????
                    //String[] words = commandLine.split(" ");
                    //List<String> l = Arrays.asList(words);
                    //(2) create a ProcessBuilder object
                    ProcessBuilder pb = new ProcessBuilder(parms);
                    pb.directory(new File(MyPath));
                    Process process = pb.start();
                    try {
                        //(3) start the process
                        //Process process = pb.start();
                        //(4) obtain the output stream
                        //System.out.println(parms);
                        InputStream is = process.getInputStream();
                        InputStreamReader isr = new InputStreamReader(is);
                        BufferedReader br = new BufferedReader(isr);
                        String line;
                        while ((line = br.readLine()) != null)
                            //(5) output the contents returned by the command
                            System.out.println(line);
                        br.close();
                    }catch(java.io.IOException e){
                        System.out.println("????");
                    }
                }

            }
        } catch(java.io.IOException e){
            System.out.println("????");
        }
    }


}
