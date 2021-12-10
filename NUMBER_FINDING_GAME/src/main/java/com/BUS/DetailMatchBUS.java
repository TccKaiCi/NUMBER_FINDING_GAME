package com.BUS;

import com.DAO.DetailMatchDAO;
import com.DTO.DetailMatchDTO;

import java.util.ArrayList;

public class DetailMatchBUS {
    private ArrayList<DetailMatchDTO> list_DTO;
    /**
     * Xử lý các lệnh trong SQL
     */
    private DetailMatchDAO DAO;

    public DetailMatchBUS() throws Exception {
        list_DTO = new ArrayList<>();
        DAO = new DetailMatchDAO();
        list_DTO = DAO.readDB();
    }

    /**
     * thêm 1 tài khoản vào danh sách và database
     *
     * @return true nếu thành công
     */
    public Boolean add(DetailMatchDTO DTO) throws Exception {
        if (DAO.add(DTO)) {
            list_DTO.add(DTO);
        }
        return false;
    }

    public Boolean update(DetailMatchDTO DTO) throws Exception {
        if (DAO.update(DTO)) {
            // duyệt từng phẩn tử
            for ( DetailMatchDTO tmp : list_DTO ) {
                if (tmp.getStrIdRoom().equals(DTO.getStrIdRoom())
                        && tmp.getStrUid().equals(DTO.getStrUid())){
                    tmp.setIntPoint(DTO.getIntPoint());
                    tmp.setStrPlayerColor(DTO.getStrPlayerColor());
                    return true;
                }
            }
        }
        return false;
    }

}
