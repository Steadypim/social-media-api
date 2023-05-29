package dev.steadypim.socialmediaapi.image;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Изображение
 * @field id - идентификатор сообщения в БД
 * @field name - имя изображения
 * @field type - тип изображения
 * @field filePath - путь до каталога
 */
@Entity
@Table(name = "image")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String type;
    private String filePath;
}
