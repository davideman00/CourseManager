package testapp.dgreenberg.com.CourseManager.model;

public class Note {
    private long id;
    private long courseID;
    private String title;
    private String text;

    public static Note selectedNote;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String type) {
        this.text = type;
    }

    public long getCourseID() {
        return courseID;
    }

    public void setCourseID(long courseID) {
        this.courseID = courseID;
    }


}
