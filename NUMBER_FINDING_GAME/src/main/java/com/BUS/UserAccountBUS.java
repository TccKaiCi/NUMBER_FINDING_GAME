package com.BUS;

import com.DAO.UserAccountDAO;
import com.DTO.UserAccountDTO;

import java.util.ArrayList;

/**
 * Server
 * Mỗi tài khoản chỉ thuộc về 1 người
 */
public class UserAccountBUS {
    private ArrayList<UserAccountDTO> list_DTO;
    /**
     * Xử lý các lệnh trong SQL
     */
    private final UserAccountDAO DAO;

    public UserAccountBUS() throws Exception {
        list_DTO = new ArrayList<>();
        DAO = new UserAccountDAO();
        list_DTO = DAO.readDB();
    }

    /**
     * thêm 1 tài khoản vào danh sách và database
     *
     * @return true nếu thành công
     */
    public Boolean them(UserAccountDTO DTO) throws Exception {
        if (DAO.add(DTO)) {
            list_DTO.add(DTO);
        }
        return false;
    }

    /**
     * xóa 1 tài khoản khỏi danh sách và database
     *
     * @return true nếu thành công
     */
    public Boolean delete(UserAccountDTO DTO) throws Exception {
        if (DAO.delete(DTO)) {
            // duyệt từng phẩn tử
            for (UserAccountDTO taikhoan : list_DTO) {
                if (taikhoan.getStrUid().equals(DTO.getStrUid())) {
                    list_DTO.remove(taikhoan);
                    break;
                }
            }
        }
        return false;
    }

    /**
     * sửa thông tin của 1 tài khoản <br>
     * - Trừ thông tin đăng nhập của tài khoản đó
     *
     * @return true nếu thực hiện thành công
     */
    public Boolean update(UserAccountDTO DTO) throws Exception {
        if (DAO.update(DTO)) {

            // duyệt từng phẩn tử
            for (UserAccountDTO dto : list_DTO) {
                if (dto.getStrUid().equals(DTO.getStrUid())) {
                    dto.setStrDayOfBirth(DTO.getStrDayOfBirth());
                    dto.setStrGender(DTO.getStrGender());
                    dto.setStrNameInf(DTO.getStrNameInf());
                    dto.setStrPassWord(DTO.getStrPassWord());
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Kiêm tra xem tài khoản đó có trong arraylist hay chưa <br>
     * <h3> Không phân biệt hoa thường </h3>
     *
     * @return true nếu thành công
     */
    public Boolean kiemTraDangNhap(UserAccountDTO DTO) {
        for (UserAccountDTO model : list_DTO) {
            // kiểm tra mật khảu và tên đăng nhập
            if (model.getStrUserName().equals(DTO.getStrUserName())
                    && model.getStrPassWord().equals(DTO.getStrPassWord())) {
                return true;
            }
        }
        return false;
    }

    public Boolean kiemTraDangki(UserAccountDTO DTO) {
        for (UserAccountDTO model : list_DTO) {
            // kiểm tra mật khảu và tên đăng nhập
            if (model.getStrUserName().equals(DTO.getStrUserName())) {
                return true;
            }
        }
        return false;
    }

    public UserAccountDTO getUserAccountByUserName(UserAccountDTO DTO) {
        for (UserAccountDTO model : list_DTO) {
            // kiểm tra mật khảu và tên đăng nhập
            if (model.getStrUserName().equals(DTO.getStrUserName())
                    && model.getStrPassWord().equals(DTO.getStrPassWord())) {
                return model;
            }
        }
        return null;
    }

    public UserAccountDTO getUserAccountByUID(String UID) {
        for (UserAccountDTO model : list_DTO) {
            // kiểm tra mật khảu và tên đăng nhập
            if (model.getStrUid().equals(UID)) {
                return model;
            }
        }
        return null;
    }

    public String getNameInf_UID(String uid) {
        for (UserAccountDTO model : list_DTO) {
            // kiểm tra mật khảu và tên đăng nhập
            if (model.getStrUid().equals(uid)) {
                return model.getStrNameInf();
            }
        }
        return null;
    }

    public String getDefault() {
        if (list_DTO.size() == 0)
            return "3118410";
        else {
            String s = "311841";
            int iNumb = 0;

            for (UserAccountDTO user : list_DTO) {
                //3118410
                String[] UID = user.getStrUid().split("311841");
                iNumb = Integer.parseInt(UID[1]);
                iNumb++;
            }
            switch (demSoChuSo(iNumb)) {
                case 1:
                    s += "0";
                case 2:
                    s += "0";
                case 3:
            }
            s += iNumb;
            return s;
        }
    }

    public int demSoChuSo(int nInput) {
        if (nInput < 10) {
            return 1;
        }
        return 1 + demSoChuSo(nInput / 10);
    }
}
