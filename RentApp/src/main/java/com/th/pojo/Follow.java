/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.pojo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author voquochuy
 */
@Entity
@Table(name = "Follow")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Follow.findAll", query = "SELECT f FROM Follow f"),
    @NamedQuery(name = "Follow.findByFollowId", query = "SELECT f FROM Follow f WHERE f.followId = :followId")})
public class Follow implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "follow_id")
    private Integer followId;
    @JoinColumn(name = "follower_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User followerId;
    @JoinColumn(name = "followee_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User followeeId;

    public Follow() {
    }

    public Follow(Integer followId) {
        this.followId = followId;
    }

    public Integer getFollowId() {
        return followId;
    }

    public void setFollowId(Integer followId) {
        this.followId = followId;
    }

    public User getFollowerId() {
        return followerId;
    }

    public void setFollowerId(User followerId) {
        this.followerId = followerId;
    }

    public User getFolloweeId() {
        return followeeId;
    }

    public void setFolloweeId(User followeeId) {
        this.followeeId = followeeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (followId != null ? followId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Follow)) {
            return false;
        }
        Follow other = (Follow) object;
        if ((this.followId == null && other.followId != null) || (this.followId != null && !this.followId.equals(other.followId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.th.pojo.Follow[ followId=" + followId + " ]";
    }
    
}
