package dev.steadypim.socialmediaapi.image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

/**
 * Сервис по загрузке/выгрузке изображений
 */
@Service
@RequiredArgsConstructor
public class ImageService {

    private static final String FOLDER_PATH = "C:\\code\\social-media-api\\src\\main\\resources\\static\\images\\";
    private final ImageRepository repository;

    /**
     * Метод загружает изображение в папку и сохраняет в БД путь до изображения
     * @param file загружаемый файл
     * @return сущность изображения
     * @throws IOException обработка ошибок
     */
    public Image uploadImageToFileSystem(MultipartFile file) throws IOException {
        String filePath = FOLDER_PATH + file.getOriginalFilename();

        Image image = Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .filePath(filePath).build();


        image = repository.save(image);
        file.transferTo(new File(filePath));

        if (image != null) {
            return image;
        }else {
            throw new IOException("Image not saved");
        }
    }

    /**
     * Метод выгружает изображение из БД на клиент
     * @param fileName имя файла
     * @return изображение
     * @throws IOException обработка ошибок
     */
    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
        Optional<Image> fileData = repository.findByName(fileName);
        String filePath = fileData.get().getFilePath();
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
    }
}
