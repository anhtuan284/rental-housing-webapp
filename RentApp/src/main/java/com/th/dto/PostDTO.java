/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.dto;

/**
 *
 * @author voquochuy
 */
import com.th.pojo.Image;
import com.th.pojo.Post;
import com.th.pojo.PropertyDetail;
import org.springframework.stereotype.Component;

@Component
public class PostDTO {
    private Post post;
    private PropertyDetail propertyDetail;
    private Image image;

    public PostDTO() {
    }

    public PostDTO(Post post, PropertyDetail propertyDetail, Image image) {
        this.post = post;
        this.propertyDetail = propertyDetail;
        this.image = image;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public PropertyDetail getPropertyDetail() {
        return propertyDetail;
    }

    public void setPropertyDetail(PropertyDetail propertyDetail) {
        this.propertyDetail = propertyDetail;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
