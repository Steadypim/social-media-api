package dev.steadypim.socialmediaapi.post;

import dev.steadypim.socialmediaapi.image.Image;
import dev.steadypim.socialmediaapi.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Конвертер постов
 */
@Component
@RequiredArgsConstructor
public class PostMapper {
    private final ImageService imageService;


    public PostDto toDto(Post post) throws IOException {
        return PostDto.builder()
                .text(post.getText())
                .title(post.getTitle())
                .images(convertPathsToImages(post.getImages()))
                .build();
    }

    public List<byte[]> convertPathsToImages(List<Image> imagesPaths) throws IOException {
        List<byte[]> images = new ArrayList<>();
        for (Image imagePath : imagesPaths) {
            byte[] image = imageService.downloadImageFromFileSystem(imagePath.getName());

            images.add(image);
        }
        return images;
    }
}
