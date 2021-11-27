package com.BUS;

import com.DAO.UserAccountDAO;
import com.DTO.UserAccountDTO;

import java.util.ArrayList;

/**
 * Mỗi tài khoản chỉ thuộc về 1 người
 */
public class UserAccountBUS {
    private ArrayList<UserAccountDTO> list_DTO;
    /**
     * Xử lý các lệnh trong SQL
     */
    private UserAccountDAO DAO;

    public UserAccountBUS() throws Exception {
        list_DTO = new ArrayList<>();
        DAO = new UserAccountDAO();
        list_DTO = DAO.readDB();
    }

    /**
     * thêm 1 tài khoản vào danh sách và database
     * @return true nếu thành công
     */
    public Boolean them(UserAccountDTO DTO) throws Exception{
        if ( DAO.add(DTO) ) {
            list_DTO.add(DTO);
        }
        return false;
    }

    /**
     * xóa 1 tài khoản khỏi danh sách và database
     * @return true nếu thành công
     */
    public Boolean delete(UserAccountDTO DTO) throws Exception {
        if ( DAO.delete(DTO) ) {
            // duyệt từng phẩn tử
            for ( UserAccountDTO taikhoan : list_DTO ) {
                if (taikhoan.getStrUid().equals(DTO.getStrUid())){
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
     * @return true nếu thực hiện thành công
     */
    public Boolean update(UserAccountDTO DTO) throws Exception {
        if ( DAO.update(DTO) ) {

            // duyệt từng phẩn tử
            for ( UserAccountDTO model : list_DTO ) {
                if (model.getStrUid().equals(DTO.getStrUid())){
                    break;
                }
            }
        }

        return false;
    }

    /**
     * Kiêm tra xem tài khoản đó có trong arraylist hay chưa <br>
     * <h3> Không phân biệt hoa thường </h3>
     * @return true nếu thành công
     */
    public Boolean kiemTraDangNhap(UserAccountDTO DTO) {
        for ( UserAccountDTO model : list_DTO ) {
            // kiểm tra mật khảu và tên đăng nhập
            if (model.getStrUserName().equals(DTO.getStrUserName())
                    && model.getStrPassWord().equals(DTO.getStrPassWord()) ) {
                return true;
            }
        }
        return false;
    }
}
