package de.happycarl.geotown.server.models;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
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
    String rightAnswer;
    List<String> wrongAnswers = new ArrayList<String>();

    String blobstoreImageKey;

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

    public Long getId() {
        return this.id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getWrongAnswers() {
        return wrongAnswers;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public boolean equals(Waypoint that) {
        return this.id.equals(that.id);
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public void setBlobstoreImageKey(String blobstoreImageKey) {
        this.blobstoreImageKey = blobstoreImageKey;
    }

    public String getImageUrl() {
        ImagesService imagesService = ImagesServiceFactory.getImagesService();
        return imagesService.getServingUrl(ServingUrlOptions.Builder.withBlobKey(new BlobKey(blobstoreImageKey)));
    }
}
