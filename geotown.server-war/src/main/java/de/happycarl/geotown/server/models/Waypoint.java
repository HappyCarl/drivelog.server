package de.happycarl.geotown.server.models;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jhbruhn on 12.06.14.
 */
@Entity
@Cache
@Index
public class Waypoint {
    @Id
    Long id;

    Ref<Route> route;

    double latitude;
    double longitude;

    String question;
    List<String> answers = new ArrayList<String>();

    public Waypoint(Route route, double latitude, double longitude) {
        this.route = Ref.create(route);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    private Waypoint() {
    }

    public Route getRoute() {
        return route.get();
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public boolean equals(Waypoint that) {
        return this.id.equals(that.id);
    }
}
