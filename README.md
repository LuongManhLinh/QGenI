# 🌟 QGenI - Ứng dụng sinh câu hỏi IELTS
## 📝 Giới thiệu
QGenI là ứng dụng hỗ trợ học IELTS với tính năng nổi bật là tạo bộ câu hỏi từ một đoạn văn hoặc hình ảnh cho trước

## 🎯 Tính năng nổi bật: Tạo bộ câu hỏi IELTS tự động
### ✨ Tạo đề đọc
Người dùng sẽ dán một đoạn văn bản hoặc upload một file text (.txt), đồng thời nhập số câu hỏi. 
Hệ thống sẽ dựa trên phần văn bản đó, sinh ra đề bao gồm các câu hỏi dạng "True - False - Not Given". 

### ✨ Tạo đề nghe
Người dùng sẽ upload tối đa 4 hình aảnh, mỗi hình ảnh đại diện cho 1 chủ đề, mỗi chủ đề sẽ gồm 4 câu hỏi.
Hệ thống sẽ dựa trên các hình ảnh chủ đề để tìm kiếm các hình ảnh khác tương đương, sau đó tạo ra đề có dạng như sau:
Mỗi câu hỏi sẽ gồm 4 hình ảnh và 1 đoạn âm thanh, nhiệm vụ của người dùng là chọn hình ảnh đúng nhất với mô tả 
trong đoạn âm thanh đó.

---

# 🚀 Cách chạy trên Android Studio
## Yêu cầu
    - minSdk: 24 (Android 7.0 Nougat)
    - targetSdk: 34 (Tối ưu hóa cho Android 14)
    - JDK: 8 trở lên
    - Kotlin: 1.8 
## Chạy ứng dụng
1. Clone project và chạy trên Android Studio
2. Khi chạy ứng dụng, ở màn hình đầu tiên, khi nhấn nút `Next` sẽ có một Dialog hiện ra, hãy nhập giá trị cho các trường `Database Port` và `Image Generator Port` như sau:
    - Database Port: `17826`
    - Image Generator Port: `19601`

    Lí do là vì í do vì nhóm em chạy Server ở __Local__. Để các máy ngoài LAN có thể truy cập, nhóm sử dụng __Ngrok__ để tạo tunnels nhưng các tunnels không có port cố định nên cần phải config. Hơn nữa, vì chạy Server ở Local nên khi tắt máy tính, các chức năng chính của ứng dụng sẽ không hoạt động.
    

    Nhóm sẽ cố gắng __cập nhật__ Database Port và Image Generator Port trong file README.md mỗi khi có thay đổi.

3. Trải nghiệm ứng dụng
                   
