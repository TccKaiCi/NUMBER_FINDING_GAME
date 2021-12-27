package com.BUS;

import com.DAO.DetailMatchDAO;
import com.DTO.DetailMatchDTO;
import com.DTO.Ranking;

import java.util.ArrayList;

/**
 * Only for server
 */
public class DetailMatchBUS {
    private ArrayList<DetailMatchDTO> list_DTO;
    /**
     * Xử lý các lệnh trong SQL
     */
    private final DetailMatchDAO DAO;

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

    // Lấy dữ liệu UID Diem KetqUA từ DetailmatchDto
    public ArrayList<Ranking> getUID_Diem_KetQua() {
        ArrayList<Ranking> list = new ArrayList<>();
        Ranking dto = new Ranking();
        for (DetailMatchDTO dtos : list_DTO) {
            dto.setUID(dtos.getStrUid());
            dto.setPoint(dtos.getIntPoint());
            dto.setKetQua(dtos.getStrKetQua());
            list.add(dto);
        }
        return list;
    }


    /**
     * Get data by Json
     * @return
     */
    public String initJsonRankTable() {
        try {
            UserAccountBUS bus = new UserAccountBUS();
            StringBuilder sb = new StringBuilder();

//        open json
            sb.append("{\n" +
                    "  \"data\": [");

            list_DTO.forEach(model -> {
                sb.append("{\n" +
                        "      \"strUID\": " + model.getStrUid() + " ,\n" +
                        "      \"intPoint\": " + model.getIntPoint() + ",\n" +
                        "      \"strKetQua\": " + model.getStrKetQua() + ",\n" +
                        "      \"strNameInfor\": " + bus.getNameInf_UID(model.getStrUid()) + "\n" +
                        "    },");
            });

//        delete end ","
            sb.deleteCharAt(sb.length() - 1);
//        close json
            sb.append("\n" +
                    " \n]" +
                    "}");

            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
