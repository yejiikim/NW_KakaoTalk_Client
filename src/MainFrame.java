import ImageResizer.ImageResizer;
import Object.ServerInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Scanner;

public class MainFrame extends JFrame {
    public static int totalUserNumber;
    private String user;

    private JPanel mainFrame;
    public JPanel mainPanel;
    private JPanel APIPanel;

    private JButton btnFriend;
    private JButton btnSearch;
    private JLabel lblAPI;
    private JButton btnChat;
    private JButton btnMore;
    private JPanel leftFrame;
    private JPanel fieldPanel;


    private static JPanel target;
    public static JPanel getTarget() {
        return target;
    }
    public void setTarget(JPanel target) {
        this.target = target;
    }

    private ServerInfo info;
    private static Socket socket;
    public static Socket getSocket() {
        return socket;
    }
    public static void setSocket(Socket sck) {
        socket = sck;
    }
    private Scanner clientInput;
    private PrintWriter clientOutput;

    private static boolean isChange = false;
    public static boolean getIsChange() {
        return isChange;
    }
    public static void setIsChange(boolean isChange) {
        MainFrame.isChange = isChange;
    }
    private static boolean isLogout = false;
    public boolean isLogout() {
        return isLogout;
    }
    public static void setLogout(boolean logout) {
        isLogout = logout;
    }

    MainFrame(ServerInfo serverInfo, String nickName) {
        this.info = serverInfo;
        this.user = new String(nickName);

        try {
            socket = new Socket(info.serverIP, info.serverPort);
        } catch(Exception e) {
            e.printStackTrace();
        }

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (isLogout == true) {
                    isLogout = false;
                    dispose();
                } // ????????????
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (isLogout == true) {
                    isLogout = false;
                    dispose();
                } // ????????????
            }
        }); // ????????? ?????????

        btnFriend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FriendBoard friendBoard = new FriendBoard(user, socket);

                fieldPanel.removeAll();
                fieldPanel.add(friendBoard.getFieldPanel());
                // ?????? ?????? ?????????

                mainPanel.removeAll();
                mainPanel.add(friendBoard.getScrFriend());
                // ?????? ?????? ?????????

                mainPanel.revalidate();
                mainPanel.repaint();
                target = mainPanel;
            }
        }); // ?????? ?????? ????????????

        btnChat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        }); // ?????? ?????? ????????????

        btnMore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        }); // ????????? ??????

        JButton[] btnGroup = new JButton[3];
        btnGroup[0] = btnFriend;
        btnGroup[1] = btnChat;
        btnGroup[2] = btnMore;
        // ?????? ?????? ??????

        setContentPane(mainFrame);

        FriendBoard friendBoard = new FriendBoard(user, socket);

        fieldPanel.setLayout(new GridLayout(1, 1));
        mainPanel.setLayout(new GridLayout(1, 1));
        fieldPanel.add(friendBoard.getFieldPanel());
        mainPanel.add(friendBoard.getScrFriend());
        target = mainPanel;
        // ?????? ?????? ????????????

        setSize(450, 600);
        ImageResizer.InterfaceImage(btnGroup);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("KakaoTalk - " + user);
        setVisible(true);
    }
}
