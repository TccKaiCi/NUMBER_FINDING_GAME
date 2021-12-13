package com.DAO;

import com.DTO.UserAccountDTO;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class UserAccountDAO {
    MyConnectUnit connect;
    String strTableName = "tbluseraccount";

    /**
     * Lấy thông tin từ Database
     */
    public ArrayList<UserAccountDTO> readDB(String condition, String orderBy) throws Exception {
        // kết nối CSDL
        connect = new MyConnectUnit();

        ResultSet result = this.connect.Select(strTableName, condition, orderBy);
        ArrayList<UserAccountDTO> DTOs = new ArrayList<>();
        while (result.next()) {
            UserAccountDTO taokhoan = new UserAccountDTO();
            taokhoan.setStrUid(result.getString("UID"));
            taokhoan.setStrUserName(result.getString("username"));
            taokhoan.setStrPassWord(result.getString("passwd"));
            taokhoan.setStrNameInf(result.getString("nameinf"));
            taokhoan.setStrGender(result.getString("gender"));
            taokhoan.setStrDayOfBirth(result.getString("dayofbirth"));

            DTOs.add(taokhoan);
        }
        connect.Close();
        return DTOs;
    }

    public ArrayList<UserAccountDTO> readDB(String condition) throws Exception {
        return readDB(condition, null);
    }

    public ArrayList<UserAccountDTO> readDB() throws Exception {
        return readDB(null);
    }

    /**
     * Tạo thêm 1 tài khoản dựa theo đã có thông tin trước
     *
     * @return true nếu thành công
     */
    public Boolean add(UserAccountDTO tk) throws Exception {
        connect = new MyConnectUnit();

        // tạo đối tượng truyền vào
        HashMap<String, Object> insertValues = new HashMap<>();
        insertValues.put("UID", tk.getStrUid());
        insertValues.put("username", tk.getStrUserName());
        insertValues.put("passwd", tk.getStrPassWord());
        insertValues.put("nameinf", tk.getStrNameInf());
        insertValues.put("gender", tk.getStrGender());
        insertValues.put("dayofbirth", tk.getStrDayOfBirth());

        Boolean check = connect.Insert(strTableName, insertValues);

        connect.Close();
        return check;
    }

    /**
     * @param tk chuyền vào dữ liệu tài khoản để xóa
     * @return true nếu thành công
     */
    public Boolean delete(UserAccountDTO tk) throws Exception {
        connect = new MyConnectUnit();
        String condition = " UID = '" + tk.getStrUid() + "'";

        Boolean check = connect.Delete(strTableName, condition);

        connect.Close();
        return check;
    }

    /**
     * @param tk truyền vào dữ liệu tài khoản mới
     *           Sửa thông tin đăng nhập hoặc là cấp bậc của 1 tài khoản
     * @return true nếu thành công
     */
    public Boolean update(UserAccountDTO tk) throws Exception {
        connect = new MyConnectUnit();

        // tạo đối tượng truyền vào
        HashMap<String, Object> insertValues = new HashMap<>();
        insertValues.put("UID", tk.getStrUid());
        insertValues.put("username", tk.getStrUserName());
        insertValues.put("passwd", tk.getStrPassWord());
        insertValues.put("nameinf", tk.getStrNameInf());
        insertValues.put("gender", tk.getStrGender());
        insertValues.put("dayofbirth", tk.getStrDayOfBirth());
//        insertValues.put("capbac", tk.getiCapBac());

        String condition = " UID = '" + tk.getStrUid() + "'";

        Boolean check = connect.Update(strTableName, insertValues, condition);

        connect.Close();
        return check;
    }

}
