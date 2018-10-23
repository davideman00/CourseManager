package testapp.dgreenberg.com.CourseManager.model;

public class Assessment implements Comparable<Assessment>{
    private long id;
    private long courseID;
    private String title;
    private String type;
    private String dueDate;
    private String goalDate;

    public static Assessment selectedAssessment;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getGoalDate() {
        return goalDate;
    }

    public void setGoalDate(String goalDate) {
        this.goalDate = goalDate;
    }


    public long getCourseID() {
        return courseID;
    }

    public void setCourseID(long courseID) {
        this.courseID = courseID;
    }

    @Override
    public int compareTo(Assessment obj) {
        return Long.compare(obj.courseID, this.courseID);
    }
}
