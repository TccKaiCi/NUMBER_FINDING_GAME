# TRÒ CHƠI TÌM SỐ [Document](https://github.com/HiamKaito/NUMBER_FINDING_GAME/blob/main/NUMBER_FINDING_GAME/Document/README.md) || [Demo](https://youtu.be/KDLBvzmWwRU)

## Tái sử dụng code từ: [Shoe Shop](https://github.com/HiamKaito/Shop_Manager_System), [SQL Java](https://github.com/HiamKaito/Ket-Noi-SQL-JAVA), [TicTacToe - LAN](https://github.com/HiamKaito/TicTacToe)

## Chạy file : [Client](https://github.com/HiamKaito/NUMBER_FINDING_GAME/blob/main/NUMBER_FINDING_GAME/src/main/java/com/client/number_finding_game/LoginForm.java) và [Server](https://github.com/HiamKaito/NUMBER_FINDING_GAME/blob/main/NUMBER_FINDING_GAME/src/main/java/com/server/number_finding_game/ServerManager.java)

![Language](https://img.shields.io/badge/Language-Java-orange.svg)
![Language](https://img.shields.io/badge/Team-SGU-orange.svg)
![Language](https://img.shields.io/badge/Môn-LTM-orange.svg)
![Language](https://img.shields.io/badge/Type-Game-orange.svg)

## Team
| Họ và Tên  | Link |
| ----- | ----- |
| Nguyễn Tuấn Anh | [FB](https://www.facebook.com/ProHZGOD) |
| Tăng Chí Chung | [FB](https://www.facebook.com/hiamkaito.404/) |
| Nguyễn Ngọc Tiến Em | [FB](https://www.facebook.com/nguyenngoctienem.nguyen) |

# Yêu cầu về chức năng phía client (phải có GUI):
- Cách chơi: SV xem hình minh họa ở trên, game mô phỏng lại trò chơi tìm số trên giấy, các số cần tìm không cần theo thứ tự tăng dần mà theo thứ tự chương trình đưa ra.<br>
- Chức năng đăng ký tài khoản mới, đăng nhập, cập nhật thông tin tài khoản tương tự đề tài 1 (loại trừ phần xác thực email).<br>
- Chức năng chơi game:<br>
  ✓ Cho phép 2-3 người chơi đồng thời.<br>
  ✓ Hiển thị 100 số từ 1->100 (có thể cấu hình lại từ phía server).<br>
  ✓ Thời gian chơi 1 ván là 2 phút (có thể cấu hình lại từ phía server). Nếu hết thời gian chơi mà chưa tìm hết số thì kết quả thắng/thua dựa trên số lượng số mỗi người tìm được.<br>
  ✓ Số nào đã tìm được thì đánh dấu bằng màu dựa trên người chơi.<br>
  ✓ Thông báo số cần tìm kế tiếp khi có người đã tìm ra số hiện tại. Các số cần tìm hiển thị ngẫu nhiên, không cần theo thứ tự 1->100.<br>
  ✓ Các chức năng tăng cường cho người chơi:<br>
  - Tăng điểm: cộng điểm nhiều hơn khi tìm được số may mắn.<br>
  - Ưu tiên: che hết số của những người chơi khác trong vòng 3 giây.<br>
- Chức năng xếp hạng: liệt kê thứ hạng dựa trên thành tích của người dùng. Tra cứu được thứ hạng, tỉ lệ thắng/thua của từng người chơi.<br>
# Yêu cầu về chức năng phía server (không cần GUI):
- Xử lý mọi dữ liệu từ client và gửi ngược lại cho client cập nhật.
- Thống kê được số lượng người dùng, người dùng đang online.
- Cấu hình được số lượng số / ván chơi, thời gian / ván chơi.
# Yêu cầu chung:
- Các client phải chạy trên các máy tính khác nhau.
