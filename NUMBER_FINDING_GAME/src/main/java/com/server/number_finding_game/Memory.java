package com.server.number_finding_game;

import com.DTO.Ranking;
import com.DTO.UserAccountDTO;

import java.util.HashMap;

/**
 * Information data storage
 * @author HiamKaito
 */
public class Memory {
    public static NewClient client = new NewClient();

    public static UserAccountDTO userAccountDTO = new UserAccountDTO();
    public static Ranking rankingDTO = new Ranking();

    public static String userColor;
    public static HashMap<String,String> otherUserInfor_Color = new HashMap<>();

//    ======================================================================
//    ======================================================================

    //    About Client to CLien
    public static String messenger = " ";
    /**
     * true if there is a messenger come from the other client
     * <br>It mean in your turn is false
     */
    static boolean playerMessenger = true;

    static String statusMessenger = " ";
}
