package testapp.dgreenberg.com.CourseManager.model;


public class Term {
    private long id;
    private String name;
    private String start;
    private String end;



    public static Term selectedTerm;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }


    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }





    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return name;
    }
}