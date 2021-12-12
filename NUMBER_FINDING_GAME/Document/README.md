# Cú pháp function handle trong server

![Language](https://img.shields.io/badge/Language-Java-orange.svg)
![Language](https://img.shields.io/badge/Team-SGU-orange.svg)

## Team
| Cú pháp | Luồng dữ liệu | Ví dụ | Mô tả |
|---| --- | --- | --- |
| SIGNIN;username;passwd | Client -> Server | SIGNIN;Test;test | đăng nhập | 
| MAP;Value | Server -> Client | trả về Map | Tạo mạp random từ server cho các client |
| start | Server <-> Client | Client join lobby | Cấp cho client một lobby còn trống |
| exit | Client -> Server | Client thoát khỏi Server | Thoát khỏi lobby |
| Pickup | Client <-> Server | Pickup;Number:Color:Rare:UID | Gửi dữ liệu in game |
| NextNumber | Server -> Client | NextNumber;Value:Rare | Số cần chọn kế tiếp |
| FillColor | Client <-> Server | FillColor;Number:Color | Tô màu cho số |

[comment]: <> (
| start | cấp cho client một lobby còn trống |  |
| exit | xóa client khỏi danh sách connect | |
| reset | reconect client | |
)
## Luồng 
![image](https://user-images.githubusercontent.com/52872325/145433704-401a032e-7755-4d2b-b22c-41601eccb929.png)

![image](https://user-images.githubusercontent.com/52872325/145433888-3576728d-5e46-408f-97e6-30a85fe143eb.png)
