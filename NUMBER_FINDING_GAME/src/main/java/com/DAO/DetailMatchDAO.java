package com.DAO;

import com.DTO.DetailMatchDTO;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;


public class DetailMatchDAO {
    MyConnectUnit connect;
    String strTableName = "tbldetailmatch";

    /**
     * Lấy thông tin từ Database
     */
    public ArrayList<DetailMatchDTO> readDB(String condition, String orderBy) throws Exception {
        // kết nối CSDL
        connect = new MyConnectUnit();

        ResultSet result = this.connect.Select(strTableName, condition, orderBy);
        ArrayList<DetailMatchDTO> DTOs = new ArrayList<>();
        while (result.next()) {
            DetailMatchDTO DTO = new DetailMatchDTO();
            DTO.setStrUid(result.getString("UID"));
            DTO.setStrIdRoom(result.getString("IdRoom"));
            DTO.setStrPlayerColor(result.getString("playerColor"));
            DTO.setIntPoint(Integer.parseInt(result.getString("point")));
            DTO.setStrKetQua(result.getString("KetQua"));

            DTOs.add(DTO);
        }
        connect.Close();
        return DTOs;
    }

    public ArrayList<DetailMatchDTO> readDB(String condition) throws Exception {
        return readDB(condition, null);
    }

    public ArrayList<DetailMatchDTO> readDB() throws Exception {
        return readDB(null);
    }


    /**
     * Tạo thêm 1 dựa theo đã có thông tin trước
     *
     * @return true nếu thành công
     */
    public Boolean add(DetailMatchDTO tk) throws Exception {
        connect = new MyConnectUnit();

        // tạo đối tượng truyền vào
        HashMap<String, Object> insertValues = new HashMap<>();
        insertValues.put("UID", tk.getStrUid());
        insertValues.put("IdRoom ", tk.getStrIdRoom());
        insertValues.put("playerColor", tk.getStrPlayerColor());
        insertValues.put("point", tk.getIntPoint());
        insertValues.put("KetQua", tk.getStrKetQua());

        Boolean check = connect.Insert(strTableName, insertValues);

        connect.Close();
        return check;
    }
}
