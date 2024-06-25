package Dino.Duett.global.utils;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public abstract class Validator {
    public static boolean isNullOrBlank(final String string) {
        return string == null || string.isBlank();
    }
    public static boolean isNullOrEmpty(final MultipartFile file) {
        return file == null || file.isEmpty();
    }

    public static boolean isNullOrEmpty(final List<?> list){
        return list == null || list.isEmpty();
    }
}
