package dev.steadypim.socialmediaapi.post;

import dev.steadypim.socialmediaapi.image.Image;
import dev.steadypim.socialmediaapi.image.ImageService;
import dev.steadypim.socialmediaapi.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Сервис постов
 */
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ImageService imageService;
    private final PostMapper postMapper;

    /**
     * Метод создает пост
     * @param user пользователь создающий пост
     * @param title заголовок поста
     * @param text текст поста
     * @param images изображения поста
     * @return сохраненный пост
     * @throws IOException обработка ошибок
     */
    public Post create(
            User user,
            String title,
            String text,
            List<MultipartFile> images) throws IOException {

        return postRepository.save(Post.builder()
                .user(user)
                .title(title)
                .text(text)
                .images(convertImageList(images))
                .build());
    }

    /**
     * Метод возвращает все посты в БД
     * @return все посты в БД
     * @throws IOException обработка ошибок
     */
    public List<PostDto> getAllPosts() throws IOException {
        List<Post> posts = postRepository.findAll();

        List<PostDto> postDtos = new ArrayList<>();
        for (Post post : posts) {
            PostDto postDto = postMapper.toDto(post);
            postDtos.add(postDto);
        }

        return postDtos;
    }

    /**
     * Метод возвращает все посты определенного пользователя
     * @param id идентификатор пользователя
     * @return посты пользователя
     * @throws IOException обработка ошибок
     */
    public List<PostDto> getAllUserPosts(Integer id) throws IOException {
        List<Post> posts = postRepository.findByUserId(id)
                .orElseThrow(() -> new RuntimeException("User does not have posts"));

        List<PostDto> postDtos = new ArrayList<>();
        for (Post post : posts) {
            PostDto postDto = postMapper.toDto(post);
            postDtos.add(postDto);
        }

        return postDtos;
    }

    /**
     * Метод возвращает дто поста по идентификатору
     * @param postId идентификатор поста
     * @return пост
     * @throws IOException обработка ошибок
     */
    public PostDto getPostById(Integer postId) throws IOException {
        return postMapper.toDto(postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found")));
    }

    /**
     * Метод возвращает сущность поста по идентификатору
     * @param postId идентификатор поста
     * @return пост
     */
    public Post findById(Integer postId){
        return postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    /**
     * Метод редактирует существующий пост
     * @param postId идентификатор поста
     * @param title заголовок поста
     * @param text текст поста
     * @param imageFiles изображения поста
     * @param user владелец поста
     * @return обновленный пост
     * @throws IOException обработка ошибок
     */
    public Post updatePost(Integer postId, String title, String text, List<MultipartFile> imageFiles, User user) throws IOException {
        return postRepository.save(Post.builder()
                        .id(postId)
                        .user(user)
                        .title(title)
                        .text(text)
                        .images(convertImageList(imageFiles))
                        .build());
    }

    /**
     * Метод конвертирует MultipartFile изображения в сущность
     * @param imageFiles изображения с клиента
     * @return сущности сохраненные в БД
     * @throws IOException обработка ошибок
      */
    public List<Image> convertImageList(List<MultipartFile> imageFiles) throws IOException {
        List<Image> savedImages = new ArrayList<>();
        for (MultipartFile imageFile : imageFiles) {
            Image image = imageService.uploadImageToFileSystem(imageFile);
            savedImages.add(image);
        }

        return savedImages;
    }

    /**
     * Метод удаляет пост по его идентификатору
     * @param postId идентификатор поста
     */
    public void deletePost(Integer postId) {
        postRepository.deleteById(postId);
    }
}
