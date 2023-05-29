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

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ImageService imageService;
    private final PostMapper postMapper;

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

    public List<PostDto> getAllPosts() throws IOException {
        List<Post> posts = postRepository.findAll();

        List<PostDto> postDtos = new ArrayList<>();
        for (Post post : posts) {
            PostDto postDto = postMapper.toDto(post);
            postDtos.add(postDto);
        }

        return postDtos;
    }

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

    public PostDto getPostById(Integer postId) throws IOException {
        return postMapper.toDto(postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found")));
    }

    public Post updatePost(Integer postId, String title, String text, List<MultipartFile> imageFiles, User user) throws IOException {
        return postRepository.save(Post.builder()
                        .id(postId)
                        .user(user)
                        .title(title)
                        .text(text)
                        .images(convertImageList(imageFiles))
                        .build());
    }

    public List<Image> convertImageList(List<MultipartFile> imageFiles) throws IOException {
        List<Image> savedImages = new ArrayList<>();
        for (MultipartFile imageFile : imageFiles) {
            Image image = imageService.uploadImageToFileSystem(imageFile);
            savedImages.add(image);
        }

        return savedImages;
    }

    public void deletePost(Integer postId) {
        postRepository.deleteById(postId);
    }
}
