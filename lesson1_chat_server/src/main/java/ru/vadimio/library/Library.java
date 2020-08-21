package ru.vadimio.library;

import java.io.*;
import java.util.HashSet;

public class Library {
    /*
    /auth_request±login±password
    /auth_accept±nickname
    /auth_error
    /broadcast±msg
    /msg_format_error±msg
    /user_list±user1±user2±user3±....
    * */
    public static final String DELIMITER = "±";
    public static final String AUTH_REQUEST = "/auth_request";
    public static final String AUTH_ACCEPT = "/auth_accept";
    public static final String AUTH_DENIED = "/auth_denied";
    public static final String SIGN_UP_DENIED = "/sign_up_denied";
    public static final String MSG_FORMAT_ERROR = "/msg_format_error";
    public static final String SIGN_UP_REQUEST = "/sign_up_request";
    // если мы вдруг не поняли, что за сообщение и не смогли разобрать
    public static final String TYPE_BROADCAST = "/bcast";
    // то есть сообщение, которое будет посылаться всем
    public static final String TYPE_BCAST_CLIENT = "/client_msg";
    public static final String USER_LIST = "/user_list";
    private static final String CENSORED_TXT = "C:\\Users\\Vadim\\Documents\\Spring1\\lesson1_chat_server\\src\\main\\java\\ru\\vadimio\\censored.txt";

    public static String getTypeBcastClient(String msg) throws IOException {
        return TYPE_BCAST_CLIENT + DELIMITER + censorshipCheck(msg);
    }

    public static String getUserList(String users) {
        return USER_LIST + DELIMITER + users;
    }

    public static String getAuthRequest(String login, String password) {
        return AUTH_REQUEST + DELIMITER + login + DELIMITER + password;
    }

    public static String getAuthAccept(String nickname) {
        return AUTH_ACCEPT + DELIMITER + nickname;
    }

    public static String getAuthDenied() {
        return AUTH_DENIED;
    }

    public static String getSignUpDenied() {
        return SIGN_UP_DENIED;
    }

    public static String getMsgFormatError(String message) {
        return MSG_FORMAT_ERROR + DELIMITER + message;
    }

    public static String getTypeBroadcast(String src, String message) {
        return TYPE_BROADCAST + DELIMITER + System.currentTimeMillis() +
                DELIMITER + src + DELIMITER + message;
    }

    public static String getSignUpRequest(String login, String nickname, String password) {
        return SIGN_UP_REQUEST + DELIMITER + login + DELIMITER + nickname + DELIMITER + password;
    }

    public static String censorshipCheck(String msg) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(CENSORED_TXT));
        HashSet<String> censoredSet = new HashSet<>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            censoredSet.add(line);
        }
        bufferedReader.close();
        msg = msg.toLowerCase();
        for (String s : censoredSet) {
            msg = msg.replace(s, "[censored]");
        }
        return msg;
    }
}
