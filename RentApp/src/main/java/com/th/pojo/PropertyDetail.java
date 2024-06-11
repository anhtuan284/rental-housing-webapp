/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author voquochuy
 */
@Entity
@Table(name = "property_detail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PropertyDetail.findAll", query = "SELECT p FROM PropertyDetail p"),
    @NamedQuery(name = "PropertyDetail.findByCreatedDate", query = "SELECT p FROM PropertyDetail p WHERE p.createdDate = :createdDate"),
    @NamedQuery(name = "PropertyDetail.findByPrice", query = "SELECT p FROM PropertyDetail p WHERE p.price = :price"),
    @NamedQuery(name = "PropertyDetail.findByAcreage", query = "SELECT p FROM PropertyDetail p WHERE p.acreage = :acreage"),
    @NamedQuery(name = "PropertyDetail.findByCapacity", query = "SELECT p FROM PropertyDetail p WHERE p.capacity = :capacity"),
    @NamedQuery(name = "PropertyDetail.findByPostId", query = "SELECT p FROM PropertyDetail p WHERE p.postId = :postId")})
public class PropertyDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "price")
    private String price;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "acreage")
    private String acreage;
    @Basic(optional = false)
    @NotNull
    @Column(name = "capacity")
    private int capacity;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "post_id")
    private Integer postId;
    @JoinColumn(name = "post_id", referencedColumnName = "post_id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    @JsonIgnore
    private Post post;

    public PropertyDetail() {
    }

    public PropertyDetail(Integer postId) {
        this.postId = postId;
    }

    public PropertyDetail(Integer postId, String price, String acreage, int capacity) {
        this.postId = postId;
        this.price = price;
        this.acreage = acreage;
        this.capacity = capacity;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAcreage() {
        return acreage;
    }

    public void setAcreage(String acreage) {
        this.acreage = acreage;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (postId != null ? postId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PropertyDetail)) {
            return false;
        }
        PropertyDetail other = (PropertyDetail) object;
        if ((this.postId == null && other.postId != null) || (this.postId != null && !this.postId.equals(other.postId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.th.pojo.PropertyDetail[ postId=" + postId + " ]";
    }
    
}
