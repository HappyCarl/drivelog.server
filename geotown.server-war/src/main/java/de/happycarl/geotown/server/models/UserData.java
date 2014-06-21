package de.happycarl.geotown.server.models;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.google.appengine.api.users.User;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import de.happycarl.geotown.server.util.Deref;

import java.util.ArrayList;
import java.util.List;

@Entity
@Cache
@Index
public class UserData {
    @Id
    String email;

    String username;

    public UserData(User user) {
        this.email = user.getEmail();
    }

    private UserData() {
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String n) {
        this.username = n;
    }

    public String getUsername() {
        return this.username;
    }

    public boolean equals(UserData that) {
        return this.getEmail().equals(that.getEmail());
    }
}
