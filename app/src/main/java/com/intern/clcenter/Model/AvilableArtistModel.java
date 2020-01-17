package com.intern.clcenter.Model;

public class AvilableArtistModel {

    String artispic, state, featured, price, jobcomp, ratetype, workcat, bio, qualification, fullname, emailid, location, mobileno;
    int rating;
    String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public AvilableArtistModel(String username) {
        this.username = username;
    }

    public AvilableArtistModel(){

    }

    public String getArtispic() {
        return artispic;
    }

    public void setArtispic(String artispic) {
        this.artispic = artispic;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getFeatured() {
        return featured;
    }

    public void setFeatured(String featured) {
        this.featured = featured;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getJobcomp() {
        return jobcomp;
    }

    public void setJobcomp(String jobcomp) {
        this.jobcomp = jobcomp;
    }

    public String getRatetype() {
        return ratetype;
    }

    public void setRatetype(String ratetype) {
        this.ratetype = ratetype;
    }

    public String getWorkcat() {
        return workcat;
    }

    public void setWorkcat(String workcat) {
        this.workcat = workcat;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public AvilableArtistModel(String artispic, String state, String featured, String price, String jobcomp, String ratetype, String workcat, String bio, String qualification, String fullname, String emailid, String location, String mobileno, int rating) {
        this.artispic = artispic;
        this.state = state;
        this.featured = featured;
        this.price = price;
        this.jobcomp = jobcomp;
        this.ratetype = ratetype;
        this.workcat = workcat;
        this.bio = bio;
        this.qualification = qualification;
        this.fullname = fullname;
        this.emailid = emailid;
        this.location = location;
        this.mobileno = mobileno;
        this.rating = rating;
    }
}