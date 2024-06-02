/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.pojo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Hi
 */
@Entity
@Table(name = "property_detail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PropertyDetail.findAll", query = "SELECT p FROM PropertyDetail p"),
    @NamedQuery(name = "PropertyDetail.findByPropertyDetailId", query = "SELECT p FROM PropertyDetail p WHERE p.propertyDetailId = :propertyDetailId"),
    @NamedQuery(name = "PropertyDetail.findByCreatedDate", query = "SELECT p FROM PropertyDetail p WHERE p.createdDate = :createdDate"),
    @NamedQuery(name = "PropertyDetail.findByPrice", query = "SELECT p FROM PropertyDetail p WHERE p.price = :price"),
    @NamedQuery(name = "PropertyDetail.findByAcreage", query = "SELECT p FROM PropertyDetail p WHERE p.acreage = :acreage"),
    @NamedQuery(name = "PropertyDetail.findByCapacity", query = "SELECT p FROM PropertyDetail p WHERE p.capacity = :capacity")})
public class PropertyDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "property_detail_id")
    private Integer propertyDetailId;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Size(max = 45)
    @Column(name = "price")
    private String price;
    @Size(max = 20)
    @Column(name = "acreage")
    private String acreage;
    @Column(name = "capacity")
    private Integer capacity;
    @JoinColumn(name = "Post_id", referencedColumnName = "post_id")
    @ManyToOne(optional = false)
    private Post postid;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "propertyDetail")
    private Location location;

    public PropertyDetail() {
    }

    public PropertyDetail(Integer propertyDetailId) {
        this.propertyDetailId = propertyDetailId;
    }

    public Integer getPropertyDetailId() {
        return propertyDetailId;
    }

    public void setPropertyDetailId(Integer propertyDetailId) {
        this.propertyDetailId = propertyDetailId;
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

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Post getPostid() {
        return postid;
    }

    public void setPostid(Post postid) {
        this.postid = postid;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (propertyDetailId != null ? propertyDetailId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PropertyDetail)) {
            return false;
        }
        PropertyDetail other = (PropertyDetail) object;
        if ((this.propertyDetailId == null && other.propertyDetailId != null) || (this.propertyDetailId != null && !this.propertyDetailId.equals(other.propertyDetailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.th.pojo.PropertyDetail[ propertyDetailId=" + propertyDetailId + " ]";
    }
    
}
