/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MapeoBD;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Eduardo
 */
@Entity
@Table(name = "securityItem", catalog = "SEGURITYSF", schema = "dbo")
@NamedQueries({
    @NamedQuery(name = "SecurityItem.findAll", query = "SELECT s FROM SecurityItem s")})
public class SecurityItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "securityItemId", nullable = false)
    private Integer securityItemId;
    @Basic(optional = false)
    @Column(name = "securityItemName", nullable = false, length = 100)
    private String securityItemName;
    @Column(name = "description", length = 200)
    private String description;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "price", precision = 53)
    private Double price;
    @Column(name = "appliesInstalation")
    private Boolean appliesInstalation;
    @OneToMany(mappedBy = "securityItemId", fetch = FetchType.LAZY)
    private Collection<Stock> stockCollection;

    public SecurityItem() {
    }

    public SecurityItem(Integer securityItemId) {
        this.securityItemId = securityItemId;
    }

    public SecurityItem(Integer securityItemId, String securityItemName) {
        this.securityItemId = securityItemId;
        this.securityItemName = securityItemName;
    }

    public Integer getSecurityItemId() {
        return securityItemId;
    }

    public void setSecurityItemId(Integer securityItemId) {
        this.securityItemId = securityItemId;
    }

    public String getSecurityItemName() {
        return securityItemName;
    }

    public void setSecurityItemName(String securityItemName) {
        this.securityItemName = securityItemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getAppliesInstalation() {
        return appliesInstalation;
    }

    public void setAppliesInstalation(Boolean appliesInstalation) {
        this.appliesInstalation = appliesInstalation;
    }

    public Collection<Stock> getStockCollection() {
        return stockCollection;
    }

    public void setStockCollection(Collection<Stock> stockCollection) {
        this.stockCollection = stockCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (securityItemId != null ? securityItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SecurityItem)) {
            return false;
        }
        SecurityItem other = (SecurityItem) object;
        if ((this.securityItemId == null && other.securityItemId != null) || (this.securityItemId != null && !this.securityItemId.equals(other.securityItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SecurityItem{" +
               "ID=" + securityItemId +
               ", Nombre='" + securityItemName + '\'' +
               ", Descripción='" + description + '\'' +
               ", Precio=" + price +
               ", Aplica Instalación=" + appliesInstalation +
               '}';
    }
}
