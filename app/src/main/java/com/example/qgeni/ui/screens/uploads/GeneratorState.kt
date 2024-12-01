package com.example.qgeni.ui.screens.uploads

sealed interface GeneratorState {
    data object Idle : GeneratorState // Lúc bình thường
    data object Loading : GeneratorState // Đang tạo
    data object Titling: GeneratorState // Màn hình lưu title của đề
    data object Saving : GeneratorState  // Đang lưu
    data object Success : GeneratorState  // Tạo xong
    data object Error : GeneratorState // Bị lỗi
}