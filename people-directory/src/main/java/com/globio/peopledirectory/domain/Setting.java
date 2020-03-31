package com.globio.peopledirectory.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Setting.
 */
@Entity
@Table(name = "setting")
public class Setting implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "member_visible")
    private Boolean memberVisible;

    @Column(name = "self_add")
    private Boolean selfAdd;

    @Column(name = "allow_sub_group")
    private Boolean allowSubGroup;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isMemberVisible() {
        return memberVisible;
    }

    public Setting memberVisible(Boolean memberVisible) {
        this.memberVisible = memberVisible;
        return this;
    }

    public void setMemberVisible(Boolean memberVisible) {
        this.memberVisible = memberVisible;
    }

    public Boolean isSelfAdd() {
        return selfAdd;
    }

    public Setting selfAdd(Boolean selfAdd) {
        this.selfAdd = selfAdd;
        return this;
    }

    public void setSelfAdd(Boolean selfAdd) {
        this.selfAdd = selfAdd;
    }

    public Boolean isAllowSubGroup() {
        return allowSubGroup;
    }

    public Setting allowSubGroup(Boolean allowSubGroup) {
        this.allowSubGroup = allowSubGroup;
        return this;
    }

    public void setAllowSubGroup(Boolean allowSubGroup) {
        this.allowSubGroup = allowSubGroup;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Setting)) {
            return false;
        }
        return id != null && id.equals(((Setting) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Setting{" +
            "id=" + getId() +
            ", memberVisible='" + isMemberVisible() + "'" +
            ", selfAdd='" + isSelfAdd() + "'" +
            ", allowSubGroup='" + isAllowSubGroup() + "'" +
            "}";
    }
}
