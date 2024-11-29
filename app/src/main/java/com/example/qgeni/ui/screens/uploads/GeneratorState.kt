package com.example.qgeni.ui.screens.uploads

sealed interface GeneratorState {
    data object Idle : GeneratorState // Lúc bình thường
    data object Loading : GeneratorState // Đang tạo đề
    data object Saving : GeneratorState // Màn hình lưu title của đề
    data object Success : GeneratorState  // Tạo xong
    data object Error : GeneratorState // Bị lỗi
}