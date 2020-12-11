package com.partyhost.model;

import com.partyhost.repository.UsersRepository;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "users")
@SecondaryTable(name = "password_storage", pkJoinColumns = @PrimaryKeyJoinColumn(name="user_id"))
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String emailId;

    @Column(name = "mobile_number")
    private Long mobileNumber;

    @Column(table = "password_storage", name="password")
    private String password;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserFriends> userFriendsList;

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(Long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean passwordAuthenticate(String password) {
        return this.password.equals(password);
    }

    public UserFriends getFriendshipDetail(Long friendId) {
        for (UserFriends userFriends: userFriendsList) {
            if(friendId.equals(userFriends.getFriendId())) {
                return userFriends;
            }
        }
        return null;
    }

    public List<UserFriends> fetchFriendsAmountList() {
        return userFriendsList;
    }

    public List<Users> getDetailedFriendsList(UsersRepository usersRepository) {
        List<Users> list = new LinkedList<>();
        for (UserFriends userFriends: userFriendsList) {
            Long friendId = userFriends.getFriendId();
            Optional<Users> friend = usersRepository.findById(friendId);
            if(friend.isPresent()) {
                list.add(friend.get());
            }
        }
        return list;
    }

    public void setUserFriendsList(List<UserFriends> userFriendsList) {
        this.userFriendsList = userFriendsList;
    }
}
