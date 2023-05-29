package dev.steadypim.socialmediaapi;

import dev.steadypim.socialmediaapi.post.Post;
import dev.steadypim.socialmediaapi.post.PostController;
import dev.steadypim.socialmediaapi.post.PostDto;
import dev.steadypim.socialmediaapi.post.PostService;
import dev.steadypim.socialmediaapi.user.User;
import dev.steadypim.socialmediaapi.user.UserService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @MockBean
    private UserService userService;

    @Test
    public void testCreatePost() throws Exception {
        // Arrange
        String text = "Post text";
        String title = "Post title";
        List<MultipartFile> imageFiles = new ArrayList<>();
        Authentication authentication = Mockito.mock(Authentication.class);
        User user = new User();
        Post createdPost = new Post();
        createdPost.setText(text);
        createdPost.setTitle(title);

        Mockito.when(userService.getCurrentUser(authentication)).thenReturn(user);
        Mockito.when(postService.create(user, title, text, imageFiles)).thenReturn(createdPost);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/posts")
                        .param("text", text)
                        .param("title", title)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .characterEncoding("UTF-8")
                        .requestAttr("imageFiles", imageFiles)
                        .principal(authentication))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.text", Matchers.is(text)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is(title)));
    }

    @Test
    public void testGetAllPosts() throws Exception {
        // Arrange
        List<PostDto> posts = new ArrayList<>();
        posts.add(new PostDto());
        posts.add(new PostDto());

        Mockito.when(postService.getAllPosts()).thenReturn(posts);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.is(posts.size())));
    }


}
