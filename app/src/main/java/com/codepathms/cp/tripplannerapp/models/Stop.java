package com.codepathms.cp.tripplannerapp.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.parceler.Parcel;

/**
 * Created by melissa on 4/3/17.
 */

/* Stop fields:
    Id
    Title
    Description
    Tags: Restaurant, Italian
    Rating
    Location
    PricePoint
    PrevStopId
    NextStopId
    ImageUrl
 */
@Parcel(analyze={Stop.class})
//@Table(database = MyDatabase.class)
@ParseClassName("Stop")
public class Stop extends ParseObject {

    public Stop() {
        super();
    }

    public Stop(String title) {
        super();
        setTitle(title);
    }

    public void setTitle(String title) {
        put("title", title);
    }

    public String getTitle() {
        return getString("title");
    }

    public void setDescription(String description) {
        put("description", description);
    }

    public String getDescription() {
        return getString("description");
    }

    public void setImageUrl(String imageUrl) {
        put("imageUrl", imageUrl);
    }

    public String getImageUrl() {
        return getString("imageUrl");
    }

    public void setPlaceId(String placeId) {
        put("placeId", placeId);
    }

    public String getPlaceId() {
        return getString("placeId");
    }

    public void setAddress(String address) {
        put("address", address);
    }

    public String getAddress() {
        return getString("address");
    }

    public void setItineraryId(String itineraryId) {
        put("itineraryId", itineraryId);
    }

    public String getItineraryId() {
        return getString("itineraryId");
    }

    public void setSequenceNumber(int sequence) {
        put("sequence", sequence);
    }

    public int getSequenceNumber() {
        return getInt("sequence");
    }

    // Get the user for this item
    public ParseUser getUser()  {
        return getParseUser("owner");
    }

    // Associate each item with a user
    public void setOwner(ParseUser user) {
        put("owner", user);
    }



    /*
    @Column
    @PrimaryKey (autoincrement=true)
    long id;

    @Column
    String title;

    @Column
    String description;

    @Column
    String tags;

    @Column
    int rating; //1-5

    @Column
    String location; //city name

    @Column
    int pricePoint; //1-4 ($-$$$$)

    @Column
    long prevStopId;

    @Column
    long nextStopId;

    @Column
    String imageUrl;

    @Column
    long itineraryId;

    @Column
    String placeId;

    @Column
    int sequenceNumber;

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPricePoint(int pricePoint) {
        this.pricePoint = pricePoint;
    }

    public void setPrevStopId(long prevStopId) {
        this.prevStopId = prevStopId;
    }

    public void setNextStopId(long nextStopId) {
        this.nextStopId = nextStopId;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setItineraryId(long itineraryId) {
        this.itineraryId = itineraryId;
    }
    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }
    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getTags() {
        return tags;
    }

    public int getRating() {
        return rating;
    }

    public String getLocation() {
        return location;
    }

    public int getPricePoint() {
        return pricePoint;
    }

    public long getPrevStopId() {
        return prevStopId;
    }

    public long getNextStopId() {
        return nextStopId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public long getItineraryId() {
        return itineraryId;
    }

    public String getPlaceId() {
        return placeId;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }
    */
}
