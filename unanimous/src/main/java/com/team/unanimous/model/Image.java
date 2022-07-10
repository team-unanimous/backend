package com.team.unanimous.model;

import com.team.unanimous.dto.ImageDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@NoArgsConstructor
@Getter
@Entity
public class Image{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @Column(nullable = false)
    private String filename;

    @Column(length = 500)
    private String userImage;

    @Column
    private String imageUrl;

    @Column(nullable = true)
    private Long userId;

    public Image(String filename, String userImage, Long userId) {
        this.filename = filename;
        this.userImage = userImage;
        this.userId = userId;
    }
    //소셜 로그인 가입 시 이미지 repo 등록
    public Image(String profileImgUrl, Long userId) {
        this.filename = profileImgUrl + "1";
        this.userImage = profileImgUrl;
        this.userId = userId;
    }
    public Image(ImageDto imageDto){
        this.filename = imageDto.getFileName();
        this.imageUrl = imageDto.getImageUrl();
    }


}