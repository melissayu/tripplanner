package com.codepathms.cp.tripplannerapp.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by melissa on 4/3/17.
 */

/* Itinerary fields:
    Id
    Title
    Description
    CreatedBy (UserId)
    Tags: food, outdoor, city, loud, italian
    Rating
    Location (city?)
    TimeDuration (calculated after full itinerary saved)
    Distance
    ImageUrl
    Stops (list of Stops)

 */
@Parcel(analyze={Itinerary.class})
//@Table(database = MyDatabase.class)
@ParseClassName("Itinerary")
public class Itinerary extends ParseObject {

    /*
    long id;
    String title;
    String description;
    String tags;
    int rating; //1-5
    String location; //City name
    int timeDuration; //In hours
    float distance;  //In miles
    String imageUrl;
    ArrayList<Stop> stops;
    */

    public Boolean bookmarked;
    public int matchLevel = 1; //1 = high relevance, 3 = low

    public Itinerary() {
        super();
    }

    public Itinerary(String title) {
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

    public void setImageBitmap(ParseFile image) {
        put("image", image);
    }

    public ParseFile getImageBitmap() {
        return getParseFile("image");
    }

    public void setFeatures(List<String> features) {
        put("features", features);
    }

    public List<String> getFeatures() {
        return getList("features");
    }

    public void setPricePoint(int pricePoint) {
        put("pricePoint", pricePoint);
    }

    public int getPricePoint() {
        return getInt("pricePoint");
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
    public void setId(long id) {
        this.id = id;
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

    public void setTimeDuration(int timeDuration) {
        this.timeDuration = timeDuration;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setStops(ArrayList<Stop> stops) {
        this.stops = stops;
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

    public int getTimeDuration() {
        return timeDuration;
    }

    public float getDistance() {
        return distance;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ArrayList<Stop> getStops() {
        return stops;
    }
*/

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
    String location; //City name

    @Column
    int timeDuration; //In hours

    @Column
    float distance;  //In miles

    @Column
    String imageUrl;

    @ColumnIgnore
    ArrayList<Stop> stops;
*/

}
