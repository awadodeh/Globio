package com.globio.peopledirectory.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * Globio Inc.\n\nThis is system design for people directory.
 */
@ApiModel(description = "Globio Inc.\n\nThis is system design for people directory.")
@Entity
@Table(name = "user_group")
public class UserGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "group_id")
    private String groupID;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "group_description")
    private String groupDescription;

    @Column(name = "group_owner")
    private String groupOwner;

    @Column(name = "last_updated")
    private String lastUpdated;

    @OneToOne
    @JoinColumn(unique = true)
    private Contact contact;

    @OneToOne
    @JoinColumn(unique = true)
    private Setting setting;

    @OneToOne
    @JoinColumn(unique = true)
    private UserGroup parent;

    @OneToMany(mappedBy = "userGroup")
    private Set<Person> members = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("contacts")
    private Person person;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupID() {
        return groupID;
    }

    public UserGroup groupID(String groupID) {
        this.groupID = groupID;
        return this;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public UserGroup groupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public UserGroup groupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
        return this;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public String getGroupOwner() {
        return groupOwner;
    }

    public UserGroup groupOwner(String groupOwner) {
        this.groupOwner = groupOwner;
        return this;
    }

    public void setGroupOwner(String groupOwner) {
        this.groupOwner = groupOwner;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public UserGroup lastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
        return this;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Contact getContact() {
        return contact;
    }

    public UserGroup contact(Contact contact) {
        this.contact = contact;
        return this;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Setting getSetting() {
        return setting;
    }

    public UserGroup setting(Setting setting) {
        this.setting = setting;
        return this;
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
    }

    public UserGroup getParent() {
        return parent;
    }

    public UserGroup parent(UserGroup userGroup) {
        this.parent = userGroup;
        return this;
    }

    public void setParent(UserGroup userGroup) {
        this.parent = userGroup;
    }

    public Set<Person> getMembers() {
        return members;
    }

    public UserGroup members(Set<Person> people) {
        this.members = people;
        return this;
    }

    public UserGroup addMembers(Person person) {
        this.members.add(person);
        person.setUserGroup(this);
        return this;
    }

    public UserGroup removeMembers(Person person) {
        this.members.remove(person);
        person.setUserGroup(null);
        return this;
    }

    public void setMembers(Set<Person> people) {
        this.members = people;
    }

    public Person getPerson() {
        return person;
    }

    public UserGroup person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserGroup)) {
            return false;
        }
        return id != null && id.equals(((UserGroup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UserGroup{" +
            "id=" + getId() +
            ", groupID='" + getGroupID() + "'" +
            ", groupName='" + getGroupName() + "'" +
            ", groupDescription='" + getGroupDescription() + "'" +
            ", groupOwner='" + getGroupOwner() + "'" +
            ", lastUpdated='" + getLastUpdated() + "'" +
            "}";
    }
}
